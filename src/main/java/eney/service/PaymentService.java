package eney.service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.Base64;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import eney.domain.*;
import eney.exception.PaymentAbusingException;
import eney.domain.ItemVo;
import eney.exception.PaymentException;
import eney.exception.PaymentLackException;
import eney.property.EneyProperties;
import eney.property.PaymentProperties;
import eney.util.DateUtil;
import eney.util.EtcUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

//import eney.domain.ItemVo.ItemCategory;
import eney.domain.PaymentLogVo.PayMethod;
import eney.domain.PaymentLogVo.PayStatus;
import eney.exception.PaymentAbusingException;
import eney.exception.PaymentException;
import eney.exception.PaymentLackException;
import eney.mapper.PaymentDao;
import eney.mapper.SupplyDao;
import eney.property.EneyProperties;
import eney.property.PaymentProperties;
import eney.util.DateUtil;
import eney.util.EtcUtil;
import com.galaxia.api.Command;
import com.galaxia.api.ConfigInfo;
import com.galaxia.api.MessageTag;
import com.galaxia.api.ServiceCode;
import com.galaxia.api.cashreceipt.ServiceBroker;
import com.galaxia.api.crypto.GalaxiaCipher;
import com.galaxia.api.crypto.Seed;
import com.galaxia.api.merchant.Message;
import com.galaxia.api.util.ChecksumUtil;



@Service
public class PaymentService {
	
	public static final String GALAXIA_API_VERSION = "0100" ;

	private static final Logger logger = LoggerFactory.getLogger(PaymentService.class);
	private static final String DATE_STRIONG_FORMAT = "yyyyMMddHHmmss";
	
	private static final String LOG_PREFIX_WEB_HOSTING_PAYMENT = "[Web Hosting 결제]";
	private static final String LOG_PREFIX_PATCHCALL_PAYMENT="[Patchcall 결제]";
	
	@Resource
	JoinService joinService;
	@Resource(name="userService")
    UserService userService;
	@Resource
	AlertService alertService;
	@Resource
	MailService mailService;
	@Resource
	SupplyService supplyService;
	@Resource
	PaymentDao paymentDao;
	@Resource 
	SupplyDao supplyDao;
	
	private PaymentProperties paymentProperties;
	
	public PaymentVO getPaymentVO(PaymentVO payVO) throws PaymentAbusingException {
		PaymentVO paymentVO = paymentDao.getPaymentVO(payVO);
		if(paymentVO==null)
			throw new PaymentAbusingException(payVO.getUserid());
		
		return paymentVO;
	}
	
	public PaymentVO getPaymentVO(G_PayVO2 gpayVO2) throws PaymentAbusingException {
		PaymentVO payVO = new PaymentVO();
		payVO.setService_code(gpayVO2.getITEM_CODE());
		PaymentVO paymentVO = paymentDao.getPaymentVO(payVO);
		if(paymentVO==null)
			throw new PaymentAbusingException(payVO.getUserid());
		return paymentVO;
	}
	
	public G_PayVO getGPaymentVO(G_PayVO gPayVO){
		return paymentDao.getGPaymentVO(gPayVO);
	}

	public void submitPaymentLog(G_PayVO gpayVO) {
		gpayVO.setStatus("paying");
		paymentDao.submitPaymentLog(gpayVO);
	}
	
	public PaymentVO getItemData(G_PayVO gpayVO) throws PaymentAbusingException{
		PaymentVO param = new PaymentVO();
		gpayVO = getGPaymentVO(gpayVO);
		param.setService_code(gpayVO.getService_code());
		return getPaymentVO(param);
	}
	
	public G_PayVO updatePaymentLog(G_PayVO gpayVO){
		paymentDao.updatePaymentLog(gpayVO);
		return paymentDao.getGPaymentVO(gpayVO);
	}
	
	public G_PayVO updatePaymentLog(G_PayVO2 gpayVO2) {
		G_PayVO gpayVO = new G_PayVO();
		gpayVO.setMessage("성공");
		gpayVO.setStatus("approve");
		gpayVO.setOrderId(gpayVO2.getORDER_ID());
		paymentDao.updatePaymentLog(gpayVO);
		return paymentDao.getGPaymentVO(gpayVO);
	}

	public G_PayVO submitPaymentLog(PaymentVO payVO, String pay_method) throws Exception{
		
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		payVO.setUserid(userVO.getUserid());
		//인증!
		PaymentVO payVO_db = getPaymentVO(payVO);
		
		G_PayVO gpayVO = new G_PayVO();
		gpayVO.setUserId(userVO.getUserid());
		gpayVO.setUserIp(EtcUtil.getLocalServerIp());
		gpayVO.setUserName(userVO.getName());
		if(!pay_method.equals("epoint"))
			gpayVO.setAmount(String.valueOf(payVO_db.getTaxIncludedAmount()));
		else
			gpayVO.setAmount(String.valueOf(payVO_db.getSale_price()));
		gpayVO.setItemCode(payVO_db.getService_code());
		gpayVO.setItemName(payVO_db.getService_name());
		gpayVO.setItemCate(payVO_db.getCategory());
		if(payVO_db.getCategory().equals("utilization_050")){
			gpayVO.setData1(payVO.getService_data());
			gpayVO.setData2(payVO_db.getService_period());
		}
		gpayVO.setPay_method(pay_method);
		
		String checkSum = gpayVO.getCheckSum();
		gpayVO.setCheckSum(checkSum);
		//payVO.setLog_cate("payment");
		//payVO.setSerial_number(payVO.generateSerialNumber());
		submitPaymentLog(gpayVO);
		return gpayVO;
	}

	public UserVO deductEpoint(PaymentVO payVO,UserVO user) throws PaymentLackException {
//		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
//		Authentication authentication = customAuthProvider.reloadSession(userVO); 
//		SecurityContext context = SecurityContextHolder.getContext();                          
//       	context.setAuthentication(authentication);
//       	userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		//userVO.setEpoint(String.valueOf(Integer.parseInt(.getEpoint())-payVO.getSale_price()));
		if(payVO.getUserid()==null)
			throw new PaymentLackException("결제자의 정보가 누락되었습니다.");
		UserVO paramVO =userService.loadUserByUsername(payVO.getUserid());
		paramVO.setEpoint(String.valueOf(Integer.parseInt(paramVO.getEpoint())-payVO.getSale_price()));
		
		if(Integer.parseInt(paramVO.getEpoint())<0)
			throw new PaymentLackException("EPOINT 잔액이 부족합니다.");
		user = joinService.updateUserInfo(paramVO);
		payVO.setBalance_epoint(Integer.parseInt(user.getEpoint()));
		payVO.setCharge_epoint(payVO.getSale_price());
		paymentDao.insertEpointChargeLog(payVO);
		return user;
	}
	
