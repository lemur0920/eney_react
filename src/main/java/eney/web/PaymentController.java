package eney.web;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.ServerEndpoint;

import eney.domain.*;
import eney.service.SupplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import eney.domain.BillgateApprovalResponseVo;
import eney.domain.BillgatePayRequestVo;
import eney.domain.BillgatePayResponseVo;
import eney.domain.UserVO;
import eney.domain.ItemVo.ItemCategory;
import eney.domain.PaymentLogVo.PayMethod;
import eney.service.PaymentService;
import org.springframework.web.socket.WebSocketSession;

import javax.websocket.server.ServerEndpoint;



@Controller
@RequestMapping("/payment")
public class PaymentController {
	
	@Resource
	PaymentService paymentService;
	@Resource
	SupplyService supplyService;

	@Autowired
	private final SimpMessagingTemplate template;

	private static final List<String> sessionList = new ArrayList<>();

	@Autowired
	public PaymentController(SimpMessagingTemplate template) {
		this.template = template;
	}


	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/epoint/item", method=RequestMethod.GET)
	public ResponseEntity<?> getEpointItemInfo(){

		PaymentVO paymentVO = new PaymentVO();
		paymentVO.setCategory("epoint");
		List<PaymentVO> paymentList = supplyService.getPaymentList(paymentVO);
		return new ResponseEntity<>(paymentList, HttpStatus.OK);
	}

	/**
	 * {payMethod}로 결제
	 * @param payMethod (credit, mobile, happymoney, epoint, booklife, culture)
	 * @param serviceCode 서비스 코드
	 * @param itemParam1 구매상품 추가정보
	 * @param model
	 * @param request
	 * @return
	 */
	@PreAuthorize("isAuthenticated()")
	@RequestMapping(value="/{payMethod}/pay", method=RequestMethod.POST)
	public ResponseEntity<?> getPayRequestInfo(@PathVariable("payMethod") PaymentLogVo.PayMethod payMethod,
											   @RequestBody PaymentVO paymentInfo, String itemParam1, HttpServletRequest request, Authentication authentication){
		System.out.println("====");
		System.out.println(payMethod);
		System.out.println(paymentInfo);
		System.out.println(itemParam1);
		System.out.println(request.getRemoteAddr());

		System.out.println(authentication);
		System.out.println(authentication.getPrincipal());
		System.out.println("====");
		UserPrincipal user = (UserPrincipal) authentication.getPrincipal();

		String serviceCode = paymentInfo.getService_code();

//		if(serviceCode.equals(ItemVo.ItemCategory.web_hosting) || serviceCode.equals(ItemVo.ItemCategory.patchcall) ||
//				serviceCode.equals(ItemVo.ItemCategory.patchcall_other) || serviceCode.equals(ItemVo.ItemCategory.callback_sms)
//				||serviceCode.equals(ItemVo.ItemCategory.record)){
//			boolean result = paymentService.checkPaymentService(serviceCode, itemParam1);
//
//			if(result != true){
//
//				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//			}
//		}
		BillgatePayRequestVo payRequest = paymentService.genPaymentRequestForm(payMethod,
				serviceCode, itemParam1, user.getUserId(), request.getRemoteAddr());

		HashMap<String, Object> map = new HashMap<>();

		map.put("payRequest", payRequest);
		map.put("payMethod", payMethod);
		map.put("itemParam", itemParam1);
		map.put("paymentRequestUrl", paymentService.getPaymentRequestUrl(payMethod));

		return new ResponseEntity<>(map, HttpStatus.OK);


	}

//	@RequestMapping(value="/test")
//	@SendTo("/topic/test")
	@MessageMapping("/test")
	public void getTest(@Payload String msg, SimpMessageHeaderAccessor headerAccessor){

		System.out.println("받음 aa");

//		System.out.println(sessionList);
		System.out.println(headerAccessor.getSessionId());
		System.out.println(headerAccessor.getSessionAttributes());
		String sessionId = headerAccessor.getSessionId();

		System.out.println(msg);

		template.convertAndSend("/queue/res", sessionId);

	}

	public void setSocketSession(String sessionId){
		sessionList.add(sessionId);
		System.out.println(sessionId);

		System.out.println(sessionList);
	}


//	@RequestMapping(value="/{payMethod}/pay", method=RequestMethod.POST)
//	public String paySubmit(@PathVariable("payMethod") PaymentLogVo.PayMethod payMethod,
//								String serviceCode, String itemParam1,
//								Model model, HttpServletRequest request){
//
//		UserVO user = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//		if(serviceCode.equals(ItemVo.ItemCategory.web_hosting) || serviceCode.equals(ItemVo.ItemCategory.patchcall) ||
//				serviceCode.equals(ItemVo.ItemCategory.patchcall_other) || serviceCode.equals(ItemVo.ItemCategory.callback_sms)
//				||serviceCode.equals(ItemVo.ItemCategory.record)){
//			boolean result = paymentService.checkPaymentService(serviceCode, itemParam1);
//
//			if(result != true){
//				model.addAttribute("payMethod", payMethod);
//				model.addAttribute("itemParam", itemParam1);
//				return "payment/error";
//			}
//		}
//		BillgatePayRequestVo payRequest = paymentService.genPaymentRequestForm(payMethod,
//				serviceCode, itemParam1, user, request.getRemoteAddr());
//
//		model.addAttribute("payRequest", payRequest);
//		model.addAttribute("payMethod", payMethod);
//		model.addAttribute("itemParam", itemParam1);
//		model.addAttribute("paymentRequestUrl", paymentService.getPaymentRequestUrl(payMethod));
//
//		return "payment/pay";
//	}
	
