package eney.mapper;

import java.util.List;
import java.util.Map;

import eney.domain.ServiceBIVO;
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
public class AdminDao{
	
	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;
	
	private static final Logger logger = LoggerFactory.getLogger(AdminDao.class);
	
	/**
	 * 가입된 회원 리스트 출력
	 * @param userVO
	 * @return 회원 리스트
	 */
	public List<UserVO> getUserList(UserVO userVO) {
		return sqlSession.selectList("admin.getUserList", userVO);
	}
	
	/**
	 * 회원 번호를 가지고 회원 정보 가져오기
	 * @param paramVO
	 * @return 회원 정보
	 */
	public UserVO getUserInfo(UserVO paramVO) {
		return (UserVO)sqlSession.selectOne("admin.getUserInfo", paramVO);
	}
	/**
	 * 회원정보 수정(유형, 이름, 휴대폰번호, 이메일)
	 * @param updateVO
	 */
	public void updateUserInfo(UserVO updateVO) {
		sqlSession.update("admin.updateUserInfo", updateVO);
	}
	/**
	 * 회원정보 수정(서비스 권한)
	 * @param updateVO
	 */
	public void updateUserInfoTB(UserVO updateVO) {
		sqlSession.update("admin.updateUserInfoTB", updateVO);
	}
	
	/**
	 * 인보이스 정보 등록
	 * @param userInvoiceVO
	 */
	public int insertInvoice(UserInvoiceVO userInvoiceVO) {
		return sqlSession.insert("admin.insertInvoice", userInvoiceVO);
	}
	/**
	 * 홈텍스 정보 등록
	 * @param userVO
	 */
	public void insertHometax(UserHometaxVO userHometaxVO) {
		sqlSession.insert("admin.insertHometax", userHometaxVO);
	}
	public int getHometaxIdx(Integer invoice_idx){
		return sqlSession.selectOne("admin.getHometaxIdx", invoice_idx);
	}
	/**
	 * 홈택스 등록 후 invoice.hometax_idx 업데이트
	 * @param hometax_idx
	 */
	public void upDateInvoiceHometaxIdx(UserInvoiceVO userInvoiceVO) {
		sqlSession.update("admin.upDateInvoiceHometaxIdx", userInvoiceVO);
	}
	/**
	 * 회원 리스트 조회
	 * @param userVO
	 * @return 회원 리스트
	 */
	public List<UserVO> getSearchIdList(UserVO userVO) {
		return sqlSession.selectList("admin.getSearchIdList", userVO);
	}
	/**
	 * 회원 수 조회
	 * @param userVO
	 * @return 회원 수
	 */
	public int getUserListCnt(UserVO userVO) {		
		return sqlSession.selectOne("admin.getUserListCnt",userVO);
	}
	
	/**
	 * 최근 10일간의 콜 수 조회
	 * @return 날짜와 콜 수
	 */
	public List<Map<String, Object>> getCallCountSummary(){
		return sqlSession.selectList("admin.getCallCountSummary");
	}

	/**
	 * 매핑된 패치콜 번호 개수
	 * @return 매핑된 패치콜 개수
	 */
	public int getMappedPatchcallAmount() {
		return sqlSession.selectOne("admin.getMappedPatchcallAmount");
	}
	/** *
	 * 최근 일주일간 환불내역 Count
	 * @return
	 */
	
	public int getRefundCount() {
		return sqlSession.selectOne("admin.getRefundCount");
	}
	/**
	 * 최근 일주일간 환불내역 리스트
	 * @return
	 */
	public List<EpointRefundVO> getRefundList(){
		return sqlSession.selectList("admin.getRefundList");
	}
	/**
	 * refund_id에 해당하는 환불신청 정보
	 * @param paramVO
	 * @return
	 */
	public EpointRefundVO getRefundInfo(EpointRefundVO paramVO) {
		return (EpointRefundVO)sqlSession.selectOne("admin.getRefundInfo", paramVO);
	}
	public void updateRefundInfo(EpointRefundVO refundVO) {
	System.out.println("Dao refund_id : " +  refundVO.getRefund_id());
	System.out.println("Dao status : " +  refundVO.getStatus());
		sqlSession.update("admin.updateRefundInfo", refundVO);
	}
	
	public List<Map<String, Object>> getPatchcallAmount() {
		return sqlSession.selectOne("admin.getPatchcallAmount");
	}
	public List<ServicePatchcallVO> getPatchcallList(ServicePatchcallVO servicePatchcallVO) {
		return sqlSession.selectList("admin.getPatchcallList");
	}
	public List<Map<String,String>> getWebHostingList(ServiceWebHostingVO serviceWebHostingVO) {
		return sqlSession.selectList("admin.getWebHostingList");
	}
	public List<ServicePatchcallOtherVO> getPatchcallOtherList(ServicePatchcallOtherVO servicePatchcallOtherVO) {
		return sqlSession.selectList("admin.getPatchcallOtherList",servicePatchcallOtherVO);
	}
	public void updateWebHostingGenerate(Integer web_hosting_idx) {
		sqlSession.update("admin.updateWebHostingGenerate", web_hosting_idx);
	}
	public void updatePatchcallGenerate(Integer patchcall_idx) {
		sqlSession.update("admin.updatePatchcallGenerate", patchcall_idx);
	}
	public void updateBillingStatus(Integer invoice_idx) {
		sqlSession.update("admin.updateBillingStatus", invoice_idx);
	}
	public void updatePatchcallOtherGenerate(Integer idx){
		sqlSession.update("admin.updatePatchcallOtherGenerate",idx);
	}
	public void insertEvent(UserEventVO userEventVO) {
		sqlSession.insert("admin.insertEvent",userEventVO);
	}
	public List<UserEventVO> getUserEvent(String userid) {
		return sqlSession.selectList("admin.getUserEvent",userid);
	}

