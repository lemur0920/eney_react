package eney.domain;

public class ServiceVpnHostingVO extends Pagination{
	
	public enum Status {using,delete};
	
	private int vpn_idx;
	private String userid;
	private String username;
	private String vpn_install_address;
	private String vpn_ip_address;
	private String reg_date;
	private int service_type;
	private int service_amount;
	private int service_vat;	
	private String service_period;	
	private String corporate_address;	
	private String ceo_name;
	private String publish_email;
	private String pay_way;
	private String end_date;
	private PaymentLogVo.PayStatus pay_state;
	private String generate_yn;
	private Status status;
	private String note;
	public int getVpn_idx() {
		return vpn_idx;
	}
	public void setVpn_idx(int vpn_idx) {
		this.vpn_idx = vpn_idx;
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
	public String getVpn_install_address() {
		return vpn_install_address;
	}
	public void setVpn_install_address(String vpn_install_address) {
		this.vpn_install_address = vpn_install_address;
	}
	public String getVpn_ip_address() {
		return vpn_ip_address;
	}
	public void setVpn_ip_address(String vpn_ip_address) {
		this.vpn_ip_address = vpn_ip_address;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public int getService_type() {
		return service_type;
	}
	public void setService_type(int service_type) {
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
	public String getPay_way() {
		return pay_way;
	}
	public void setPay_way(String pay_way) {
		this.pay_way = pay_way;
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
	public Status getStatus() {
		return status;
	}
	public void setStatus(Status status) {
		this.status = status;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	@Override
	public String toString() {
		return "ServiceVpnHostingVO [vpn_idx=" + vpn_idx + ", userid=" + userid + ", username=" + username
				+ ", vpn_install_address=" + vpn_install_address + ", vpn_ip_address=" + vpn_ip_address + ", reg_date="
				+ reg_date + ", service_type=" + service_type + ", service_amount=" + service_amount + ", service_vat="
				+ service_vat + ", service_period=" + service_period + ", corporate_address=" + corporate_address
				+ ", ceo_name=" + ceo_name + ", publish_email=" + publish_email + ", pay_way=" + pay_way + ", end_date="
				+ end_date + ", pay_state=" + pay_state + ", generate_yn=" + generate_yn + ", status=" + status
				+ ", note=" + note + "]";
	}
}
