package eney.domain;

public class NoteVO extends Pagination{
	private int note_id;
	private String sender_id;
	private String receiver_id;
	private String message;
	private String send_date;
	private String receive_date;
	private String check_yn;
	private String mode;
	private String view_type;
	
	public int getNote_id() {
		return note_id;
	}
	public void setNote_id(int note_id) {
		this.note_id = note_id;
	}
	public String getSender_id() {
		return sender_id;
	}
	public void setSender_id(String sender_id) {
		this.sender_id = sender_id;
	}
	public String getReceiver_id() {
		return receiver_id;
	}
	public void setReceiver_id(String receiver_id) {
		this.receiver_id = receiver_id;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getSend_date() {
		return send_date;
	}
	public void setSend_date(String send_date) {
		this.send_date = send_date;
	}
	public String getReceive_date() {
		return receive_date;
	}
	public void setReceive_date(String receive_date) {
		this.receive_date = receive_date;
	}
	public String getCheck_yn() {
		return check_yn;
	}
	public void setCheck_yn(String check_yn) {
		this.check_yn = check_yn;
	}
	@Override
	public String toString() {
		return "NoteVO [note_id=" + note_id + ", sender_id=" + sender_id
				+ ", receiver_id=" + receiver_id + ", message=" + message
				+ ", send_date=" + send_date + ", receive_date=" + receive_date
				+ ", check_yn=" + check_yn + "]";
	}
	public String getMode() {
		return mode;
	}
	public void setMode(String mode) {
		this.mode = mode;
	}
	public String getView_type() {
		return view_type;
	}
	public void setView_type(String view_type) {
		this.view_type = view_type;
	}
}
