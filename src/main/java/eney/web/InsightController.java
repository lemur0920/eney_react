
package eney.web;

import eney.api.v1.domain.ApiTokenVo;
import eney.api.v1.service.ApiMainService;
import eney.mapper.AdminDao;
import eney.mapper.MainDao;
import eney.mapper.SupplyDao;
import eney.service.*;
import eney.util.ExcelView;
import eney.util.FileUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.*;
import eney.domain.*;
import eney.util.ExcelView;
import eney.util.FileUtil;
import io.swagger.annotations.Api;
import jdk.nashorn.internal.parser.JSONParser;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping(value = "/api/service/*")
@Api(value = "service", produces = "application/json", protocols = "http/https(권장)")
public class InsightController {

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

    @Resource
    ApiMainService apiMainService;

    @Resource
    MessageService messageService;

    @Resource
    AdminService adminService;

    @Resource
    BatchService batchService;

    @Resource
    ExcelView excelView;

    @Autowired
    FileUtil fileUtil;


    private static final Logger logger = LoggerFactory.getLogger(InsightController.class);


    public UserVO session() {

//        UserVO userVO = userService.loadUserByUsername("gkstmvdl");
//        UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        /* UserVO userVO = userService.loadUserByUsername("gkstmvdl");*/
        UserVO userVO = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (userVO == null) {
            redirect();
        } else {
            return userVO;
        }

        return null;
    }

    @RequestMapping(value = "/run", method = RequestMethod.GET)
    public @ResponseBody
    void run() throws Exception {
        batchService.run();
    }


    public String redirect() {
        return "redirect:/login";
    }