	public boolean deductEpointForMsg(Map<String,String> map) throws PaymentLackException{
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		PaymentVO payVO = new PaymentVO();
		String mode = String.valueOf(map.get("mode"));
		Integer charge_point = Integer.parseInt(String.valueOf(map.get("charge_point")));

		boolean result = false;

		//msg_type
		//1 : SMS , 3 : MMS (file 없을 시 LMS)
		//6 : 알림톡, 7:친구톡

		//sms : 20원 lms : 30원 mms : 100원 알림톡 : 8원 친구톡(텍스트) :17원 친구톡(이미지):25원
		
		payVO.setUserid(userVO.getUserid());

		payVO.setService_code(mode);
		payVO.setService_name("메시지 전송_" + mode);
		payVO.setSale_price(charge_point);
		payVO.setCharge_epoint(charge_point);
		payVO.setBalance_epoint(Integer.parseInt(userVO.getEpoint()) - charge_point);

		UserVO paramVO =userService.loadUserByUsername(payVO.getUserid());

		if(Integer.parseInt(paramVO.getEpoint())< charge_point){
			throw new PaymentLackException("EPOINT 잔액이 부족합니다.");
		}

		paramVO.setEpoint(String.valueOf(Integer.parseInt(paramVO.getEpoint())-payVO.getSale_price()));

		joinService.updateUserInfo(paramVO);
		
		paymentDao.insertEpointChargeLog(payVO);

		return result;
	}
	
	
	/**
	 * 회원 epoint 사용내역 로그
	 * @param paymentVO
	 * @return epoint 사용내역 로그 출력
	 */
	public Map<String, Object> getEpointLogs(EpointLogVo epointLogVo) {
		epointLogVo.setTotalCount(paymentDao.getEpointLogCnt(epointLogVo));

		Map<String, Object> map = new HashMap<>();

		epointLogVo.setTotalCount(paymentDao.getEpointLogCnt(epointLogVo));
		List<EpointLogVo> logList = paymentDao.getEpointLogs(epointLogVo);

		map.put("logList", logList);
		map.put("page", epointLogVo);

		return map;
	}
	
	/**
	 * 회원 결제내역 로그
	 * @param paymentVO
	 * @return 결제내역 로그 출력
	 */
	public Map<String, Object> getPaymentLogs(PaymentVO paymentVO) {

		paymentVO.setTotalCount(paymentDao.getPaymentLogCnt(paymentVO));

		Map<String, Object> map = new HashMap<>();

		List<PaymentVO> logList = paymentDao.getPaymentLogs(paymentVO);

		map.put("logList", logList);
		map.put("page", paymentVO);


		return map;
	}

	public G_PayVO submitPaymentLog(PaymentVO payVO) throws Exception {
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		payVO.setUserid(userVO.getUserid());
		//인증!
		PaymentVO payVO_db = getPaymentVO(payVO);
		
		G_PayVO gpayVO = new G_PayVO();
		gpayVO.setUserId(userVO.getUserid());
		gpayVO.setUserIp(EtcUtil.getLocalServerIp());
		gpayVO.setUserName(userVO.getName());
		if(!payVO.getPay_method().equals("epoint"))
			gpayVO.setAmount(String.valueOf(payVO_db.getTaxIncludedAmount()));
		else
			gpayVO.setAmount(String.valueOf(payVO_db.getSale_price()));
		gpayVO.setItemCode(payVO_db.getService_code());
		gpayVO.setItemName(payVO_db.getService_name());
		gpayVO.setItemCate(payVO_db.getCategory());
		//050번호 연장
		if(payVO_db.getCategory().equals("utilization_050")){
			StringTokenizer st = new StringTokenizer(payVO.getService_data(),",");
			
			gpayVO.setAmount(String.valueOf(Integer.parseInt(gpayVO.getAmount())*st.countTokens()));
			
			while(st.hasMoreTokens()) {
				System.out.println(st.nextToken());
			}
			
			gpayVO.setData1(payVO.getService_data());
			gpayVO.setData2(payVO_db.getService_period());
		}
		gpayVO.setPay_method(payVO.getPay_method());
		
		String checkSum = gpayVO.getCheckSum();
		gpayVO.setCheckSum(checkSum);
		//payVO.setLog_cate("payment");
		//payVO.setSerial_number(payVO.generateSerialNumber());
		submitPaymentLog(gpayVO);
		return gpayVO;
	}

	public G_PayVO setGiftCardInfo(G_PayVO gpayVO) {
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		gpayVO.setAccumulate_point(String.valueOf((int)(Integer.parseInt(gpayVO.getAmount())*0.88)));
		gpayVO.setExpected_balance_point(String.valueOf((Integer.parseInt(userVO.getEpoint())+Integer.parseInt(gpayVO.getAccumulate_point()))));
		return gpayVO;
	}

	public G_PayVO getGPaymentVO(G_PayVO2 gpayVO2) {
		G_PayVO gpayVO = new G_PayVO();
		gpayVO.setOrderId(gpayVO2.getORDER_ID());
		return paymentDao.getGPaymentVO(gpayVO);
	}
	
	public void sendPaymentAlert(PaymentVO payInfo, UserVO userInfo){
		AlertVO sendAlertInfo = new AlertVO();
		
		try{
			sendAlertInfo.setPusher_id("system");
			sendAlertInfo.setPuller_id(payInfo.getUserid());
			sendAlertInfo.setItem_code(AlertVO.ITEM_CODE_PAYMENT_SUCCESS);
			sendAlertInfo.setType("system");
			sendAlertInfo.setMenu_name("payment");
			sendAlertInfo.setDescription(payInfo.getService_name() + " 결제되었습니다.");
			
			alertService.sendAlert(sendAlertInfo);
			
			Map<String, Object> mailModel = new HashMap<>();
			mailModel.put("userInfo", userInfo);
			mailModel.put("payInfo", payInfo);
			
			mailService.sendTempletMail(userInfo, "[에네이] 구매하신 상품의 결제가 완료되었습니다.", "paymentSuccessAlert", mailModel);
		} catch(Exception e){
			logger.warn("[상품 구매 알림] 실패 - 처리중 예외가 발생했습니다."
					+ "(Exception: " + e.getMessage()
					+ ", payInfo: " + payInfo
					+ ", userInfo: " + userInfo + ")");
		}
	}
	
	/**
	 * 050 번호 연장
	 * @param payLog 결제 기록
	 * @return
	 */
	public int extendVnoAgent(PaymentLogVo payLog){
		
		String targetVnoList[] = payLog.getData1().split(PaymentLogVo.DATA_SPLIT_CHAR);
		Integer extendMonth = Integer.parseInt(payLog.getData2());
		Map<String, Object> updateVnoData = new HashMap<>();
		int updatedNum;
		
		if(! ItemVo.ItemCategory.utilization_050.equals(payLog.getItem_cate())){
			logger.warn("[050 사용기간 연장] 실패 - 결제된 건이 050 사용기간 연장이 아닙니다."
					+ "(payLog: " + payLog);
			
			return -1;
		}
		
		updateVnoData.put("targetVnoList", targetVnoList);
		updateVnoData.put("extendMonth", extendMonth);
		
		updatedNum = paymentDao.extendVnoAgent(updateVnoData);
		
		if(updatedNum != targetVnoList.length){
			logger.warn("[050 사용기간 연장] 주의 - 결제시 연장신청한 번호와 연장된 번호의 수가 일치하지 않습니다. "
					+ "(연장 신청 번호 수: " + targetVnoList.length
					+ ", 연장된 번호 수: " + updatedNum
					+ ", payLog: " + payLog);
		}
		
		logger.info("[050 사용기간 연장] 성공 - "
				+ "(연장된 번호 수: " + updatedNum
				+ ", 연장된 번호: [" + targetVnoList + "]"
				+ ", payLog: " + payLog);
		return updatedNum;
	}
	
