package eney.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Random;

import javax.annotation.Resource;

import eney.domain.*;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import eney.domain.CallbackSmsVO;
import eney.domain.RecordVO;
import eney.domain.ServicePatchcallOtherVO;
import eney.domain.ServicePatchcallVO;
import eney.domain.ServiceWebHostingVO;
import eney.domain.UserInvoiceVO;
import eney.domain.UserVO;
import eney.mapper.InvoiceDao;

@Service
public class InvoiceService {

	@Resource
	AdminService adminService;
	
	@Resource
	UserService userService;
	
	@Resource
	InvoiceDao invoiceDao;
	
	/**
	 * 인보이스 등록
	 * 인보이스 번호는 오늘 날짜, 아이디, 랜덤값을 조합해서 만든다.
	 * @param userVO
	 */
	public int insertInvoice(UserInvoiceVO userInvoiceVO) {
		Random random = new Random();        
		int result = random.nextInt(10000)+1000;	 
		if(result>10000){
		    result = result - 1000;
		}		
		Calendar oCalendar = Calendar.getInstance();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
    	
		String today = dateFormat.format(oCalendar.getTime());
		userInvoiceVO.setInvoice_number(today +"-"+ userInvoiceVO.getUserid() +"-"+ result);
		
		return invoiceDao.insertInvoice(userInvoiceVO);
	}
	
	public void insertInvoiceContent(UserInvoiceVO invoiceVO) {
		invoiceDao.insertInvoiceContent(invoiceVO);
	}
	