	public List<UserVO> getUserListAll(UserVO userVO) {
		return sqlSession.selectList("admin.getUserListAll",userVO);
	}
	public int insertInvoiceContent(UserInvoiceVO contentVO) {
		return sqlSession.insert("admin.insertInvoiceContent",contentVO);
	}

	public List<RecordVO> getRecordList(RecordVO recordVO) {
		return sqlSession.selectList("admin.getRecordList",recordVO);
	}
	public List<CallbackSmsVO> getCallbackList(CallbackSmsVO callbackVO) {
		return sqlSession.selectList("admin.getCallbackList",callbackVO);
	}

    public Map<String,Integer> getEnablePatchCallSKB() {
    	long startTime = System.currentTimeMillis();
    	Map<String,Integer> enableSKBNumber = sqlSession.selectOne("admin.getEnablePatchCallSKBAll");
    	long endTime = System.currentTimeMillis();
		logger.warn("Enable SKB CallNumber 쿼리문 걸린 시간  = " +( endTime - startTime )/1000.0f +"초");
		return enableSKBNumber;
    }

	public Map<String,Integer> getEnablePatchCallSeJong() {
		return sqlSession.selectOne("admin.getEnablePatchCallSeJong");
	}

	public List<Map<String,Object>> getTotalCallCount() {
		
		long startTime = System.currentTimeMillis();
		List<Map<String,Object>> totalCallcountMapList = sqlSession.selectList("admin.getTotalCallCount");
		long endTime = System.currentTimeMillis();
		logger.warn("Admin totalCallCount 쿼리문 걸린 시간  = " +( endTime - startTime )/1000.0f +"초");
		return totalCallcountMapList;
	}

	public int getRecordListCnt(RecordVO recordVO) {
		return sqlSession.selectOne("admin.getRecordListCnt",recordVO);
	}
	
	public int getPatchcallListCnt(ServicePatchcallVO patchcallVO) {
		return sqlSession.selectOne("admin.getPatchcallListCnt",patchcallVO);
	}
	
	public int getPatchcallOtherListCnt(ServicePatchcallOtherVO patchcallVO) {
		return sqlSession.selectOne("admin.getPatchcallOtherListCnt",patchcallVO);
	}
	
	public int getWebHostingListCnt(ServiceWebHostingVO webVO) {
		return sqlSession.selectOne("admin.getWebHostingListCnt",webVO);
	}
	
	public int getCallbackListCnt(CallbackSmsVO callbackVO) {
		return sqlSession.selectOne("admin.getCallbackListCnt",callbackVO);
	}
	public ServiceWebHostingVO getWebApplicantList(ServiceWebHostingVO serviceInfoVO) {
		return (ServiceWebHostingVO) sqlSession.selectOne("admin.getWebApplicantList",serviceInfoVO);
	}
	public ServicePatchcallVO getPatchcallApplicantList(ServicePatchcallVO serviceInfoVO) {
		
		return (ServicePatchcallVO) sqlSession.selectOne("admin.getPatchcallApplicantList",serviceInfoVO);
	}
	public ServicePatchcallOtherVO getOtherApplicantsList(ServicePatchcallOtherVO serviceInfoVO) {
		return (ServicePatchcallOtherVO) sqlSession.selectOne("admin.getOtherApplicantsList",serviceInfoVO);
	}

	public List<Map<String, Object>> getApiMesurement() {
		List<Map<String,Object>> monthlyApiMesurementMapList = sqlSession.selectList("admin.getApiMesurement");
		
		return monthlyApiMesurementMapList;
	}

	public List<AgentVO> getEndAgentList(AgentVO agentVO) {
		return sqlSession.selectList("admin.getEndAgentList",agentVO);
	}

	public int getEndAgentListCnt(AgentVO agentVO) {
		return sqlSession.selectOne("admin.getEndAgentListCnt",agentVO);
	}
	public List<UserHometaxVO> getBillingList(UserHometaxVO userHometaxVO) {
		return sqlSession.selectList("admin.getBillingList",userHometaxVO);
	}
	public List<UserHometaxVO> getNoHometaxList(UserInvoiceVO userInvoiceVO) {
		return sqlSession.selectList("admin.getNoHometaxList",userInvoiceVO);
	}

	public void updateNoteContent(ServicePatchcallVO patchcallVO) {
		 sqlSession.update("admin.updateNoteContent",patchcallVO);
	}

