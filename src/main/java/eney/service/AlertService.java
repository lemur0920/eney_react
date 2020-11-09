package eney.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import eney.domain.MessageVO;
import eney.domain.AlertVO;
import eney.domain.CommonVO;
import eney.domain.MessageVO;
import eney.domain.UserVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import eney.domain.AlertVO;
import eney.domain.CommonVO;
import eney.domain.UserVO;
import eney.exception.AccessDeniedException;
import eney.mapper.AlertDao;
import eney.mapper.MainDao;

@Service
public class AlertService extends Exception{
	
	private static final Logger logger = LoggerFactory.getLogger(AlertService.class);

	@Resource
	private UserService userService;
	@Resource
	private MailService mailService;
	@Resource
	private MessageService messageService;
	@Resource
	private IvrService switchService;
	@Resource
	private SupplyService supplyService;
	@Resource
	private AlertDao alertDao;
	@Resource
	private MainDao mainDao;
	
	/**
	 * 받는 사람의 아이디가 user_id 이고, check_yn이 0인 리스트 출력
	 * @return 알람 리스트
	 */
	public List<Map<String, Object>> getReloadData(){
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AlertVO param = new AlertVO();
		param.setPuller_id(userVO.getUserid());
		param.setCheck_yn(0);
		
		List<AlertVO> alertList = alertDao.getReloadData(param);
		List<Map<String, Object>> res = new LinkedList<>();
		for (AlertVO alertVO : alertList) {
			Map<String, Object> obj = new HashMap<>();
			obj.put("alert_id", alertVO.getAlert_id());
			obj.put("check_yn", alertVO.getCheck_yn());
			obj.put("date", alertVO.getDate());
			obj.put("description", alertVO.getDescription());
			obj.put("menu_name", alertVO.getMenu_name());
			obj.put("puller_id", alertVO.getPuller_id());
			obj.put("pusher_id", alertVO.getPusher_id());
			obj.put("type", alertVO.getType());
			res.add(obj);
		}
		return res;
	}
	
	/**
	 * 050 번호 만료일 안내
	 */
	//막음
//	@Scheduled(cron="00 00 00 * * *")
//	public void pushAlertWillBeExpired050(){
//		Calendar calendar = Calendar.getInstance();
//    	SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    	System.out.println("현재 시각: " +  dateFormat.format(calendar.getTime()));
//
//    	AlertVO alertVO = new AlertVO(AlertVO.ITEM_CODE_050_EXPIRE_WEEK);
//    	alertVO.setVno_list(alertDao.getExpireVnoUserList(alertVO));
//    	//alertVO.setPuller_id(userVO.getUserid());
//    	alertVO.setDescription("개의 번호가 일주일뒤 만료됩니다.");
//    	if(alertVO.getVno_list().size()>0)
//    		alertDao.pushAlertExpireVno(alertVO);
//    	sendExpiredEmail("050 번호가 일주일 뒤", alertVO.getVno_list());				// 대상자에게 메일 전송
//    	logger.info("[번호 만료 알림 - 1주 전] 성공 "
//    			+ "(알림 전송: " + alertVO.getVno_list().size() + ")");
//
//
//    	alertVO = new AlertVO(AlertVO.ITEM_CODE_050_EXPIRE_DAY);
//    	alertVO.setVno_list(alertDao.getExpireVnoUserList(alertVO));
//    	//alertVO.setPuller_id(userVO.getUserid());
//    	alertVO.setDescription("개의 번호가 내일 만료됩니다.");
//    	if(alertVO.getVno_list().size()>0)
//    		alertDao.pushAlertExpireVno(alertVO);
//    	sendExpiredEmail("050 번호가 내일", alertVO.getVno_list());					// 대상자에게 메일 전송
//    	logger.info("[번호 만료 알림 - 하루 전] 성공 "
//    			+ "(알림 전송: " + alertVO.getVno_list().size() + ")");
//	}
	
