package eney.web;

import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import eney.domain.*;
import eney.util.ExcelView;
import eney.util.FileUploadUtil;
import eney.util.SFTPUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import eney.domain.AcsTransmitVO;
import eney.domain.AgentVO;
import eney.domain.CallLogVO;
import eney.domain.CallbackSmsVO;
import eney.domain.ColoringRegisterVO;
import eney.domain.ColoringSampleVO;
import eney.domain.ColoringUploadVO;
import eney.domain.CommonVO;
import eney.domain.EpointRefundVO;
import eney.domain.FileVO;
import eney.domain.LinkChannelVo;
import eney.domain.LinkInfoVo;
import eney.domain.PaymentVO;
import eney.domain.RecordVO;
import eney.domain.UserVO;
import eney.domain.VnoVO;
import eney.exception.AccessDeniedException;
import eney.mapper.AdminDao;
import eney.mapper.MainDao;
import eney.mapper.SupplyDao;
import eney.service.AcsService;
import eney.service.FileService;
import eney.service.LinkService;
import eney.service.PaymentService;
import eney.service.SupplyService;
import eney.service.UserService;
import eney.util.ExcelView;
import eney.util.FileUploadUtil;
import eney.util.SFTPUtil;


@Controller
@RequestMapping("/service")
public class ServiceController {
	
	private static final Logger logger = LoggerFactory.getLogger(ServiceController.class);
	
	@Resource
	HttpSession session;
	@Resource
	HttpServletRequest request;
	
	@Resource
	SupplyService supplyService;
	@Resource
	FileService fileService;
	@Resource
	PaymentService paymentService;
	@Resource
	LinkService linkService;
	@Resource
	UserService userService;
	@Resource
	AcsService acsService;
	@Resource
    SFTPUtil sftpUtil;
	@Resource
	MainDao mainDao;
	@Resource
	AdminDao adminDao;
	@Resource
	SupplyDao supplyDao;
	@Resource
    ExcelView excelView;
	@Autowired
    FileUploadUtil fileUploadUtil;
	
	
	
	/**
	 * 패치콜 번호관리
	 * @param agentVO
	 * @return 현재 사용중인 번호 목록 리스트
	 * @throws Throwable
	 */
	@RequestMapping(value="/050/manage.do", method=RequestMethod.GET)
	public ModelAndView manage050View(@ModelAttribute("agentVO") AgentVO agentVO)
			throws Throwable{
		long startTime = System.currentTimeMillis();
		Map<String,String> map = new HashMap<>();
		map.put("type", "번호관리View");
		
		ModelAndView mav = new ModelAndView();
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mav.addObject("userVO", userVO);
		
		//AgentVO agentVO = new AgentVO();
		agentVO.setUser_id(userVO.getUserid());
		
		/*List<AgentVO> agentList = supplyService.getAgentVOList(agentVO);
		mav.addObject("agentList", agentList);*/
		
		List<AgentVO> sk = supplyService.getAgentVOList(agentVO);
		mav.addObject("sk", sk);
		
		/*List<AgentVO> sejong = supplyService.getAgent22(agentVO);
		mav.addObject("sejong", sejong);*/

		mav.addObject("agentVO",agentVO);
		long endTime = System.currentTimeMillis();
		map.put("time", ( endTime - startTime )/1000.0f +"초");
		mainDao.insertTimeMeasure(map);
		logger.warn("번호관리 view 전환까지 걸린 시간  = " +( endTime - startTime )/1000.0f +"초");

		return mav;
	}
	
	@RequestMapping(value="/050/manage.do", method=RequestMethod.POST)
	public ModelAndView manage050Submit(@ModelAttribute("agentVO") AgentVO agentVO) 
			throws Throwable{
		return manage050View(agentVO);
		
	}
	
	/**
	 * agentVo idx 값을 가지고 clickToCall 링크를 찾음
	 * @param idx
	 * @return
	 */
	@RequestMapping(value="/050/getClickToCall")
	public @ResponseBody String getClickToCall(@RequestParam("idx") Integer idx, @RequestParam(value="keyword", required=false) String keyword){
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		AgentVO agentVO = new AgentVO();
		agentVO.setIdx(idx);
		agentVO.setUser_id(userVO.getUserid());
		agentVO = supplyService.getAgentVO(agentVO);
		
		if(agentVO != null){
			LinkInfoVo selectQuery = new LinkInfoVo();
			selectQuery.setLink_url("tel:" + agentVO.getVno());
			selectQuery.setLink_type(LinkInfoVo.LINK_TYPE_CLICK_TO_CALL);
			LinkInfoVo linkInfo = linkService.selectLinkInfo(selectQuery);
			if(linkInfo != null){
				if(keyword != null && !"".equals(keyword)){
					return linkService.makeLinkChannel(linkInfo.getLink_idx(), keyword, userVO.getUserid());
				} else {
					return linkService.linkUrl(linkInfo.getLink_idx(), linkInfo.getLink_key(), null);
				}
			}
		}		
		return "";
	}
	
