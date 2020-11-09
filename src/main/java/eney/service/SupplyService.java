package eney.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import eney.domain.*;
import eney.exception.AccessDeniedException;
import eney.exception.PaymentLackException;
import eney.util.EncryptUtil;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import eney.exception.AccessDeniedException;
import eney.exception.PaymentLackException;
import eney.mapper.AdminDao;
import eney.mapper.IvrDao;
import eney.mapper.SupplyDao;
import eney.util.EncryptUtil;
import org.springframework.web.multipart.MultipartFile;

@Service
public class SupplyService {
	
	private static final Logger logger = LoggerFactory.getLogger(SupplyService.class);
	
	@Resource
	LinkService linkService;
	@Resource
	PaymentService paymentService;
	
	@Resource
	SupplyDao supplyDao;
	@Resource
	IvrDao ivrDao;
	@Resource
	AdminDao adminDao;
	

	

	/**
	 * 컬러링 리스트 출력
	 * @param sampleVO
	 * @return
	 */
	public List<ColoringSampleVO> getSampleList(ColoringSampleVO sampleVO) {
		return supplyDao.getSampleList(sampleVO);
	}

	/**
	 * 모비엠 컬러링 신청
	 * @param registerVO
	 * @return
	 */
	@Transactional
	public String submitColoringRegister(ColoringRegisterVO registerVO,UserVO user){
//		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		String res = null;
		
		try{
			//coloringDB 전송
			supplyDao.insertColoringRegister(registerVO);
		}catch(Exception e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			res = "신청 과정에서 문제가 발생했습니다.";
			return res;		
		}
		try{
			//포인트 차감.
			PaymentVO paymentVO = new PaymentVO();
			paymentVO.setCategory("colorring_music");
			paymentVO = supplyDao.getPaymentList(paymentVO).get(0);
			paymentVO.setUserid(user.getUserid());
			paymentService.deductEpoint(paymentVO,user);
		}catch(PaymentLackException e){
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			res = "보유하신 EPOINT가 부족합니다.";
			return res;
		}catch(Exception e){
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			res = "정산 과정에서 문제가 발생했습니다.";
			return res;
		}
		return res;
	}

	/**
	 * 컬러링 업로드
	 * @param uploadVO
	 * @return
	 */
	public int submitColoringUpload(ColoringUploadVO uploadVO) {

		return supplyDao.submitColoringUpload(uploadVO);
	}

	/**
	 * 업로드 된 컬러링 리스트(페이징)
	 * @param uploadVO
	 * @return
	 */
	public List<ColoringUploadVO> getUploadList(ColoringUploadVO uploadVO) {

		uploadVO.setTotal_item_num(supplyDao.getUploadListCnt(uploadVO));
		List<ColoringUploadVO> uploadList = supplyDao.getUploadList(uploadVO);

		return uploadList;
	}

	public List<ColoringUploadVO> getRcvmentList(ColoringUploadVO uploadVO) {

		uploadVO.setTotal_item_num(supplyDao.getRcvmentList(uploadVO).size());
		List<ColoringUploadVO> uploadList = supplyDao.getRcvmentList(uploadVO);

		return uploadList;
	}

	/**
	 * 등록된 컬러링 리스트 출력 (모비엠)
	 * @param param
	 * @return
	 */
	public List<ColoringRegisterVO> getColoringRegisterVOList(ColoringRegisterVO param) {
		
		StringTokenizer st = new StringTokenizer(param.getStatus(),",");
		List<Map<String,String>> statusList = new ArrayList<Map<String,String>>();
		while(st.hasMoreTokens()){
			Map<String,String> map = new HashMap<String,String>();
			map.put("value", st.nextToken());
			statusList.add(map);
			
		}
		param.setStatus_list(statusList);
		return supplyDao.getColoringRegisterVOList(param);
	}
	
	/**
	 * 모비엠 컬러링 업데이트
	 * @param registerVO
	 */
	public void updateColoringRegisterVO(ColoringRegisterVO registerVO) {
		supplyDao.updateColoringRegisterVO(registerVO);
	}

	/**
	 * 컬러링 업로드 내역 삭제
	 * @param uploadVO
	 */
	public void coloringUploadDelete(ColoringUploadVO uploadVO) {
		supplyDao.coloringUploadDelete(uploadVO);
	}
	
	public void rcvmentUploadDelete(ColoringUploadVO uploadVO) {
		supplyDao.rcvmentUploadDelete(uploadVO);
	}

