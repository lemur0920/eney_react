package eney.web;

import java.io.File;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.sql.Date;
import java.text.ParseException;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import eney.domain.payload.ApiResponse;
import eney.domain.payload.LoginRequest;
import eney.domain.payload.LoginResponse;
import eney.domain.payload.PasswordRequest;
import eney.property.UserCertifyProperties;
import eney.security.JwtTokenProvider;
import eney.service.*;
import eney.util.*;
import eney.domain.*;
import org.apache.catalina.User;
import org.apache.commons.validator.EmailValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/user")
public class UserController {
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Resource
	UserService userService;
	
	@Resource
	AdminService adminService;
	
	@Resource
	SupplyService supplyService;
	
	@Resource
	MessageService messageService;
	
	@Resource
	PaymentService paymentService;
	
	@Resource
	AcsService acsService;
	
	@Autowired
	private UserCertifyService userCertifyService;
	
	@Resource
	InvoiceService invoiceService;
	
	@Resource
	AlertService alertService;

	@Autowired
	JwtTokenProvider tokenProvider;

	@Autowired
	AuthenticationManager authenticationManager;

	@Resource
	CustomValidatorUtil customValidatorUtil;

	@Resource
	FileService fileService;

	private UserCertifyProperties userCertifyProperties;



	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(
						loginRequest.getUserId(),
						loginRequest.getPassword()
				)
		);

		SecurityContextHolder.getContext().setAuthentication(authentication);

		UserPrincipal user = (UserPrincipal)authentication.getPrincipal();

		UserVO userVO = userService.loadUserByUsername(user.getUserId());

		System.out.println("=====");
		System.out.println(userVO);
		System.out.println(userVO.getEpoint());
		System.out.println("=====");

        String jwt = tokenProvider.generateToken(authentication);
        Collection<GrantedAuthority> authorities = user.getAuthorities();
        userService.updateLastLogin(user.getUserId());

		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setUserId(user.getUserId());
        loginResponse.setUsername(user.getUsername());
        loginResponse.setUserId(user.getUserId());
        loginResponse.setRole(UserAuthUtil.getUserRole(authorities));
        loginResponse.setAccessToken(jwt);
		loginResponse.setEpoint(userVO.getEpoint());
		loginResponse.setEmail(userVO.getEmail());

		System.out.println(jwt);

		return ResponseEntity.ok(loginResponse);
	}

    @PreAuthorize("isAuthenticated()")
	@PostMapping("/authCheck")
	public ResponseEntity<?> AuthCheck(Principal principal) {

		return ResponseEntity.ok(HttpStatus.OK);
	}

	@PreAuthorize("isAuthenticated()")
	@PostMapping("/authUpdate")
	public ResponseEntity<?> authUpdate(Authentication  authentication) {

		UserPrincipal user = (UserPrincipal)authentication.getPrincipal();

		UserVO userVO = userService.loadUserByUsername(user.getUserId());

		String jwt = tokenProvider.generateToken(authentication);
		Collection<GrantedAuthority> authorities = user.getAuthorities();
		userService.updateLastLogin(user.getUserId());

		LoginResponse loginResponse = new LoginResponse();
		loginResponse.setUserId(user.getUserId());
		loginResponse.setUsername(user.getUsername());
		loginResponse.setUserId(user.getUserId());
		loginResponse.setRole(UserAuthUtil.getUserRole(authorities));
		loginResponse.setAccessToken(jwt);
		loginResponse.setEpoint(userVO.getEpoint());
		System.out.println(jwt);

		return ResponseEntity.ok(loginResponse);
	}


	/**
	 * 회원정보 변경
	 * @param userVO
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@ResponseBody
	@RequestMapping(value="/ajax/changeUserInfo.do",method=RequestMethod.POST)
	public String changeUserInfoAjaxAction(@RequestBody UserVO userVO) throws NoSuchAlgorithmException{
		return userService.checkChangeUserInfoForm(userVO);
	}
	
	/**
	 * 회원정보 중복여부 검사
	 * @param userVO
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/ajax/checkUserDataOverlapped.do",method=RequestMethod.POST)
	public Map<String,String> checkUserDataOverlapped(@RequestBody UserVO userVO){
		return userService.checkUserDataOverlapped(userVO);
	}
	
	/**
	 * 마이페이지 - 본인인증 코드 생성 
	 * @param userVO
	 * @param result
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value="/sendIdentification.do",  method = RequestMethod.POST)
	  public ModelAndView sendIdentification(@ModelAttribute UserVO userVO, BindingResult result) throws Throwable {
	    ModelAndView mav = new ModelAndView();
	    userService.sendIdentification(userVO);
	    
	    mav.setViewName("redirect:/user/myeney/certification.do");
	    return mav;


	  }
	@RequestMapping(value="/issueingTicket.do",  method = RequestMethod.POST)
	  public ModelAndView isseuingTicket(@ModelAttribute UserEventVO userEventVO, BindingResult result) throws Throwable {
	    ModelAndView mav = new ModelAndView();
	    userService.issueingTicket(userEventVO);
	    
	    mav.setViewName("redirect:/user/myeney/certification.do");
	    return mav;

	}
	
		@RequestMapping(value="/updateMargetingAgree.do",  method = RequestMethod.POST)
		public ModelAndView updateMargetingAgree(UserVO userVO) throws Throwable {
			ModelAndView mav = new ModelAndView();

			userService.updateUserMarketingData(userVO);
			mav.setViewName("redirect:/user/myeney/profile.do");
			return mav;


	  }
	
	/**
	 * 회원탈퇴 페이지 출력
	 * @param model
	 * @throws Throwable
	 */
	@RequestMapping(value="/drop.do", method=RequestMethod.GET)
	public void dropView(ModelMap model) throws Throwable{
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("userVO", userVO);
	}
	
	/**
	 * 회원 탈퇴
	 * @param userVO
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@RequestMapping(value="/drop.do", method=RequestMethod.POST)
	public ModelAndView dropUser(@ModelAttribute UserVO userVO) throws NoSuchAlgorithmException{
		ModelAndView mav = new ModelAndView();
		UserVO sessionVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//drop check
		if(sessionVO.getPassword().equals(EncryptUtil.encryptSHA256(userVO.getPassword()))){
			//연관된 자료 삭제:: 번호 해지..
			mav.setViewName("redirect:gone.do");
			userService.dropUser(sessionVO);
			//supplyService.close050Service(agentVO);
			SecurityContextHolder.clearContext();		// logout
		}else{
			mav.addObject("userVO", sessionVO);
			mav.addObject("script", "passwordNotEqual()");

		}



		return mav;
	}

	@RequestMapping(value = "/ajax/dropUser" , method=RequestMethod.POST)
	public @ResponseBody boolean dropUser(@RequestBody String password) throws NoSuchAlgorithmException {

		UserVO sessionVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if(sessionVO.getPassword().equals(EncryptUtil.encryptSHA256(password))){

			userService.dropUser(sessionVO);
			SecurityContextHolder.clearContext();
			return true;

		}else return false;
	}



	/**
	 * 탈퇴처리가 완료되었습니다 페이지 출력
	 */
	@RequestMapping("/gone.do")
	public void goneView(){}
	
	/**
	 * 비밀번호 찾기 1단계
	 * @return
	 */
	@RequestMapping(value="/pop/findPw", method=RequestMethod.GET)
	public String findPwView(@RequestParam(required=false)Integer error, Model model){
		String errorMsg = "";
		
		if(error != null){
			if(1 == error){
				errorMsg = "본인 인증이 완료되지 않았습니다.";
			} else if(2 == error){
				errorMsg = "입력한 계정과 사용자 정보가 일치하지 않습니다.";
			}
		}
		
		model.addAttribute("errorMsg", errorMsg);
		
		return "user/pop/findPw";
	}
	
	/**
	 * 비밀번호 찾기 2단계 이메일, 휴대폰번호 인증
	 * @param userid
	 * @return
	 */
	@RequestMapping(value="/pop/findPw", method=RequestMethod.POST)
	public String findPwPost(@RequestParam String userid, Model model){
		MobilUserCertifyRequestVo userCertifyRequest = userCertifyService.getUserCerifyRequestData();
		UserVO userVO = userService.loadUserByUsername(userid);
		
		if(userVO == null){
			model.addAttribute("errorMsg", "가입되지 않은 사용자입니다.");
			
			return "user/pop/findPw";
		}
		
		if(userVO.getCi() == null || userVO.getCi().equals("")){
			model.addAttribute("errorMsg", "본인인증 방식 변경으로 고객센터에 연락 주시기 바랍니다.<br/>에네이 고객센터 : 0506-191-0024<br/>");
		}
		
		model.addAttribute("userCeritfyRequest", userCertifyRequest);
		model.addAttribute("userid", userVO.getUserid());
		
		return "user/pop/findPwCertify";
	}
	
	/**
	 * 패스워드 초기화 뷰
	 * @param userid
	 * @param errorMsg
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/pop/resetPw", method=RequestMethod.GET)
	public String resetPwForm(String userid, @RequestParam(required=false) String errorMsg,
			Model model, HttpSession session, HttpServletRequest request){
		UserCertifyVo userCertify = (UserCertifyVo) session.getAttribute("userCertify");
		UserVO user = userService.loadUserByUsername(userid);
		if(userCertify == null){
			logger.warn("[회원가입] 경고 - 본인 인증 없이 비밀번호 초기화 시도 (User:{}, IP: {}", user, request.getRemoteAddr());
        	return "redirect:/user/pop/findPw?error=1";
		}
		if(! userService.checkUserCertify(user, userCertify)){
			return "redirect:/user/pop/findPw?error=2";
		}
		model.addAttribute("userid", userid);
		model.addAttribute("errorMsg", errorMsg);
		
		return "user/pop/resetPwForm";
	}
	/**
	 * 본인인증 후 패스워드 변경
	 * @param userid
	 * @param password
	 * @param passwordCheck
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/pop/resetPw", method=RequestMethod.POST)
	public String resetPw(String userid, String password, String passwordCheck, Model model, HttpSession session, HttpServletRequest request){
//		UserCertifyVo userCertify = (UserCertifyVo) session.getAttribute("userCertify");
//		UserVO user = userService.loadUserByUsername(userid);
//
//		if(userCertify == null){
//			logger.warn("[회원가입] 경고 - 본인 인증 없이 비밀번호 초기화 시도 (User:{}, IP: {}", user, request.getRemoteAddr());
//        	return "redirect:/user/pop/findPw?error=1";
//		}
//
//		if(! userService.checkUserCertify(user, userCertify)){
//			return "redirect:/user/pop/findPw?error=2";
//		}
//
//		if(userService.passwordValidator(password, passwordCheck)){
//			if(! userService.updateUserPassword(userid, password)){
//				return resetPwForm(userid, "비밀번호 변경중 오류가 발생했습니다", model, session, request);
//			}
//		} else {
//			return resetPwForm(userid, "입력한 비밀번호를 확인해 주세요", model, session, request);
//		}
		
		return "user/pop/resetPw";
	}
	
	/**
	 * 휴대폰 인증으로 아이디 찾기
	 * @param error
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/pop/findId", method=RequestMethod.GET)
	public String findId(@RequestParam(required=false)Integer error, Model model){
		MobilUserCertifyRequestVo userCertifyRequest = userCertifyService.getUserCerifyRequestData();

		model.addAttribute("userCeritfyRequest", userCertifyRequest);
		return "user/pop/findId";
	}
	/**
	 * 휴대폰 인증으로 아이디 찾기
	 * @param error
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/pop/findId", method=RequestMethod.POST)
	public String findIdPost(@RequestParam(required=false)Integer error, Model model){
		MobilUserCertifyRequestVo userCertifyRequest = userCertifyService.getUserCerifyRequestData();

		model.addAttribute("userCeritfyRequest", userCertifyRequest);
		return "user/pop/informId";
	}
	/**
	 * 휴대폰 인증 후 아이디 안내
	 * @param errorMsg
	 * @param model
	 * @param session
	 * @param request
	 * @return
	 */
	@RequestMapping(value="/pop/informId", method=RequestMethod.GET)
	public String informIdView(@RequestParam(required=false) String errorMsg,
			Model model, HttpSession session, HttpServletRequest request){
		UserCertifyVo userCertify = (UserCertifyVo) session.getAttribute("userCertify");
		
		if(userCertify == null){
			logger.warn("[회원가입] 경고 - 본인 인증 없이 아이디 찾기 시도 (User:{}, IP: {}", request.getRemoteAddr());
        	return "redirect:/user/pop/findId?error=1";
		}else{
			String regEx = "^(\\d{2,3})(\\d{3,4})(\\d{4,5})$";
			String rawphone = userCertify.getPhone_number();
			String phone = rawphone.replaceAll(regEx, "$1-$2-$3");
			UserVO userVO = userService.findUserId(phone);
			if(userVO == null){
				model.addAttribute("errorMsg", "해당 번호로 가입된 아이디가 없습니다.<br/>에네이 고객센터 : 0506-191-0024<br/>");
			}else{
				if(userVO.getCi() == null || userVO.getCi().equals("")){
					model.addAttribute("errorMsg", "본인인증 방식 변경으로 고객센터에 연락 주시기 바랍니다.<br/>에네이 고객센터 : 0506-191-0024<br/>");
				}
			}
			model.addAttribute("userVO",userVO);
		}
		
		model.addAttribute("errorMsg", errorMsg);
		
		return "user/pop/informId";
	}
	
	@RequestMapping(value = "/myeney/certification.do")
	public ModelAndView certificationView(@ModelAttribute UserVO userVO, UserEventVO userEventVO){
		ModelAndView mav = new ModelAndView();
		userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userEventVO.setUserid(userVO.getUserid());
		UserEventVO  userEvent= userService.getTicket(userEventVO);
		UserVO iden_code = userService.getIdenCode(userVO);
		String userId = userVO.getUserid();
		List<UserEventVO> eventList =  userService.getUserEvent(userVO.getUserid());
		mav.addObject("iden_code",iden_code);
		mav.addObject("userEvent",userEvent);
		mav.addObject("eventList",eventList);
		mav.addObject("userVO",userVO);
		mav.addObject("userId",userId);
		return mav;
	}

	@RequestMapping(value = "/myeney/event")
	public ModelAndView eventView(@ModelAttribute UserVO userVO){
		ModelAndView mav = new ModelAndView();
		userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	System.out.println("userid : " + userVO.getUserid());
		List<UserEventVO> eventList = userService.getUserEvent(userVO.getUserid());
		mav.addObject("eventList", eventList);
		return mav;
	}
	@RequestMapping(value = "/myeney/profile.do")
	public ModelAndView profileView(@ModelAttribute UserVO userVO){
		ModelAndView mav = new ModelAndView();
		UserVO session = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		userVO = userService.loadUserByUsername(session.getUserid());
		
		mav.addObject("userVO",userVO);
		return mav;
	}
	@RequestMapping(value = "/myeney/pw_change.do")
	public ModelAndView pwChangeView(@ModelAttribute UserVO userVO){
		ModelAndView mav = new ModelAndView();
		return mav;
	}

	
	@RequestMapping(value = "/myservice/bill.do")
	public ModelAndView billView(@ModelAttribute UserVO userVO,UserEventVO useEventVO){
		ModelAndView mav = new ModelAndView();
		userVO = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		useEventVO.setUserid(userVO.getUserid());
		List<UserHometaxVO>billingList = userService.getBillingList(userVO.getUserid());
		mav.addObject("billingList",billingList);
		return mav;
	}

	@RequestMapping(value = "/mypage/serviceList")
	public ResponseEntity<?> getMyserviceList(@RequestParam(value="myservicePage", defaultValue="1") Integer myservicePage, Authentication  authentication){



		Map<String, Object> map = new HashMap<>();
		ServiceListVO serviceListVO = new ServiceListVO();



		try{
			UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
			System.out.println(user.getUserId());

			if(user.getUserId() == null || user.getUserId() == ""){
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			}else{

				serviceListVO.setPageNo(myservicePage);
				serviceListVO.setUserid(user.getUserId());
				map = userService.getServiceList(serviceListVO);

				return new ResponseEntity<>(map, HttpStatus.OK);
			}


		}catch(Exception ex){
			String errorMessage;
			errorMessage = ex + " <== error";
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}

	}

	@RequestMapping(value = "/myservice/payment_history.do")
	public ModelAndView paymentHistoryView(@ModelAttribute UserVO userVO,UserEventVO userEventVO){
		ModelAndView mav = new ModelAndView();
		userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		userEventVO.setUserid(userVO.getUserid());
		List<UserHometaxVO>billingList = userService.getBillingList(userVO.getUserid());
		mav.addObject("billingList", billingList);
		return mav;
	}

