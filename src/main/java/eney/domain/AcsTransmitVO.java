package eney.domain;

public class AcsTransmitVO extends PageVO{
	
	private String idx;
	private String mobile;
	private String auth_code;
	private String date;
	private String userid;
	private String callback;
	private String method;
	private String status;
	private String reg_date;
	private String change_date;
	private String req_num;
	private String result;
	private String tel_num;
	
	private String code;
	private String company_id;
	private String length;
	private String mode;
	private String call_back_num;
	private String auth_num;
	private String server_ip;
	private String server_port;
	
	private boolean pagination_flag;

	public String getIdx() {
		return idx;
	}
	public void setIdx(String idx) {
		this.idx = idx;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getAuth_code() {
		return auth_code;
	}
	public void setAuth_code(String auth_code) {
		this.auth_code = auth_code;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getCallback() {
		return callback;
	}
	public void setCallback(String callback) {
		this.callback = callback;
	}
	public String getMethod() {
		return method;
	}
	public void setMethod(String method) {
		this.method = method;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getChange_date() {
		return change_date;
	}
	public void setChange_date(String change_date) {
		this.change_date = change_date;
	}
	public String getReq_num() {
		return req_num;
	}
	public void setReq_num(String req_num) {
		this.req_num = req_num;
	}
	public String getResult() {
		return result;
	}
	public void setResult(String result) {
		this.result = result;
	}
	public String getTel_num() {
		return tel_num;
	}
	public void setTel_num(String tel_num) {
		this.tel_num = tel_num;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getCompany_id() {
		return company_id;
	}
	public void setCompany_id(String company_id) {
		this.company_id = company_id;
	}
	public String getLength() {
		return length;
	}
	public void setLength(String length) {
		this.length = length;
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getCall_back_num() {
		return call_back_num;
	}
	public void setCall_back_num(String call_back_num) {
		this.call_back_num = call_back_num;
	}
	public String getAuth_num() {
		return auth_num;
	}
	public void setAuth_num(String auth_num) {
		this.auth_num = auth_num;
	}
	public String getServer_ip() {
		return server_ip;
	}
	public void setServer_ip(String server_ip) {
		this.server_ip = server_ip;
	}
	public String getServer_port() {
		return server_port;
	}
	public void setServer_port(String server_port) {
		this.server_port = server_port;
	}
	
	public boolean isPagination_flag() {
		return pagination_flag;
	}
	public void setPagination_flag(boolean pagination_flag) {
		this.pagination_flag = pagination_flag;
	}
	@Override
	public String toString() {
		return "AcsTransmitVO [idx=" + idx + ", mobile=" + mobile + ", auth_code=" + auth_code + ", date=" + date
				+ ", userid=" + userid + ", callback=" + callback + ", method=" + method + ", status=" + status
				+ ", reg_date=" + reg_date + ", change_date=" + change_date + ", req_num=" + req_num + ", result="
				+ result + ", tel_num=" + tel_num + ", code=" + code + ", company_id=" + company_id + ", length="
				+ length + ", mode=" + mode + ", call_back_no=" + call_back_num + ", auth_num=" + auth_num
				+ ", server_ip=" + server_ip + ", server_port=" + server_port + "]";
	}
	
	
	
}
