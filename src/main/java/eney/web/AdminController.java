package eney.web;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;


import eney.domain.payload.PasswordRequest;
import eney.exception.AccessDeniedException;
import eney.service.*;
import eney.util.DateUtil;
import eney.util.StringUtil;
import eney.domain.*;
import eney.util.ExcelView;
import org.apache.commons.io.FilenameUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private static final Logger logger = LoggerFactory.getLogger(AdminController.class);

    @Resource
    private AdminService adminService;

    @Resource
    private HttpServletRequest request;

    @Resource
    private SupplyService supplyService;

    @Resource
    private FileService fileService;

    @Resource
    private MailService mailService;

    @Resource
    private PaymentService paymentService;

    @Resource
    private IvrService switchService;

    @Resource
    private ExcelView excelView;

    @Resource
    private UserService userService;

    @Resource
    private AcsService acsService;

    @Resource
    private MessageService messageService;

    @Resource
    private ServiceApplyService serviceApplyService;


    /**
     * 대쉬보드
     * 패치콜 사용개수, 매핑개수, 미사용개수 , 최근 10간의 콜 수, 최근 일주일간 환불내역, API 월간평균 속도 표시
     */
    @RequestMapping("/dashboard")
    public ModelAndView dashboardView() {

        long startTime = System.currentTimeMillis();
        //int mappedAmount = adminService.getMappedPatchcallAmount();
        int refundCount = adminService.getRefundCount();
        //List<Map<String, Object>> callCountSummary = adminService.getCallCountSummary();
        List<Map<String, Object>> callCountSummary = adminService.getTotalCallCount();
//		List<Map<String,String>> rssMap = rssParserUtil.rssParse();


        Map<String, Integer> teleExchangeSKB = adminService.getEnablePatchCallSKB();
        Map<String, Integer> teleExchangeSeJong = adminService.getEnablePatchCallSeJong();

        List<Map<String, Object>> apiMesurement = adminService.getApiMesurement();

        //Map<String, Long> checkMap = switchService.getLast50CdrSummary();


        ModelAndView mav = new ModelAndView();
//		mav.addObject("rssMap", rssMap);
        mav.addObject("callCountSummary", callCountSummary);
        //mav.addObject("checkmap",checkMap);
        mav.addObject("skb", teleExchangeSKB);
        mav.addObject("sejong", teleExchangeSeJong);
        mav.addObject("refundCount", refundCount);
        mav.addObject("apiMesurement", apiMesurement);
        long endTime = System.currentTimeMillis();
        logger.warn("admin dashboard view 전환까지 걸린 시간  = " + (endTime - startTime) / 1000.0f + "초");

        return mav;
    }


    /**
     * 최근 일주일간 환불 내역 리스트
     */
    @RequestMapping("/refundList")
    public ModelAndView refundListView(@RequestParam(required = false) Integer present_page) {

        List<EpointRefundVO> refundList = adminService.getRefundList();
        EpointRefundVO refundVO = new EpointRefundVO();

        ModelAndView mav = new ModelAndView();

        if (present_page != null && present_page != 1) {
            refundVO.setPresent_page(present_page);
        }
        mav.addObject("refundList", refundList);
        mav.addObject("refundVO", refundVO);
        return mav;
    }

    /**
     * refund_id에 해당하는 환불신청 정보
     *
     * @param idx
     * @return
     */
    @RequestMapping("/ajax/getRefundInfo")
    public @ResponseBody
    EpointRefundVO getRefundInfo(@RequestParam Integer idx) {
        EpointRefundVO paramVO = new EpointRefundVO();
        paramVO.setRefund_id(idx);
        EpointRefundVO refundVO = adminService.getRefundInfo(paramVO);
        return refundVO;
    }

    /**
     * 환불내역 update
     *
     * @param
     * @return
     */
    @RequestMapping("/updateRefundInfo")
    public ModelAndView updateRefundInfo(HttpServletRequest request) {
        EpointRefundVO paramVO = new EpointRefundVO();
        paramVO.setRefund_id(Integer.parseInt(request.getParameter("refund_id")));

        EpointRefundVO refundVO = adminService.getRefundInfo(paramVO);
        refundVO.setStatus("completed");

        adminService.updateRefundInfo(refundVO);

        String text ="[에네이] " + refundVO.getAccount_holder()
                + "님 " + refundVO.getWhich_bank() + "은행계좌로 "
                + refundVO.getAmount_to_refund() + "원이 환불 완료되었습니다.";

        MessageVO messageVO = new MessageVO();
        messageVO.setCallback(MessageService.ADDR_ENEY_OFFICE);
        messageVO.setDstaddr(refundVO.getPhone_number());
        messageVO.setText(text);

        messageService.sendMsg(messageVO);

        return (ModelAndView) new ModelAndView("redirect:/admin/refundList");


    }
	/*@RequestMapping(value="/updateUserInfo", method=RequestMethod.POST)
	public ModelAndView updateUserInfo(@ModelAttribute UserVO updateVO) throws Throwable{
		adminService.updateUserInfo(updateVO);
		return usersView(null);
	}*/

    /**
     * 교환기 상태 조회
     *
     * @return 최근 50개의 통화 통계
     */
    @RequestMapping("/ajax/getSwitchSummary")
    public @ResponseBody
    Map<String, Long> getSwitchSummary() {

        return switchService.getLast50CdrSummary();
    }


    @RequestMapping(value = "/ajax/send_msg.do", method = RequestMethod.POST)
    public @ResponseBody
    boolean sendMsg(MultipartHttpServletRequest map) throws Exception {

        boolean result = false;
        String calling_number = map.getParameter("calling_number");

        if (StringUtil.numberCheck(calling_number) == true) {
            String[] called_arr = String.valueOf(map.getParameter("called_number")).split(",");

            for (String called_number : called_arr) {
                if (StringUtil.numberCheck(called_number) == true) {

                    MessageVO messageVO = new MessageVO();

                    messageVO.setCallback(calling_number);
                    messageVO.setDstaddr(called_number);
                    messageVO.setText(map.getParameter("msg_txt"));

                    if (map.getFile("mms_file").getSize() == 0) {

                        messageService.sendMsg(messageVO);
                        result = true;
                    } else {

                        List<MultipartFile> list = new ArrayList<>();
                        list.add(map.getFile("mms_file"));

                        String mmsLoc = "";

                        try {
                            List<FileVO> fileVOList = fileService.processUpload(list, "MMS", 0, request);

                            for (FileVO file : fileVOList) {
                                mmsLoc = "/usr/local/upload/mms/" + file.getUpload_name() + "." + file.getExtenstion();
                                messageVO.setFileloc1(mmsLoc);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        messageService.sendMsg(messageVO);

                        result = true;
                    }

                } else {
                    result = false;
                }
            }

        } else {
            result = false;
        }

        return result;
    }


    /**
     * 컬러링 샘플 분류, 번호 assign, 교환기 상태정보 문자 전송 등 카테고리 내역
     *
     * @param selectQuery
     * @return 분류에 따른 카테고리내역
     */
    @RequestMapping(value = "/ajax/commonCode", method = RequestMethod.GET)
    public @ResponseBody
    List<CommonVO> getCommonCodeList(CommonVO selectQuery) {
        return adminService.getCommonCodeList(selectQuery);
    }

    /**
     * 컬러링 샘플 분류, 번호 assign, 교환기 상태정보 문자 전송 등 카테고리 수정
     *
     * @param
     * @param
     */
    @RequestMapping(value = "/ajax/commonCode/{common_id}", method = RequestMethod.PATCH)
    public @ResponseBody
    Boolean updateCommonCode(@PathVariable("common_id") Integer common_id, CommonVO updateData) {
        updateData.setCommon_id(common_id);
        return adminService.updateCommonCode(updateData);
    }

    /**
     * users 페이지
     *
     * @param userVO
     * @return userList
     */
    @ResponseBody
    @RequestMapping(value = "/users")
    public ResponseEntity<?> usersView(@RequestParam(value = "page", defaultValue = "1") Integer page, @RequestParam(value = "search", required = false) String search, @RequestParam(value = "search_filed", required = false) String searchFiled)throws Exception {
        System.out.println("받음");

        UserVO userVO = new UserVO();
        userVO.setPageNo(page);

        if (search != null && searchFiled != null) {
            userVO.setSearchFiled(searchFiled);
            userVO.setSearchValue(search);
        }

        logger.info(">>>> page home start!!");

        //검색조건, 검색어
        logger.info("SearchFiled : " + userVO.getSearchFiled());
        logger.info("SearchValue : " + userVO.getSearchValue());

        //--페이징 처리
//        int totalCount = adminService.getEmpListCount(userVO); //게시물 총갯수를 구한다
//        userVO.setTotalCount(totalCount); //페이징 처리를 위한 setter 호출
//        model.addAttribute("pageVO", empVO);
        logger.info("PageSize // 한 페이지에 보여줄 게시글 수 : " + userVO.getPageSize());
        logger.info("PageNo // 페이지 번호 : " + userVO.getPageNo());
        logger.info("StartRowNo //조회 시작 row 번호 : " + userVO.getStartRowNo());
        logger.info("EndRowNo //조회 마지막 now 번호 : " + userVO.getEndRowNo());
        logger.info("FirstPageNo // 첫 번째 페이지 번호 : " + userVO.getFirstPageNo());
        logger.info("FinalPageNo // 마지막 페이지 번호 : " + userVO.getFinalPageNo());
        logger.info("PrevPageNo // 이전 페이지 번호 : " + userVO.getPrevPageNo());
        logger.info("NextPageNo // 다음 페이지 번호 : " + userVO.getNextPageNo());
        logger.info("StartPageNo // 시작 페이지 (페이징 네비 기준) : " + userVO.getStartPageNo());
        logger.info("EndPageNo // 끝 페이지 (페이징 네비 기준) : " + userVO.getEndPageNo());
//        logger.info("totalCount // 게시 글 전체 수 : " + totalCount);
        System.out.println("중간");


        try{
            Map<String, Object> map = adminService.getUserList(userVO);
            return new ResponseEntity<>(map,HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @ResponseBody
    @RequestMapping(value="/user/info", method= RequestMethod.GET)
    public ResponseEntity<?> userInfo(@RequestParam(value="idx") int idx) throws NumberFormatException{
        Map<String, Object> map = new HashMap<>();

        UserVO userVO = new UserVO();
        userVO.setIdx(idx);

        map.put("license",fileService.selectFileList("LICENSE", idx));

        map.put("userInfo", adminService.getUserInfo(userVO));

        try{
            map.put("license",fileService.selectFileList("LICENSE", idx));

            map.put("userInfo", adminService.getUserInfo(userVO));
            return ResponseEntity.ok(map);
        }catch(NumberFormatException e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value="/user/info", method= RequestMethod.PUT)
    public ResponseEntity<?> userInfo(@RequestBody UserVO userVO) throws Exception{
        UserVO tmpUser = adminService.getUserInfo(userVO);
        userVO.setUserid(tmpUser.getUserid());

        try{
            adminService.updateUserInfoTB(userVO);
            return new ResponseEntity<>(HttpStatus.OK);
        }catch(Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }


    /**
     * 웹 호스팅 서비스 리스트(Admin)
     *
     * @param serviceWebHostingVO
     * @return 전체 리스트
     */
    @RequestMapping(value = "/serviceList/webhosting")
    public ModelAndView serviceListView(@ModelAttribute ServiceWebHostingVO serviceWebHostingVO
            , @RequestParam(required = false) Integer present_page) {
        ModelAndView mav = new ModelAndView();
        List<Map<String, String>> webHostingList = adminService.getWebHostingList(serviceWebHostingVO);
        ServiceWebHostingVO webVO = new ServiceWebHostingVO();

        if (present_page != null && present_page != 1) {
            webVO.setPresent_page(present_page);
        }
        mav.addObject("webVO", webVO);
        mav.addObject("webHostingList", webHostingList);
        return mav;
    }

    @RequestMapping(value = "/serviceList/endPatchcall")
    public ModelAndView endPatchcallView(@ModelAttribute AgentVO agentVO) {
        ModelAndView mav = new ModelAndView();
        List<AgentVO> endAgentList = adminService.getEndAgentList(agentVO);

        mav.addObject("agentVO", agentVO);
        mav.addObject("endAgentList", endAgentList);
        return mav;
    }

    @ResponseBody
    @RequestMapping(value = "/service/delete_patchcall.do")
    public ModelAndView endPatchcall(String vno) {
        ModelAndView mav = new ModelAndView();
        AgentVO agentVO = new AgentVO();
        agentVO.setVno(vno);
        supplyService.close050Admin(agentVO);

        mav.setViewName("redirect:/admin/serviceList/endPatchcall");

        return mav;
    }

    /**
     * 패치콜 서비스 리스트(Admin)
     *
     * @param servicePatchcallVO
     * @return 전체 리스트
     */
    @RequestMapping(value = "/serviceList/patchcall")
    public ModelAndView patchcallListView(@ModelAttribute ServicePatchcallVO servicePatchcallVO,
                                          @RequestParam(required = false) Integer present_page) {
        ModelAndView mav = new ModelAndView();
        List<ServicePatchcallVO> patchcallList = adminService.getPatchcallList(servicePatchcallVO);
        ServicePatchcallVO patchcall = new ServicePatchcallVO();

//        if (present_page != null && present_page != 1) {
//            patchcall.setPresent_page(present_page);
//        }

        mav.addObject("patchcallList", patchcallList);
        mav.addObject("patchcall", patchcall);

        return mav;
    }

    /**
     * 080번호, 전국대표번호 서비스 리스트(Admin)
     *
     * @param servicePatchcallOtherVO
     * @return 전체 리스트
     */
    @RequestMapping(value = "/serviceList/other")
    public ModelAndView patchcallOtherListView(@ModelAttribute ServicePatchcallOtherVO servicePatchcallOtherVO,
                                               @RequestParam(required = false) Integer present_page) {
        ModelAndView mav = new ModelAndView();
        List<ServicePatchcallOtherVO> patchcallList = adminService.getPatchcallOtherList(servicePatchcallOtherVO);
        ServicePatchcallOtherVO patchcallOther = new ServicePatchcallOtherVO();
        if (present_page != null && present_page != 1) {
            patchcallOther.setPresent_page(present_page);
        }

        mav.addObject("patchcallList", patchcallList);
        mav.addObject("patchcallOther", patchcallOther);
        return mav;
    }

    /**
     * 콜백 메시징 사용 시 사용 가능한 발신번호 리스트
     *
     * @param acsTransmitVO
     * @return 발신번호 리스트
     */
//    @RequestMapping(value = "/serviceList/messaging")
//    public ModelAndView messagingListView(@ModelAttribute AcsTransmitVO acsTransmitVO,
//                                          @RequestParam(required = false) Integer present_page) {
//        ModelAndView mav = new ModelAndView();
//        List<AcsTransmitVO> messagingList = acsService.getMessaingList(acsTransmitVO);
//        AcsTransmitVO acsVO = new AcsTransmitVO();
//        if (present_page != null && present_page != 1) {
//            acsVO.setPresent_page(present_page);
//        }
//
//        mav.addObject("messagingList", messagingList);
//        mav.addObject("acsVO", acsVO);
//        return mav;
//    }

    @RequestMapping(value = "/serviceList/record")
    public ModelAndView recordView(@ModelAttribute RecordVO RecordVO,
                                   @RequestParam(required = false) Integer present_page) {
        ModelAndView mav = new ModelAndView();
        List<RecordVO> recordList = adminService.getRecordList(RecordVO);
        RecordVO record = new RecordVO();

        if (present_page != null && present_page != 1) {
            record.setPresent_page(present_page);
        }
        mav.addObject("recordList", recordList);
        mav.addObject("record", record);
        return mav;
    }

    @RequestMapping(value = "/serviceList/callback")
    public ModelAndView callbackView(@ModelAttribute CallbackSmsVO callbackVO,
                                     @RequestParam(required = false) Integer present_page) {


        ModelAndView mav = new ModelAndView();

        List<CallbackSmsVO> callbackList = adminService.getCallbackList(callbackVO);
        CallbackSmsVO callback = new CallbackSmsVO();

        if (present_page != null && present_page != 1) {
            callback.setPresent_page(present_page);
        }
        mav.addObject("callbackList", callbackList);
        mav.addObject("callback", callback);
        return mav;
    }

    @RequestMapping(value = "/serviceList/bi")
    public ModelAndView patchcallListView(@ModelAttribute ServiceBIVO serviceBiVO,
                                          @RequestParam(required = false) Integer present_page) {
        ModelAndView mav = new ModelAndView();
        List<ServiceBIVO> list = adminService.getBiList(serviceBiVO);

        ServiceBIVO bi = new ServiceBIVO();

        if (present_page != null && present_page != 1) {
            bi.setPresent_page(present_page);
        }

        ServicePatchcallVO patchcall = new ServicePatchcallVO();

        if (present_page != null && present_page != 1) {
            patchcall.setPresent_page(present_page);
        }

        mav.addObject("list", list);
        mav.addObject("bi", bi);

        return mav;
    }

    /**
     * 웹호스팅 서비스 생성 여부 확인
     *
     * @param web_hosting_idx
     * @return 완료 버튼 클릭 시 generate_yn = 'Y'
     */
    @ResponseBody
    @RequestMapping(value = "/webGenerate_yn.do")
    public ModelAndView webGenerateYn(@RequestParam Integer web_hosting_idx, @RequestParam String service_type) {
        ModelAndView mav = new ModelAndView();
        adminService.updateWebHostingGenerate(web_hosting_idx);

        ServiceWebHostingVO serviceInfoVO = new ServiceWebHostingVO();
        serviceInfoVO.setWeb_hosting_idx(web_hosting_idx);
        ServiceWebHostingVO applicantsVO = adminService.getWebApplicantList(serviceInfoVO);

        if (applicantsVO.getPublish_email() != null) {
            adminService.sendEmail(applicantsVO);
        }

        mav.setViewName("redirect:/admin/serviceList/webhosting");
        return mav;
    }

    /**
     * 패치콜 서비스 생성 여부 확인
     *
     * @param
     * @return 완료 버튼 클릭 시 generate_yn = 'Y'
     */
    @ResponseBody
    @RequestMapping(value = "/patchcallGenerate_yn.do")
    public ModelAndView patchcallGenerateYn(@RequestParam Integer patchcall_idx, @RequestParam String service_type) {
        ModelAndView mav = new ModelAndView();
        adminService.updatePatchcallGenerate(patchcall_idx);

        ServicePatchcallVO serviceInfoVO = new ServicePatchcallVO();
        serviceInfoVO.setPatchcall_idx(patchcall_idx);
        ServicePatchcallVO applicantsVO = adminService.getPatchcallApplicantList(serviceInfoVO);


        if (applicantsVO.getPublish_email() != null) {
            adminService.sendEmail(applicantsVO);
        }

        mav.setViewName("redirect:/admin/serviceList/patchcall");
        return mav;
    }

    /**
     * 청구서 입금내역 완료처리
     *
     * @param
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updateBillingStatus")
    public ModelAndView updateBillingStatus(@RequestParam Integer invoice_idx, @RequestParam String userid) {
        ModelAndView mav = new ModelAndView();

        adminService.updateBillingStatus(invoice_idx);


        mav.setViewName("redirect:/admin/billingList?userid=" + userid);
        return mav;
    }

    /**
     * 080번호, 전국대표번호, 콜백SMS, 녹취, Short URL 서비스 생성 여부 확인
     *
     * @param
     * @return 완료 버튼 클릭 시 generate_yn = 'Y'
     */
    @ResponseBody
    @RequestMapping(value = "/patchcallOtherGenerate_yn.do")
    public ModelAndView patchcallOtherGenerateYn(@RequestParam Integer idx, @RequestParam String service_type) {
        ModelAndView mav = new ModelAndView();

        adminService.updatePatchcallOtherGenerate(idx);


        mav.setViewName("redirect:/admin/serviceList/other");
        ServicePatchcallOtherVO serviceInfoVO = new ServicePatchcallOtherVO();
        serviceInfoVO.setIdx(idx);
        serviceInfoVO.setUserid(request.getParameter("userid"));
        serviceInfoVO.setService_type(service_type);

        ServicePatchcallOtherVO applicantsVO = adminService.getOtherApplicantsList(serviceInfoVO);

        if (applicantsVO.getPublish_email() != null) {
            adminService.sendEmail(applicantsVO);
        }


        mav.setViewName("redirect:/admin/serviceList/other");
        return mav;
    }

    /**
     * 홈텍스 정보 등록 시 유저 정보 가져오기
     *
     * @param
     * @return 유저 정보
     */
    @RequestMapping("/ajax/hometaxModalInfo")
    public @ResponseBody
    UserVO hometaxModalInfo(@RequestParam Integer idx) {
        UserVO paramVO = new UserVO();
        paramVO.setIdx(idx);
        UserVO userInfo = adminService.getUserInfo(paramVO);
        return userInfo;
    }

    /**
     * 회원 상세 정보
     *
     * @param idx
     * @return 회원 상세정보 출력
     */
    @RequestMapping("/ajax/getUserInfo")
    public @ResponseBody
    UserVO getUserInfo(@RequestParam Integer idx) {
        UserVO paramVO = new UserVO();
        paramVO.setIdx(idx);
        UserVO userInfo = adminService.getUserInfo(paramVO);
        return userInfo;
    }

    /**
     * 050 가맹점 번호 리스트 출력
     *
     * @param agentVO
     */
    @RequestMapping("/ajax/getUserServiceDetail")
    public @ResponseBody
    List<AgentVO> getUserServiceDetail(@RequestBody AgentVO agentVO) {
        return supplyService.getAgentVOList(agentVO);
    }

    /**
     * 회원별 서비스내역  출력
     *
     * @param
     * @return 회원별 결제내역 로그
     */
    @RequestMapping("/ajax/getUserServiceList")
    public @ResponseBody
    List<ServiceListVO> getUserServiceList(@RequestBody ServiceListVO serviceListVO) {

        return supplyService.getUserServiceList(serviceListVO);
    }

    /**
     * epoint 사용내역 로그 출력
     *
     * @param paymentVO
     * @return 회원별 epoint 로그
     */
//    @RequestMapping("/ajax/getUserEpointDetail")
//    public @ResponseBody
//    List<PaymentVO> getUserEpointDetail(@RequestBody PaymentVO paymentVO) {
//        paymentVO.setPresent_page(-1);
//        return paymentService.getEpointLogs(paymentVO);
//    }

    /**
     * 회원정보 수정
     *
     * @param updateVO (유형, 이름, 휴대폰번호, 이메일)
     * @throws Throwable
     */
//    @RequestMapping(value = "/updateUserInfo", method = RequestMethod.POST)
//    public ModelAndView updateUserInfo(@ModelAttribute UserVO updateVO) throws Throwable {
//        adminService.updateUserInfo(updateVO);
//        return usersView(null);
//    }

    /**
     * 회원목록 다운로드 - 엑셀
     *
     * @param userVO
     */
//    @RequestMapping(value = "/downloadUser", method = RequestMethod.POST)
//    public ModelAndView downloadUserListExcel(@ModelAttribute UserVO userVO) {
//        List<UserVO> userList = adminService.getUserList(userVO);
//        Map<String, Object> excel_model = new HashMap<String, Object>();
//        excel_model.put("data_cate", new String("user"));
//        excel_model.put("data_list", userList);
//        return new ModelAndView(excelView, excel_model);
//    }

    /**
     * 사용자에게 보낸 메일 조회
     *
     * @param selectQuery 조회 범위
     * @return
     */
    @RequestMapping("/mail/list")
    public ModelAndView mailMangerList(@ModelAttribute("selectQuery") MailManagerVo selectQuery) {
        ModelAndView mav = new ModelAndView();

        List<MailManagerVo> mailManagerInfoList = mailService.selectMailManagerInfoList(selectQuery);
        mav.addObject("mailManagerInfoList", mailManagerInfoList);

        return mav;
    }

    /**
     * 해당 항목 메일 전송
     *
     * @param mail_idx 메일 IDX
     * @return 성공 여부
     */
    @RequestMapping("/mail/ajax/sendMail")
    public @ResponseBody
    Boolean sendMailByMangerInfo(Integer mail_idx) {
        UserVO userVO = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return mailService.sendMailByMangerInfo(mail_idx, userVO);
    }

    /**
     * 사용자에게 보낼 메일 작성 (GET)
     *
     * @param mailManagerInfo
     * @param mav
     * @return
     */
    @RequestMapping(value = "/mail/write", method = RequestMethod.GET)
    public ModelAndView mailManagerWriteFrom(@ModelAttribute("mailManagerInfo") MailManagerVo mailManagerInfo, ModelAndView mav) {

        if (mailManagerInfo.getMail_type() == null) {
            MailManagerVo selectQuery = new MailManagerVo();
            selectQuery.setMail_idx(0);
            mailManagerInfo = mailService.selectMailManagerInfo(selectQuery);
            mailManagerInfo.setMail_idx(null);
            mailManagerInfo.setMail_title(null);
        }

        mav.setViewName("admin/mail/form");
        mav.addObject("mailManagerInfo", mailManagerInfo);
        mav.addObject("isEdit", false);
        return mav;
    }

    /**
     * 사용자에게 보낼 메일 작성 처리 (POST)
     *
     * @param mailManagerInfo
     * @param bindingResult
     * @param mav
     * @return
     */
    @RequestMapping(value = "/mail/write", method = RequestMethod.POST)
    public ModelAndView mailManagerWrite(@Valid @ModelAttribute("mailManagerInfo") MailManagerVo mailManagerInfo,
                                         BindingResult bindingResult, ModelAndView mav) {
        if (bindingResult.hasErrors()) {
            //mav = mailManagerWriteFrom(mailManagerInfo, mav);
            mav.setViewName("admin/mail/form");
        } else {
            UserVO userVO = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            mailService.insertMailManagerInfo(mailManagerInfo, userVO);
            mav.setViewName("redirect:list");
        }

        return mav;
    }

    /**
     * 사용자에게 보낼 메일 수정 (GET)
     *
     * @param mail_idx        메일 IDX
     * @param mailManagerInfo
     * @param mav
     * @return
     */
    @RequestMapping(value = "/mail/edit", method = RequestMethod.GET)
    public ModelAndView mailManagerEditFrom(@RequestParam Integer mail_idx,
                                            @ModelAttribute("mailManagerInfo") MailManagerVo mailManagerInfo, ModelAndView mav) {

        if (mailManagerInfo.getMail_type() == null) {
            MailManagerVo selectQuery = new MailManagerVo();
            selectQuery.setMail_idx(mail_idx);
            mailManagerInfo = mailService.selectMailManagerInfo(selectQuery);
        }


        mav.setViewName("admin/mail/form");
        mav.addObject("mailManagerInfo", mailManagerInfo);
        mav.addObject("isEdit", true);
        return mav;
    }

    /**
     * 사용자에게 보낼 메일 수정 처리  (POST)
     *
     * @param mailManagerInfo
     * @param bindingResult
     * @param mav
     * @return
     */
    @RequestMapping(value = "/mail/edit", method = RequestMethod.POST)
    public ModelAndView mailManagerEdit(@Valid @ModelAttribute("mailManagerInfo") MailManagerVo mailManagerInfo,
                                        BindingResult bindingResult, ModelAndView mav) {
        if (bindingResult.hasErrors()) {
            mav.setViewName("admin/mail/form");
        } else {
            UserVO userVO = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            /* 메일 관리 정보 등록 */
            mailService.updateMailMangerInfo(mailManagerInfo, userVO);
            mav.setViewName("redirect:list");
        }

        return mav;
    }

    /**
     * 메일 미리보기
     *
     * @param mailIdx 메일 IDX
     * @return
     */
    @RequestMapping("/mail/preview")
    public ModelAndView maillPreview(Integer mailIdx) {
        ModelAndView mav = new ModelAndView();

        UserVO userVO = (UserVO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        MailManagerVo mailInfo = mailService.mailPreview(mailIdx, userVO);

        mav.addObject("mailInfo", mailInfo);

        return mav;
    }

    /**
     * 링크 뷰
     */
    @RequestMapping("/link")
    public ModelAndView linkView() {
        ModelAndView mav = new ModelAndView("admin/link");
        return mav;
    }


    /**
     * 이벤트 등록
     *
     * @param userEventVO 이벤트 발생시간, 발생일자, 담당자, 내용
     * @return
     */
    @RequestMapping(value = "/insertEvent.do", method = RequestMethod.POST)
    public ModelAndView insertEvent(@ModelAttribute UserEventVO userEventVO) {
        adminService.insertEvent(userEventVO);

        ModelAndView mav = new ModelAndView();

        mav.setViewName("redirect:/admin/users");

        return mav;
    }

    /**
     * Admin - users에서 이벤트 버튼 클릭 시 해당 아이디의 이벤트 내역 출력
     *
     * @param userEventVO - userid
     * @return 이벤트 리스트
     */
    @RequestMapping(value = "/ajax/getEventList")
    public @ResponseBody
    List<UserEventVO> getEventList(@RequestBody UserEventVO userEventVO) {
        String userid = userEventVO.getUserid();
        return adminService.getUserEvent(userid);
    }

    /*@RequestMapping(value="/ajax/getChatList")
    public @ResponseBody String getJsonByString(@RequestParam String userChatId) throws Exception{

        String accessKey ="5a693230784e88e7";
        String accessSecret ="33b1e6e55b5f1f9b5a1cf537ee1ed8bf";


        StringEntity param = new StringEntity("{" +
                "\"message\":" + "\"" + message + "\"" +
                "}", "UTF-8");
            @SuppressWarnings("restriction")
            URI uri = new URI("http://api.channel.io/open/userChatId" +
                    userChatId +
                "/messages?botName=");
            HttpClient httpClient = HttpClientBuilder.create().build();
            HttpPost httpPost = new HttpPost(String.valueOf(uri));
            httpPost.addHeader("X-Access-Key", accessKey);
            httpPost.addHeader("X-Access-Secret", accessSecret);
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-type", "application/json");
            //httpPost.setEntity(param);
            HttpResponse response = httpClient.execute(httpPost);

            HttpEntity entity = response.getEntity();
            String content = EntityUtils.toString(entity);
        System.out.println(content);


        return content;
    }*/
/*	@RequestMapping(value="/ajax/getBillingList")
	public @ResponseBody List<UserHometaxVO> billingList(@ModelAttribute UserHometaxVO userHometaxVO){
		return adminService.getBillingList(userHometaxVO);
	}*/
    @RequestMapping(value = "/billingList")
    public ModelAndView billingListListView(@ModelAttribute UserHometaxVO userHometaxVO) {
        UserInvoiceVO userInvoiceVO = new UserInvoiceVO();
        userInvoiceVO.setUserid(userHometaxVO.getUserid());
        List<UserHometaxVO> billingList = adminService.getBillingList(userHometaxVO);
        List<UserHometaxVO> noHometaxList = adminService.getNoHometaxList(userInvoiceVO);
        String userid = userHometaxVO.getUserid();

        ModelAndView mav = new ModelAndView();
        mav.addObject("billingList", billingList);
        mav.addObject("noHometaxList", noHometaxList);
        mav.addObject("userid", userid);

        return mav;
    }

    /**
     * 홈텍스 등록
     *
     * @param
     * @throws Throwable
     */
    @RequestMapping(value = "/insertHometax", method = RequestMethod.POST)
    public ModelAndView insertHometax(@ModelAttribute UserHometaxVO userHometaxVO) throws Throwable {

        ModelAndView mav = new ModelAndView();

        adminService.insertHometax(userHometaxVO);
        int invoice_idx = userHometaxVO.getInvoice_idx();
        String userid = userHometaxVO.getUserid();

        int hometax_idx = adminService.getHometaxIdx(invoice_idx);


        UserInvoiceVO userInvoiceVO = new UserInvoiceVO();
        userInvoiceVO.setInvoice_idx(invoice_idx);
        userInvoiceVO.setHometax_idx(hometax_idx);

        adminService.upDateInvoiceHometaxIdx(userInvoiceVO);
        mav.setViewName("redirect:/admin/billingList?userid=" + userid);

        return mav;
    }

    @RequestMapping(value = "/call_log", method = RequestMethod.GET)
    public @ResponseBody
    ResponseEntity<?> getPatchCallLogList(@RequestParam(value="user_id", required = false) String user_id,@RequestParam(value="page", defaultValue="1") Integer page, @RequestParam(value="search", required = false) String search, @RequestParam(value="search_filed",required = false) String searchFiled ,
                                          @RequestParam(value="startDate",required = false) String startDate, @RequestParam(value="endDate",required = false) String endDate, Authentication authentication) {

//        UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
//        UserVO userVO = userService.loadUserByUsername(user.getUserId());

        Map<String, Object> map = new HashMap<String, Object>();

        CallLogVO callLogVO = new CallLogVO();
        callLogVO.setPageNo(page);

        if(startDate != null && startDate != ""){
            callLogVO.setStartDate(DateUtil.getStringToDate(startDate));
        }
        if(endDate != null && endDate != ""){
            callLogVO.setEndDate(DateUtil.getStringToDate(endDate));
        }

        if(search != null && searchFiled != null){
            callLogVO.setSearchFiled(searchFiled);
            callLogVO.setSearchValue(search);
        }

        System.out.println(callLogVO);

        map = supplyService.getCallLogList(callLogVO);

        return new ResponseEntity<>(map, HttpStatus.OK);

    }

    @RequestMapping(value = "/call_log/excel_download", method = RequestMethod.GET)
    public ResponseEntity<byte[]> getPatchCallLogListDownload(@RequestParam(value="user_id", required = false) String user_id,@RequestParam(value="page", defaultValue="1") Integer page, @RequestParam(value="search", required = false) String search, @RequestParam(value="search_filed",required = false) String searchFiled ,
                                                              @RequestParam(value="startDate",required = false) String startDate,@RequestParam(value="endDate",required = false) String endDate,Authentication authentication) {

        Map<String, Object> map = new HashMap<String, Object>();

        CallLogVO callLogVO = new CallLogVO();
        callLogVO.setPageNo(page);

        if(startDate != null && startDate != ""){
            callLogVO.setStartDate(DateUtil.getStringToDate(startDate));
        }
        if(endDate != null && endDate != ""){
            callLogVO.setEndDate(DateUtil.getStringToDate(endDate));
        }

        if(search != null && searchFiled != null){
            callLogVO.setSearchFiled(searchFiled);
            callLogVO.setSearchValue(search);
        }


        ByteArrayOutputStream os  = supplyService.getCallLogListDownload(callLogVO);

        String contentType = "application/vnd.ms-excel";

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename=text.xls").body(os.toByteArray());

    }

    @RequestMapping(value = "/call_log/audio_download", method = RequestMethod.POST)
    public ResponseEntity<byte[]> getPatchCallLogAudioDownload(@RequestBody Map<String, String> audioPath, Authentication authentication) {

//    public void getPatchCallLogAudioDownload(@RequestBody String fileName, Authentication authentication) {
        UserPrincipal user = (UserPrincipal)authentication.getPrincipal();
        UserVO userVO = userService.loadUserByUsername(user.getUserId());



        try{
            String month = (String)audioPath.get("month");
            String fileName = (String)audioPath.get("fileName");
            HttpClient client = HttpClientBuilder.create().build(); // HttpClient 생성
            HttpGet getRequest = new HttpGet("https://www.eney.co.kr/rec_file/"+month+"/"+fileName); //GET 메소드 URL 생성
            HttpResponse response = client.execute(getRequest);

            InputStream is = response.getEntity().getContent();

            ByteArrayOutputStream buffer = new ByteArrayOutputStream();

            int nRead;
            byte[] data = new byte[16384];

            while ((nRead = is.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }

            System.out.println("====");

            String contentType = "application/wav";

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .header(HttpHeaders.CONTENT_DISPOSITION,"attachment; filename="+fileName).body(buffer.toByteArray());

        }catch (Exception e){
            System.err.println(e.toString());
            return null;
        }


    }

    //변경
//    @RequestMapping(value = "/coloring")
//    public ModelAndView coloringListView(@RequestParam(required = false) Integer present_page) {
//
//        List<ColoringRegisterVO> coloringList = adminService.getColoringList();
//        ColoringRegisterVO coloringRegisterVO = new ColoringRegisterVO();
//
//        ModelAndView mav = new ModelAndView();
//
//        if (present_page != null && present_page != 1) {
//            coloringRegisterVO.setPresent_page(present_page);
//        }
//        mav.addObject("coloringList", coloringList);
//        mav.addObject("coloringRegisterVO", coloringRegisterVO);
//        return mav;
//    }
    @RequestMapping(value = "/coloring", method=RequestMethod.GET)
    public ResponseEntity<?> getCustomerCaseList( @RequestParam(value="page", defaultValue="1") Integer page) throws Exception {
        Map<String, Object> map = new HashMap<>();

//        List<ColoringRegisterVO> coloringList = adminService.getColoringList();

        try {
            ColoringRegisterVO coloringRegisterVO = new ColoringRegisterVO();
            coloringRegisterVO.setPageNo(page);
            map = adminService.getColoringList(coloringRegisterVO);

        } catch (Exception ex) {
            String errorMessage;
            errorMessage = ex + " <== error";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(map, HttpStatus.OK);
    }
    @RequestMapping(value = "/coloring", method=RequestMethod.PUT)
    public ResponseEntity<?> updateColoringRegister(@RequestBody ColoringRegisterVO coloringRegisterVO) throws Exception {

        try {
            adminService.updateColoringRegister(coloringRegisterVO);

        } catch (Exception ex) {
            String errorMessage;
            errorMessage = ex + " <== error";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/updateColoringStatus.do")
    public ModelAndView partnershipMobiemUpload(Integer id, String status) {

        ModelAndView mav = new ModelAndView();

        ColoringRegisterVO registerVO = new ColoringRegisterVO();

        registerVO.setStatus(status);
        registerVO.setId(id);

        supplyService.updateColoringRegisterVO(registerVO);

        mav.setViewName("redirect:/admin/coloring");
        return mav;
    }

    @RequestMapping("/ajax/getNoteContent")
    public @ResponseBody
    ServicePatchcallVO getNoteContent(@RequestParam Integer patchcall_idx) {
        ServicePatchcallVO paramVO = new ServicePatchcallVO();
        paramVO.setPatchcall_idx(patchcall_idx);
        ServicePatchcallVO noteContent = adminService.getNoteContent(paramVO);
        return noteContent;
    }

    @RequestMapping("/ajax/getPatchcallDetail")
    public @ResponseBody
    Map<String, Object> getPatchcallDetail(@RequestParam Integer patchcall_idx) {
        ServicePatchcallVO paramVO = new ServicePatchcallVO();
        paramVO.setPatchcall_idx(patchcall_idx);
        Map<String, Object> data = adminService.getPatchcallDetail(paramVO);
        return data;
    }

    @RequestMapping(value = "/updateNoteContent", method = RequestMethod.POST)
    public ModelAndView updateNoteContent(@ModelAttribute ServicePatchcallVO patchcallVO) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/admin/serviceList/patchcall");
        adminService.updateNoteContent(patchcallVO);

        return mav;
    }

    @RequestMapping("/ajax/getWebNoteContent")
    public @ResponseBody
    ServiceWebHostingVO getWebNoteContent(@RequestParam Integer web_hosting_idx) {
        ServiceWebHostingVO webVO = new ServiceWebHostingVO();
        webVO.setWeb_hosting_idx(web_hosting_idx);
        ServiceWebHostingVO noteContent = adminService.getWebNoteContent(webVO);
        return noteContent;
    }

    @RequestMapping("/ajax/getBiNoteContent")
    public @ResponseBody
    ServiceBIVO getBiNoteContent(@RequestParam Integer idx) {
        ServiceBIVO bi = new ServiceBIVO();
        bi.setIdx(idx);
        ServiceBIVO noteContent = adminService.getBiNoteContent(bi);
        return noteContent;
    }

    @RequestMapping("/ajax/getWebDetail")
    public @ResponseBody
    Map<String, Object> getWebDetail(@RequestParam Integer web_hosting_idx) {
        ServiceWebHostingVO webVO = new ServiceWebHostingVO();
        webVO.setWeb_hosting_idx(web_hosting_idx);
        Map<String, Object> data = adminService.getWebDetail(webVO);
        return data;
    }

    @RequestMapping("/ajax/getBiDetail")
    public @ResponseBody
    Map<String, Object> getBiDetail(@RequestParam Integer idx) {
        ServiceBIVO biVO = new ServiceBIVO();
        biVO.setIdx(idx);
        Map<String, Object> data = adminService.getBiDetail(biVO);
        return data;
    }

    @RequestMapping("/ajax/getBiLink")
    public @ResponseBody
    BiInfoVO getBiLink(@RequestParam Integer idx) {
        BiInfoVO biVO = new BiInfoVO();
        biVO.setService_idx(idx);


        BiInfoVO data = adminService.getBiLink(biVO);
        return data;
    }


    @RequestMapping(value = "/ajax/updateBiLink", method = RequestMethod.POST)
    public @ResponseBody
    ModelAndView updateBiLink(@ModelAttribute BiInfoVO biVO) throws Exception {
        ModelAndView mav = new ModelAndView();
        List<MultipartFile> files = biVO.getFiles();
        if (!(files == null)) {
            fileService.processUpload(files, "BI_SERVICE_KEY", biVO.getIdx(), request);
            biVO.setFile_name(files.get(0).getOriginalFilename());
        }
        adminService.updateBiLink(biVO);

        mav.setViewName("redirect:/admin/serviceList/bi");
        return mav;
    }

    @RequestMapping(value = "/updateWebNoteContent", method = RequestMethod.POST)
    public ModelAndView updateWebNoteContent(@ModelAttribute ServiceWebHostingVO webVO) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/admin/serviceList/webhosting");
        adminService.updateNoteContent(webVO);

        return mav;
    }

    @RequestMapping(value = "/updateBi", method = RequestMethod.POST)
    public ModelAndView updateWebNoteContent(@ModelAttribute ServiceBIVO biVO) {
        ModelAndView mav = new ModelAndView();
        mav.setViewName("redirect:/admin/serviceList/bi");
        adminService.updateBi(biVO);

        return mav;
    }

    /**
     * 컬러링 업로드
     *
     * @param uploadForm
     * @param request
     * @return
     * @throws Throwable
     */
    @RequestMapping(value = "/upload/sound", method = RequestMethod.POST)
    public ResponseEntity<?> coloringUploadSubmit(@ModelAttribute ColoringUploadVO uploadForm, HttpServletRequest request)
            throws Throwable {

//        uploadForm.setUserid(uploadForm.getUserid());
        uploadForm.setTargetid(uploadForm.getUserid());

        List<FileVO> fileVOList = null;

        System.out.println("======");
        System.out.println(uploadForm);
        System.out.println("======");
        //폼 데이터 로직
        supplyService.submitColoringUpload(uploadForm);

        //파일 업로드 로직
        List<MultipartFile> files = uploadForm.getFiles();


        if (uploadForm.getType().equals("coloring") && uploadForm.getIvr().equals("skb")) {
            fileVOList = fileService.coloringUpload(files, uploadForm, "COLORING", uploadForm.getId(), request);
        } else if (uploadForm.getType().equals("coloring") && uploadForm.getIvr().equals("sejong")) {
            fileVOList = fileService.coloringUpload(files, uploadForm, "COLORING_SEJONG", uploadForm.getId(), request);
        } else if (uploadForm.getType().equals("rcvment") && uploadForm.getIvr().equals("skb")) {
            fileVOList = fileService.coloringUpload(files, uploadForm, "RCVMENT_SKB", uploadForm.getId(), request);
        } else if (uploadForm.getType().equals("rcvment") && uploadForm.getIvr().equals("sejong")) {
            fileVOList = fileService.coloringUpload(files, uploadForm, "RCVMENT_SEJONG", uploadForm.getId(), request);
        }

        logger.debug("CustomLog:::" + fileVOList);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/couponCreate/{point}/{count}", method=RequestMethod.POST)
    public ResponseEntity<?> createCouponNum(@PathVariable("point") int point, @PathVariable("count") int count){

        //실행시 ???개 쿠폰 생성
        int couponSize = count;
        System.out.println(count);

        if(point <= 0 || count <= 0){
            return new ResponseEntity<>("포인트와 수량은 0보다 커야합니다",HttpStatus.BAD_REQUEST);
        }

        final char[] possibleCharacters =
                {'1','2','3','4','5','6','7','8','9','0','A','B','C','D','E','F',
                        'G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V',
                        'W','X','Y','Z'};

        final int possibleCharacterCount = possibleCharacters.length;

        ArrayList<CouponVO> couponList = new ArrayList<>();
//        CouponVO[] arr = new String[couponSize];
        Random rnd = new Random();
        int currentIndex = 0;
        int i = 0;
        while (currentIndex < couponSize) {
            StringBuffer buf = new StringBuffer(16);
            //i는 8자리의 랜덤값을 의미
            for (i= 16; i > 0; i--) {
                buf.append(possibleCharacters[rnd.nextInt(possibleCharacterCount)]);
            }

            CouponVO coupon = new CouponVO();
            String couponnum = buf.toString();
            System.out.println("couponnum==>"+couponnum);
            coupon.setCoupon_num(couponnum);
            coupon.setPoint(point);
            couponList.add(coupon);
            currentIndex++;
        }

        int result = adminService.createCouponNum(couponList);

        System.out.println(couponList.toString());
        System.out.println(result);


        if(result > 0 ){
            return new ResponseEntity<>(HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value="/coupon", method=RequestMethod.GET)
    public ResponseEntity<?> couponList(@RequestParam(value="page", defaultValue="1") Integer page){

        Map<String, Object> map = new HashMap<>();

        CouponVO couponVO = new CouponVO();

        couponVO.setPageNo(page);

        map = adminService.getCouponList(couponVO);

        return new ResponseEntity<>(map,HttpStatus.OK);

    }

//    @RequestMapping(value="/customUserCount", method=RequestMethod.GET)
//    public ResponseEntity<?> getCustomUserCount(){
//        int totalCount = adminService.getCallCountAll();
//        CustomUserCount customCount = adminService.getCustomUserCount();
//
//        Map<String, Object> map = new HashMap<>();
//
//        map.put("total", totalCount);
//        map.put("customCount", customCount);
//
//        return new ResponseEntity<>(map,HttpStatus.OK);
//
//    }

    @RequestMapping(value="/customUserCount", method=RequestMethod.PUT)
    public ResponseEntity<?> updateCustomUserCount(@RequestBody CustomUserCount data){
        return new ResponseEntity<>(adminService.updateCustomUserCount(data),HttpStatus.OK);
    }

    @RequestMapping(value="/cloud/service_apply", method=RequestMethod.GET)
    public ResponseEntity<?> getCloudServiceApplyList( @RequestParam(value="page", defaultValue="1") Integer page) throws Exception {
        Map<String, Object> map = new HashMap<>();

        try {
            ServiceApplyVO serviceApplyVO = new ServiceApplyVO();

            serviceApplyVO.setPageNo(page);
            map = serviceApplyService.getCloudServiceApplyList(serviceApplyVO);

        } catch (Exception ex) {
            String errorMessage;
            errorMessage = ex + " <== error";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }



        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @RequestMapping(value="/cloud/service_apply", method=RequestMethod.POST)
    public ResponseEntity<?> editCloudServiceApplyStatus(ServiceApplyVO serviceApplyVO) throws Exception {

        try{

            serviceApplyService.editCloudServiceApplyStatus(serviceApplyVO);


        }catch (Exception ex) {
            String errorMessage;
            errorMessage = ex + " <== error";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value="/service_apply/{serviceType}", method=RequestMethod.GET)
    public ResponseEntity<?> getServiceApplyList( @RequestParam(value="page", defaultValue="1") Integer page,@PathVariable("serviceType") String serviceType) throws Exception {
        Map<String, Object> map = new HashMap<>();

        try {
            ServiceApplyVO serviceApplyVO = new ServiceApplyVO();

            serviceApplyVO.setPageNo(page);

            switch(serviceType){
                case "patchcall":
                    map = adminService.getPatchCallApplyList(serviceApplyVO);
                    break;
                case "patch_intelligence":
                    map = adminService.getPatchIntelligenceApplyList(serviceApplyVO);
                    break;
                case "third_part":
                    map = adminService.getThirdPartApplyList(serviceApplyVO);
                    break;
            }
//            map = serviceApplyService.getCloudServiceApplyList(serviceApplyVO);

        } catch (Exception ex) {
            String errorMessage;
            errorMessage = ex + " <== error";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }



        return new ResponseEntity<>(map, HttpStatus.OK);
    }

    @RequestMapping(value="/service_apply/{serviceType}", method=RequestMethod.PUT)
    public ResponseEntity<?> editServiceApplyStatus(@PathVariable("serviceType") String serviceType, @RequestBody ServiceApplyVO serviceApplyVO) throws Exception {


        try{

            switch(serviceType){
                case "patchcall":
                    adminService.updatePatchCallApply(serviceApplyVO);
                    break;
                case "patch_intelligence":
                    adminService.updatePatchIntelligenceApply(serviceApplyVO);
                    break;
                case "third_part":
                    adminService.updateThirdPartApply(serviceApplyVO);
                    break;
            }


        }catch (Exception ex) {
            String errorMessage;
            errorMessage = ex + " <== error";
            return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }




//    public ServiceApplyVO getCloudServiceApply(int idx) {
//        return serviceApplyDao.getCloudServiceApply(idx);
//    }
//
//    public Map<String, Object> getCustomerCaseList(ServiceApplyVO serviceApplyVO) {
//        serviceApplyVO.setTotalCount(serviceApplyDao.getCloudServiceApplyCnt(serviceApplyVO));
//
//        Map<String, Object> map = new HashMap<>();
//
//        List<ServiceApplyVO> serviceApplyList = serviceApplyDao.getCloudServiceApplyList(serviceApplyVO);
//
//        map.put("list", serviceApplyList);
//        map.put("page", serviceApplyVO);
//
//        return map;
//    }
//
//    public int editCloudServiceApply(ServiceApplyVO serviceApplyVO) {
//        return serviceApplyDao.editCloudServiceApply(serviceApplyVO);
//    }

}