	public List<UserInvoiceVO> selectInvoiceList (UserInvoiceVO userInvoiceVO){
		return invoiceDao.selectInvoiceList(userInvoiceVO);
	}

//	//막음
//	@Scheduled(cron="00 10 00 15 * *")
//	public @ResponseBody Integer insertInvoice() throws ParseException{
//		UserVO userVO = new UserVO();
//		UserInvoiceVO invoiceVO = new UserInvoiceVO();
//		List<UserVO> userList =  adminService.getUserListAll(userVO);
//		for(UserVO user : userList){
//			List<ServicePatchcallVO> patchcallList = userService.getPatchcallServiceListForInvoice(user.getUserid());
//			List<ServicePatchcallOtherVO> otherList = userService.getPatchcallOtherserviceListForInvoice(user.getUserid());
//			List<ServiceWebHostingVO> webList = userService.getHostingServiceListForInvoice(user.getUserid());
//			CallbackSmsVO callback = userService.checkCallbackSmsServiceForInvoice(user.getUserid());
//			RecordVO recordVO = new RecordVO(); recordVO.setUserid(user.getUserid());
//			RecordVO record = userService.selectRecordServiceListByUserVO(recordVO);
//			int count = patchcallList.size() + otherList.size() + webList.size();
//			if(callback != null){count ++;}
//			if(count > 0){
//				invoiceVO.setUserid(user.getUserid());
//				insertInvoice(invoiceVO);
//				Integer invoice_idx = invoiceVO.getInvoice_idx();
//				for(ServicePatchcallVO patch : patchcallList){
//					invoiceVO.setUserid(user.getUserid());
//					invoiceVO.setService_type(patch.getService_type());
//					invoiceVO.setService_period(patch.getService_period());
//					invoiceVO.setEnd_date(patch.getEnd_date());
//					invoiceVO.setPay_state(patch.getPay_state());
//					invoiceVO.setPay_way(patch.getPay_way());
//					invoiceVO.setInvoice_idx(invoice_idx);
//					invoiceVO.setService("patchcall");
//					switch(patch.getService_type()){
//					case "패치콜 single" :
//						invoiceVO.setSum(10000);
//		    			break;
//					case "패치콜 double" :
//		        		invoiceVO.setSum(50000);
//		    			break;
//		        	case "패치콜 support" :
//		        		invoiceVO.setSum(100000);
//		    			break;
//		        	case "패치콜 start-up" :
//		        		invoiceVO.setSum(150000);
//		    			break;
//		        	case "패치콜 business" :
//		        		invoiceVO.setSum(200000);
//		    			break;
//		        	case "패치콜 enterprise" :
//		        		invoiceVO.setSum(250000);
//		    			break;
//					};
//					invoiceVO.setCeo_name(patch.getCeo_name());
//					invoiceVO.setCorporate_address(patch.getCorporate_address());
//					invoiceVO.setPublish_email(patch.getPublish_email());
//					invoiceVO.setReg_date(patch.getReg_date());
//					insertInvoiceContent(invoiceVO);
//				}
//
//				for(ServicePatchcallOtherVO other : otherList){
//					invoiceVO.setUserid(user.getUserid());
//					invoiceVO.setService_type(other.getService_type());
//					invoiceVO.setService_period(other.getService_period());
//					invoiceVO.setEnd_date(other.getEnd_date());
//					invoiceVO.setPay_state(other.getPay_state());
//					invoiceVO.setPay_way(other.getPay_way());
//					invoiceVO.setInvoice_idx(invoice_idx);
//					switch(other.getService_type()){
//					case "080 수신자부담번호" :
//						invoiceVO.setService("number080");
//		        		invoiceVO.setSum(30000);
//		    			break;
//		        	case "대표번호" :
//		        		invoiceVO.setService("general_number");
//		        		invoiceVO.setSum(10000);
//		    			break;
//		        	case "콜백SMS" :
//		        		invoiceVO.setService("callback_sms");
//		        		invoiceVO.setSum(10000);
//		    			break;
//		        	case "short_url" :
//		        		invoiceVO.setService("short_url");
//		        		invoiceVO.setSum(10000);
//		    			break;
//		        	case "녹취" :
//		        		invoiceVO.setService("record");
//		        		invoiceVO.setSum(200000);
//		    			break;
//					};
//					invoiceVO.setCeo_name(other.getCeo_name());
//					invoiceVO.setCorporate_address(other.getCorporate_address());
//					invoiceVO.setPublish_email(other.getPublish_email());
//					invoiceVO.setReg_date(other.getReg_date());
//					insertInvoiceContent(invoiceVO);
//				}
//
//				for(ServiceWebHostingVO web : webList){
//					invoiceVO.setUserid(user.getUserid());
//					invoiceVO.setService_type(web.getService_type());
//					invoiceVO.setService_period(web.getService_period());
//					invoiceVO.setEnd_date(web.getEnd_date());
//					invoiceVO.setPay_state(web.getPay_state());
//					invoiceVO.setPay_way(web.getPay_way());
//					invoiceVO.setInvoice_idx(invoice_idx);
//					invoiceVO.setService("webhosting");
//					switch(web.getService_type()){
//					case "웹호스팅 standard" :
//		        		invoiceVO.setSum(35000);
//		    			break;
//		        	case "웹호스팅 support_web" :
//		        		invoiceVO.setSum(50000);
//		    			break;
//		        	case "웹호스팅 premium" :
//		        		invoiceVO.setSum(150000);
//		    			break;
//					};
//					invoiceVO.setCeo_name(web.getCeo_name());
//					invoiceVO.setCorporate_address(web.getCorporate_address());
//					invoiceVO.setPublish_email(web.getPublish_email());
//					invoiceVO.setReg_date(web.getReg_date());
//					insertInvoiceContent(invoiceVO);
//				}
//				if(callback != null){
//					invoiceVO.setUserid(user.getUserid());
//					invoiceVO.setService_type(callback.getService_type());
//					invoiceVO.setService_period(callback.getService_period());
//					invoiceVO.setEnd_date(callback.getEnd_date());
//					invoiceVO.setPay_state(callback.getPay_state());
//					invoiceVO.setPay_way(callback.getPay_way());
//					invoiceVO.setInvoice_idx(invoice_idx);
//					invoiceVO.setService("callback_sms");
//					invoiceVO.setSum(10000);
//					invoiceVO.setCeo_name(callback.getCeo_name());
//					invoiceVO.setCorporate_address(callback.getCorporate_address());
//					invoiceVO.setPublish_email(callback.getPublish_email());
//					invoiceVO.setReg_date(callback.getReg_date());
//					//insertInvoiceContent(invoiceVO);
//				}
//
//				if(record != null){
//					invoiceVO.setUserid(user.getUserid());
//					invoiceVO.setService_type(record.getService_type());
//					invoiceVO.setService_period(record.getService_period());
//					invoiceVO.setEnd_date(record.getEnd_date());
//					invoiceVO.setPay_state(record.getPay_state());
//					invoiceVO.setPay_way(record.getPay_way());
//					invoiceVO.setInvoice_idx(invoice_idx);
//					invoiceVO.setService("record");
//					invoiceVO.setSum(250000);
//					invoiceVO.setCeo_name(record.getCeo_name());
//					invoiceVO.setCorporate_address(record.getCorporate_address());
//					invoiceVO.setPublish_email(record.getPublish_email());
//					invoiceVO.setReg_date(record.getReg_date());
//					insertInvoiceContent(invoiceVO);
//				}
//			}
//			continue;
//		}
//
//		return invoiceVO.getInvoice_idx();
//	}
	
