package eney.domain;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import eney.domain.PaymentLogVo.PayStatus;

public class ServiceListVO extends PageVO{
	
	private String userid;
	private String service_type;
	private int service_amount;
	private int service_vat;
	private String service_period;
	private String pay_way;
	private String corporate_address;
	private String ceo_name;
	private String publish_email;
	private String end_date;
	private PaymentLogVo.PayStatus pay_state;
	private String generate_yn;
	private String reg_date;
	private String status;
	private String note;
	
	private List<MultipartFile> files;
	private List<FileVO> fileVO_list;
	private boolean pagination_flag;
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
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
	public String getPublish_email() {
		return publish_email;
	}
	public void setPublish_email(String publish_email) {
		this.publish_email = publish_email;
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
	public String getGenerate_yn() {
		return generate_yn;
	}
	public void setGenerate_yn(String generate_yn) {
		this.generate_yn = generate_yn;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public boolean isPagination_flag() {
		return pagination_flag;
	}
	public void setPagination_flag(boolean pagination_flag) {
		this.pagination_flag = pagination_flag;
	}
	
	@Override
	public String toString() {
		return "ServiceListVO [userid=" + userid + ", service_type=" + service_type + ", service_amount="
				+ service_amount + ", service_vat=" + service_vat + ", service_period=" + service_period + ", pay_way="
				+ pay_way + ", corporate_address=" + corporate_address + ", ceo_name=" + ceo_name + ", publish_email="
				+ publish_email + ", end_date=" + end_date + ", pay_state=" + pay_state + ", generate_yn=" + generate_yn
				+ ", reg_date=" + reg_date + ", status=" + status + ", note=" + note + ", files=" + files
				+ ", fileVO_list=" + fileVO_list + ", pagination_flag=" + pagination_flag + "]";
	}

}
