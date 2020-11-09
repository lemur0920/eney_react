package eney.web;


import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import eney.domain.*;
import eney.service.*;
import com.google.common.hash.Hashing;
import eney.domain.UserVO;
import eney.service.AnalyticsService;
import eney.service.LinkService;
import eney.service.SupplyService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;


@Controller
@RequestMapping("/service/analytics")
public class AnalyticsController {
	
	@Resource
	private AnalyticsService analyticsService;
	
	@Resource
	private SupplyService supplyService;

	@Resource
	private UserService userService;
	
	@Resource
    LinkService linkService;
	
	@Resource
	HttpServletRequest request;


	/*
	 * 콜분석 main 페이지 연결
	 */
	
	@RequestMapping(value="/main.do")  
	public String companyIntroView(Model model,String idx){
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.out.println(Hashing.crc32()
				.hashString("http://www.ppomppu.co.kr/zboard/zboard.php?id=card_consult", StandardCharsets.UTF_8).toString());

		//String from="",to ="";
		//supplyService.getCallLogListById(userVO.getUserid(),from,to);

		List<Map<String, Object>> callCountSummary = analyticsService.getAllCallAnalyticsById(userVO.getUserid());
		Map<String,Map<String, Integer>> campaignCountSummary =analyticsService.getCampaignCountSummaryById(userVO.getUserid(),idx);
		//List<LinkTagVo> tagList = analyticsService.getTagListByUserid(userVO.getUserid());

		List<Map<String, Object>> titleList = analyticsService.getTitleListByUserId(userVO.getUserid());
		//List<Map<String, Object>> callCountSummary = adminService.getCallCountSummary();

		model.addAttribute("userVO", userVO);
		model.addAttribute("tagList", titleList);
		model.addAttribute("callCountSummary", callCountSummary);
		model.addAttribute("campaignCountSummary",campaignCountSummary);
		return "analytics/main";
	}
	/**
	 *  캠페인 추가 
	 *    
	 */



//	@RequestMapping(value="/addtag.do")
//	public  String addtag(LinkTagVo tag, String[] channels,RedirectAttributes redirectAttributes){
//		String url = request.getParameter("tag_url");
//		UserVO user = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//		//先 중복체크 false 리턴시 중복없음 , true 리턴시 중복임
//		if(analyticsService.checkUserTag(tag)){
//			//TODO 중복의 경우 기존태그 사용리턴
//
//		}else{
//			List <LinkManagementVo> management_channels_idx = new ArrayList <> ();
//			int tagIdx = analyticsService.addtag(tag, channels, user,url,management_channels_idx);
//		System.out.println("tagIdx : " +tagIdx);
//
////		LinkManagementVo linkManagement= new LinkManagementVo();
////
////		linkManagement.setManagement_tag_idx(tagIdx);
////		int management_tag_idx = linkManagement.getManagement_tag_idx();
////	    management_channels_idx.add(linkManagement);
//	//    management_channels_idx.add(new LinkManagementVo(tagIdx));
//
//	    //linkManagement.setManagement_channel_idx(channel_idx);
//		//tagIdx값을 기준으로 management_channel_idx select하기 ->반환값 List
//	    List <String> shortUrl = new ArrayList <>();
//
//	    management_channels_idx = analyticsService.getManagement_channel_idx(tagIdx);
//			for(LinkManagementVo vo:management_channels_idx){
//				int management_channel_idx=vo.getManagement_channel_idx();
//				System.out.println("management_channel_idx : "+management_channel_idx);
//
//				LinkChannelVo linkChannelVo  =analyticsService.linkIdxSelector(management_channel_idx);
//			  	int link_idx = linkChannelVo.getLink_idx();
//			  	System.out.println("link_idx ;" + link_idx);
//
//			  	LinkInfoVo linkInfoVo = analyticsService.linkKeySelector(link_idx);
//			  	String link_key = linkInfoVo.getLink_key();
//			  	System.out.println("link_key : "+link_key);
//
//			  	String shortUrls = linkService.linkUrl(link_idx, link_key,management_channel_idx);
//			  	System.out.println("shortUrls : " + shortUrls);
//
//				shortUrl.add(shortUrls);
//			}
//
//
//
//			redirectAttributes.addAttribute("tagIdx", tagIdx);
//			redirectAttributes.addAttribute("shortUrl", shortUrl);
//
//			System.out.println(shortUrl);
//			return "redirect:/analytics/list.do";
//		}
//
//		return "";
//
//	}
	
//	@RequestMapping(value="/list.do")
//	public String compaignListView
//(@RequestParam(required=false) Integer tagIdx,
//		@RequestParam(required=false)  LinkInfoVo linkInfo		,
//		@RequestParam(required=false) List<String> shortUrl ,Model model){
//		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		AgentVO selectQuery = new AgentVO();
//		selectQuery.setUser_id(userVO.getUserid());
//		selectQuery.setPresent_page(-1);
//
//		List<AgentVO> agentList = supplyService.getAgentVOList(selectQuery);
//		List<LinkTagVo> tagList = analyticsService.getTagListByUserid(userVO.getUserid());
//		//int useAmount = analyticsService.getUsePatchcallAmount();
//
//
//		model.addAttribute("linkInfo",linkInfo);
//		model.addAttribute("userVO", userVO);
//		model.addAttribute("agentList", agentList);
//		model.addAttribute("tagList", tagList);
//		model.addAttribute("no", tagIdx);
//		model.addAttribute("shortUrl", shortUrl);
//		System.out.println("shortUrl : "+shortUrl);
//
//		return "analytics/list";
//	}

//	@RequestMapping(value ="/addShortUrl.do")
//	public String addShortUrl(HttpServletRequest httpRequest, String[] channels, String tag_title, String tag_description){
//		String requestUrl = httpRequest.getRequestURL().toString();
//		String url = request.getParameter("tag_url").trim();
//
//		UserVO user = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//		urlService.addShortUrl(requestUrl,url,user.getUserid(),channels,tag_title,tag_description);
//
//		return "redirect:/analytics/shortUrlManagement.do";
//	}

//	@RequestMapping(value = "/shortUrlManagement.do")
//	public String shortUrlManagement(Model model,String nowPage){
//		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//
//		List<Map<String, Object>> titleList = analyticsService.getTitleListByUserId(userVO.getUserid());
//		ListVO listVO = analyticsService.getCampaignPagingByUserId(userVO.getUserid(),nowPage);
//
//		model.addAttribute("tagList", titleList);
//		model.addAttribute("ListVO",listVO);
//
//		return "analytics/shorturl_management";
//	}
//
//	@RequestMapping(value = "/ajaxShortUrl.do")
//	@ResponseBody
//	public List<ShortUrlJoinVO> getShortUrlList(String idx){
//
//		return analyticsService.getShortUrlListByIdx(idx);
//	}
//
//	@RequestMapping(value = "/ajaxShortUrlCustomizing.do")
//	@ResponseBody
//	public String urlCustomizing(String name){
//		return "a";
//	}

