package eney.service;

import eney.domain.DashboardDataVO;
import eney.domain.DashboardGraphDataVO;
import eney.mapper.DashboardDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import eney.util.DateUtil;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;


@Service
public class DashboardService {

	@Resource
	DashboardDao dashboardDao;

	public List<Map<String, Object>> gets(Map<String,Object> map){
		return dashboardDao.gets(map);
	}

	public Map<String, Object> get(Map<String,Object> map) throws JsonProcessingException{
		return dashboardDao.get(map);
	}

	public Integer post(Map<String,Object> map) {
		 return dashboardDao.post(map);
	}

	public void put(String userid, Integer id, String data) {
		dashboardDao.put(userid,id,data);
	}

	public void delete(String userid, Integer id) {
		dashboardDao.delete(userid,id);
	}

    public List<Map<String,Object>> GetsByUserID(String userid) {
		return dashboardDao.GetsByUserID(userid);
    }

//    public Map<String,Object> getCallCount(Map<String,Object> map){
//		return dashboardDao.getCallCount(map);
//	}
//	public Map<String, Object> getTest(Map<String,Object> map, Map<String,Object> visitMap)throws Exception{
//
//		long start = System.currentTimeMillis();
//
//		Map<String,Future> futures = new HashMap<String, Future>();
//
//		Map<String, Object> tmpMap = new HashMap<String, Object>();
//
//		CompletableFuture<List<Map<String,Object>>> timeAvg = getTimeAvgCallCount2(map);
//		CompletableFuture<List<Map<String,Object>>> visit = getVisitTypeCount2(visitMap);
//		CompletableFuture<List<Map<String,Object>>> location = getLocationRanking2(map);
//		CompletableFuture<List<Map<String,Object>>> agent = getAgentRanking2(map);
//
//		CompletableFuture.allOf(timeAvg, visit, location,agent).join();
//
//		try {
//			tmpMap.put("timeAvg",timeAvg.get());
//
//			tmpMap.put("visit",visit.get());
//
//			tmpMap.put("location",location.get());
//
//			tmpMap.put("agent",agent.get());
//
//		} catch(ExecutionException e) {
//			e.printStackTrace();
//		} catch(InterruptedException e) {
//			e.printStackTrace();
//		}
//
//		System.out.println("Elapsed time: " + (System.currentTimeMillis() - start) + " ms");
//
//		return tmpMap;
//
//	}


	@Async
	public CompletableFuture<Map<String,Object>> getTimeAvgCallCount(Map<String,Object> map)throws InterruptedException{
		List<Map<String,Object>> result = dashboardDao.getTimeAvgCallCount(map);

		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();

		String [] labels = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};


		for(int i = 0; i<=23; i++){
			Map<String, Object> tmpMap = new HashMap<String, Object>();
			tmpMap.put("time", labels[i]);
			tmpMap.put("count", 0);
			resultMap.add(tmpMap);
		}

		int index = 0;

		for (Map<String,Object> item : result){

			String timeString = String.valueOf(item.get("cs_time")).substring(0, 2);
			int timeInt = Integer.parseInt(timeString);
			int value = (Integer) resultMap.get(timeInt).get("count");

			resultMap.get(timeInt).put("count",value+1);
		}

		ArrayList<String> timeList = new ArrayList<>();
		ArrayList<Integer> countList = new ArrayList<>();


		for (Map<String,Object> item : resultMap) {
			timeList.add(item.get("time").toString());
			countList.add(Integer.parseInt(item.get("count").toString()));
		}