	/**
	 * 공통 코드 리스트
	 * @param commonVO
	 * @return
	 */
	public List<CommonVO> getCommonList(CommonVO commonVO) {
		return supplyDao.getCommonList(commonVO);
	}
	
	public Map<String, String> getCommonMap(CommonVO commonVO){
		Map<String, String> map = new HashMap<>();
		List<CommonVO> commonCodeList = getCommonList(commonVO);
		
		for(CommonVO commonCode: commonCodeList){
			map.put(commonCode.getKeyword(), commonCode.getValue());
		}
		
		return map;
	}
	
	public int updateCommon(CommonVO commonVO){
		return supplyDao.updateCommon(commonVO);
	}
	public int deleteCommon(CommonVO commonVO){
		return supplyDao.deleteCommon(commonVO);
	}
	
	/**
	 * 컬러링 리스트 출력
	 * @param param userid
	 */
	public List<Map<String, Object>> getColoringListAll(ColoringUploadVO param) {
		return supplyDao.getColoringListAll(param);
	}
	/**
	 * 착신멘트 리스트 출력
	 * @param param userid
	 */
	public List<Map<String, Object>> getRcvmentListAll(ColoringUploadVO param) {
		return supplyDao.getRcvmentListAll(param);
	}

	/**
	 * commonVO에 있는 050_number_assigned에 있는 카테고리 불러오기
	 * @param commonVO
	 */
	public Map<String, List<CommonVO>> get050_1th2ndNumList(CommonVO commonVO) {
		List<CommonVO> commonVOList = getCommonList(commonVO);
		Map<String,List<CommonVO>> res = new HashMap<String,List<CommonVO>>();
		List<CommonVO> fistNumList = new ArrayList<CommonVO>();
		List<CommonVO> secondNumList = new ArrayList<CommonVO>();
		for (CommonVO common : commonVOList) {
			String key = common.getKeyword();
			if(key.equals("FIRST"))
				fistNumList.add(common);
			else
				secondNumList.add(common);
		}
		
		res.put("FIRST", fistNumList);
		res.put("SECOND", secondNumList);
		return res;
	}

	/**
	 * 사용여부 확인
	 * @param vnoVO
	 */
	public VnoVO check050Number(VnoVO vnoVO) {
		VnoVO result = null;
		if(vnoVO.getVno().substring(0,4).equals("0506")){
			result = supplyDao.getVnoVO(vnoVO);
			return result;
		}else{
			result = supplyDao.getVnoVO22(vnoVO);
			return result;
		}
		
	}

	/**
	 * 추천 번호 리스트(해당 vno와 비슷한 번호 중 사용되지 않는 번호 리스트 출력
	 * @param vnoVO
	 */
	public List<VnoVO> getRecommendNumList(VnoVO vnoVO) {
		String number = vnoVO.getVno();
		String firstNsecondNumber = number.substring(0,7);
		String firstNumber = number.substring(0,4); 
		String thirdNumber = number.substring(7);
		List<VnoVO> recommendList = new ArrayList<VnoVO>();
		List<VnoVO> list = null;
		int flag = 3;
		while(flag!=-1){
			vnoVO.setVno(firstNsecondNumber + replaceStringIdx(thirdNumber,flag+1,"%"));
			if(firstNumber.equals("0506")){
				list = supplyDao.getRecommendNumList(vnoVO);
			}else{
				list = supplyDao.getRecommendNumList22(vnoVO);
			}
			for (VnoVO item : list) {
				recommendList.add(item);
			}
			if(recommendList==null || recommendList.size()<3){
				flag-=1;
				/*if(flag == -1){
					vnoVO.setVno(firstNsecondNumber+"%");
					list = supplyDao.getRecommendNumList(vnoVO);
					for (VnoVO item : list) {
						recommendList.add(item);
					}
				}*/
			}else
				flag = -1;
			
		}
		return recommendList;
	}
	private String replaceStringIdx(String param, int idx, String replace){
		String res = "";
		int len = param.length();
		if(idx==0){
			res = replace + param.substring(1); 
		}else if(idx==len){
			res = param.substring(0, len-1) + replace;
		}else{
			res = param.substring(0,idx-1) + replace + param.substring(idx,len);
		}
			
		
		return res;
	}
	
	/**
	 * 050 번호 등록
	 * @param agentVO
	 */
	@Transactional
	public void submit050Register(AgentVO agentVO) {
		if(agentVO.getSendment_idx()==null){
			agentVO.setSendment_idx(0);
		}
		//콜백 SMS 설정 시 
		if(agentVO.getVno().substring(0,4).equals("0507")){
			check050Register(agentVO);
			supplyDao.submit050Register22(agentVO);
			
		}else{
			check050Register(agentVO);
			supplyDao.submit050Register(agentVO);
			Map<String,Integer> map = adminDao.getEnablePatchCallSKB();
			supplyDao.skbUseNumber(map);
		}
	}

