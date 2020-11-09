package eney.domain;

import lombok.ToString;

@ToString
public class BoardConfigVO {
	private int id;
	private int board_id;
	private String board_name;
	private String board_eng_name;
	private String writer_auth;
	private String comment_yn;
	private String upload_yn;
	private String group = "";
	private String condition = "";
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getBoard_id() {
		return board_id;
	}
	public void setBoard_id(int board_id) {
		this.board_id = board_id;
	}
	public String getBoard_name() {
		return board_name;
	}
	public void setBoard_name(String board_name) {
		this.board_name = board_name;
	}
	public String getBoard_eng_name() {
		return board_eng_name;
	}
	public void setBoard_eng_name(String board_eng_name) {
		this.board_eng_name = board_eng_name;
	}
	public String getComment_yn() {
		return comment_yn;
	}
	public void setComment_yn(String comment_yn) {
		this.comment_yn = comment_yn;
	}
	public String getUpload_yn() {
		return upload_yn;
	}
	public void setUpload_yn(String upload_yn) {
		this.upload_yn = upload_yn;
	}
	public String getWriter_auth() {
		return writer_auth;
	}
	public void setWriter_auth(String writer_auth) {
		this.writer_auth = writer_auth;
	}
	public String getGroup() {
		return group;
	}
	public void setGroup(String group) {
		this.group = group;
	}
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}

}
