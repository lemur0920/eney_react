package eney.service;

import eney.mapper.DataSourceDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Service
public class DataSourceService {

	@Resource
	DataSourceDao dataSourceDao;

	@Resource
	BatchService batchService;


	public List<Map<String, Object>> GetTables(String userid) {
		return dataSourceDao.GetTables(userid);
	}

	public List<Map<String, Object>> GetFields(String tablename) {

		List<Map<String,Object>> list = dataSourceDao.getFields(tablename);
		List<Map<String,Object>> calList = new ArrayList<>();


		for(Map<String,Object> map : list){
			String type = (String)map.get("columnType");

			switch(type){
				case "varchar" : map.replace("columnType", "string"); break;
				case "text" : map.replace("columnType", "string"); break;
				case "int" :
					map.replace("columnType", "number");

					//계산을 위해서 임의로 생성한 컬럼
					String[] columnName = String.valueOf( map.get("columnName")).split("_");
					if(columnName.length == 1){
						Map<String,Object> calMap = new HashMap<>();
						calMap.put("columnName", map.get("columnName")+"_계산");
						calMap.put("columnType", "number");
						calMap.put("columnComment", "");

						Map<String, Object> DayAgoMap = new HashMap<>();
						DayAgoMap.put("columnName", map.get("columnName")+"_하루전");
						DayAgoMap.put("columnType", "number");
						DayAgoMap.put("columnComment", "");

						Map<String, Object> WeekAgo = new HashMap<>();
						WeekAgo.put("columnName", map.get("columnName")+"_일주일");
						WeekAgo.put("columnType", "number");
						WeekAgo.put("columnComment", "");

						Map<String, Object> AvgMap = new HashMap<>();
						AvgMap.put("columnName", map.get("columnName")+"_평균");
						AvgMap.put("columnType", "number");
						AvgMap.put("columnComment", "");

						calList.add(calMap);
						calList.add(DayAgoMap);
						calList.add(WeekAgo);
						calList.add(AvgMap);
					}

					break;
				case "float" :  map.replace("columnType", "number"); break;
				case "date" : map.replace("columnType", "date");break;
			}


		}

		for(Map<String,Object> cal : calList){
			list.add(cal);
		}

		return list;
	}


	public List<Map<String, Object>> GetDatas(String tablename) {

		return GetCompareDatas(tablename);
	}

	public List<Map<String, Object>> GetBatchDatas(String tablename) {

		return GetCompareDatas(tablename);
	}

	public int i = 0;

	public List<Map<String, Object>> GetCompareDatas(String tablename) {

		Map<String, Object> map = new HashMap<>();

		map.put("tablename", tablename);

		Calendar c1 = new GregorianCalendar();

		Calendar c2 = new GregorianCalendar();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); // 날짜 포맷
		List<Map<String, Object>> list = dataSourceDao.getBatchDatas(map);
		List<Map<String,Object>> fields = GetFields(tablename);
		List<String> compareFieldList = new ArrayList<>();

		fields
				.stream()
				.filter(m -> {
					if(m.get("columnType").equals("number")
							&& String.valueOf(m.get("columnName")).contains("_")){
						compareFieldList.add((String)m.get("columnName"));
					}
					return true;
				})
				.collect(Collectors.toList());
		for(i = 0 ; i < list.size() ; i ++){
			c1.setTime((Date) list.get(i).get("날짜"));
			c1.add(Calendar.DATE, -1);

			c2.setTime((Date) list.get(i).get("날짜"));
			c2.add(Calendar.DATE, -7);

			String d = sdf.format(c1.getTime());
			String weekAgo = sdf.format(c2.getTime());

			String compare_value = (String) list.get(i).get("기기카테고리");

			for(String compare : compareFieldList){
				list.get(i).put(compare, 0);
			}
			list
					.stream()
					.filter(s -> {
						if(String.valueOf(s.get("날짜")).equals(d)  && String.valueOf(s.get("기기카테고리")).equals(compare_value)){
							for(String compare : compareFieldList){
								String[] arr = compare.split("_");
								if(compare.contains("_하루전")){
									list.get(i).replace( compare, s.get(arr[0]));
								}else if(compare.contains("_계산")){
									list.get(i).replace(compare, Integer.parseInt(list.get(i).get(arr[0]).toString()) - Integer.parseInt(s.get(arr[0]).toString()));
								}
							}
							return false;
						}else if(String.valueOf(s.get("날짜")).equals(weekAgo)  && String.valueOf(s.get("기기카테고리")).equals(compare_value)){
							for(String compare : compareFieldList){
								String[] arr = compare.split("_");
								if(compare.contains("_일주일")){
									list.get(i).replace(compare, batchService.getValue(s.get("날짜"), s.get("기기카테고리"), arr[0], tablename));
								}else if(compare.contains("_평균")){
									list.get(i).replace(compare, Integer.parseInt(String.valueOf(batchService.getValue(s.get("날짜"), s.get("기기카테고리"), arr[0], tablename) / 7)));
								}
							}
						}
						return true;
					})
					.collect(Collectors.toList());
		}

		i = 0;

		return list;



	}

}