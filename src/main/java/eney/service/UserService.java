package eney.service;

import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import eney.domain.*;
import eney.mapper.PaymentDao;
import eney.util.DateUtil;
import eney.util.EncryptUtil;
import eney.util.RandomString;
import org.apache.ibatis.exceptions.TooManyResultsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eney.domain.PaymentLogVo.PayStatus;
import eney.mapper.UserDao;
import eney.util.DateUtil;
import eney.util.EncryptUtil;
import eney.util.RandomString;

@Service
public class UserService{
	
	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	@Resource
	HttpServletRequest request;
	@Resource
	SupplyService supplyService;
	@Resource
	PaymentService paymentService;
	@Resource
	PaymentDao paymentDao;
	@Resource
	MessageService messageService;
	@Resource
	MailService mailService;
	@Resource
	AcsService acsService;
	@Resource
	FileService fileService;
	@Resource
	UserDao userDao;
 
	/**
	 * user_id를 기준으로 user정보 가져오기
	 * @param username = user_id
	 * @throws UsernameNotFoundException
	 */
    public UserVO loadUserByUsername(final String username) throws UsernameNotFoundException {
        return userDao.loadUserByUsername(username);
    }
    
    /**
     * 탈퇴회원 정보 가져오기
     * @param username = user_id
     * @throws UsernameNotFoundException
     */
    public UserVO loadDropUserByUsername(final String username) throws UsernameNotFoundException {
        return userDao.loadDropUserByUsername(username);
    }

	public int updateCompanyName(UserVO user) throws NullPointerException {
		return userDao.updateCompanyName(user);
	}

	public int updateCompanyKind(UserVO user) throws NullPointerException {
		return userDao.updateCompanyKind(user);
	}

	public int updateEmail(UserVO user) throws NullPointerException {
		return userDao.updateEmail(user);
	}

	public int updateAddress(UserVO address) throws NullPointerException {
		return userDao.updateAddress(address);
	}

	public int updatePhone(UserVO user) throws NullPointerException {
		return userDao.updatePhone(user);
	}

    /**
     * 비밀번호 입력 후 회원정보 수정
     * @param userVO
     * @throws NoSuchAlgorithmException
     */
	public String checkChangeUserInfoForm(UserVO userVO) throws NoSuchAlgorithmException{
		String res = "";
		if(userVO.getColumn().equals("password")){
			String check = checkPasswordChangeForm(userVO);
			if(check!=null)
				return check;
			else{
				userVO.setUpdatedVal(EncryptUtil.encryptSHA256(userVO.getUpdatedVal()));
			}
		}else{
			if(!checkCertifyKey(userVO)){
				return "접근 권한이 없습니다.";
			}
		}
		UserVO userSession = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userVO.setUserid(userSession.getUserid());
		UserVO ret = userDao.updateUserData(userVO);
		if(userVO.getColumn().equals("password")){
			res = "비밀번호가 성공적으로 변경되었습니다.";
		}else if(userVO.getColumn().equals("email")){
			res = "이메일이 성공적으로 변경되었습니다.";
		}else{
			res = userVO.getColumn() + "이/가 변경되었습니다.";
		}
		Authentication authentication = reloadSession(ret); 
		SecurityContext context = SecurityContextHolder.getContext();                          
       	context.setAuthentication(authentication);
		return res;
	}
	
	public boolean updateUserPassword(String userid, String password, String date){
		UserVO user = loadUserByUsername(userid);
		
		if(user == null){
			return false;
		}
		
		try {
			user.setPassword_last_update(date);
			user.setUpdatedVal(EncryptUtil.encryptSHA256(password));
			user.setColumn("password");
		} catch (NoSuchAlgorithmException e) {
			logger.error("[비밀번호 변경] 오류 - 예외 발생 (E: {})", e.toString());
			return false;
		}
		
		userDao.updateUserData(user);
		
		return true;
	}
	
	public boolean passwordValidator(String password, String passwordCheck){
		if(!password.equals(passwordCheck)){
			return false;
		}
		
		if(password.length()<6 || password.length() > 16){
			return false;
		}
		
		if(! checkPasswordRegex(password)){
			return false;
		}
		
		return true;
	}
	
	/**
	 * 비밀번호 변경
	 * @param userVO
	 * @return
	 */
	private String checkPasswordChangeForm(UserVO userVO, UserVO userSession) {
		String res = "";
		
		if(! passwordValidator(userVO.getUpdatedVal(), userVO.getUpdatedVal2())){
			res = "새로 입력한 비밀번호를 확인해 주세요";
			return res;
		}
		
		try{
			if(!EncryptUtil.encryptSHA256(userVO.getPassword()).equals(userSession.getPassword())){
				res = "기존 비밀번호를 다시 확인해주세요.";
				return res;
			}
		}catch(NoSuchAlgorithmException e){
			res = "기존 비밀번호를 다시 확인해주세요.";
			return res;
		}
		
		return null;
	}

