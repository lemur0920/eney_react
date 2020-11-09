package eney.domain;

public class UserInvoiceVO {
	private Integer idx;
	private Integer invoice_idx;
	private String userid;
	private String invoice_service_name;
	private String invoice_issue_date;
	private String invoice_number;

	private String content_patchcall;	
	private String content_patchcall_other;
	private String content_hosting_web;
	private String content_callback;
	private int file_id;
	
	private String service_type;
	private String service_period;
	private String end_date;
	private PaymentLogVo.PayStatus pay_state;
	private String pay_way;
	private Integer sum;
	private String service;
	
	private String corporate_address;
	private String ceo_name;
	private String publish_email;
	private String reg_date;
	
	private String generate_yn;
	private Integer hometax_idx;
	private String pay_history;
	public Integer getIdx() {
		return idx;
	}
	public void setIdx(Integer idx) {
		this.idx = idx;
	}
	public Integer getInvoice_idx() {
		return invoice_idx;
	}
	public void setInvoice_idx(Integer invoice_idx) {
		this.invoice_idx = invoice_idx;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getInvoice_service_name() {
		return invoice_service_name;
	}
	public void setInvoice_service_name(String invoice_service_name) {
		this.invoice_service_name = invoice_service_name;
	}
	public String getInvoice_issue_date() {
		return invoice_issue_date;
	}
	public void setInvoice_issue_date(String invoice_issue_date) {
		this.invoice_issue_date = invoice_issue_date;
	}
	public String getInvoice_number() {
		return invoice_number;
	}
	public void setInvoice_number(String invoice_number) {
		this.invoice_number = invoice_number;
	}
	public String getContent_patchcall() {
		return content_patchcall;
	}
	public void setContent_patchcall(String content_patchcall) {
		this.content_patchcall = content_patchcall;
	}
	public String getContent_patchcall_other() {
		return content_patchcall_other;
	}
	public void setContent_patchcall_other(String content_patchcall_other) {
		this.content_patchcall_other = content_patchcall_other;
	}
	public String getContent_hosting_web() {
		return content_hosting_web;
	}
	public void setContent_hosting_web(String content_hosting_web) {
		this.content_hosting_web = content_hosting_web;
	}
	public String getContent_callback() {
		return content_callback;
	}
	public void setContent_callback(String content_callback) {
		this.content_callback = content_callback;
	}
	public int getFile_id() {
		return file_id;
	}
	public void setFile_id(int file_id) {
		this.file_id = file_id;
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
	public String getPay_way() {
		return pay_way;
	}
	public void setPay_way(String pay_way) {
		this.pay_way = pay_way;
	}
	public Integer getSum() {
		return sum;
	}
	public void setSum(Integer sum) {
		this.sum = sum;
	}
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
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
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getGenerate_yn() {
		return generate_yn;
	}
	public void setGenerate_yn(String generate_yn) {
		this.generate_yn = generate_yn;
	}
	public Integer getHometax_idx() {
		return hometax_idx;
	}
	public void setHometax_idx(Integer hometax_idx) {
		this.hometax_idx = hometax_idx;
	}
	public String getPay_history() {
		return pay_history;
	}
	public void setPay_history(String pay_history) {
		this.pay_history = pay_history;
	}
	@Override
	public String toString() {
		return "UserInvoiceVO [idx=" + idx + ", invoice_idx=" + invoice_idx + ", userid=" + userid
				+ ", invoice_service_name=" + invoice_service_name + ", invoice_issue_date=" + invoice_issue_date
				+ ", invoice_number=" + invoice_number + ", content_patchcall=" + content_patchcall
				+ ", content_patchcall_other=" + content_patchcall_other + ", content_hosting_web="
				+ content_hosting_web + ", content_callback=" + content_callback + ", file_id=" + file_id
				+ ", service_type=" + service_type + ", service_period=" + service_period + ", end_date=" + end_date
				+ ", pay_state=" + pay_state + ", pay_way=" + pay_way + ", sum=" + sum + ", service=" + service
				+ ", corporate_address=" + corporate_address + ", ceo_name=" + ceo_name + ", publish_email="
				+ publish_email + ", reg_date=" + reg_date + ", generate_yn=" + generate_yn + ", hometax_idx="
				+ hometax_idx + ", pay_history=" + pay_history + "]";
	}
}