	/**
	 * 050 번호가 사용중인지 체크
	 * @param agentVO
	 */
	private void check050Register(AgentVO agentVO) {
		VnoVO vnoInfo = null;
		if(agentVO.getVno().substring(0,4).equals("0507") ){
			vnoInfo = supplyDao.getVnoVO22(agentVO);
		}else{
			vnoInfo = supplyDao.getVnoVO(agentVO);
		}
		if(vnoInfo.getReg_gubun().equals("A")){
			throw new RuntimeException();
		}
	}

	/**
	 * 050 번호와 사용여부 수정
	 * @param agentVO
	 */
	public void update050VnoVO(AgentVO agentVO) {
		if(agentVO.getVno().substring(0,4).equals("0507")){
			supplyDao.update050VnoVO22(agentVO);
		}else{
			supplyDao.update050VnoVO(agentVO);
		}
		
	}

	/**
	 * 회원 별 콜 로그 조회 및 엑셀 출력 기능
	 * @param callLogVO
	 * @return 콜 로그 리스트
	 */
	public Map<String,Object> getCallLogList(CallLogVO callLogVO) {

		Map<String, Object> map = new HashMap<>();

		callLogVO.setTotalCount(ivrDao.getCallLogCnt(callLogVO));

		if(CallLogVO.VIEW_MODE_EXCEL.equals(callLogVO.getView_mode())){
			/* 엑셀로 출력할 경우 */
			callLogVO.setTotalCount(100000);
					//SpreadsheetVersion.EXCEL2007.getLastRowIndex() - 엑셀 최대 길이까지만 출력하도록 설정
		}
		List<CallLogVO> callLogList = ivrDao.getCallLogList(callLogVO);

		map.put("page",callLogVO);
		map.put("list",callLogList);
	
		return map;
	}

	public Map<String,Object> getCallLogListAPI(CallLogVO callLogVO) {

		Map<String, Object> map = new HashMap<>();

		callLogVO.setTotalCount(ivrDao.getCallLogCnt01(callLogVO));

		if(CallLogVO.VIEW_MODE_EXCEL.equals(callLogVO.getView_mode())){
			/* 엑셀로 출력할 경우 */
			callLogVO.setTotalCount(100000);
			//SpreadsheetVersion.EXCEL2007.getLastRowIndex() - 엑셀 최대 길이까지만 출력하도록 설정
		}
		List<CallLogVO> callLogList = ivrDao.getCallLogListApi01(callLogVO);

		map.put("page",callLogVO);
		map.put("list",callLogList);

		return map;
	}