	/**
	 * 사용자가 생성한 Click To Call 채널 조회
	 * @param idx ClickToCall IDX
	 * @return 채널 리스트
	 */
	@RequestMapping(value="/050/getClickToCallChannel")
	public @ResponseBody List<String> getClickToCallChannel(@RequestParam("idx") Integer idx){
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<LinkChannelVo> channelList = linkService.selectLinkChannel(idx, LinkChannelVo.LINK_CHANNEL_REF_CATEGORY_USER_MAKE,
				null, userVO.getUserid());
		
		List<String> channelNameList = new LinkedList<>();
		for(LinkChannelVo each : channelList){
			channelNameList.add(each.getLink_channel_name());
		}
		return channelNameList;
	}
	/**
	 * 050 전화번호 추가 등록 페이지 
	 * @param agentVO
	 * @param mav
	 * @throws Throwable
	 */
	@RequestMapping(value="/050/register.do", method=RequestMethod.GET)
	public ModelAndView register050View(@ModelAttribute AgentVO agentVO, @ModelAttribute CallbackSmsVO callbackVO, ModelAndView mav)
			throws Throwable{
		
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ColoringUploadVO param = new ColoringUploadVO();
		CallbackSmsVO userid = new CallbackSmsVO();
		RecordVO recordVO = new RecordVO();
		userid.setUserid(userVO.getUserid());
		param.setUserid(userVO.getUserid());
		recordVO.setUserid(userVO.getUserid());
		CallbackSmsVO callbackCheck = userService.checkCallbackSmsService(userVO.getUserid());
		List<AcsTransmitVO> messagingList = acsService.getMessaingListByUserid(userVO.getUserid());
		CommonVO commonVO = new CommonVO();
		commonVO.setCategory("050_NUMBER_ASSIGNED");
		
		Map<String, List<CommonVO>> numberOption = supplyService.get050_1th2ndNumList(commonVO);
		
		mav.addObject("numberOption", numberOption);
		mav.addObject("callbackCheck", callbackCheck);
		mav.addObject("messagingList", messagingList);
		mav.addObject("userVO", userVO);
		mav.addObject("mapping", "register");
		
		return mav;
	}
	