	private String checkPasswordChangeForm(UserVO userVO) {
		UserVO userSession = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		return checkPasswordChangeForm(userVO, userSession);
	}

	/**
	 * 비밀번호 양식 확인
	 * @param password
	 * @return
	 */
	public boolean checkPasswordRegex(String password){
		String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
		password =password.replaceAll(match, "?");

		if(password.indexOf("?")==-1){
			return false;
		}else
			return true;
	}

	/**
	 * 비밀번호 양식 확인
	 * @param password
	 * @return
	 * 영문,숫자,특수문자 최소 8 최대 16
	 */
	public boolean checkPasswordValidation(String password){
		String pattern = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[$@$!%*#?&])[A-Za-z\\d$@$!%*#?&]{8,16}$";

		System.out.println(password);
		if(password.matches(pattern)) {
			return true;
		}else{
			return false;
		}

	}

	public boolean checkUserIdFormValidation(String userId){
		String pattern = "^[a-z0-9]{3,12}$";

		System.out.println(userId);
		if(userId.matches(pattern)) {
			return true;
		}else{
			return false;
		}

	}

	/**
	 * 회원정보 수정 접근권한 확인
	 * @param userVO
	 */
	private boolean checkCertifyKey(UserVO userVO) {
		String column = userVO.getColumn();
		
		if(column==null)
			return false;
		else if(!(column.equals("password")||!(column.equals("phone_number"))||!(column.equals("email"))))
			return false;
		if(!userVO.getCertify_key().equals(getCerifyKeyByUpdatedVal(userVO.getUpdatedVal())))
			return false;
		
		return true;
	}

	public String getCerifyKeyByUpdatedVal(String val){
		char[] yyyymmdd =  (DateUtil.getTodateString().substring(0, 8)+DateUtil.getTodateString().substring(0, 8)).toCharArray();
		//if(!(column.equals("password")||column.equals("phone_number")||column.equals("email")))
		
		String index = "poiuyt3rew_qlk2jhg45f6ds1am7n0bvc9xz8-";
		char[] indexArr = index.toCharArray();
		String key = "what9DaeJangBongJunPammJjungPaohuCumchuckzz";
		
		String certifyKey = new String();
		
		for(int i=0;i<val.length();i++){
			char a = val.charAt(i);
			if(i%2==0){
			int j = 0;
			for (char c : indexArr) {
				if(c==a){
					certifyKey += key.charAt(j);
					break;
				}
				j++;
			}
			}else{
				certifyKey += key.charAt(yyyymmdd[i]%key.length());
			}
		}
		System.out.println(certifyKey);
		return certifyKey;
			
	}
	/**
	 * 회원정보 중복여부 검사
	 * @param userVO
	 * @return
	 */
	public Map<String, String> checkUserDataOverlapped(UserVO userVO) {
		Map<String,String> returnMap = new HashMap<String,String>();
		String result = "SUCCESS";
		String certify_key = "";
		UserVO userSession = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//userVO.setEmail(userVO.getEmail_address());
		userVO.setUserid(userSession.getUserid());
		try{
			//회원정보가 null값이 아닐 경우 결과값 FAIL
			if(userDao.getUserInfoByData(userVO)!=null){
				result = "FAIL";
			}else{
				certify_key = getCerifyKeyByUpdatedVal(userVO.getColumn_data());
			}
		}catch(TooManyResultsException e){
			result = "FAIL";
		}
		returnMap.put("result", result);
		returnMap.put("certify_key", certify_key);
		return returnMap;
	}
	
	/**
	 * 휴대폰번호 체크
	 * @param userVO
	 * @return
	 */
	public String checkPhoneForm(UserVO userVO) {
		String phoneNumPattern = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$";
		boolean flag1 = Pattern.matches(phoneNumPattern, userVO.getPhone_number());
		if(!flag1)
			return "휴대폰 입력 값을 다시 확인해주세요.";
		boolean flag2 = checkCertifyKey(userVO);
		if(!flag2)
			return "접근 권한이 없습니다.";
		return null;
	}

	public List<CompanyKindVO> getCompanyKindList () {
		return userDao.getCompanyKindList();
	}
	/**
	 * 회원정보 수정
	 * @param userVO
	 */
	public void updateUserData(UserVO userVO) {
		userDao.updateUserData(userVO);
	}

	public void updateUserMarketingData(UserVO userVO) {
		userDao.updateUserMarketingData(userVO);
	}
	/**
	 * 아이디와 휴대폰번호를 가지고 정보 확인(일치/불일치)
	 * @param userVO_DATA
	 * @return
	 */
	public boolean checkUserVoForCertifyNum(UserVO userVO_DATA) {
		boolean flag = false;
		UserVO userVO_DB = loadUserByUsername(userVO_DATA.getUserid());
		if(userVO_DATA.getPhone_number().equals(userVO_DB.getPhone_number())
				&&userVO_DATA.getName().equals(userVO_DB.getPhone_number()))
			flag = true;
		return flag;
	}
	
	/**
	 * 비밀번호 재설정 메일 전송
	 * @param userid
	 * @param name
	 * @param phone_number
	 * @param check_number
	 */
	public boolean sendResetPasswordMail(String userid, String name, String phone_number, String check_number, String userIp) {
		
		MessageVO msgVO = new MessageVO();
		Map<String, Object> res = null;
		Map<String, Object> mailModel = new HashMap<>();
		RandomString randomString = new RandomString(80);
		String resetKey = randomString.nextString();
		
		msgVO.setDstaddr(phone_number);
		msgVO.setExt_col2(check_number);
		
		try {
			res = messageService.checkCertifyNum(msgVO);
		} catch (Exception e) {
			return false;
		}
		
		if(! "SUCCESS".equals(res.get("check"))){
			logger.info("[비밀번호 재설정 메일 전송] 실패 - 인증번호 인증에 실패하였습니다. "
					+ "(userid: " + userid + ", "
					+ ", name: " + name
					+ ", phone_number: " + phone_number
					+ ", check_number: " + check_number
					+ ", IP: " + userIp);
			return false;
		}
		
		UserVO userData = loadUserByUsername(userid);
		if(! userData.getPhone_number().equals(phone_number)
				|| ! userData.getName().equals(name)){
			logger.info("[비밀번호 재설정 메일 전송] 실패 - 사용자 정보가 일치하지 않습니다. "
					+ "(userid: " + userid + ", "
					+ ", name: " + name
					+ ", phone_number: " + phone_number
					+ ", check_number: " + check_number
					+ ", userData: " + userData
					+ ", IP: " + userIp);
			return false;
		}

		mailModel.put("userInfo", userData);
		mailModel.put("resetKey", resetKey);
		
		if(mailService.sendTempletMail(userData, "에네이계정 비밀번호 재설정", "passwordReset", mailModel)){
			logger.info("[비밀번호 재설정 메일 전송] 실패 - 메일 전송에 실패했습니다. "
					+ "(userid: " + userid + ", "
					+ ", name: " + name
					+ ", phone_number: " + phone_number
					+ ", check_number: " + check_number
					+ ", mailModel: " + mailModel
					+ ", userData: " + userData
					+ ", IP: " + userIp);
		}
		
		return true;
	}
	/**
	 * 회원 탈퇴
	 * @param userVO
	 */
	@Transactional
	public void dropUser(UserVO userVO) {
		supplyService.close050All(userVO);
		transferUserToDrop(userVO);
		deleteService(userVO);
		userVO.setType(UserVO.TYPE_DROP);
		updateUserInfo(userVO);
		deleteUser(userVO);
	}
	public void deleteService(UserVO userVO){
		if(!userDao.getPatchcallServiceList(userVO.getUserid()).isEmpty()){
			userDao.deletePatchcall(userVO);
		}
		if(!userDao.getPatchcallOtherserviceList(userVO.getUserid()).isEmpty()){
			userDao.deletePatchcallOther(userVO);
		}
		RecordVO recordVO = new RecordVO();
		recordVO.setUserid(userVO.getUserid());
		RecordVO record = userDao.selectRecordServiceListByUserVO(recordVO);
		if(record != null){
			userDao.deleteRecord(userVO);
		}
		if(!userDao.getHostingServerList(userVO).isEmpty()){
			userDao.deleteServer(userVO);
		}
		if(!userDao.getVpnList(userVO.getUserid()).isEmpty()){
			userDao.deleteVpn(userVO);
		}
		if(!userDao.getHostingServiceList(userVO.getUserid()).isEmpty()){
			userDao.deleteWeb(userVO);
		}
		CallbackSmsVO callback = userDao.checkCallbackSmsService(userVO.getUserid());
		if(callback != null){
			userDao.deleteCallback(userVO);
			if(acsService.getMessaingListByUserid(userVO.getUserid()) != null){
				AcsTransmitVO acsVO = new AcsTransmitVO();
				acsVO.setUserid(userVO.getUserid());
				acsService.deleteMessagingNumberForUserid(acsVO);
			}
			
		}
		
	}
	/**
	 * n_user_info 정보 등록(패치콜 권한, 유형, epoint, 이미지)
	 * @param userVO
	 */
	//TODO 유형, 이미지 
	public void updateUserInfo(UserVO userVO) {
		userDao.updateUserInfo(userVO);
	}
	/**
	 * n_user_drop에 정보 입력(한번 회원가입 시 같은 로그인으로 회원가입 불가)
	 * @param userVO
	 */
	private void transferUserToDrop(UserVO userVO) {
		userDao.insertWithDrawUser(userVO);
	}
	/**
	 * 회원 탈퇴
	 * @param userVO
	 */
	private void deleteUser(UserVO userVO) {
		userDao.deleteUser(userVO);
	}
	/**
	 * 관리자페이지에서 등록한 패치콜 서비스 내역 리스트
	 * @param userVO userid
	 * @return
	 */
	public List<ServicePatchcallVO> getPatchcallServiceList(String userid) {
		return userDao.getPatchcallServiceList(userid);
	}
	public List<ServicePatchcallVO> getPatchcallServiceListCheckOn(String userid) {
		return userDao.getPatchcallServiceListCheckOn(userid);
	}
	
	public List<ServicePatchcallOtherVO> getPatchcallOtherserviceList(String userid) {
		return userDao.getPatchcallOtherserviceList(userid);
	}

	/**
	 * 관리자페이지에서 등록한 웹호스딩 서비스 내역 리스트
	 * @param userVO userid
	 * @return 
	 */
	public List<ServiceWebHostingVO> getHostingServiceList(String userid) {

		return userDao.getHostingServiceList(userid);
	}

	public List<ServiceWebHostingVO> getHostingServiceListByVO(ServiceWebHostingVO webHostingVO) {

		webHostingVO.setTotal_item_num(userDao.getHostingServiceListByVO(webHostingVO).size());


		return userDao.getHostingServiceListByVO(webHostingVO);
	}
	/**
	 * 관리자페이지에서 등록한 서버호스팅 서비스 내역 리스트
	 * @param userVO userid
	 * @return
	 */
	public List<UserVO> getHostingServerList(UserVO userVO) {
		return userDao.getHostingServerList(userVO);
	}
	/**
	 * 관리자페이지에서 등록한 VPN호스팅 서비스 내역 리스트
	 * @param userVO userid
	 * @return
	 */
	public List<ServiceVpnHostingVO> getVpnList(String userid) {
		return userDao.getVpnList(userid);
	}

	public List<ServiceVpnHostingVO> getVpnListByVO(ServiceVpnHostingVO vpnHostingVO) {
		vpnHostingVO.setTotal_item_num(userDao.getVpnListByVO(vpnHostingVO).size());

		return userDao.getVpnListByVO(vpnHostingVO);
	}
	
	/**
	 * 관리자페이지에서 등록한 홈텍스 리스트
	 * @param userVO userid
	 * @return
	 */
	public List<UserHometaxVO> getHometaxList(String userid) {
		return userDao.getHometaxList(userid);
	}
	/**
	 * 마이에네이 청구서 리스트
	 * @param userid
	 * @return
	 */
	public List<UserHometaxVO> getBillingList(String userid){
		return userDao.getBillingList(userid);
	}

	/**
	 * 마이페이지 본인인증 코드 생성
	 * @param userVO
	 * @return 
	 */
	public Integer sendIdentification(UserVO userVO) {
		return  userDao.sendIdentification(userVO);
		
	}
	/**
	 * 마이페이지 본인인증 코드 가져오기
	 * @param userVO
	 * @return
	 */
	public UserVO getIdenCode(UserVO userVO) {
		return userDao.getIdenCode(userVO);
	}

	/**
	 * 마지막 로그인 기록 업데이트
	 * @param username
	 */
	public void updateLastLogin(String username) {
		userDao.updateLastLogin(username);
	}

	/**
	 * 사용자 정보 reload
	 * @param userVO reload할 사용자 데이터
	 * @return
	 * @throws AuthenticationException
	 */
	public Authentication reloadSession(UserVO userVO) throws AuthenticationException {
    	SimpleDateFormat mSimpleDateFormat = new SimpleDateFormat ( "yyyy.MM.dd HH:mm:ss", Locale.KOREA );
    	Date currentTime = new Date ( );
    	String mTime = mSimpleDateFormat.format ( currentTime );
    	
    	if (userVO == null) 
            throw new BadCredentialsException("Username not found.");
    	
        List<GrantedAuthority> authorities = userVO.getAuthorities();
        
        if(userVO.getAuth()!=null&&!userVO.getAuth().equals("USER"))
        	authorities.add(new SimpleGrantedAuthority("ROLE_"+userVO.getAuth()));
        	userVO.setLast_login(mTime);
        	
        return new UsernamePasswordAuthenticationToken(userVO, userVO.getPassword(), authorities);
	}
	
	/**
	 * 사용자 정보 reload
	 * @return
	 */
	public Authentication reloadSession(){
		UserVO user = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		user = loadUserByUsername(user.getUsername());
		
		return reloadSession(user);
	}
	
	public ServiceWebHostingVO selectWebHosting(Integer web_hosting_idx) {
		return userDao.selectWebhostingService(web_hosting_idx);	
	}
	public Integer insertWebHosting(ServiceWebHostingVO webHostingService) {
		return userDao.insertWebHostingService(webHostingService);
	}
	public int updateWebHosting(ServiceWebHostingVO webHostingService){
		return userDao.updateWebHostingService(webHostingService);
	}
	public int updatePatchcall(ServicePatchcallVO patchcallService){
		return userDao.updatePatchcallService(patchcallService);
	}
	public int updatePatchcallOther(ServicePatchcallOtherVO patchcall_other) {
		return userDao.updatePatchcallOtherService(patchcall_other);
	}
	public Integer insertPatchcall(ServicePatchcallVO userVO) {
		return userDao.insertPatchcall(userVO);
	}
	public Integer insertBI(ServiceBIVO userVO) {return userDao.insertBI(userVO);}
	public ServicePatchcallVO selectPatchcallService(Integer patchcall_idx) {
		return userDao.selectPatchcallService(patchcall_idx);
	}
	public Integer insertPatchcallOther(ServicePatchcallOtherVO userVO) {
		return userDao.insertPatchcallOther(userVO);
	}
	public ServicePatchcallOtherVO selectPatchcallEtcService(Integer idx) {
		return userDao.selectPatchcallEtcService(idx);
	}
	public ServicePatchcallOtherVO selectPatchcallEtcServiceByUserid(UserVO userVO){
		return userDao.selectPatchcallEtcServiceByUserid(userVO);
	}

	public void sendEmail(UserVO userVO) {
		/* 서비스 신청 안내 이메일 발송 부분 */
		Map<String, Object> mailModel = new HashMap<>();
		mailModel.put("userInfo", userVO);
		Boolean isEmailSend = mailService.sendTempletMail(userVO, "[주식회사 에네이] 서비스 신청이 완료되었습니다.", "successRegist", mailModel);
		logger.info("[서비스 신청] 완료"
				+ "(userVO: " + userVO
				+ ", isEmailSend: " + isEmailSend
				+ ", ip: " + request.getRemoteAddr());
	}

	public String checkPhonePopForm(UserVO userVO) {
		UserVO userSession = loadUserByUsername(userVO.getUserid());
		String res = "FAIL";
		boolean flag1 = userVO.getName().equals(userSession.getName());
		boolean flag2 = userVO.getPhone_number().equals(userSession.getPhone_number());
		
		if(flag1 && flag2)
			res = "SUCCESS";
		
		return res;
	}

	public boolean checkUserCertify(UserVO user, UserCertifyVo userCertify){
		if(userCertify.getCi().equals(user.getCi()) && userCertify.getDi().equals(user.getDi())){
			return true;
		}
		return false;
	}
	/**
	 * 회원가입 시 핸드폰 번호로 user가 있는지 검색, 같은 휴대폰번호가 있으면 가입 X
	 * @param name 휴대폰번호
	 * @return
	 */	
	public UserVO findUserId(String name) {
		return userDao.selectFindUserId(name);	
	}

	public List<UserEventVO> getUserEvent(String userid) {
		return userDao.getUserEvent(userid);
	}

//	public int insertCallbacksms(ServicePatchcallOtherVO userVO) {
//		return userDao.insertCallbacksms(userVO);
//	}
	public int insertCallbacksms(ServicePatchcallVO userVO) {
	return userDao.insertCallbacksms(userVO);
}

	public CallbackSmsVO selectCallbackSmsService(Integer idx) {
		return userDao.selectCallbackSmsService(idx);
	}
	
	public List<CallbackSmsVO> selectCallbackSmsServiceListByUserVO(CallbackSmsVO userVO) {
		return userDao.selectCallbackSmsServiceListByUserVO(userVO);
	}
	
	public RecordVO selectRecordServiceListByUserVO(RecordVO recordVO) {
		return userDao.selectRecordServiceListByUserVO(recordVO);
	}

	public int updateCallbackSMS(CallbackSmsVO callback) {
		return userDao.updateCallbackSMS(callback);
	}

	public int updateCallbackSMSInfo(CallbackSmsVO callback) {
		return userDao.updateCallbackSMSInfo(callback);
	}

	
	public CallbackSmsVO checkCallbackSmsService(String userid) {
		return userDao.checkCallbackSmsService(userid);
	}

	public List<UserInvoiceVO> getInvoicePdf(Integer idx) {
		return userDao.getInvoicePdf(idx);
	}
	
	public UserInvoiceVO getInvoiceByIdx(Integer idx) {
		return userDao.getInvoiceByIdx(idx);
	}
	

	public UserInvoiceVO getInvoicePdfGroupBy(Integer idx) {
		return userDao.getInvoicePdfGroupBy(idx);
	}

	public List<ServicePatchcallVO> getPatchcallServiceListForInvoice(String userid) {
		return userDao.getPatchcallServiceListForInvoice(userid);
	}

	public List<ServicePatchcallOtherVO> getPatchcallOtherserviceListForInvoice(String userid) {
		return userDao.getPatchcallOtherserviceListForInvoice(userid);
	}

	public List<ServiceWebHostingVO> getHostingServiceListForInvoice(String userid) {
		return userDao.getHostingServiceListForInvoice(userid);
	}

	public CallbackSmsVO checkCallbackSmsServiceForInvoice(String userid) {
		return userDao.checkCallbackSmsServiceForInvoice(userid);
				
	}
	public int insertRecord(RecordVO userVO) {
		return userDao.insertRecord(userVO);
	}

	public RecordVO selectRecordService(Integer idx) {
		RecordVO record = userDao.selectRecordService(idx);
		AgentVO agentVO = new AgentVO();
		agentVO.setUser_id(record.getUserid());
		List<AgentVO> agentList = supplyService.getAgentVOList(agentVO);
		for(AgentVO agent : agentList){
			if(agent.getVno().substring(0,4).equals("0507")){
				agent.setRec_yn("Y");
				supplyService.update050Agent22ByVno(agent);
			}
		}
		return record;
	}

	public int updateRecord(RecordVO record) {
		return userDao.updateRecord(record);
	}

	public boolean checkPaymentWebHosting(String serviceCode, String itemParam) {
		ServiceWebHostingVO hosting = selectWebHosting(Integer.parseInt(itemParam));
		if(! hosting.getPay_state().equals(PaymentLogVo.PayStatus.standby)){
			return false;
		}else{
			return true;
		}
	}

	public boolean checkPaymentPatchcall(String serviceCode, String itemParam) {
		ServicePatchcallVO patchcall = selectPatchcallService(Integer.parseInt(itemParam));
		
		if(! patchcall.getPay_state().equals(PaymentLogVo.PayStatus.standby)){
			return false;
		}else{
			return true;
		}
	}
	public boolean checkPaymentPatchcallOther(String serviceCode, String itemParam  ) {
		ServicePatchcallOtherVO other = selectPatchcallEtcService(Integer.parseInt(itemParam));
		
		if(! other.getPay_state().equals(PaymentLogVo.PayStatus.standby)){
			return false;
		}else{
			return true;
		}
	}
	
	public boolean checkPaymentCallbackSms(String serviceCode, String itemParam) {
		CallbackSmsVO callback = selectCallbackSmsService(Integer.parseInt(itemParam));
		
		if(! callback.getPay_state().equals(PaymentLogVo.PayStatus.standby)){
			return false;
		}else{
			return true;
		}
	}
	public boolean checkPaymentRecord(String serviceCode, String itemParam) {
		RecordVO record = selectRecordService(Integer.parseInt(itemParam));
		
		if(! record.getPay_state().equals(PaymentLogVo.PayStatus.standby)){
			return false;
		}else{
			return true;
		}
	}

	public boolean checkPaymentPatchcallBi(String serviceCode, String itemParam) {
		ServiceBIVO bi = userDao.getBiService(Integer.parseInt(itemParam));

		if(! bi.getPay_state().equals(PaymentLogVo.PayStatus.standby)){
			return false;
		}else{
			return true;
		}
	}

	public void deleteRecord(UserVO userVO) {
		userDao.deleteRecord(userVO);
	}

	public ServicePatchcallVO getServiceListCheckPublishEmail(UserVO sessionVO) {
		return userDao.getServiceListCheckPublishEmail(sessionVO);
	}

	public List<ServicePatchcallVO> getServiceListAll(UserVO userVO) {
		return userDao.getServiceListALL(userVO);
	}

	public RecordVO selectRecordServiceListForInvoice(String userid) {
		return userDao.selectRecordServiceListForInvoice(userid);
	}

	public void deleteCallback(UserVO user) {
		userDao.deleteCallback(user);
	}
	public void deletePatcahcallDashboard(UserVO user) {
		userDao.deletePatcahcallDashboard(user);
	}

	public void insertMessaging(ServiceMessagingVO userVO) {
		userDao.insertMessage(userVO);
		
	}
	public ServiceMessagingVO getUserIpForUserVO(UserVO user) {
		return userDao.getUserIpForUserVO(user);
	}
	public List<ServiceListVO>getUserServiceListByUserid(String userid){
		return userDao.getUserServiceListByUserid(userid);
		
	}
	public void issueingTicket(UserEventVO userEventVO) {
	    userDao.issueingTicket(userEventVO);
	    
	  }
	public UserEventVO getTicket(UserEventVO userEventVO) {
	    return userDao.getTicket(userEventVO);
	  }
	  //막음
//	@Scheduled(cron="0 0 0 * * *")
//	public void processExpiredService(){
//		java.util.Calendar calendar = java.util.Calendar.getInstance();
//    	java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    	System.out.println("현재 시각: " +  dateFormat.format(calendar.getTime()));
//
//    	//패치콜 서비스 만료
//    	List<ServicePatchcallVO> patchcallList = userDao.getExpiredServicePatchcallList();
//    	List<Integer> close_patchcall = new ArrayList<Integer>();
//    	for (ServicePatchcallVO patchcallVO : patchcallList) {
//    		close_patchcall.add(patchcallVO.getPatchcall_idx());
//		}
//    	ServicePatchcallVO patchcall = new ServicePatchcallVO();
//    	patchcall.setClose_service(close_patchcall);
//
//    	if(close_patchcall.size()>0){
//    		Map<String, Object> map = new HashMap<>();
//    		map.put("list", close_patchcall);
//    		userDao.updateServicePatchcallStatus(map);
//    	}
//
//    	//웹호스팅 서비스 만료
//    	List<ServiceWebHostingVO> webList = userDao.getExpiredServiceWebHostingList();
//    	List<Integer> close_web = new ArrayList<Integer>();
//    	for (ServiceWebHostingVO webVO : webList) {
//    		close_web.add(webVO.getWeb_hosting_idx());
//		}
//    	ServiceWebHostingVO web = new ServiceWebHostingVO();
//    	web.setClose_service(close_web);
//
//    	if(close_web.size()>0){
//    		Map<String, Object> map = new HashMap<>();
//    		map.put("list", close_web);
//    		userDao.updateServiceWebHostingStatus(map);
//    	}
//
//    	//전국대표번호, 080 서비스 만료
//    	List<ServicePatchcallOtherVO> otherList = userDao.getExpiredServicePatchcallOtherList();
//    	List<Integer> close_other = new ArrayList<Integer>();
//    	for (ServicePatchcallOtherVO webVO : otherList) {
//    		close_other.add(webVO.getIdx());
//		}
//    	ServicePatchcallOtherVO other = new ServicePatchcallOtherVO();
//    	other.setClose_service(close_other);
//
//    	if(close_other.size()>0){
//    		Map<String, Object> map = new HashMap<>();
//    		map.put("list", close_other);
//    		userDao.updateServicePatchcallOtherStatus(map);
//    	}
//
//    	//녹취 서비스 만료
//    	List<RecordVO> recordList = userDao.getExpiredServiceRecordList();
//    	List<Integer> close_record = new ArrayList<Integer>();
//    	for (RecordVO recordVO : recordList) {
//    		close_record.add(recordVO.getIdx());
//		}
//    	RecordVO record = new RecordVO();
//    	record.setClose_service(close_record);
//
//    	if(close_record.size()>0){
//    		Map<String, Object> map = new HashMap<>();
//    		map.put("list", close_record);
//    		userDao.updateServicePatchcallOtherStatus(map);
//    	}
//
//    	//콜백 SMS 서비스 만료
//    	List<CallbackSmsVO> callbackList = userDao.getExpiredServiceCallbackSmsList();
//    	List<Integer> close_callback = new ArrayList<Integer>();
//    	for (CallbackSmsVO callbackVO : callbackList) {
//    		close_callback.add(callbackVO.getIdx());
//		}
//    	CallbackSmsVO callback = new CallbackSmsVO();
//    	callback.setClose_service(close_callback);
//
//    	if(close_callback.size()>0){
//    		Map<String, Object> map = new HashMap<>();
//    		map.put("list", close_callback);
//    		userDao.updateServiceCallbackSmsStatus(map);
//    	}
//
//	}

	public ServiceMessagingVO selectMessageVO(String userid) {
		return userDao.selectMessageVO(userid);
	}


    public List<ServiceBIVO> getBiServiceList(UserVO session) {
		return userDao.getBiServiceList(session);
    }

    public List<BiCategoryVO> getCategoryList() {
		return userDao.getCategoryList();
    }

	public ServiceBIVO getBiService(Integer idx) {
		return userDao.getBiService(idx);
	}

	public int updatePatchcallBi(ServiceBIVO patchcall_bi) {
		return userDao.updatePatchcallBi(patchcall_bi);
	}

	public void insertBIInfo(ServiceBIVO userVO) {
		userDao.insertBIInfo(userVO);
	}

	public int insertPatchcallDashBoard(ServicePatchcallVO servicePatchcallVO) {
		return userDao.insertPatchcallDashBoard(servicePatchcallVO);
	}
	public int updatePatchcallDashBoard(ServicePatchcallVO servicePatchcallVO) {
		return userDao.updatePatchcallDashBoard(servicePatchcallVO);
	}
	public ServicePatchcallDashBoardVO selectPatchcallDashBoardByUserid(ServicePatchcallVO servicePatchcallVO) {
		return userDao.selectPatchcallDashBoardByUserid(servicePatchcallVO);
	}


	public int insertPatchInteligence(ServicePatchcallVO servicePatchcallVO) {
		return userDao.insertPatchInteligence(servicePatchcallVO);
	}
	public int deletePatcahInteligence(ServicePatchcallVO servicePatchcallVO) {
		return userDao.deletePatcahInteligence(servicePatchcallVO);
	}
	public ServicePatchcallVO selectPatchInteligenceByUserVO(ServicePatchcallVO servicePatchcallVO) {
		return userDao.selectPatchInteligenceByUserVO(servicePatchcallVO);
	}
	public int insertCloud(ServicePatchcallVO servicePatchcallVO) {
		return userDao.insertCloud(servicePatchcallVO);
	}
	public int deleteCloud(ServicePatchcallVO servicePatchcallVO) {
		return userDao.deleteCloud(servicePatchcallVO);
	}
	public ServicePatchcallVO selectCloudByUserVO(ServicePatchcallVO servicePatchcallVO) {
		return userDao.selectCloudByUserVO(servicePatchcallVO);
	}

	public int insert3rdPart(ServicePatchcallVO servicePatchcallVO) {
		return userDao.insert3rdPart(servicePatchcallVO);
	}
	public int delete3rdPart(ServicePatchcallVO servicePatchcallVO) {
		return userDao.delete3rdPart(servicePatchcallVO);
	}
	public ServicePatchcallVO select3rdPartByUserVO(ServicePatchcallVO servicePatchcallVO) {
		return userDao.select3rdPartByUserVO(servicePatchcallVO);
	}
	public int insertGeneralDirectory(ServicePatchcallVO servicePatchcallVO) {
		return userDao.insertGeneralDirectory(servicePatchcallVO);
	}
	public int deleteGeneralDirectory(ServicePatchcallVO servicePatchcallVO) {
		return userDao.deleteGeneralDirectory(servicePatchcallVO);
	}
	public ServicePatchcallVO selectGeneralDirectoryByUserVO(ServicePatchcallVO servicePatchcallVO) {
		return userDao.selectGeneralDirectoryByUserVO(servicePatchcallVO);
	}


	public Map<String, Object> getServiceList(ServiceListVO serviceListVO) {

		Map<String, Object> map = new HashMap<>();

		serviceListVO.setTotalCount(supplyService.getUserServiceCnt(serviceListVO));
		List<ServiceListVO> list = supplyService.getUserServiceList(serviceListVO);

		map.put("list", list);
		map.put("page", serviceListVO);

		return map;
	}

	public int couponUse(CouponVO coupon, String userId) {

		CouponVO searchCoupon = userDao.getCoupon(coupon);
		if(searchCoupon == null || searchCoupon.getUsed() == true){
			return 0;
		}

		UserVO user = new UserVO();
		user.setUserid(userId);

		PaymentLogVo paymentLogVo = new PaymentLogVo();
		paymentLogVo.setAmount(searchCoupon.getPoint());
		paymentLogVo.setPay_method(PaymentLogVo.PayMethod.coupon);
		paymentLogVo.setOrder_date(new Date());
		paymentLogVo.setService_code(null);
		paymentLogVo.setUserid(userId);
		paymentLogVo.setService_name(null);
		paymentLogVo.setSerial_number(coupon.getCoupon_num());

		paymentLogVo.setItem_cate(ItemVo.ItemCategory.epoint);
		paymentLogVo.setData1(null);
		paymentLogVo.setData2(null);
		paymentLogVo.setOrder_date(new Date());
		paymentLogVo.setStatus(PayStatus.approve);
		paymentLogVo.setSerial_number(null);

		coupon.setUsed_user(userId);

		System.out.println("@@@@@@@@@@@@@@@@@@@@");

		System.out.println(paymentLogVo.toString());


		paymentService.chargeEpoint(paymentLogVo,user);
		paymentDao.insertPaymentLog(paymentLogVo);


		return userDao.couponUse(coupon);
	}
}