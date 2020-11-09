package eney.service;

import eney.mapper.DashboardDao;
import eney.mapper.VisualizationDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;


@Service
public class VisualizationService {

	@Resource
	VisualizationDao visualDao;

	public String gets(String userid) throws JsonProcessingException {
		return new ObjectMapper().writeValueAsString(visualDao.gets(userid));
	}

	public String get(String userid, Integer id) throws JsonProcessingException{
		Map<String,Object> map = visualDao.get(userid,id);



		return (String) map.get("JSONStr");
	}

    public Integer post(String userid, String json) {
		Map<String,Object> map = new HashMap<>();
    	map.put("userid",userid);
    	map.put("json",json);

    	visualDao.post(map);

    	return Integer.parseInt(map.get("idx").toString());
	}

	public void put(String userid, Integer id, String json) {
		Map<String,Object> map = new HashMap<>();
		map.put("userid",userid);
		map.put("json",json);
		map.put("id",id);

		visualDao.put(map);
	}

	public void delete(String userid, Integer id) {
		Map<String,Object> map = new HashMap<>();
		map.put("userid",userid);
		map.put("id",id);

		visualDao.delete(map);

	}
}
