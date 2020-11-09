package eney.api.v1.web;

import java.util.List;

import javax.annotation.Resource;

import eney.api.v1.domain.ApiTokenVo;
import eney.api.v1.service.ApiMainService;
import eney.api.v1.service.ApiUserService;
import eney.domain.AgentVO;
import eney.domain.ColoringUploadVO;
import eney.domain.UserVO;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import eney.service.LinkService;
import eney.service.SupplyService;

@Controller
@RequestMapping("/apis/user")
public class ApiUserController {
	
	@Resource
    ApiUserService apiUserService;
	
	@Resource
    ApiMainService apiMainService;
	
	@Resource
	SupplyService supplyService;
	
	@Resource
	LinkService linkService;
	
	/**
	 * 개발자센터 안내 뷰페이지
	 */
	@RequestMapping("/main")
	public void ApiUserMainView(){}
	
	/**
	 * 개발자센터 시작하기 뷰페이지
	 */
	@RequestMapping("/start")
	public void ApiUserStartView(){
		
	}
	
	/**
	 * 번호, 컬러링, 착신멘트 뷰페이지
	 * @param model
	 */
	@RequestMapping("/resources")
	public void ApiUserResourcesView(Model model){
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		//colorring
		ColoringUploadVO uploadVO = new ColoringUploadVO();
		uploadVO.setUserid(userVO.getUserid());
		uploadVO.setPresent_page(-1);
		List<ColoringUploadVO> uploadList = supplyService.getUploadList(uploadVO);
		
		model.addAttribute("userVO", userVO);
		model.addAttribute("uploadList", uploadList);
		
		//050번호
		AgentVO agentVO = new AgentVO();
		agentVO.setUser_id(userVO.getUserid());
		
		List<AgentVO> agentList = supplyService.getAgentVOList(agentVO);
		model.addAttribute("agentList", agentList);
		
	}
	
	/*token key 리스트 출력*/
	@RequestMapping("/token")
	public void ApiUserTokenView(Model model){
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ApiTokenVo apiTokenVo = new ApiTokenVo();
		apiTokenVo.setToken_userid(userVO.getUserid());
		List<ApiTokenVo> tokenList = apiMainService.getTokenInfoList(userVO);
		
		List<ApiTokenVo> messageList = apiMainService.getMessageTokenInfoList(userVO);
		model.addAttribute("tokenList", tokenList);
		model.addAttribute("messageList",messageList);
	}
	
	/**
	 * API document 페이지 뷰
	 * @param model
	 */
	@RequestMapping("/doc")
	public void ApiDocumentPage(Model model){}
	
	@RequestMapping("/message")
	public void ApiMessagePage(Model model){}
	
}