    @RequestMapping(value = "/sessionUser", method = RequestMethod.GET)
    public @ResponseBody
    String sessionUser() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(session());
    }


    @RequestMapping(value = "/batch/getTableList", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getTableList() {
        Map<String, Object> map = new HashMap<>();

        map.put("table", batchService.getTableList());

        return map;
    }

    /*@RequestMapping(value = "/batch/insertGAData",method =RequestMethod.POST)
    public @ResponseBody void insertGAData(Map<String, Object> table, LinkedHashMap<String,Object> gaData ) throws Exception {

        batchService.insertGAData(table, gaData);
    }*/

    @RequestMapping(value = "/bi/userInfo", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> biUserInfo() {
        Map<String, Object> map = new HashMap<>();

        map.put("user", session());
        map.put("service", userService.getBiServiceList(session()));

        return map;
    }


    @RequestMapping(value = "/bi/session", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> biSession() {

        Map<String, Object> map = new HashMap<String, Object>();

        List<ServiceBIVO> list = userService.getBiServiceList(session());

        map.put("list", list);
        map.put("session", session());
        return map;
    }

    @RequestMapping(value = "/bi/getGAUserInfoList", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> getUserByUserId() {

        Map<String, Object> map = new HashMap<>();

        List<Map<String, Object>> list = batchService.getGAUserInfoList(session());

        map.put("list", list);

        return map;
    }


//    @RequestMapping(value = "/patchcall/manage/{pageNo}", method = RequestMethod.POST)
//    public @ResponseBody
//    Map<String, Object> PatchcallSearchList(@RequestBody Map<String, Object> search, @PathVariable("pageNo") Integer pageNo) {
//
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        AgentVO agentVO = new AgentVO();
//        agentVO.setUser_id(session().getUserid());
//        agentVO.setPresent_page(pageNo);
//
//        if (search.get("search_cate") != null) {
//            switch ((String) search.get("search_cate")) {
//                case "name":
//                    agentVO.setName((String) search.get("search_text"));
//                    break;
//                case "dong_name":
//                    agentVO.setDong_name((String) search.get("search_text"));
//                    break;
//                case "vno":
//                    agentVO.setVno((String) search.get("search_text"));
//                    break;
//                case "rcv_no":
//                    agentVO.setRcv_no((String) search.get("search_text"));
//                    break;
//            }
//        }
//
//        List<AgentVO> patchcallList = supplyService.getAgentVOListForService(agentVO);
//
//
//        map.put("list", patchcallList);
//        map.put("agentVO", agentVO);
//
//        return map;
//
//    }

    @RequestMapping(value = "/patchcall/management", method = RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> PatchcallSearchList(@RequestParam(value="page", defaultValue="1") Integer page, @RequestParam(value="search", required = false) String search, @RequestParam(value="search_filed",required = false) String searchFiled ,Authentication  authentication) {

        UserPrincipal user = (UserPrincipal)authentication.getPrincipal();

        UserVO userVO = userService.loadUserByUsername(user.getUserId());

//        Map<String, Object> map = new HashMap<String, Object>();

        AgentVO agentVO = new AgentVO();
        agentVO.setUser_id(userVO.getUserid());
        agentVO.setPageNo(page);


        if(search != null && searchFiled != null){
            agentVO.setSearchFiled(searchFiled);
            agentVO.setSearchValue(search);
        }

        Map<String, Object> map = supplyService.getAgentVOListForService(agentVO);

        return new ResponseEntity<>(map,HttpStatus.OK);
    }

    @RequestMapping(value = "/patchcall/manage", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> NoPaginationPatchcallSearchList(@RequestBody Map<String, Object> search) {

        Map<String, Object> map = new HashMap<String, Object>();

        AgentVO agentVO = new AgentVO();
        agentVO.setUser_id(session().getUserid());

        if (search.get("search_cate") != null) {
            switch ((String) search.get("search_cate")) {
                case "name":
                    agentVO.setName((String) search.get("name"));
                    break;
                case "dong_name":
                    agentVO.setDong_name((String) search.get("search_text"));
                    break;
                case "vno":
                    agentVO.setVno((String) search.get("search_text"));
                    break;
                case "rcv_no":
                    agentVO.setRcv_no((String) search.get("search_text"));
                    break;
            }
        }

        List<String> patchcallList = supplyService.getAgentVOListAll(agentVO);


        map.put("list", patchcallList);
        map.put("count", patchcallList.size());

        return map;

    }

//    @RequestMapping(value = "/patchcall/calllog/{pageNo}", method = RequestMethod.POST)
//    public @ResponseBody
//    Map<String, Object> CallLogSearchList(@RequestBody Map<String, Object> search, @PathVariable("pageNo") Integer pageNo) {
//
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        CallLogVO callLogVO = new CallLogVO();
//
//        callLogVO.setUser_id(session().getUserid());
//
//        callLogVO.setPresent_page(pageNo);
//
//        if (search.get("search_cate") != null) {
//            switch ((String) search.get("search_cate")) {
//                case "agent_name":
//                    callLogVO.setAgent_name((String) search.get("search_text"));
//                    break;
//                case "dong_name":
//                    callLogVO.setDong_name((String) search.get("search_text"));
//                    break;
//                case "ani":
//                    callLogVO.setAni((String) search.get("search_text"));
//                    break;
//                case "dn":
//                    callLogVO.setDn((String) search.get("search_text"));
//                    break;
//                case "called_no":
//                    callLogVO.setCalled_no((String) search.get("search_text"));
//                    break;
//            }
//        }
//
//        if (search.get("search_period_from") != null) {
//            callLogVO.setSearch_period_from((String) search.get("search_period_from"));
//            callLogVO.setSearch_period_to((String) search.get("search_period_to"));
//        }
//
//
//        List<CallLogVO> callLogList = supplyService.getCallLogList(callLogVO);
//
//        map.put("list", callLogList);
//        map.put("callLogVO", callLogVO);
//
//
//        return map;
//
//    }

    @RequestMapping(value = "/patchcall/calllog", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> noPaginationCallLogSearchList(@RequestBody Map<String, Object> search) {

        Map<String, Object> map = new HashMap<String, Object>();

        CallLogVO callLogVO = new CallLogVO();

        callLogVO.setUser_id(session().getUserid());

        if (search.get("search_cate") != null) {
            switch ((String) search.get("search_cate")) {
                case "agent_name":
                    callLogVO.setAgent_name((String) search.get("search_text"));
                    break;
                case "dong_name":
                    callLogVO.setDong_name((String) search.get("search_text"));
                    break;
                case "ani":
                    callLogVO.setAni((String) search.get("search_text"));
                    break;
                case "dn":
                    callLogVO.setDn((String) search.get("search_text"));
                    break;
                case "called_no":
                    callLogVO.setCalled_no((String) search.get("search_text"));
                    break;
            }
        }

        if (search.get("search_period_from") != null) {
            callLogVO.setSearch_period_from((String) search.get("search_period_from"));
            callLogVO.setSearch_period_to((String) search.get("search_period_to"));
        }


        List<String> callLogList = supplyService.getCallLogListAll(callLogVO);

        map.put("list", callLogList);
        map.put("count", callLogList.size());
        return map;

    }

    @RequestMapping(value="/patchcall/calllog/insertMemo",method=RequestMethod.POST)
    public @ResponseBody boolean  insertMemo(@RequestBody Map<String,Object> memoInfo){

        String userId = session().getUserid();

        Map<String,Object> map = memoInfo;

        map.put("user_id",userId);

        int resultNum = supplyService.insertMemo(map);

        boolean result = false;

        if(resultNum == 1){
            result = true;
        }else{
            result = false;
        }

        return result;

    }

    @RequestMapping(value="/patchcall/calllog/updateMemo",method=RequestMethod.PUT)
    public @ResponseBody boolean updateMemo(@RequestBody Map<String,Object> memoInfo){

        String userId = session().getUserid();

        Map<String,Object> map = memoInfo;

        map.put("user_id",userId);

        int resultNum = supplyService.updateMemo(map);

        boolean result = false;

        if(resultNum == 1){
            result = true;
        }else{
            result = false;
        }

        return result;

    }

    @RequestMapping(value="/patchcall/calllog/deleteMemo/{memo_index}",method=RequestMethod.DELETE)
    public @ResponseBody boolean deleteMemo(@PathVariable("memo_index") Integer memo_index){

        String userId = session().getUserid();

        Map<String,Object> map = new HashMap<String, Object>();

        map.put("user_id",userId);
        map.put("idx",memo_index);
//
//        System.out.println("####");
//        System.out.println(memoInfo);
//        System.out.println("####");

        int resultNum = supplyService.deleteMemo(map);

        boolean result = false;

        if(resultNum == 1){
            result = true;
        }else{
            result = false;
        }

        return result;

    }

    @RequestMapping(value="/patchcall/calllog/getMemo/{log_index}/{month}",method=RequestMethod.GET)
    public @ResponseBody Map<String, Object> getMemoList(@PathVariable("log_index") Integer logIndex, @PathVariable("month") String month){
        String userId = session().getUserid();

        Map<String,Object> map = new HashMap<String, Object>();


        map.put("user_id",userId);
        map.put("log_index",logIndex);
        map.put("month",month);

        return supplyService.getMemo(map);

    }


//    @RequestMapping(value = "/patchcall/cid/{pageNo}", method = RequestMethod.GET)
//    public @ResponseBody
//    Map<String, Object> CallbackList(@PathVariable("pageNo") Integer pageNo) {
//
//        Map<String, Object> map = new HashMap<String, Object>();
//
//        AcsTransmitVO acsTransmitVO = new AcsTransmitVO();
//        acsTransmitVO.setUserid(session().getUserid());
//        acsTransmitVO.setPresent_page(pageNo);
//
//        List<AcsTransmitVO> messagingList = acsService.getMessaingListByAcsVO(acsTransmitVO);
//
//        map.put("list", messagingList);
//        map.put("acsTransmitVO", acsTransmitVO);
//
//        return map;
//
//    }

    @RequestMapping(value = "/patchcall/coloring/{pageNo}", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> ColoringList(@PathVariable("pageNo") Integer pageNo) {

        Map<String, Object> map = new HashMap<String, Object>();

        ColoringUploadVO uploadVO = new ColoringUploadVO();
        uploadVO.setUserid(session().getUserid());
        uploadVO.setPresent_page(pageNo);

        List<ColoringUploadVO> uploadList = supplyService.getUploadList(uploadVO);

        map.put("list", uploadList);
        map.put("coloringVO", uploadVO);

        return map;

    }

    @RequestMapping(value = "/patchcall/rcvment/{pageNo}", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> rcvmentList(@PathVariable("pageNo") Integer pageNo) {


        //컬러링으로 되어있음!! 변경해야 함
        Map<String, Object> map = new HashMap<String, Object>();

        ColoringUploadVO rcvmentVO = new ColoringUploadVO();
        rcvmentVO.setUserid(session().getUserid());
        rcvmentVO.setPresent_page(pageNo);

        List<ColoringUploadVO> uploadList = supplyService.getRcvmentList(rcvmentVO);

        map.put("list", uploadList);
        map.put("rcvmentVO", rcvmentVO);

        return map;

    }


    @RequestMapping(value = "/patchcall/update/{vno}", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> update050View1(@PathVariable("vno") String vno) {
        Map<String, Object> map = new HashMap<String, Object>();
        String userid = session().getUserid();
        CallbackSmsVO callbackCheck = userService.checkCallbackSmsService(userid);

        AgentAddressVO agentAddress = supplyService.getAgentAddress(vno);

        AgentVO agentVO = new AgentVO();
        agentVO.setVno(vno);
        agentVO.setUser_id(userid);
        agentVO = supplyService.getAgentVO(agentVO);

        map.put("agentVO",agentVO);

        CommonVO commonVO = new CommonVO();
        commonVO.setCategory("050_NUMBER_ASSIGNED");
        Map<String, List<CommonVO>> numberOption = supplyService.get050_1th2ndNumList(commonVO);

        map.put("numberOption",numberOption);

        ColoringUploadVO param = new ColoringUploadVO();
        param.setUserid(userid);
        List<AcsTransmitVO> messagingList = acsService.getMessaingListByUserid(userid);

        CallbackSmsVO callback = new CallbackSmsVO();
        callback.setUserid(userid);

        if(agentVO.getSms_yn().equals("M")){
            String fileName = agentVO.getMms_file().substring(22);
            String onlyName = fileName.substring(0,36);

            FileVO file = fileService.getFileVO(onlyName);

            String agentVO_mms_file = file.getName();

            map.put("agentVO_mms_file",agentVO_mms_file);
        }

        map.put("callbackCheck", callbackCheck);
        map.put("messagingList", messagingList);
        map.put("agentAddress", agentAddress);

        return map;

    }

    @RequestMapping(value = "/patchcall/mmsUpload", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, String> mmsUpload(List<MultipartFile> file, HttpServletRequest request) throws Exception {

        List<FileVO> fileVOList = fileService.processUpload(file, "MMS", 0, request);

        Map<String, String> map = new HashMap<>();

        for (FileVO file_1 : fileVOList) {
            map.put("route", "/usr/local/upload/mms/" + file_1.getUpload_name() + "." + file_1.getExtenstion());
            return map;
        }
        return null;
    }

    @RequestMapping(value = "/patchcall/manageUpload", method = RequestMethod.POST)
    public @ResponseBody
    void manageUpload(List<MultipartFile> file, HttpServletRequest request) throws Exception {

        FileInputStream fis = new FileInputStream(fileUtil.convert(file.get(0)));
        XSSFWorkbook workbook = new XSSFWorkbook(fis);

        int rowindex = 0;
        int columnindex = 0;

        XSSFSheet sheet = workbook.getSheetAt(0);

        int rows = sheet.getPhysicalNumberOfRows();

        for (rowindex = 3; rowindex < rows; rowindex++) {
            XSSFRow row = sheet.getRow(rowindex);
            String vno = "";
            if (row != null) {
                AgentVO agent = new AgentVO();
                int cells = row.getLastCellNum();

                for (columnindex = 0; columnindex <= cells; columnindex++) {
                    XSSFCell cell = row.getCell(columnindex);
                    if (cell == null) {
                        continue;
                    } else {
                        switch (columnindex) {
                            case 0:
                                vno = cell.getStringCellValue();
                                agent.setVno(vno);
                                break;
                            case 1:
                                agent.setRcv_no(cell.getStringCellValue());
                                break;
                            case 2:
                                agent.setName(cell.getStringCellValue());
                                break;
                            case 3:
                                agent.setDong_name(cell.getStringCellValue());
                                break;
                            case 4:
                                agent.setDong_yn(cell.getStringCellValue());
                                break;
                            case 5:
                                agent.setSms(cell.getStringCellValue());
                            case 6:
                                agent.setSms_yn(cell.getStringCellValue());
                                break;
                            case 7:
                                agent.setOut_sms(cell.getStringCellValue());
                                break;
                            case 8:
                                agent.setOut_sms_yn(cell.getStringCellValue());
                                break;
                            case 11:
                                agent.setUser_id(cell.getStringCellValue());
                            default:
                                continue;
                        }

                    }
                }

                AgentVO getIdx = supplyService.getAgentVO(agent);
                agent.setIdx(getIdx.getIdx());

                supplyService.update050Agent(agent);
            }
        }


    }

    @RequestMapping(value = "/patchcall/edit/{vno}", method = RequestMethod.POST)
    public @ResponseBody
    void update050Submit(HttpServletRequest request, @RequestBody Map<String, Object> map2, /*@RequestParam List<MultipartFile> file, */  @PathVariable("vno") String vno) throws Exception {

        long startTime = System.currentTimeMillis();
        Map<String,String> map = new HashMap<>();
        ObjectMapper oMapper = new ObjectMapper();

        map.put("type", "번호 수정");

        Map<String, Object> agentMap = oMapper.convertValue(((Map<String,Object>)map2.get("manage")).get("agentVO"), Map.class);
        AgentAddressVO agentAddress = oMapper.convertValue(((Map<String,Object>)map2.get("manage")).get("agentAddressVO"), AgentAddressVO.class);
        System.out.println(agentAddress);

        AgentVO agent = new AgentVO();
        agent.setVno(vno);
        agent.setUser_id(session().getUserid());
        agent = supplyService.getAgentVO(agent);

        AgentVO agentVO = new AgentVO();
        agentVO.setIdx(agent.getIdx());
        agentVO.setVno((String)agentMap.get("vno"));
        agentVO.setUser_id(session().getUserid());
        agentVO.setRcv_no((String)agentMap.get("rcvNo"));
        agentVO.setName((String)agentMap.get("name"));
        agentVO.setDong_name((String)agentMap.get("dongName"));
        agentVO.setDong_yn((String)agentMap.get("dongYn"));


        agentVO.setSms((String)agentMap.get("sms"));
        agentVO.setOut_sms((String)agentMap.get("out_sms"));
        agentVO.setSms_yn((String)agentMap.get("smsYn"));
        agentVO.setOut_sms_yn((String)agentMap.get("outSmsYn"));
        agentVO.setCallback_no((String)agentMap.get("callback_no"));
        agentVO.setOut_sms_phone((String)agentMap.get("out_sms_phone"));
        agentVO.setMms_file((String)agentMap.get("mmsFile"));



        Integer success = supplyService.update050Agent(agentVO);
        supplyService.updateAgentAddress(agentAddress);

        long endTime = System.currentTimeMillis();
        logger.warn("번호 수정까지 걸린 시간  = " +( endTime - startTime )/1000.0f +"초");

        map.put("time", ( endTime - startTime )/1000.0f +"초");
        mainDao.insertTimeMeasure(map);

        Map<String,Integer> enablePatchcall = adminDao.getEnablePatchCallSKB();
        supplyDao.skbUseNumber(enablePatchcall);


    }


    @RequestMapping(value = "/webhosting/manage/{pageNo}", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> WebhostingList(@PathVariable("pageNo") Integer pageNo) {

        Map<String, Object> map = new HashMap<String, Object>();
        ServiceWebHostingVO webHostingVO = new ServiceWebHostingVO();
        webHostingVO.setUserid(session().getUserid());
        webHostingVO.setPresent_page(pageNo);


        List<ServiceWebHostingVO> webHostingVOList = userService.getHostingServiceListByVO(webHostingVO);


        map.put("list", webHostingVOList);
        map.put("webhostingVO", webHostingVO);
        return map;

    }

    @RequestMapping(value = "/vpn/list/{pageNo}", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> VpnList(@PathVariable("pageNo") Integer pageNo) {

        Map<String, Object> map = new HashMap<String, Object>();

        ServiceVpnHostingVO vpnHostingVO = new ServiceVpnHostingVO();
        vpnHostingVO.setUserid(session().getUserid());
        vpnHostingVO.setPresent_page(pageNo);

        List<ServiceVpnHostingVO> vpnList = userService.getVpnListByVO(vpnHostingVO);


        map.put("list", vpnList);
        map.put("vpnhostingVO", vpnHostingVO);

        return map;

    }


    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> ServiceCount() {
        ServiceListVO serviceListVO = new ServiceListVO();
        serviceListVO.setUserid(session().getUserid());

        Map<String, Object> map = new HashMap<String, Object>();


        map.put("patchcall", userService.getPatchcallServiceList(session().getUserid()));
        map.put("patchcallbi", userService.getBiServiceList(session()));
        map.put("webhosting", userService.getHostingServiceList(session().getUserid()));
        map.put("vpnhosting", userService.getVpnList(session().getUserid()));
        map.put("messaging", userService.selectMessageVO(session().getUserid()));
        map.put("service_count", supplyService.getUserServiceList(serviceListVO).size());
        map.put("session", session());

        return map;

    }

    @RequestMapping(value = "/messaging/token", method = RequestMethod.GET)
    public @ResponseBody
    Map<String, Object> messageTokenList() {

        Map<String, Object> map = new HashMap<String, Object>();

        ApiTokenVo apiTokenVo = new ApiTokenVo();
        apiTokenVo.setToken_userid(session().getUserid());

        List<ApiTokenVo> messageList = apiMainService.getMessageTokenInfoListByVO(apiTokenVo);

        map.put("messageList", messageList);

        return map;
    }

    @RequestMapping(value = "/messaging/result", method = RequestMethod.POST)
    public @ResponseBody
    Map<String, Object> messageResultList(@RequestBody Map<String, Object> data) {

        Map<String, Object> map = new HashMap<String, Object>();

        Map<String, String> msgMap = new HashMap<String, String>();
        AcsTransmitVO acsTransmitVO = new AcsTransmitVO();
        acsTransmitVO.setUserid(session().getUserid());

        msgMap.put("callback", (String) data.get("callback"));
        msgMap.put("yyyymm", (String) data.get("date_year") + data.get("date_month"));
        List<Map<String, String>> resultList = messageService.getMessageList(msgMap);

        map.put("yyyy", (String) data.get("date_year"));
        map.put("mm", (String) data.get("date_month"));
        map.put("resultList", resultList);

        return map;


    }

    @RequestMapping(value = "/messaging/send", method = RequestMethod.POST)
    public @ResponseBody
    void send(@RequestBody Map<String, Object> data) {

        Gson gson = new Gson();
        String json = gson.toJson(data.get("sendList"));


        JsonParser parser = new JsonParser();

        JsonElement element = parser.parse(json);

        JsonArray name = element.getAsJsonArray();


        String callback = data.get("callback").toString(); // 발신자 번호
        String subject = data.get("subject").toString(); // 제목

        List<Object> msg_type = new ArrayList<Object>();
        msg_type = (List<Object>) data.get("msgType");


        /*System.out.println("msg_type[size] :: ]" + msg_type.size());
        System.out.println("msg_type[0]" + msg_type.get(0).toString());
        System.out.println("msg_type[1]" + msg_type.get(1));*/

        String request_time = "";
        if (data.get("ReserveTime").toString() != "") {
            request_time = data.get("ReserveTime").toString();
        } else {
            request_time = "";
        }


        for (int i = 0; i <= name.size() - 1; i++) {
            JsonObject excelObject = (JsonObject) name.get(i).getAsJsonObject();
            String messageTemplate = data.get("messageTemplate").toString();
            messageTemplate = messageTemplate.replace("[@이름]", excelObject.get("column_01").toString().replaceAll("\"", ""));
            messageTemplate = messageTemplate.replace("[@1]", excelObject.get("column_03").toString()).replaceAll("\"", "");
            messageTemplate = messageTemplate.replace("[@2]", excelObject.get("column_04").toString()).replaceAll("\"", "");
            messageTemplate = messageTemplate.replace("[@3]", excelObject.get("column_05").toString()).replaceAll("\"", "");
            messageTemplate = messageTemplate.replace("[@4]", excelObject.get("column_06").toString()).replaceAll("\"", "");
            messageTemplate = messageTemplate.replace("[@5]", excelObject.get("column_07").toString()).replaceAll("\"", "");
            Map<String, String> map = new HashMap<>();

            int msgSize = Integer.parseInt(msg_type.get(i).toString());
            if (msgSize <= 90 && msgSize >= 0) {
                System.out.println("msg_type :: 1");
                map.put("msg_type", "1");

            } else if (msgSize >= 91 && msgSize <= 2000) {
                System.out.println("msg_type :: 3");
                map.put("msg_type", "3");

            }
//             메세지 전송 타입 계산
            map.put("dstaddr", excelObject.get("column_02").toString().replaceAll("\"", ""));
            map.put("callback", callback); //발신자 번호
            map.put("subject", subject);
            map.put("text", messageTemplate);
            map.put("text2", messageTemplate);

            map.put("request_time", request_time);
            map.put("k_template_code", "SJT_020958");
            map.put("k_button_type", "2");
            map.put("k_button_name", "홈페이지 연결하기");
            map.put("k_button_url", "https://www.eney.co.kr");
            map.put("k_button_url2", "https://www.eney.co.kr");
            map.put("k_button2_type", null);
            map.put("k_button2_name", null);
            map.put("k_button2_url", null);
            map.put("k_button2_url2", null);
            map.put("k_button3_type", null);
            map.put("k_button3_name", null);
            map.put("k_button3_url", null);
            map.put("k_button3_url2", null);
            map.put("k_button4_type", null);
            map.put("k_button4_name", null);
            map.put("k_button4_url", null);
            map.put("k_button4_url2", null);
            map.put("k_button5_type", null);
            map.put("k_button5_name", null);
            map.put("k_button5_url", null);
            map.put("k_button5_url2", null);
            map.put("k_next_type", "7");
            map.put("filecnt", "0");
            map.put("fileloc1", null);
            map.put("fileloc2", null);
            map.put("fileloc3", null);
            map.put("fileloc4", null);
            map.put("fileloc5", null);
            map.put("sender_key", null);
            MessageVO messageVO = new MessageVO();

            messageVO.setMsg_type(map.get("msg_type"));
            messageVO.setDstaddr(map.get("dstaddr"));
            messageVO.setCallback(map.get("callback"));
            messageVO.setSubject(map.get("subject"));
            messageVO.setText(map.get("text"));
            messageVO.setText2(map.get("text"));
            messageVO.setRequest_time(map.get("request_time"));
            messageVO.setK_template_code(map.get("k_template_code"));

            messageVO.setK_button_type(map.get("k_button_type"));
            messageVO.setK_button_name(map.get("k_button_name"));
            messageVO.setK_button_url(map.get("k_button_url"));
            messageVO.setK_button_url2(map.get("k_button_url2"));

            messageVO.setK_button_type(map.get("k_button2_type"));
            messageVO.setK_button_name(map.get("k_button2_name"));
            messageVO.setK_button_url(map.get("k_button2_url"));
            messageVO.setK_button_url2(map.get("k_button2_url2"));

            messageVO.setK_button_type(map.get("k_button3_type"));
            messageVO.setK_button_name(map.get("k_button3_name"));
            messageVO.setK_button_url(map.get("k_button3_url"));
            messageVO.setK_button_url2(map.get("k_button3_url2"));

            messageVO.setK_button_type(map.get("k_button4_type"));
            messageVO.setK_button_name(map.get("k_button4_name"));
            messageVO.setK_button_url(map.get("k_button4_url"));
            messageVO.setK_button_url2(map.get("k_button4_url2"));

            messageVO.setK_button_type(map.get("k_button5_type"));
            messageVO.setK_button_name(map.get("k_button5_name"));
            messageVO.setK_button_url(map.get("k_button5_url"));
            messageVO.setK_button_url2(map.get("k_button5_url2"));

            messageVO.setK_img_link_url(map.get("k_img_link_url"));
            messageVO.setK_next_type(map.get("k_next_type"));

            Integer count = 0;
            if (map.get("fileloc1") != null) {
                count++;
            }
            if (map.get("fileloc2") != null) {
                count++;
            }
            if (map.get("fileloc3") != null) {
                count++;
            }
            if (map.get("fileloc4") != null) {
                count++;
            }
            if (map.get("fileloc5") != null) {
                count++;
            }

            messageVO.setFilecnt(String.valueOf(count));
            messageVO.setFileloc1(map.get("fileloc1"));
            messageVO.setFileloc1(map.get("fileloc2"));
            messageVO.setFileloc1(map.get("fileloc3"));
            messageVO.setFileloc1(map.get("fileloc4"));
            messageVO.setFileloc1(map.get("fileloc5"));
            messageVO.setSender_key(map.get("sender_key"));

            messageService.sendSmsEpontCharge(messageVO);
        }
        /*System.out.println(name.size());
        System.out.println(name.iterator());*/



/*        String json = "{\"name\":\"kim\",\"age\":20,\"gender\":\"M\"}";
        JsonParser parser = new JsonParser();
        JsonElement element = parser.parse(json);
        String name = element.getAsJsonObject().get("name").getAsString();
        System.out.println("name = "+name);
        int age = element.getAsJsonObject().get("age").getAsInt();
        System.out.println("age = "+age);*/


    }


    @RequestMapping(value = "/messaging/excelUpload", method = RequestMethod.POST)
    public @ResponseBody
    String excelUpload(List<MultipartFile> file, HttpServletResponse response) {

        try {
            FileInputStream fis = new FileInputStream(fileUtil.convert(file.get(0)));
            XSSFWorkbook workbook = new XSSFWorkbook(fis);
            int rowindex = 0;
            int columnindex = 0;

            XSSFSheet sheet = workbook.getSheetAt(0);

            int rows = sheet.getPhysicalNumberOfRows();

            JsonArray array = new JsonArray(); //[]


            for (rowindex = 2; rowindex < rows; rowindex++) {
                XSSFRow row = sheet.getRow(rowindex);
                String vno = "";
                if (row != null) {
                    AgentVO agent = new AgentVO();
                    int cells = row.getLastCellNum();

                    JsonObject object = new JsonObject(); // 실 데이터 오브젝트 생성
                    for (columnindex = 0; columnindex <= cells; columnindex++) {


                        XSSFCell cell = row.getCell(columnindex);
                        if (cell == null) {
                            continue;
                        } else {

                            switch (columnindex) {
                                case 0:
//                                System.out.println("고객명 :: " + cell.getStringCellValue());
                                    object.addProperty("column_01", cell.getStringCellValue());
                                    break;
                                case 1:
//                                System.out.println("수신자번호 :: " + cell.getStringCellValue());
                                    object.addProperty("column_02", cell.getStringCellValue());
                                    break;
                                case 2:
//                                System.out.println("내용1 :: " + cell.getStringCellValue());
                                    object.addProperty("column_03", cell.getStringCellValue());
                                    break;
                                case 3:
//                                System.out.println("내용2 :: " + cell.getStringCellValue());
                                    object.addProperty("column_04", cell.getStringCellValue());

                                    break;
                                case 4:
//                                System.out.println("내용3 :: " + cell.getStringCellValue());
                                    object.addProperty("column_05", cell.getStringCellValue());

                                    break;
                                case 5:
//                                System.out.println("내용4 :: " + cell.getStringCellValue());
                                    object.addProperty("column_06", cell.getStringCellValue());

                                case 6:
//                                System.out.println("내용5 :: " + cell.getStringCellValue());
                                    object.addProperty("column_07", cell.getStringCellValue());

                                    break;
                                default:

                            }
//                        System.out.println("array :: "+array);
                        }
                    }
                    System.out.println("object ::: " + object);
                    array.add(object);


                }

            }
            /*JsonObject info = new JsonObject(); //{}
            info.add("property", array);
            String returnJSON = info.toString();*/
            String returnJSON = array.toString();

            System.out.println("JSON :: " + returnJSON);
            fis.close();
            return returnJSON;

        } catch (Exception e) {
            System.out.println("형식에 많이 어긋남" + e.getMessage());
            e.printStackTrace();
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            /*
             * 예외 발생 시 HTTP CODE 400 전송*/
        } finally {
            System.out.println("메세지 업로드 끝");
        }


        return null;
    }


    @RequestMapping(value = "/token/list", method = RequestMethod.GET)
    public ResponseEntity<?> getTokenList(@RequestParam(value="page", defaultValue="1") Integer page, Authentication authentication){

        Map<String, Object> map = new HashMap<String, Object>();
        ApiTokenVo apiTokenVo = new ApiTokenVo();
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

        apiTokenVo.setPageNo(page);
        apiTokenVo.setToken_userid(user.getUserId());

        map = apiMainService.selectMessageTokenInfoList(apiTokenVo);

        return new ResponseEntity<>(map, HttpStatus.OK);

    }

    @RequestMapping(value = "/token", method = RequestMethod.POST)
    public ResponseEntity<?> genToken(@RequestBody ApiTokenVo apiTokenVo, Authentication authentication){

        System.out.println("#####");
        System.out.println(apiTokenVo);
        System.out.println("#####");

        Map<String, Object> map = new HashMap<String, Object>();
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

        UserVO userVO = new UserVO();
        userVO.setUserid(user.getUserId());

        ApiTokenVo tokenInfo = null;

        switch (apiTokenVo.getService()){
            case "PATCHCALL":
                tokenInfo = apiMainService.generateTokenWithIp(apiTokenVo,userVO);
                break;
            case "MESSAGE":
                tokenInfo = apiMainService.generateMessageTokenWithIp(apiTokenVo,userVO);
                break;
        }

        if(tokenInfo != null){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value = "/token", method = RequestMethod.GET)
    public @ResponseBody
    String genToken() {
        UserVO userInfo = session();
        ApiTokenVo tokenInfo = apiMainService.generateToken(userInfo);

        return tokenInfo.getToken_key();
    }


    @RequestMapping(value = "/messageToken", method = RequestMethod.GET)
    public @ResponseBody
    String genMessageToken() {
        UserVO userInfo = session();
        ApiTokenVo tokenInfo = apiMainService.generateMessageToken(userInfo);

        return tokenInfo.getToken_key();
    }

    @RequestMapping(value = "/admin/userList", method = RequestMethod.GET)
    public @ResponseBody
    String getBIUserListByAdmin() throws JsonProcessingException {
        return new ObjectMapper().writeValueAsString(adminService.getBIUserListByAdmin());

    }
}
