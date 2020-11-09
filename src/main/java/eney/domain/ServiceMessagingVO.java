package eney.domain;

public class ServiceMessagingVO {

	private Integer idx;
	private String userid;
	private String ip;
	private String reg_date;
	
	public Integer getIdx() {
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
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	
	@Override
	public String toString() {
		return "ServiceMessagingVO [idx=" + idx + ", userid=" + userid + ", ip=" + ip + ", reg_date=" + reg_date + "]";
	}
}
