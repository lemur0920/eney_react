package eney.domain;

import java.util.Date;

public class LinkTagVo {
	
	private Integer tag_idx;
	private String tag_title;
	private String tag_description;
	private String tag_userid;
	private Date tag_created_time;
	private String tag_vno;
	private Boolean tag_deleted;
	
	public Integer getTag_idx() {
		return tag_idx;
	}



	public void setTag_idx(Integer tag_idx) {
		this.tag_idx = tag_idx;
	}



	public String getTag_title() {
		return tag_title;
	}



	public void setTag_title(String tag_title) {
		this.tag_title = tag_title;
	}



	public String getTag_description() {
		return tag_description;
	}



	public void setTag_description(String tag_description) {
		this.tag_description = tag_description;
	}



	public String getTag_userid() {
		return tag_userid;
	}



	public void setTag_userid(String tag_userid) {
		this.tag_userid = tag_userid;
	}



	public Date getTag_created_time() {
		return tag_created_time;
	}



	public void setTag_created_time(Date tag_created_time) {
		this.tag_created_time = tag_created_time;
	}



	public String getTag_vno() {
		return tag_vno;
	}



	public void setTag_vno(String tag_vno) {
		this.tag_vno = tag_vno;
	}



	public Boolean getTag_deleted() {
		return tag_deleted;
	}



	public void setTag_deleted(Boolean tag_deleted) {
		this.tag_deleted = tag_deleted;
	}



	@Override
	public String toString() {
		return "LinktagVo [tag_idx=" + tag_idx + ", tag_title=" + tag_title
				+ ", tag_description=" + tag_description + ", tag_userid=" + tag_userid
				+ ", tag_created_time=" + tag_created_time + ", tag_vno=" + tag_vno
				+ ", tag_deleted=" + tag_deleted + "]";
	}
}