	public ByteArrayOutputStream getCallLogListDownload(CallLogVO callLogVO) {

		Map<String, Object> map = new HashMap<>();

		callLogVO.setPageSize(999999);
		callLogVO.setTotalCount(ivrDao.getCallLogCnt(callLogVO));

		if(CallLogVO.VIEW_MODE_EXCEL.equals(callLogVO.getView_mode())){
			/* 엑셀로 출력할 경우 */
			callLogVO.setTotalCount(20);
			//SpreadsheetVersion.EXCEL2007.getLastRowIndex() - 엑셀 최대 길이까지만 출력하도록 설정
		}
		List<CallLogVO> callLogList = ivrDao.getCallLogList(callLogVO);

		try{
			//Excel Down 시작
			Workbook workbook = new HSSFWorkbook();
			//시트생성
			Sheet sheet = workbook.createSheet("수신내역");

			//행, 열, 열번호
			Row row = null;
			Cell cell = null;
			int rowNo = 0;

			// 테이블 헤더용 스타일
			CellStyle headStyle = workbook.createCellStyle();

			// 가는 경계선을 가집니다.
			headStyle.setBorderTop(BorderStyle.THIN);
			headStyle.setBorderBottom(BorderStyle.THIN);
			headStyle.setBorderLeft(BorderStyle.THIN);
			headStyle.setBorderRight(BorderStyle.THIN);

			// 배경색은 노란색입니다.
			headStyle.setFillForegroundColor(HSSFColor.YELLOW.index);
			headStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			// 데이터는 가운데 정렬합니다.
			headStyle.setAlignment(HorizontalAlignment.CENTER);

			// 데이터용 경계 스타일 테두리만 지정
			CellStyle bodyStyle = workbook.createCellStyle();
			bodyStyle.setBorderTop(BorderStyle.THIN);
			bodyStyle.setBorderBottom(BorderStyle.THIN);
			bodyStyle.setBorderLeft(BorderStyle.THIN);
			bodyStyle.setBorderRight(BorderStyle.THIN);

			// 헤더 생성
			row = sheet.createRow(rowNo++);

//			가맹점명	발신지명	발신번호	착신번호	050번호	통화날짜	통화발신시간	통화수신시간	통화종료시간	통화시간	통화결과
			cell = row.createCell(0);
			cell.setCellStyle(headStyle);
			cell.setCellValue("가맹점명");

			cell = row.createCell(1);
			cell.setCellStyle(headStyle);
			cell.setCellValue("발신지명");

			cell = row.createCell(2);
			cell.setCellStyle(headStyle);
			cell.setCellValue("발신번호");

			cell = row.createCell(3);
			cell.setCellStyle(headStyle);
			cell.setCellValue("착신번호");

			cell = row.createCell(4);
			cell.setCellStyle(headStyle);
			cell.setCellValue("050번호");

			cell = row.createCell(5);
			cell.setCellStyle(headStyle);
			cell.setCellValue("통화날짜");

			cell = row.createCell(6);
			cell.setCellStyle(headStyle);
			cell.setCellValue("통화발신시간");

			cell = row.createCell(7);
			cell.setCellStyle(headStyle);
			cell.setCellValue("통화수신시간");

			cell = row.createCell(8);
			cell.setCellStyle(headStyle);
			cell.setCellValue("통화종료시간");

			cell = row.createCell(9);
			cell.setCellStyle(headStyle);
			cell.setCellValue("통화시간");

			cell = row.createCell(10);
			cell.setCellStyle(headStyle);
			cell.setCellValue("통화결과 코드");

			cell = row.createCell(11);
			cell.setCellStyle(headStyle);
			cell.setCellValue("통화결과");

			// 데이터 부분 생성
			for(CallLogVO log : callLogList) {
//				Map<String, Object> mapValue = (Map<String, Object>) map1;

//				logger.info("DB DATA : "+mapValue.toString());
//			가맹점명	발신지명	발신번호	착신번호	050번호	통화날짜	통화발신시간	통화수신시간	통화종료시간	통화시간	통화결과
				row = sheet.createRow(rowNo++);
				cell = row.createCell(0);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(""+log.getAgent_name());

				cell = row.createCell(1);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(""+log.getDong_name());

				cell = row.createCell(2);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(""+log.getAni());

				cell = row.createCell(3);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(""+log.getCalled_no());

				cell = row.createCell(4);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(""+log.getDn());

				cell = row.createCell(5);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(""+log.getCs_date());

				cell = row.createCell(6);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(""+log.getCs_time());

				cell = row.createCell(7);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(""+log.getSe_time());

				cell = row.createCell(8);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(""+log.getSs_time());

				cell = row.createCell(9);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(""+log.getSvc_duration_text());

				cell = row.createCell(10);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(""+log.getCall_result());

				cell = row.createCell(11);
				cell.setCellStyle(bodyStyle);
				cell.setCellValue(""+log.getCall_result_text());
			}

			// 엑셀 출력

			ByteArrayOutputStream os = new ByteArrayOutputStream();
			workbook.write(os);
			os.close();

			return os;

		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}


	}



	public List<String> getCallLogListAll(CallLogVO callLogVO) {
		return ivrDao.getCallLogListAll(callLogVO);
	}

	public int insertMemo(Map<String, Object> map) {
		return ivrDao.insertMemo(map);
	}

    public int updateMemo(Map<String, Object> map) {
        return ivrDao.updateMemo(map);
    }
	public int deleteMemo(Map<String, Object> map) {
		return ivrDao.deleteMemo(map);
	}

	public Map<String,Object> getMemo(Map<String, Object> map) {
		return ivrDao.getMemo(map);
	}



	/**
	 * 가맹점 리스트 출력 및 검색
	 * @param agentVO
	 * @return 가맹점 리스트
	 */
	public List<AgentVO> getAgentVOList(AgentVO agentVO) {
		List<AgentVO> abc = supplyDao.getAgentVOList(agentVO);
//		agentVO.setTotal_item_num(abc.size());
//		agentVO.setPage_per_item_num(10);

		return abc;
	}

