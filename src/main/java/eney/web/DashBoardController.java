package eney.web;


import eney.domain.*;
import eney.service.DashboardService;
import eney.service.UserService;
import eney.util.DateUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import eney.domain.UserVO;
import eney.util.DateUtil;
import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sun.management.resources.agent;

import javax.annotation.Resource;
import javax.net.ssl.HttpsURLConnection;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@Controller
@RequestMapping(value="/service/dashboard/*")
@Api(value="service", produces="application/json", protocols="http/https(권장)")
public class DashBoardController {

    @Resource
    DashboardService dashboardService;

    @Resource
    UserService userService;

    @RequestMapping(value="/change_rate",method=RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getChangeRate (Authentication authentication){

        UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
        UserVO userVO = userService.loadUserByUsername(user.getUserId());

        Map<String,Object> map = new HashMap<>();



        return new ResponseEntity<>(map, HttpStatus.OK);

    }



    @RequestMapping(value="{userid}", method= RequestMethod.GET)
    public @ResponseBody String GetsByUserID(@PathVariable("userid") String userid) throws JsonProcessingException {
        String jsonData = new ObjectMapper().writeValueAsString(dashboardService.GetsByUserID(userid));
        return jsonData;
    }

    @RequestMapping(value="{userid}/{groupid}", method= RequestMethod.GET)
    public @ResponseBody  String Gets(@PathVariable("userid") String userid, @PathVariable("groupid") Integer groupid) throws JsonProcessingException {

        Map<String,Object> map = new HashMap<>();

        map.put("userid",userid);
        map.put("groupid",groupid);

        String jsonData = new ObjectMapper().writeValueAsString(dashboardService.gets(map));

        return jsonData;
    }

    @RequestMapping(value="{userid}/{groupid}", method= RequestMethod.POST)
    public @ResponseBody  Integer Post(@PathVariable("userid") String userid, @PathVariable("groupid") Integer groupid, @RequestBody String data) {

        Map<String,Object> map = new HashMap<>();

        map.put("userid",userid);
        map.put("groupid",groupid);
        map.put("data", data);

        dashboardService.post(map);

        return Integer.parseInt(map.get("idx").toString());

    }


    @RequestMapping(value="{userid}/{id}", method= RequestMethod.PUT)
    public @ResponseBody  void Put(@PathVariable("userid") String userid, @PathVariable("id") Integer id, @RequestBody String data) throws JsonProcessingException {

        dashboardService.put(userid, id, data);

    }

    @RequestMapping(value="{userid}/{id}", method= RequestMethod.DELETE)
    public @ResponseBody  void Delete(@PathVariable("userid") String userid, @PathVariable("id") Integer id) throws JsonProcessingException {

        dashboardService.delete(userid, id);
    }

//    @RequestMapping(value="/call_report/getCallTotalCount/{userId}", method= RequestMethod.GET)
//    public @ResponseBody Map<String,Object> getCallTotalCount(@PathVariable("userId") String userId) throws JsonProcessingException {
//        userId = "myksh";
//
//        Map<String,Object> map = new HashMap<>();
//
//        String thisWeekMonday = DateUtil.getCurMonday();
//        String today = DateUtil.getStringToday();
//        int DiffDayCount = DateUtil.getDiffDayCount(thisWeekMonday, today);
//
//        Date lastMondayDate = DateUtil.getPreviousWeek(DateUtil.getStringToDate(thisWeekMonday));
//        String lastMonday = DateUtil.getDateString(lastMondayDate, "yyyyMMdd");
//        String lastCurrentDay = DateUtil.getDateString(DateUtil.getNextDate(lastMondayDate, DiffDayCount),"yyyyMMdd");
//
//        String [] monthArray = DateUtil.getDiffDays(lastMonday, today);
//
//        HashSet monthList = new HashSet();
//        for(String item: monthArray) {
//            System.out.println(item.substring(4, 6));
//            monthList.add(item.substring(4, 6));
//        }
//
//        System.out.println(thisWeekMonday);
//        System.out.println(today);
//        System.out.println(lastMonday);
//        System.out.println(lastCurrentDay);
//        System.out.println(monthList);
//
//        map.put("userId",userId);
//        map.put("resultCode",0);
//        map.put("startDate",thisWeekMonday);
//        map.put("endDate",today);
//        map.put("startCompare",lastMonday);
//        map.put("endCompare",lastCurrentDay);
//        map.put("month_list",monthList);
//
//        return dashboardService.getCallCount(map);
//    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="/compare_data", method= RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getCompareDate(Authentication authentication) throws JsonProcessingException {
        UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
        UserVO userVO = userService.loadUserByUsername(user.getUserId());


        DashboardDataVO dashboardDataVO = dashboardService.getCompareData(userVO.getUserid());

        return new ResponseEntity<>(dashboardDataVO, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="/getDateByCallCount/{startDate}/{endDate}", method= RequestMethod.GET)
    public @ResponseBody  ResponseEntity<?> getDateByCallCount(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate,Authentication authentication) throws JsonProcessingException {
        UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
        UserVO userVO = userService.loadUserByUsername(user.getUserId());

        Map<String,Object> map = new HashMap<>();

        map.put("userId",userVO.getUserid());
        map.put("startDate",startDate);
        map.put("endDate",endDate);

        Map<String,Object> result = dashboardService.getDateByCallCount(map);

        System.out.println(result);

        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="/getTimeByCallCount/{date}", method= RequestMethod.GET)
    public @ResponseBody  ResponseEntity<?>  getTimeByCallCount(@PathVariable("date") String date,Authentication authentication) throws JsonProcessingException {

        UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
        UserVO userVO = userService.loadUserByUsername(user.getUserId());
        String month = date.substring(4, 6);

        Map<String,Object> map = new HashMap<>();

        map.put("userId",userVO.getUserid());
        map.put("date",date);
        map.put("month",month);

        Map<String,Object> list = dashboardService.getTimeByCallCount(map);
        list.put("date", date);

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @RequestMapping(value="/call_report/getTodayTimeByCallCount/{userId}", method= RequestMethod.GET)
    public @ResponseBody  Map<String,Object> getTodayTimeByCallCount(@PathVariable("userId") String userId) throws JsonProcessingException {

        userId = "myksh";

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); // 날짜 포맷


        Map<String, Object> map1 = new HashMap<>();

        Calendar c1 = new GregorianCalendar();
        c1.add(Calendar.DATE, -1); // 오늘날짜로부터 -1
        String yesterday = sdf.format(c1.getTime()); // String으로 저장

        String yesterdayMonth = yesterday.substring(4, 6);

        map1.put("userId", userId);
        map1.put("date", yesterday);
        map1.put("month", yesterdayMonth);

        Map<String, Object> map2 = new HashMap<>();

        Calendar c2 = new GregorianCalendar();
        String today = sdf.format(c1.getTime()); // String으로 저장

        String todayMonth = today.substring(4, 6);

        map2.put("userId", userId);
        map2.put("date", today);
        map2.put("month", todayMonth);


        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("today", dashboardService.getTimeByCallCount(map1));
        resultMap.put("yesterday", dashboardService.getTimeByCallCount(map2));

        return resultMap;
    }

    @PreAuthorize("isAuthenticated()")
    @RequestMapping(value="/getTimeAvgCallCount/{startDate}/{endDate}", method= RequestMethod.GET)
    public @ResponseBody  ResponseEntity<?> getTimeAvgCallCount(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate, Authentication authentication) throws InterruptedException, ExecutionException,Exception {
        UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
        UserVO userVO = userService.loadUserByUsername(user.getUserId());

        Map<String,Object> map = new HashMap<>();
        Map<String,Object> visitMap = new HashMap<>();
        String [] monthArray = DateUtil.getDiffDays(startDate, endDate);

        HashSet monthList = new HashSet();
        for(String item: monthArray) {
            monthList.add(item.substring(4, 6));
        }


        //시간대 별 통계
        map.put("userId",userVO.getUserid());
        map.put("startDate",startDate);
        map.put("endDate",endDate);
        map.put("month_list",monthList);


        //방문자 유형
        String [] mobileList ={"010","011","016","017","018","019"};
        String [] areaList = {"02","031","032","033","041","042","043","051","052","053","054","055","061","062","063","064"};

        visitMap.put("userId",userVO.getUserid());
        visitMap.put("startDate",startDate);
        visitMap.put("endDate",endDate);
        visitMap.put("month_list",monthList);
        visitMap.put("mobile_list",mobileList);
        visitMap.put("area_list",areaList);

        long start = System.currentTimeMillis();

        Map<String, Object> tmpMap = new HashMap<String, Object>();

        CompletableFuture<Map<String,Object>> timeAvg = dashboardService.getTimeAvgCallCount(map);
        CompletableFuture<List<Map<String,Object>>> visit = dashboardService.getVisitTypeCount(visitMap);
        CompletableFuture<Map<String,Object>> location = dashboardService.getLocationRanking(map);
        CompletableFuture<List<Map<String,Object>>> agent = dashboardService.getAgentRanking(map);

        CompletableFuture.allOf(timeAvg, visit, location,agent).join();
        try {
            tmpMap.put("timeAvg",timeAvg.get());
            tmpMap.put("visit",visit.get());
            tmpMap.put("location",location.get());

            tmpMap.put("agent", agent.get());

        } catch(ExecutionException e) {
            e.printStackTrace();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Elapsed time: " + (System.currentTimeMillis() - start) + " ms");
            return new ResponseEntity<>(tmpMap, HttpStatus.OK);
    }
//    @RequestMapping(value="/call_report/getVisitTypeCount/{startDate}/{endDate}", method= RequestMethod.GET)
//    public @ResponseBody  List<Map<String, Object>> getVisitTypeCount(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) throws JsonProcessingException {
//        UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        Map<String,Object> map = new HashMap<>();
//        String [] monthArray = DateUtil.getDiffDays(startDate, endDate);
//
//        HashSet monthList = new HashSet();
//        for(String item: monthArray) {
//            monthList.add(item.substring(4, 6));
//        }
//        String [] mobileList ={"010","011","016","017","018","019"};
//        String [] areaList = {"02","031","032","033","041","042","043","051","052","053","054","055","061","062","063","064"};
//
//        map.put("userId",userVO.getUserid());
//        map.put("startDate",startDate);
//        map.put("endDate",endDate);
//        map.put("month_list",monthList);
//        map.put("mobile_list",mobileList);
//        map.put("area_list",areaList);
//
//        return dashboardService.getVisitTypeCount(map);
//    }

//    @RequestMapping(value="/call_report/getAgentRanking/{startDate}/{endDate}", method= RequestMethod.GET)
//    public @ResponseBody  List<Map<String, Object>> getAgentRanking(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) throws JsonProcessingException {
//        UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        Map<String,Object> map = new HashMap<>();
//        String [] monthArray = DateUtil.getDiffDays(startDate, endDate);
//
//        HashSet monthList = new HashSet();
//        for(String item: monthArray) {
//            monthList.add(item.substring(4, 6));
//        }
//
//        map.put("userId",userVO.getUserid());
//        map.put("startDate",startDate);
//        map.put("endDate",endDate);
//        map.put("month_list",monthList);
//
//        return dashboardService.getAgentRanking(map);
//    }
//
//    @RequestMapping(value="/call_report/getLocationRanking/{startDate}/{endDate}", method= RequestMethod.GET)
//    public @ResponseBody  List<Map<String, Object>> getLocationRanking(@PathVariable("startDate") String startDate, @PathVariable("endDate") String endDate) throws JsonProcessingException {
//        UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//        Map<String,Object> map = new HashMap<>();
//        String [] monthArray = DateUtil.getDiffDays(startDate, endDate);
//
//        HashSet monthList = new HashSet();
//        for(String item: monthArray) {
//            monthList.add(item.substring(4, 6));
//        }
//
//        map.put("userId",userVO.getUserid());
//        map.put("startDate",startDate);
//        map.put("endDate",endDate);
//        map.put("month_list",monthList);
//
//        return dashboardService.getLocationRanking(map);
//    }

    @RequestMapping(value="/call_ratio", method= RequestMethod.GET)
    public @ResponseBody ResponseEntity<?> getCallRatio(Authentication authentication) throws JsonProcessingException,InterruptedException {
        UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
        UserVO userVO = userService.loadUserByUsername(user.getUserId());


        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); // 날짜 포맷
        Calendar c1 = new GregorianCalendar();
        c1.add(Calendar.DATE, -1); // 오늘날짜로부터 -1
//        String startDate = sdf.format(c1.getTime()); // String으로 저장
//        String endDate = DateUtil.getStringToday();

        String startDate = "20190501";
        String endDate = "20190529";


        Map<String,Object> map = new HashMap<>();

        map.put("userId",userVO.getUserid());
        map.put("startDate",startDate);
        map.put("endDate",endDate);

        long start = System.currentTimeMillis();

        Map<String, Object> tmpMap = new HashMap<String, Object>();

        CompletableFuture<List<Map<String,Object>>> keyword = dashboardService.getCallRatioKeyword(map);
        CompletableFuture<Map<String,Object>> source = dashboardService.getCallRatioSource(map);
        CompletableFuture<Map<String,Object>> os = dashboardService.getCallRatioOS(map);
        CompletableFuture<Map<String,Object>> browser = dashboardService.getCallRatioBrowser(map);

        CompletableFuture.allOf(keyword, source, os, browser).join();
        try {
            tmpMap.put("keyword",keyword.get());
            tmpMap.put("source",source.get());
            tmpMap.put("os",os.get());
            tmpMap.put("browser", browser.get());

        } catch(ExecutionException e) {
            e.printStackTrace();
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Elapsed time: " + (System.currentTimeMillis() - start) + " ms");
        return new ResponseEntity<>(tmpMap, HttpStatus.OK);
    }


}
