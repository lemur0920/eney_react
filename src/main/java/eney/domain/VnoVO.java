package eney.domain;

public class VnoVO {
	private int idx;
	private String dn;
	private String vno;
	private String reg_date;
	private String reg_gubun;
	private String login_id;
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getDn() {
		return dn;
	}
	public void setDn(String dn) {
		this.dn = dn;
	}
	public String getVno() {
		return vno;
	}
	public void setVno(String vno) {
		this.vno = vno;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getReg_gubun() {
		return reg_gubun;
	}
	public void setReg_gubun(String reg_gubun) {
		this.reg_gubun = reg_gubun;
	}
	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	@Override
	public String toString() {
		return "VnoVO [idx=" + idx + ", dn=" + dn + ", vno=" + vno
				+ ", reg_date=" + reg_date + ", reg_gubun=" + reg_gubun
				+ ", login_id=" + login_id + "]";
	}
}
