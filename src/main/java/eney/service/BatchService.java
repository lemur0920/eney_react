package eney.service;

import eney.domain.ServiceBIVO;
import eney.domain.UserVO;
import eney.mapper.BatchDao;
import eney.util.DateUtil;
import eney.util.GABatch;
import eney.util.StringUtil;
import com.google.api.services.analyticsreporting.v4.AnalyticsReporting;
import com.google.api.services.analyticsreporting.v4.model.GetReportsResponse;
import eney.domain.ServiceBIVO;
import eney.domain.UserVO;
import eney.util.DateUtil;
import eney.util.GABatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class BatchService {

    private static final Logger logger = LoggerFactory.getLogger(BatchService.class);

    @Resource
    BatchDao batchDao;

    @Resource
    GABatch gaBatch;

    public List<Map<String,Object>> getTableList() {
        return batchDao.getTableList();
    }

    public List<ServiceBIVO> serviceBIList(){
        return batchDao.getServiceBIList();
    }

    public Map<String,Object> getTable(String tablename){

        Map<String,Object> map = new HashMap<>();

        map.put("tablename", tablename);

        return batchDao.getTable(map);}

//    @Scheduled(cron="0 0 1 * * *")
    public void run() throws Exception{

        GetReportsResponse response;

        List<Map<String, Object>> tableList = getTableList();

        for(Map<String, Object> table : tableList){
            if(table.get("status").equals("using")){
                AnalyticsReporting service = gaBatch.initializeAnalyticsReporting(gaBatch.getKeyFile((String)table.get("file_name")));
                response  = gaBatch.getReport(service, table, (String)table.get("view_id"));

                gaBatch.printResponse(response, table);
            }
        }

        logger.warn("GA DATA 배치 실행");

    }

    public void insertGAData(Map<String, Object> table, LinkedHashMap<String, Object> gaData) throws Exception {

        Map<String,Object> map = new HashMap<>();

        if(gaData.containsKey("ga:dayOfWeekName")){
            //영어로 되어있는 요일을 한글로 변경
            gaData.put("ga:dayOfWeekName", DateUtil.getWeekNameKr((String)gaData.get("ga:dayOfWeekName")));
        }

        if(gaData.containsKey("ga:week")){
            //ga에서 제공하는 week는 연에 대한 주차로 나오기 때문에 월 주차로 변경
            gaData.put("ga:week",   DateUtil.weekCalendar((String)gaData.get("ga:date")));
        }

        map.put("table_name", table.get("table_name"));
        map.put("size", gaData.size());
        map.put("list", gaData.values().stream().collect(Collectors.toList()));

        batchDao.insertGAData(map);

    }


    public List<Map<String,Object>> getGAUserInfoList(UserVO session) {

        return batchDao.getGAUserInfoList(session);
    }

    public Map<String,Object> getCompareValue(Map<String, Object> map) throws Exception{

        SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
        SimpleDateFormat new_format = new SimpleDateFormat("yyyy-MM-dd");

        Calendar start_date = Calendar.getInstance();
        Calendar end_date = Calendar.getInstance();

        Date date = transFormat.parse(String.valueOf(map.get("date")));

        start_date.setTime(date);
        end_date.setTime(date);

        start_date.add(Calendar.DATE, 1);

        List<String> list = (List<String>) map.get("key_list");

        for(int i = 0 ; i < list.size() ; i ++){
            list.set(i,gaValue(list.get(i)));
        }

        Map<String,Object> searchMap = new HashMap<>();
        Map<String,Object> resultMap = new HashMap<>();

        searchMap.put("list", list);
        searchMap.put("column1", "날짜");
        searchMap.put("column2", "기기카테고리");
        searchMap.put("column3", "통계값");
        searchMap.put("day_value", "1.일");
        searchMap.put("keyword", map.get("cal_keyword"));
        searchMap.put("table_name", map.get("table_name"));
        searchMap.put("end_date", new_format.format(end_date.getTime()));

        switch(String.valueOf(map.get("cal_name"))){
            case "1.일" : break;
            case "1.주" :
                searchMap.put("start_date", Week(start_date, -7));
                resultMap.put("start_date",DateUtil.convertDateToString(start_date, end_date));
                resultMap.put("value",getSumValue(searchMap));
                break;

            case "1.1개월" :
                searchMap.put("start_date", Month(start_date, -1));
                resultMap.put("start_date",DateUtil.convertDateToString(start_date, end_date));
                resultMap.put("value",getSumValue(searchMap));
                break;

            case "1.3개월" :
                searchMap.put("start_date", Month(start_date, -3));
                resultMap.put("start_date",DateUtil.convertDateToString(start_date, end_date));
                resultMap.put("value",getSumValue(searchMap));

                break;

            case "1.6개월" :
                searchMap.put("start_date", Month(start_date, -6));
                resultMap.put("start_date",DateUtil.convertDateToString(start_date, end_date));
                resultMap.put("value",getSumValue(searchMap));
                break;

            case "1.년" :
                searchMap.put("start_date", Year(start_date, -1));
                resultMap.put("start_date",DateUtil.convertDateToString(start_date, end_date));
                resultMap.put("value",getSumValue(searchMap));
                break;

            case "2.비교_1일" :
                start_date.add(Calendar.DATE, -1);
                resultMap.put("start_date",DateUtil.convertDateToString2(start_date));

                searchMap.put("date", Day(start_date, -1));
                /*resultMap.put("value",getValue(searchMap));*/
                break;

           case "2.비교_7일" :
                searchMap.put("date", Day(start_date, -7));
                resultMap.put("start_date",DateUtil.convertDateToString(start_date, end_date));

               searchMap.put("start_date", Day(start_date, -7));
               searchMap.put("end_date", Day(end_date, -7));
               resultMap.put("value", getSumValue(searchMap));

                break;

            case "2.비교_1개월" :
                start_date.add(Calendar.MONTH, -1);
                resultMap.put("start_date",DateUtil.convertDateToString(start_date, end_date));

                searchMap.put("start_date", Month(start_date, -1));
                searchMap.put("end_date", Month(end_date, -1));
                resultMap.put("value",getSumValue(searchMap));
                break;

            case "2.비교_3개월" :
                start_date.add(Calendar.MONTH, -3);
                resultMap.put("start_date",DateUtil.convertDateToString(start_date, end_date));

                searchMap.put("start_date", Month(start_date, -3));
                searchMap.put("end_date", Month(end_date, -3));
                resultMap.put("value",getSumValue(searchMap));

                break;

            case "2.비교_6개월" :

                start_date.add(Calendar.MONTH, -6);
                resultMap.put("start_date",DateUtil.convertDateToString(start_date, end_date));

                searchMap.put("start_date", Month(start_date, -6));
                searchMap.put("end_date", Month(end_date, -6));
                resultMap.put("value",getSumValue(searchMap));
                break;

            case "2.비교_1년" :

                start_date.add(Calendar.YEAR, -1);
                resultMap.put("start_date",DateUtil.convertDateToString(start_date, end_date));

                searchMap.put("start_date", Year(start_date, -1));
                searchMap.put("end_date", Year(end_date, -1));
                resultMap.put("value",getSumValue(searchMap));
                break;

            case "3.증감_1일" :
                searchMap.put("start_date", Day(start_date, -1));
                resultMap.put("start_date",DateUtil.convertDateToString(start_date, end_date));

                searchMap.put("compare_start_date", Day(start_date, -1));
                searchMap.put("compare_end_date", Day(end_date, -1));
                break;
            case "3.증감_7일" :
                searchMap.put("start_date", Day(start_date, -7));
                resultMap.put("start_date",DateUtil.convertDateToString(start_date, end_date));

                searchMap.put("compare_start_date", Day(start_date, -7));
                searchMap.put("compare_end_date", Day(end_date, -7));

                resultMap.put("value",getComValue(searchMap));
                break;
            case "3.증감_1개월" :
                searchMap.put("start_date", Month(start_date, -1));
                resultMap.put("start_date",DateUtil.convertDateToString(start_date, end_date));

                searchMap.put("compare_start_date", Month(start_date, -1));
                searchMap.put("compare_end_date", Month(end_date, -1));

                resultMap.put("value",getComValue(searchMap));
                break;
            case "3.증감_3개월" :
                searchMap.put("start_date", Month(start_date, -3));
                resultMap.put("start_date",DateUtil.convertDateToString(start_date, end_date));

                searchMap.put("compare_start_date", Month(start_date, -3));
                searchMap.put("compare_end_date", Month(end_date, -3));

                resultMap.put("value",getComValue(searchMap));
                break;
            case "3.증감_6개월" :
                searchMap.put("start_date", Month(start_date, -6));
                resultMap.put("start_date",DateUtil.convertDateToString(start_date, end_date));

                searchMap.put("compare_start_date", Month(start_date, -6));
                searchMap.put("compare_end_date", Month(end_date, -6));
                resultMap.put("value",getComValue(searchMap));
                break;
            case "3.증감_1년" :
                searchMap.put("start_date", Year(start_date, -1));
                resultMap.put("start_date",DateUtil.convertDateToString(start_date, end_date));

                searchMap.put("compare_start_date", Year(start_date, -1));
                searchMap.put("compare_end_date", Year(end_date, -1));

                resultMap.put("value",getComValue(searchMap));
                break;
            case "4.평균_7일"  :
                searchMap.put("start_date", Day(start_date, -7));

                resultMap.put("start_date",DateUtil.convertDateToString(start_date, end_date));

                resultMap.put("value",getAvgValue(searchMap));

                break;
            case "4.평균_1개월"  :
                searchMap.put("start_date", Month(start_date, -1));
                resultMap.put("start_date",DateUtil.convertDateToString(start_date, end_date));
                resultMap.put("value",getAvgValue(searchMap));
                break;
            case "4.평균_3개월"  :
                searchMap.put("start_date", Month(start_date, -3));
                resultMap.put("start_date",DateUtil.convertDateToString(start_date, end_date));
                resultMap.put("value",getAvgValue(searchMap));
                break;
            case "4.평균_6개월"  :
                searchMap.put("start_date", Month(start_date, -6));
                resultMap.put("start_date",DateUtil.convertDateToString(start_date, end_date));
                resultMap.put("value",getAvgValue(searchMap));
                break;
            case "4.평균_1년"  :
                searchMap.put("start_date", Year(start_date, -1));
                resultMap.put("start_date",DateUtil.convertDateToString(start_date, end_date));
                resultMap.put("value",getAvgValue(searchMap));
                break;

        }



        return resultMap;
    }

    private Map<String,Object> getComValue(Map<String,Object> searchMap) {
        Map<String, Object> value = batchDao.getComValue(searchMap);

        return value;
    }
    private Map<String,Object> getSumValue(Map<String,Object> searchMap) {
        Map<String, Object> value = batchDao.getSumValue(searchMap);

        return value;
    }

    private Map<String,Object> getAvgValue(Map<String,Object> searchMap) {
        Map<String, Object> value = batchDao.getAvgValue(searchMap);

        return value;
    }

    public Integer getValue(Object date, Object device, String compare, String tablename) {

        Map<String,Object> searchMap = new HashMap<>();

        searchMap.put("column1", "날짜");
        searchMap.put("column2", "기기카테고리");
        searchMap.put("tablename", tablename);


        searchMap.put("날짜", date);
        searchMap.put("기기카테고리", device);
        searchMap.put("compare", compare);

        return batchDao.getValue(searchMap);

    }

    private String Day(Calendar date, Integer day){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        date.add(Calendar.DATE, day);
        return format.format(date.getTime());
    }

    private String Week(Calendar date, Integer day){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        date.add(Calendar.DATE, day);
        return format.format(date.getTime());
    }

    private String Month(Calendar date, Integer day){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        date.add(Calendar.MONTH, day);
        return format.format(date.getTime());
    }

    private String Year(Calendar date, Integer day){
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        date.add(Calendar.YEAR, day);
        return format.format(date.getTime());
    }

    public String[] gaValueArray(){
        String[] array = {
                "ga:pageviews","ga:entrances","ga:revenuePerUser",
                "ga:transactions","ga:organicSearches","ga:sessions",
                "ga:productAddsToCart","ga:transactionRevenue","ga:newUsers",
                "ga:goal20Completions","ga:productListViews","ga:productListClicks",
                "ga:productCheckouts","ga:uniquePurchases","ga:itemRevenue"
        };
        return array;
    }
    public Map<String,String> getGAMap(){
        Map<String, String> map = new HashMap<>();

        map.put("ga:pageviews", "페이지뷰");
        map.put("ga:entrances", "방문수");
        map.put("ga:revenuePerUser", "사용자당수익");
        map.put("ga:transactions", "거래수");
        map.put("ga:organicSearches", "자연검색수");
        map.put("ga:sessions", "세션");
        map.put("ga:productAddsToCart","장바구니추가횟수");
        map.put("ga:transactionRevenue", "수익");
        map.put("ga:transactionRevenuePerSession", "세션당수익");
        map.put("ga:newUsers","신규방문자");
        map.put("ga:goal20Completions","가입수");
        map.put("ga:productListViews","제품목록조회수");
        map.put("ga:productListClicks","제품목록클릭수");
        map.put("ga:productCheckouts","제품결제횟수");
        map.put("ga:uniquePurchases","순구매수");
        map.put("ga:itemRevenue","상품수익");

        map.put("페이지뷰", "ga:pageviews");
        map.put("방문수","ga:entrances");
        map.put("사용자당수익","ga:revenuePerUser");
        map.put("거래수", "ga:transactions");
        map.put("자연검색수","ga:organicSearches");
        map.put("세션","ga:sessions");
        map.put("장바구니추가횟수","ga:productAddsToCart");
        map.put("수익","ga:transactionRevenue");
        map.put("신규방문자","ga:newUsers");
        map.put("가입수","ga:goal20Completions");
        map.put("제품목록조회수","ga:productListViews");
        map.put("제품목록클릭수","ga:productListClicks");
        map.put("제품결제횟수","ga:productCheckouts");
        map.put("순구매수","ga:uniquePurchases");
        map.put("상품수익","ga:itemRevenue");

        return map;
    }

    public String gaValue(String key){
        return getGAMap().get(key);
    }

}