	/**
	 * payMethod에 따라서 결제 방식 다르게 처리
	 * @param payMethod (credit, mobile, happymoney, epoint, booklife, culture)
	 * @param payResponse
	 * @param model
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(value="/{payMethod}/return")
	public void returnView(@PathVariable("payMethod") PaymentLogVo.PayMethod payMethod,BillgatePayResponseVo payResponse,  HttpServletRequest request) throws IOException{
//		UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//		UserVO user = new UserVO();
//		user.setUserid("eney1982");


		System.out.println("========");
		System.out.println(payResponse);
		System.out.println(payResponse.getRESERVED1());
		System.out.println("========");

//		template.convertAndSendToUser(payResponse.getRESERVED1(),"/queue/res2", "결제 결과");


		BillgateApprovalResponseVo approvalResponse;
		if(PaymentLogVo.PayMethod.epoint.equals(payMethod)){
			approvalResponse = paymentService.epointConfirmProcess(payResponse,request.getRemoteAddr());
		} else {
			approvalResponse = paymentService.paymentConfirmProcess(payMethod, payResponse, request.getRemoteAddr());
		}

//		model.addAttribute("payResponse", approvalResponse);

		if(approvalResponse == null
				|| ! BillgateApprovalResponseVo.RESPONSE_CODE_SUCCESS.equals(approvalResponse.getResponseCode())){
//			model.addAttribute("payResponse", approvalResponse);
			System.out.println("실패");
			template.convertAndSend("/queue/res/"+payResponse.getORDER_ID(), "fail");
//			return "payment/error";
		}

		System.out.println("결제 성공");
        System.out.println(payResponse.getORDER_ID());
		template.convertAndSend("/queue/res/"+payResponse.getORDER_ID(), "success");
//		return "payment/success";
	}



//	@RequestMapping(value="/epoint/pay.do", method=RequestMethod.GET)
//	public ModelAndView epointPayView(@ModelAttribute G_PayVO payVO){
//		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		ModelAndView mav = new ModelAndView();
//		mav.addObject("payVO", payVO);
//		mav.addObject("userVO",userVO);
//		return mav;
//	}
//	@RequestMapping(value="/epoint/pay.do", method=RequestMethod.POST)
//	public ModelAndView epointPaySubmit(@ModelAttribute PaymentVO payVO) throws Exception{
//		payVO.setPay_method("epoint");
//		G_PayVO gpayVO = paymentService.submitPaymentLog(payVO);
//		return epointPayView(gpayVO);
//	}
//	
//	@RequestMapping(value="/epoint/process.do", method=RequestMethod.POST)
//	public ModelAndView epointProcessSubmit(@ModelAttribute G_PayVO2 gpayVO2) throws PaymentAbusingException{
//		ModelAndView mav = new ModelAndView();
//		return mav;
//	}
//	
//	@RequestMapping(value="/epoint/return.do", method=RequestMethod.POST)
//	public ModelAndView epointReturnView(@ModelAttribute G_PayVO2 gpayVO2) throws PaymentAbusingException, PaymentLackException{
//		ModelAndView mav = new ModelAndView();
//		System.out.println("+===================");
//		System.out.println(gpayVO2);
//		
//		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		
//		PaymentVO payVO = new PaymentVO();
//		payVO.setService_code(gpayVO2.getITEM_CODE());
//		
//		payVO = paymentService.getPaymentVO(payVO);
//		
//		
//		G_PayVO logVo = paymentService.getGPaymentVO(gpayVO2);
//		
//		payVO.setUserid(userVO.getUserid());
//		
//		StringTokenizer st = new StringTokenizer(logVo.getData1(),",");
//		
//		if(payVO.getService_code().substring(0, 2).equals("UA"))
//				payVO.setSale_price(payVO.getSale_price()*st.countTokens());
//		
//		userVO = paymentService.deductEpoint(payVO);
//		
//		AgentVO agentVO = new AgentVO();
//		if(payVO.getCategory().equals("utilization_050")){
//			G_PayVO res = paymentService.updatePaymentLog(gpayVO2);
//			agentVO = paymentService.extend050Agent(res);
//		}else if(payVO.getCategory().equals("adpower")){
//			G_PayVO res = paymentService.updatePaymentLog(gpayVO2);
//			//TODO:: ADPOWER 결제 완료 후 처리해줘야 될 부분
//		}
//		//TODO:: epoint로 결제 로그안남겨지고있음..
//		
//		try {
//			agentVO.setVno(logVo.getData1());
//			gpayVO2.setMessage("성공");
//			gpayVO2.setDetailMessage("정상 처리 되었습니다.");
//			
//			paymentService.sendPaymentAlert(payVO, userVO);
//			mav.addObject("userVO",userVO);
//			mav.addObject("gpayVO",gpayVO2);
//			mav.addObject("itemVO", paymentService.getPaymentVO(gpayVO2));
//			mav.addObject("agentVO", agentVO);
//		} catch (PaymentAbusingException e) {
//			logger.debug("Custom Log::: error was occured --- epoint return...");
//		}
//		
//		return mav;
//	}
}