		Map<String,Object> value = new HashMap<>();
		value.put("timeList", timeList);
		value.put("countList", countList);
		value.put("startDate", map.get("startDate"));
		value.put("endDate", map.get("endDate"));

//		Object.keys(response).forEach(function(key) {
//			timeList.push(response[key].time+"시")
//			countList.push(response[key].count)
//		});
		return CompletableFuture.completedFuture(value);
	}

	@Async
	public CompletableFuture<List<Map<String, Object>>> getVisitTypeCount(Map<String,Object> map)throws InterruptedException{
		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
		resultMap.add(dashboardDao.getRevisitTypeCount(map));
		resultMap.add(dashboardDao.getCallDeviceTypeCount(map));

		return CompletableFuture.completedFuture(resultMap);

	}

	@Async
	public CompletableFuture<Map<String,Object>> getLocationRanking(Map<String,Object> map)throws InterruptedException{
		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();


		resultMap = dashboardDao.getLocationRanking(map);

		ArrayList<String> sidoList = new ArrayList<>();
		ArrayList<Integer> countList = new ArrayList<>();


		for (Map<String,Object> item : resultMap) {
			sidoList.add(item.get("sido").toString());
			countList.add(Integer.parseInt(item.get("count").toString()));
		}

		Map<String,Object> value = new HashMap<>();
		value.put("sidoList", sidoList);
		value.put("countList", countList);
		value.put("startDate", map.get("startDate"));
		value.put("endDate", map.get("endDate"));

		return CompletableFuture.completedFuture(value);
	}

	@Async
	public CompletableFuture<List<Map<String,Object>>> getAgentRanking(Map<String,Object> map)throws InterruptedException{
		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
		resultMap = dashboardDao.getAgentRanking(map);

//		ArrayList<String> agentList = new ArrayList<>();
//		ArrayList<Integer> countList = new ArrayList<>();
//
//
//		for (Map<String,Object> item : resultMap) {
//			agentList.add(item.get("agent_name").toString());
//			countList.add(Integer.parseInt(item.get("count").toString()));
//		}
//
//		Map<String,Object> value = new HashMap<>();
//		value.put("agentList", agentList);
//		value.put("countList", countList);

		return CompletableFuture.completedFuture(resultMap);
	}

	////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    public DashboardDataVO getCompareData(String userId){

        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); // 날짜 포맷

        Map<String, Object> map1 = new HashMap<>();

        Calendar c1 = new GregorianCalendar();
        c1.add(Calendar.DATE, -1); // 오늘날짜로부터 -1
        String yesterday = sdf.format(c1.getTime()); // String으로 저장

        Calendar c2 = new GregorianCalendar();
        c2.add(Calendar.DATE, -2); // 오늘날짜로부터 -1
        String twoDaysAgo = sdf.format(c2.getTime()); // String으로 저장

        Map<String,Object> map = new HashMap<>();


        String [] monthArray = DateUtil.getDiffDays(twoDaysAgo, yesterday);

        HashSet monthList = new HashSet();
        for(String item: monthArray) {
            monthList.add(item.substring(4, 6));
        }

        map.put("userId",userId);
        map.put("resultCode",0);
        map.put("twoDaysAgo",twoDaysAgo);
        map.put("yesterday",yesterday);
        map.put("month_list",monthList);

        DashboardDataVO dashboardDataVO = dashboardDao.getCompareDate(map);

        Double conversionRate = getConversionRate(dashboardDataVO.getResult_call(), dashboardDataVO.getTotal_call());
        Double compareConversionRate = getConversionRate(dashboardDataVO.getCompare_result_call(), dashboardDataVO.getCompare_total_call());
        dashboardDataVO.setConversion_rate(conversionRate);


        //증감율 계산
        dashboardDataVO.setConversion_rate_change_rate(conversionRate - compareConversionRate);
        dashboardDataVO.setTotal_call_change_rate(getIncrease(dashboardDataVO.getCompare_total_call(), dashboardDataVO.getTotal_call()));
        dashboardDataVO.setResult_call_change_rate(getIncrease(dashboardDataVO.getCompare_result_call(), dashboardDataVO.getResult_call()));
        dashboardDataVO.setSvc_duration_change_rate(getIncrease(dashboardDataVO.getCompare_svc_duration(), dashboardDataVO.getSvc_duration()));

        return dashboardDataVO;