	/**
	 * epoint 충전
	 * @param payLog
	 * @return
	 */
	public int chargeEpoint(PaymentLogVo payLog, UserVO user){
		Integer addEpoint =  payLog.getAmount() - (payLog.getAmount() / 11);
		
		//상품권,쿠폰으로 epoint충전할때
		if(payLog.getPay_method().equals("happymoney")||payLog.getPay_method().equals("culture")||payLog.getPay_method().equals("booklife")||payLog.getPay_method().equals("coupon"))
			addEpoint =  getExpectedBalancePointGiftCard(payLog.getAmount());
		
		Map<String, Object> chargeEpointData = new HashMap<>();
		chargeEpointData.put("userid", user.getUserid());
		chargeEpointData.put("chargeEpoint", addEpoint);
		
		int chargedUserNum = paymentDao.chargeEpoint(chargeEpointData);
		
		if(chargedUserNum != 1){
			logger.warn("[epoint 충전] 주의 - 충전된 사용자 수가 한명이 아닙니다. "
					+ "(충전된 사용자 수: " + chargedUserNum
					+ ", 충전시 사용된 데이터: " + chargeEpointData
					+ ", userInfo: " + user
					+ ", payLog: " + payLog);
		}
		
		logger.info("[epoint 충전] 성공"
				+ "(chargeEpointData: " + chargeEpointData
				+ ", userInfo: " + user
				+ ", payLog: " + payLog);
		
		// TODO 포인트 충전 후 스프링 시큐리티에 있는 사용자 정보에 리 로드 처리를 해줘야 함
		
		return addEpoint;
	}
	
	/**
	 * 웹 호스팅 결제 처리
	 * @param payLog
	 * @return 결제 처리된 데이터 수
	 */
	public int extendWebHosting(PaymentLogVo payLog){
		
		int updatedNum;
		int webHostingIdx = Integer.parseInt(payLog.getData1());
		ServiceWebHostingVO webHosting = userService.selectWebHosting(webHostingIdx);
		
		if(! ItemVo.ItemCategory.web_hosting.equals(payLog.getItem_cate())){
			logger.warn(LOG_PREFIX_WEB_HOSTING_PAYMENT + " 실패 - 결제된 건이 웹호스팅 신청이 아닙니다."
					+ "(payLog: " + payLog);
			
			return -1;
		} else if(webHosting == null){
			logger.warn(LOG_PREFIX_WEB_HOSTING_PAYMENT +  " 실패 - 웹 호스팅 정보를 찾을 수 없습니다."
					+ "(payLog: " + payLog);
			
			return -1;
		} else if(! PaymentLogVo.PayStatus.standby.equals(webHosting.getPay_state())){
			
			logger.warn(LOG_PREFIX_WEB_HOSTING_PAYMENT + " 실패 - 결제 대기 상태가 아닙니다."
					+ "(payLog: " + payLog + ")");
			
			return -1;
		}
		
		webHosting.setPay_state(PaymentLogVo.PayStatus.approve);
		updatedNum = userService.updateWebHosting(webHosting);
		
		if(updatedNum != 1){
			logger.warn(LOG_PREFIX_WEB_HOSTING_PAYMENT + " 주의 - 수정된 행이 1개가 아닙니다. "
					+ "(updatedNum: " + updatedNum
					+ ", payLog: " + payLog);
		}
		
		logger.info(LOG_PREFIX_WEB_HOSTING_PAYMENT + " 성공 - "
				+ "(payLog: " + payLog
				+ ", webHosting: " + webHosting);
		
		return updatedNum;
	}
	
	/**
	 * 패치콜 결제 처리
	 * @param payLog
	 * @return 결제 처리된 데이터 수
	 */
	public int extendPatchcall(PaymentLogVo payLog){
		
		int updatedNum;
		int patchcallIdx = Integer.parseInt(payLog.getData1());
		ServicePatchcallVO patchcall = userService.selectPatchcallService(patchcallIdx);
		
		if(! ItemVo.ItemCategory.patchcall.equals(payLog.getItem_cate())){
			logger.warn(LOG_PREFIX_PATCHCALL_PAYMENT + " 실패 - 결제된 건이 패치콜 신청이 아닙니다."
					+ "(payLog: " + payLog);
			
			return -1;
		} else if(patchcall == null){
			logger.warn(LOG_PREFIX_PATCHCALL_PAYMENT +  " 실패 - 패치콜 정보를 찾을 수 없습니다."
					+ "(payLog: " + payLog);
			
			return -1;
		} else if(! PaymentLogVo.PayStatus.standby.equals(patchcall.getPay_state())){
			logger.warn(LOG_PREFIX_PATCHCALL_PAYMENT + " 실패 - 결제 대기 상태가 아닙니다."
					+ "(payLog: " + payLog + ")");
			
			return -1;
		}
		
		patchcall.setPay_state(PaymentLogVo.PayStatus.approve);
		updatedNum = userService.updatePatchcall(patchcall);
		
		if(updatedNum != 1){
			logger.warn(LOG_PREFIX_PATCHCALL_PAYMENT + " 주의 - 수정된 행이 1개가 아닙니다. "
					+ "(updatedNum: " + updatedNum
					+ ", payLog: " + payLog);
		}
		
		logger.info(LOG_PREFIX_PATCHCALL_PAYMENT + " 성공 - "
				+ "(payLog: " + payLog
				+ ", webHosting: " + patchcall);
		
		return updatedNum;
	}
	
	public int extendCallbackSms(PaymentLogVo payLog){
		int updatedNum;
		int idx = Integer.parseInt(payLog.getData1());
		CallbackSmsVO callback = userService.selectCallbackSmsService(idx);
		
		if(! ItemVo.ItemCategory.callback_sms.equals(payLog.getItem_cate())){
			logger.warn(LOG_PREFIX_PATCHCALL_PAYMENT + " 실패 - 결제된 건이 콜백SMS 신청이 아닙니다."
					+ "(payLog: " + payLog);
			
			return -1;
		} else if(callback == null){
			logger.warn(LOG_PREFIX_PATCHCALL_PAYMENT +  " 실패 - 결제 정보를 찾을 수 없습니다."
					+ "(payLog: " + payLog);
			
			return -1;
		} else if(! PaymentLogVo.PayStatus.standby.equals(callback.getPay_state())){
			logger.warn(LOG_PREFIX_PATCHCALL_PAYMENT + " 실패 - 결제 대기 상태가 아닙니다."
					+ "(payLog: " + payLog + ")");
			
			return -1;
		}
		
		callback.setPay_state(PaymentLogVo.PayStatus.approve);
		updatedNum = userService.updateCallbackSMS(callback);
		
		if(updatedNum != 1){
			logger.warn(LOG_PREFIX_PATCHCALL_PAYMENT + " 주의 - 수정된 행이 1개가 아닙니다. "
					+ "(updatedNum: " + updatedNum
					+ ", payLog: " + payLog);
		}
		
		logger.info(LOG_PREFIX_PATCHCALL_PAYMENT + " 성공 - "
				+ "(payLog: " + payLog
				+ ", patchcall_other: " + callback);
		
		return updatedNum;
	}
	
