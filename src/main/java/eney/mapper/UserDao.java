package eney.mapper;

import java.util.List;
import java.util.Map;

import eney.domain.*;
import eney.domain.ServiceBIVO;
import eney.DatasourceConfig;
import eney.domain.*;
import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository
public class UserDao{
	
	@Autowired
	@Qualifier(DatasourceConfig.WebDatasourceConfig.SQL_SESSION_NAME)
	private SqlSession sqlSession;
	
	/**
	 * 회원정보 가져오기
	 * @param username userid
	 * @return
	 */
	public UserVO loadUserByUsername(String username) {
		UserVO param = new UserVO();
		param.setUserid(username);
		return (UserVO)sqlSession.selectOne("user.getUserInfo", param);
    }
	
	/**
	 * 탈퇴 회원 정보 가져오기
	 * @param username userid
	 * @return
	 */
	public UserVO loadDropUserByUsername(String username) {
		UserVO param = new UserVO();
		param.setUserid(username);
		return (UserVO)sqlSession.selectOne("user.getDropUserInfo", param);
    }

	public int updateCompanyName(UserVO user) {
		return sqlSession.update("user.updateCompanyName", user);
	}

	public int updateCompanyKind(UserVO user) {
		return sqlSession.update("user.updateCompanyKind", user);
	}

	public int updateEmail(UserVO user) {
		return sqlSession.update("user.updateEmail", user);
	}

	public int updateAddress(UserVO user) {
		return sqlSession.update("user.updateAddress", user);
	}

	public int updatePhone(UserVO user) {
		return sqlSession.update("user.updatePhone", user);
	}

	/**
	 * 회원정보 수정 
	 * @param userVO (아이디, 비밀번호, 이메일, 휴대폰번호)
	 * @return
	 */
	public UserVO updateUserData(UserVO userVO) {
		sqlSession.update("user.updateUserData", userVO);
		return loadUserByUsername(userVO.getUserid());
	}

	public List<CompanyKindVO> getCompanyKindList() {
		return sqlSession.selectList("user.getCompanyKindList");
	}

	public UserVO updateUserMarketingData(UserVO userVO) {
		sqlSession.update("user.updateUserMarketingData", userVO);
		return loadUserByUsername(userVO.getUserid());
	}

	//TODO column이 뭔지 모르겠음.
	public UserVO getUserInfoByData(UserVO userVO) {
		return (UserVO)sqlSession.selectOne("user.getUserInfoByData",userVO);
	}

	/**
	 * 회원 탈퇴
	 * @param userVO
	 */
	public void deleteUser(UserVO userVO) {
		sqlSession.delete("user.deleteUser",userVO);
	}
	
	/**
	 * n_user_drop에 정보 등록
	 * @param userVO
	 */
	public void insertWithDrawUser(UserVO userVO) {
		sqlSession.insert("user.insertWithDrawUser", userVO);
	}
	/**
	 * n_user_info 정보 업데이트 (패치콜 권한, 유형, epoint, 이미지)
	 * @param userVO
	 */
	public void updateUserInfo(UserVO userVO) {
		sqlSession.update("user.updateUserInfo", userVO);
	}
	
	/**
	 *패치콜 서비스 내역 리스트
	 * @param userid
	 */
	public List<ServicePatchcallVO> getPatchcallServiceList(String userid) {
		return sqlSession.selectList("user.getPatchcallServiceList", userid);
	}
	
	public List<ServicePatchcallVO> getPatchcallServiceListCheckOn(String userid) {
		return sqlSession.selectList("user.getPatchcallServiceListCheckOn", userid);
	}
	/**
	 * 웹 호스팅 서비스 내역 리스트
	 * @param userid
	 * @return
	 */
	public List<ServiceWebHostingVO> getHostingServiceList(String userid) {
		return sqlSession.selectList("user.getHostingServiceList", userid);
	}
	
	/**
	 * 패치콜 나머지 서비스 리스트(080, 대표번호, 녹취, shortURL)
	 * @param userid
	 * @return
	 */
	
