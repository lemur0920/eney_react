package eney.service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import eney.domain.MessageVO;
import eney.domain.CommonVO;
import eney.domain.IvrStatusLogVo;
import eney.domain.MessageVO;
import eney.mapper.LogDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import eney.domain.CommonVO;
import eney.domain.IvrStatusLogVo;
import eney.mapper.IvrDao;
import eney.mapper.LogDao;


@Service
public class IvrService {
	
	private static final Logger logger = LoggerFactory.getLogger(IvrService.class);
	
	@Autowired
	SupplyService supplyService;
	@Autowired
	MessageService messageService;
	
	@Autowired
	IvrDao ivrDao;

	@Autowired
    LogDao logDao;
	
	
	public Boolean switchStatusChange(String result_code, HttpServletRequest request) {
		CommonVO commonCodeSelectQuery = new CommonVO();
		commonCodeSelectQuery.setCategory(CommonVO.CATEGORY_SWITCH_DEAD_ALERT);
		Map<String, String> settingMap = supplyService.getCommonMap(commonCodeSelectQuery);
		
		
		if(settingMap.get("allow_ip") != null && ! settingMap.get("allow_ip").isEmpty() && ! request.getRemoteAddr().equals(settingMap.get("allow_ip"))){
			logger.info("[교환기 상태] 차단 - 허용되지 않은 아이피로 접속되었습니다. (IP: " + request.getRemoteAddr() + ")");
			return false;
		}
		String[] phoneNumbers = settingMap.get("phone_numbers").split(",");
		
		IvrStatusLogVo ivrStatusLogData = new IvrStatusLogVo();
		ivrStatusLogData.setStatus_code(result_code);
		ivrStatusLogData.setIp(request.getRemoteAddr());
		
		logDao.insertIvrStatusLog(ivrStatusLogData);
		
		
		switch(result_code){
			case "01":
				if(Boolean.valueOf(settingMap.get("send_sms"))){
	    			for(String phoneNumber: phoneNumbers){

	    				MessageVO messageVO = new MessageVO();

						messageVO.setCallback(MessageService.ADDR_ENEY_OFFICE);
						messageVO.setDstaddr(phoneNumber.trim());
						messageVO.setText("[교환기 오류]\n\n교환기 프로세서 kill 알림!");

	    				messageService.sendMsg(messageVO);
	    			}
				}
    			logger.warn("[교환기 상태] call 프로세스 kill (result_code: " + result_code + ", IP: " + request.getRemoteAddr() + ")");
				break;
			case "02":
				if(Boolean.valueOf(settingMap.get("send_sms"))){
	    			for(String phoneNumber: phoneNumbers){

						MessageVO messageVO = new MessageVO();

						messageVO.setCallback(MessageService.ADDR_ENEY_OFFICE);
						messageVO.setDstaddr(phoneNumber.trim());
						messageVO.setText("[교환기 오류]\n\n교환기 프로세서 재기동 알림!");

						messageService.sendMsg(messageVO);
	    			}
				}
    			logger.warn("[교환기 상태] call 프로세스 재기동 (result_code: " + result_code + ", IP: " + request.getRemoteAddr() + ")");
				break;
			default:
				if(Boolean.valueOf(settingMap.get("send_sms"))){
					for(String phoneNumber: phoneNumbers){
						MessageVO messageVO = new MessageVO();

						messageVO.setCallback(MessageService.ADDR_ENEY_OFFICE);
						messageVO.setDstaddr(phoneNumber.trim());
						messageVO.setText("[교환기 오류]\n\n알려지지 않은 오류 코드 접수 (" + result_code + ")");

						messageService.sendMsg(messageVO);
	    			}
				}
				logger.warn("[교환기 상태] 오류 - 알려지지 않은 상태 코드입니다. (result_code: " + result_code + ", IP: " + request.getRemoteAddr() + ")");
				return false;
		}
		
		return true;
	}
	
	public Boolean getIvrStatus(){
		IvrStatusLogVo ivrStatusLog = logDao.selectIvrStatus();
		
		switch(ivrStatusLog.getStatus_code()){
		case IvrStatusLogVo.STATUS_CODE_IVR_PROCESS_KILL:
			return false;
		}
		
		return true;
	}

	
	/**
	 * 교환기에서 최근 50개의 통화 통계를 조회
	 * @return 조회 결과 (all, connected, connect_failed, agent_not_found, warn, connect_failed_per, agent_not_found_per, warn_per)
	 */
	public Map<String, Long> getLast50CdrSummary(){
		Calendar calendar = Calendar.getInstance();
    	String monthString = new SimpleDateFormat("MM").format(calendar.getTime());
    	Map<String, Long> last50CdrSummary = ivrDao.getLast50CdrSummary(monthString);
    	
		long all = last50CdrSummary.get("all");
		long connected = last50CdrSummary.get("connected");
		long connect_failed = last50CdrSummary.get("connect_failed");
		long agent_not_found = last50CdrSummary.get("agent_not_found");

		long connect_per = connected * 100 / all;
		long connect_failed_per = (connect_failed ) * 100 / all ;
		long agent_not_found_per = (agent_not_found) * 100 / all;

		last50CdrSummary.put("connect_per", connect_per);
		last50CdrSummary.put("connect_failed_per", connect_failed_per);
    	last50CdrSummary.put("agent_not_found_per", agent_not_found_per);

		return last50CdrSummary;
	}
	
}