	public int extendRecord(PaymentLogVo payLog){
		int updatedNum;
		int idx = Integer.parseInt(payLog.getData1());
		RecordVO record = userService.selectRecordService(idx);
		
		if(! ItemVo.ItemCategory.record.equals(payLog.getItem_cate())){
			logger.warn(LOG_PREFIX_PATCHCALL_PAYMENT + " 실패 - 결제된 건이 녹취 신청이 아닙니다."
					+ "(payLog: " + payLog);
			
			return -1;
		} else if(record == null){
			logger.warn(LOG_PREFIX_PATCHCALL_PAYMENT +  " 실패 - 결제 정보를 찾을 수 없습니다."
					+ "(payLog: " + payLog);
			
			return -1;
		} else if(! PaymentLogVo.PayStatus.standby.equals(record.getPay_state())){
			logger.warn(LOG_PREFIX_PATCHCALL_PAYMENT + " 실패 - 결제 대기 상태가 아닙니다."
					+ "(payLog: " + payLog + ")");
			
			return -1;
		}
		
		record.setPay_state(PaymentLogVo.PayStatus.approve);
		updatedNum = userService.updateRecord(record);
		
		if(updatedNum != 1){
			logger.warn(LOG_PREFIX_PATCHCALL_PAYMENT + " 주의 - 수정된 행이 1개가 아닙니다. "
					+ "(updatedNum: " + updatedNum
					+ ", payLog: " + payLog);
		}
		
		logger.info(LOG_PREFIX_PATCHCALL_PAYMENT + " 성공 - "
				+ "(payLog: " + payLog
				+ ", record: " + record);
		
		return updatedNum;
	}
	
	public int extendPatchcallOther(PaymentLogVo payLog){
		
		int updatedNum;
		int idx = Integer.parseInt(payLog.getData1());
		ServicePatchcallOtherVO patchcall_other = userService.selectPatchcallEtcService(idx);
		
		if(! ItemVo.ItemCategory.patchcall_other.equals(payLog.getItem_cate())){
			logger.warn(LOG_PREFIX_PATCHCALL_PAYMENT + " 실패 - 결제된 건이 패치콜 신청이 아닙니다."
					+ "(payLog: " + payLog);
			
			return -1;
		} else if(patchcall_other == null){
			logger.warn(LOG_PREFIX_PATCHCALL_PAYMENT +  " 실패 - 패치콜 정보를 찾을 수 없습니다."
					+ "(payLog: " + payLog);
			
			return -1;
		} else if(! PaymentLogVo.PayStatus.standby.equals(patchcall_other.getPay_state())){
			logger.warn(LOG_PREFIX_PATCHCALL_PAYMENT + " 실패 - 결제 대기 상태가 아닙니다."
					+ "(payLog: " + payLog + ")");
			
			return -1;
		}
		
		patchcall_other.setPay_state(PaymentLogVo.PayStatus.approve);
		updatedNum = userService.updatePatchcallOther(patchcall_other);
		
		if(updatedNum != 1){
			logger.warn(LOG_PREFIX_PATCHCALL_PAYMENT + " 주의 - 수정된 행이 1개가 아닙니다. "
					+ "(updatedNum: " + updatedNum
					+ ", payLog: " + payLog);
		}
		
		logger.info(LOG_PREFIX_PATCHCALL_PAYMENT + " 성공 - "
				+ "(payLog: " + payLog
				+ ", patchcall_other: " + patchcall_other);
		
		return updatedNum;
	}


	public int extendPatchcallBi(PaymentLogVo payLog){

		int updatedNum;
		int idx = Integer.parseInt(payLog.getData1());
		ServiceBIVO patchcall_bi = userService.getBiService(idx);

		if(! ItemVo.ItemCategory.patchcall_bi.equals(payLog.getItem_cate())){
			logger.warn(LOG_PREFIX_PATCHCALL_PAYMENT + " 실패 - 결제된 건이 패치콜 신청이 아닙니다."
					+ "(payLog: " + payLog);

			return -1;
		} else if(patchcall_bi == null){
			logger.warn(LOG_PREFIX_PATCHCALL_PAYMENT +  " 실패 - 패치콜 정보를 찾을 수 없습니다."
					+ "(payLog: " + payLog);

			return -1;
		} else if(! PaymentLogVo.PayStatus.standby.equals(patchcall_bi.getPay_state())){
			logger.warn(LOG_PREFIX_PATCHCALL_PAYMENT + " 실패 - 결제 대기 상태가 아닙니다."
					+ "(payLog: " + payLog + ")");

			return -1;
		}

		patchcall_bi.setPay_state(PaymentLogVo.PayStatus.approve);
		updatedNum = userService.updatePatchcallBi(patchcall_bi);

		if(updatedNum != 1){
			logger.warn(LOG_PREFIX_PATCHCALL_PAYMENT + " 주의 - 수정된 행이 1개가 아닙니다. "
					+ "(updatedNum: " + updatedNum
					+ ", payLog: " + payLog);
		}

		logger.info(LOG_PREFIX_PATCHCALL_PAYMENT + " 성공 - "
				+ "(payLog: " + payLog
				+ ", patchcall_other: " + patchcall_bi);

		return updatedNum;
	}
	
	/**
	 * 이포인트 사용
	 * @param payLog
	 * @return 잔여 금액
	 * @throws PaymentException 결제 실패 예외
	 */
	@Transactional
	public int deductEpoint(PaymentLogVo payLog) throws PaymentException{
		if(payLog.getUserid()==null)
			throw new PaymentException("결제자의 정보가 누락되었습니다.");
		UserVO user = userService.loadUserByUsername(payLog.getUserid());
		
		Integer epoint = Integer.parseInt(user.getEpoint());
		
		epoint -= payLog.getAmount();
		
		if(epoint < 0)
			throw new PaymentException("EPOINT 잔액이 부족합니다.");
		
		user.setEpoint(epoint.toString());
		
		Map<String, Object> chargeEpointData = new HashMap<>();
		chargeEpointData.put("userid", payLog.getUserid());
		chargeEpointData.put("chargeEpoint", (-1 * payLog.getAmount()));
		int chargedUserNum = paymentDao.chargeEpoint(chargeEpointData);
		
		if(chargedUserNum != 1){
			throw new PaymentException("EPOINT 차감 대상자가 한명이 아닙니다.");
		}
		
		EpointLogVo epointLog = new EpointLogVo();
		Date orderDate = new Date();

		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String serialNum = transFormat.format(orderDate);
		serialNum += "PN"+serialNum;


		epointLog.setSerial_number(serialNum);
		epointLog.setUserid(payLog.getUserid());
		epointLog.setService_code(payLog.getService_code());
		epointLog.setService_name(payLog.getService_name());
		epointLog.setCharge_epoint(payLog.getAmount());
		epointLog.setBalance_epoint(Integer.parseInt(user.getEpoint()));
		
		paymentDao.insertEpointLog(epointLog);
		
		return Integer.parseInt(user.getEpoint());
	}
	
