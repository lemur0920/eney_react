package eney.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eney.domain.UserVO;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import eney.domain.LinkInfoVo;
import eney.domain.UserVO;
import eney.service.LinkService;

@Controller
@RequestMapping("/link")
public class LinkController {
	
	@Resource
	LinkService linkService;
	
	/**
	 * 통계를 위한 단축 URL 링크 (링크 접속 키를 설정하지 않는경우)
	 * @param linkId 링크 IDX (BASE62 인코딩)
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{linkId}")
	public String linkContact(@PathVariable("linkId") String linkId,
									HttpServletRequest request,
									HttpServletResponse response){
		return linkContact(linkId, null, null, request, response);
	}
	
	/**
	 * 통계를 위한 단축 URL 링크 (링크 접속 키를 설정하였으나 광고 채널을 설정하지 않은경우)
	 * @param linkId 링크 IDX (BASE62 인코딩)
	 * @param linkKey 링크 접속 키
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{linkId}/{linkKey}")
	public String linkContact(@PathVariable("linkId") String linkId,
									@PathVariable("linkKey") String linkKey,
									HttpServletRequest request,
									HttpServletResponse response){
		return linkContact(linkId, linkKey, null, request, response);		
	}
	
	/**
	 * 통계를 위한 단축 URL 링크
	 * @param linkId 링크 IDX (BASE62 인코딩)
	 * @param linkKey 링크 접속 키
	 * @param linkChannel 광고 채널 IDX (BASE62 인코딩)
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("{linkId}/{linkKey}/{linkChannel}")
	public String linkContact(@PathVariable("linkId") String linkId,
									@PathVariable("linkKey") String linkKey,
									@PathVariable("linkChannel") String linkChannel,
									HttpServletRequest request,
									HttpServletResponse response){
		String redirectUrl = linkService.linkContact(linkId, linkKey, linkChannel, request);
		if(redirectUrl != null){
			return "redirect:" + redirectUrl;
		} 
		return "redirect:/";			
	}
	/**
	 * 링크 추가를 위한 컨트롤러
	 * @param linkUrl 링크 URL
	 * @param linkKey 링크 접속 키
	 * @return
	 */
	@RequestMapping("addLink.do")
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public @ResponseBody String addLink(@RequestParam("linkUrl") String linkUrl,
								@RequestParam(value="linkKey", required=false) String linkKey){
		UserVO userInfo = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return linkService.makeLink(linkUrl, linkKey, null, null, userInfo);
	}

}
