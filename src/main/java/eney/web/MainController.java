package eney.web;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Principal;
import java.util.*;

import javax.activation.FileTypeMap;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sound.sampled.*;
import javax.swing.*;

import eney.domain.CustomUserCount;
import eney.domain.FileVO;
import eney.domain.UserVO;
import eney.service.*;
import eney.util.*;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpGet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;


import java.net.URI;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.util.EntityUtils;


@Controller
public class MainController {

	@Resource
	private SupplyService supplyService;
	@Resource
	private BoardService boardService;
	@Resource
	private UserService userService;
	@Resource
	private FileService fileService;

	@Resource
	private AdminService adminService;

	@Resource
	private FileDownloadView fileDownloadView;
	@Autowired
	private FileUploadUtil fileUploadUtil;

	@Resource
	private GABatch gaBatch;

	@Resource
	private BatchService batchService;


	private static final Logger logger = LoggerFactory.getLogger(MainController.class);





//	@RequestMapping("/*")
//	public String mainView(){
//		ModelAndView mav = new ModelAndView("index");
//
////		BoardContentVO boardNoticeContentVO = new BoardContentVO();
////		BoardConfigVO boardNoticeConfigVO = boardService.getBoardConfigByPath("notice");
////		System.out.println(boardNoticeConfigVO);
////		boardNoticeContentVO.setBoard_id(boardNoticeConfigVO.getBoard_id());
////
////		boardNoticeContentVO.setBoard_name(boardNoticeConfigVO.getBoard_eng_name());
////		List<BoardContentVO> noticeList = boardService.getBoardContentListById(boardNoticeContentVO,boardNoticeConfigVO.getBoard_id());
////
//////		/* 번호조회 관련 부분 시작 */
////		CommonVO commonVO = new CommonVO();
////		commonVO.setCategory("050_NUMBER_ASSIGNED");
////		Map<String, List<CommonVO>> numberOption = supplyService.get050_1th2ndNumList(commonVO);
////
////		mav.addObject("numberOption", numberOption);
////		mav.addObject("boardNoticeContentVO", boardNoticeContentVO);
////
////		mav.addObject("noticeList", noticeList);
//		System.out.println("인덱스");
//
//		return "/build/index.html";
//	}

	/**
	 * 제휴문의 신청 팝업 뷰
	 */
	@RequestMapping("/pop/partnership.do")
	public void partnershipPopupView(@RequestParam Map<String, Object> paramMap, ModelMap model ,Principal principal)
			throws Throwable{

	}

	@ResponseBody
	@RequestMapping("/testga.do")
	public void tesdddt() throws  Exception{
		batchService.run();
	}
	/**
	 * 로그인 페이지 연결
	@RequestMapping("/login")
	public String login(@RequestParam(required=false) String action) throws Throwable{
		return "login";
	}

	@RequestMapping("/loginFail")
	@ResponseBody
	public ResponseEntity<?> loginFail() {
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}

	//로그인 성공
	@ResponseBody
	@RequestMapping("/succeslogin")
	public ResponseEntity<?> succesLogin(Principal principal) {

		try{

			String userName = principal.getName();

			System.out.println("로그인 성공"+userName);

			return new ResponseEntity<>(userName, HttpStatus.OK);
		}catch(Exception ex){
			String errorMessage;
			errorMessage = ex + " <== error";
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}
	}
	**/

	//로그인 여부
	@ResponseBody
	@RequestMapping(value="/auth/check", method= RequestMethod.GET)
	public ResponseEntity<?> loginCheck(Principal principal) {


		try{

			String userName = principal.getName();

			System.out.println("로그인 성공 : "+userName);

			return new ResponseEntity<>(userName, HttpStatus.OK);
		}catch(Exception ex){
			String errorMessage;
			errorMessage = ex + " <== error";
			return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
		}

	}

	/**
	 * 회사소개 페이지 뷰
	 */
	@RequestMapping("introduce.do")
	public void introView(){}
	/**
	 * 이용약관 페이지 뷰
	 */
	@RequestMapping("/rule/agreement")
	public void ruleAgreementView(){}
	/**
	 * 개인정보취급방침 뷰
	 */
	@RequestMapping("/rule/privacy")
	public void rulePrivacyView(){}

	@RequestMapping("/rule/idcAgreement")
	public void ruleIDCAgreementView(){}

	@RequestMapping("/rule/chargeAgreement")
	public void ruleChargeAgreementView(){}

	@RequestMapping("/rule/etc1")
	public void ruleETCAgreementView(){}

	@RequestMapping("/rule/accretion")
	public void ruleAccretionAgreementView(){}


	@RequestMapping("/test")
	public void test(){}

