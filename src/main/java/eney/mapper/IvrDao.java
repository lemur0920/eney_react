package eney.mapper;

import java.util.List;
import java.util.Map;


import eney.DatasourceConfig;
import eney.domain.CallLogVO;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import eney.DatasourceConfig;
import eney.domain.CallLogVO;

@Repository
public class IvrDao{
	
	private static final Logger logger = LoggerFactory.getLogger(IvrDao.class);
	
	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;
	
	/**
	 * 콜로그 리스트 조회
	 * @param callLogVO
	 * @return 콜 로그 리스트
	 */
	public List<CallLogVO> getCallLogList(CallLogVO callLogVO) {
		System.out.println("콜로그 시작");
		long startTime = System.currentTimeMillis();
		List<CallLogVO> callLogList = sqlSession.selectList("ivr.getCallLogList", callLogVO);
		long endTime = System.currentTimeMillis();
		logger.warn("콜로그 조회 쿼리문  걸린 시간  = " +( endTime - startTime )/1000.0f +"초");
		
		return callLogList;
	}

	public List<CallLogVO> getCallLogListApi01(CallLogVO callLogVO) {
		System.out.println("콜로그 시작");
		long startTime = System.currentTimeMillis();
		List<CallLogVO> callLogList = sqlSession.selectList("ivr.getCallLogListApi01", callLogVO);
		System.out.println("2222");
		System.out.println(callLogList);
		long endTime = System.currentTimeMillis();
		logger.warn("콜로그 조회 쿼리문  걸린 시간  = " +( endTime - startTime )/1000.0f +"초");

		return callLogList;
	}

	public List<String> getCallLogListAll(CallLogVO callLogVO) {
		long startTime = System.currentTimeMillis();
		List<String> callLogList = sqlSession.selectList("ivr.getCallLogListAll", callLogVO);
		long endTime = System.currentTimeMillis();
		logger.warn("콜로그 조회 쿼리문  걸린 시간  = " +( endTime - startTime )/1000.0f +"초");

		return callLogList;
	}

	public CallLogVO getCallLog(Map<String,Object> map) {
		return sqlSession.selectOne("ivr.getCallLog", map);
	};


	public int insertMemo(Map<String, Object> map) {
		return (Integer)sqlSession.insert("ivr.insertMemo", map);
	}

    public int updateMemo(Map<String, Object> map) {
        return (Integer)sqlSession.insert("ivr.updateMemo", map);
    }

	public int deleteMemo(Map<String, Object> map) {
		return (Integer)sqlSession.delete("ivr.deleteMemo", map);
	}

	public Map<String,Object> getMemo(Map<String, Object> map) {
		return sqlSession.selectOne("ivr.getMemo", map);
	}


	/**
	 * 콜 로그 개수 조회
	 * @param callLogVO
	 * @return
	 */
	public int getCallLogCnt(CallLogVO callLogVO) {
		return (Integer)sqlSession.selectOne("ivr.getCallLogCnt", callLogVO);
	}

	public int getCallLogCnt01(CallLogVO callLogVO) {
		return (Integer)sqlSession.selectOne("ivr.getCallLogCnt01", callLogVO);
	}

	public int getCallLogCntForAPI(CallLogVO callLogVO) {
		return (Integer)sqlSession.selectOne("ivr.getCallLogCntForAPI", callLogVO);
	}
	
	/* 교환기 상태 체크 */
	
	/**
	 * 최근 50건의 교환 상태를 체크
	 * @param month
	 * @return
	 */
	public Map<String, Long> getLast50CdrSummary(String month){
		return sqlSession.selectOne("ivr.getLast50CdrSummary", month);
	}

	public List<CallLogVO> getCallLogListSetTime(CallLogVO callLogVO) {
		long startTime = System.currentTimeMillis();
		List<CallLogVO> callLogList = sqlSession.selectList("ivr.getCallLogListSetTime", callLogVO);
		long endTime = System.currentTimeMillis();
		logger.warn("콜로그 조회 쿼리문  걸린 시간  = " +( endTime - startTime )/1000.0f +"초");

		return callLogList;
	}
}
