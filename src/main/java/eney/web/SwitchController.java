package eney.web;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import eney.service.IvrService;

@Controller
public class SwitchController {
	
	@Autowired
	IvrService swtichService;
	
	/**
	 * 교환기 상태정보 조회
	 * @param result_code
	 * @param request
	 * @return
	 */
	@RequestMapping("/ivr_stat")
	public @ResponseBody Integer ivr_stat(String result_code, HttpServletRequest request){
		return swtichService.switchStatusChange(result_code, request) ? 1 : 2;
		
	}
}