	@RequestMapping(value="/management",method=RequestMethod.GET)
	public @ResponseBody ResponseEntity<?>  getAnalyticsViewIdList (@RequestParam(value="page", defaultValue="1") Integer page, Authentication authentication){

		UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
		UserVO userVO = userService.loadUserByUsername(user.getUserId());

		GAViewIdVO gaViewIdVO = new GAViewIdVO();

		gaViewIdVO.setPageNo(page);
		gaViewIdVO.setUserId(userVO.getUserid());


		return new ResponseEntity<>(analyticsService.getAnalyticsViewIdList(gaViewIdVO), HttpStatus.OK);
	}

	@RequestMapping(value="/management",method=RequestMethod.POST)
	public @ResponseBody ResponseEntity<?>  insertAnalyticsViewId (@RequestBody GAViewIdVO gaViewIdVO, Authentication authentication){
		UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
		UserVO userVO = userService.loadUserByUsername(user.getUserId());
		gaViewIdVO.setUserId(userVO.getUserid());

		int result = analyticsService.insertAnalyticsViewId(gaViewIdVO);
		if (result == 0) {
			return new ResponseEntity<>("동일한 VIEW ID가 있습니다", HttpStatus.BAD_REQUEST);
		}else{
			return new ResponseEntity<>(HttpStatus.OK);
		}
	}

	@RequestMapping(value="/management",method=RequestMethod.PUT)
	public @ResponseBody ResponseEntity<?>  editAnalyticsViewId (@RequestBody GAViewIdVO gaViewIdVO, Authentication authentication){

		UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
		UserVO userVO = userService.loadUserByUsername(user.getUserId());
		gaViewIdVO.setUserId(userVO.getUserid());

		System.out.println("=====");
		System.out.println(gaViewIdVO);
		System.out.println("=====");

		return new ResponseEntity<>(analyticsService.editAnalyticsViewId(gaViewIdVO),HttpStatus.OK);
	}

	@RequestMapping(value="/management/{idx}",method=RequestMethod.DELETE)
	public @ResponseBody ResponseEntity<?>  deleteAnalyticsViewId (@PathVariable("idx") Integer idx,Authentication authentication){
		GAViewIdVO gaViewIdVO = new GAViewIdVO();

		UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
		UserVO userVO = userService.loadUserByUsername(user.getUserId());
		gaViewIdVO.setUserId(userVO.getUserid());
		gaViewIdVO.setIdx(idx);

		return new ResponseEntity<>(analyticsService.deleteAnalyticsViewId(gaViewIdVO),HttpStatus.OK);
	}

}
