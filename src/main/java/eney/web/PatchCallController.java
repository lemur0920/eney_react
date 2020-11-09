package eney.web;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.io.ByteStreams;
import com.sun.media.jfxmediaimpl.MediaUtils;
import eney.domain.*;
import eney.mapper.AdminDao;
import eney.mapper.MainDao;
import eney.mapper.SupplyDao;
import eney.service.AcsService;
import eney.service.FileService;
import eney.service.SupplyService;
import eney.service.UserService;
import eney.util.DateUtil;
import eney.util.HttpRequestUtil;
import eney.util.ObjectUtil;
import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import sun.awt.image.InputStreamImageSource;
import sun.management.resources.agent;

import javax.activation.MimeType;
import javax.activation.MimetypesFileTypeMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.*;
//import java.sql.Date;
import java.util.Date;
import java.util.concurrent.Executor;

@Controller
@RequestMapping(value = "/service/patchcall")
public class PatchCallController {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceController.class);

    @Resource
    UserService userService;

    @Resource
    FileService fileService;

    @Resource
    SupplyService supplyService;

    @Resource
    AcsService acsService;

    @Resource
    MainDao mainDao;

    @Resource
    AdminDao adminDao;

    @Resource
    SupplyDao supplyDao;


    @RequestMapping(value = "/management", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> PatchcallSearchList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "search", required = false) String search, @RequestParam(value = "search_filed", required = false) String searchFiled, Authentication authentication) {

        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        UserVO userVO = userService.loadUserByUsername(user.getUserId());

        AgentVO agentVO = new AgentVO();
        agentVO.setUser_id(userVO.getUserid());
        agentVO.setPageNo(page);


        if (search != null && searchFiled != null) {
            agentVO.setSearchFiled(searchFiled);
            agentVO.setSearchValue(search);
        }

        Map<String, Object> map = supplyService.getAgentVOListForService(agentVO);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @RequestMapping(value = "/management/edit", method = RequestMethod.POST)
    public ResponseEntity<?> updateAgent(AgentVO agentVO, Authentication authentication, HttpServletRequest request) throws IOException {

        long startTime = System.currentTimeMillis();
        Map<String, String> map = new HashMap<>();

        map.put("type", "번호 수정");

        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        UserVO userVO = userService.loadUserByUsername(user.getUserId());

        ObjectMapper oMapper = new ObjectMapper();
        AgentAddressVO agentAddressVO = oMapper.readValue(agentVO.getAgent_address_info(), AgentAddressVO.class);
        agentAddressVO.setVno(agentVO.getVno());

        AgentVO tmpAgent = new AgentVO();
        tmpAgent.setVno(agentVO.getVno());
        tmpAgent.setUser_id(userVO.getUserid());
        tmpAgent = supplyService.getAgentVO(tmpAgent);

        agentVO.setIdx(tmpAgent.getIdx());

        try{
            if(agentVO.getFiles() != null && agentVO.getFiles().get(0) != null && !agentVO.getFiles().isEmpty()){
                List<FileVO> fileVOList = fileService.processUpload(agentVO.getFiles(), "MMS", 0, request);
                System.out.println(fileVOList.get(0));
                agentVO.setMms_file("/usr/local/upload/mms/"+fileVOList.get(0).getUpload_name() + fileVOList.get(0).getExtenstion());
            }


            Integer success = supplyService.update050Agent(agentVO);

            supplyService.updateAgentAddress(agentAddressVO);

            long endTime = System.currentTimeMillis();
            logger.warn("번호 수정까지 걸린 시간  = " +( endTime - startTime )/1000.0f +"초");

            map.put("time", ( endTime - startTime )/1000.0f +"초");
            mainDao.insertTimeMeasure(map);

            Map<String,Integer> enablePatchcall = adminDao.getEnablePatchCallSKB();
            supplyDao.skbUseNumber(enablePatchcall);

            return new ResponseEntity<>(HttpStatus.OK);


        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        }
    }

    @RequestMapping(value = "/management/detail", method = RequestMethod.GET)
    public ResponseEntity<?> update050View1(@RequestParam(value="vno") String vno, Authentication authentication) {
        Map<String, Object> map = new HashMap<String, Object>();
        UserPrincipal user = (UserPrincipal)authentication.getPrincipal();

        UserVO userVO = userService.loadUserByUsername(user.getUserId());

        CallbackSmsVO callbackCheck = userService.checkCallbackSmsService(user.getUserId());

//        AgentAddressVO agentAddress = supplyService.getAgentAddress(vno);

        AgentVO agentVO = new AgentVO();
        agentVO.setVno(vno);
        agentVO.setUser_id(user.getUserId());
        agentVO = supplyService.getAgentVO(agentVO);

        map.put("agentVO",agentVO);
        map.put("address",supplyService.getAgentAddress(vno));

        CommonVO commonVO = new CommonVO();
        commonVO.setCategory("050_NUMBER_ASSIGNED");
        Map<String, List<CommonVO>> numberOption = supplyService.get050_1th2ndNumList(commonVO);

        map.put("numberOption",numberOption);

        ColoringUploadVO param = new ColoringUploadVO();
        param.setUserid(user.getUserId());
        List<AcsTransmitVO> messagingList = acsService.getMessaingListByUserid(user.getUserId());

        CallbackSmsVO callback = new CallbackSmsVO();
        callback.setUserid(user.getUserId());

        if(agentVO.getSms_yn().equals("M") && agentVO.getMms_file() != null){
            String fileName = agentVO.getMms_file().substring(22);
            String onlyName = fileName.substring(0,36);

            FileVO file = fileService.getFileVO(onlyName);

            String agentVO_mms_file = file.getName();

            map.put("agentVO_mms_file",agentVO_mms_file);
        }

        map.put("callbackCheck", callbackCheck);
        map.put("messagingList", messagingList);
//        map.put("agentAddress", agentAddress);

        return new ResponseEntity<>(map, HttpStatus.OK);

    }


    @RequestMapping(value = "/call_log", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getPatchCallLogList(@RequestParam(value="page", defaultValue="1") Integer page, @RequestParam(value="search", required = false) String search, @RequestParam(value="search_filed",required = false) String searchFiled ,
                                          @RequestParam(value="startDate",required = false) String startDate,@RequestParam(value="endDate",required = false) String endDate,Authentication authentication) {

        UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
        UserVO userVO = userService.loadUserByUsername(user.getUserId());

        Map<String, Object> map = new HashMap<String, Object>();

        CallLogVO callLogVO = new CallLogVO();
        callLogVO.setPageNo(page);
        callLogVO.setUser_id(userVO.getUserid());

        if(startDate != null && startDate != ""){
            callLogVO.setStartDate(DateUtil.getStringToDate(startDate));
        }
        if(endDate != null && endDate != ""){
            callLogVO.setEndDate(DateUtil.getStringToDate(endDate));
        }

        if(search != null && searchFiled != null){
            callLogVO.setSearchFiled(searchFiled);
            callLogVO.setSearchValue(search);
        }

        System.out.println(callLogVO);

        map = supplyService.getCallLogList(callLogVO);

        return new ResponseEntity<>(map, HttpStatus.OK);

    }

    @RequestMapping(value = "/call_log/excel_download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getPatchCallLogListDownload(@RequestParam(value="search", required = false) String search, @RequestParam(value="search_filed",required = false) String searchFiled ,
                                          @RequestParam(value="startDate",required = false) String startDate,@RequestParam(value="endDate",required = false) String endDate,Authentication authentication) {

        UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
        UserVO userVO = userService.loadUserByUsername(user.getUserId());

        Map<String, Object> map = new HashMap<String, Object>();

        CallLogVO callLogVO = new CallLogVO();
//        callLogVO.setPageNo(page);
        callLogVO.setUser_id(userVO.getUserid());

        if(startDate != null && startDate != ""){
            callLogVO.setStartDate(DateUtil.getStringToDate(startDate));
        }
        if(endDate != null && endDate != ""){
            callLogVO.setEndDate(DateUtil.getStringToDate(endDate));
        }

        if(search != null && searchFiled != null){
            callLogVO.setSearchFiled(searchFiled);
            callLogVO.setSearchValue(search);
        }


        ByteArrayOutputStream os  = supplyService.getCallLogListDownload(callLogVO);

        String contentType = "application/vnd.ms-excel";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=text.xls").body(os.toByteArray());

    }

    @RequestMapping(value = "/call_log/audio_download", method = RequestMethod.POST)
    public ResponseEntity<byte[]> getPatchCallLogAudioDownload(@RequestBody Map<String, String> audioPath, Authentication authentication) {

//    public void getPatchCallLogAudioDownload(@RequestBody String fileName, Authentication authentication) {
        UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
        UserVO userVO = userService.loadUserByUsername(user.getUserId());



        try{
            String month = (String)audioPath.get("month");
            String fileName = (String)audioPath.get("fileName");
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
            HttpGet getRequest = new HttpGet("https://www.eney.co.kr/rec_file/"+month+"/"+fileName); //GET 메소드 URL 생성
            HttpResponse response = client.execute(getRequest);

            InputStream is = response.getEntity().getContent();

                    ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            System.out.println("====");

            String contentType = "application/wav";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+fileName).body(buffer.toByteArray());

        }catch (Exception e){
            System.err.println(e.toString());
            return null;
        }


    }

    @RequestMapping(value = "/sample_download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> patchCallManageSampleDownload(Authentication authentication) {
        UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
        UserVO userVO = userService.loadUserByUsername(user.getUserId());

        try {
            FileInputStream file = new FileInputStream("/upload/resources/static/번호관리_샘플.xls");
            HSSFWorkbook workbook = new HSSFWorkbook(file);

            ByteArrayOutputStream os = new ByteArrayOutputStream();
            workbook.write(os);
            os.close();


            String contentType = "application/vnd.ms-excel";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=text.xls").body(os.toByteArray());

        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }

//        bos.flush();
//        // Close destination stream
//        bos.close();
        // Close URL stream
//        is.close();

//        return ResponseEntity.ok()
//                .contentType(MediaType.parseMediaType(contentType))
//                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=test.xls").body(bFile);


//        InputStream in = null;
//        ResponseEntity<byte[]> entity = null;


//        try {
//            File target = new File(System.getProperty("user.dir")+"/src/main/resources/static/번호관리_샘플.xls");
////            String mimeType = Files.probeContentType(Paths.get(target.getAbsolutePath()));
////            if (mimeType == null) {
////                mimeType = "application/octet-stream";
////            }
//
//            HttpHeaders headers = new HttpHeaders();
//
//            in = new FileInputStream(System.getProperty("user.dir")+"/src/main/resources/static/번호관리_샘플.xls");
//            String mimeType = URLConnection.guessContentTypeFromName(System.getProperty("user.dir")+"/src/main/resources/static/번호관리_샘플.xls");
//
//            System.out.println(mimeType);
//
//            if (mimeType != null) {
//                headers.setContentType(MediaType.parseMediaType(mimeType));
//            } else {
//                String fileName = "test.png";
//                headers.setContentType(MediaType.parseMediaType("application/vnd.ms-excel"));
//                headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
//                headers.add("Content-Disposition", "attatchment; filename=test.xls");
//            }
//            entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), headers, HttpStatus.CREATED);
//        } catch(Exception e) {
//            e.printStackTrace();
//            entity = new ResponseEntity<byte[]>(HttpStatus.BAD_REQUEST);
//        } finally {
//            in.close();
//        }
//
//        return entity;

//        try{
////            byte[] sample = Files.readAllBytes(Paths.get(System.getProperty("user.dir")+"/src/main/resources/static/번호관리샘플파일.xls"));
//
//            File target = new File(System.getProperty("user.dir")+"/src/main/resources/static/excel_icon.png");
//            HttpHeaders header = new HttpHeaders();
//            org.springframework.core.io.Resource rs = null;
//            if(target.exists()) {
//                String mimeType = Files.probeContentType(Paths.get(target.getAbsolutePath()));
//                if (mimeType == null) {
//                    mimeType = "application/octet-stream";
//                }
//
//                rs = new UrlResource(target.toURI());
//                header.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename =\""+rs.getFilename() +"\"");
//                header.setCacheControl("no-cache");
//                header.setContentType(MediaType.parseMediaType(mimeType));
//            }
//
//
//            return new ResponseEntity<org.springframework.core.io.Resource>(rs, header, HttpStatus.OK);
//
//        }catch (Exception e){
//
//            System.err.println(e.toString());
//            return new ResponseEntity<org.springframework.core.io.Resource>(HttpStatus.BAD_REQUEST);
//
//        }


    }

    @RequestMapping(value="/cid",method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<?>  acsTransmitList (@RequestParam(value="page", defaultValue="1") Integer page,Authentication authentication){

        UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
        UserVO userVO = userService.loadUserByUsername(user.getUserId());

        Map<String, Object> map = new HashMap<String, Object>();

        AcsTransmitVO acsTransmitVO = new AcsTransmitVO();
        acsTransmitVO.setUserid(userVO.getUserid());
        acsTransmitVO.setPageNo(page);

        map = acsService.getMessaingListWithPage(acsTransmitVO);

        System.out.println(page);


        return new ResponseEntity<>(map, HttpStatus.OK);

    }

    @RequestMapping(value = "/All_cid", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getAllCidList(Authentication authentication) {

        System.out.println("All_cid");

        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        UserVO userVO = userService.loadUserByUsername(user.getUserId());

        Map<String, Object> map = new HashMap<String, Object>();

        AcsTransmitVO acsTransmitVO = new AcsTransmitVO();
        acsTransmitVO.setUserid(userVO.getUserid());

        map = acsService.getAllCidList(acsTransmitVO);


        return new ResponseEntity<>(map, HttpStatus.OK);

    }

    @RequestMapping(value="/removeAcs",method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<?>  removeAcs (@RequestParam(value="page", defaultValue="1") Integer page,Authentication authentication){


        AcsTransmitVO acsTransmitVO = new AcsTransmitVO();
        acsTransmitVO.setTel_num("01090509223");
        acsService.deleteMessagingNumber(acsTransmitVO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/excel_upload",method=RequestMethod.POST)
    public ResponseEntity<?> excelUpload (@RequestParam("file") MultipartFile file,Authentication authentication){

        try{
            UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
            UserVO userVO = userService.loadUserByUsername(user.getUserId());


            Exception result = supplyService.bulkUpdateAgent(file, userVO.getUserid());
            if(result != null){
                return new ResponseEntity<>("데이터를 확인해주세요",HttpStatus.BAD_REQUEST);
            }else{
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("데이터를 확인해주세요.",HttpStatus.BAD_REQUEST);

        }

    }


}
