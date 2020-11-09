package eney.mapper;

import eney.DatasourceConfig;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DashboardGroupDao {
	
	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;
	

	public List<Map<String,Object>> gets(String userid) {

		return sqlSession.selectList("dashboardGroup.gets",userid);
	}
	public List<Map<String,Object>> get(Map<String,Object> map) {

		return sqlSession.selectList("dashboardGroup.get",map);
	}

	public Integer post(Map<String,Object> map) {

		return sqlSession.insert("dashboardGroup.post",map);

	}

	public void put(String userid, Integer id, String groupname) {

		Map<String,Object> map = new HashMap<>();

		map.put("userid",userid);
		map.put("id",id);
		map.put("groupname",groupname);

		sqlSession.update("dashboardGroup.put",map);
	}

	public void delete(String userid, Integer id) {

		Map<String,Object> map = new HashMap<>();

		map.put("userid",userid);
		map.put("id",id);
		sqlSession.update("dashboardGroup.delete",map);

	}

}
