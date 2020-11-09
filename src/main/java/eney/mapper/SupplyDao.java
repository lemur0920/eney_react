package eney.mapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import eney.DatasourceConfig;
import eney.domain.*;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import eney.DatasourceConfig;


@Repository
public class SupplyDao{
	
	private static final Logger logger = LoggerFactory.getLogger(SupplyDao.class);

	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;
	
	@Resource
	MainDao mainDao;
	
	/**
	 * 컬러링 샘플 리스트 출력
	 * @param sampleVO
	 * @return
	 */
	public List<ColoringSampleVO> getSampleList(ColoringSampleVO sampleVO) {
		return sqlSession.selectList("service.getSampleList",sampleVO);
	}

	/**
	 * 모비엠 컬러링 신청
	 * @param registerVO
	 */
	public void insertColoringRegister(ColoringRegisterVO registerVO) {
		sqlSession.insert("service.submitColoringRegister",registerVO);
	}

	/**
	 * 컬러링 업로드
	 * @param uploadVO
	 * @return
	 */
	public int submitColoringUpload(ColoringUploadVO uploadVO) {
		return (Integer)sqlSession.insert("service.submitColoringUpload",uploadVO);
	}


	/**
	 * 아이디 별 컬러링 리스트
	 * @param uploadVO
	 * @return
	 */
	public List<ColoringUploadVO> getUploadList(ColoringUploadVO uploadVO) {
		return sqlSession.selectList("service.getUploadList",uploadVO);
	}

	public List<ColoringUploadVO> getRcvmentList(ColoringUploadVO uploadVO) {
		return sqlSession.selectList("service.getRcvmentList",uploadVO);
	}

	/**
	 * 등록된 컬러링 리스트 출력 (모비엠)
	 * @param param
	 * @return
	 */
	public List<ColoringRegisterVO> getColoringRegisterVOList(ColoringRegisterVO param) {
		return sqlSession.selectList("service.getColoringRegisterVOList",param);
	}

	/**
	 * 모비엠 컬러링 상태 업데이트
	 * @param registerVO
	 */
	public void updateColoringRegisterVO(ColoringRegisterVO registerVO) {
		sqlSession.update("service.updateColoringRegisterVO",registerVO);
	}

	/**
	 * 업로드 된 컬러링 갯수
	 * @param uploadVO
	 */
	public int getUploadListCnt(ColoringUploadVO uploadVO) {
		return (Integer)sqlSession.selectOne("service.getUploadListCnt", uploadVO);
	}

	/**
	 * 컬러링 업로드 내역 삭제 -> remark를 deleted로 변경
	 * @param uploadVO
	 */
	public void coloringUploadDelete(ColoringUploadVO uploadVO) {
		sqlSession.delete("service.coloringUploadDelete", uploadVO);
	}
	
	//TODO 쿼리 수정해야함!
	/**
	 * 모비엠 컬러링 삭제(상태를 deleted로)
	 * @param uploadVO
	 */
	public void rcvmentUploadDelete(ColoringUploadVO uploadVO) {
		sqlSession.delete("service.rcvmentUploadDelete", uploadVO);
	}

	/**
	 * 공통 코드 리스트
	 * @param commonVO
	 * @return
	 */
	public List<CommonVO> getCommonList(CommonVO commonVO) {
		return sqlSession.selectList("service.getCommonList", commonVO);
	}
	/**
	 * 공통코드 업데이트
	 * @param commonVO
	 * @return
	 */
	public int updateCommon(CommonVO commonVO){
		return sqlSession.update("service.updateCommon", commonVO);
	}
	/**
	 * 공통코드 삭제
	 * @param commonVO
	 * @return
	 */
	public int deleteCommon(CommonVO commonVO){
		return sqlSession.delete("service.deleteCommon", commonVO);
	}

	/**
	 * 컬러링 리스트 출력
	 * @param param userid
	 */
	public List<Map<String, Object>> getColoringListAll(ColoringUploadVO param) {
		return sqlSession.selectList("service.getColoringListAll", param);
	}
	