	/**
	 * 050 전화번호 추가 등록
	 * @param agentVO
	 * @param bindingResult
	 * @param mav
	 * @throws Throwable
	 */
	@RequestMapping(value="/050/register.do", method=RequestMethod.POST)
	public ModelAndView regsiter050Submit(@Valid @ModelAttribute AgentVO agentVO, @ModelAttribute CallbackSmsVO callbackVO, BindingResult bindingResult, ModelAndView mav) throws Throwable{
		if(bindingResult.hasErrors()){
			mav = register050View(agentVO,callbackVO, mav);
		} else {
			long startTime = System.currentTimeMillis();
			Map<String,String> map = new HashMap<>();
			map.put("type", "번호관리View");
			UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			RecordVO recordVO = new RecordVO();
			recordVO.setUserid(userVO.getUserid());
			RecordVO record = userService.selectRecordServiceListByUserVO(recordVO);
			//포인트 검사 및 삭감 
			Map<String,String> res = supplyService.processEpoint050Regist();
			if(res.get("result").equals("fail")){
				mav.addObject("script", "alert('"+res.get("message")+"')");
				return mav;
			}
			
			//MMS 관련
			if(agentVO.getSms().equals("M")){
				List<MultipartFile> files = agentVO.getFiles();
				List<FileVO> fileVOList = fileService.processUpload(files, "MMS", 0, request);
				for(FileVO file : fileVOList ){
					agentVO.setMms_file("/usr/local/upload/mms"+file.getUpload_name()+"."+file.getExtenstion());
				}
			}
			
			agentVO.setUser_id(userVO.getUserid());
			///컬러링, 착신멘트 사용자가 등록 못하게
			agentVO.setColorring_idx(0);
			agentVO.setRcvment_idx(0);
			
			supplyService.submit050Register(agentVO);
			
			
			if(agentVO.getVno().substring(0,4).equals("0507") && record != null){
				agentVO.setRec_yn("Y");
				supplyService.update050Agent22ByVno(agentVO);
			}
			
			agentVO.setReg_gubun("A");
			supplyService.update050VnoVO(agentVO);
			
			if(agentVO.getMake_click_to_call() == true){
				linkService.makeLink("tel:"+agentVO.getVno(), linkService.keyGen(), null, LinkInfoVo.LINK_TYPE_CLICK_TO_CALL, userVO);
			}
			long endTime = System.currentTimeMillis();
			map.put("time", ( endTime - startTime )/1000.0f +"초");

			logger.warn("번호 등록까지 걸린 시간  = " +( endTime - startTime )/1000.0f +"초");
			mainDao.insertTimeMeasure(map);
			
			Map<String,Integer> enablePatchcall = adminDao.getEnablePatchCallSKB();
			supplyDao.skbUseNumber(enablePatchcall);
			
			mav.setViewName("redirect:manage.do");
		}
		return mav;
	}
	/**
	 * 050 번호 수정 페이지 뷰
	 * @param idx 해당 idx의 정보를 수정
	 * @param mav
	 * @return
	 */
	//@ModelAttribute("agentVO")
	@RequestMapping(value="/050/update.do", method=RequestMethod.GET)
	public ModelAndView update050View1(@RequestParam String vno, ModelAndView mav){
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		CallbackSmsVO callbackCheck = userService.checkCallbackSmsService(userVO.getUserid());
		mav = new ModelAndView("service/050/register");
		mav.addObject("mapping", "update");
		
		AgentVO agentVO = new AgentVO();
		agentVO.setVno(vno);
		agentVO.setUser_id(userVO.getUserid());
		agentVO = supplyService.getAgentVO(agentVO); 
		
		mav.addObject("agentVO", agentVO);
		
		CommonVO commonVO = new CommonVO();
		commonVO.setCategory("050_NUMBER_ASSIGNED");
		Map<String, List<CommonVO>> numberOption = supplyService.get050_1th2ndNumList(commonVO);
		mav.addObject("numberOption", numberOption);
		
		ColoringUploadVO param = new ColoringUploadVO();
		param.setUserid(userVO.getUserid());
		List<AcsTransmitVO> messagingList = acsService.getMessaingListByUserid(userVO.getUserid());
		
		CallbackSmsVO callback = new CallbackSmsVO();
		callback.setUserid(userVO.getUserid());
		
		if(agentVO.getSms_yn().equals("M")){
			String fileName = agentVO.getMms_file().substring(22);
			String onlyName = fileName.substring(0,36);
			
			FileVO file = fileService.getFileVO(onlyName);
			
			String agentVO_mms_file = file.getName();
			mav.addObject("agentVO_mms_file",agentVO_mms_file);
		}
		
		
		mav.addObject("callbackCheck", callbackCheck);
		mav.addObject("messagingList", messagingList);
		
		return mav;
	}
	/**
	 * 050 번호 수정
	 * @param agentVO
	 * @param bindingResult
	 * @param mav
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/050/update.do", method=RequestMethod.POST)
	public ModelAndView update050Submit(@ModelAttribute AgentVO agentVO, @ModelAttribute CallbackSmsVO callbackVO, BindingResult bindingResult, ModelAndView mav) throws Exception{
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(bindingResult.hasErrors()){
			mav = update050View1(agentVO.getVno(), mav);
		}else{
			long startTime = System.currentTimeMillis();
			Map<String,String> map = new HashMap<>();
			map.put("type", "번호 수정");
			//MMS 관련
			if(agentVO.getSms_yn().equals("M")){
				List<MultipartFile> files = agentVO.getFiles();
				List<FileVO> fileVOList = fileService.processUpload(files, "MMS", 0, request);
				for(FileVO file : fileVOList ){
					agentVO.setMms_file("/usr/local/upload/mms/"+file.getUpload_name()+"."+file.getExtenstion());
				}
			}
			
			supplyService.update050Agent(agentVO);
						
			/*LinkInfoVo selectQuery = new LinkInfoVo();
			selectQuery.setLink_url("tel:" + agentVO.getVno());
			selectQuery.setLink_type(LinkInfoVo.LINK_TYPE_CLICK_TO_CALL);
			
			LinkInfoVo linkInfo = linkService.selectLinkInfo(selectQuery);
			
			if(agentVO.getMake_click_to_call() == true){
				if(linkInfo == null){
					linkService.makeLink("tel:"+agentVO.getVno(), linkService.keyGen(), null, LinkInfoVo.LINK_TYPE_CLICK_TO_CALL, userVO);
				}
			} else {
				if(linkInfo != null){
					linkService.deleteLinkInfo(linkInfo.getLink_idx());
				}
			}*/
			