	public Map<String, Object> getAgentVOListForService(AgentVO agentVO) {

//		List<AgentVO> abc = supplyDao.getAgentVOListForService(agentVO);
//		agentVO.setTotal_item_num(supplyDao.getAgentVOListCnt(agentVO));
//		agentVO.setPage_per_item_num(10);

		agentVO.setPageSize(50);
		agentVO.setTotalCount(supplyDao.getAgentListCnt(agentVO));

		Map<String, Object> map = new HashMap<>();

		List<AgentVO> agentList = supplyDao.getAgentVOListForService(agentVO);

		map.put("list", agentList);
		map.put("page", agentVO);


		return map;
	}


	public List<String> getAgentVOListAll(AgentVO agentVO) {
		return supplyDao.getAgentVOListAll(agentVO);
	}
	
	public List<AgentVO> getAgentVOListUnion(AgentVO agentVO) {
		return supplyDao.getAgentVOListUnion(agentVO);
	}
	
//	public List<AgentVO> getAgent(AgentVO agentVO) {
//		agentVO.setTotal_item_num(supplyDao.getAgentCnt(agentVO));
//		return supplyDao.getAgent(agentVO);
//	}
//	public List<AgentVO> getAgent22(AgentVO agentVO) {
//		agentVO.setTotal_item_num(supplyDao.getAgentCnt(agentVO));
//		return supplyDao.getAgent22(agentVO);
//	}
	

	/**
	 * 해당 아이디의 agentVO 출력(vno, rcv_no, dong_name 등)
	 * @param agentVO
	 * @return
	 */
	public AgentVO getAgentVO(AgentVO agentVO) {
		if(agentVO.getVno().substring(0,4).equals("0506")){
			return supplyDao.getAgentVO(agentVO);
		}else{
			return supplyDao.getAgentVO22(agentVO);
		}
		
	}

	/**
	 * 050 상세 정보 수정
	 * @param agentVO
	 */
	public Integer update050Agent(AgentVO agentVO) {
		agentVO.setVno(agentVO.getVno().replaceAll("-", ""));
		if(agentVO.getRcv_no()!=null){
			agentVO.setRcv_no(agentVO.getRcv_no().replaceAll("-", ""));
		}
		
		if(agentVO.getVno().substring(0,4).equals("0506")){
			supplyDao.update050Agent(agentVO);
			Map<String,Integer> map = adminDao.getEnablePatchCallSKB();
			supplyDao.skbUseNumber(map);
		}else{
			return supplyDao.update050Agent22(agentVO);
		}

		return 0;
	}

	public void updateAgentAddress(AgentAddressVO agentAddress) {
		supplyDao.updateAgentAddress(agentAddress);
	}


	public List<PaymentVO> getPaymentList(PaymentVO paymentVO) {
		return supplyDao.getPaymentList(paymentVO);
	}

	public List<PaymentVO> getPaymentListWithCate(PaymentVO paymentVO) {
		return supplyDao.getPaymentListWithCate(paymentVO);
	}

	private String getDateForm(String date) {
		date = date.replace("년 ", "");
		date = date.replace("월 ", "");
		date = date.replace("일 ", "");
		date = date.replace("시 ", "");
		date = date.replace("분", "00");
		return date;
	}

	public boolean checkPassword(AgentVO agentVO) throws AccessDeniedException, NoSuchAlgorithmException {
		boolean flag = false;
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if(!userVO.getPassword().equals(EncryptUtil.encryptSHA256(agentVO.getEtc_data1())))
			throw new AccessDeniedException("");
		else{
			flag = true;
			return flag;
		}
			
	}
	
	@Transactional
	public void close050Service(AgentVO agentVO) {
		delete050Agent(agentVO);
		update050Vno(agentVO);
		
		List<String> closeVnoList = agentVO.getClose_vno();
		for(String closeVnoEach : closeVnoList){
			LinkInfoVo selectQuery = new LinkInfoVo();
			selectQuery.setLink_url("tel:" + closeVnoEach);
			selectQuery.setLink_type(LinkInfoVo.LINK_TYPE_CLICK_TO_CALL);
			LinkInfoVo linkInfo = linkService.selectLinkInfo(selectQuery);
			
			if(linkInfo != null){
				linkService.deleteLinkInfo(linkInfo.getLink_idx());						
			}
		}
	}
	
	@Transactional
	public void close050Admin(AgentVO agentVO) {
		String vno = agentVO.getVno();
	
		if(vno.substring(0,4).equals("0506")){
			supplyDao.deleteAgentByVno(agentVO);
			agentVO.setReg_gubun("B");
			supplyDao.update050VnoForCancelByVno(agentVO);
			supplyDao.deleteAgentDelByVno(agentVO);
		}else{
			supplyDao.deleteAgentByVno22(agentVO);
			agentVO.setReg_gubun("B");
			supplyDao.update050VnoForCancelByVno22(agentVO);
			supplyDao.deleteAgentDelByVno(agentVO);
		}
	}
	