//        return dashboardDao.getCallCount(map);
    }


	public Integer getResultByCallCount(Map<String,Object> map){
		return dashboardDao.getResultByCallCount(map);
	}
	public Map<String,Object> getDateByCallCount(Map<String,Object> map){
		List<DashboardGraphDataVO> dataList = dashboardDao.getDateByCallCount(map);

		ArrayList<String> dateList = new ArrayList<>();
		ArrayList<Integer> countList = new ArrayList<>();


		for (DashboardGraphDataVO item : dataList) {
			dateList.add(item.getDate());
			countList.add(item.getCount());
		}

		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("dateList", dateList);
		resultMap.put("countList", countList);


		return resultMap;
	}

	public Map<String,Object> getTimeByCallCount(Map<String,Object> map){

		List<Map<String,Object>> dataList = dashboardDao.getTimeByCallCount(map);

		ArrayList<String> timeList = new ArrayList<>();
		ArrayList<Integer> countList = new ArrayList<>();


		for (Map<String,Object> item : dataList) {
			timeList.add(item.get("call_time").toString());
			countList.add(Integer.parseInt(item.get("count").toString()));
		}

		Map<String,Object> resultMap = new HashMap<>();
		resultMap.put("timeList", timeList);
		resultMap.put("countList", countList);

		return resultMap;
	}

//	public Map<String, Object> getCompareData(String userId){
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd"); // 날짜 포맷
//
//		Map<String, Object> map1 = new HashMap<>();
//
//		Calendar c1 = new GregorianCalendar();
//		c1.add(Calendar.DATE, -1); // 오늘날짜로부터 -1
//		String twoDaysAgo = sdf.format(c1.getTime()); // String으로 저장
//
//		String yesterdayMonth = twoDaysAgo.substring(4, 6);
//
//		map1.put("userId", userId);
//		map1.put("date", twoDaysAgo);
//		map1.put("month", yesterdayMonth);
//
//		Map<String, Object> map2 = new HashMap<>();
//
//		Calendar c2 = new GregorianCalendar();
//		c2.add(Calendar.DATE, -2); // 오늘날짜로부터 -2
//		String yesterday = sdf.format(c1.getTime()); // String으로 저장
//
//		String twoDayMonth = yesterday.substring(4, 6);
//
//		map2.put("userId", userId);
//		map2.put("date", yesterday);
//		map2.put("month", twoDayMonth);
//
//
//		Map<String, Object> resultMap = new HashMap<>();
//		resultMap.put("yesterday", dashboardDao.getTimeByCallCount(map1));
//		resultMap.put("twoDaysAgo", dashboardDao.getTimeByCallCount(map2));
//
//		return resultMap;
//	}

