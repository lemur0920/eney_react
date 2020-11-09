package eney.service;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import eney.domain.ServiceBIVO;
import eney.domain.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import eney.mapper.AdminDao;


@Service
public class AdminService {
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);
	
	@Resource
	HttpServletRequest request;
	@Resource
	UserService userService;
	@Resource
	SupplyService supplyService;
	@Resource
	FileService fileService;
	@Resource
	AdminDao adminDao;
	@Resource
	MailService mailService;

	/**
	 * 페이징 처리 된 회원 목록 리스트
	 * @param userVO
	 * @return
	 */
	public Map<String, Object> getUserList(UserVO userVO) {

		Map<String, Object> map = new HashMap<>();

		userVO.setTotalCount(adminDao.getUserListCnt(userVO));
//		if(userVO.getTotal_item_num()>userVO.getPage_per_item_num())
//			userVO.setPagination_flag(true);
		List<UserVO> userList = adminDao.getUserList(userVO);

//		List userList = new ArrayList();
//
//		for(UserVO user : dataArray){
//			List userData = new ArrayList();
//			userData.add(user.getIdx());
//			userData.add(user.getMember_kind());
//			userData.add(user.getUserid());
//			userData.add(user.getName());
//			userData.add(user.getPhone_number());
//			userData.add(user.getReg_date());
//			userData.add(user.getLast_login());
//			userData.add(user.getPurpose());
//
//			userList.add(userData);
//		}

		map.put("list", userList);
		map.put("page", userVO);
		return map;
	}

	public List<UserVO> getUserListAll(UserVO userVO) {
		List<UserVO> userList = adminDao.getUserListAll(userVO);
		return userList;
	}
	/**
	 * 회원 정보 가져오기
	 * @param paramVO user_idx
	 * @return 회원 정보
	 */
	public UserVO getUserInfo(UserVO paramVO) {
		return adminDao.getUserInfo(paramVO);
	}
	
	/**
	 * 회원 정보 수정 (유형, 이름, 휴대폰번호, 이메일)
	 * @param updateVO
	 */
	public void updateUserInfo(UserVO updateVO) {
		adminDao.updateUserInfo(updateVO);
	}

	/**
	 * 회원 정보 수정 (서비스 권)
	 * @param updateVO한
	 */
	public void updateUserInfoTB(UserVO updateVO) {
		adminDao.updateUserInfoTB(updateVO);
	}
	/**
	 * 인보이스 등록
	 * 인보이스 번호는 오늘 날짜, 아이디, 랜덤값을 조합해서 만든다.
	 * @param
	 */
	public int insertInvoice(UserInvoiceVO userInvoiceVO) {
		Random random = new Random();        
		int result = random.nextInt(10000)+1000;	 
		if(result>10000){
		    result = result - 1000;
		}		
		Calendar oCalendar = Calendar.getInstance();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    	
		String today = dateFormat.format(oCalendar.getTime());
		userInvoiceVO.setInvoice_number(today +"-"+ userInvoiceVO.getUserid() +"-"+ result);
		
		return adminDao.insertInvoice(userInvoiceVO);
	}
	/**
	 * 홈텍스 등록
	 * @param
	 */
	public void insertHometax(UserHometaxVO userHometaxVO) {
		adminDao.insertHometax(userHometaxVO);
	}
	public int getHometaxIdx(Integer invoice_idx){
		
		return adminDao.getHometaxIdx(invoice_idx);
	}
	
	/**
	 * 홈택스 등록 후 invoice.hometax_idx 업데이트
	 * @param
	 */
	public void upDateInvoiceHometaxIdx(UserInvoiceVO userInvoiceVO) {
		adminDao.upDateInvoiceHometaxIdx(userInvoiceVO);
	}
	
	/**
	 * 회원 등록을 위한 회원 목록 리스트
	 * @param userVO
	 * @return
	 */
	public List<UserVO> getSearchIdList(UserVO userVO) {
		return adminDao.getSearchIdList(userVO);
	}
	
	/**
	 * 최근 10일의 콜 카운트
	 * @return 최근 10일간의 콜 카운트 리스트
	 */
	public List<Map<String, Object>> getCallCountSummary(){
		return adminDao.getCallCountSummary();
	}

	/**
	 * 매핑된 패치콜 번호 개수 조회
	 * @return 매핑된 패치콜 번호 개수
	 */
	public int getMappedPatchcallAmount() {
		return adminDao.getMappedPatchcallAmount();
	}
	/** *
	 * 최근 일주일간 환불신청내역 count
	 * @return
	 */
	public int getRefundCount(){
		return adminDao.getRefundCount();
	}
	/** *
	 * 최근 일주일간 환불리스트 내역
	 * @return
	 */
	
	public List<EpointRefundVO> getRefundList(){
		return adminDao.getRefundList();
	}
	
	/** *
	 * refund_id에 해당하는 환불신청 정보 
	 * @param paramVO
	 * @return
	 */
	public EpointRefundVO getRefundInfo(EpointRefundVO paramVO){
		return adminDao.getRefundInfo(paramVO);
	}
	
	/** *
	 * 
	 */
	public void updateRefundInfo(EpointRefundVO refundVO) {
		adminDao.updateRefundInfo(refundVO);
	}
	
	/**
	 * 공통 코드 리스트 출력
	 * @param selectQuery
	 * @return 공통 코드
	 */
	public List<CommonVO> getCommonCodeList(CommonVO selectQuery){
		List<CommonVO> commonCodeList = supplyService.getCommonList(selectQuery);
		return commonCodeList;
	}
	/**
	 * 공통코드 업데이트
	 * @param updateData
	 * @return
	 */
	public Boolean updateCommonCode(CommonVO updateData){
		return supplyService.updateCommon(updateData) == 1;
	}

	public List<Map<String, Object>> getPatchcallAmount() {
		return adminDao.getPatchcallAmount();
	}
	public List<Map<String,String>> getWebHostingList(ServiceWebHostingVO serviceWebHostingVO) {
		serviceWebHostingVO.setTotal_item_num(adminDao.getWebHostingListCnt(serviceWebHostingVO));
		serviceWebHostingVO.setPage_per_item_num(30);
		if(serviceWebHostingVO.getTotal_item_num()>serviceWebHostingVO.getPage_per_item_num())
			serviceWebHostingVO.setPagination_flag(true); 
		return adminDao.getWebHostingList(serviceWebHostingVO);
	}
	public void updateWebHostingGenerate(Integer web_hosting_idx) {
		adminDao.updateWebHostingGenerate(web_hosting_idx);
	}
	public List<ServicePatchcallVO> getPatchcallList(ServicePatchcallVO servicePatchcallVO) {
//		servicePatchcallVO.setTotal_item_num(adminDao.getPatchcallListCnt(servicePatchcallVO));
//		servicePatchcallVO.setPage_per_item_num(30);
//		if(servicePatchcallVO.getTotal_item_num()>servicePatchcallVO.getPage_per_item_num())
//			servicePatchcallVO.setPagination_flag(true);
		
		return adminDao.getPatchcallList(servicePatchcallVO);
	}
	public void updatePatchcallGenerate(Integer patchcall_idx) {
		adminDao.updatePatchcallGenerate(patchcall_idx);
	}
	public void updateBillingStatus(Integer invoice_idx) {
		adminDao.updateBillingStatus(invoice_idx);
	}
	public List<ServicePatchcallOtherVO> getPatchcallOtherList(ServicePatchcallOtherVO servicePatchcallOtherVO) {
		servicePatchcallOtherVO.setTotal_item_num(adminDao.getPatchcallOtherListCnt(servicePatchcallOtherVO));
		servicePatchcallOtherVO.setPage_per_item_num(30);
		if(servicePatchcallOtherVO.getTotal_item_num()>servicePatchcallOtherVO.getPage_per_item_num())
			servicePatchcallOtherVO.setPagination_flag(true); 
		
		return adminDao.getPatchcallOtherList(servicePatchcallOtherVO);
	}
	public void updatePatchcallOtherGenerate(Integer idx){
		adminDao.updatePatchcallOtherGenerate(idx);
	}
	public void insertEvent(UserEventVO userEventVO) {
		adminDao.insertEvent(userEventVO);
	}
	public List<UserEventVO> getUserEvent(String userid) {
		return adminDao.getUserEvent(userid);
	}

	public List<RecordVO> getRecordList(RecordVO recordVO) {
		recordVO.setTotal_item_num(adminDao.getRecordListCnt(recordVO));
		recordVO.setPage_per_item_num(30);
		if(recordVO.getTotal_item_num()>recordVO.getPage_per_item_num())
			recordVO.setPagination_flag(true); 
		
		return adminDao.getRecordList(recordVO);
	}
	public List<CallbackSmsVO> getCallbackList(CallbackSmsVO callbackVO) {
		callbackVO.setTotal_item_num(adminDao.getCallbackListCnt(callbackVO));
		callbackVO.setPage_per_item_num(30);
		if(callbackVO.getTotal_item_num()>callbackVO.getPage_per_item_num())
			callbackVO.setPagination_flag(true); 
		
		return adminDao.getCallbackList(callbackVO);
	}

    public Map<String,Integer> getEnablePatchCallSKB() {
		return adminDao.getEnablePatchCallSKB();
    }

	public Map<String,Integer> getEnablePatchCallSeJong() {
		return adminDao.getEnablePatchCallSeJong();
	}

	public List<Map<String,Object>> getTotalCallCount() {
		return adminDao.getTotalCallCount();
	}
	
	public ServiceWebHostingVO getWebApplicantList(ServiceWebHostingVO serviceInfoVO) {
		
		return adminDao.getWebApplicantList(serviceInfoVO);
	}
	public ServicePatchcallVO getPatchcallApplicantList(ServicePatchcallVO serviceInfoVO) {
		
		return adminDao.getPatchcallApplicantList(serviceInfoVO);
	}
	public ServicePatchcallOtherVO getOtherApplicantsList(ServicePatchcallOtherVO serviceInfoVO) {
		return adminDao.getOtherApplicantsList(serviceInfoVO);
	}
	public void sendEmail(ServiceWebHostingVO serviceInfoVO) {
		/* IDC 호스팅 서비스 승인 완료 이메일 발송 부분 */
		Map<String, Object> mailModel = new HashMap<>();
		mailModel.put("userInfo", serviceInfoVO);
		Boolean isEmailSend = mailService.sendTempletMail(serviceInfoVO, "[주식회사 에네이] 서비스 승인이 완료되었습니다.", "idcHosting", mailModel);
		logger.info("[서비스 승인] 완료"
				+ "(serviceInfoVO: " + serviceInfoVO
				+ ", isEmailSend: " + isEmailSend
				+ ", ip: " + request.getRemoteAddr());
	}
	public void sendEmail(ServicePatchcallVO serviceInfoVO) {
		/* 패치콜 서비스 승인 완료 이메일 발송 부분 */
		Map<String, Object> mailModel = new HashMap<>();
		mailModel.put("userInfo", serviceInfoVO);
		Boolean isEmailSend = mailService.sendTempletMail(serviceInfoVO, "[주식회사 에네이] 서비스 승인이 완료되었습니다.", "idcHosting", mailModel);
		logger.info("[서비스 승인] 완료"
				+ "(serviceInfoVO: " + serviceInfoVO
				+ ", isEmailSend: " + isEmailSend
				+ ", ip: " + request.getRemoteAddr());
	}
	public void sendEmail(ServicePatchcallOtherVO serviceInfoVO) {
		/* 080,대표번호서비스 승인 완료 이메일 발송 부분 */
		Map<String, Object> mailModel = new HashMap<>();
		mailModel.put("userInfo", serviceInfoVO);
		Boolean isEmailSend = mailService.sendTempletMail(serviceInfoVO, "[주식회사 에네이] 서비스 승인이 완료되었습니다.", "patchcallOther", mailModel);
		logger.info("[서비스 승인] 완료"
				+ "(serviceInfoVO: " + serviceInfoVO
				+ ", isEmailSend: " + isEmailSend
				+ ", ip: " + request.getRemoteAddr());
	}

	public List<Map<String, Object>> getApiMesurement() {
		
		return adminDao.getApiMesurement();
	}

	public List<AgentVO> getEndAgentList(AgentVO agentVO) {
		
		agentVO.setTotalCount(adminDao.getEndAgentListCnt(agentVO));
		agentVO.setPageSize(30);
		if(agentVO.getTotalCount()>agentVO.getPageSize())
			agentVO.setPagination_flag(true); 
		return adminDao.getEndAgentList(agentVO);
	}
	public List<UserHometaxVO> getBillingList(UserHometaxVO userHometaxVO) {
		return adminDao.getBillingList(userHometaxVO);
	}
	public List<UserHometaxVO> getNoHometaxList(UserInvoiceVO userInvoiceVO) {
		return adminDao.getNoHometaxList(userInvoiceVO);
	}
	public void updateNoteContent(ServicePatchcallVO patchcallVO) {
			adminDao.updateNoteContent(patchcallVO);
		
	}
	public ServicePatchcallVO getNoteContent(ServicePatchcallVO patchcallVO) {
		return adminDao.getNoteContent(patchcallVO);
	}

	public Map<String,Object> getPatchcallDetail(ServicePatchcallVO patchcallVO) {
		return adminDao.getPatchcallDetail(patchcallVO);
	}


    public List<Map<String,Object>> getBIUserListByAdmin() {
        return adminDao.getBIUserListByAdmin();
    }

	public void updateNoteContent(ServiceWebHostingVO webVO) {
		adminDao.updateWebNoteContent(webVO);

	}
	public ServiceWebHostingVO getWebNoteContent(ServiceWebHostingVO webVO) {
		return adminDao.getWebNoteContent(webVO);
	}
	public void updateWebNoteContent(ServiceWebHostingVO webVO) {
		adminDao.updateWebNoteContent(webVO);

	}
	public Map<String,Object> getWebDetail(ServiceWebHostingVO webVO) {
		return adminDao.getWebDetail(webVO);
	}


    public List<ServiceBIVO> getBiList(ServiceBIVO serviceBiVO) {

		serviceBiVO.setTotal_item_num(adminDao.getBiList(serviceBiVO).size());
		serviceBiVO.setPage_per_item_num(30);
		if(serviceBiVO.getTotal_item_num()>serviceBiVO.getPage_per_item_num())
			serviceBiVO.setPagination_flag(true);
		return adminDao.getBiList(serviceBiVO);
    }

	public Map<String,Object> getBiDetail(ServiceBIVO biVO) {
		return adminDao.getBiDetail(biVO);
	}

	public void updateBi(ServiceBIVO biVO) {
		adminDao.updateBi(biVO);
	}

	public ServiceBIVO getBiNoteContent(ServiceBIVO bi) {
		return adminDao.getBiNoteContent(bi);
	}

    public BiInfoVO getBiLink(BiInfoVO biVO) {
		return adminDao.getBiLink(biVO);
    }

	public void updateBiLink(BiInfoVO biVO) {
		adminDao.updateBiLink(biVO);
	}

	public int createCouponNum(List<CouponVO> couponList){return adminDao.createCouponNum(couponList);}

	public Map<String, Object> getCouponList(CouponVO couponVO) {

		couponVO.setTotalCount(adminDao.getCouponListCnt(couponVO));
		Map<String, Object> map = new HashMap<>();
		List<CouponVO> list = adminDao.getCouponList(couponVO);

		System.out.println("##########");
		System.out.println(list);
		map.put("list", list);
		map.put("page", couponVO);

		return map;
	}


	public CustomUserCount getCustomUserCount() {
		return adminDao.getCustomUserCount();
	}

	public int updateCustomUserCount(CustomUserCount data) {
		return adminDao.updateCustomUserCount(data);
	}

	public int getCallCountAll() {
		return adminDao.getCallCountAll();
	}

	//서비스 신청 관련

	public Map<String, Object> getColoringList(ColoringRegisterVO coloringRegisterVO) {

		coloringRegisterVO.setTotalCount(adminDao.getColoringListCount());

		Map<String, Object> map = new HashMap<>();

		List<ColoringRegisterVO> coloringList = adminDao.getColoringList(coloringRegisterVO);

		map.put("list", coloringList);
		map.put("page", coloringRegisterVO);

		return map;
	}

	public int updateColoringRegister(ColoringRegisterVO coloringRegisterVO) {

		return adminDao.updateColoringRegister(coloringRegisterVO);
	}

	public Map<String, Object> getPatchCallApplyList(ServiceApplyVO serviceApplyVO) {

		serviceApplyVO.setTotalCount(adminDao.getPatchCallApplyListCount(serviceApplyVO));

		Map<String, Object> map = new HashMap<>();

		List<ServiceApplyVO> serviceApplyList = adminDao.getPatchCallApplyList(serviceApplyVO);

		map.put("list", serviceApplyList);
		map.put("page", serviceApplyVO);

		return map;
	}

	public int updatePatchCallApply(ServiceApplyVO serviceApplyVO) {

		return adminDao.updatePatchCallApply(serviceApplyVO);
	}

	public Map<String, Object> getPatchIntelligenceApplyList(ServiceApplyVO serviceApplyVO) {

		serviceApplyVO.setTotalCount(adminDao.getPatchIntelligenceApplyListCount(serviceApplyVO));

		Map<String, Object> map = new HashMap<>();

		List<ServiceApplyVO> serviceApplyList = adminDao.getPatchIntelligenceApplyList(serviceApplyVO);

		map.put("list", serviceApplyList);
		map.put("page", serviceApplyVO);

		return map;
	}

	public int updatePatchIntelligenceApply(ServiceApplyVO serviceApplyVO) {

		return adminDao.updatePatchIntelligenceApply(serviceApplyVO);
	}

	public Map<String, Object> getThirdPartApplyList(ServiceApplyVO serviceApplyVO) {

		serviceApplyVO.setTotalCount(adminDao.getThirdPartApplyListCount(serviceApplyVO));

		Map<String, Object> map = new HashMap<>();

		List<ServiceApplyVO> serviceApplyList = adminDao.getThirdPartApplyList(serviceApplyVO);

		map.put("list", serviceApplyList);
		map.put("page", serviceApplyVO);

		return map;
	}

	public int updateThirdPartApply(ServiceApplyVO serviceApplyVO) {

		return adminDao.updateThirdPartApply(serviceApplyVO);
	}



}
