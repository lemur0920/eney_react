package eney.service;

import eney.mapper.DashboardDao;
import eney.mapper.DashboardGroupDao;
import com.fasterxml.jackson.core.JsonProcessingException;
import eney.mapper.DashboardGroupDao;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class DashboardGroupService {

	@Resource
    DashboardGroupDao dashboardGroupDao;

	public List<Map<String, Object>> gets(String userid){
		return dashboardGroupDao.gets(userid);
	}

	public List<Map<String,Object>> get(Map<String,Object> map) throws JsonProcessingException{
		return dashboardGroupDao.get(map);
	}

	public Integer post(Map<String,Object> map) {
		return dashboardGroupDao.post(map);
	}

	public void put(String userid, Integer groupid, String groupname) {
		dashboardGroupDao.put(userid,groupid,groupname);
	}

	public void delete(String userid, Integer groupid)
	{
		dashboardGroupDao.delete(userid,groupid);
	}

}
