package eney.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@ToString
public class UserCertifyVo {

	public enum UserCertifyStatus { stanby, success, error };
	public enum UserCertifyType { JOIN, ID_FIND, PW_RECOVER,PHONE_CHANGE }
	
	private int user_cretify_idx;
	private UserCertifyStatus ceritfy_status;
	private UserCertifyType ceritfy_type;
	private String ci;
	private String di;

	@Getter
	@Setter
	private String impUid;
	
	private String mobil_id;
	private String sign_date;
	
	private String name;
	private String phone_number;
	private String comm_id;
	private String socialno;
	private String sex;
	private String foreigner;
	private String mstr;
	private String result_cd;
	private String result_msg;
	
	private String tradeid;
	
	private Date timestamp;

	public int getUser_cretify_idx() {
		return user_cretify_idx;
	}
	public void setUser_cretify_idx(int user_cretify_idx) {
		this.user_cretify_idx = user_cretify_idx;
	}
	public UserCertifyStatus getCeritfy_status() {
		return ceritfy_status;
	}
	public void setCeritfy_status(UserCertifyStatus ceritfy_status) {
		this.ceritfy_status = ceritfy_status;
	}
	public UserCertifyType getCeritfy_type() {
		return ceritfy_type;
	}
	public void setCeritfy_type(UserCertifyType ceritfy_type) {
		this.ceritfy_type = ceritfy_type;
	}
	public String getCi() {
		return ci;
	}
	public void setCi(String ci) {
		this.ci = ci;
	}
	public String getDi() {
		return di;
	}
	public void setDi(String di) {
		this.di = di;
	}
	public String getMobil_id() {
		return mobil_id;
	}
	public void setMobil_id(String mobil_id) {
		this.mobil_id = mobil_id;
	}
	public String getSign_date() {
		return sign_date;
	}
	public void setSign_date(String sign_date) {
		this.sign_date = sign_date;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPhone_number() {
		return phone_number;
	}
	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}
	public String getComm_id() {
		return comm_id;
	}
	public void setComm_id(String comm_id) {
		this.comm_id = comm_id;
	}
	public String getSocialno() {
		return socialno;
	}
	public void setSocialno(String socialno) {
		this.socialno = socialno;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getForeigner() {
		return foreigner;
	}
	public void setForeigner(String foreigner) {
		this.foreigner = foreigner;
	}
	public String getMstr() {
		return mstr;
	}
	public void setMstr(String mstr) {
		this.mstr = mstr;
	}
	public String getResult_cd() {
		return result_cd;
	}
	public void setResult_cd(String result_cd) {
		this.result_cd = result_cd;
	}
	public String getResult_msg() {
		return result_msg;
	}
	public void setResult_msg(String result_msg) {
		this.result_msg = result_msg;
	}
	public String getTradeid() {
		return tradeid;
	}
	public void setTradeid(String tradeid) {
		this.tradeid = tradeid;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

}