	public ServicePatchcallVO getNoteContent(ServicePatchcallVO patchcallVO) {
		return sqlSession.selectOne("admin.getNoteContent", patchcallVO);
	}

	public Map<String, Object> getPatchcallDetail(ServicePatchcallVO patchcallVO) {
		return sqlSession.selectOne("admin.getPatchcallDetail", patchcallVO);
	}

	public void updateWebNoteContent(ServiceWebHostingVO webVO) {
		sqlSession.update("admin.updateWebNoteContent",webVO);
	}

	public ServiceWebHostingVO getWebNoteContent(ServiceWebHostingVO webVO) {
		return sqlSession.selectOne("admin.getWebNoteContent", webVO);
	}

	public Map<String, Object> getWebDetail(ServiceWebHostingVO webVO) {
		return sqlSession.selectOne("admin.getWebDetail", webVO);
	}


    public List<Map<String,Object>> getBIUserListByAdmin() {
        return sqlSession.selectList("admin.getBIUserListByAdmin");
    }

    public List<ServiceBIVO> getBiList(ServiceBIVO serviceBiVO) {
		return sqlSession.selectList("admin.getBiList", serviceBiVO);
    }

	public Map<String,Object> getBiDetail(ServiceBIVO biVO) {
		return sqlSession.selectOne("admin.getBiDetail",biVO);
	}

	public void updateBi(ServiceBIVO biVO) {
		sqlSession.update("admin.updateBi",biVO);
	}

	public ServiceBIVO getBiNoteContent(ServiceBIVO bi) {
		return sqlSession.selectOne("admin.getBiNoteContent", bi);
	}

    public BiInfoVO getBiLink(BiInfoVO biVO) {
    	return sqlSession.selectOne("admin.getBiLink", biVO);
	}

	public void updateBiLink(BiInfoVO biVO) {
		sqlSession.update("admin.updateBiLink", biVO);
	}

	public int createCouponNum(List<CouponVO> couponList) {
		return sqlSession.insert("admin.createCouponNum", couponList);
	}

	public List<CouponVO> getCouponList(CouponVO couponVO) {
		return sqlSession.selectList("admin.getCouponList",couponVO);
	}

	public int getCouponListCnt(CouponVO couponVO) {
		return (Integer) sqlSession.selectOne("admin.getCouponListCnt",couponVO);
	}

	public CustomUserCount getCustomUserCount() {
		return sqlSession.selectOne("admin.getCustomUserCount");
	}

	public int updateCustomUserCount(CustomUserCount data) {
		return (Integer) sqlSession.update("admin.updateCustomUserCount",data);
	}

	public int getCallCountAll() {
		return (Integer) sqlSession.selectOne("getCallCountAll");
	}

	//서비스 신청 리스트 조회

	public int getColoringListCount() {
		return sqlSession.selectOne("admin.getColoringListCount");
	}

	public List<ColoringRegisterVO> getColoringList(ColoringRegisterVO coloringRegisterVO) {
		return sqlSession.selectList("admin.getColoringList",coloringRegisterVO);
	}

	public int updateColoringRegister(ColoringRegisterVO coloringRegisterVO) {
		return sqlSession.update("admin.updateColoringRegister",coloringRegisterVO);
	}

	public int getPatchCallApplyListCount(ServiceApplyVO serviceApplyVO) {
		return sqlSession.selectOne("admin.getPatchCallApplyListCount",serviceApplyVO);
	}

	public List<ServiceApplyVO> getPatchCallApplyList(ServiceApplyVO serviceApplyVO) {
		return sqlSession.selectList("admin.getPatchCallApplyList",serviceApplyVO);
	}

	public int updatePatchCallApply(ServiceApplyVO ServiceApplyVO) {
		return sqlSession.update("admin.updatePatchCallApply",ServiceApplyVO);
	}

	public int getPatchIntelligenceApplyListCount(ServiceApplyVO serviceApplyVO) {
		return sqlSession.selectOne("admin.getPatchIntelligenceApplyListCount",serviceApplyVO);
	}

	public List<ServiceApplyVO> getPatchIntelligenceApplyList(ServiceApplyVO serviceApplyVO) {
		return sqlSession.selectList("admin.getPatchIntelligenceApplyList",serviceApplyVO);
	}

	public int updatePatchIntelligenceApply(ServiceApplyVO serviceApplyVO) {
		return sqlSession.update("admin.updatePatchIntelligenceApply",serviceApplyVO);
	}

	public int getThirdPartApplyListCount(ServiceApplyVO serviceApplyVO) {
		return sqlSession.selectOne("admin.getThirdPartApplyListCount",serviceApplyVO);
	}

	public List<ServiceApplyVO> getThirdPartApplyList(ServiceApplyVO serviceApplyVO) {
		return sqlSession.selectList("admin.getThirdPartApplyList",serviceApplyVO);
	}

	public int updateThirdPartApply(ServiceApplyVO serviceApplyVO) {
		return sqlSession.update("admin.updateThirdPartApply",serviceApplyVO);
	}


}