	private void insert050AgentBeExpired(List<AgentVO> expiredAgentList){
		Map<String, Object> map = new HashMap<>();
		map.put("list", expiredAgentList);
		supplyDao.insert050AgentBeExpired(map);
		/*for(AgentVO agent : expiredAgentList){
			delete050Agent(agent);
			update050Vno(agent);
		}*/
	}
	
	
	
	private void update050Vno(AgentVO agentVO) {
		List<String> vnoList = agentVO.getClose_vno();
		for(String vno : vnoList){
			if(vno.substring(0,4).equals("0506")){
				agentVO.setReg_gubun("B");
				supplyDao.update050VnoForCancel(agentVO);
			}else{
				agentVO.setReg_gubun("B");
				supplyDao.update050VnoForCancel22(agentVO);
			}
		}
	}

	private void delete050Agent(AgentVO agentVO) {
		List<String> vnoList = agentVO.getClose_vno();
		for(String vno : vnoList){
			if(vno.substring(0,4).equals("0506")){
				supplyDao.deleteAgent(agentVO);
			}else{
				supplyDao.deleteAgent22(agentVO);
			}
		}		
	}


//	//막음
//	@Scheduled(cron="0 0 0 * * *")
//	//@Scheduled(fixedDelay=5000)
//	public void processExpired050(){
//		java.util.Calendar calendar = java.util.Calendar.getInstance();
//    	java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//    	System.out.println("현재 시각: " +  dateFormat.format(calendar.getTime()));
//
//    	List<AgentVO> expiredAgentList = supplyDao.getExpiredAgentList();
//    	List<String> close_vno = new ArrayList<String>();
//    	for (AgentVO agentVO : expiredAgentList) {
//			close_vno.add(agentVO.getVno());
//		}
//    	AgentVO param = new AgentVO();
//    	param.setClose_vno(close_vno);
//
//    	if(close_vno.size()>0){
//    		//close050Service(param);
//    		insert050AgentBeExpired(expiredAgentList);
//    	}
//	}

	/**
	 * 환불 요청 시 포인트, 계좌정보, 연락처 확인
	 * @param refundVO
	 * @return
	 */
	public Map<String,Object> checkRefundForm(EpointRefundVO refundVO) {
		Map<String,Object> ret = new HashMap<String,Object>();
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		
		boolean res = true;
		String message = new String();
		int userEpoint = Integer.parseInt(userVO.getEpoint());
		//1. 현재 유저의 epoint가 맞는지
		if(refundVO.getEpoint_present()!=userEpoint){
			message += "현재 epoint와 다릅니다.\\n";
			res = false;
		}
		//2. 환불 포인트가 1000보다 큰지
		if(refundVO.getAmount_to_refund()<1000){
			message += "환불 금액이 1000원이상이어야 합니다.\\n";
			res = false;
		}
		//3. 현재 포인트와 환불 포인트의 차가 0보다 큰지
		if(refundVO.getEpoint_present() - refundVO.getEpoint_to_refund() < 0){
			message += "현재 epoint가 부족합니다.\\n";
			res = false;
		}
		//4. 환불 금액이 정책에 맞게 계산이 됐는지
		if(refundVO.getAmount_to_refund()!=getAmountToRefund(refundVO.getEpoint_to_refund())){
			message += "환불 금액이 변조되었습니다.\\n";
			res = false;
		}
		//5. 잔여 포인트가 현재 포인트 - 환불 포인트인지
		if(refundVO.getEpoint_balance()!=refundVO.getEpoint_present()-refundVO.getEpoint_to_refund()){
			message += "잔여 포인트가 변조되었습니다.\\n";
			res = false;
		}
		//6. 은행을 입력하지 않진 않았는지
		if(refundVO.getWhich_bank()==null||refundVO.getWhich_bank().equals("")||refundVO.getWhich_bank().equals("은행선택")){
			message += "은행을 선택해주세요.\\n";
			res = false;
		}
		if(refundVO.getAccount_holder()==null||refundVO.getAccount_holder().equals("")){
			message += "예금주를 입력해주세요.\\n";
			res = false;
		}
		if(refundVO.getAccount_number().equals("")){
			message += "계좌번호를 입력해주세요.\\n";
			res = false;
		}
		//7. 연락처 양식이 맞는지
		if(!rejectIfNotPhoneNumberFormed(refundVO.getPhone_number())){
			message += "연락처 형식이 잘못되었습니다.\\n";
			res = false;
		}
		
		ret.put("result", res);
		ret.put("message", message);
		return ret;
	}

