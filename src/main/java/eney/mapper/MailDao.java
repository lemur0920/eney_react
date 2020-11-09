package eney.mapper;

import java.util.List;

import eney.DatasourceConfig;
import eney.domain.MailManagerVo;
import eney.domain.UserVO;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import eney.DatasourceConfig;
import eney.domain.MailManagerVo;
import eney.domain.UserVO;

@Repository
public class MailDao{
	
	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;
	
	/**
	 * 메일 관리 정보 생성
	 * @param insertMailManagerInfo
	 * @return
	 */
	public Integer insertMailManagerInfo(MailManagerVo insertMailManagerInfo){
		return sqlSession.insert("mail.insertMailManagerInfo", insertMailManagerInfo);
	}
	
	/**
	 * 메일 관리 정보 수정
	 * @param updateMailManagerInfo
	 * @return
	 */
	public Integer updateMailManagerInfo(MailManagerVo updateMailManagerInfo){
		return sqlSession.update("mail.updateMailManagerInfo", updateMailManagerInfo);
	}
	
	/**
	 * 메일 관리 정보 조회
	 * @param selectQuery
	 * @return
	 */
	public MailManagerVo selectMailManagerInfo(MailManagerVo selectQuery){
		return (MailManagerVo) sqlSession.selectOne("mail.selectMailManagerInfo", selectQuery);
	}
	
	/**
	 * 메일 관리 정보 조회
	 * @param mailIdx 메일 IDX
	 * @return
	 */
	public MailManagerVo selectMailManagerInfo(Integer mailIdx){
		MailManagerVo selectQuery = new MailManagerVo();
		selectQuery.setMail_idx(mailIdx);
		return selectMailManagerInfo(selectQuery);
	}
	
	/**
	 * 조건에 맞는 항목 수 조회
	 * @param selectQuery
	 * @return
	 */
	public Integer getMailManagerInfoCnt(MailManagerVo selectQuery){
		return (Integer) sqlSession.selectOne("mail.getMailManagerInfoCnt", selectQuery);
	}
	
	/**
	 * 메일 관리 정보 리스트 조회
	 * @param selectQuery
	 * @return
	 */
	public List<MailManagerVo> selectMailManagerInfoList(MailManagerVo selectQuery){
		return sqlSession.selectList("mail.selectMailManagerInfo", selectQuery);
	}
	
	/**
	 * 메일을 보낼 대상자 목록 조회
	 * @param selectQuery
	 * @return
	 */
	public List<UserVO> getMailTargetInfo(UserVO selectQuery){
		return sqlSession.selectList("mail.getMailTargetInfo", selectQuery);
	}
}