	public List<ServicePatchcallOtherVO> getPatchcallOtherserviceList(String userid) {
		return sqlSession.selectList("user.getPatchcallOtherserviceList",userid);
	}
	/**
	 * 서버 호스팅 서비스 내역 리스트
	 * @param userVO
	 * @return
	 */
	public List<UserVO> getHostingServerList(UserVO userVO) {
		return sqlSession.selectList("user.getHostingServerList", userVO);
	}
	
	/**
	 * VPN 호스팅 서비스 내역 리스트
	 * @return
	 */
	public List<ServiceVpnHostingVO> getVpnList(String userid) {
		return sqlSession.selectList("user.getVpnList", userid);
	}

	public List<ServiceVpnHostingVO> getVpnListByVO(ServiceVpnHostingVO vpnHostingVO) {
		return sqlSession.selectList("user.getVpnListByVO", vpnHostingVO);
	}
	
	/**
	 * 홈텍스 내역 리스트
	 * @return
	 */
	public List<UserHometaxVO> getHometaxList(String userid) {
		return sqlSession.selectList("user.getHometaxList", userid);
	}
	public List<UserHometaxVO> getBillingList(String userid){
		return sqlSession.selectList("user.getBillingList",userid);
	}
	/**
	 * 본인인증 코드 생성
	 * @param userVO
	 * @return 
	 */
	public Integer sendIdentification(UserVO userVO) {
		return sqlSession.update("user.sendIdentification",userVO);		
	}
	/**
	 * 본인인증 코드 조회
	 * @param userVO
	 * @return
	 */
	public UserVO getIdenCode(UserVO userVO) {
		return sqlSession.selectOne("user.getIdenCode",userVO);
	}

	/**
	 * 로그인 일자 저장
	 * @param username
	 */
	public void updateLastLogin(String username) {
		UserVO param = new UserVO();
		param.setUserid(username);
		sqlSession.update("user.updateLastLogin", param);		
	}
	
	public ServiceWebHostingVO selectWebhostingService(Integer web_hosting_idx) {
		return sqlSession.selectOne("user.selectWebHostingService", web_hosting_idx);
	}

	public Integer insertWebHostingService(ServiceWebHostingVO webHosting) {
		return sqlSession.insert("user.insertWebHostingService", webHosting);
	}
	
	public Integer updateWebHostingService(ServiceWebHostingVO webHosting){
		return sqlSession.update("user.updateWebHostingService", webHosting);
	}
	
	public Integer updatePatchcallService(ServicePatchcallVO patchcall){
		return sqlSession.update("user.updatePatchcallService", patchcall);
	}
	public Integer updatePatchcallOtherService(ServicePatchcallOtherVO patchcall){
		return sqlSession.update("user.updatePatchcallOtherService", patchcall);
	}

	public Integer insertPatchcall(ServicePatchcallVO userVO) {
		return sqlSession.insert("user.insertPatchcall", userVO);
	}
	public Integer insertBI(ServiceBIVO userVO) { return sqlSession.insert("user.insertBI",userVO);}
	public ServicePatchcallVO selectPatchcallService(Integer patchcall_idx) {
		return sqlSession.selectOne("user.selectPatchcallService",patchcall_idx);
	}

	public Integer insertPatchcallOther(ServicePatchcallOtherVO userVO) {
		return sqlSession.insert("user.insertPatchcallOther",userVO);
	}

	public ServicePatchcallOtherVO selectPatchcallEtcService(Integer idx) {
		return sqlSession.selectOne("user.selectPatchcallEtcService",idx);
	}
	
	public ServicePatchcallOtherVO selectPatchcallEtcServiceByUserid(UserVO userVO) {
		return sqlSession.selectOne("user.selectPatchcallEtcServiceByUserid",userVO);
	}
	

