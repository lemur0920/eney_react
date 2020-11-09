package eney.mapper;

import eney.DatasourceConfig;
import eney.DatasourceConfig;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class VisualizationDao {
	
	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;
	

	public List<Map<String,Object>> gets(String userid){

		return sqlSession.selectList("visual.gets",userid);
	}
	public Map<String,Object> get(String userid, Integer id) {
		Map<String,Object> map = new HashMap<>();

		map.put("userid",userid);
		map.put("id",id);

		Map<String,Object> map2 = sqlSession.selectOne("visual.get",map);


		return map2;

	}

    public Integer post(Map<String, Object> map) {
		return sqlSession.insert("visual.post",map);
    }

	public void put(Map<String, Object> map) {
		sqlSession.update("visual.put",map);
	}

	public void delete(Map<String, Object> map) {
		sqlSession.update("visual.delete",map);
	}
}
