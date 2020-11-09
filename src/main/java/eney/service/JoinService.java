package eney.service;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import eney.domain.UserCertifyVo;
import eney.domain.UserVO;
import eney.exception.MalFormedDataException;
import eney.util.EncryptUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import eney.mapper.JoinDao;

@Service
public class JoinService {
	
	private static final Logger logger = LoggerFactory.getLogger(JoinService.class);
	
//	@Resource
//	AuthenticationProvider authenticationProvider;

	@Resource
	HttpServletRequest request;

	@Resource
	MessageService messageService;
	@Resource
	UserService userService;
	@Resource
	MailService mailService;
	
	@Resource
	JoinDao joinDao;
	
	
	public int insertJoinCheck(UserVO userVO){
		if(userVO.getMember_kind().equals("corporate"))
			return joinDao.insertJoinCheckCorporate(userVO);
		else 
			return joinDao.insertJoinPersonal(userVO);
	}

	/**
	 * 회원가입 로직
	 * @param userVO
	 * @throws NoSuchAlgorithmException
	 */
	@Transactional
	public void insertJoinerInfo(UserVO userVO) throws NoSuchAlgorithmException {
		
//		if(! UserVO.MEMBER_KIND_CORPORATE.equals(userVO.getMember_kind())){
////			userVO.setName(userCertify.getName());
////			userVO.setCi(userCertify.getCi());
////			userVO.setDi(userCertify.getDi());
////		}else{
////			userVO.setCi(userCertify.getCi());
////			userVO.setDi(userCertify.getDi());
////		}
		
		userVO.setPassword(EncryptUtil.encryptSHA256(userVO.getPassword()));
		joinDao.insertJoinerInfo(userVO);
		joinDao.insertJoinerUserInfo(userVO);
		joinDao.insertJoinerIdenInfo(userVO);
		
		/* 회원가입 안내 이메일 발송 부분 */
		Map<String, Object> mailModel = new HashMap<>();
		mailModel.put("userInfo", userVO);
		Boolean isEmailSend = mailService.sendTempletMail(userVO, "에네이 회원가입을 축하드립니다.", "memberJoin", mailModel);
		
		logger.info("[사용자 회원가입] 완료"
				+ "(userVO: " + userVO
				+ ", isEmailSend: " + isEmailSend
				+ ", ip: " + request.getRemoteAddr());
	}

	public UserVO getUserVOByIdx(int idx) {
		return joinDao.getUserVOByIdx(idx);
	}
	
	public UserVO updateUserInfo(UserVO userVO){
		UserVO ret = joinDao.updateUserInfo(userVO);
		//session reload
		Authentication authentication = userService.reloadSession(ret); 
		SecurityContext context = SecurityContextHolder.getContext();                          
       	context.setAuthentication(authentication);
		return ret;
	}

	public String checkPhonePopForm(UserVO userVO) {
		String res = "FAIL";
		userVO.setPhone_number(userVO.getPhone_number().replace("-", ""));
		String namePattern = "[가-힣]{2,3}";
		String phoneNumPattern = "^01(?:0|1|[6-9])(?:\\d{3}|\\d{4})\\d{4}$";
		String birthdayPattern = "^(\\d+)[/|\\-|\\s]+[0|1](\\d)[/|\\-|\\s]+([0|1|2|3]\\d)$";
		boolean flag1 = userVO.getName().matches(namePattern);
		boolean flag2 = Pattern.matches(phoneNumPattern, userVO.getPhone_number().replaceAll("-", ""));
		boolean flag3 = Pattern.matches(birthdayPattern, userVO.getBirth_day());
		boolean flag4 = userVO.getSex().equals("male")||userVO.getSex().equals("female"); 
		
		if(flag1 && flag2 && flag3 && flag4)
			res = "SUCCESS";
		
		return res;
	}
	/**
	 * 사업자 등록번호 체크(중복 여부, 로직에 맞는지)
	 * @param bizID 사업자 등록번호
	 * @return
	 */
	public String checkCorporateNumber(String bizID){
		String res = "CORRECT";
		boolean check_form = checkCorpNumForm(bizID);
		boolean check_overlap = checkCorpNumOverlap(bizID);
		if(!check_form){
			res = "ERROR:FORM";
			return res;
		}
		if(!check_overlap){
			res = "ERROR:OVERLAP";
			return res;
		}
		return res;
	}
	/**
	 * 사업자등록번호 중복 여부 확인 
	 * @param bizID 사업자등록번호
	 * @return
	 */
	private boolean checkCorpNumOverlap(String bizID) {
		int cnt = joinDao.getUserCntByCorpNumber(bizID);
		if(cnt!=0)
			return false;
		else
			return true;
	}
	/**
	 * 사업자등록번호 확인 (확인 코드가 적용이 안되어서 수정해야 함)
	 * @param bizID 사업자등록번호
	 * @return
	 */
	private boolean checkCorpNumForm(String bizID) {
		bizID = bizID.replace("-", "");
		boolean res;
		
		if(bizID.length() == 10){
			res = true;
		}else{
			res = false;
		}
		/*try{
			int sum=0;
	        // 1. 각각의 자리에 1 3 7 1 3 7 1 3 5 를 곱한 합을 구한다. 
	        String checkNo="137137135";
	        for(int i=0;i<checkNo.length();i++)
	            sum += (bizID.charAt(i)-'0') * (checkNo.charAt(i)-'0');
	        // 2. 마지막에서 두번째 숫자에 5를 곱하고 10으로 나누어 나온 몫을 더한다.
	        sum += ((bizID.charAt(8)-'0') * 5)/10;
	
	        // 3. 매직키인 10로 나누어 나머지만 취한다. 
	        sum %= 10;
	
	        // 4. 매직키인 10에서 나머지를 빼면
	        sum = 10 - sum;
	
	        // 5. 이숫자가 사업자등록번호 마지막 자리의 숫자와 일치하면 대한민국 사업자이다. 
	        if(sum==bizID.charAt(9)-'0')
	        	res = true;
	        else
	        	res = false;
		}catch(Exception e){
			res = false;
		}*/
		return res;
       
	}
	
	/**
	 * 사용자 중복 가입 확인
	 * @param ci 사용자 고유 번호
	 * @return 회원 가입 가능 여부 (중복시 false)
	 */
	public boolean checkUserCi(String ci){
		System.out.println(ci);
		UserVO user = joinDao.getUserByCi(ci);
		System.out.println(user);
		if(user != null)
			return false;
		else
			return true;
	}

	public UserVO getUserByCi(String ci){
		return joinDao.getUserByCi(ci);
	}
	
	/**
	 * 사업자로 등록하지 않을 때 추가 정보 입력(생일, 휴대폰번호)
	 * @param userVO 생일, 휴대폰번호
	 * @return
	 * @throws MalFormedDataException
	 */
	public boolean checkNonCorpMember(UserVO userVO) throws MalFormedDataException {
		boolean flag = false;
		Map<String,String> paramMap = new HashMap<String,String>();
		paramMap.put("data1", userVO.getBirth_day());
		paramMap.put("data2", String.valueOf(userVO.getPhone_number()));
		if(userVO.getCertify_key().equals(messageService.getCertificationKey(paramMap)))
			flag = true;
		return flag;
	}
}
