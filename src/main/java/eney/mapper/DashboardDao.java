package eney.mapper;

import eney.DatasourceConfig;
import eney.DatasourceConfig;
import eney.domain.DashboardDataVO;
import eney.domain.DashboardGraphDataVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DashboardDao {
	
	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;
	

	public List<Map<String,Object>> gets(Map<String,Object> map){
		return sqlSession.selectList("dashboard.gets",map);
	}
	public Map<String,Object> get(Map<String,Object> map) {
	    return sqlSession.selectOne("dashboard.get",map);
	}

	public Integer post(Map<String,Object> map) {
		return sqlSession.insert("dashboard.post",map);

	}

	public void put(String userid, Integer id, String data) {

		Map<String,Object> map = new HashMap<>();

		map.put("userid",userid);
		map.put("id",id);
		map.put("data",data);

		sqlSession.update("dashboard.put",map);
	}

	public void delete(String userid, Integer id) {

		Map<String,Object> map = new HashMap<>();

		map.put("userid",userid);
		map.put("id",id);
		sqlSession.update("dashboard.delete",map);
	}

    public List<Map<String,Object>> GetsByUserID(String userid) {
		return sqlSession.selectList("dashboard.GetsByUserID",userid);
    }

    //특정 유저 전체 Call Count 조회
    public DashboardDataVO getCompareDate(Map<String,Object> map){
        return sqlSession.selectOne("dashboard.getCompareDate",map);
    }

    //특정 유저 CallResult 기준 Call Count 조회
    public Integer getResultByCallCount(Map<String,Object> map){
        return sqlSession.selectOne("dashboard.getResultByCallCount",map);
    }

    //특정 유저,기간 Call Count 조회
    public List<DashboardGraphDataVO> getDateByCallCount(Map<String,Object> map){
        return sqlSession.selectList("dashboard.getDateByCallCount",map);
    }

	//특정 유저,날짜 시간별 Call Count 조회
	public List<Map<String,Object>> getTimeByCallCount(Map<String,Object> map){
		return sqlSession.selectList("dashboard.getTimeByCallCount",map);
	}


	public List<Map<String,Object>> getTimeAvgCallCount(Map<String,Object> map){
		return sqlSession.selectList("dashboard.getTimeAvgCallCount",map);
	}

	//재방문율 조회
	public Map<String,Object> getRevisitTypeCount(Map<String,Object> map){
		return sqlSession.selectOne("dashboard.getRevisitTypeCount",map);
	}

	//콜 디바이스 유형 조회
	public Map<String,Object> getCallDeviceTypeCount(Map<String,Object> map){
		return sqlSession.selectOne("dashboard.getCallDeviceTypeCount",map);
	}

	//가맹점 랭킹 조회
	public List<Map<String,Object>> getAgentRanking(Map<String,Object> map){
		return sqlSession.selectList("dashboard.getAgentRanking",map);
	}

	//지역별 랭킹 조회
	public List<Map<String,Object>> getLocationRanking(Map<String,Object> map){
		return sqlSession.selectList("dashboard.getLocationRanking",map);
	}

	//Call Ratio keyword
	public List<Map<String,Object>> getCallRatioKeyword(Map<String,Object> map){
		return sqlSession.selectList("dashboard.getCallRatioKeyword",map);
	}

	//Call Ratio source
	public List<Map<String,Object>> getCallRatioSource(Map<String,Object> map){
		return sqlSession.selectList("dashboard.getCallRatioSource",map);
	}

	//Call Ratio os
	public List<Map<String,Object>> getCallRatioOS(Map<String,Object> map){
		return sqlSession.selectList("dashboard.getCallRatioOS",map);
	}

	//Call Ratio browser
	public List<Map<String,Object>> getCallRatioBrowser(Map<String,Object> map){
		return sqlSession.selectList("dashboard.getCallRatioBrowser",map);
	}
}