	/**
	 * 이포인트로 서비스 구매 처리
	 * @param paymentResponse 
	 * @param user 사용자
	 * @param ip 사용자 IP
	 * @return
	 */
	public BillgateApprovalResponseVo epointConfirmProcess(BillgatePayResponseVo paymentResponse,String ip){
		
		PaymentLogVo paymentLogVo;
		BillgateApprovalResponseVo approvalResponse;
		Integer amount;
		int orderId = Integer.parseInt(paymentResponse.getORDER_ID());
		
		paymentLogVo = paymentDao.getPaymentLogByOrderid(orderId);
		UserVO user =  new UserVO();
		user.setUserid(paymentLogVo.getUserid());

//		if(! PaymentLogVo.PayStatus.standby.equals(paymentLogVo.getStatus())
//				|| ! checkPaymentService(paymentLogVo.getService_code(), paymentLogVo.getData1())){
//			logger.info("[이포인트 결제 처리] 실패 - 결제 진행중인 결제가 아닙니다. "
//					+ "(status: " + paymentLogVo.getStatus()
//					+ ", paymentResponse: " + paymentResponse
//					+ ", user: " + user + ","
//					+ ", ip: " + ip + ")");
//
//			return null;
//		}
		
		try {
			amount = deductEpoint(paymentLogVo);
		} catch (PaymentException e) {
			approvalResponse = new BillgateApprovalResponseVo();
			
			approvalResponse.setOrderId(paymentResponse.getORDER_ID());
			approvalResponse.setResponseCode("-1");
			approvalResponse.setResponseMesage(e.getMessage());
			
			paymentLogVo.setMessage(e.getMessage());
			paymentLogVo.setStatus(PaymentLogVo.PayStatus.error);
			paymentDao.updatePaymentLog(paymentLogVo);
			
			logger.warn("[이포인트 결제 처리] 실패 - 결제 중 예외가 발생했습니다."
							+ "(exceptionMsg: " + e.getMessage()
							+ ", paymentResponse: " + paymentResponse
							+ ", user: " + user + ","
							+ ", ip: " + ip + ")");
			
			
			
			return null;
		}
		
		paymentLogVo.setStatus(PaymentLogVo.PayStatus.approve);
		paymentLogVo.setCheck_sum(paymentResponse.getCHECK_SUM());
		paymentDao.updatePaymentLog(paymentLogVo);
		
		logger.info("[이포인트 결제 처리] 성공 - "
				+ "(paymentResponse: " + paymentResponse
				+ ", user: " + user + ","
				+ ", ip: " + ip + ")");

		itemBuyProcess(paymentLogVo, user);
		
		approvalResponse = new BillgateApprovalResponseVo();
		
		approvalResponse.setOrderId(paymentResponse.getORDER_ID());
		approvalResponse.setResponseCode(BillgateApprovalResponseVo.RESPONSE_CODE_SUCCESS);
		approvalResponse.setResponseMesage("결제 완료");
		approvalResponse.setDetailResponseMesage("잔여 epoint: " + amount);
		
		/*AgentVO agentVO = new AgentVO();
		agentVO.setUser_id(user.getUserid());
		List<AgentVO> agentList = supplyService.getAgentVOList(agentVO);
		for(AgentVO agent : agentList){
			agentVO.setVno(agent.getVno());
			supplyService.update050Agent22ByVno(agentVO);
		}*/
		
		return approvalResponse; 
	}
	
	/* 겔럭시아 컴즈 PG 결제 처리 영역 */
	
	/**
	 * PG사에 결제 요청할 데이터 생성
	 * @param payMethod 결제 방식
	 * @param serviceCode 사용자가 구매하는 서비스 코드
	 * @param itemParam1 구매 상품 추가 정보
	 * @param user 사용자계정
	 * @param userIp 사용자IP
	 * @return PG사에 결제요청할 데이터
	 */
	public BillgatePayRequestVo genPaymentRequestForm(PaymentLogVo.PayMethod payMethod, String serviceCode,
                                                      String itemParam1, String userId, String userIp){
		BillgatePayRequestVo paymentRequest;
		PaymentLogVo paymentLog = null;
		
		ItemVo item = paymentDao.getItemByService_code(serviceCode);
		
		if(item == null)
			return null;
		
		switch (item.getCategory()) {
		case utilization_050:
			String itemParam2 = item.getService_period().toString();
			paymentLog = addStanbyPaymentLog(payMethod, item, itemParam1, itemParam2, userId);
			
			break;
		case web_hosting:
			ServiceWebHostingVO webHosting = userService.selectWebHosting(Integer.parseInt(itemParam1));
			if(serviceCode.equals("WH_" + webHosting.getService_type().substring(5,9)+webHosting.getService_period())){
				paymentLog = addStanbyPaymentLog(payMethod, item, itemParam1, null, userId);
			}else{
				paymentRequest = null;
			}
			
			break;
		case patchcall:
//			ServicePatchcallVO patchcall = userService.selectPatchcallService(Integer.parseInt(itemParam1));
//			if(serviceCode.equals("PC_" + patchcall.getService_type().substring(4,8) + patchcall.getService_period())){
				paymentLog = addStanbyPaymentLog(payMethod, item, itemParam1, null, userId);
//			}else{
//				paymentRequest = null;
//			}
			
			break;
		case patchcall_other:
			ServicePatchcallOtherVO patchcall_other = userService.selectPatchcallEtcService(Integer.parseInt(itemParam1));
			if(serviceCode.equals("PC_" + patchcall_other.getService_type().substring(0,4)+patchcall_other.getService_period())){
				paymentLog = addStanbyPaymentLog(payMethod, item, itemParam1, null, userId);
			}else{
				paymentRequest = null;
			}
			break;
			
		case callback_sms:
			CallbackSmsVO callback = userService.selectCallbackSmsService(Integer.parseInt(itemParam1));
			if(serviceCode.equals("PC_call" + callback.getService_period())){
				paymentLog = addStanbyPaymentLog(payMethod, item, itemParam1, null, userId);
			}else{
				paymentRequest = null;
			}
			break;
		case record:
			RecordVO record = userService.selectRecordService(Integer.parseInt(itemParam1));
			if(serviceCode.equals("PC_reco"+record.getService_period())){
				paymentLog = addStanbyPaymentLog(payMethod, item, itemParam1, null, userId);
			}else{
				paymentRequest = null;
			}
			break;
		default:
			paymentLog = addStanbyPaymentLog(payMethod, item, itemParam1, null, userId);
			break;
		}
		
		paymentRequest = genPaymentRequestFormByPayLog(paymentLog, userIp);
		
		return paymentRequest;
	}
	