	/**
	 * 파일 다운로드
	 * @param fileName 파일이름
	 * @param fileNo 파일번호
	 * @param request
	 * @return
	 */
	@RequestMapping("/file/download.do")
	public ModelAndView download( @RequestParam(value="fileName", required=false)String fileName,
								  @RequestParam(value="fileNo", required=false)Integer fileNo, HttpServletRequest request){

		ModelAndView mvc = new ModelAndView(fileDownloadView);
		if(fileNo != null){
			FileVO fileVO = fileService.getFileVoInfo(fileNo);
			fileName = fileVO.getUpload_name();
			mvc.addObject("downloadFileVoInfo", fileVO);
		}

		fileName = fileName.replaceAll(" ", "+");
		String path = fileUploadUtil.getFileUploadPath() + "/" + fileName;

		File file = new File(path);
		mvc.addObject("downloadFile", file);

		return mvc;
	}

	/**
	 * 파일 다운로드
	 * @param fileName 다운받을 파일명
	 * @param category 다운받을 파일의 카테고리(디렉토리 명)
	 * @param response
	 * @return
	 */
	@RequestMapping(path={"/upload/{fileName:.+}/{realName}", "/upload/{category}/{fileName:.+}/{realName}"} ,produces = "application/json; charset=utf8")
	public ResponseEntity<org.springframework.core.io.Resource> viewdFile(@PathVariable("fileName") String fileName,@PathVariable("realName") String realName,
																		  @PathVariable(value="category", required=false) String category,
																		  HttpServletResponse response) throws IOException {


		Path path = fileUploadUtil.getFileUploadPath();

		if(category != null){
			path = path.resolve(category);
		}

		File file = new File(path.toString()+"/"+fileName);

		System.out.println();
		System.out.println(file);
		System.out.println(realName);
		System.out.println(java.net.URLEncoder.encode(realName, "utf-8"));

		HttpHeaders header = new HttpHeaders();

		header.add("Content-Disposition", "attachment; fileName=\""
				+ java.net.URLEncoder.encode(realName, "utf-8") + "\";");
		header.add("Cache-Control", "no-cache, no-store, must-revalidate");
		header.add("Pragma", "no-cache");
		header.add("Expires", "0");

		Path filePath = Paths.get(file.getAbsolutePath());
		System.out.println("파일 패스");
		System.out.println(path.toString());
		System.out.println(file.getName());
		System.out.println(filePath);
		System.out.println("파일 패스");
		ByteArrayResource resource = new ByteArrayResource(Files.readAllBytes(filePath));
		return ResponseEntity.ok()
				.headers(header)
				.contentLength(file.length())
				.contentType(MediaType.parseMediaType("application/octet-stream"))
				.body(resource);



//		if(category != null){
//			path = path.resolve(category);
//		}
//
//		org.springframework.core.io.Resource file;
//		try {
//			file = new UrlResource(path.resolve(fileName+".png").toUri());
//
//			System.out.println("$$$$$$$$$$$$$");
//			System.out.println(file);
//			System.out.println("$$$$$$$$$$$$$");
//
//		} catch (MalformedURLException e) {
//			return new ResponseEntity<org.springframework.core.io.Resource>(HttpStatus.NOT_FOUND);
//		}
//
//		if(category.equals("record")){
//
//			return ResponseEntity
//					.ok()
//					.header(HttpHeaders.CONTENT_TYPE, "audio/mpeg")
//					.header(HttpHeaders.CONTENT_LENGTH, String.valueOf(file.contentLength()))
//					//.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
//					.body(file);
//
//		}else{
//			return ResponseEntity
//					.ok()
//					.contentType(MediaType.parseMediaType("application/octet-stream"))
//					.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
//					.body(file);
//		}
//
//


	}




	/**
	 * 이미지 업로드 컨트롤러 
	 * @param request
	 * @param files
	 * @throws Exception
	 */
	@RequestMapping(value="/file/imageUpload", method=RequestMethod.POST, produces="text/html;charset=utf-8")
	public ResponseEntity<?> communityImageUpload(HttpServletRequest request, @RequestParam ArrayList<MultipartFile> files) throws Exception {
		List<FileVO> fileInfoList = null;
//		List<MultipartFile> fileList = new LinkedList<>();
//		fileList.add(upload);

		try{
			fileInfoList = fileService.processUpload(files, "CONTENT_IMG", 0, request);
		} catch (Exception e) {
			return new ResponseEntity<>("이미지 업로드 중 오류가 발생했습니다.", HttpStatus.BAD_REQUEST);
		}

		if(fileInfoList == null || fileInfoList.size() == 0){
			return new ResponseEntity<>("이미지 파일이 부적합합니다.", HttpStatus.BAD_REQUEST);
		} else {
			FileVO fileInfo = fileInfoList.get(0);
			String fileUrl = fileInfo.getUpload_name() +"." +fileInfo.getExtenstion();//저장경로

			return new ResponseEntity<>(fileUrl, HttpStatus.OK);
		}

	}

