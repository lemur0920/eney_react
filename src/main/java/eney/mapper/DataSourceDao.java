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
public class DataSourceDao {
	
	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;


	public List<Map<String,Object>> GetTables(String userid) {
		return sqlSession.selectList("datasource.GetTables",userid);
	}

	public List<Map<String,Object>> getFields(String tablename) {
		return sqlSession.selectList("datasource.GetFields",tablename);
	}

	public List<Map<String,Object>> getDatas(Map<String, Object> map) {
		return sqlSession.selectList("datasource.GetDatas",map);
	}

	public List<Map<String,Object>> getBatchDatas(Map<String, Object> map) {
		return sqlSession.selectList("datasource.GetBatchDatas",map);
	}
}