	/**
	 * 갤럭시아 컴즈 (PG)에 결제 요청 및 상품 구매
	 * @param payMethod 결재 방식
	 * @param paymentResponse PG사에서 받은 데이터
	 * @param user 사용자 정보
	 * @param ip 사용자 IP
	 * @return
	 */
	public BillgateApprovalResponseVo paymentConfirmProcess(PaymentLogVo.PayMethod payMethod, BillgatePayResponseVo paymentResponse,String ip){
		
		String serviceCode = getServiceCodeByPayMethod(payMethod);
		Message authMessage;
		PaymentLogVo paymentLogVo;
		BillgateApprovalResponseVo approvalResponse;
		int orderId = Integer.parseInt(paymentResponse.getORDER_ID());

		paymentLogVo = paymentDao.getPaymentLogByOrderid(orderId);
		UserVO user =  new UserVO();
		user.setUserid(paymentLogVo.getUserid());
		
		if(serviceCode == null) {
			
			logger.warn("[결제 결과 처리] 실패 - 지원하지 않는 결제 방식입니다. "
							+ "(payMethod: " + payMethod
							+ ", paymentResponse: " + paymentResponse
							+ ", user: " + user + ","
							+ ", ip: " + ip + ")");
			return null;
		}
		
		if(! PaymentLogVo.PayStatus.standby.equals(paymentLogVo.getStatus())
				|| ! checkPaymentService(paymentLogVo.getService_code(), paymentLogVo.getData1())){
			logger.info("[결제 결과 처리] 실패 - 결제 진행중인 결제가 아닙니다. "
					+ "(status: " + paymentLogVo.getStatus()
					+ ", payMethod: " + payMethod
					+ ", paymentResponse: " + paymentResponse
					+ ", user: " + user + ","
					+ ", ip: " + ip + ")");
			
			return null;
		}
		
		if(! BillgatePayResponseVo.RESPONSE_CODE_SUCCESS.equals(paymentResponse.getRESPONSE_CODE())){
			approvalResponse = new BillgateApprovalResponseVo();
			
			approvalResponse.setOrderId(paymentResponse.getORDER_ID());
			approvalResponse.setTransactionId(paymentResponse.getTRANSACTION_ID());
			approvalResponse.setResponseCode(paymentResponse.getRESPONSE_CODE());
			approvalResponse.setResponseMesage(paymentResponse.getRESPONSE_MESSAGE());
			approvalResponse.setDetailResponseCode(paymentResponse.getDETAIL_RESPONSE_CODE());
			approvalResponse.setDetailResponseMesage(paymentResponse.getDETAIL_RESPONSE_MESSAGE());
			
			
			logger.info("[결제 결과 처리] 실패 - PG 결제를 실패했습니다. "
					+ "(responseCode: " + paymentResponse.getRESPONSE_CODE()
					+ ", detailResponseCode: " + paymentResponse.getDETAIL_RESPONSE_CODE()
					+ ", payMethod: " + payMethod
					+ ", paymentResponse: " + paymentResponse
					+ ", user: " + user + ","
					+ ", ip: " + ip + ")");
			paymentLogVo.setMessage(paymentResponse.getRESPONSE_CODE() 
					+ ":" + paymentResponse.getDETAIL_RESPONSE_CODE() 
					+ "-" + paymentResponse.getMESSAGE());
			paymentLogVo.setStatus(PaymentLogVo.PayStatus.error);
			paymentLogVo.setCheck_sum(paymentResponse.getCHECK_SUM());
			
			paymentDao.updatePaymentLog(paymentLogVo);
			
			return approvalResponse;
		}
		
		if(! diffPaymentRequestChecksum(payMethod, paymentResponse, paymentLogVo)){
			logger.warn("[결제 결과 처리] 실패 - 유효성 검사를 통과하지 못했습니다"
					+ "(paymentResponse: " + paymentResponse
					+ ", user: " + user + ","
					+ ", ip: " + ip + ")");
			
			paymentLogVo.setMessage(paymentResponse.getMESSAGE());
			paymentLogVo.setStatus(PaymentLogVo.PayStatus.error);
			paymentLogVo.setCheck_sum(paymentResponse.getCHECK_SUM());
			
			paymentDao.updatePaymentLog(paymentLogVo);
			
			return null;
		}
		
		try {
			authMessage = authProcess(serviceCode, paymentProperties.getServiceId(), paymentResponse);
			approvalResponse = getApprovalResponseByMessage(authMessage);
		} catch (Exception e) {
			logger.warn("[결제 결과 처리] 실패 - 결제 요청중 예외가 발생했습니다."
							+ "(exceptionMsg: " + e.getMessage()
							+ ", paymentResponse: " + paymentResponse
							+ ", user: " + user + ","
							+ ", ip: " + ip + ")");
			
			return null;
		}
		
		if(! BillgateApprovalResponseVo.RESPONSE_CODE_SUCCESS
				.equals(approvalResponse.getResponseCode())){
			logger.warn("[결제 결과 처리] 실패 - 결제 승인 요청에 실패하였습니다."
					+ "(responseCode: " + approvalResponse.getResponseCode()
					+ ", detailResponseCode: " + approvalResponse.getDetailResponseCode()
					+ ", responseMsg: " + approvalResponse.getResponseMesage()
					+ ", detailResponseMsg: " + approvalResponse.getDetailResponseMesage()
					+ ", paymentResponse: " + paymentResponse
					+ ", user: " + user + ","
					+ ", ip: " + ip + ")");
			
			paymentLogVo.setMessage(paymentResponse.getRESPONSE_CODE() 
								+ ":" + paymentResponse.getDETAIL_RESPONSE_CODE() 
								+ "-" + paymentResponse.getMESSAGE());
			paymentLogVo.setStatus(PaymentLogVo.PayStatus.error);
			paymentLogVo.setCheck_sum(paymentResponse.getCHECK_SUM());
			
			paymentDao.updatePaymentLog(paymentLogVo);
			
			return approvalResponse;
		}
		
		if(paymentLogVo.getAmount().toString()
				.equals(approvalResponse.getAuthAmount())){
			logger.warn("[결제 결과 처리] 실패 - 결제 승인금액과 요청 금액이 다릅니다."
					+ "(authAmount: " + approvalResponse.getAuthAmount()
					+ ", requestAmount: " + paymentLogVo.getAmount()
					+ ", paymentResponse: " + paymentResponse
					+ ", user: " + user + ","
					+ ", ip: " + ip + ")");
			
			// TODO 결제 취소가 들어가야 됨
			return null;
		}
		
		paymentLogVo.setMessage(approvalResponse.getResponseMesage());
		paymentLogVo.setStatus(PaymentLogVo.PayStatus.approve);
		paymentLogVo.setCheck_sum(paymentResponse.getCHECK_SUM());
		paymentDao.updatePaymentLog(paymentLogVo);
		
		logger.info("[결제 결과 처리] 성공 - "
				+ "(approvalResponse: " + approvalResponse
				+ ", paymentResponse: " + paymentResponse
				+ ", user: " + user + ","
				+ ", ip: " + ip + ")");
		
		itemBuyProcess(paymentLogVo, user);
		
		return approvalResponse; 
	}

	/**
	 * 결제 요청 로그 생성
	 * @param payMethod 결제유형
	 * @param item 상품
	 * @param orderDate 주문일시
	 * @param itemParam1 상품 추가정보 1
	 * @param itemParam2 상품 추가정보 2
	 * @param user 사용자 정보
	 * @return 생성된 결제로그
	 */
	private PaymentLogVo addStanbyPaymentLog(PaymentLogVo.PayMethod payMethod, ItemVo item,
                                             String itemParam1, String itemParam2, String userId){
		PaymentLogVo paymentLog = new PaymentLogVo();
		Date orderDate = new Date();
		
		if("utilization_050".equals(item.getCategory())){
			String targetVnoList[] = itemParam1.split(PaymentLogVo.DATA_SPLIT_CHAR);
			
			paymentLog.setAmount(item.getSale_price() * targetVnoList.length);
		} else {
			paymentLog.setAmount(item.getSale_price());
		}
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmm");
		String serialNum = transFormat.format(orderDate);
		serialNum += "PY"+serialNum;

		paymentLog.setUserid(userId);
		paymentLog.setService_code(item.getService_code());
		paymentLog.setService_name(item.getService_name());
		paymentLog.setItem_cate(item.getCategory());
		paymentLog.setPay_method(payMethod);
		paymentLog.setData1(itemParam1);
		paymentLog.setData2(itemParam2);
		paymentLog.setOrder_date(orderDate);
		paymentLog.setStatus(PaymentLogVo.PayStatus.standby);
		paymentLog.setSerial_number(serialNum);
		paymentDao.insertPaymentLog(paymentLog);
		
		return paymentLog;
	}
	