	private int getAmountToRefund(int epoint_to_refund) {
		int res = 0;
		res = (int) (epoint_to_refund * 0.9);
		return res;	
	}
	
	private boolean rejectIfNotPhoneNumberFormed(String phone_num) {
		boolean flag = true;
		if(phone_num==null){
			flag = false;
			return flag;
		}
		phone_num = phone_num.replace("-", "");
		String regex = "^\\d{2,4}\\d{3,4}\\d{4}$";
		
		if(!Pattern.matches(regex, phone_num))
			flag = false;
		
		return flag;
	}

	public void writeRefundLog(EpointRefundVO refundVO) {
		supplyDao.writeRefundLog(refundVO);
	}

	public List<EpointRefundVO> getRefundList(EpointRefundVO paramVO) {
		paramVO.setTotal_item_num(supplyDao.getRefundListCnt(paramVO));
		return supplyDao.getRefundList(paramVO);
	}

	public ColoringUploadVO getColoringUploadVO(ColoringUploadVO colorringVo) {
		return supplyDao.getColoringUploadVO(colorringVo);
	}
	
	public ColoringUploadVO getRcvmentUploadVO(ColoringUploadVO colorringVo){
		return supplyDao.getRcvmentUploadVO(colorringVo);
	}


	//TODO... 막아둠
	public Map<String, String> processEpoint050Regist() {
		UserVO userVO = (UserVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map<String,String> res = new HashMap<String,String>();
		PaymentVO paymentVO = new PaymentVO();

//		try{
//			//포인트 차감.
//			paymentVO.setCategory("register_050");
//			paymentVO = supplyDao.getPaymentList(paymentVO).get(0);
//			paymentVO.setUserid(userVO.getUserid());
////			paymentService.deductEpoint(paymentVO);
//		}catch(PaymentLackException e){
//			//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//			String message = "보유하신 EPOINT가 부족합니다.\\n추가등록을 위해서는 " + paymentVO.getSale_price() + "POINT가 필요합니다.";
//			res.put("result", "fail");
//			res.put("message", message);
//			return res;
//		}catch(Exception e){
//			e.printStackTrace();
//			//TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
//			String message = "정산 과정에서 문제가 발생했습니다.";
//			res.put("result", "fail");
//			res.put("message", message);
//			return res;
//		}
		res.put("result", "success");
		return res;
	}

	/*
	 * 해당유저의 가입된 모든 050번호를 해지한다.
	 */
	@Transactional(propagation=Propagation.NESTED)
	public void close050All(UserVO userVO) {
		AgentVO agentVO = new AgentVO();
		agentVO.setUser_id(userVO.getUserid());
//		agentVO.setPresent_page(-1);
		List<AgentVO> agentList = getAgentVOList(agentVO);
		List<String> closedVno = new ArrayList<String>(); 
		for (AgentVO agent : agentList) {
			closedVno.add(agent.getVno());
		}
		agentVO.setClose_vno(closedVno);
		if(closedVno.size()!=0){
			delete050Agent(agentVO);
			update050Vno(agentVO);
			
			List<String> closeVnoList = agentVO.getClose_vno();
			for(String closeVnoEach : closeVnoList){
				LinkInfoVo selectQuery = new LinkInfoVo();
				selectQuery.setLink_url("tel:" + closeVnoEach);
				selectQuery.setLink_type(LinkInfoVo.LINK_TYPE_CLICK_TO_CALL);
				LinkInfoVo linkInfo = linkService.selectLinkInfo(selectQuery);
				
				if(linkInfo != null){
					linkService.deleteLinkInfo(linkInfo.getLink_idx());						
				}
			}
		}
	}
	
	// 신규 교환기에 등록

	public void update050VnoVO22(AgentVO agentVO) {
		supplyDao.update050VnoVO22(agentVO);
	}

	public void update050Agent22ByVno(AgentVO agentVO) {
		supplyDao.update050Agent22ByVno(agentVO);
	}
	public Integer getAgentVOListCnt(AgentVO agentVO){
		return supplyDao.getAgentVOListCnt(agentVO);
	}


	public List<ServiceListVO> getUserServiceList(ServiceListVO serviceListVO) {
		
		return supplyDao.getUserServiceList(serviceListVO);
	}

	public Integer getUserServiceCnt(ServiceListVO serviceListVO){
		return supplyDao.getUserServiceCount(serviceListVO);
	}


	public List<CallLogVO> getCallLogListSetTime(CallLogVO callLogVO) {
		callLogVO.setTotalCount(ivrDao.getCallLogCntForAPI(callLogVO));

		if(CallLogVO.VIEW_MODE_EXCEL.equals(callLogVO.getView_mode())){
			/* 엑셀로 출력할 경우 */
			callLogVO.setTotalCount(100000);
			//SpreadsheetVersion.EXCEL2007.getLastRowIndex() - 엑셀 최대 길이까지만 출력하도록 설정
		}

		return ivrDao.getCallLogListSetTime(callLogVO);
	}

	//    해당 vno 주소 검색
	public AgentAddressVO getAgentAddress(String vno) {
		return supplyDao.getAgentAddress(vno);
	}


	public Exception bulkUpdateAgent(MultipartFile file, String userId) {



		try{

			List<AgentVO> agent0506List = new ArrayList<>();
			List<AgentVO> agent0507List = new ArrayList<>();

			if(file!=null && file != null && file.getSize() > 0){
				InputStream excelFile =  new BufferedInputStream(file.getInputStream());

				HSSFWorkbook workbook = new HSSFWorkbook(excelFile);

				int rowindex=0;
				int columnindex=0;
				//시트 수 (첫번째에만 존재하므로 0을 준다)
				//만약 각 시트를 읽기위해서는 FOR문을 한번더 돌려준다
				HSSFSheet sheet=workbook.getSheetAt(0);
				//행의 수
				int rows=sheet.getPhysicalNumberOfRows();
				for(rowindex=1;rowindex<rows;rowindex++){
					//행을읽는다
					HSSFRow row=sheet.getRow(rowindex);
					if(row !=null){
						//셀의 수
						int cells=row.getPhysicalNumberOfCells();
						AgentVO agentVO = new AgentVO();
						agentVO.setUser_id(userId);
						for(columnindex=0; columnindex<=4; columnindex++){
							//셀값을 읽는다
							HSSFCell cell=row.getCell(columnindex);
							String value="";
							//셀이 빈값일경우를 위한 널체크
							if(columnindex == 0 && cell == null){
								throw new Exception("050번호는 필수입니다.");
							}
//							if(cell==null){
//								continue;
//							}

							switch (columnindex){
								case 0:
									agentVO.setVno(this.StringReplace(cell.getStringCellValue()));
									break;
								case 1:
									String rcvNo;
									try{
										rcvNo = this.StringReplace(cell.getStringCellValue());
									}catch(NullPointerException e){
										rcvNo = "";
									}
									agentVO.setRcv_no(rcvNo);
									break;
								case 2:
									String name;
									try{
										name = cell.getStringCellValue();
									}catch(NullPointerException e){
										name = "";
									}
									agentVO.setName(name);
									break;
								case 3:
									String dongName;
									try{
										dongName = cell.getStringCellValue();
									}catch(NullPointerException e){
										dongName = "";
									}
									agentVO.setDong_name(dongName);
									break;
								case 4:
									String Yn;
									try{
										Yn = (cell.getNumericCellValue() == 1) ? "Y" : "N";
									}catch(NullPointerException e){
										Yn = "";
									}
									agentVO.setDong_yn(Yn);
									break;
							}
							if(columnindex == 4){
								switch (agentVO.getVno().substring(0, 4)){
									case "0506":
										agent0506List.add(agentVO);
										break;
									case "0507":
										agent0507List.add(agentVO);
										break;
								};
								System.out.println(agentVO);
								agentVO = new AgentVO();
							}
						}

					}else{
						System.out.println("로우 널임");
					}
				}

				if(agent0506List.size() != 0 ){
					supplyDao.bulkUpdate0506Agent(agent0506List);
//					for(int i=0;i <=agent0506List.size()-1; i++){
//						supplyDao.bulkUpdate0506Agent(agent0506List.get(i));
//					}
				}

				if(agent0507List.size() != 0){
					supplyDao.bulkUpdate0507Agent(agent0507List);
//					for(int i=0;i <=agent0507List.size()-1; i++){
//						supplyDao.bulkUpdate0507Agent(agent0507List.get(i));
//					}
				}
			}

			return null;

		}catch(Exception e){
			e.printStackTrace();
			return e;
		}


	}

	public static String StringReplace(String str){
		String match = "[^\uAC00-\uD7A3xfe0-9a-zA-Z\\s]";
		str =str.replaceAll(match, "");
		return str;
	}



}
