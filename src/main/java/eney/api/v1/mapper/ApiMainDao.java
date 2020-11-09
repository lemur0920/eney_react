package eney.api.v1.mapper;

import java.util.List;

import eney.api.v1.domain.ApiLogVo;
import eney.api.v1.domain.ApiTokenVo;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import eney.DatasourceConfig;
import eney.api.v1.domain.ApiLogVo;
import eney.api.v1.domain.ApiTokenVo;

@Repository
public class ApiMainDao{
	
	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;
	
	/** 토큰 관련 부분 **/
	/**
	 * 토큰 정보 등록
	 * @param apiTokenInfo 등록할 토큰 정보
	 * @return 토큰 IDX
	 */
	public Integer insertTokenInfo(ApiTokenVo apiTokenInfo){
		return sqlSession.insert("api-v1-main.insertTokenInfo", apiTokenInfo);
	}
	
	public Integer insertMessageTokenInfo(ApiTokenVo apiTokenInfo) {
		return sqlSession.insert("api-v1-main.insertMessageTokenInfo", apiTokenInfo);
	}
	
	
	/**
	 * 토큰정보 조회
	 * @param query 검색 조건
	 * @return 토큰 정보
	 */
	public ApiTokenVo selectTokenInfo(ApiTokenVo query){
		return (ApiTokenVo)sqlSession.selectOne("api-v1-main.selectTokenInfoQuery", query);
	}
	
	public ApiTokenVo selectMessageTokenInfo(ApiTokenVo query){
		return (ApiTokenVo)sqlSession.selectOne("api-v1-main.selectMessageTokenInfoQuery", query);
	}
	
	
	/**
	 * 토큰정보 리스트 조회
	 * @param query 검색 조건
	 * @return 토큰 정보
	 */
	public List<ApiTokenVo> selectTokenInfoList(ApiTokenVo query){
		return sqlSession.selectList("api-v1-main.selectTokenInfoQuery", query);
	}
	
	public List<ApiTokenVo> selectMessageTokenInfoList(ApiTokenVo query){
		return sqlSession.selectList("api-v1-main.selectMessageTokenInfoQuery", query);
	}

	public List<ApiTokenVo> selectTokenAllInfoList(ApiTokenVo query){
		return sqlSession.selectList("api-v1-main.selectTokenAllInfoList", query);
	}

	public int selectTokenAllInfoListCnt(ApiTokenVo query){
		return (Integer) sqlSession.selectOne("api-v1-main.selectTokenAllInfoListCnt", query);
	}
	
		
	/**
	 * 토큰정보 수정
	 * @param query query
	 * @return 수정된 레코드 수
	 */
	public int updateTokenInfo(ApiTokenVo query){
		return sqlSession.update("api-v1-main.updateTokenInfo", query);
	}
	/*api 로그 등록*/
	public void insertApiLog(ApiLogVo query) {
		sqlSession.insert("api-v1-main.insertApiLog", query);
	}

	
	
}
