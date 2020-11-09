package eney.mapper;

import eney.DatasourceConfig;
import eney.domain.IvrStatusLogVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import eney.DatasourceConfig;
import eney.domain.IvrStatusLogVo;

@Repository
public class LogDao{
	
	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;
	
	/**
	 * 교환기 상태 로그 등록
	 * @param switchStatusLogData
	 * @return
	 */
	public Integer insertIvrStatusLog(IvrStatusLogVo switchStatusLogData){
		return sqlSession.insert("log.insertIvrStatusLog", switchStatusLogData);
	}
	
	/**
	 * 로그 출력(status_code가 '01','02'인 것중 최근 1개
	 * @return 로그
	 */
	public IvrStatusLogVo selectIvrStatus(){
		return sqlSession.selectOne("log.selectIvrStatus");
	}
}
