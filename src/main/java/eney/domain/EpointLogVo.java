package eney.domain;

import lombok.ToString;

import java.util.Date;

@ToString
public class EpointLogVo extends PageVO{
	private Integer epoint_log_id;
//	private Integer rownum;
	private String serial_number;
	private String userid;
	private String service_code;
	private String service_name;
	private Integer charge_epoint;
	private Integer balance_epoint;
	private Date charge_date;
	private String gubun;

	public Integer getEpoint_log_id() {
		return epoint_log_id;
	}
	public void setEpoint_log_id(Integer epoint_log_id) {
		this.epoint_log_id = epoint_log_id;
	}

//	public Integer getRownum() {
//		return rownum;
//	}
//	public void setRownum(Integer rownum) {
//		this.rownum = rownum;
//	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getService_code() {
		return service_code;
	}
	public void setService_code(String service_code) {
		this.service_code = service_code;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public Integer getCharge_epoint() {
		return charge_epoint;
	}
	public void setCharge_epoint(Integer charge_epoint) {
		this.charge_epoint = charge_epoint;
	}
	public Integer getBalance_epoint() {
		return balance_epoint;
	}
	public void setBalance_epoint(Integer balance_epoint) {
		this.balance_epoint = balance_epoint;
	}
	public Date getCharge_date() {
		return charge_date;
	}
	public void setCharge_date(Date charge_date) {
		this.charge_date = charge_date;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
}