			long endTime = System.currentTimeMillis();
			logger.warn("번호 수정까지 걸린 시간  = " +( endTime - startTime )/1000.0f +"초");
			
			map.put("time", ( endTime - startTime )/1000.0f +"초");
			mainDao.insertTimeMeasure(map);
			
			Map<String,Integer> enablePatchcall = adminDao.getEnablePatchCallSKB();
			supplyDao.skbUseNumber(enablePatchcall);
			
			mav.setViewName("redirect:manage.do");
		}
		return mav;
	}
	/**
	 * 050 반호 삭제
	 * @param agentVO
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	@RequestMapping(value="/050/close.do")
	public ModelAndView close050(@ModelAttribute AgentVO agentVO) throws NoSuchAlgorithmException{
		ModelAndView mav = new ModelAndView("service/050/manage");
		try{
			boolean flag = supplyService.checkPassword(agentVO);
			if(flag){
				supplyService.close050Service(agentVO);
			}
			mav.addObject("script", "closeCompleted()");
		}catch(AccessDeniedException e){
			mav.addObject("script", "passwordNotEqual()");
		}
		return mav;
	}
	

	
//	@RequestMapping(value="/050/update.do", method=RequestMethod.GET)
//	public ModelAndView update050View2(@ModelAttribute AgentVO agentVO){
//		ModelAndView mav = new ModelAndView("service/050/register");
//		mav.addObject("mapping", "update");
//		
//		
//		
//		return mav;
//	}
	/**
	 * 050 번호 사용여부 확인
	 * @param vnoVO
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping("/050/numberCheck.do")
	public Map<String,Object> numberCheck050(@RequestBody VnoVO vnoVO) throws Exception{
		//logger.debug("customLog:::" + vnoVO);
		Map<String,Object> res = new HashMap<String,Object>();

		VnoVO inputVO = supplyService.check050Number(vnoVO);
		logger.debug("customLog:::" + inputVO);
		if(inputVO==null){
			res.put("status", "FAIL");
			List<VnoVO> recommendNumList = supplyService.getRecommendNumList(vnoVO); 
			logger.debug("customLog:::FAIL");
			logger.debug("customLog:::recommendList:::" + recommendNumList);
			res.put("recommendList", recommendNumList);
			return res;
		}
		else{
			res.put("status", "SUCCESS");
			res.put("inputVO", inputVO);
		}
		
		return res;
	}
	/**
	 * 콜로그 리스트
	 * @param callLogVO
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value="/050/log.do", method=RequestMethod.GET)
	public ModelAndView log050View(@ModelAttribute("searchForm") CallLogVO callLogVO) throws Throwable{
		long startTime = System.currentTimeMillis();
		Map<String,String> map = new HashMap<>();
		map.put("type", "콜로그 View");
		ModelAndView mav = new ModelAndView();
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mav.addObject("userVO", userVO);
		
		//CallLogVO callLogVO = new CallLogVO();
//		if(present_page!=null && present_page != 1){
//			callLogVO.setPresent_page(present_page);
//		}
		callLogVO.setUser_id(userVO.getUserid());
		
		if(callLogVO.getSearch_cate() != null){
			switch(callLogVO.getSearch_cate()){
			case "dong_name":
				callLogVO.setDong_name(callLogVO.getSearch_text());
				break;
			case "ani":
				callLogVO.setAni(callLogVO.getSearch_text());
				break;
			case "dn":
				callLogVO.setDn(callLogVO.getSearch_text());
				break;
			case "called_no":
				callLogVO.setCalled_no(callLogVO.getSearch_text());
				break;
			}
		}

		//임시지움
//		List<CallLogVO> callLogList = supplyService.getCallLogList(callLogVO);
//		System.out.println(callLogList);
//		mav.addObject("callLogList", callLogList);
		
		mav.addObject("callLogVO", callLogVO);
		long endTime = System.currentTimeMillis();
		logger.warn("콜로그 view 전환까지 걸린 시간  = " +( endTime - startTime )/1000.0f +"초");
		map.put("time", ( endTime - startTime )/1000.0f +"초");
		mainDao.insertTimeMeasure(map);
		
		return mav;
	}
	
	@RequestMapping(value="/050/play.do", method=RequestMethod.GET)
	public void playBtn() throws Throwable{
	}
	
	
	@RequestMapping(value="/050/log.do", method=RequestMethod.POST)
	public ModelAndView log050Submit(@ModelAttribute("searchForm") CallLogVO callLogVO) throws Throwable{
		return log050View(callLogVO);
	}
	
	
	 /**
     * 콜로그 엑셀 다운로드
     */
    @RequestMapping(value = "/050/downloadLog", method = RequestMethod.POST)
    public ModelAndView downloadLogPost(@ModelAttribute("searchForm") CallLogVO callLogVO) {
    	UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    	
    	callLogVO.setUser_id(userVO.getUserid());
    	callLogVO.setView_mode(CallLogVO.VIEW_MODE_EXCEL);

    	//임시지움 -hkc
//        List<CallLogVO> callLogList = supplyService.getCallLogList(callLogVO);
        Map<String,Object> excel_model = new HashMap<String,Object>();
        
        excel_model.put("data_cate", "callLog");
//        excel_model.put("data_list",callLogList);
        
        return new ModelAndView(excelView, excel_model);
    }


	/**
	 * 컬러링 업로드 페이지
	 * @param model
	 * @param present_page
	 * @throws Throwable
	 */
	@RequestMapping(value="/coloring/upload.do", method=RequestMethod.GET)
	public void coloringUploadView(ModelMap model, @RequestParam(required=false) Integer present_page) 
			throws Throwable{
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		ColoringUploadVO uploadVO = new ColoringUploadVO();
		uploadVO.setUserid(userVO.getUserid());
		if(present_page!=null && present_page != 1){
			uploadVO.setPresent_page(present_page);
		}
		List<ColoringUploadVO> uploadList = supplyService.getUploadList(uploadVO);
		
		model.addAttribute("uploadVO", uploadVO);
		model.addAttribute("userVO", userVO);
		model.addAttribute("uploadList", uploadList);
	}
	/**
	 * 컬러링 업로드
	 * @param uploadForm
	 * @param request
	 * @return
	 * @throws Throwable
	 */
	@RequestMapping(value="/coloring/upload.do", method=RequestMethod.POST)
	public ModelAndView coloringUploadSubmit(@ModelAttribute ColoringUploadVO uploadForm, HttpServletRequest request) 
			throws Throwable{
		
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		uploadForm.setUserid(userVO.getUserid());
		uploadForm.setTargetid(userVO.getUserid());
		//폼 데이터 로직
		supplyService.submitColoringUpload(uploadForm);
		
		//파일 업로드 로직
		List<MultipartFile> files = uploadForm.getFiles();
		List<FileVO> fileVOList = fileService.processUpload(files, "COLORING", uploadForm.getId(), request);
		logger.debug("CustomLog:::" + fileVOList);
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:upload.do");
		return mav;
	}
	/**
	 * 컬러링 업로드 내역 삭제
	 * @param uploadVO
	 * @return
	 */
	@RequestMapping(value="/coloring/upload_delete.do", method=RequestMethod.POST)
	public @ResponseBody String coloringUploadDelete(@RequestBody ColoringUploadVO uploadVO){
		supplyService.coloringUploadDelete(uploadVO);
		//supplyService.rcvmentUploadDelete(uploadVO);
		return "OK";
	}
	
	/**
	 * 컬러링 다운로드 처리
	 * @param fileId
	 * @return
	 */
	@RequestMapping(value="/coloring/download.do")
	public ResponseEntity<org.springframework.core.io.Resource> coloringDownload(int fileId){
		
		FileVO fileVoInfo = fileService.getFileVoInfo(fileId);
		
		if(FileService.UPLOAD_CATE_COLORING.equals(fileVoInfo.getCategory())
				|| FileService.UPLOAD_CATE_COLORING.equals(fileVoInfo.getCategory())){
			org.springframework.core.io.Resource file= fileService.getFile(fileVoInfo);
			
			return ResponseEntity
					.ok()
					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + fileVoInfo.getName() + "\"")
					.body(file);
		} else {
			return new ResponseEntity<org.springframework.core.io.Resource>(HttpStatus.NOT_FOUND);
		}
	}
	
	/**
	 * 패치콜 도움말 팝업
	 * @param paramMap
	 * @param model
	 * @throws Throwable
	 */
	@RequestMapping("/pop/help.do")
	public void helpPopView(@RequestParam Map<String, Object> paramMap, ModelMap model) 
			throws Throwable{
		
	}
	
	@RequestMapping("/partnership.do")
	public ModelAndView partnershipView(@RequestParam Map<String, Object> paramMap, ModelMap model){
		ModelAndView mav = new ModelAndView();
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		mav.setViewName("redirect:partnership/"+userVO.getCompany_name()+".do");
		return mav;	
	}
	
