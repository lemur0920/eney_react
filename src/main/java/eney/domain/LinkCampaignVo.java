package eney.domain;

import java.util.Date;

public class LinkCampaignVo {
	
	private Integer campaign_idx;
	private String campaign_title;
	private String campaign_description;
	private String campaign_userid;
	private Date campaign_created_time;
	private String campaign_vno;
	private Boolean campaign_deleted;
	
	public Integer getCampaign_idx() {
		return campaign_idx;
	}
	public void setCampaign_idx(Integer campaign_idx) {
		this.campaign_idx = campaign_idx;
	}
	public String getCampaign_title() {
		return campaign_title;
	}
	public void setCampaign_title(String campaign_title) {
		this.campaign_title = campaign_title;
	}
	public String getCampaign_description() {
		return campaign_description;
	}
	public void setCampaign_description(String campaign_description) {
		this.campaign_description = campaign_description;
	}
	public String getCampaign_userid() {
		return campaign_userid;
	}
	public void setCampaign_userid(String campaign_userid) {
		this.campaign_userid = campaign_userid;
	}
	public Date getCampaign_created_time() {
		return campaign_created_time;
	}
	public void setCampaign_created_time(Date campaign_created_time) {
		this.campaign_created_time = campaign_created_time;
	}
	public String getCampaign_vno() {
		return campaign_vno;
	}
	public void setCampaign_vno(String campaign_vno) {
		this.campaign_vno = campaign_vno;
	}
	public Boolean getCampaign_deleted() {
		return campaign_deleted;
	}
	public void setCampaign_deleted(Boolean campaign_deleted) {
		this.campaign_deleted = campaign_deleted;
	}
	
	@Override
	public String toString() {
		return "LinkCampaignVo [campaign_idx=" + campaign_idx + ", campaign_title=" + campaign_title
				+ ", campaign_description=" + campaign_description + ", campaign_userid=" + campaign_userid
				+ ", campaign_created_time=" + campaign_created_time + ", campaign_vno=" + campaign_vno
				+ ", campaign_deleted=" + campaign_deleted + "]";
	}
}