//	public List<Map<String, Object>> getTimeAvgCallCount(Map<String,Object> map){
//		List<Map<String,Object>> result = dashboardDao.getTimeAvgCallCount(map);
//
//		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
//
//		String [] labels = {"00","01","02","03","04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20","21","22","23"};
//
//
//		for(int i = 0; i<=23; i++){
//			Map<String, Object> tmpMap = new HashMap<String, Object>();
//			tmpMap.put("time", labels[i]);
//			tmpMap.put("count", 0);
//			resultMap.add(tmpMap);
//		}
//
//		int index = 0;
//
//		for (Map<String,Object> item : result){
//
//			String timeString = String.valueOf(item.get("cs_time")).substring(0, 2);
//			int timeInt = Integer.parseInt(timeString);
//			int value = (Integer) resultMap.get(timeInt).get("count");
//
//
//			resultMap.get(timeInt).put("count",value+1);
//		}
//
//		return resultMap;
//	}
//
//	public List<Map<String, Object>> getVisitTypeCount(Map<String,Object> map){
//		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();
//		resultMap.add(dashboardDao.getRevisitTypeCount(map));
//		resultMap.add(dashboardDao.getCallDeviceTypeCount(map));
//
//		return resultMap;
//
//	}
//
//	public List<Map<String,Object>> getLocationRanking(Map<String,Object> map){
//		return dashboardDao.getLocationRanking(map);
//	}
//
//	public List<Map<String, Object>> getAgentRanking(Map<String,Object> map){
//		return dashboardDao.getAgentRanking(map);
//	}

	public Map<String, Object> getCallRatio(Map<String,Object> map){

		Map<String, Object> resultMap = new HashMap<String, Object>();

		resultMap.put("keyword", dashboardDao.getCallRatioKeyword(map));
		resultMap.put("source", dashboardDao.getCallRatioSource(map));
		resultMap.put("os", dashboardDao.getCallRatioOS(map));
		resultMap.put("browser", dashboardDao.getCallRatioBrowser(map));

		return resultMap;
	}

	@Async
	public CompletableFuture<List<Map<String,Object>>> getCallRatioKeyword(Map<String,Object> map)throws InterruptedException{
		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();


		resultMap = dashboardDao.getCallRatioKeyword(map);

//		ArrayList<String> keywordList = new ArrayList<>();
//		ArrayList<Integer> countList = new ArrayList<>();
//
//
//		for (Map<String,Object> item : resultMap) {
//			keywordList.add(item.get("keyword").toString());
//			countList.add(Integer.parseInt(item.get("count").toString()));
//		}
//
//		Map<String,Object> value = new HashMap<>();
//		value.put("keywordList", keywordList);
//		value.put("countList", countList);

		return CompletableFuture.completedFuture(resultMap);
	}


	@Async
	public CompletableFuture<Map<String,Object>> getCallRatioSource(Map<String,Object> map)throws InterruptedException{
		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();


		resultMap = dashboardDao.getCallRatioSource(map);

		ArrayList<String> sourceList = new ArrayList<>();
		ArrayList<Integer> countList = new ArrayList<>();


		for (Map<String,Object> item : resultMap) {
			sourceList.add(item.get("source").toString());
			countList.add(Integer.parseInt(item.get("count").toString()));
		}

		Map<String,Object> value = new HashMap<>();
		value.put("sourceList", sourceList);
		value.put("countList", countList);

		return CompletableFuture.completedFuture(value);
	}

	@Async
	public CompletableFuture<Map<String,Object>> getCallRatioOS(Map<String,Object> map)throws InterruptedException{
		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();


		resultMap = dashboardDao.getCallRatioOS(map);

		ArrayList<String> osList = new ArrayList<>();
		ArrayList<Integer> countList = new ArrayList<>();


		for (Map<String,Object> item : resultMap) {
			osList.add(item.get("os").toString());
			countList.add(Integer.parseInt(item.get("count").toString()));
		}

		Map<String,Object> value = new HashMap<>();
		value.put("osList", osList);
		value.put("countList", countList);

		return CompletableFuture.completedFuture(value);
	}

	@Async
	public CompletableFuture<Map<String,Object>> getCallRatioBrowser(Map<String,Object> map)throws InterruptedException{
		List<Map<String, Object>> resultMap = new ArrayList<Map<String, Object>>();


		resultMap = dashboardDao.getCallRatioBrowser(map);

		ArrayList<String> browserList = new ArrayList<>();
		ArrayList<Integer> countList = new ArrayList<>();


		for (Map<String,Object> item : resultMap) {
			browserList.add(item.get("browser").toString());
			countList.add(Integer.parseInt(item.get("count").toString()));
		}

		Map<String,Object> value = new HashMap<>();
		value.put("browserList", browserList);
		value.put("countList", countList);

		return CompletableFuture.completedFuture(value);
	}


	//전환율 계산
	private Double getConversionRate (int resultData, int totalData){

		Double result;

		try{
			if(resultData != 0 && totalData != 0){
				result  = (((double) resultData / (double)totalData) * 100);
			}else{
				result  = 0.0;
			}
		}catch(ArithmeticException e){
			result  = 0.0;
		}

	    return Double.parseDouble(String.format("%.2f",result));

    }

    //증감율 계산
    private Double getIncrease (int compareData, int data) {

		Double result;

		System.out.println("===111===");
		System.out.println(compareData);
		System.out.println(data);

		try{
			System.out.println("===222===");
			Double value = (((double)data - (double)compareData) / (double)compareData) * 100;
			result  = Double.parseDouble(String.format("%.2f",value));
		}catch(ArithmeticException e){
			System.out.println("===333===");
			result  = 0.0;
		}

		System.out.println(result);
		System.out.println("=====result=====");

        return result;

    }


}
