package eney.api.v1.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonInclude;
import eney.domain.PageVO;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
@ToString
public class ApiTokenVo extends PageVO {

//	@Getter
//	@Setter
//	private Integer rownum;

	@Getter
	@Setter
	private String service;

	@Getter
	@Setter
	private String allocation_ip;

	private Integer token_idx;
	private String token_key;
	private String token_userid;
	private String token_expiration_date;
	private Boolean token_enable_yn;
	private Date token_issued_date;
	
	public Integer getToken_idx() {
		return token_idx;
	}
	public void setToken_idx(Integer token_idx) {
		this.token_idx = token_idx;
	}
	public String getToken_key() {
		return token_key;
	}
	public void setToken_key(String token_key) {
		this.token_key = token_key;
	}
	public String getToken_userid() {
		return token_userid;
	}
	public void setToken_userid(String token_userid) {
		this.token_userid = token_userid;
	}
	public String getToken_expiration_date() {
		return token_expiration_date;
	}
	public void setToken_expiration_date(String token_expiration_date) {
		this.token_expiration_date = token_expiration_date;
	}
	public Boolean getToken_enable_yn() {
		return token_enable_yn;
	}
	public void setToken_enable_yn(Boolean token_enable_yn) {
		this.token_enable_yn = token_enable_yn;
	}
	public Date getToken_issued_date() {
		return token_issued_date;
	}
	public void setToken_issued_date(Date token_issued_date) {
		this.token_issued_date = token_issued_date;
	}

//	@Override
//	public String toString() {
//		return "ApiTokenVo [token_idx=" + token_idx + ", token_key=" + token_key + ", token_userid=" + token_userid
//				+ ", token_expiration_date=" + token_expiration_date + ", token_enable_yn=" + token_enable_yn
//				+ ", token_issued_date=" + token_issued_date + "]";
//	}
}