	/**
	 * 착신멘트 리스트 출력 
	 * @param param userid
	 */
	public List<Map<String, Object>> getRcvmentListAll(ColoringUploadVO param) {
		return sqlSession.selectList("service.getRcvmentListAll", param);
	}

	/**
	 * 사용중인지 미사용중인지 표시
	 * @param vnoVO
	 * @return
	 */
	public VnoVO getVnoVO(VnoVO vnoVO) {
		return (VnoVO)sqlSession.selectOne("service.getVnoVO", vnoVO);
	}
	public VnoVO getVnoVO22(VnoVO vnoVO) {
		return (VnoVO)sqlSession.selectOne("service.getVnoVO22", vnoVO);
	}
	

	/**
	 * 추천 번호 리스트
	 * @param vnoVO
	 * @return
	 */
	public List<VnoVO> getRecommendNumList(VnoVO vnoVO) {
		return sqlSession.selectList("service.getRecommendNumList", vnoVO);
	}

	/**
	 * 050 번호 등록
	 * @param agentVO
	 */
	public void submit050Register(AgentVO agentVO) {
		sqlSession.insert("service.submit050Register",agentVO);
	}

	/**
	 * 050번호와 사용여부 수정
	 * @param agentVO
	 */
	public void update050VnoVO(AgentVO agentVO) {
		sqlSession.update("service.update050VnoVO",agentVO);	
	}

//	@SuppressWarnings("unchecked")
//	public List<CallLogVO> getCallLogList(CallLogVO callLogVO) {
//		return (List<CallLogVO>)sqlSession.selectList("switch.getCallLogList", callLogVO);
//	}
//
//	public int getCallLogCnt(CallLogVO callLogVO) {
//		return (Integer)sqlSession.selectOne("switch.getCallLogCnt", callLogVO);
//	}
	
	/**
	 * 가맹점 리스트 출력 및 검색
	 * @param agentVO
	 * @return 가맹점 리스트
	 */
	public List<AgentVO> getAgentVOList(AgentVO agentVO) {
		long startTime = System.currentTimeMillis();
		List<AgentVO> agentList = sqlSession.selectList("service.getAgentVOList", agentVO);
		long endTime = System.currentTimeMillis();
		logger.warn("사용중인 번호 목록 쿼리문  걸린 시간  = " +( endTime - startTime )/1000.0f +"초");
		return agentList;
	}

	public List<AgentVO> getAgentVOListForService(AgentVO agentVO) {
		long startTime = System.currentTimeMillis();
		List<AgentVO> agentList = sqlSession.selectList("service.getAgentVOListForService", agentVO);
		long endTime = System.currentTimeMillis();
		logger.warn("사용중인 번호 목록 쿼리문  걸린 시간  = " +( endTime - startTime )/1000.0f +"초");
		return agentList;
	}

	
	public List<AgentVO> getAgentVOListUnion(AgentVO agentVO) {
		long startTime = System.currentTimeMillis();
		List<AgentVO> agentList = sqlSession.selectList("service.getAgentVOListUnion", agentVO);
		long endTime = System.currentTimeMillis();
		logger.warn("사용중인 번호 목록(View) 쿼리문  걸린 시간  = " +( endTime - startTime )/1000.0f +"초");
		return agentList;
	}
	
	public List<AgentVO> getAgent(AgentVO agentVO) {
		long startTime = System.currentTimeMillis();
		List<AgentVO> agentList = sqlSession.selectList("service.getAgent", agentVO);
		long endTime = System.currentTimeMillis();
		logger.warn("사용중인 번호 목록(SK) 쿼리문  걸린 시간  = " +( endTime - startTime )/1000.0f +"초");
		Map<String,String> map = new HashMap<>();
		map.put("type", "사용중인 번호목록(SK)쿼리문");
		map.put("time", ( endTime - startTime )/1000.0f +"초");
		mainDao.insertTimeMeasure(map);
		return agentList;
	}
	
