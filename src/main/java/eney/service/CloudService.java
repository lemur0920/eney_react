package eney.service;

//import com.google.api.client.util.Base64;
import com.google.gson.*;
import com.sun.org.apache.xpath.internal.operations.Bool;
import eney.domain.*;
import eney.mapper.AdminDao;
import eney.mapper.CloudDao;
import eney.mapper.ServiceApplyDao;
import eney.util.HttpRequestUtil;
import org.apache.commons.io.FileUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.velocity.texen.util.FileUtil;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.io.File;
import java.io.FileInputStream;


@Service
public class CloudService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Resource
    HttpServletRequest request;
    @Resource
    UserService userService;
    @Resource
    SupplyService supplyService;
    @Resource
    FileService fileService;

    @Resource
    ServiceApplyDao serviceApplyDao;
    @Resource
    CloudDao cloudDao;

    @Resource
    AdminDao adminDao;
    @Resource
    MailService mailService;

    public void instanceCreate(int applyIndex)throws ClientProtocolException, IOException {
        ServiceApplyVO serviceApplyVO = serviceApplyDao.getCloudServiceApply(applyIndex);


        //token을 얻기 위해 로그인
        OpenStackUserVO openStackAdmin = this.openStackLogin();
        System.out.println("======");

        //instance 생성 api호출
        Map<String,Object> createResponse = this.callCreateInstance(openStackAdmin, serviceApplyVO);
        String body = (String) createResponse.get("body");

        //response에서 instance_id 가져옴
        JsonParser jsonParser = new JsonParser();
        JsonObject object = (JsonObject)jsonParser.parse(body);
        JsonObject server = (JsonObject)object.get("server");

        String instanceId = server.get("id").toString().replace("\"","");;

        OpenStackInstanceVO openStackInstanceVO = new OpenStackInstanceVO();

        openStackInstanceVO.setInstance_id(instanceId);
        openStackInstanceVO.setApply_idx(serviceApplyVO.getIdx());
        openStackInstanceVO.setUser_id(serviceApplyVO.getUserid());

        //DB에 인스턴스 정보 추가
        cloudDao.addInstanceInfo(openStackInstanceVO);

        //신청 상태 변경
        serviceApplyVO.setPay_state(PaymentLogVo.PayStatus.approve);
        serviceApplyVO.setStatus("using");

        System.out.println("======");
        System.out.println(serviceApplyVO);
        System.out.println("======");
        serviceApplyDao.editCloudServiceApplyStatus(serviceApplyVO);

    }

    public Boolean instanceRemove(int applyIndex)throws ClientProtocolException,NullPointerException, IOException {
        ServiceApplyVO serviceApplyVO = serviceApplyDao.getCloudServiceApply(applyIndex);
        OpenStackInstanceVO openStackInstanceVO = cloudDao.getOpenStackInstanceInfoByApplyIdx(applyIndex);

        //instance 생성 api호출

        try{
            openStackInstanceVO.setStatus("deleted");
            Map<String,Object> result = this.callInstanceRemove(openStackInstanceVO);

            if(((HttpResponse) result.get("response")).getStatusLine().getStatusCode() == 204);
            serviceApplyVO.setStatus("deleted");
            serviceApplyVO.setPay_state(PaymentLogVo.PayStatus.standby);
            serviceApplyDao.editCloudServiceApplyStatus(serviceApplyVO);
            cloudDao.editInstanceInfo(openStackInstanceVO);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public OpenStackUserVO openStackLogin() throws ClientProtocolException, IOException {

        OpenStackUserVO openStackAdmin = cloudDao.getOpenStackUser("eney_cloud");

        //최종 완성될 JSONObject 선언(전체)
        Gson gson = new Gson();
        JsonObject jsonObject = new JsonObject();

        JsonObject auth = new JsonObject();
        JsonObject identity = new JsonObject();
        JsonObject scope = new JsonObject();
        JsonArray methods = new JsonArray();
        methods.add("password");
        JsonObject password = new JsonObject();
        JsonObject user = new JsonObject();

        JsonObject domain = new JsonObject();
        domain.addProperty("id", "default");

        user.addProperty("name", openStackAdmin.getUser_id());
        user.add("domain",domain);
        user.addProperty("password", openStackAdmin.getPasswd());

        password.add("user",user);

        identity.add("methods",methods);
        identity.add("password",password);

        auth.add("identity",identity);

        JsonObject project = new JsonObject();
        //eney_cloud 프로젝트 id
        project.addProperty("id", "44c4eec0f496483d9f6ce07d10add35e");
        scope.add("project", project);
        auth.add("scope",scope);

        jsonObject.add("auth",auth);
        String jsonInfo = gson.toJson(jsonObject);

        Map<String,Object> loginResult = HttpRequestUtil.sendHttpPost(openStackAdmin.getIdentity_url()+"/auth/tokens",jsonObject,null,null);

        HttpResponse loginResponse = (HttpResponse)loginResult.get("response");
        openStackAdmin.setToken(loginResponse.getFirstHeader("X-Subject-Token").getValue());

        return  openStackAdmin;
    }

    public String createUserData(String password) throws ClientProtocolException, IOException {

        String contents = new String(Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"/src/main/resources/config/cloud-config.txt")), Charset.defaultCharset());
        String userData = java.util.Base64.getEncoder().encodeToString(contents.replaceFirst("password", password).getBytes());

        return userData;
    }

    public Map<String,Object> callCreateInstance(OpenStackUserVO openStackAdmin,ServiceApplyVO serviceApplyVO) throws ClientProtocolException, IOException {

        try{
            String imageId = cloudDao.getOpenStackImageId("centos_7").getImage_id();
            String networkUUID = cloudDao.getOpenStackNetworkUUID("private_network").getNetwork_uuid();
            String flavorId = "";
            if(serviceApplyVO.getService_type().equals("web_single")){
                flavorId = cloudDao.getOpenStackFlavorId("single").getId();
            }else if(serviceApplyVO.getService_type().equals("web_double")){
                flavorId = cloudDao.getOpenStackFlavorId("double").getId();
            }

            OpenStackInstanceVO openStackInstanceVO = new OpenStackInstanceVO();
            openStackInstanceVO.setUser_id(serviceApplyVO.getUserid());
            int instanceCnt = cloudDao.instanceInfoCnt(openStackInstanceVO) + 1;

            //최종 완성될 JSONObject 선언(전체)
            Gson gson = new Gson();
            JsonObject jsonObject = new JsonObject();

            JsonObject server = new JsonObject();

            JsonArray networks = new JsonArray();
            JsonObject uuid = new JsonObject();

            uuid.addProperty("uuid", networkUUID);
            networks.add(uuid);
            server.addProperty("name",serviceApplyVO.getUserid()+"_cloud_"+instanceCnt);
            server.addProperty("imageRef",imageId);
            server.addProperty("flavorRef",flavorId);
            server.addProperty("OS-DCF:diskConfig","AUTO");
            server.addProperty("user_data",this.createUserData(serviceApplyVO.getUserid()));
            server.add("networks",networks);



            jsonObject.add("server",server);
            String jsonInfo = gson.toJson(jsonObject);

            return  HttpRequestUtil.sendHttpPost(openStackAdmin.getCompute_url()+"/servers",jsonObject,null, openStackAdmin.getToken());
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Map<String,Object>> getInstanceInfoList(OpenStackInstanceVO openStackInstanceVO) throws IOException {

//        try{

            //token을 얻기 위해 로그인
            OpenStackUserVO openStackAdmin = this.openStackLogin();
            List<OpenStackInstanceVO> instanceList =  cloudDao.getInstanceInfoList(openStackInstanceVO);

            List<Map<String,Object>> infoList = new ArrayList<>();

                instanceList.forEach(instance-> {
                    Map<String,Object> response = this.callInstanceInfo(instance, openStackAdmin);
                    String body = (String) response.get("body");

                    //response에서 instance_id 가져옴
                    JsonParser jsonParser = new JsonParser();
                    JsonObject object = (JsonObject)jsonParser.parse(body);
                    JsonObject server = (JsonObject)object.get("server");

                    Gson gson = new Gson();

                    Map map = new HashMap();

                    map = (Map) gson.fromJson(server, map.getClass());
                    infoList.add(map);
                });

            return infoList;
//        }catch(Exception e){
//            e.printStackTrace();
//            return null;
//        }
    }

    public Map<String,Object> callInstanceInfo(OpenStackInstanceVO openStackInstanceVO, OpenStackUserVO openStackUserVO){

        try{
            System.out.println(openStackUserVO);
            System.out.println("------");
            System.out.println(openStackUserVO.getCompute_url()+"/servers/"+openStackInstanceVO.getInstance_id());
            return  HttpRequestUtil.sendHttpGet(openStackUserVO.getCompute_url()+"/servers/"+openStackInstanceVO.getInstance_id(),null,null, openStackUserVO.getToken());
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public Boolean instanceStart(OpenStackInstanceVO openStackInstanceVO){

        try{
            Map<String,Object> result = this.callInstanceStart(openStackInstanceVO);

            if(((HttpResponse) result.get("response")).getStatusLine().getStatusCode() == 202);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public Map<String,Object> callInstanceStart(OpenStackInstanceVO openStackInstanceVO){

        try{
            OpenStackUserVO openStackUserVO = this.openStackLogin();


            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("os-start", "");
            return  HttpRequestUtil.sendHttpPost(openStackUserVO.getCompute_url()+"/servers/"+openStackInstanceVO.getInstance_id()+"/action",jsonObject,null, openStackUserVO.getToken());
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Boolean instanceStop(OpenStackInstanceVO openStackInstanceVO){

        try{
            Map<String,Object> result = this.callInstanceStop(openStackInstanceVO);

            System.out.println(((HttpResponse) result.get("response")).getStatusLine().getStatusCode());

            if(((HttpResponse) result.get("response")).getStatusLine().getStatusCode() == 202);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public Map<String,Object> callInstanceStop(OpenStackInstanceVO openStackInstanceVO){

        try{
            OpenStackUserVO openStackUserVO = this.openStackLogin();


            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("os-stop", "");
            return  HttpRequestUtil.sendHttpPost(openStackUserVO.getCompute_url()+"/servers/"+openStackInstanceVO.getInstance_id()+"/action",jsonObject,null, openStackUserVO.getToken());
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Boolean instanceRestart(OpenStackInstanceVO openStackInstanceVO){

        try{
            Map<String,Object> result = this.callInstanceRestart(openStackInstanceVO);

            System.out.println(((HttpResponse) result.get("response")).getStatusLine().getStatusCode());

            if(((HttpResponse) result.get("response")).getStatusLine().getStatusCode() == 202);
            return true;
        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }


    public Map<String,Object> callInstanceRestart(OpenStackInstanceVO openStackInstanceVO){

        try{
            OpenStackUserVO openStackUserVO = this.openStackLogin();


            JsonObject jsonObject = new JsonObject();
            JsonObject type = new JsonObject();
            type.addProperty("type","HARD");

            jsonObject.add("reboot",type);

            return  HttpRequestUtil.sendHttpPost(openStackUserVO.getCompute_url()+"/servers/"+openStackInstanceVO.getInstance_id()+"/action",jsonObject,null, openStackUserVO.getToken());
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public Map<String,Object> callInstanceRemove(OpenStackInstanceVO openStackInstanceVO){

        try{
            OpenStackUserVO openStackUserVO = this.openStackLogin();

            return  HttpRequestUtil.sendHttpDelete(openStackUserVO.getCompute_url()+"/servers/"+openStackInstanceVO.getInstance_id(), null,null, openStackUserVO.getToken());
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
    }


}