	/**
	 * 5분 간격마다 콜로그를 이용해 교환기 상태를 체크하고 문제 발생시 SMS을 전송
	 */
	/*@Scheduled(cron="0 0/5 * * * *")*/
	public void checkSwitchHealthAndSmsAlert(){
		long startTime = System.currentTimeMillis();
		Map<String,String> map = new HashMap<>();
		map.put("type","교환기 상태체크");
		CommonVO commonCodeSelectQuery = new CommonVO();
		commonCodeSelectQuery.setCategory(CommonVO.CATEGORY_SWITCH_ALERT);
		Map<String, String> settingMap = supplyService.getCommonMap(commonCodeSelectQuery);
		
		if(! Boolean.valueOf(settingMap.get("enable"))){
			return;
		}
		
    	Map<String, Long> last50CdrSummary = null;

    	try{
    		last50CdrSummary = switchService.getLast50CdrSummary();
    		long endTime = System.currentTimeMillis();
    		logger.warn("교환기 상태 체크까지 걸린 시간  = " +( endTime - startTime )/1000.0f +"초");
    		map.put("time",( endTime - startTime )/1000.0f +"초");
    		mainDao.insertTimeMeasure(map);
    	} catch(Exception e){
    		if(Boolean.valueOf(settingMap.get("send_sms"))){
    			/* SMS 전송 영역 */
    			String[] phoneNumbers = settingMap.get("phone_numbers").split(",");
    			for(String phoneNumber: phoneNumbers){

					MessageVO messageVO = new MessageVO();
					messageVO.setCallback(MessageService.ADDR_ENEY_OFFICE);
					messageVO.setDstaddr(phoneNumber.trim());
					messageVO.setText("[교환기 상태]\n\n정보를 생성할 수 없음");

					messageService.sendMsg(messageVO);
    			}
    		}
    		
    		logger.warn("[교환기 상태 체크] 오류 - DB에서 데이터를 가져올 수 없습니다.");
    		return;
    	}
    	
		long connect_failed_per = last50CdrSummary.get("connect_failed_per");
		long agent_not_found_per = last50CdrSummary.get("agent_not_found_per");
		long warn_per = last50CdrSummary.get("warn_per");
    	
		try{
	    	if(connect_failed_per >= Integer.parseInt(settingMap.get("connect_failed_per")) 
					|| agent_not_found_per >= Integer.parseInt(settingMap.get("agent_not_found_per"))  
					|| warn_per >= Integer.parseInt(settingMap.get("warn_per")) ){
	    		/* 알람 발송이 필요한 경우 */
	    		
	    		if(Boolean.valueOf(settingMap.get("send_sms"))){
	    			/* SMS 전송 영역 */
		    		String[] phoneNumbers = settingMap.get("phone_numbers").split(",");
					for(String phoneNumber: phoneNumbers){

						String text = "[교환기 상태 경보]\n\n연결실패: " + (int) connect_failed_per + "%\n교환데이터 로드 실패: " + (int) agent_not_found_per + "%\n불랑추정: " + (int) warn_per + "%";

						MessageVO messageVO = new MessageVO();
						messageVO.setCallback(MessageService.ADDR_ENEY_OFFICE);
						messageVO.setDstaddr(phoneNumber.trim());
						messageVO.setText(text);

						messageService.sendMsg(messageVO);
					}
	    		}
				logger.warn("[교환기 상태 체크] 경고 - (연결 실패: " + connect_failed_per + "%, 교환데이터 로드 실패: " + agent_not_found_per + "%, 연결불랑 추정치: " + warn_per + "%)");
			} else {
				logger.info("[교환기 상태 체크] 정상 - (연결 실패: " + connect_failed_per + "%, 교환데이터 로드 실패: " + agent_not_found_per + "%, 연결불랑 추정치: " + warn_per + "%)");
			}
		}catch(NumberFormatException e){
			if(Boolean.valueOf(settingMap.get("send_sms"))){
    			/* SMS 전송 영역 */
	    		String[] phoneNumbers = settingMap.get("phone_numbers").split(",");
				for(String phoneNumber: phoneNumbers){

					MessageVO messageVO = new MessageVO();
					messageVO.setCallback(MessageService.ADDR_ENEY_OFFICE);
					messageVO.setDstaddr(phoneNumber.trim());
					messageVO.setText("[교환기 상태]\n\n기준치를 읽을 수 없습니다.\n설정을 확인해 주세요!");

					messageService.sendMsg(messageVO);
				}
    		}
			logger.warn("[교환기 상태 체크] 실패 - 기준치를 변환시킬 수 없습니다. ");
		}
	}
	/**
	 * 알람 VO 설정
	 * @param alert_code (050 번호 만료 일주일전, 050 번호 만료 하루 전, 결제 성공)
	 * @param pusher_id 'system'
	 * @param puller_id 해당 아이디
	 * @param type 'system'
	 * @param menu_name (050, payment)
	 * @param description 알람 멘트
	 */
	public Integer sendAlert(String alert_code, String pusher_id, String puller_id, String type, String menu_name, String description){
		AlertVO alertInfo = new AlertVO();
		alertInfo.setItem_code(alert_code);
		alertInfo.setPusher_id(pusher_id);
		alertInfo.setPuller_id(puller_id);
		alertInfo.setType(type);
		alertInfo.setMenu_name(menu_name);
		alertInfo.setDescription(description);
		return sendAlert(alertInfo);
	}
	