	/**
	 * 이미지 Viewer
	 * @throws Exception
	 */
//	@RequestMapping(value="/file/image/{filePath}", method=RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
//	public ResponseEntity<byte[]> ImageLoad(HttpServletRequest request, HttpServletResponse response, @PathVariable("filePath") String filePath) throws Exception {
//
//
//		System.out.println("스트림");
////		String path = request.getParameter("fileUrl") + request.getParameter("fileName");
//		System.out.println(fileUploadUtil.getFileUploadPath().resolve("image").toString());
//		System.out.println(fileUploadUtil.getFileUploadPath().resolve("image").toString()+"/" + filePath);
////		InputStream in = servletContext.getResourceAsStream(fileUploadUtil.getFileUploadPath().resolve("image").toString()+"/" + filePath);
//
//		File img = new File(fileUploadUtil.getFileUploadPath().resolve("image").toString()+"/" + filePath);
//		return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img))).body(Files.readAllBytes(img.toPath()));
//	}

	@RequestMapping(value="/file/{category}/{fileName}", method=RequestMethod.GET, produces = MediaType.IMAGE_JPEG_VALUE)
	public ResponseEntity<byte[]> ImageLoad(@PathVariable("category") String category, @PathVariable("fileName") String fileName) throws Exception {
		return fileService.imageLoad(category, fileName);
	}

	@RequestMapping("/loginfail.do")
	public ModelAndView loginFailView(){
		ModelAndView mv = new ModelAndView();
		return mv;
	}


	@ResponseBody
	@RequestMapping("/service")
	public ModelAndView serviceView(HttpServletRequest request){
		ModelAndView mv = new ModelAndView();

		UserVO session = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

		if(isMobile(request) == true){
			mv.setViewName("redirect:/main.do");
		}else if(userService.getServiceListAll(session) == null || userService.getServiceListAll(session).size()==0){
			mv.setViewName("redirect:/main.do");
		}else{
			mv.setViewName("redirect:/build/index.html");

		}

		return mv;
	}

	@RequestMapping(value = {"/robots", "/robot", "/robot.txt", "/robots.txt", "/null"}, method = RequestMethod.GET)
	@ResponseBody
	public void robot(HttpServletResponse  response,HttpServletRequest request) {

		InputStream resourceAsStream = null;
		try {

			ClassLoader classLoader = getClass().getClassLoader();
			resourceAsStream = classLoader.getResourceAsStream("robots.txt");
//            resourceAsStream = new FileInputStream(IOUtils.toString(getClass().getResourceAsStream("/robots.txt"),"UTF-8"));

			response.addHeader("Content-disposition", "filename=robot.txt");
			response.setContentType("text/plain");
			IOUtils.copy(resourceAsStream, response.getOutputStream());
			response.flushBuffer();
		} catch (Exception e) {
			logger.error("Problem with displaying robot.txt", e);
		}
	}

	@RequestMapping(value = "/sitemap.xml", method = RequestMethod.GET)
	@ResponseBody
	public XmlUrlSet main(){
		XmlUrlSet xmlUrlSet = new XmlUrlSet();
		create(xmlUrlSet, "", XmlUrl.Priority.HIGH);
		create(xmlUrlSet,"/link-1",XmlUrl.Priority.HIGH);
		create(xmlUrlSet,"/link-2",XmlUrl.Priority.MEDIUM);

		return xmlUrlSet;
	}

	public void create(XmlUrlSet xmlUrlSet, String link, XmlUrl.Priority priority){
		xmlUrlSet.addUrl(new XmlUrl("https://www.eney.co.kr"+link, priority));
	}

	private boolean isMobile(HttpServletRequest request){
		String userAgent = request.getHeader("user-agent");
		boolean mobile1 = userAgent.matches(".*(iPhone|iPod|Android|Windows CE|BlackBerry|Symbian|Windows Phone|webOS|Opera Mini|Opera Mobi|POLARIS|IEMobile|lgtelecom|nokia|SonyEricsson).*");
		boolean mobile2 = userAgent.matches(".*(LG|SAMSUNG|Samsung).*");

		if(mobile1 ||  mobile2){
			return true;
		}
		return false;
	}

	@RequestMapping(value="/customUserCount", method=RequestMethod.GET)
	public ResponseEntity<?> getCustomUserCount(){
		int totalCount = adminService.getCallCountAll();
		CustomUserCount customCount = adminService.getCustomUserCount();

		Map<String, Object> map = new HashMap<>();

		map.put("total", totalCount);
		map.put("customCount", customCount);

		return new ResponseEntity<>(map,HttpStatus.OK);

	}

}
