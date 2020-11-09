package eney.web;


import eney.domain.*;
import eney.mapper.AdminDao;
import eney.mapper.MainDao;
import eney.mapper.SupplyDao;
import eney.service.*;
import eney.util.RandomString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
@PreAuthorize("isAuthenticated()")
@RequestMapping(value = "/service/message")
public class MsgController {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceController.class);

    @Resource
    UserService userService;

    @Resource
    FileService fileService;

    @Resource
    SupplyService supplyService;

    @Resource
    MessageService messageService;

    @Resource
    CustomerService customerService;

    @Resource
    AcsService acsService;

    @Resource
    MainDao mainDao;

    @Resource
    AdminDao adminDao;

    @Resource
    SupplyDao supplyDao;

    @RequestMapping(value = "/template", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getMessageTemplateList(@RequestParam(value = "page", defaultValue = "1") Integer page, Authentication authentication) {

        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        System.out.println(user.toString());
        System.out.println(user.getUserId());
        System.out.println("in template get");
        System.out.println(page);
        UserVO userVO = userService.loadUserByUsername(user.getUserId());

        System.out.println(userVO.toString());

        MessageTemplateVO messageTemplateVO = new MessageTemplateVO();
        messageTemplateVO.setUserid(userVO.getUserid());
        messageTemplateVO.setPageNo(page);

        Map<String, Object> map = messageService.getMessageTemplateList(messageTemplateVO);
        System.out.println("map :: " + map);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }


    @RequestMapping(value = "/templateDetail", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getMessageTemplateDetail(@RequestParam(value = "idx") int idx, Authentication authentication) {
        System.out.println("getMessageTemplateDetail");
        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

        MessageTemplateVO messageTemplateVO = new MessageTemplateVO();


        messageTemplateVO = messageService.getMessageTemplateDetail(idx);

        return new ResponseEntity<>(messageTemplateVO, HttpStatus.OK);
    }

    @RequestMapping(value = "/template/delete", method = RequestMethod.POST)
    public @ResponseBody
    ResponseEntity<?> deleteMessageTemplate(@RequestParam(value = "idx") int idx, Authentication authentication) {

        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();


        int deleteRows = messageService.deleteMessageTemplate(idx);

        System.out.println(deleteRows);

        return new ResponseEntity<>(deleteRows, HttpStatus.OK);
    }


    //    msg_type, subject, text
    @RequestMapping(value = "/template", method = RequestMethod.POST)
    public ResponseEntity<?> insertMessageTemplateList(@RequestParam(value = "subject") String subject,
                                                       @RequestParam(value = "text") String text, @RequestParam(value = "msg_type") String msg_type, Authentication authentication) {

        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        System.out.println("in template post");
        UserVO userVO = userService.loadUserByUsername(user.getUserId());

        MessageTemplateVO msgTemplateVO = new MessageTemplateVO();
        msgTemplateVO.setUserid(userVO.getUserid());
        msgTemplateVO.setMsg_type(msg_type);
        msgTemplateVO.setSubject(subject);
        msgTemplateVO.setText(text);
        System.out.println(msgTemplateVO.toString());
        String errMsg = messageService.insertMessageTemplateList(msgTemplateVO);

//        Map<String, Object> map = messageService.getMessageTemplateList(messageTemplateVO);
//        System.out.println("map :: " + map);


        if (errMsg != null) {
            return new ResponseEntity<>(errMsg, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(value = "/template", method = RequestMethod.PUT)
    public @ResponseBody
    ResponseEntity<?> updateMessageTemplateList(@RequestParam(value = "idx") int idx, @RequestParam(value = "subject") String subject,
                                                @RequestParam(value = "text") String text, Authentication authentication) {

        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        System.out.println("in template put");
        UserVO userVO = userService.loadUserByUsername(user.getUserId());

        MessageTemplateVO msgTemplateVO = new MessageTemplateVO();
        msgTemplateVO.setUserid(userVO.getUserid());
        msgTemplateVO.setIdx(idx);
        msgTemplateVO.setSubject(subject);
        msgTemplateVO.setText(text);
        String errMsg = messageService.updateMessageTemplateList(msgTemplateVO);

        if (errMsg != null) {
            return new ResponseEntity<>(errMsg, HttpStatus.BAD_REQUEST);
        } else {
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }


    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getMessageResultList(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "search", required = false) String search,
                                           @RequestParam(value = "startYear", required = false) String startYear, @RequestParam(value = "startMonth", required = false) String startMonth, @RequestParam(value = "startDay", required = false) String startDay,
                                           @RequestParam(value = "endYear", required = false) String endYear, @RequestParam(value = "endMonth", required = false) String endMonth, @RequestParam(value = "endDay", required = false) String endDay,
                                           Authentication authentication) {

        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        System.out.println(user.toString());
        System.out.println(user.getUserId());
        System.out.println("in messge result get");
        UserVO userVO = userService.loadUserByUsername(user.getUserId());


        System.out.println(userVO.toString());
        Map<String, Object> searchMap = new HashMap<>();

        StringBuffer date = new StringBuffer();


        /*date.append(year);
        date.append(month.length() < 2 ? "0" + month : month);


        searchMap.put("date", date.toString());*/


        searchMap.put("userid", user.getUserId()); // 검색조건 userid

        // start, end date 추가

        int convEndYear = (int) Integer.parseInt(endYear);
        int convEndMonth = (int) Integer.parseInt(endMonth);
        int convEndDay = (int) Integer.parseInt(endDay);

        int convStartYear = (int) Integer.parseInt(startYear);
        int convStartMonth = (int) Integer.parseInt(startMonth);
        int convStartDay = (int) Integer.parseInt(startDay);

        LocalDate sDate = LocalDate.of(convStartYear, convStartMonth, convStartDay);
        LocalDate eDate = LocalDate.of(convEndYear, convEndMonth, convEndDay);

        Period period = Period.between(sDate, eDate);
        System.out.println("차이 :: " + period.getMonths());
        System.out.println(sDate);
        System.out.println(eDate);
        searchMap.put("startDate", sDate.toString());
        searchMap.put("endDate", eDate.toString());

        for (int i = 0; i <= period.getMonths(); i++) {
//            searchMap.put("month01", eDate);
            String month = eDate.minusMonths(i).toString().replaceAll("-", "").substring(0, 6);
            System.out.println("eDate");
            System.out.println(month);
            searchMap.put("month" + i, month);
        }
        System.out.println("page ::: " + page);
        searchMap.put("pageNo", page);
        System.out.println("searchMap :: " + searchMap);


        Map<String, Object> map = messageService.getMessageResultList(searchMap);

        System.out.println("map :: " + map);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @RequestMapping(value = "/tableList", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getMsgResultTableList(Authentication authentication) {

        List<Map> map = messageService.getMsgResultTableList();

        System.out.println("map :: " + map);

        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    /*@RequestMapping(value = "/send_sms", method = RequestMethod.POST, produces = "text/html;charset=utf-8")
    @ResponseBody
    public ResponseEntity<?> insertMessageTemplateList(
            org.apache.catalina.servlet4preview.http.HttpServletRequest request,
            @RequestParam(value = "subject", required = true) String subject,
            @RequestParam(value = "text", required = true) String text,
            @RequestParam(value = "msg_type", required = true) String msg_type,
            @RequestParam(value = "callback", required = true) String callback,
            @RequestParam(value = "group_idx", required = true) int group_idx,
            @RequestParam(value = "request_time") String request_time,
            @RequestParam(value = "mmsImage_01") ArrayList<MultipartFile> mmsImage_01,
//                                                       @RequestParam(value = "request_time") java.util.Date request_time,
            Authentication authentication) {*/


    @RequestMapping(method = RequestMethod.POST, value = "/send_sms")
//    public ResponseEntity<?> insertMessageTemplateList(WebSmsSendVO webSmsSendVO, Authentication authentication) throws Exception {
    public ResponseEntity<?> insertMessageTemplateList(@RequestParam(value = "subject", required = true) String subject,
                                                       @RequestParam(value = "text", required = true) String text,
                                                       @RequestParam(value = "msg_type", required = true) String msg_type,
                                                       @RequestParam(value = "callback", required = true) String callback,
                                                       @RequestParam(value = "group_idx", required = true) int group_idx,
                                                       @RequestParam(value = "request_time", required = true) String request_time,
                                                       @RequestParam(value = "fileloc1", required = false) MultipartFile fileloc1,

//                                                       @RequestParam(value = "request_time") java.util.Date request_time,
                                                       Authentication authentication) throws Exception {

        System.out.println("in send sms");
        System.out.println(msg_type);
        System.out.println(fileloc1);
        String fileName = null;
        if (msg_type.equals("3") && fileloc1 != null) {
            System.out.println("mmsImage_01 :: " + fileloc1);
            fileName = fileService.mmsImageUpload(fileloc1, "MMS");
            msg_type = "MMS";
        } else if (msg_type.equals("1")) {
            msg_type = "SMS";
        } else {
            msg_type = "LMS";
        }
        System.out.println("fileName :: " + fileName);

        RandomString randomString = new RandomString(10);
        String randomTokenKey = randomString.nextString();

        StringBuffer post_key = new StringBuffer();

        Calendar calendar = Calendar.getInstance();
        String dateFormat = new SimpleDateFormat("ddHHmmssSSS_").format(calendar.getTime());
        System.out.println("현재 시간 : " + dateFormat);
//        System.out.println("현재 시간 : " + dateFormat.format(calendar.getTime()));


        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        UserVO user = userService.loadUserByUsername(userPrincipal.getUserId());

        // group_idx 로 전화번호 리스트 가져오기
        Map<String, Object> map = new HashMap<>();
        CustomerVO customerVO = new CustomerVO();
        customerVO.setGroup_idx(group_idx);
        customerVO.setUser_id(user.getUserid());

        map = customerService.getGroupByCustomerList(customerVO); // 그룹리스트 번호목록 => map
        List<CustomerVO> customerVOList = (List<CustomerVO>) map.get("usedGroup");


        post_key.append(user.getUserid() + "_");
        post_key.append(dateFormat);
        post_key.append(randomTokenKey);


        List<WebSmsSendVO> webSmsList = new ArrayList<>();  //발송 리스트


        for (int i = 0; i <= customerVOList.size() - 1; i++) {
            WebSmsSendVO vo = new WebSmsSendVO();
            vo.setMsg_type(msg_type);
            vo.setDstaddr(customerVOList.get(i).getPhone_number());
            vo.setCallback(callback);
            vo.setSubject(subject);
            vo.setText(text);
            vo.setUserid(user.getUserid());
            vo.setRequest_time(request_time);
            vo.setPost_key(post_key.toString()); //발송토큰값
            if (msg_type.equals("MMS")) vo.setFileloc1(fileName);
            webSmsList.add(vo);
        }

        Map<String, Object> result = messageService.webSendSMS(webSmsList);

        return new ResponseEntity<>(result, HttpStatus.OK);
//        }
    }


    @RequestMapping(value = "/blockNumber", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getBlockNumberList(@RequestParam(value = "page", defaultValue = "1") Integer page, Authentication authentication) {

        UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
        UserVO userVO = userService.loadUserByUsername(user.getUserId());

        System.out.println("getBlockNumberList");

        BlockNumberVO blockNumberVO = new BlockNumberVO();
        blockNumberVO.setUserid(userVO.getUserid());
        blockNumberVO.setPageNo(page);

        Map<String, Object> map = messageService.getBlockNumberList(blockNumberVO);

        System.out.println(map);
        return new ResponseEntity<>(map, HttpStatus.OK);
    }

}