	/**
	 * 알람 전송
	 * @param alertInfo
	 * @return
	 */
	public Integer sendAlert(AlertVO alertInfo){
		return alertDao.insertAlert(alertInfo);
	}
	
	/**
	 * 
	 * @param param
	 * @throws AccessDeniedException
	 */
	@SuppressWarnings("unchecked")
	public void checkAlert(AlertVO param) throws AccessDeniedException {
		boolean check  = false;
		Collection<SimpleGrantedAuthority> authorities = (Collection<SimpleGrantedAuthority>)SecurityContextHolder.getContext().getAuthentication().getAuthorities();
		
		for (SimpleGrantedAuthority authoritity : authorities) {
			if(String.valueOf(authoritity).equals("ROLE_ADMIN")){
				check = true;
			}
		}
		AlertVO alertVO = alertDao.getAlertVOById(param.getAlert_id());
		
		if(check)
			param = alertVO;
		
		param.setCheck_yn(1);
		
		if(alertVO==null || (!check && !alertVO.getPuller_id().equals(param.getPuller_id())))
				throw new AccessDeniedException(param.getPuller_id());
		
		alertDao.updateAlert(param);
	}
	
	/**
	 * 번호 만기일 안내 메일 전송
	 * @param expiredTerm 메일내에 들어갈 앞으로 남은 만료 기간 (문자열, ex]일주일 뒤), NULL입력시 내용이 들어가지 않음
	 * @param expiredInfoList 메일을 보내야될 번호 정보 리스트
	 */
	public void sendExpiredEmail(String expiredTerm, List<Map<String,Object>> expiredInfoList){
		int mailSendCount = 0;
    	int mailSendErrorCount = 0;
    	for(Map<String, Object> expiredInfoEach : expiredInfoList){
    		Map<String, Object> mailModel = new HashMap<>();
    		String user_id = (String) expiredInfoEach.get("user_id");
    		String userid = (String) expiredInfoEach.get("userid");
    		UserVO userInfo = new UserVO();
    		
    		if(user_id != null){
    			userInfo = userService.loadUserByUsername(user_id);
    		}else{
    			userInfo = userService.loadUserByUsername(userid);
    		}
    			
    		
    		if(userInfo != null && !"".equals(userInfo.getEmail())){
    			mailModel.put("expiredInfo", expiredInfoEach);
    			mailModel.put("expiredTerm", expiredTerm);
        		mailModel.put("userInfo", userInfo);
        		
        		if(mailService.sendTempletMail(userInfo, expiredTerm + " 만료 예정입니다.", "vnoExpired", mailModel)){
        			mailSendCount ++;
        		} else {
        			mailSendErrorCount ++;
        		}
    		}
    	}
    	logger.info("[알림 메일 전송] 완료" 
    			+ "(expiredTerm: " + expiredTerm
    			+ ", 성공한 메일전송: " + mailSendCount + "건"
    			+ ", 실패한 메일전송: " + mailSendErrorCount + "건" + ")");
	}

//	//막음
//	@Scheduled(cron="00 00 00 * * *")
//	public void pushAlertWillBeExpiredServicePatchcall(){
//    	AlertVO alertVO = new AlertVO(AlertVO.ITEM_CODE_SERVICE_PATCHCLL_EXPIRE_WEEK);
//    	alertVO.setPatchcall_service_list(alertDao.getExpireServicePatchcallList(alertVO));
//    	//alertVO.setPuller_id(userVO.getUserid());
//    	alertVO.setDescription("개의 패치콜 서비스가 일주일뒤 만료됩니다.");
//    	if(alertVO.getPatchcall_service_list().size()>0)
//    		sendExpiredEmail("패치콜 서비스가 일주일 뒤", alertVO.getPatchcall_service_list());				// 대상자에게 메일 전송
//    	logger.info("[서비스 만료 알림 - 1주 전] 성공 "
//    			+ "(알림 전송: " + alertVO.getPatchcall_service_list().size() + ")");
//
//
//    	alertVO = new AlertVO(AlertVO.ITEM_CODE_SERVICE_PATCHCLL_EXPIRE_DAY);
//    	alertVO.setPatchcall_service_list(alertDao.getExpireServicePatchcallList(alertVO));
//    	//alertVO.setPuller_id(userVO.getUserid());
//    	alertVO.setDescription("개의 패치콜 서비스가 하루 뒤 만료됩니다.");
//    	if(alertVO.getPatchcall_service_list().size()>0)
//    		sendExpiredEmail("패치콜 서비스가 내일", alertVO.getPatchcall_service_list());					// 대상자에게 메일 전송
//    	logger.info("[서비스 만료 알림 - 하루 전] 성공 "
//    			+ "(알림 전송: " + alertVO.getPatchcall_service_list().size() + ")");
//	}