	/**
	 * 결제 로그를 가지고 PG사에 결제 요청할 데이터 생성
	 * @param paymentLog 결제로그
	 * @param userIp 결제 요청 사용자IP
	 * @return PG사에 요청할 결제 정보
	 */
	private BillgatePayRequestVo genPaymentRequestFormByPayLog(PaymentLogVo paymentLog, String userIp){
		BillgatePayRequestVo paymentRequest = new BillgatePayRequestVo();
		String orderDateString = DateUtil.getDateString(paymentLog.getOrder_date(), DATE_STRIONG_FORMAT);

		G_PayVO payVO = new G_PayVO();
		
		try {
			paymentRequest.setORDER_ID(paymentLog.getOrderid().toString());
			paymentRequest.setSERVICE_ID(payVO.getServiceId());
			paymentRequest.setORDER_DATE(orderDateString);
			paymentRequest.setITEM_NAME(paymentLog.getService_name());
			paymentRequest.setITEM_CODE(paymentLog.getService_code());
			paymentRequest.setAMOUNT(paymentLog.getAmount());
			paymentRequest.setUSER_ID(paymentLog.getUserid());
			paymentRequest.setUSER_IP(userIp);
			paymentRequest.setRETURN_URL(getPaymentReturnUrl(paymentLog.getPay_method()));
			paymentRequest.setCHECK_SUM(genCheckSum(paymentRequest));
		} catch (Exception e) {
			logger.warn("[결제 요청 생성] 실패 - 예외 발생 (e: " + e.getMessage() + ")");
			
			return null;
		}
		
		return paymentRequest;
	}
	
	/**
	 * 결제 요청 검증코드 생성
	 * @param paymentRequest 결제요청
	 * @return 생성된 검증코드
	 * @throws Exception
	 */
	private String genCheckSum(BillgatePayRequestVo paymentRequest) throws Exception{
		return ChecksumUtil.genCheckSum(paymentRequest.getSERVICE_ID() + paymentRequest.getORDER_ID() + paymentRequest.getAMOUNT());
	}
	
	
	/**
	 * PG에서 전송한 결제 데이터 유효성 검사
	 * @param response PG에서 결제 데이터
	 * @param payLog 서버에 저장된 결제 로그
	 * @return 유효성 검사 통과 여부
	 */
	private boolean diffPaymentRequestChecksum(PaymentLogVo.PayMethod payMethod, BillgatePayResponseVo response, PaymentLogVo payLog){
		String checkData;
		
		switch (payMethod) {
		case credit:
			checkData = response.getSERVICE_ID() + response.getORDER_ID() + response.getORDER_DATE();
			break;
		case booklife:
		case culture:
		case happymoney:
			checkData = response.getSERVICE_ID() + response.getORDER_ID() + response.getTRANSACTION_ID();
			break;
		default:
			return false;
		}
		
		try{
			if(! paymentProperties.getServiceId().equals(response.getSERVICE_ID())){
				logger.warn("[결제 유효성 검사] 실패 - 서비스 ID가 다릅니다.");
				return false;
				
			} else if (! payLog.getOrderid().equals(Integer.parseInt(response.getORDER_ID()))){
				logger.warn("[결제 유효성 검사] 실패 - 주문번호가 다릅니다.");
				return false;
				
			} else if (! DateUtil.getDateString(payLog.getOrder_date(), DATE_STRIONG_FORMAT).equals(response.getORDER_DATE())){
				logger.warn("[결제 유효성 검사] 실패 - 결제요청 날짜가 다릅니다.");
				return false;
				
			} else if (! ChecksumUtil.diffCheckSum(response.getCHECK_SUM(), checkData)){
				logger.warn("[결제 유효성 검사] 실패 - checksum값과 다른 데이터입니다");
				return false;
			}
		} catch(Exception e){
			logger.warn("[결제 유효성 검사] 실패 - 예외가 발생하였습니다 (e: " + e.toString() + ")");
			
			return false;
		}
		
		return true;
		
	}
	
	/**
	 * 갤럭시아컴즈 암호화 처리
	 * @param serviceId 서비스 ID
	 * @param serviceCode 서비스 코드 (결제 유형, ServiceCode 참고)
	 * @return
	 * @throws Exception
	 */
	private GalaxiaCipher getCipher(String serviceCode, String serviceId) throws Exception{
		
		
		GalaxiaCipher cipher = null ;
				
		String key = null ;
		String iv = null ;
		
		try { 
			
			ConfigInfo config = new ConfigInfo(serviceCode);
			
			key = config.getKey();
			iv = config.getIv();
			
			/*key = "QkZJRlBDRTI4T0c1OUtBMw==";
			iv = "PRJ59Q2GHPT844TQ";*/
			
			cipher = new Seed();
			cipher.setKey(key.getBytes());
			cipher.setIV(iv.getBytes());
			
		} catch(Exception e) {
			throw e;
		}
		return cipher;
	}
	
	/**
	 * PG 결제 승인 요청 <br>
	 * <p>갤럭시아컴즈에서 카드와 상품권 결제 방식이 달라 해당 메소드에서 분리해 처리</p>
	 * @param serviceCode 서비스 코드 (결제 방법)`
	 * @param serviceCode 서비스 ID
	 * @param response PG에서 받은 결제 데이터
	 * @return 승인 결과 메시지
	 * @throws Exception 
	 */
	private Message authProcess(String serviceCode, String serviceId, BillgatePayResponseVo response) throws Exception{
		switch (serviceCode) {
		case ServiceCode.CREDIT_CARD:
			return cardLinkAuthProcess(serviceCode, serviceId, response.getMESSAGE());
			
		case ServiceCode.BOOKNLIFE_COUPON:
		case ServiceCode.CULTURELAND_COUPON:
		case ServiceCode.HAPPYMONEY_COUPON:
			return couponAuthProcess(serviceCode, serviceId, response.getORDER_ID(), response.getORDER_DATE(), response.getTRANSACTION_ID());
			
		default:
			throw new PaymentException("지원하지 않는 결제 방식입니다.");
		}
	}
	
	/**
	 * PG <b>카드</b> 결제 승인 요청
	 * @param serviceCode 서비스 코드 (결제 방법)
	 * @param serviceId 서비스 ID
	 * @param msg PG에서 받은 결제 데이터 중 Message
	 * @param config 
	 * @return 승인 결과 메시지
	 * @throws Exception
	 */
	private Message cardLinkAuthProcess(String serviceCode, 
			String serviceId, String msg) throws Exception{	
		
		logger.info("[카드결제 승인요청] Galaxia Log msg: " + msg);
		
		//메시지 Length 제거
		byte[] b = new byte[msg.getBytes().length - 4] ;
		System.arraycopy(msg.getBytes(), 4, b, 0, b.length);
		
		Message requestMsg = new Message(b, getCipher(serviceCode, serviceId)) ;
		Message responseMsg = null ;
		
		ServiceBroker sb = new ServiceBroker(serviceCode);
		responseMsg = sb.invoke(requestMsg, "UTF-8");
		
		return responseMsg;
	}
	
