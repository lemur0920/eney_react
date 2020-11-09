package eney.domain;

public class ServiceServerHostingVO {
	
	private Integer server_idx;
	private String userid;
	private String username;	
	private String server_domain;
	private String server_ip_address;
	private String server_root_id;
	private String server_root_pw;
	private String server_type;
	private String server_fw;
	private String note;
	public Integer getServer_idx() {
		return server_idx;
	}
	public void setServer_idx(Integer server_idx) {
		this.server_idx = server_idx;
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
	public String getServer_domain() {
		return server_domain;
	}
	public void setServer_domain(String server_domain) {
		this.server_domain = server_domain;
	}
	public String getServer_ip_address() {
		return server_ip_address;
	}
	public void setServer_ip_address(String server_ip_address) {
		this.server_ip_address = server_ip_address;
	}
	public String getServer_root_id() {
		return server_root_id;
	}
	public void setServer_root_id(String server_root_id) {
		this.server_root_id = server_root_id;
	}
	public String getServer_root_pw() {
		return server_root_pw;
	}
	public void setServer_root_pw(String server_root_pw) {
		this.server_root_pw = server_root_pw;
	}
	public String getServer_type() {
		return server_type;
	}
	public void setServer_type(String server_type) {
		this.server_type = server_type;
	}
	public String getServer_fw() {
		return server_fw;
	}
	public void setServer_fw(String server_fw) {
		this.server_fw = server_fw;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	
	@Override
	public String toString() {
		return "ServiceServerHostingVO [server_idx=" + server_idx + ", userid=" + userid + ", username=" + username
				+ ", server_domain=" + server_domain + ", server_ip_address=" + server_ip_address + ", server_root_id="
				+ server_root_id + ", server_root_pw=" + server_root_pw + ", server_type=" + server_type
				+ ", server_fw=" + server_fw + ", note=" + note + "]";
	}
}
