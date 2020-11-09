package eney.mapper;

import eney.DatasourceConfig;
import eney.domain.ServiceBIVO;
import eney.domain.UserVO;
import eney.DatasourceConfig;
import eney.domain.ServiceBIVO;
import eney.domain.UserVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class BatchDao {
	
	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;
	
	public List<Map<String,Object>> getTableList() {
		return sqlSession.selectList("batch.getTableList");
	}

	public void insertGAData(Map<String,Object> map) {

		sqlSession.insert("batch.insertGAData", map);
	}

	public LinkedHashMap<String, String> getCompareDate(){
		return sqlSession.selectOne("batch.getCompareDate");
	}

    public List<ServiceBIVO> getServiceBIList() {
		return sqlSession.selectList("batch.getServiceBIList");
    }

    public List<Map<String, Object>> getGAUserInfoList(UserVO session) {
    	return sqlSession.selectList("batch.getGAUserInfoList", session);
	}

	public Map<String,Object> getComValue(Map<String,Object> map) {
		return sqlSession.selectOne("batch.getComValue", map);
	}

	public Integer getValue(Map<String,Object> searchMap) {
		return sqlSession.selectOne("batch.getValue", searchMap);
	}

	public Map<String,Object> getAvgValue(Map<String,Object> searchMap) {
		return sqlSession.selectOne("batch.getAvgValue", searchMap);
	}

	public Map<String,Object> getSumValue(Map<String,Object> searchMap) {
		return sqlSession.selectOne("batch.getSumValue", searchMap);
	}

    public List<Map<String,Object>> callProcedure(Map<String,Object> searchMap) {
		return sqlSession.selectList("batch.callProcedure", searchMap);
    }

	public Map<String,Object> getTable(Map<String,Object> map) {
		return sqlSession.selectOne("batch.getTable", map);
	}
}
