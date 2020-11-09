package eney.api.v1.domain;

public class ApiIpWhitelistVo {
	private int iw_idx;
	private int iw_token_idx;
	private int iw_ipaddress;
	
	public int getIw_idx() {
		return iw_idx;
	}
	public void setIw_idx(int iw_idx) {
		this.iw_idx = iw_idx;
	}
	public int getIw_token_idx() {
		return iw_token_idx;
	}
	public void setIw_token_idx(int iw_token_idx) {
		this.iw_token_idx = iw_token_idx;
	}
	public int getIw_ipaddress() {
		return iw_ipaddress;
	}
	public void setIw_ipaddress(int iw_ipaddress) {
		this.iw_ipaddress = iw_ipaddress;
	}
	
	@Override
	public String toString() {
		return "ApiIpWhitelistVo [iw_idx=" + iw_idx + ", iw_token_idx=" + iw_token_idx + ", iw_ipaddress="
				+ iw_ipaddress + "]";
	}
}
