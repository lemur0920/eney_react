package eney.domain;

import java.util.Date;

public class LinkLogVo {
	private Integer link_log_idx;
	private Integer link_idx;
	private String link_log_referrer_url;
	private String link_log_referrer_host;
	private String link_log_referrer_search_query;
	private Integer link_channel_idx;
	private String link_log_ip;
	private String link_log_agent;
	private Date link_log_timestamp;
	
	public Integer getLink_log_idx() {
		return link_log_idx;
	}
	public void setLink_log_idx(Integer link_log_idx) {
		this.link_log_idx = link_log_idx;
	}
	public Integer getLink_idx() {
		return link_idx;
	}
	public void setLink_idx(Integer link_idx) {
		this.link_idx = link_idx;
	}
	public String getLink_log_referrer_url() {
		return link_log_referrer_url;
	}
	public void setLink_log_referrer_url(String link_log_referrer_url) {
		this.link_log_referrer_url = link_log_referrer_url;
	}
	public String getLink_log_referrer_host() {
		return link_log_referrer_host;
	}
	public void setLink_log_referrer_host(String link_log_referrer_host) {
		this.link_log_referrer_host = link_log_referrer_host;
	}
	public String getLink_log_referrer_search_query() {
		return link_log_referrer_search_query;
	}
	public void setLink_log_referrer_search_query(String link_log_referrer_search_query) {
		this.link_log_referrer_search_query = link_log_referrer_search_query;
	}
	public Integer getLink_channel_idx() {
		return link_channel_idx;
	}
	public void setLink_channel_idx(Integer link_channel_idx) {
		this.link_channel_idx = link_channel_idx;
	}
	public String getLink_log_ip() {
		return link_log_ip;
	}
	public void setLink_log_ip(String link_log_ip) {
		this.link_log_ip = link_log_ip;
	}
	public String getLink_log_agent() {
		return link_log_agent;
	}
	public void setLink_log_agent(String link_log_agent) {
		this.link_log_agent = link_log_agent;
	}
	public Date getLink_log_timestamp() {
		return link_log_timestamp;
	}
	public void setLink_log_timestamp(Date link_log_timestamp) {
		this.link_log_timestamp = link_log_timestamp;
	}
	@Override
	public String toString() {
		return "LinkLogVo [link_log_idx=" + link_log_idx + ", link_idx=" + link_idx + ", link_log_referrer_url="
				+ link_log_referrer_url + ", link_log_referrer_host=" + link_log_referrer_host
				+ ", link_log_referrer_search_query=" + link_log_referrer_search_query + ", link_channel_idx="
				+ link_channel_idx + ", link_log_ip=" + link_log_ip + ", link_log_agent=" + link_log_agent
				+ ", link_log_timestamp=" + link_log_timestamp + "]";
	}
}