	public UserCertifyVo getUserCerityByUser_cretify_idx(int user_cretify_idx){
		return sqlSession.selectOne("user.getUserCerityByUser_cretify_idx", user_cretify_idx);
	}
	public int insertUserCerity(UserCertifyVo userCertify) {
		return sqlSession.insert("user.insertUserCerity", userCertify);
	}
	public int updateUserCertify(UserCertifyVo userCertify) {
		return sqlSession.update("user.updateUserCerity", userCertify);
	}

	public UserVO selectFindUserId(String name) {
		return sqlSession.selectOne("user.selectFindUserId", name);
	}

	public List<UserEventVO> getUserEvent(String userid) {
		return sqlSession.selectList("user.getUserEvent",userid);
	}

	public int insertCallbacksms(ServicePatchcallVO userVO) {
		return sqlSession.insert("user.insertCallbacksms",userVO);
	}
	
	/**
	 * idx를 기준값으로 콜백sms 출력(결제 부분에서 사용)
	 * @param idx 콜백 sms idx값
	 * @return
	 */
	public CallbackSmsVO selectCallbackSmsService(Integer idx) {
		return sqlSession.selectOne("user.selectCallbackSmsService",idx);
	}

	public int updateCallbackSMS(CallbackSmsVO callback) {
		return sqlSession.update("user.updateCallbackSMS",callback);
	}

	public int updateCallbackSMSInfo(CallbackSmsVO callback) {
		return sqlSession.update("user.updateCallbackSMSInfo",callback);
	}

	
	public List<CallbackSmsVO> selectCallbackSmsServiceListByUserVO(CallbackSmsVO userVO) {
		return sqlSession.selectList("user.selectCallbackSmsServiceListByUserVO",userVO);
	}
	
	public CallbackSmsVO checkCallbackSmsService(String userid) {
		return sqlSession.selectOne("user.checkCallbackSmsService",userid);
	}

	public List<UserInvoiceVO> getInvoicePdf(Integer idx) {
		return sqlSession.selectList("user.getInvoicePdf",idx);
	}
	
	public UserInvoiceVO getInvoiceByIdx(Integer idx) {
		return sqlSession.selectOne("user.getInvoiceByIdx",idx);
	}

	public UserInvoiceVO getInvoicePdfGroupBy(Integer idx) {
		return sqlSession.selectOne("user.getInvoicePdfGroupBy",idx);
	}

	public List<ServicePatchcallVO> getPatchcallServiceListForInvoice(String userid) {
		return sqlSession.selectList("user.getPatchcallServiceListForInvoice",userid);
	}

	public List<ServicePatchcallOtherVO> getPatchcallOtherserviceListForInvoice(String userid) {
		return sqlSession.selectList("user.getPatchcallOtherserviceListForInvoice",userid);
	}

	public List<ServiceWebHostingVO> getHostingServiceListForInvoice(String userid) {
		return sqlSession.selectList("user.getHostingServiceListForInvoice",userid);
	}

	public CallbackSmsVO checkCallbackSmsServiceForInvoice(String userid) {
		return sqlSession.selectOne("user.checkCallbackSmsServiceForInvoice",userid);
	}

	public RecordVO selectRecordServiceListByUserVO(RecordVO recordVO) {
		return sqlSession.selectOne("user.selectRecordServiceListByUserVO",recordVO);
	}
	public int insertRecord(RecordVO userVO) {
		return sqlSession.insert("user.insertRecord",userVO);
	}
	public RecordVO selectRecordService(Integer idx) {
		return sqlSession.selectOne("user.selectRecordService",idx);
	}
	public int updateRecord(RecordVO record) {
		return sqlSession.update("user.updateRecord",record);
	}
	public void deletePatchcall(UserVO userVO) {
		sqlSession.delete("user.deletePatchcall",userVO);
	}
	public void deletePatchcallOther(UserVO userVO) {
		sqlSession.delete("user.deletePatchcallOther",userVO);
	}
	public void deleteRecord(UserVO userVO) {
		sqlSession.update("user.deleteRecord",userVO);
	}
	public void deleteServer(UserVO userVO) {
		sqlSession.delete("user.deleteServer",userVO);
	}
	public void deleteVpn(UserVO userVO) {
		sqlSession.delete("user.deleteVpn",userVO);
	}
	public void deleteWeb(UserVO userVO) {
		sqlSession.delete("user.deleteWeb",userVO);
	}
	public void deleteCallback(UserVO userVO) {
		sqlSession.delete("user.deleteCallback",userVO);
	}
	public void deletePatcahcallDashboard(UserVO userVO) {
		sqlSession.delete("user.deletePatcahcallDashboard",userVO);
	}
	public ServicePatchcallVO getServiceListCheckPublishEmail(UserVO sessionVO) {
		return sqlSession.selectOne("user.getServiceListCheckPublishEmail",sessionVO);
	}
	public List<ServicePatchcallVO> getServiceListALL(UserVO userVO) {
		return sqlSession.selectList("user.getServiceListAll",userVO);
	}

