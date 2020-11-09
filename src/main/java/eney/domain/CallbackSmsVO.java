package eney.domain;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

public class CallbackSmsVO extends Pagination {
	private Integer idx;
	private String userid;
	private String service_type;
	private String service_period;
	private String pay_way;
	private String corporate_address;
	private String ceo_name;
	private String end_date;
	private PaymentLogVo.PayStatus pay_state;
	private String ani;
	private String callback_sms;
	private String vno;
	private String rcv_no;
	private String sms;
	private String sms_yn;
	private String out_sms;
	private String out_sms_yn;
	private String publish_email;
	private String reg_date;
	private List<MultipartFile> files;
	private List<FileVO> fileVO_list;
	
	private List<Integer> close_service;
	
	/*신규 교환기에 추가된 컬럼*/
	private String callback_no; //callback sms 발송시 발신번호
	private String out_sms_phone; //부재중 sms 착신 휴대폰번호
	
	private boolean pagination_flag;
	
	public String getAni() {
		return ani;
	}
	public void setAni(String ani) {
		this.ani = ani;
	}
	public int getIdx() {
		return idx;
	}
	public void setIdx(Integer idx) {
		this.idx = idx;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCallback_sms() {
		return callback_sms;
	}
	public void setCallback_sms(String callback_sms) {
		this.callback_sms = callback_sms;
	}
	public String getVno() {
		return vno;
	}
	public void setVno(String vno) {
		this.vno = vno;
	}
	public String getRcv_no() {
		return rcv_no;
	}
	public void setRcv_no(String rcv_no) {
		this.rcv_no = rcv_no;
	}
	public String getSms() {
		return sms;
	}
	public void setSms(String sms) {
		this.sms = sms;
	}
	public String getSms_yn() {
		return sms_yn;
	}
	public void setSms_yn(String sms_yn) {
		this.sms_yn = sms_yn;
	}
	
	public String getService_type() {
		return service_type;
	}
	public void setService_type(String service_type) {
		this.service_type = service_type;
	}
	public String getService_period() {
		return service_period;
	}
	public void setService_period(String service_period) {
		this.service_period = service_period;
	}
	public String getPay_way() {
		return pay_way;
	}
	public void setPay_way(String pay_way) {
		this.pay_way = pay_way;
	}
	public String getCorporate_address() {
		return corporate_address;
	}
	public void setCorporate_address(String corporate_address) {
		this.corporate_address = corporate_address;
	}
	public String getCeo_name() {
		return ceo_name;
	}
	public void setCeo_name(String ceo_name) {
		this.ceo_name = ceo_name;
	}
	public String getEnd_date() {
		return end_date;
	}
	public void setEnd_date(String end_date) {
		this.end_date = end_date;
	}
	public PaymentLogVo.PayStatus getPay_state() {
		return pay_state;
	}
	public void setPay_state(PaymentLogVo.PayStatus pay_state) {
		this.pay_state = pay_state;
	}
	public String getOut_sms() {
		return out_sms;
	}
	public void setOut_sms(String out_sms) {
		this.out_sms = out_sms;
	}
	public String getOut_sms_yn() {
		return out_sms_yn;
	}
	public void setOut_sms_yn(String out_sms_yn) {
		this.out_sms_yn = out_sms_yn;
	}
	public String getCallback_no() {
		return callback_no;
	}
	public void setCallback_no(String callback_no) {
		this.callback_no = callback_no;
	}
	public String getOut_sms_phone() {
		return out_sms_phone;
	}
	public void setOut_sms_phone(String out_sms_phone) {
		this.out_sms_phone = out_sms_phone;
	}
	public String getPublish_email() {
		return publish_email;
	}
	public void setPublish_email(String publish_email) {
		this.publish_email = publish_email;
	}
	
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public List<MultipartFile> getFiles() {
		return files;
	}
	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
	public List<FileVO> getFileVO_list() {
		return fileVO_list;
	}
	public void setFileVO_list(List<FileVO> fileVO_list) {
		this.fileVO_list = fileVO_list;
	}
	public boolean isPagination_flag() {
		return pagination_flag;
	}
	public void setPagination_flag(boolean pagination_flag) {
		this.pagination_flag = pagination_flag;
	}
	
	public List<Integer> getClose_service() {
		return close_service;
	}
	public void setClose_service(List<Integer> close_service) {
		this.close_service = close_service;
	}
	@Override
	public String toString() {
		return "CallbackSmsVO [idx=" + idx + ", userid=" + userid + ", service_type=" + service_type
				+ ", service_period=" + service_period + ", pay_way=" + pay_way + ", corporate_address="
				+ corporate_address + ", ceo_name=" + ceo_name + ", end_date=" + end_date + ", pay_state=" + pay_state
				+ ", ani=" + ani + ", callback_sms=" + callback_sms + ", vno=" + vno + ", rcv_no=" + rcv_no + ", sms="
				+ sms + ", sms_yn=" + sms_yn + ", out_sms=" + out_sms + ", out_sms_yn=" + out_sms_yn
				+ ", publish_email=" + publish_email + ", reg_date=" + reg_date + ", files=" + files + ", fileVO_list="
				+ fileVO_list + ", callback_no=" + callback_no + ", out_sms_phone=" + out_sms_phone
				+ ", pagination_flag=" + pagination_flag + "]";
	}
}