	public List<AgentVO> getAgent22(AgentVO agentVO) {
		long startTime = System.currentTimeMillis();
		List<AgentVO> agentList = sqlSession.selectList("service.getAgent22", agentVO);
		long endTime = System.currentTimeMillis();
		logger.warn("사용중인 번호 목록(SEJONG) 쿼리문  걸린 시간  = " +( endTime - startTime )/1000.0f +"초");
		Map<String,String> map = new HashMap<>();
		map.put("type", "사용중인 번호목록(SEJONG)쿼리문");
		map.put("time", ( endTime - startTime )/1000.0f +"초");
		mainDao.insertTimeMeasure(map);
		return agentList;
	}
	
	

	/**
	 * 검색값에 맞는 050 번호 개수 출력
	 * @param agentVO
	 * @return
	 */
	public int getAgentCnt(AgentVO agentVO) {
		return (Integer)sqlSession.selectOne("service.getAgentCnt", agentVO);
	}

	/**
	 * 해당 아이디의 agentVO 출력
	 * @param agentVO
	 * @return
	 */
	public AgentVO getAgentVO(AgentVO agentVO) {
		return (AgentVO)sqlSession.selectOne("service.getAgentVO", agentVO);
	}
	
	public AgentVO getAgentVO22(AgentVO agentVO) {
		return (AgentVO)sqlSession.selectOne("service.getAgentVO22", agentVO);
	}

	/**
	 * 연결되어있는 050 번호 수정
	 * @param agentVO
	 */
	public void update050Agent(AgentVO agentVO) {
		sqlSession.update("service.update050Agent",agentVO);
	}

	/**
	 * 결제 카테고리 리스트 출력
	 * @param paymentVO 카테고리
	 * @return
	 */
	public List<PaymentVO> getPaymentList(PaymentVO paymentVO) {
		return sqlSession.selectList("service.getPaymentList", paymentVO);
	}

	public List<PaymentVO> getPaymentListWithCate(PaymentVO paymentVO) {
		return sqlSession.selectList("service.getPaymentListWithCate", paymentVO);
	}

	/**
	 * 050 번호 조회
	 * @param agentVO
	 * @return
	 */
	public VnoVO getVnoVO(AgentVO agentVO) {
		return (VnoVO)sqlSession.selectOne("service.getVnoVoByAgent",agentVO);
	}
	public VnoVO getVnoVO22(AgentVO agentVO) {
		return (VnoVO)sqlSession.selectOne("service.getVnoVoByAgent22",agentVO);
	}


	public void update050VnoForCancel(AgentVO agentVO) {
		sqlSession.update("service.update050VnoForCancel", agentVO);
	}
	public void update050VnoForCancel22(AgentVO agentVO) {
		sqlSession.update("service.update050VnoForCancel22", agentVO);
	}
	
	public void update050VnoForCancelByVno(AgentVO agentVO) {
		sqlSession.update("service.update050VnoForCancelByVno", agentVO);
	}
	public void update050VnoForCancelByVno22(AgentVO agentVO) {
		sqlSession.update("service.update050VnoForCancelByVno22", agentVO);
	}

	public void deleteAgent(AgentVO agentVO) {
		sqlSession.delete("service.deleteAgent",agentVO);
	}
	
	public void deleteAgentByVno(AgentVO agentVO) {
		sqlSession.delete("service.deleteAgentByVno",agentVO);
	}
	
	public void deleteAgentByVno22(AgentVO agentVO) {
		sqlSession.delete("service.deleteAgentByVno22",agentVO);
	}
	
	public void deleteAgent22(AgentVO agentVO) {
		sqlSession.delete("service.deleteAgent22",agentVO);
	}

	public void deleteAgentDelByVno(AgentVO agentVO) {
		sqlSession.delete("service.deleteAgentDelByVno",agentVO);
	}
	
	public List<AgentVO> getExpiredAgentList() {
		return sqlSession.selectList("service.getExpiredAgentList");
	}

