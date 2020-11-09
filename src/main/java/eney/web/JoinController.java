package eney.web;

import java.security.Principal;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import eney.domain.MessageVO;
import eney.domain.MobilUserCertifyRequestVo;
import eney.domain.UserCertifyVo;
import eney.domain.UserVO;
import eney.domain.payload.JoinReponse;
import eney.service.*;
import org.apache.poi.ss.formula.functions.T;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import eney.domain.MessageVO;
import eney.domain.MobilUserCertifyRequestVo;
import eney.domain.UserCertifyVo;
import eney.domain.UserCertifyVo.UserCertifyStatus;
import eney.domain.UserVO;
import eney.validator.JoinValidator;

@Controller
@RequestMapping("/join")
public class JoinController {
	
	private static final Logger logger = LoggerFactory.getLogger(JoinController.class);
	
	@Resource
	JoinValidator joinValidator;
	@Autowired
	private UserCertifyService userCertifyService;
	@Resource
	JoinService joinService;
	@Resource
	UserService userService;
	@Resource
	SupplyService supplyService;
	@Resource
	MessageService messageService;
	
	/**
	 * 회원가입 약관동의 페이지
	 */
	@RequestMapping("/wrap.do")
	public void joinwrapView(@RequestParam Map<String, Object> paramMap, ModelMap model ,Principal principal) 
			throws Throwable{
		
	}
	/*
	 *가입여부 확인 페이지 연결 
	 */
//	@RequestMapping(value="/check.do", method = RequestMethod.GET)
//	public String joincheckView(@RequestParam Map<String, Object> paramMap, Model model ,Principal principal)
//			throws Throwable{
//		MobilUserCertifyRequestVo userCertifyRequest = userCertifyService.getUserCerifyRequestData();
//
//		System.out.println(userCertifyRequest);
//
//		model.addAttribute("userCeritfyRequest", userCertifyRequest);
//		System.out.println(userCertifyRequest);
//
//		return "join/check";
//	}
	/*
	*본인 인증에 필요한 userCeritfy 값 전송
	*/
	@ResponseBody
	@RequestMapping(value="/auth/userCertify", method = RequestMethod.GET)
	public MobilUserCertifyRequestVo joincheckView(@RequestParam Map<String, Object> paramMap, Model model , Principal principal)
			throws Throwable{
		MobilUserCertifyRequestVo userCertifyRequest = userCertifyService.getUserCerifyRequestData();

		String tradeId = userCertifyService.makeUserCerity(UserCertifyVo.UserCertifyType.JOIN).getTradeid();

		userCertifyRequest.setTradeid(tradeId);

		System.out.println(userCertifyRequest);

		return userCertifyRequest;
	}

