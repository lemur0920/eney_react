package eney.web;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import eney.domain.*;
import eney.util.EncryptUtil;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import eney.domain.PaymentLogVo.PayStatus;
import eney.service.AcsService;
import eney.service.AdminService;
import eney.service.BoardService;
import eney.service.FileService;
import eney.service.InvoiceService;
import eney.service.MailService;
import eney.service.SupplyService;
import eney.service.UserService;
import eney.util.EncryptUtil;

@Controller
@RequestMapping("/introduce")
public class introduceController {
	
	@Resource
	HttpServletRequest request;
	
	@Resource
	BoardService boardService;
	@Resource
	FileService fileService;
	@Resource
	UserService userService;
	@Resource
	MailService mailService;
	@Resource
	SupplyService supplyService;
	@Resource
	AcsService acsService;
	@Resource
	AdminService adminService;
	
	@Resource
	InvoiceService invoiceService;
	
	@InitBinder
    protected void initBinder(WebDataBinder binder){
        DateFormat  dateFormat = new SimpleDateFormat("yyyy-MM");
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat,true));
    }

	/**
	 * 신청 성공 스크립트 실행
	 */
	@RequestMapping("/regist_success.do")
	public ModelAndView registSuccessView(){		
		ModelAndView mv = new ModelAndView();	
		return mv;		
	}
	/**
	 * 신청 실패 스크립트 실행
	 */
	@RequestMapping("/regist_fail.do")
	public ModelAndView registFailView(){		
		ModelAndView mv = new ModelAndView();	
		return mv;		
	}
	/**
	 * 결제 권한 없음 스크립트 실행
	 */
	@RequestMapping("/pay_access_deny.do")
	public ModelAndView payAccessDenyView(){		
		ModelAndView mv = new ModelAndView();	
		return mv;		
	}
	@RequestMapping("/exist_service.do")
	public ModelAndView existServiceView(){		
		ModelAndView mv = new ModelAndView();	
		return mv;		
	}
	@RequestMapping("/message_regist.do")
	public ModelAndView messageRegistView(){		
		ModelAndView mv = new ModelAndView();	
		return mv;		
	}


	@RequestMapping("/bi/regist_pay_bi.do")
	public ModelAndView registPayBiView(Integer idx) throws ParseException{
		ModelAndView mv = new ModelAndView();
		UserVO sessionVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceBIVO biService = userService.getBiService(idx);
		if(sessionVO.getUserid().equals(biService.getUserid()) ){
			mv.addObject("biService", biService);
		}else{
			mv.setViewName("redirect:/introduce/pay_access_deny.do");
		}
		return mv;
	}

	/**
	 * 패치콜 결제 페이지
	 * @param patchcall_idx 패치콜 idx
	 * @throws ParseException 
	 */
	@RequestMapping("/patchcall/regist_pay_patchcall.do")
	public ModelAndView registPayPatchcallView(Integer patchcall_idx) throws ParseException{		
		ModelAndView mv = new ModelAndView();
		UserVO sessionVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServicePatchcallVO patchcallService = userService.selectPatchcallService(patchcall_idx);
		if(sessionVO.getUserid().equals(patchcallService.getUserid()) ){
			mv.addObject("patchcallService", patchcallService);
		}else{
			mv.setViewName("redirect:/introduce/pay_access_deny.do");
		}
		return mv;
	}
	/**
	 * 080번호, 대표번호, 녹취, shortURL 결제 페이지
	 * @param idx
	 * @throws ParseException 
	 */
	@RequestMapping("/patchcall/regist_pay_patchcall_etc.do")
	public ModelAndView registPayPatchcallEtcView(Integer idx) throws ParseException{		
		ModelAndView mv = new ModelAndView();
		UserVO sessionVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServicePatchcallOtherVO patchcallService = userService.selectPatchcallEtcService(idx);
		if(sessionVO.getUserid().equals(patchcallService.getUserid())){
			mv.addObject("patchcallService", patchcallService);
			
		}else{
			mv.setViewName("redirect:/introduce/pay_access_deny.do");
		}
		return mv;
	}
	/**
	 * 콜백 SMS 결제 페이지
	 * @param idx
	 * @throws ParseException 
	 */
	@RequestMapping("/patchcall/callback_sms_pay.do")
	public ModelAndView registPayCallbackSmsView(Integer idx) throws ParseException{		
		ModelAndView mv = new ModelAndView();
		UserVO sessionVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CallbackSmsVO callbackService = userService.selectCallbackSmsService(idx);
		if(sessionVO.getUserid().equals(callbackService.getUserid())){
			mv.addObject("callbackService", callbackService);
		}else{
			mv.setViewName("redirect:/introduce/pay_access_deny.do");
		}
		return mv;
	}
	
	@RequestMapping("/patchcall/record_pay.do")
	public ModelAndView registPayRecordView(Integer idx) throws ParseException{		
		ModelAndView mv = new ModelAndView();
		UserVO sessionVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		RecordVO RecordService = userService.selectRecordService(idx);
		if(sessionVO.getUserid().equals(RecordService.getUserid())){
			mv.addObject("RecordService", RecordService);
			
		}else{
			mv.setViewName("redirect:/introduce/pay_access_deny.do");
		}
		return mv;
	}
	
	/**
	 * 패치콜 소개 페이지 연결
	 */
	@RequestMapping("/patchcall/patchcall.do")
	public ModelAndView patchcallView(){		
		ModelAndView mv = new ModelAndView();	
		return mv;		
	}
	
	/**
	 * 컬러링 소개 페이지
	 * @param model
	 * @param category
	 * @throws Throwable
	 */
	@RequestMapping("/patchcall/coloring.do")
	public void coloringListenView(ModelMap model, @RequestParam(defaultValue="education") String category) 
			throws Throwable{
		ColoringSampleVO sampleVO = new ColoringSampleVO();
		sampleVO.setCategory(category);
		List<ColoringSampleVO> sampleList = supplyService.getSampleList(sampleVO);
		CommonVO commonVO = new CommonVO();
		commonVO.setCategory("COLORING_SAMPLE_CATE"); 
		List<CommonVO> commonList = supplyService.getCommonList(commonVO);
		model.addAttribute("sampleList", sampleList);
		model.addAttribute("commonList", commonList);
	}

	/**
	 * 컬러링 제작 신청 페이지 뷰
	 * @param paramMap
	 * @param model
	 * @throws Throwable
	 */
	@RequestMapping(value="/patchcall/coloring/register.do", method=RequestMethod.GET)
	public void coloringRegisterView(@RequestParam Map<String, Object> paramMap, ModelMap model)
			throws Throwable{
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ColoringSampleVO sampleVO = new ColoringSampleVO();
		sampleVO.setGubun("REGISTER");
		List<ColoringSampleVO> sampleList = supplyService.getSampleList(sampleVO);
		model.addAttribute("sampleList", sampleList);
		model.addAttribute("userVO", userVO);
	}

	/**
	 * 컬러링 제작 신청
	 * @param registerVO
	 * @return
	 */
	@RequestMapping(value="/patchcall/coloring/register.do", method=RequestMethod.POST)
	public ModelAndView coloringRegisterSubmit(@ModelAttribute ColoringRegisterVO registerVO){
		ModelAndView mav = new ModelAndView();
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		registerVO.setUserid(userVO.getUserid());
		String res = supplyService.submitColoringRegister(registerVO, userVO);

		if(res==null)
			mav.addObject("script", "alert('신청이 완료되었습니다.')");
		else{
			mav.addObject("script", "alert('"+res+"')");
		}
		return mav;
	}

	@RequestMapping("/patchcall/price_info.do")
	public ModelAndView priceInfoView(){		
		ModelAndView mv = new ModelAndView();	
		return mv;		
	}
	
	@RequestMapping("/messaging/big_message.do")
	public ModelAndView bigMessageView(){		
		ModelAndView mv = new ModelAndView();	
		return mv;		
	}
	@RequestMapping("/messaging/big_price.do")
	public ModelAndView bigPriceView(){		
		ModelAndView mv = new ModelAndView();	
		return mv;		
	}
	
	@RequestMapping("/idc/idc.do")
	public ModelAndView idcView(){		
		ModelAndView mv = new ModelAndView();	
		return mv;		
	}
	@RequestMapping("/idc/vpn.do")
	public ModelAndView vpnView(){		
		ModelAndView mv = new ModelAndView();	
		return mv;		
	}
	@RequestMapping("/idc/idc_price.do")
	public ModelAndView idcPriceView(){		
		ModelAndView mv = new ModelAndView();	
		return mv;		
	}
	
	@RequestMapping("/bi/bi.do")
	public ModelAndView biView(){		
		ModelAndView mv = new ModelAndView();	
		return mv;		
	}
	@RequestMapping("/bi/bi_price.do")
	public ModelAndView biPriceView(){		
		ModelAndView mv = new ModelAndView();	
		return mv;		
	}
	
	@RequestMapping("/bi/advertising.do")
	public ModelAndView advertisingView(){		
		ModelAndView mv = new ModelAndView();	
		return mv;		
	}

	@RequestMapping(value = "/bi/bi_regist.do")
	public ModelAndView biRegistView(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		UserVO sessionVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String priceType = request.getParameter("type");
		ServicePatchcallVO service = userService.getServiceListCheckPublishEmail(sessionVO);
		List<BiCategoryVO> categoryList = userService.getCategoryList();

		mv.addObject("categoryList", categoryList);
		mv.addObject("service",service);
		mv.addObject("session",sessionVO);
		mv.addObject("priceType",priceType);

		return mv;
	}



	/**
	 * 패치콜 신청 페이지뷰
	 * @return
	 */
	@RequestMapping(value = "/patchcall/patchcall_regist.do")
	public ModelAndView patchcallRegistView(HttpServletRequest request){		
		ModelAndView mv = new ModelAndView();	
		UserVO sessionVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String priceType = request.getParameter("type");
		ServicePatchcallVO service = userService.getServiceListCheckPublishEmail(sessionVO);
		
		mv.addObject("service",service);
		mv.addObject("session",sessionVO);
		mv.addObject("priceType",priceType);
	
		return mv;		
	}

	@RequestMapping(value = "/patchcall/patchcall_test.do")
	public ModelAndView patchcallTestRegistView(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();
		UserVO sessionVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServicePatchcallVO service = userService.getServiceListCheckPublishEmail(sessionVO);

		mv.addObject("service",service);
		mv.addObject("session",sessionVO);

		return mv;
	}
	/**
	 * 080번호, 전국대표번호, 녹취, shortURL 서비스 신청 페이지뷰
	 * @return
	 */
	@RequestMapping(value = "/patchcall/patchcall_etc_regist.do")
	public ModelAndView patchcallEtcRegistView(HttpServletRequest request){		
		ModelAndView mv = new ModelAndView();	
		UserVO sessionVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String etc_type = request.getParameter("etc_type");
		ServicePatchcallVO service = userService.getServiceListCheckPublishEmail(sessionVO);
		
		mv.addObject("service",service);
		mv.addObject("session",sessionVO);
		mv.addObject("etc_type",etc_type);
		return mv;		
	}
	
	@RequestMapping(value = "/patchcall/callback_regist.do")
	public ModelAndView callbackRegistView(HttpServletRequest request){		
		ModelAndView mv = new ModelAndView();	
		UserVO sessionVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String etc_type = request.getParameter("etc_type");
		ServicePatchcallVO service = userService.getServiceListCheckPublishEmail(sessionVO);
		
		mv.addObject("service",service);
		mv.addObject("session",sessionVO);
		mv.addObject("priceType",etc_type);
	
		return mv;		
	}
	
	@RequestMapping(value = "/patchcall/record_regist.do")
	public ModelAndView recordRegistView(HttpServletRequest request){		
		ModelAndView mv = new ModelAndView();	
		UserVO sessionVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String etc_type = request.getParameter("etc_type");
		ServicePatchcallVO service = userService.getServiceListCheckPublishEmail(sessionVO);
		
		mv.addObject("service",service);
		mv.addObject("session",sessionVO);
		mv.addObject("priceType",etc_type);
	
		return mv;		
	}
	/**
	 * 패치콜 서비스 신청
	 * @param userVO 
	 * @param password 패스워드 확인
	 * @param result
	 * @throws Exception 
	 */
	@RequestMapping(value = "/patchcall/regist_patchcall.do" ,method=RequestMethod.POST)
	public ModelAndView patchcallRegist (@ModelAttribute ServicePatchcallVO userVO, String password, BindingResult result) throws Exception{
		ModelAndView mv = new ModelAndView();	
		
		UserVO sessionVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
 		mv.addObject("session",sessionVO);
		//비밀번호 재확인
		if(sessionVO.getPassword().equals(EncryptUtil.encryptSHA256(password))){

			if(userVO.getService_type().equals("test")){
				userVO.setPay_state(PaymentLogVo.PayStatus.approve);
				userVO.setPay_way("test");
				userService.insertPatchcall(userVO);

				mv.setViewName("redirect:/introduce/regist_success.do");

				return mv;
			}

			if(userVO.getPay_way().equals("auto_transfer") || userVO.getPay_way().equals("account_transfer")){
				userVO.setPay_state(PaymentLogVo.PayStatus.approve);
				userService.insertPatchcall(userVO);
				List<MultipartFile> files = userVO.getFiles();
				List<FileVO> fileVOList = fileService.processUpload(files, "CORPORATE_PATCHCALL", userVO.getPatchcall_idx(), request);
				userService.sendEmail(sessionVO);
			}else{
				userVO.setPay_state(PaymentLogVo.PayStatus.standby);
				userService.insertPatchcall(userVO);
			}
			mv.setViewName("redirect:/introduce/patchcall/regist_pay_patchcall.do?patchcall_idx=" + userVO.getPatchcall_idx() + "");
		}else{
			mv.addObject("userVO",userVO);
			mv.setViewName("redirect:/introduce/regist_fail.do");
		}
		return mv;
	}

	@RequestMapping(value = "/bi/bi_regist.do" ,method=RequestMethod.POST)
	public ModelAndView patchcallRegist (@ModelAttribute ServiceBIVO userVO, String password, BindingResult result) throws Exception{
		ModelAndView mv = new ModelAndView();

		UserVO sessionVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mv.addObject("session",sessionVO);
		//비밀번호 재확인
 		if(sessionVO.getPassword().equals(EncryptUtil.encryptSHA256(password))){
			if(userVO.getPay_way().equals("auto_transfer") || userVO.getPay_way().equals("account_transfer")){
				userVO.setPay_state(PaymentLogVo.PayStatus.approve);
				userService.insertBI(userVO);
				userService.insertBIInfo(userVO);
				List<MultipartFile> files = userVO.getFiles();
				List<FileVO> fileVOList = fileService.processUpload(files, "CORPORATE_BI", userVO.getIdx(), request);
				userService.sendEmail(sessionVO);
			}else{
				userVO.setPay_state(PaymentLogVo.PayStatus.standby);
				userService.insertBI(userVO);
				userService.insertBIInfo(userVO);
			}
			mv.setViewName("redirect:/introduce/bi/regist_pay_bi.do?idx=" + userVO.getIdx() + "");
		}else{
			mv.addObject("userVO",userVO);
			mv.setViewName("redirect:/introduce/regist_fail.do");
		}
		return mv;
	}
	
	
	/**
	 * 080번호, 전국대표번호, 녹취, ShortURL 서비스 신청
	 * @param userVO
	 * @param password
	 * @param result
	 * @throws Exception 
	 */
	@RequestMapping(value = "/patchcall/regist_patchcall_etc.do" ,method=RequestMethod.POST)
	public ModelAndView patchcallRegist (@ModelAttribute ServicePatchcallOtherVO userVO, RecordVO recordVO, String password, BindingResult result) throws Exception{
		ModelAndView mv = new ModelAndView();	
		UserVO sessionVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CallbackSmsVO callbackVO = new CallbackSmsVO();
		recordVO.setUserid(userVO.getUserid());
		callbackVO.setUserid(userVO.getUserid());
		//비밀번호 재확인
		if(sessionVO.getPassword().equals(EncryptUtil.encryptSHA256(password))){
			PaymentLogVo.PayStatus state;
			if(userVO.getPay_way().equals("auto_transfer") || userVO.getPay_way().equals("account_transfer")){
				state = PaymentLogVo.PayStatus.approve;
			}else{
				state = PaymentLogVo.PayStatus.standby;
			}
			if(userVO.getService_type().equals("콜백SMS")){
				CallbackSmsVO callback = userService.checkCallbackSmsService(userVO.getUserid());
				if(callback == null){
					userVO.setPay_state(state);
//					userService.insertCallbacksms(userVO);
					List<MultipartFile> files = userVO.getFiles();
					List<FileVO> fileVOList = fileService.processUpload(files, "CORPORATE_CALLBACK", userVO.getIdx(), request);
					mv.setViewName("redirect:/introduce/patchcall/callback_sms_pay.do?idx=" + userVO.getIdx() + "");
				}else{
					if(callback.getPay_state().equals(PaymentLogVo.PayStatus.standby)){
						/*결제 실패시 새로 신청*/
						UserVO user = new UserVO();
						user.setUserid(userVO.getUserid());
						userService.deleteCallback(user);
						
						userVO.setPay_state(state);
//						userService.insertCallbacksms(userVO);
						List<MultipartFile> files = userVO.getFiles();
						List<FileVO> fileVOList = fileService.processUpload(files, "CORPORATE_CALLBACK", userVO.getIdx(), request);
						mv.setViewName("redirect:/introduce/patchcall/callback_sms_pay.do?idx=" + userVO.getIdx() + "");
					}else{
						mv.setViewName("redirect:/introduce/exist_service.do");
					}
				}
			}else if(userVO.getService_type().equals("녹취")){
				RecordVO record = userService.selectRecordServiceListByUserVO(recordVO);
				if(record == null){
					recordVO.setPay_state(state);
					userService.insertRecord(recordVO);
					List<MultipartFile> files = recordVO.getFiles();
					List<FileVO> fileVOList = fileService.processUpload(files, "CORPORATE_RECORD", recordVO.getIdx(), request);
					mv.setViewName("redirect:/introduce/patchcall/record_pay.do?idx=" + recordVO.getIdx() + "");
				}else{
					if(record.getPay_state().equals(PaymentLogVo.PayStatus.standby)){
						/*결제 실패시 새로 신청*/
						UserVO user = new UserVO();
						user.setUserid(userVO.getUserid());
						userService.deleteRecord(user);
						
						recordVO.setPay_state(state);
						userService.insertRecord(recordVO);
						List<MultipartFile> files = recordVO.getFiles();
						List<FileVO> fileVOList = fileService.processUpload(files, "CORPORATE_RECORD", recordVO.getIdx(), request);
						
						mv.setViewName("redirect:/introduce/patchcall/record_pay.do?idx=" + recordVO.getIdx() + "");
					}else{
						mv.setViewName("redirect:/introduce/exist_service.do");
					}
				}
			}
			else{
				userVO.setPay_state(state);
				userService.insertPatchcallOther(userVO);
				List<MultipartFile> files = userVO.getFiles();
				List<FileVO> fileVOList = fileService.processUpload(files, "CORPORATE_OTHER", userVO.getIdx(), request);

				mv.setViewName("redirect:/introduce/patchcall/regist_pay_patchcall_etc.do?idx=" + userVO.getIdx() + "");
			}
		}else{
			mv.addObject("userVO",userVO);
			mv.setViewName("redirect:/introduce/regist_fail.do");
		}
		return mv;
	}
	
	/**
	 * 패치콜 신청방법안내 페이지 연결
	 */
	@RequestMapping("/patchcall/patchcall_apply.do")
	public ModelAndView patchcallApplyView(){		
		ModelAndView mv = new ModelAndView();	
		return mv;		
	}

	/**
	 * 호스팅 서비스 신청 페이지 뷰
	 * @return
	 */
	@RequestMapping("/idc/reg_info.do")
	public ModelAndView hostingRegInfoView(){		
		ModelAndView mv = new ModelAndView();
		UserVO sessionVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String priceType = request.getParameter("type");
		ServicePatchcallVO service = userService.getServiceListCheckPublishEmail(sessionVO);
		
		mv.addObject("service",service);
		mv.addObject("session",sessionVO);
		mv.addObject("priceType",priceType);

		return mv;		
	}

	/**
	 * 웹호스팅 서비스 신청
	 * @param userVO
	 * @param password
	 * @param result
	 * @throws Exception 
	 */
	@ResponseBody
	@RequestMapping(value="/idc/web_reg.do", method=RequestMethod.POST)
	public ModelAndView insertWebHosting(@ModelAttribute ServiceWebHostingVO userVO, String password, BindingResult result) throws Exception{
		ModelAndView mv = new ModelAndView();

		UserVO sessionVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//비밀번호 재확인
		if(sessionVO.getPassword().equals(EncryptUtil.encryptSHA256(password))){
			if(userVO.getPay_way().equals("auto_transfer") || userVO.getPay_way().equals("account_transfer")){
				userVO.setPay_state(PaymentLogVo.PayStatus.approve);
				userService.insertWebHosting(userVO);
				List<MultipartFile> files = userVO.getFiles();
				List<FileVO> fileVOList = fileService.processUpload(files, "CORPORATE_WEB", userVO.getWeb_hosting_idx(), request);
				userService.sendEmail(sessionVO);
			}else{
				userVO.setPay_state(PaymentLogVo.PayStatus.standby);
				userService.insertWebHosting(userVO);
			}
			
			mv.setViewName("redirect:/introduce/idc/regist_pay.do?web_hosting_idx=" + userVO.getWeb_hosting_idx() + "");
		}else{
			mv.addObject("userVO",userVO);
			mv.setViewName("redirect:/introduce/regist_fail.do");
		}
		
		return mv;
	}
	/**
	 * 웹호스팅 결제 페이지
	 * @param web_hosting_idx
	 * @throws ParseException 
	 */
	@RequestMapping("/idc/regist_pay.do")
	public ModelAndView registPayView(Integer web_hosting_idx) throws ParseException{		
		ModelAndView mv = new ModelAndView();
		UserVO sessionVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ServiceWebHostingVO webService = userService.selectWebHosting(web_hosting_idx);
		if(sessionVO.getUserid().equals(webService.getUserid())){
			mv.addObject("webService", webService);
		}else{
			mv.setViewName("redirect:/introduce/pay_access_deny.do");
		}
		return mv;
	}

	/**
	 * 메시징 서비스 신청 페이지 뷰
	 * @return
	 */
	@RequestMapping(value ="/messaging/messaging_regist.do"  , method=RequestMethod.GET)
	public ModelAndView messagingRegistView(){		
		ModelAndView mv = new ModelAndView();
		UserVO sessionVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mv.addObject("session",sessionVO);
		return mv;		
	}
	
	@RequestMapping(value ="/messaging/messaging_regist.do"  , method=RequestMethod.POST)
	public ModelAndView insertMessaging(@ModelAttribute ServiceMessagingVO userVO, String password, BindingResult result) throws Exception{
		ModelAndView mv = new ModelAndView();

		UserVO sessionVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		ServiceMessagingVO ip_check = userService.getUserIpForUserVO(sessionVO); 
		//비밀번호 재확인
		if(sessionVO.getPassword().equals(EncryptUtil.encryptSHA256(password))){
			if(ip_check == null){
				userService.insertMessaging(userVO);
				mv.setViewName("redirect:/introduce/message_regist.do");
			}else{
				mv.setViewName("redirect:/introduce/exist_service.do");
			}
		}else{
			mv.addObject("userVO",userVO);
			mv.setViewName("redirect:/introduce/regist_fail.do");
		}
		
		return mv;
	}
	
	/**
	 * 발신번호 등록 팝업 뷰
	 * @return
	 */
	@RequestMapping("/pop/messaging/messaging_sending_number")
	public String messagingSendNumberView(){		
		return "introduce/messaging/messaging_sending_number";
	}
	
	/**
	 * 발신번호 체크
	 * @param mobile
	 * @return
	 * @throws IOException
	 */
	@ResponseBody
	@RequestMapping(value="/messaging/check_number.do"  , method=RequestMethod.POST)
	public boolean messagingCheckNumber(String mobile) throws IOException{		
		List<AcsTransmitVO> messageNumberList = acsService.getMessaingNumberList();
		String temp = "";
		Boolean result = false;
		
		temp = mobile.replaceAll("-", "");
		
		if(temp.matches("^(02|0[3-6]\\d|01(0|1|3|5|6|7|8|9)|070|080|007)-?\\d{3,4}-?\\d{4,5}$") ||
			temp.matches("^(15|16|18)\\d{2}-?\\d{4,5}$")){
			if(temp.matches("^(02|0[3-6]\\d|01(0|1|3|5|6|7|8|9)|070|080)-?0{3,4}-?\\d{4}$")) {
				result = false;
			}else{
				AcsTransmitVO acsVO = new AcsTransmitVO();
				acsVO.setTel_num(mobile);
				
				AcsTransmitVO acs = acsService.getMessagingDupleCheck(mobile);
				if(acs == null || acs.getStatus() == null || acs.getStatus().equals("I") ){
					result = true;
				}else{
					result = false;
				}
			}
		}else{
			result = false;
		}
		return result;
		
	}

	
			
}
