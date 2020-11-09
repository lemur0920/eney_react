package eney.mapper;

import eney.DatasourceConfig;
import eney.domain.UserVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class JoinDao{
	
	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;
	
	public int insertJoinCheckCorporate(UserVO userVO) {
		return (Integer)sqlSession.insert("join.insertJoinCheckCorporate",userVO);
	}
	
	/**
	 * 회원 유형 - 개인
	 * @param userVO
	 * @return
	 */
	public int insertJoinPersonal(UserVO userVO) {
		return (Integer)sqlSession.insert("join.insertJoinPersonal",userVO);
	}
	/**
	 * 회원가입 - 기본정보
	 * @param userVO
	 */
	public void insertJoinerInfo(UserVO userVO) {
		sqlSession.insert("join.insertJoinerInfo",userVO);
	}
	
	//TODO mapper 존재 X
	public UserVO getUserVOByIdx(int idx) {
		return (UserVO)sqlSession.selectOne("join.getUserVOByIdx",idx);
	}

	/**
	 * epoint 정보 변경 후 회원 정보 출력
	 * @param userVO
	 */
	public UserVO updateUserInfo(UserVO userVO) {
		sqlSession.update("join.updateUserInfo", userVO);
		return (UserVO)sqlSession.selectOne("user.getUserInfo", userVO); 
	}
	
	/**
	 * 사업자등록번호로 회원 수 세기
	 * @param corporate_number 사업자등록번호
	 * @return 회원 수
	 */
	public Integer getUserCntByCorpNumber(String corporate_number) {
		return (Integer)sqlSession.selectOne("join.getUserCntByCorpNumber",corporate_number);
	}

	/**
	 * 패치콜 권한, epoint가 들어가는 테이블에 컬럼 추가
	 * @param userVO userid
	 */
	public void insertJoinerUserInfo(UserVO userVO) {
		sqlSession.insert("join.insertJoinerUserInfo",userVO);
	}
	
	/**
	 * 본인인증 코드를 사용하기 위해 입력되는 테이블에 컬럼 추가
	 * @param userVO userid
	 */
	public void insertJoinerIdenInfo(UserVO userVO) {
		sqlSession.insert("join.insertJoinerIdenInfo",userVO);	
	}


	public UserVO getUserByCi(String ci) {
		return (UserVO)sqlSession.selectOne("join.getUserCntByCi", ci);
	}
	
}