	public @ResponseBody Integer insertInvoiceByUserid(UserVO user) throws ParseException{
		UserInvoiceVO invoiceVO = new UserInvoiceVO();
		List<ServicePatchcallVO> patchcallList = userService.getPatchcallServiceListForInvoice(user.getUserid());
		List<ServicePatchcallOtherVO> otherList = userService.getPatchcallOtherserviceListForInvoice(user.getUserid());
		List<ServiceWebHostingVO> webList = userService.getHostingServiceListForInvoice(user.getUserid());
		CallbackSmsVO callback = userService.checkCallbackSmsServiceForInvoice(user.getUserid());
		RecordVO record = userService.selectRecordServiceListForInvoice(user.getUserid());
		int count = patchcallList.size() + otherList.size() + webList.size();
		if(callback != null){count ++;}
		if(count > 0){
			invoiceVO.setUserid(user.getUserid());
			insertInvoice(invoiceVO);
			Integer invoice_idx = invoiceVO.getInvoice_idx();			
			for(ServicePatchcallVO patch : patchcallList){
				invoiceVO.setUserid(user.getUserid());
				invoiceVO.setService_type(patch.getService_type());
				invoiceVO.setService_period(patch.getService_period());
				invoiceVO.setEnd_date(patch.getEnd_date());
				invoiceVO.setPay_state(patch.getPay_state());
				invoiceVO.setPay_way(patch.getPay_way());
				invoiceVO.setInvoice_idx(invoice_idx);
				invoiceVO.setService("patchcall");
				switch(patch.getService_type()){
				case "single" :
					invoiceVO.setSum(10000);
	    			break;
	        	case "support" :
	        		invoiceVO.setSum(100000);
	    			break;
	        	case "start-up" : 
	        		invoiceVO.setSum(150000);
	    			break;
	        	case "business" :
	        		invoiceVO.setSum(200000);
	    			break;
	        	case "enterprise" :
	        		invoiceVO.setSum(250000);
	    			break;
				};
				invoiceVO.setCeo_name(patch.getCeo_name());
				invoiceVO.setCorporate_address(patch.getCorporate_address());
				invoiceVO.setPublish_email(patch.getPublish_email());
				invoiceVO.setReg_date(patch.getReg_date());
				insertInvoiceContent(invoiceVO);
			}
			
			for(ServicePatchcallOtherVO other : otherList){
				invoiceVO.setUserid(user.getUserid());
				invoiceVO.setService_type(other.getService_type());
				invoiceVO.setService_period(other.getService_period());
				invoiceVO.setEnd_date(other.getEnd_date());
				invoiceVO.setPay_state(other.getPay_state());
				invoiceVO.setPay_way(other.getPay_way());
				invoiceVO.setInvoice_idx(invoice_idx);
				switch(other.getService_type()){
				case "number080" :
					invoiceVO.setService("number080");
	        		invoiceVO.setSum(30000);
	    			break;
	        	case "general_number" :
	        		invoiceVO.setService("general_number");
	        		invoiceVO.setSum(10000);
	    			break;
	        	case "short_url" :
	        		invoiceVO.setService("short_url");
	        		invoiceVO.setSum(10000);
	    			break;
				};
				invoiceVO.setCeo_name(other.getCeo_name());
				invoiceVO.setCorporate_address(other.getCorporate_address());
				invoiceVO.setPublish_email(other.getPublish_email());
				invoiceVO.setReg_date(other.getReg_date());
				insertInvoiceContent(invoiceVO);
			}
			
			for(ServiceWebHostingVO web : webList){
				invoiceVO.setUserid(user.getUserid());
				invoiceVO.setService_type(web.getService_type());
				invoiceVO.setService_period(web.getService_period());
				invoiceVO.setEnd_date(web.getEnd_date());
				invoiceVO.setPay_state(web.getPay_state());
				invoiceVO.setPay_way(web.getPay_way());
				invoiceVO.setInvoice_idx(invoice_idx);
				invoiceVO.setService("webhosting");
				switch(web.getService_type()){
				case "standard" :
	        		invoiceVO.setSum(35000);
	    			break;
	        	case "support_web" :
	        		invoiceVO.setSum(50000);
	    			break;
	        	case "premium" : 
	        		invoiceVO.setSum(150000);
	    			break;
				};
				invoiceVO.setCeo_name(web.getCeo_name());
				invoiceVO.setCorporate_address(web.getCorporate_address());
				invoiceVO.setPublish_email(web.getPublish_email());
				invoiceVO.setReg_date(web.getReg_date());
				insertInvoiceContent(invoiceVO);
			}
			if(callback != null){
				invoiceVO.setUserid(user.getUserid());
				invoiceVO.setService_type(callback.getService_type());
				invoiceVO.setService_period(callback.getService_period());
				invoiceVO.setEnd_date(callback.getEnd_date());
				invoiceVO.setPay_state(callback.getPay_state());
				invoiceVO.setPay_way(callback.getPay_way());
				invoiceVO.setInvoice_idx(invoice_idx);
				invoiceVO.setService("callback_sms");
				invoiceVO.setSum(10000);
				invoiceVO.setCeo_name(callback.getCeo_name());
				invoiceVO.setCorporate_address(callback.getCorporate_address());
				invoiceVO.setPublish_email(callback.getPublish_email());
				invoiceVO.setReg_date(callback.getReg_date());
				insertInvoiceContent(invoiceVO);
			}
			
			if(record != null){
				invoiceVO.setUserid(user.getUserid());
				invoiceVO.setService_type(record.getService_type());
				invoiceVO.setService_period(record.getService_period());
				invoiceVO.setEnd_date(record.getEnd_date());
				invoiceVO.setPay_state(record.getPay_state());
				invoiceVO.setPay_way(record.getPay_way());
				invoiceVO.setInvoice_idx(invoice_idx);
				invoiceVO.setService("record");
				invoiceVO.setSum(250000);
				invoiceVO.setCeo_name(record.getCeo_name());
				invoiceVO.setCorporate_address(record.getCorporate_address());
				invoiceVO.setPublish_email(record.getPublish_email());
				invoiceVO.setReg_date(record.getReg_date());
				insertInvoiceContent(invoiceVO);
			}
		}
		return 0;
	}
}