	public void writeRefundLog(EpointRefundVO refundVO) {
		sqlSession.insert("service.writeRefundLog",refundVO);
	}

	public int getRefundListCnt(EpointRefundVO paramVO) {
		return (Integer)sqlSession.selectOne("service.getRefundListCnt", paramVO);
	}

	public List<EpointRefundVO> getRefundList(EpointRefundVO paramVO) {
		return sqlSession.selectList("service.getRefundList", paramVO);
	}

	public ColoringUploadVO getColoringUploadVO(ColoringUploadVO paramVO) {
		return (ColoringUploadVO)sqlSession.selectOne("service.getColoringUploadVO", paramVO);
	}

	public void insert050AgentBeExpired(Map<String, Object> map) {
		sqlSession.insert("service.insert050AgentBeExpired", map);
	}

	public ColoringUploadVO getRcvmentUploadVO(ColoringUploadVO colorringVo) {
		return (ColoringUploadVO)sqlSession.selectOne("service.getRcvmentUploadVO", colorringVo);
	}

	//신규 교환기
	public void submit050Register22(AgentVO agentVO) {
		sqlSession.insert("service.submit050Register22",agentVO);
	}
	public void update050VnoVO22(AgentVO agentVO) {
		sqlSession.update("service.update050VnoVO22",agentVO);	
	}
	public Integer update050Agent22(AgentVO agentVO) {
		return sqlSession.update("service.update050Agent22",agentVO);
	}

	public List<VnoVO> getRecommendNumList22(VnoVO vnoVO) {
		return sqlSession.selectList("service.getRecommendNumList22", vnoVO);
	}

	public void update050Agent22ByVno(AgentVO agentVO) {
		sqlSession.update("service.update050Agent22ByVno",agentVO);
	}
	
	public Integer getAgentVOListCnt(AgentVO agentVO){
		return sqlSession.selectOne("service.getAgentVOListCnt",agentVO);
	}

	public Integer getAgentListCnt(AgentVO agentVO){
		return sqlSession.selectOne("service.getAgentListCnt",agentVO);
	}

	public void skbUseNumber(Map<String,Integer> map) {
		sqlSession.update("service.skbUseNumber",map);
		
	}

	public List<ServiceListVO> getUserServiceList(ServiceListVO serviceListVO) {
		
		return sqlSession.selectList("service.getUserServiceList",serviceListVO);
	}


    public Integer getUserServiceCount(ServiceListVO serviceListVO) {
		return sqlSession.selectOne("service.getUserServiceCount",serviceListVO);
    }

    public List<String> getAgentVOListAll(AgentVO agentVO) {
		return sqlSession.selectList("service.getAgentVOListAll", agentVO);
    }


	public AgentVO getAgentVOByVno(String vno) {
		return (AgentVO)sqlSession.selectOne("service.getAgentVOByVno", vno);
	}

	public AgentVO getAgentVOByVno22(String vno) {
		return (AgentVO)sqlSession.selectOne("service.getAgentVOByVno22", vno);
	}

	//해당 vno 주소 검색
	public AgentAddressVO getAgentAddress(String vno) {
		return sqlSession.selectOne("service.getAgentAddress", vno);
	}

	//agent 주소 업데이트
	public void updateAgentAddress(AgentAddressVO agentAddress) {
		sqlSession.update("service.updateAgentAddress", agentAddress);
	}

//	public void bulkUpdate0507Agent(AgentVO agentVOList) {
//		sqlSession.update("service.bulkUpdate0507Agent", agentVOList);
//	}
//
//	public void bulkUpdate0506Agent(AgentVO agentVOList) {
//		sqlSession.update("service.bulkUpdate0506Agent", agentVOList);
//	}

	public void bulkUpdate0507Agent(List<AgentVO> agentVOList) {
		sqlSession.update("service.bulkUpdate0507Agent", agentVOList);
	}

	public void bulkUpdate0506Agent(List<AgentVO> agentVOList) {
		sqlSession.update("service.bulkUpdate0506Agent", agentVOList);
	}

}
