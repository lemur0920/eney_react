package eney.domain;

import java.util.Date;

public class LinkInfoVo {
	public static final Integer LINK_TYPE_LINK = 1;
	public static final Integer LINK_TYPE_PHONE = 2;
	public static final Integer LINK_TYPE_CLICK_TO_CALL = 3;
	public static final Integer LINK_TYPE_LANDING_PAGE = 4;
	public static final Integer LINK_TYPE_IMG = 5;
	
	private Integer link_idx;
	private String link_url;
	private String link_key;
	private String link_category;
	private String link_userid;
	private Date timestamp;
	private Integer link_type;
	private Boolean link_delete;
	
	public Integer getLink_idx() {
		return link_idx;
	}
	public void setLink_idx(Integer link_idx) {
		this.link_idx = link_idx;
	}
	public String getLink_url() {
		return link_url;
	}
	public void setLink_url(String link_url) {
		this.link_url = link_url;
	}
	public String getLink_key() {
		return link_key;
	}
	public void setLink_key(String link_key) {
		this.link_key = link_key;
	}
	public String getLink_category() {
		return link_category;
	}
	public void setLink_category(String link_category) {
		this.link_category = link_category;
	}
	public String getLink_userid() {
		return link_userid;
	}
	public void setLink_userid(String link_userid) {
		this.link_userid = link_userid;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public Integer getLink_type() {
		return link_type;
	}
	public void setLink_type(Integer link_type) {
		this.link_type = link_type;
	}
	public Boolean getLink_delete() {
		return link_delete;
	}
	public void setLink_delete(Boolean link_delete) {
		this.link_delete = link_delete;
	}
	@Override
	public String toString() {
		return "LinkInfoVo [link_idx=" + link_idx + ", link_url=" + link_url + ", link_key=" + link_key
				+ ", link_userid=" + link_userid + ", timestamp=" + timestamp + ", link_type=" + link_type
				+ ", link_delete=" + link_delete + "]";
	}
}
