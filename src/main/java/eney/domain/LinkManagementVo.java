package eney.domain;

public class LinkManagementVo {
	
	
	private  int management_idx;
	private  int management_tag_idx;
	private  int management_channel_idx;
	
	public int getManagement_idx() {
		return management_idx;
	}
	public void setManagement_idx(int management_idx) {
		this.management_idx = management_idx;
	}
	public int getManagement_tag_idx() {
		return management_tag_idx;
	}
	public void setManagement_tag_idx(int management_tag_idx) {
		this.management_tag_idx = management_tag_idx;
	}
	public int getManagement_channel_idx() {
		return management_channel_idx;
	}
	public void setManagement_channel_idx(int management_channel_idx) {
		this.management_channel_idx = management_channel_idx;
	}
	
	
	@Override
	public String toString() {
		return "LinkManagementVo [management_idx=" + management_idx + ", management_tag_idx=" + management_tag_idx
				+ ", management_channel_idx=" + management_channel_idx + "]";
	}
	
	
	

}