//	@RequestMapping("/partnership/{companyName}.do")
//	public ModelAndView partnershipByCompanyView(@PathVariable("companyName") String companyName){
//		ModelAndView mav = new ModelAndView("service/partnership/"+companyName);
//		
//		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//		ColoringRegisterVO param = new ColoringRegisterVO();
//		param.setStatus("waiting,ongoing");
//		List<ColoringRegisterVO> registerList = supplyService.getColoringRegisterVOList(param);
//		
//		mav.addObject("userVO", userVO);
//		mav.addObject("registerList", registerList);
//		return mav;
//	}
	//@RequestMapping(value="/partnership/mobiem.do", method=RequestMethod.GET)
	/**
	 * 모비엠 사용자 이용 페이지(coloringRegisterSubmit)에서 등록한 리스트 출력
	 * @param registerVO
	 * @return
	 */
	@RequestMapping(value="/partnership/mobiem.do")
	public ModelAndView partnershipByCompanyView(@ModelAttribute("registerVO") ColoringRegisterVO registerVO){
		ModelAndView mav = new ModelAndView();
		
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		if(registerVO.getStatus()==null||registerVO.getStatus().equals(""))
			registerVO.setStatus("waiting,ongoing");
		
		List<ColoringRegisterVO> registerList = supplyService.getColoringRegisterVOList(registerVO);
		
		mav.addObject("userVO", userVO);
		mav.addObject("registerList", registerList);
		return mav;
	}
	/**
	 * 모비엠 컬러링 업로드
	 * @param uploadForm
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/partnership/mobiem_upload.do", method=RequestMethod.POST)
	public ModelAndView partnershipMobiemUpload(@ModelAttribute ColoringUploadVO uploadForm, HttpServletRequest request) throws Exception{
		
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		

		ColoringRegisterVO registerVO = new ColoringRegisterVO();
		registerVO.setId(uploadForm.getId());
		registerVO.setStatus("completed");
		//TODO 검사해야함...
//		try{
//			
//		}catch(Exception e){
//			logger.debug("악의적으로 아이디바꿔서 업로드 시도하고 있음 어디서");
//		}
		supplyService.updateColoringRegisterVO(registerVO);
		
		//upload쪽에 insert
		uploadForm.setUserid(userVO.getUserid());
		supplyService.submitColoringUpload(uploadForm);
		
		//파일 업로드 로직
		List<MultipartFile> files = uploadForm.getFiles();
		List<FileVO> fileVOList = fileService.processUpload(files, "COLORING2", uploadForm.getId(), request, uploadForm.getTargetid());
		
		logger.debug("CustomLog:::" + fileVOList);
		
		
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:mobiem.do");
		return mav;
	}
	/**
	 * 모비엠 컬러링 업데이트
	 * @param registerVO
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/partnership/mobiem_update.do", method=RequestMethod.POST)
	public int partnershipMobiemUpload(@ModelAttribute ColoringRegisterVO registerVO, HttpServletRequest request){
		supplyService.updateColoringRegisterVO(registerVO);
		return registerVO.getId();
	}
//	@RequestMapping(value="/partnership/mobiem.do", method=RequestMethod.POST)
//	public ModelAndView mobiemSearchView(@ModelAttribute("registerVO") ColoringRegisterVO registerVO){
//		ModelAndView mav = new ModelAndView();
//		List<ColoringRegisterVO> registerList = supplyService.getColoringRegisterVOList(registerVO);
//		return partnershipByCompanyView(registerVO,);
//	}
	
	@RequestMapping(value = "/patchcall_bi/dashboard.do")
	public ModelAndView dashboardView(){
		ModelAndView mv = new ModelAndView();
		return mv;
	}

	@RequestMapping(value="/index.do")
	public ModelAndView serviceIndex(){
		ModelAndView mav = new ModelAndView();

		return mav;
	}

	@RequestMapping(value="/main.do")
	public ModelAndView serviceMain(){
		ModelAndView mav = new ModelAndView();

		return mav;
	}

}
