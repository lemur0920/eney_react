package eney.api.v1.domain;

import java.sql.Date;

public class ApiLogVo {
	public static final String CATE_VNO = "vno";
	public static final String CATE_CALLLOG = "calllog";
	public static final String CATE_IVR_STAT = "ivr_stat";
	public static final String CATE_SEND_MESSAGE = "send_message";
	
	private int log_token_idx;
	private String log_category;
	private int log_statuscode;
	private String log_tokenkey;
	private String log_userid;
	private String log_event;
	private String log_ipaddress;
	private long log_elapsed_time;
	private Date log_date;
	
	public ApiLogVo(String log_category, int log_statuscode, String log_tokenkey, String log_userid, String log_event, String log_ipaddress, long log_elapsed_time){
		this.log_category = log_category;
		this.log_statuscode = log_statuscode;
		this.log_tokenkey = log_tokenkey;
		this.log_userid = log_userid;
		this.log_event = log_event;
		this.log_ipaddress = log_ipaddress;
		this.log_elapsed_time = log_elapsed_time;
	}
	
	public int getLog_token_idx() {
		return log_token_idx;
	}
	public void setLog_token_idx(int log_token_idx) {
		this.log_token_idx = log_token_idx;
	}
	public String getLog_category() {
		return log_category;
	}
	public void setLog_category(String log_category) {
		this.log_category = log_category;
	}
	public int getLog_statuscode() {
		return log_statuscode;
	}
	public void setLog_statuscode(int log_statuscode) {
		this.log_statuscode = log_statuscode;
	}
	public String getLog_tokenkey() {
		return log_tokenkey;
	}
	public void setLog_tokenkey(String log_tokenkey) {
		this.log_tokenkey = log_tokenkey;
	}
	public String getLog_userid() {
		return log_userid;
	}
	public void setLog_userid(String log_userid) {
		this.log_userid = log_userid;
	}
	public String getLog_event() {
		return log_event;
	}
	public void setLog_event(String log_event) {
		this.log_event = log_event;
	}
	public String getLog_ipaddress() {
		return log_ipaddress;
	}
	public void setLog_ipaddress(String log_ipaddress) {
		this.log_ipaddress = log_ipaddress;
	}
	public long getLog_elapsed_time() {
		return log_elapsed_time;
	}
	public void setLog_elapsed_time(long log_elapsed_time) {
		this.log_elapsed_time = log_elapsed_time;
	}
	public Date getLog_date() {
		return log_date;
	}
	public void setLog_date(Date log_date) {
		this.log_date = log_date;
	}
	
	@Override
	public String toString() {
		return "ApiLogVo [log_token_idx=" + log_token_idx + ", log_category=" + log_category + ", log_statuscode="
				+ log_statuscode + ", log_tokenkey=" + log_tokenkey + ", log_userid=" + log_userid + ", log_event="
				+ log_event + ", log_ipaddress=" + log_ipaddress + ", log_elapsed_time=" + log_elapsed_time
				+ ", log_date=" + log_date + "]";
	}	
}
