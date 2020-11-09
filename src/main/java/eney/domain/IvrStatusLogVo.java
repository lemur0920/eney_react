package eney.domain;

import java.util.Date;

public class IvrStatusLogVo {
	public static final String STATUS_CODE_IVR_PROCESS_KILL = "01";
	public static final String STATUS_CODE_IVR_PROCESS_RESTART = "02";
	
	private Integer ivr_status_log_idx;
	private String status_code;
	private Date timestamp;
	private String ip;
	
	public Integer getIvr_status_log_idx() {
		return ivr_status_log_idx;
	}
	public void setIvr_status_log_idx(Integer ivr_status_log_idx) {
		this.ivr_status_log_idx = ivr_status_log_idx;
	}
	public String getStatus_code() {
		return status_code;
	}
	public void setStatus_code(String status_code) {
		this.status_code = status_code;
	}
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	@Override
	public String toString() {
		return "IvrStatusLogVo [ivr_status_log_idx=" + ivr_status_log_idx + ", status_code=" + status_code
				+ ", timestamp=" + timestamp + ", ip=" + ip + "]";
	}
}
