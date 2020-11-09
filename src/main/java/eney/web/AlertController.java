package eney.web;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import eney.domain.AlertVO;
import eney.domain.UserVO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eney.domain.AlertVO;
import eney.domain.UserVO;
import eney.exception.AccessDeniedException;
import eney.service.AlertService;

@Controller
@RequestMapping("alertAction")
public class AlertController {
	
	@Resource
	AlertService alertService;
	
	/**
	 * 회원별 알람 내역 리스트
	 * @return 알람 리스트
	 */
	@RequestMapping("reload.do")
	public @ResponseBody List<Map<String, Object>> AlertReload() {
		return alertService.getReloadData();
	}
	/**
	 * 알람 출력
	 * @param alert_id
	 * @return 알람 정보
	 * @throws AccessDeniedException
	 */
	@ResponseBody
	@RequestMapping("check.do")
	public String AlertCheck(@RequestParam Integer alert_id) throws AccessDeniedException{
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		AlertVO param = new AlertVO();
		param.setAlert_id(alert_id);
		param.setPuller_id(userVO.getUserid());
		alertService.checkAlert(param);
		return new String("OK");
	}
}