	//막음
//	@Scheduled(cron="00 00 00 * * *")
//	public void pushAlertWillBeExpiredServiceWeb(){
//    	AlertVO alertVO = new AlertVO(AlertVO.ITEM_CODE_SERVICE_WEB_EXPIRE_WEEK);
//    	alertVO.setWeb_service_list(alertDao.getExpireServiceWebList(alertVO));
//    	//alertVO.setPuller_id(userVO.getUserid());
//    	alertVO.setDescription("개의 웹호스팅 서비스가 일주일뒤 만료됩니다.");
//    	if(alertVO.getWeb_service_list().size()>0)
//    		sendExpiredEmail("웹호스팅 서비스가 일주일 뒤", alertVO.getWeb_service_list());				// 대상자에게 메일 전송
//    	logger.info("[서비스 만료 알림 - 1주 전] 성공 "
//    			+ "(알림 전송: " + alertVO.getWeb_service_list().size() + ")");
//
//
//    	alertVO = new AlertVO(AlertVO.ITEM_CODE_SERVICE_WEB_EXPIRE_DAY);
//    	alertVO.setWeb_service_list(alertDao.getExpireServiceWebList(alertVO));
//    	//alertVO.setPuller_id(userVO.getUserid());
//    	alertVO.setDescription("개의 웹호스팅 서비스가 하루 뒤 만료됩니다.");
//    	if(alertVO.getWeb_service_list().size()>0)
//    		sendExpiredEmail("웹호스팅 서비스가 내일", alertVO.getWeb_service_list());					// 대상자에게 메일 전송
//    	logger.info("[서비스 만료 알림 - 하루 전] 성공 "
//    			+ "(알림 전송: " + alertVO.getWeb_service_list().size() + ")");
//	}
//	막음
//	@Scheduled(cron="00 00 00 * * *")
//	public void pushAlertWillBeExpiredServiceRecord(){
//    	AlertVO alertVO = new AlertVO(AlertVO.ITEM_CODE_SERVICE_RECORD_EXPIRE_WEEK);
//    	alertVO.setRecord_service_list(alertDao.getExpireServiceRecordList(alertVO));
//    	//alertVO.setPuller_id(userVO.getUserid());
//    	alertVO.setDescription("개의 녹취 서비스가 일주일뒤 만료됩니다.");
//    	if(alertVO.getRecord_service_list().size()>0)1
//    		sendExpiredEmail("녹취 서비스가 일주일 뒤", alertVO.getRecord_service_list());				// 대상자에게 메일 전송
//    	logger.info("[서비스 만료 알림 - 1주 전] 성공 "
//    			+ "(알림 전송: " + alertVO.getRecord_service_list().size() + ")");
//
//    	alertVO = new AlertVO(AlertVO.ITEM_CODE_SERVICE_RECORD_EXPIRE_DAY);
//    	alertVO.setRecord_service_list(alertDao.getExpireServiceRecordList(alertVO));
//    	//alertVO.setPuller_id(userVO.getUserid());
//    	alertVO.setDescription("개의 녹취 서비스가 하루 뒤 만료됩니다.");
//    	if(alertVO.getRecord_service_list().size()>0)
//    		sendExpiredEmail("녹취 서비스가 내일", alertVO.getRecord_service_list());					// 대상자에게 메일 전송
//    	logger.info("[서비스 만료 알림 - 하루 전] 성공 "
//    			+ "(알림 전송: " + alertVO.getRecord_service_list().size() + ")");
//	}