	public RecordVO selectRecordServiceListForInvoice(String userid) {
		return sqlSession.selectOne("user.selectRecordServiceListForInvoice",userid);
	}

	public void insertMessage(ServiceMessagingVO userVO) {
		sqlSession.insert("user.insertMessage",userVO);
		
	}
	public ServiceMessagingVO getUserIpForUserVO(UserVO user) {
		return sqlSession.selectOne("user.getUserIpForUserVO",user);
	}

	public List<ServicePatchcallVO> getExpiredServicePatchcallList() {
		return sqlSession.selectList("user.getExpiredServicePatchcallList");
	}
	
	public List<ServiceWebHostingVO> getExpiredServiceWebHostingList() {
		return sqlSession.selectList("user.getExpiredServiceWebHostingList");
	}
	
	public List<ServicePatchcallOtherVO> getExpiredServicePatchcallOtherList() {
		return sqlSession.selectList("user.getExpiredServicePatchcallOtherList");
	}
	
	public List<RecordVO> getExpiredServiceRecordList() {
		return sqlSession.selectList("user.getExpiredServiceRecordList");
	}
	
	public List<CallbackSmsVO> getExpiredServiceCallbackSmsList() {
		return sqlSession.selectList("user.getExpiredServiceCallbackSmsList");
	}

	public void updateServicePatchcallStatus(Map<String, Object> map) {
		sqlSession.update("user.updateServicePatchcallStatus", map);
	}
	
	public void updateServiceWebHostingStatus(Map<String, Object> map) {
		sqlSession.update("user.updateServiceWebHostingStatus", map);
	}
	
	public void updateServicePatchcallOtherStatus(Map<String, Object> map) {
		sqlSession.update("user.updateServicePatchcallOtherStatus", map);
	}
	
	public void updateServiceRecordStatus(Map<String, Object> map) {
		sqlSession.update("user.updateServiceRecordStatus", map);
	}
	
	public void updateServiceCallbackSmsStatus(Map<String, Object> map) {
		sqlSession.update("user.updateServiceCallbackSmsStatus", map);
	}
	public List<ServiceListVO> getUserServiceListByUserid(String userid) {
		return sqlSession.selectList("user.getUserServiceListByUserid");
	}
  public void issueingTicket(UserEventVO userEventVO) {
    sqlSession.insert("user.issueingTicket",userEventVO);    
  	}
  public UserEventVO getTicket(UserEventVO userEventVO) {
	    return sqlSession.selectOne("user.getTicket",userEventVO);
	  }

    public List<ServiceWebHostingVO> getHostingServiceListByVO(ServiceWebHostingVO webHostingVO) {
		return sqlSession.selectList("user.getHostingServiceListByVO", webHostingVO);
	}

    public ServiceMessagingVO selectMessageVO(String userid) {
		return sqlSession.selectOne("user.selectMessageVO",userid);
    }


    public List<ServiceBIVO> getBiServiceList(UserVO session) {
    	return sqlSession.selectList("user.getBiServiceList",session);
	}

    public List<BiCategoryVO> getCategoryList() {
		return sqlSession.selectList("user.getCategoryList");
    }