	/**
	 * 가입여부 확인 (회원 유형에 따라 사업자등록번호, 휴대폰번호를 사용하여 인증)
	 * @param userVO
	 * @throws Throwable
	 */
	@RequestMapping(value="/check.do", method = RequestMethod.POST)
	public ModelAndView joincheckAction(@ModelAttribute UserVO userVO, HttpSession session) throws Throwable{
		ModelAndView mav = new ModelAndView("join/form");
		
		if(UserVO.MEMBER_KIND_CORPORATE.equals(userVO.getMember_kind())){
			String check = joinService.checkCorporateNumber(userVO.getCorporate_number());
			UserCertifyVo userCertify = (UserCertifyVo) session.getAttribute("userCertify");
			if(check.equals("ERROR:FORM")){
				mav = new ModelAndView("join/check");
				mav.addObject("script", "alert('사업자등록번호를 다시 확인해주세요.')");
				return mav;
			}else if(check.equals("ERROR:OVERLAP")){
				mav = new ModelAndView("join/check");
				mav.addObject("script", "alert('이미 등록된 번호입니다.')");
				return mav;
			}
			if(userCertify == null || ! UserCertifyVo.UserCertifyStatus.success.equals(userCertify.getCeritfy_status())){
				mav = new ModelAndView("join/check");
				mav.addObject("script", "alert('본인인증 처리가 되지않았습니다.')");
				
				return mav;
			}
			if(! joinService.checkUserCi(userCertify.getCi())){
				mav = new ModelAndView("join/check");
				mav.addObject("script", "alert('이미 가입된 사용자입니다.')");
				return mav;
			}
			
			userVO.setPhone_number(userCertify.getPhone_number());
			userVO.setBirth_day(userCertify.getSocialno());
			
			mav.addObject("userCertify", userCertify);
			
		}else{
			UserCertifyVo userCertify = (UserCertifyVo) session.getAttribute("userCertify");
			
			if(userCertify == null || ! UserCertifyVo.UserCertifyStatus.success.equals(userCertify.getCeritfy_status())){
				mav = new ModelAndView("join/check");
				mav.addObject("script", "alert('본인인증 처리가 되지않았습니다.')");
				
				return mav;
			}
			if(! joinService.checkUserCi(userCertify.getCi())){
				mav = new ModelAndView("join/check");
				mav.addObject("script", "alert('이미 가입된 사용자입니다.')");
				return mav;
			}
			
			userVO.setName(userCertify.getName());
			userVO.setPhone_number(userCertify.getPhone_number());
			userVO.setBirth_day(userCertify.getSocialno());
			
			mav.addObject("userCertify", userCertify);
				
		}
			
		mav.addObject("process","CHECK_COMPLETE");
		mav.addObject("userVO", userVO);
		
		return mav;
	}
	/**
	 * 사용 X
	 * 회원가입(개인회원)가입 시 휴대폰 번호 인증을 위하여 띄우는 팝업 
	 * @param member_kind
	 * @throws Throwable
	 */
	@RequestMapping(value="/pop/certify_phone.do", method = RequestMethod.GET)
	public ModelAndView certifyPhonePopupView(@RequestParam String member_kind) 
			throws Throwable{
		ModelAndView mav = new ModelAndView();
		mav.addObject("member_kind", member_kind);
		return mav;
	}
	/**
	 * 사용 X
	 * 회원가입(개인회원)가입 시 휴대폰 번호 인증
	 * @param userVO
	 * @return
	 */
	@RequestMapping(value="/pop/certify_phone.do", method = RequestMethod.POST)
	public ModelAndView certifyPhonePopupAction(@ModelAttribute UserVO userVO){
		ModelAndView mav = new ModelAndView("join/check");
		mav.addObject("process","CERTIFY_COMPLETE");
		mav.addObject("userVO",userVO);
		return mav;
	}
	/**
	 * 사용 X
	 * 본인인증  메시지 전송
	 * @param userVO
	 */
	@ResponseBody
	@RequestMapping("/ajax/sendCertifyMsg.do")
	public String ajaxAcitonSendCertifyMsg(@RequestBody UserVO userVO){
		String res = "SUCCESS";
		res = joinService.checkPhonePopForm(userVO);
		if(res.equals("SUCCESS")){
			MessageVO msgVO = new MessageVO();
			msgVO.setDstaddr(userVO.getPhone_number());
			messageService.sendCertifyMsg(msgVO);
		}
		return res;
	}
	/**
	 * 휴대폰번호 인증 ajax 로직
	 * @param msgVO (이름, 성별, 생년월일, 전화번호, 인증번호 입력)
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ajax/checkOutCertification.do")
	public @ResponseBody Map<String, Object> ajaxActionCheckOutCertification(@RequestBody MessageVO msgVO) throws Exception{
		//String res ="SUCCESS";
		Map<String, Object> res = messageService.checkCertifyNum(msgVO);
		return res;
	}
	/**
	 * 회원가입 폼 출력
	 */
	@RequestMapping(value="/form.do", method = RequestMethod.GET)
	public ModelAndView joinFormView(@ModelAttribute UserVO userVO, HttpSession session, HttpServletRequest request) 
			throws Throwable{
		ModelAndView mav = new ModelAndView();
		
		UserCertifyVo userCertify = (UserCertifyVo) session.getAttribute("userCertify");
		
		if(! UserVO.MEMBER_KIND_CORPORATE.equals(userVO.getMember_kind())){
			
			if(userCertify == null){
				logger.warn("[회원가입] 경고 - 본인 인증 없이 가입 시도 (UserVO:{}, IP: {}", userVO, request.getRemoteAddr());
	        	return new ModelAndView("redirect:/join/check.do");
			} else {
				if(! joinService.checkUserCi(userCertify.getCi())){
					return new ModelAndView("redirect:/join/check.do");
				}
				userVO.setName(userCertify.getName());
				userVO.setPhone_number(userCertify.getPhone_number());
			}
		}else{
			if(userCertify == null){
				logger.warn("[회원가입] 경고 - 본인 인증 없이 가입 시도 (UserVO:{}, IP: {}", userVO, request.getRemoteAddr());
	        	return new ModelAndView("redirect:/join/check.do");
			} else {
				if(! joinService.checkUserCi(userCertify.getCi())){
					return new ModelAndView("redirect:/join/check.do");
				}
				userVO.setPhone_number(userCertify.getPhone_number());
			}
		}
		
		mav.addObject("userCertify", userCertify);
		
		return mav;
	}
	/**
	 * 회원가입 및 사용자 검증
	 */
//	@RequestMapping(value="/form.do",  method = RequestMethod.POST)
	@RequestMapping(value="/auth/join",  method = RequestMethod.POST)
	public ResponseEntity<?> joinAction(@RequestBody UserVO userVO, BindingResult result, HttpSession session, HttpServletRequest request) throws Throwable {
		System.out.println("회원가입 시도");

		JoinReponse joinReponse = joinValidator.userValidate(userVO);
		System.out.println(joinReponse);
		System.out.println("=====끝======");

		if(joinReponse.getResult() == false){
			return new ResponseEntity<>(joinReponse, HttpStatus.BAD_REQUEST);
		}else{
			joinService.insertJoinerInfo(userVO);
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}
	/**
	 * 회원가입 성공 페이지
	 */
	@RequestMapping("/complete.do")
	public void joinCompleteView(@RequestParam Map<String, Object> paramMap, ModelMap model ,Principal principal) 
			throws Throwable{
		
	}

	/**
	 * 아이디 중복 체크
	 * @param userVO
	 * @param result
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping("/duplicateCheck")
	public @ResponseBody
	ResponseEntity<?> duplicationCheck(@RequestBody(required = false) String userId) throws Throwable{

		System.out.println(userId);
		if(userId == null || userId == ""){
			return new ResponseEntity<>("아이디를 입력하세요",HttpStatus.BAD_REQUEST);
		} else if(userService.loadUserByUsername(userId) != null
				|| userService.loadDropUserByUsername(userId) != null){
			return new ResponseEntity<>("이미 존재하는 아이디 입니다",HttpStatus.BAD_REQUEST);
		}else if(!userService.checkUserIdFormValidation(userId)){
			return new ResponseEntity<>("아이디는 최소 3~12자 입니다",HttpStatus.BAD_REQUEST);
		}else{
			return new ResponseEntity<>("사용가능한 아이디 입니다",HttpStatus.OK);
		}
	}
	
}
