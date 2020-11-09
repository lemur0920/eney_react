package eney.domain;

import java.util.Date;

public class LinkChannelVo {
	public static final String LINK_CHANNEL_REF_CATEGORY_ADPOWER_REQUEST = "ADPOWER_REQUEST";
	public static final String LINK_CHANNEL_REF_CATEGORY_USER_MAKE		 = "USER_MAKE";
	
	private Integer link_channel_idx;
	private Integer link_idx;
	private String link_channel_name;
	private String link_channel_ref_category;
	private Integer link_channel_ref_idx;
	private String link_channel_userid;
	private Date link_channel_timestamp;
	private Boolean link_channel_delete;
	
	public Integer getLink_channel_idx() {
		return link_channel_idx;
	}
	public void setLink_channel_idx(Integer link_channel_idx) {
		this.link_channel_idx = link_channel_idx;
	}
	public Integer getLink_idx() {
		return link_idx;
	}
	public void setLink_idx(Integer link_idx) {
		this.link_idx = link_idx;
	}
	public String getLink_channel_name() {
		return link_channel_name;
	}
	public void setLink_channel_name(String link_channel_name) {
		this.link_channel_name = link_channel_name;
	}
	public String getLink_channel_ref_category() {
		return link_channel_ref_category;
	}
	public void setLink_channel_ref_category(String link_channel_ref_category) {
		this.link_channel_ref_category = link_channel_ref_category;
	}
	public Integer getLink_channel_ref_idx() {
		return link_channel_ref_idx;
	}
	public void setLink_channel_ref_idx(Integer link_channel_ref_idx) {
		this.link_channel_ref_idx = link_channel_ref_idx;
	}
	public String getLink_channel_userid() {
		return link_channel_userid;
	}
	public void setLink_channel_userid(String link_channel_userid) {
		this.link_channel_userid = link_channel_userid;
	}
	public Date getLink_channel_timestamp() {
		return link_channel_timestamp;
	}
	public void setLink_channel_timestamp(Date link_channel_timestamp) {
		this.link_channel_timestamp = link_channel_timestamp;
	}
	public Boolean getLink_channel_delete() {
		return link_channel_delete;
	}
	public void setLink_channel_delete(Boolean link_channel_delete) {
		this.link_channel_delete = link_channel_delete;
	}
	
	@Override
	public String toString() {
		return "LinkChannelVo [link_channel_idx=" + link_channel_idx + ", link_idx=" + link_idx + ", link_channel_name="
				+ link_channel_name + ", link_channel_ref_category=" + link_channel_ref_category
				+ ", link_channel_ref_idx=" + link_channel_ref_idx + ", link_channel_userid=" + link_channel_userid
				+ ", link_channel_timestamp=" + link_channel_timestamp + ", link_channel_delete=" + link_channel_delete
				+ "]";
	}
}
