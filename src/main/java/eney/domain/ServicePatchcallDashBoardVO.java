package eney.domain;

import eney.domain.PaymentLogVo.PayStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class ServicePatchcallDashBoardVO extends Pagination{
	private int idx;
	private String userid;
	private String username;
	private String service_type;
	private int service_amount;
	private int service_vat;
	private String p_service;
	private String expect_number;
	private String service_period;
	private String pay_way;
	private String corporate_address;
	private String ceo_name;
	private String end_date;
	private PayStatus pay_state;
	private String generate_yn;
	private String message_type;
	private String sms;
	private String publish_email;
	private String reg_date;
	private String note;


	private List<MultipartFile> files;
	private List<FileVO> fileVO_list;

	private List<Integer> close_service;

	private boolean pagination_flag;

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getService_type() {
		return service_type;
	}

	public void setService_type(String service_type) {
		this.service_type = service_type;
	}

	public int getService_amount() {
		return service_amount;
	}

	public void setService_amount(int service_amount) {
		this.service_amount = service_amount;
	}

	public int getService_vat() {
		return service_vat;
	}

	public void setService_vat(int service_vat) {
		this.service_vat = service_vat;
	}

	public String getP_service() {
		return p_service;
	}

	public void setP_service(String p_service) {
		this.p_service = p_service;
	}

	public String getExpect_number() {
		return expect_number;
	}

	public void setExpect_number(String expect_number) {
		this.expect_number = expect_number;
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

	public PayStatus getPay_state() {
		return pay_state;
	}

	public void setPay_state(PayStatus pay_state) {
		this.pay_state = pay_state;
	}

	public String getGenerate_yn() {
		return generate_yn;
	}

	public void setGenerate_yn(String generate_yn) {
		this.generate_yn = generate_yn;
	}

	public String getMessage_type() {
		return message_type;
	}

	public void setMessage_type(String message_type) {
		this.message_type = message_type;
	}

	public String getSms() {
		return sms;
	}

	public void setSms(String sms) {
		this.sms = sms;
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

	public String getNote() {
		return note;
	}

	public void setNote(String note) {
		this.note = note;
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

	public List<Integer> getClose_service() {
		return close_service;
	}

	public void setClose_service(List<Integer> close_service) {
		this.close_service = close_service;
	}

	public boolean isPagination_flag() {
		return pagination_flag;
	}

	public void setPagination_flag(boolean pagination_flag) {
		this.pagination_flag = pagination_flag;
	}

	@Override
	public String toString() {
		return "ServicePatchcallOtherVO [idx=" + idx + ", userid=" + userid + ", username=" + username
				+ ", service_type=" + service_type + ", service_amount=" + service_amount + ", service_vat="
				+ service_vat + ", p_service=" + p_service + ", expect_number=" + expect_number + ", service_period="
				+ service_period + ", pay_way=" + pay_way + ", corporate_address=" + corporate_address + ", ceo_name="
				+ ceo_name + ", end_date=" + end_date + ", pay_state=" + pay_state + ", generate_yn=" + generate_yn
				+ ", message_type=" + message_type + ", sms=" + sms + ", publish_email=" + publish_email + ", reg_date="
				+ reg_date + ", note=" + note + ", files=" + files + ", fileVO_list=" + fileVO_list + ", close_service="
				+ close_service + ", pagination_flag=" + pagination_flag + "]";
	}
}