	public ServiceBIVO getBiService(Integer idx) {
		return sqlSession.selectOne("user.getBiService",idx);
	}

	public int updatePatchcallBi(ServiceBIVO patchcall_bi) {
		return sqlSession.update("user.updatePatchcallBi", patchcall_bi);
	}

    public void insertBIInfo(ServiceBIVO userVO) {
    	sqlSession.insert("user.insertBIInfo", userVO);
	}

	public List<ServiceApplyVO> getServiceApplyListById(ServiceApplyVO serviceApplyVO) {
		return sqlSession.selectList("user.getServiceApplyListById", serviceApplyVO);
	}

	public int getServiceApplyCnt(ServiceApplyVO serviceApplyVO) {
		return sqlSession.selectOne("user.getServiceApplyCnt", serviceApplyVO);
	}

	public int insertPatchcallDashBoard(ServicePatchcallVO servicePatchcallVO) {
		return sqlSession.insert("user.insertPatchcallDashBoard",servicePatchcallVO);
	}
	public int updatePatchcallDashBoard(ServicePatchcallVO servicePatchcallVO) {
		return sqlSession.update("user.updatePatchcallDashBoard",servicePatchcallVO);
	}
	public ServicePatchcallDashBoardVO selectPatchcallDashBoardByUserid(ServicePatchcallVO servicePatchcallVO) {
		return sqlSession.selectOne("user.selectPatchcallDashBoardByUserid",servicePatchcallVO);
	}

	public int insertPatchInteligence(ServicePatchcallVO servicePatchcallVO) {
		return sqlSession.insert("user.insertPatchInteligence",servicePatchcallVO);
	}
	public int deletePatcahInteligence(ServicePatchcallVO servicePatchcallVO) {
		return sqlSession.update("user.deletePatcahInteligence",servicePatchcallVO);
	}
	public ServicePatchcallVO selectPatchInteligenceByUserVO(ServicePatchcallVO servicePatchcallVO) {
		return sqlSession.selectOne("user.selectPatchInteligenceByUserVO",servicePatchcallVO);
	}

	public int insertCloud(ServicePatchcallVO servicePatchcallVO) {
		return sqlSession.insert("user.insertCloud",servicePatchcallVO);
	}
	public int deleteCloud(ServicePatchcallVO servicePatchcallVO) {
		return sqlSession.update("user.deleteCloud",servicePatchcallVO);
	}
	public ServicePatchcallVO selectCloudByUserVO(ServicePatchcallVO servicePatchcallVO) {
		return sqlSession.selectOne("user.selectCloudByUserVO",servicePatchcallVO);
	}

	public int insert3rdPart(ServicePatchcallVO servicePatchcallVO) {
		return sqlSession.insert("user.insert3rdPart",servicePatchcallVO);
	}
	public int delete3rdPart(ServicePatchcallVO servicePatchcallVO) {
		return sqlSession.update("user.delete3rdPart",servicePatchcallVO);
	}
	public ServicePatchcallVO select3rdPartByUserVO(ServicePatchcallVO servicePatchcallVO) {
		return sqlSession.selectOne("user.select3rdPartByUserVO",servicePatchcallVO);
	}

	public int insertGeneralDirectory(ServicePatchcallVO servicePatchcallVO) {
		return sqlSession.insert("user.insertGeneralDirectory",servicePatchcallVO);
	}
	public int deleteGeneralDirectory(ServicePatchcallVO servicePatchcallVO) {
		return sqlSession.update("user.deleteGeneralDirectory",servicePatchcallVO);
	}
	public ServicePatchcallVO selectGeneralDirectoryByUserVO(ServicePatchcallVO servicePatchcallVO) {
		return sqlSession.selectOne("user.selectGeneralDirectoryByUserVO",servicePatchcallVO);
	}


	public int couponUse(CouponVO coupon) {
		return sqlSession.update("user.couponUse", coupon);
	}

	public CouponVO getCoupon(CouponVO coupon) {
		return sqlSession.selectOne("user.getCoupon", coupon);
	}

}
