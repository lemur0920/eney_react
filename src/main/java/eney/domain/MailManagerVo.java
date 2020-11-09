package eney.domain;

import java.util.Date;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class MailManagerVo extends Pagination{
	public static final String MAIL_TYPE_AD = "AD";
	public static final String MAIL_TYPE_INFO = "INFO";
	
	private Integer mail_idx;				// 메일 SEQ
	@NotEmpty
	@Length(min=1, max=100)
	private String mail_title;				// 제목
	@NotEmpty
	private String mail_type;				// 메일 종류
	private String mail_target_group;		// 메일을 보낼 대상
	@NotEmpty
	@Length(min=1, max=10000)
	private String mail_content;			// 내용
	private Date mail_creat_date;			// 메일 작성일
	private String mail_creat_userid;		// 메일 작성자
	private Boolean mail_send;				// 메일 전송 여부
	private Date mail_send_date;			// 메일 전송일
	private String mail_send_userid;		// 메일 전송자
	private Integer mail_success_count;		// 성공적으로 보낸 메일 수
	private Integer mail_error_count;		// 실패한 메일 수
	//private List<FileVO> fileInfoList;		// 첨부파일
	
	public Integer getMail_idx() {
		return mail_idx;
	}
	public void setMail_idx(Integer mail_idx) {
		this.mail_idx = mail_idx;
	}
	public String getMail_title() {
		return mail_title;
	}
	public void setMail_title(String mail_title) {
		this.mail_title = mail_title;
	}
	public String getMail_type() {
		return mail_type;
	}
	public void setMail_type(String mail_type) {
		this.mail_type = mail_type;
	}
	public String getMail_target_group() {
		return mail_target_group;
	}
	public void setMail_target_group(String mail_target_group) {
		this.mail_target_group = mail_target_group;
	}
	public String getMail_content() {
		return mail_content;
	}
	public void setMail_content(String mail_content) {
		this.mail_content = mail_content;
	}
	public Date getMail_creat_date() {
		return mail_creat_date;
	}
	public void setMail_creat_date(Date mail_creat_date) {
		this.mail_creat_date = mail_creat_date;
	}
	public String getMail_creat_userid() {
		return mail_creat_userid;
	}
	public void setMail_creat_userid(String mail_creat_userid) {
		this.mail_creat_userid = mail_creat_userid;
	}
	public Boolean getMail_send() {
		return mail_send;
	}
	public void setMail_send(Boolean mail_send) {
		this.mail_send = mail_send;
	}
	public Date getMail_send_date() {
		return mail_send_date;
	}
	public void setMail_send_date(Date mail_send_date) {
		this.mail_send_date = mail_send_date;
	}
	public String getMail_send_userid() {
		return mail_send_userid;
	}
	public void setMail_send_userid(String mail_send_userid) {
		this.mail_send_userid = mail_send_userid;
	}
	public Integer getMail_success_count() {
		return mail_success_count;
	}
	public void setMail_success_count(Integer mail_success_count) {
		this.mail_success_count = mail_success_count;
	}
	public Integer getMail_error_count() {
		return mail_error_count;
	}
	public void setMail_error_count(Integer mail_error_count) {
		this.mail_error_count = mail_error_count;
	}
	public static String getMailTypeAd() {
		return MAIL_TYPE_AD;
	}
	public static String getMailTypeInfo() {
		return MAIL_TYPE_INFO;
	}
	
	public String getMailTypeToString(){
		if(mail_type != null){
			if(MailManagerVo.MAIL_TYPE_AD.equals(mail_type)){
				return "광고";
			} else if(MailManagerVo.MAIL_TYPE_INFO.equals(mail_type)){
				return "알림";
			} else {
				return "ERROR";
			}
		}
		return "";
	}
	
	public String getMailTargetToString(){
		String returnString = "";
		if(mail_target_group != null){
			switch(mail_target_group){
			case "ALL":
				returnString = "모두";
				break;
			case "personal":
				returnString = "개인";
				break;
			case "corporate":
				returnString = "기업";
				break;
			case "admin":
				returnString = "관리자";
				break;
			default:
				returnString = "ERROR";
				break;
			}
		}
		return returnString;
	}
	
	@Override
	public String toString() {
		return "MailManagerVo [mail_idx=" + mail_idx + ", mail_title=" + mail_title + ", mail_type=" + mail_type
				+ ", mail_target_group=" + mail_target_group + ", mail_content=" + mail_content + ", mail_creat_date="
				+ mail_creat_date + ", mail_creat_userid=" + mail_creat_userid + ", mail_send=" + mail_send
				+ ", mail_send_date=" + mail_send_date + ", mail_send_userid=" + mail_send_userid
				+ ", mail_success_count=" + mail_success_count + ", mail_error_count=" + mail_error_count + "]";
	}
}