	/**
	 * PG <b>상품권</b> 결제 승인 요청 
	 * @param serviceCode 서비스 코드 (결제 방법)
	 * @param serviceId 서비스 ID
	 * @param orderId 주문번호
	 * @param orderDate 주문일시
	 * @param transactionId 거래번호
	 * @return 승인 결과 메시지
	 * @throws Exception
	 */
	private Message couponAuthProcess(String serviceCode, String serviceId,
			String orderId, String orderDate, String transactionId) throws Exception {

		Message requestMsg = new Message(GALAXIA_API_VERSION, serviceId, 
				serviceCode, 
				Command.AUTH_REQUEST,
				orderId, 
				orderDate, 
				getCipher(serviceCode, serviceId)) ;
		
		Message responseMsg = null ;
		
		if(transactionId != null) requestMsg.put(MessageTag.TRANSACTION_ID, transactionId);

		ServiceBroker sb = new ServiceBroker(ServiceCode.CULTURELAND_COUPON);

		responseMsg = sb.invoke(requestMsg, "UTF-8");
		
		return responseMsg;
	}
	
	/**
	 * 결제 후 서비스 적용 절차
	 * @param paymentLog 구매 기록
	 * @param user 사용자 정보
	 * @return
	 */
	private int itemBuyProcess(PaymentLogVo paymentLog, UserVO user){
		int returnData;
		
		switch (paymentLog.getItem_cate()) {
		case utilization_050:
			returnData = extendVnoAgent(paymentLog);
			break;
			
		case epoint:
			returnData = chargeEpoint(paymentLog, user);
			break;
		case web_hosting:
			returnData = extendWebHosting(paymentLog);
			break;
		case patchcall :
			returnData = extendPatchcall(paymentLog);
			break;
		case patchcall_other :
			returnData = extendPatchcallOther(paymentLog);
			break;
		case callback_sms :
			returnData = extendCallbackSms(paymentLog);
			break;
		case record :
			returnData = extendRecord(paymentLog);
			break;
		case patchcall_bi:
			returnData = extendPatchcallBi(paymentLog);
			break;
		default:
			returnData = -1;
			break;
		}
		
		return returnData;
	}
	
	/**
	 * 갤럭시아 컴즈 PG에서 승인 요청 후 받은 Message로
	 * BillgateApprovalResponseVo 객체 생성
	 * @param message 승인 요청 후 수신받은 Message
	 * @return
	 */
	private static BillgateApprovalResponseVo getApprovalResponseByMessage(Message message){
		BillgateApprovalResponseVo approvalResponse = new BillgateApprovalResponseVo();
		
		approvalResponse.setResponseCode(message.get(MessageTag.RESPONSE_CODE));
		approvalResponse.setResponseMesage(message.get(MessageTag.RESPONSE_MESSAGE));
		approvalResponse.setDetailResponseCode(message.get(MessageTag.DETAIL_RESPONSE_CODE));
		approvalResponse.setDetailResponseMesage(message.get(MessageTag.DETAIL_RESPONSE_MESSAGE));
		approvalResponse.setTransactionId(message.get(MessageTag.TRANSACTION_ID));
		
		if(BillgateApprovalResponseVo.RESPONSE_CODE_SUCCESS
				.equals(approvalResponse.getResponseCode())){
			
			approvalResponse.setAuthNumber(message.get(MessageTag.AUTH_NUMBER));
			approvalResponse.setAuthDate(message.get(MessageTag.AUTH_DATE));
			approvalResponse.setAuthAmount(MessageTag.AUTH_AMOUNT);
			
		}
		
		return approvalResponse;
	}
	
	/**
	 * 결제 방식으로 서비스 코드를 찾는 메소드
	 * @param payMethod 결제 방식
	 * @return 해당 서비스 코드
	 */
	private static String getServiceCodeByPayMethod(PaymentLogVo.PayMethod payMethod) {
		switch (payMethod) {
		case credit:
			return ServiceCode.CREDIT_CARD;
		case mobile:
			return ServiceCode.MOBILE;
		case booklife:
			return ServiceCode.BOOKNLIFE_COUPON;
		case culture:
			return ServiceCode.CULTURELAND_COUPON;
		case happymoney:
			return ServiceCode.HAPPYMONEY_COUPON;
		default:
			return null;
		}
	}
	
	/**
	 * 상품권 충전시 수수료 제외 금액
	 * @param amount 충전 금액
	 * @return 수수료 제외 금액
	 */
	private int getExpectedBalancePointGiftCard(int amount){
		int amount3 = (int) (amount * (100-paymentProperties.getGiftcardCommission()));
		
		return amount3;
	}
	
	/**
	 * PG에서 처리한 결제 결과를 처리하는 URL을 가져옴
	 * @param paymentMethod 결제 유형
	 * @return 결제 결과를 처리하는 URL
	 */
	public String getPaymentReturnUrl(PaymentLogVo.PayMethod paymentMethod){
		return paymentProperties.getReturnUrlPrefix() + paymentMethod.name() + paymentProperties.getReturnUrlSuffix();
	}
	
	/**
	 * PG 결제 처리 페이지 URL
	 * @param paymentMethod 결제 방식
	 * @return 결제 처리 URL
	 */
	public String getPaymentRequestUrl(PaymentLogVo.PayMethod paymentMethod){
		if(paymentMethod == PaymentLogVo.PayMethod.epoint){
			return paymentProperties.getReturnUrlPrefix() 
					+ paymentMethod.name() 
					+ paymentProperties.getReturnUrlSuffix();
		}
		
		return paymentProperties.getPaymentRequestUrlPrefix() 
				+ paymentMethod.name() 
				+ paymentProperties.getPaymentRequestUrlSuffix();
	}
	
	@Autowired
	public void setPaymentProperty(EneyProperties eneyProperties){
		this.paymentProperties = eneyProperties.getPortal().getPaymentProperties();
	}
	
	public boolean checkPaymentService(String serviceCode, String itemParam){
		PaymentVO query = new PaymentVO();
		query.setService_code(serviceCode);
		
		PaymentVO payment = paymentDao.getPaymentVO(query);
		
		boolean result = false;
		
		switch (payment.getCategory()) {
		case "web_hosting":
			result = userService.checkPaymentWebHosting(serviceCode, itemParam);
			break;
		case "patchcall":
			result = userService.checkPaymentPatchcall(serviceCode, itemParam);
			break;
		case "patchcall_other":
			result = userService.checkPaymentPatchcallOther(serviceCode, itemParam);
			break;
		case "callback_sms":
			result = userService.checkPaymentCallbackSms(serviceCode, itemParam);
			break;
		case "record":
			result = userService.checkPaymentRecord(serviceCode, itemParam);
			break;
		case "patchcall_bi":
			result = userService.checkPaymentPatchcallBi(serviceCode, itemParam);
			break;
		case "epoint":
			result = true;
		default:
			break;
		}
				
		return result; 
	}
	
}
