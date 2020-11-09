package eney.domain;

public class UserEventVO extends Pagination{
	
	public enum TicketStatus {접수,처리중,상담완료};

	private int idx;
	private String userid;
	private String event_date;
	private String event_time;
	private String event_charger;
	private String event_content;
	private String ticket_number;
	private String ticket_date;
	private TicketStatus ticket_status;
	private String userChatId;
	
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getEvent_date() {
		return event_date;
	}
	public void setEvent_date(String event_date) {
		this.event_date = event_date;
	}
	public String getEvent_time() {
		return event_time;
	}
	public void setEvent_time(String event_time) {
		this.event_time = event_time;
	}
	public String getEvent_charger() {
		return event_charger;
	}
	public void setEvent_charger(String event_charger) {
		this.event_charger = event_charger;
	}
	public String getEvent_content() {
		return event_content;
	}
	public void setEvent_content(String event_content) {
		this.event_content = event_content;
	}
	public String getTicket_number() {
		return ticket_number;
	}
	public void setTicket_number(String ticket_number) {
		this.ticket_number = ticket_number;
	}
	public String getTicket_date() {
		return ticket_date;
	}
	public void setTicket_date(String ticket_date) {
		this.ticket_date = ticket_date;
	}
	public TicketStatus getTicket_status() {
		return ticket_status;
	}
	public void setTicket_status(TicketStatus ticket_status) {
		this.ticket_status = ticket_status;
	}
	
	public String getUserChatId() {
		return userChatId;
	}
	public void setUserChatId(String userChatId) {
		this.userChatId = userChatId;
	}
	@Override
	public String toString() {
		return "UserEventVO [idx=" + idx + ", userid=" + userid + ", event_date=" + event_date + ", event_time="
				+ event_time + ", event_charger=" + event_charger + ", event_content=" + event_content
				+ ", ticket_number=" + ticket_number + ", ticket_date=" + ticket_date + ", ticket_status="
				+ ticket_status + ", userChatId=" + userChatId + "]";
	}
}