//	@RequestMapping(value = "/myservice/point.do")
//	public ModelAndView pointView(@ModelAttribute UserVO userVO){
//		ModelAndView mav = new ModelAndView();
//
//		UserVO session = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		userVO = userService.loadUserByUsername(session.getUserid());
//
//
//		PaymentVO paymentVO = new PaymentVO();
//		paymentVO.setUserid(userVO.getUserid());
//
//		List<PaymentVO> chargeList = paymentService.getEpointLogs(paymentVO);
//
//		PaymentVO logPayVO = new PaymentVO();
//		logPayVO.setUserid(userVO.getUserid());
//		logPayVO.setItem_cate("epoint");
//		logPayVO.setStatus("approve");
//		List<PaymentVO> paymentList = paymentService.getPaymentLogs(logPayVO);
//		mav.addObject("paymentList", paymentList);
//
//
//
//		mav.addObject("userVO",userVO);
//		mav.addObject("chargeList",chargeList);
//
//		return mav;
//	}

	//포인트 사용 내역
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/mypage/logCharge")
	public ResponseEntity<?> getLogChargeData(@RequestParam(value="chargePage", defaultValue="1") Integer chargePage, @RequestParam(value="startDate", required = false) Date startDate,@RequestParam(value="endDate", required = false) Date endDate, Authentication  authentication)
			throws Throwable {

		System.out.println("+++++++++++");
		System.out.println(startDate);
		System.out.println(endDate);
		System.out.println("+++++++++++");

		if(chargePage == null){
			chargePage = 1;
		}

//			List<EpointLogVo> logList = new ArrayList<>();
			EpointLogVo epointLogVo = new EpointLogVo();
			epointLogVo.setPageNo(chargePage);
			epointLogVo.setStartDate(startDate);
			epointLogVo.setEndDate(endDate);


			Map<String, Object> map = new HashMap<>();

			try {
				UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

				String userid = user.getUserId();

				if (userid == null || userid == "") {
					return new ResponseEntity<>(HttpStatus.FORBIDDEN);
				} else {

					epointLogVo.setUserid(userid);

//					map.put("chargeLog",paymentService.getEpointLogs(epointLogVo));
					map = paymentService.getEpointLogs(epointLogVo);

				}


			} catch (Exception ex) {
				String errorMessage;
				errorMessage = ex + " <== error";
				return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
			}


			return new ResponseEntity<>(map, HttpStatus.OK);
	}

	//포인트 충전 내역
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value = "/mypage/logPayment")
	public ResponseEntity<?> getLogPaymentData(@RequestParam(value="paymentPage", defaultValue="1") Integer paymentPage, @RequestParam(value="startDate", required = false) Date startDate,@RequestParam(value="endDate", required = false) Date endDate,Authentication  authentication)
			throws Throwable {


		PaymentVO paymentVO = new PaymentVO();

		Map<String, Object> map = new HashMap<>();

		try {
			UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

			String userid = user.getUserId();

			if (userid == null || userid == "") {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			} else {

				paymentVO.setItem_cate("epoint");
				paymentVO.setStatus("approve");
				paymentVO.setPageNo(paymentPage);
				paymentVO.setUserid(userid);
				paymentVO.setStartDate(startDate);
				paymentVO.setEndDate(endDate);

				map = paymentService.getPaymentLogs(paymentVO);

			}


		} catch (Exception ex) {
			String errorMessage;
			errorMessage = ex + " <== error";
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}


		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@RequestMapping(value = "/mypage/myinfo")
	public ResponseEntity<?> getMyInfo(Authentication  authentication){

		UserVO userVO = new UserVO();
		Map<String, Object> map = new HashMap<>();

		try {
			UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

			String userid = user.getUserId();

			if (userid == null || userid == "") {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			} else {
				userVO = userService.loadUserByUsername(userid);
			}

			List<CompanyKindVO> array = userService.getCompanyKindList();

			map.put("user",userVO);
			map.put("companyKind",array);


		} catch (Exception ex) {
			String errorMessage;
			errorMessage = ex + " <== error";
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}


		return new ResponseEntity<>(map, HttpStatus.OK);
	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/company/name", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> updateCompanyName(@RequestBody String company_name, Authentication  authentication){

		UserVO userVO = new UserVO();
		int result = 0;

		try {
			UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

			String userid = user.getUserId();
			userVO.setUserid(userid);
			userVO.setCompany_name(company_name);

			if (userid == null || userid == "") {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			} else {
				result = userService.updateCompanyName(userVO);
				if(result == 1){
					return new ResponseEntity<>(company_name, HttpStatus.OK);
				}else{
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}

		} catch (Exception ex) {
			String errorMessage;
			errorMessage = ex + " <== error";
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/mypage/email", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> updateEmail(@RequestBody String Email, Authentication  authentication){

		EmailValidator emailValidator = EmailValidator.getInstance();

		Boolean isValid = emailValidator.isValid(Email);

		if(isValid){

			UserVO userVO = new UserVO();
			int result = 0;

			try {
				UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

				String userid = user.getUserId();
				userVO.setUserid(userid);
				userVO.setEmail(Email);

				if (userid == null || userid == "") {
					return new ResponseEntity<>(HttpStatus.FORBIDDEN);
				} else {
					result = userService.updateEmail(userVO);
					if(result == 1){
						return new ResponseEntity<>(Email, HttpStatus.OK);
					}else{
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}
				}

			} catch (Exception ex) {
				String errorMessage;
				errorMessage = ex + " <== error";
				return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
			}

		}else{
			return new ResponseEntity<>("유효한 E-Mail이 아닙니다", HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/mypage/companyKind", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> updateCompanyKind(@RequestBody String companyKind, Authentication  authentication){

			UserVO userVO = new UserVO();
			int result = 0;

			try {
				UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

				String userid = user.getUserId();
				userVO.setUserid(userid);
				userVO.setCompany_kind(companyKind);

				if (userid == null || userid == "") {
					return new ResponseEntity<>(HttpStatus.FORBIDDEN);
				} else {
					result = userService.updateCompanyKind(userVO);
					if(result == 1){
						return new ResponseEntity<>(companyKind, HttpStatus.OK);
					}else{
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}
				}
			} catch (Exception ex) {
				String errorMessage;
				errorMessage = ex + " <== error";
				return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
			}
	}


//	/**
//	 * 이미지 업로드 컨트롤러
//	 * @param request
//	 * @param files
//	 * @throws Exception
//	 */
//	@RequestMapping(value="/file/imageUpload", method=RequestMethod.POST, produces="text/html;charset=utf-8")
//	public ResponseEntity<?> communityImageUpload(HttpServletRequest request, @RequestParam ArrayList<MultipartFile> files) throws Exception {
//		List<FileVO> fileInfoList = null;
////		List<MultipartFile> fileList = new LinkedList<>();
////		fileList.add(upload);
//		try{
//			fileInfoList = fileService.processUpload(files, "CONTENT_IMG", 0, request);
//		} catch (Exception e) {
//			return new ResponseEntity<>("이미지 업로드 중 오류가 발생했습니다.", HttpStatus.BAD_REQUEST);
//		}
//
//		if(fileInfoList == null || fileInfoList.size() == 0){
//			return new ResponseEntity<>("이미지 파일이 부적합합니다.", HttpStatus.BAD_REQUEST);
//		} else {
//			FileVO fileInfo = fileInfoList.get(0);
//			String fileUrl = fileInfo.getUpload_name() +"." +fileInfo.getExtenstion();//저장경로
//
//			return new ResponseEntity<>(fileUrl, HttpStatus.OK);
//		}
//
//	}
//

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/mypage/company/license", method=RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<?> uploadLicence(Authentication  authentication){
		UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
		UserVO userVO = userService.loadUserByUsername(user.getUserId());

		try{
			return new ResponseEntity<>(fileService.selectFileList("LICENSE",userVO.getIdx()),HttpStatus.OK);

		} catch (Exception e) {
			return new ResponseEntity<>("파일 업로드 중 오류가 발생했습니다.", HttpStatus.BAD_REQUEST);
		}

	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/mypage/company/license", method=RequestMethod.POST,produces="text/html;charset=utf-8")
	@ResponseBody
	public ResponseEntity<?> uploadLicence(org.apache.catalina.servlet4preview.http.HttpServletRequest request, @RequestParam ArrayList<MultipartFile> files, Authentication  authentication){
		UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
		UserVO userVO = userService.loadUserByUsername(user.getUserId());

		List<FileVO> fileInfoList = null;
//		List<MultipartFile> fileList = new LinkedList<>();
//		fileList.add(upload);
		try{
			fileInfoList = fileService.processUpload(files, "LICENSE", userVO.getIdx(), request);
		} catch (Exception e) {
			return new ResponseEntity<>("파일 업로드 중 오류가 발생했습니다.", HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.OK);

	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/mypage/company/license/{idx}", method=RequestMethod.DELETE)
	public ResponseEntity<?> deleteCustomerCase( @PathVariable("idx") int idx,Authentication  authentication) throws Exception {

		UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
		UserVO userVO = userService.loadUserByUsername(user.getUserId());

		try{
			FileVO fileVO = new FileVO();
			fileVO.setRefer_id(userVO.getIdx());
			fileVO.setFile_id(idx);

			fileService.deleteFile(fileVO);

		}catch (Exception ex) {
			String errorMessage;
			errorMessage = ex + " <== error";
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}



	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/mypage/password", method=RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> updatePw(@RequestBody PasswordRequest passwordRequest, Authentication  authentication){

		System.out.println(passwordRequest);


//		if(sessionVO.getPassword().equals(EncryptUtil.encryptSHA256(password))){
//			return true;
//		}else return false;


//		if(isValid){

			try {
				UserPrincipal user = (UserPrincipal) authentication.getPrincipal();
				String userid = user.getUserId();
				UserVO userVO = userService.loadUserByUsername(userid);
				String newPw = EncryptUtil.encryptSHA256(passwordRequest.getCurrentPw());

				String date = DateUtil.getTodateString2();

				System.out.println(userVO.getPassword());
				System.out.println(newPw);
				System.out.println(passwordRequest.getCurrentPw() +"입력 패스워드");


				String error = null;
				if(!userVO.getPassword().equals(newPw)){
					error = "패스워드가 틀렸습니다.";
				}

				if(!passwordRequest.getNewPw().equals(passwordRequest.getNewPwCheck())){
					System.out.println(passwordRequest.getNewPw());
					System.out.println(passwordRequest.getNewPwCheck());
					error = "패스워드가 일치하지 않습니다.";
				}

				if(!customValidatorUtil.passwordNotFormedOrNotEqual(passwordRequest.getNewPw())){
					error = "비밀번호 길이는 8~16자 이내로 반드시 숫자,문자,특수문자가 1개 이상 포함되어야 합니다";
				}

				if(error == null){
					userService.updateUserPassword(userid, passwordRequest.getNewPw(), date);
					return new ResponseEntity<>(date,HttpStatus.OK);
				}else{
					return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
				}


			} catch (Exception ex) {
				String errorMessage;
				errorMessage = ex + " <== error";
				return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
			}
//
//		}else{
//			return new ResponseEntity<>("유효한 E-Mail이 아닙니다", HttpStatus.BAD_REQUEST);
//		}

	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/mypage/address", method=RequestMethod.PUT)
	public ResponseEntity<?> updateAddress(@RequestBody UserVO address, Authentication  authentication){

		System.out.println(address);

			int result = 0;

			try {
				UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

				String userid = user.getUserId();
				address.setUserid(userid);

				if (userid == null || userid == "") {
					return new ResponseEntity<>(HttpStatus.FORBIDDEN);
				} else {
					result = userService.updateAddress(address);
					if(result == 1){
						return new ResponseEntity<>(address, HttpStatus.OK);
					}else{
						return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
					}
				}

			} catch (Exception ex) {
				String errorMessage;
				errorMessage = ex + " <== error";
				return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
			}

	}

	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/mypage/phone", method=RequestMethod.PUT)
	public ResponseEntity<?> updatePhone(@RequestBody String phone, Authentication  authentication){

		System.out.println(phone);

		UserVO userVo = new UserVO();
		int result = 0;
		phone = EtcUtil.makePhoneNumber(phone);
		System.out.println(phone);

		try {
			UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

			String userid = user.getUserId();
			userVo.setUserid(userid);
			userVo.setPhone_number(phone);

			if (userid == null || userid == "") {
				return new ResponseEntity<>(HttpStatus.FORBIDDEN);
			} else {
				result = userService.updatePhone(userVo);
				if(result == 1){
					return new ResponseEntity<>(phone, HttpStatus.OK);
				}else{
					return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
				}
			}

		} catch (Exception ex) {
			String errorMessage;
			errorMessage = ex + " <== error";
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}

	}

//	@RequestMapping(value = "/myservice/log_pay.do")
//	public ModelAndView epointPayLogView( @RequestParam(required=false) Integer present_page)
//			throws Throwable{
//
//		ModelAndView mav = new ModelAndView();
//
//		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		mav.addObject("userVO", userVO);
//
//		PaymentVO paymentVO = new PaymentVO();
//
//		if(present_page!=null && present_page != 1){
//			paymentVO.setPresent_page(present_page);
//		}
//
//		paymentVO.setUserid(userVO.getUserid());
//		paymentVO.setItem_cate("epoint");
//		paymentVO.setStatus("approve");
//
//		List<PaymentVO> paymentList = paymentService.getPaymentLogs(paymentVO);
//		mav.addObject("paymentList", paymentList);
//		mav.addObject("paymentVO",paymentVO);
//
//		return mav;
//	}

    @RequestMapping("/myservice/log_refund.do")
    public void epointRefundLogView(ModelMap model, @RequestParam(required=false) Integer present_page)
            throws Throwable{

        UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute("userVO", userVO);

        EpointRefundVO refundVO = new EpointRefundVO();
        refundVO.setUserid(userVO.getUserid());
        List<EpointRefundVO> refundList = supplyService.getRefundList(refundVO);
        model.addAttribute("refundList", refundList);
        if(present_page!=null && present_page != 1){
            refundVO.setPresent_page(present_page);
        }
        model.addAttribute("refundVO",refundVO);
    }


	@RequestMapping(value = "/epoint/charge.do")
	public void epointChargeView(@RequestParam Map<String, Object> paramMap, ModelMap model)
			throws Throwable{

		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		model.addAttribute("userVO", userVO);

		PaymentVO paymentVO = new PaymentVO();
		paymentVO.setCategory("epoint");
		List<PaymentVO> paymentList = supplyService.getPaymentList(paymentVO);
		model.addAttribute("paymentList", paymentList);

	}

    @RequestMapping(value="/epoint/refund.do", method=RequestMethod.GET)
    public ModelAndView epointRefundView()
            throws Throwable{
        ModelAndView mav = new ModelAndView();
        UserVO session = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserVO userVO = userService.loadUserByUsername(session.getUserid());
        mav.addObject("userVO", userVO);

        return mav;

    }

    @RequestMapping(value="/epoint/refund.do", method=RequestMethod.POST)
    public ModelAndView epointRefundLogic(@ModelAttribute EpointRefundVO refundVO) throws Throwable{
        UserVO session = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        UserVO userVO = userService.loadUserByUsername(session.getUserid());
        ModelAndView mav = new ModelAndView();
        Map<String,Object> data = supplyService.checkRefundForm(refundVO);
        if((boolean) data.get("result")){
            PaymentVO payVO = new PaymentVO();
            payVO.setUserid(userVO.getUserid());
            payVO.setSale_price(refundVO.getEpoint_to_refund());
            payVO.setGubun("REFUND");
//            paymentService.deductEpoint(payVO);
            refundVO.setUserid(userVO.getUserid());
            supplyService.writeRefundLog(refundVO);
            mav.addObject("script","refundSuccess()");
        }
        else{
            mav = epointRefundView();
            mav.addObject("script","refundUnsuccess('"+data.get("message")+"')");
        }

        return mav;
    }


    @RequestMapping(value="/ajax/password_check", method=RequestMethod.POST)
	public @ResponseBody boolean passwordCheck(@RequestBody String password) throws NoSuchAlgorithmException {

		UserVO sessionVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if(sessionVO.getPassword().equals(EncryptUtil.encryptSHA256(password))){
			return true;
		}else return false;
	}

	@RequestMapping(value="/insertInvoice.do")
	public void insertInvoice() throws ParseException {
		UserVO userVO = new UserVO();
		userVO.setUserid("gkstmvdl");

		invoiceService.insertInvoiceByUserid(userVO);
	}

	@RequestMapping(value="/idCheck", method= RequestMethod.POST)
	public ResponseEntity<?> idCheck(@RequestBody(required = false) String userId){
		System.out.println(userId);
		UserVO user = userService.loadUserByUsername(userId);

		if(userId == null){
			return new ResponseEntity<>("아이디를 입렵하세요",HttpStatus.BAD_REQUEST);
		}

		if(user != null){
			return new ResponseEntity<>(user.getUserid(), HttpStatus.OK);
		}else{
			return new ResponseEntity<>("존재하지않은 아이디 입니다",HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value="/passwordReset", method= RequestMethod.POST)
	public ResponseEntity<?> passwodReset(@RequestBody PasswordRequest passwordRequest){

		String error = null;
		String date = DateUtil.getTodateString2();

		if(!passwordRequest.getNewPw().equals(passwordRequest.getNewPwCheck())){
			System.out.println(passwordRequest.getNewPw());
			System.out.println(passwordRequest.getNewPwCheck());
			error = "패스워드가 일치하지 않습니다.";
		}

		if(!customValidatorUtil.passwordNotFormedOrNotEqual(passwordRequest.getNewPw())){
			error = "비밀번호 길이는 8~16자 이내로 반드시 숫자,문자,특수문자가 1개 이상 포함되어야 합니다";
		}

		if(error == null){
			userService.updateUserPassword(passwordRequest.getUserId(), passwordRequest.getNewPw(), date);
			return new ResponseEntity<>(date,HttpStatus.OK);
		}else{
			return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
		}
	}

	@RequestMapping(value="/couponUse", method= RequestMethod.POST)
	public ResponseEntity<?> couponUse(@RequestBody CouponVO coupon,Authentication  authentication){

		UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
		coupon.setUsed_user(user.getUserId());

		int result = userService.couponUse(coupon, user.getUserId());

		System.out.println(result);

		if(result > 0){
			return new ResponseEntity<>(true,HttpStatus.OK);
		}else{
			return new ResponseEntity<>("유효한 쿠폰 번호가 아닙니다",HttpStatus.BAD_REQUEST);
		}
	}

}