	//막음
//	@Scheduled(cron="00 00 00 * * *")
//	public void pushAlertWillBeExpiredServiceCallback(){
//    	AlertVO alertVO = new AlertVO(AlertVO.ITEM_CODE_SERVICE_CALLBACK_EXPIRE_WEEK);
//    	alertVO.setCallback_service_list(alertDao.getExpireServiceCallbackList(alertVO));
//    	//alertVO.setPuller_id(userVO.getUserid());
//    	alertVO.setDescription("개의 콜백메시징 서비스가 일주일뒤 만료됩니다.");
//    	if(alertVO.getCallback_service_list().size()>0)
//    		sendExpiredEmail("콜백메시징 서비스가 일주일 뒤", alertVO.getCallback_service_list());				// 대상자에게 메일 전송
//    	logger.info("[서비스 만료 알림 - 1주 전] 성공 "
//    			+ "(알림 전송: " + alertVO.getCallback_service_list().size() + ")");
//
//    	alertVO = new AlertVO(AlertVO.ITEM_CODE_SERVICE_CALLBACK_EXPIRE_DAY);
//    	alertVO.setCallback_service_list(alertDao.getExpireServiceCallbackList(alertVO));
//    	//alertVO.setPuller_id(userVO.getUserid());
//    	alertVO.setDescription("개의 콜백메시징 서비스가 하루 뒤 만료됩니다.");
//    	if(alertVO.getCallback_service_list().size()>0)
//    		sendExpiredEmail("콜백메시징 서비스가 내일", alertVO.getCallback_service_list());					// 대상자에게 메일 전송
//    	logger.info("[서비스 만료 알림 - 하루 전] 성공 "
//    			+ "(알림 전송: " + alertVO.getCallback_service_list().size() + ")");
//	}
}
