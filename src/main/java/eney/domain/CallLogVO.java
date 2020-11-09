package eney.domain;

import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.ToString;

/*@JsonIgnoreProperties({ "total_item_num", "total_page_num", "page_per_item_num", "present_page",
		"present_first_item_idx", "present_last_item_idx", "page_bar_idx_num", "page_bar_first_num",
		"page_bar_last_num", "agentIdx", "agentIdx", "agentName", "dongYn", "search_cate", "search_text",
		"search_period_from", "search_period_to", "view_mode" })*/
@ApiModel(value = "CallLogInfo", description = "통화내역")
@ToString
public class CallLogVO extends PageVO {
	@ApiModelProperty(hidden = true)
	public static final String[] TABLE_MONTH_LIST = { "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11",
			"12" };
	public static final String VIEW_MODE_EXCEL = "excel";

	/* DB COLUMN */

	@JsonProperty("idx")
	@ApiModelProperty(value = "인덱스", example = "1")
	private int idx;// 인덱스

	@JsonProperty("aniNo")
	@ApiModelProperty(value = "발신번호", example = "01012341234")
	private String ani;// 발신번호

	@JsonProperty("rcvNo")
	@ApiModelProperty(value = "착신번호", example = "021231234")
	private String called_no;// 수신번호

	@JsonProperty("csDate")
	@ApiModelProperty(value = "발신일", example = "20160131")
	private String cs_date;// 발신날짜

	@JsonProperty("csTime")
	@ApiModelProperty(value = "발신시간", example = "13:00:00")
	private String cs_time;// 발신시간

	@JsonProperty("ssDate")
	@ApiModelProperty(value = "수신일", example = "20160131")
	private String ss_date;// 수신날짜

	@JsonProperty("ssTime")
	@ApiModelProperty(value = "수신시간", example = "13:00:30")
	private String ss_time;// 수신시간

	@JsonProperty("seDate")
	@ApiModelProperty(value = "통화 종료일", example = "20160131")
	private String se_date;// 종료날짜

	@JsonProperty("seTime")
	@ApiModelProperty(value = "통화 종료시간", example = "13:10:50")
	private String se_time;// 종료시간

	@JsonProperty("callDuration")
	@ApiModelProperty(value = "총 통화 연결시간(통화 종료시간 - 발신시간)", example = "650")
	private int call_duration;// se_time-cs_time(sec)

	@JsonProperty("svcDuration")
	@ApiModelProperty(value = "통화시간(통화 종료시간 - 수신시간)", example = "620")
	private int svc_duration; // se_time-ss_time(sec)

	@JsonProperty("callResult")
	@ApiModelProperty(value = "통화 성공여부 (0: 성공, 2: 실패)", allowableValues = "0,1,2", example = "0")
	private String call_result;// 결과 0:성공, 1:? 2:실패

	@JsonProperty("vno")
	@ApiModelProperty(value = "수신 050번호", example = "05061911234")
	private String dn;// 050번호

	@JsonProperty("dongName")
	@ApiModelProperty(value = "발신지명", example = "발신지명")
	private String dong_name;// 발신지명

	@JsonProperty("dongYn")
	@ApiModelProperty(value = "발신지명 사용 여부", allowableValues = "Y,N", hidden = true)
	private String dong_yn;// 발신지명 사용 유무

	@JsonProperty("agentIdx")
	@ApiModelProperty(value = "agent idx", hidden = true)
	private int agent_idx;// agent id값

	@JsonProperty("agentName")
	@ApiModelProperty(value = "agent name", hidden = true)
	private String agent_name;// agent ??

	@ApiModelProperty(value = "userid", hidden = true)
	@JsonProperty("userid")
	private String user_id;// 등록자 아이디

	@JsonProperty("memo")
	@ApiModelProperty(value = "call memo", example = "테스트 메모")
	private String memo;// call 메모

	/* CUSTOM VALUALBLE */
	@ApiModelProperty(hidden = true)
	private String call_result_text;

	@ApiModelProperty(hidden = true)
	private String svc_duration_text;

	/* SEARCH VALUABLE */
	@ApiModelProperty(hidden = true)
	private String search_cate;

	@ApiModelProperty(hidden = true)
	private String search_text;

	@Pattern(regexp = "(19[7-9][0-9]|20[0-9]{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])", message = "YYYYMMDD 형식으로 입력해 주세요")
	@ApiModelProperty(hidden = true)
	private String search_period_from;

	@Pattern(regexp = "(19[7-9][0-9]|20[0-9]{2})(0[0-9]|1[0-2])(0[1-9]|[1-2][0-9]|3[0-1])", message = "YYYYMMDD 형식으로 입력해 주세요")
	@ApiModelProperty(hidden = true)
	private String search_period_to;

	@ApiModelProperty(hidden = true)
	private String search_time_from;

	@ApiModelProperty(hidden = true)
	private String search_time_to;

	@ApiModelProperty(hidden = true)
	private String view_mode;

	@JsonProperty("voiceFile")
	@ApiModelProperty(value = "녹취파일", hidden = true)
	private String voice_file;// 녹취파일 제목

	// TODO MySQL의 limit 사용으로 인한 1을 빼줘야 됨 (임시처리)
//	@Override
//	public int getPresent_first_item_idx() {
//		return super.getPresent_first_item_idx() - 1;
//	}

	public int getIdx() {
		return idx;
	}

	public void setIdx(int idx) {
		this.idx = idx;
	}

	public String getAni() {
		return ani;
	}

	public void setAni(String ani) {
		this.ani = ani;
	}

	public String getCalled_no() {
		return called_no;
	}

	public void setCalled_no(String called_no) {
		this.called_no = called_no;
	}

	public String getCs_date() {
		return cs_date;
	}

	public void setCs_date(String cs_date) {
		this.cs_date = cs_date;
	}

	public String getCs_time() {
		String res = cs_time;
		if (res != null && !res.equals("")) {
			res = cs_time.substring(0, 2) + ":" + cs_time.substring(2, 4) + ":" + cs_time.substring(4, 6);
		}
		return res;
	}

	public void setCs_time(String cs_time) {
		this.cs_time = cs_time;
	}

	public String getSs_date() {
		return ss_date;
	}

	public void setSs_date(String ss_date) {
		this.ss_date = ss_date;
	}

	public String getSs_time() {
		String res = ss_time;
		if (res != null && !res.equals("")) {
			res = ss_time.substring(0, 2) + ":" + ss_time.substring(2, 4) + ":" + ss_time.substring(4, 6);
		}
		return res;
	}

	public void setSs_time(String ss_time) {
		this.ss_time = ss_time;
	}

	public String getSe_date() {
		return se_date;
	}

	public void setSe_date(String se_date) {
		this.se_date = se_date;
	}

	public String getSe_time() {
		String res = se_time;
		if (res != null && !res.equals("")) {
			res = se_time.substring(0, 2) + ":" + se_time.substring(2, 4) + ":" + se_time.substring(4, 6);
		}
		return res;
	}

	public void setSe_time(String se_time) {
		this.se_time = se_time;
	}

	public int getCall_duration() {
		return call_duration;
	}

	public void setCall_duration(int call_duration) {
		this.call_duration = call_duration;
	}

	public int getSvc_duration() {
		return svc_duration;
	}

	public void setSvc_duration(int svc_duration) {
		this.svc_duration = svc_duration;
	}

	public String getDn() {
		return dn;
	}

	public void setDn(String dn) {
		this.dn = dn;
	}

	public String getDong_name() {
		return dong_name;
	}

	public void setDong_name(String dong_name) {
		this.dong_name = dong_name;
	}

	public String getDong_yn() {
		return dong_yn;
	}

	public void setDong_yn(String dong_yn) {
		this.dong_yn = dong_yn;
	}

	public int getAgent_idx() {
		return agent_idx;
	}

	public void setAgent_idx(int agent_idx) {
		this.agent_idx = agent_idx;
	}

	public String getAgent_name() {
		return agent_name;
	}

	public void setAgent_name(String agent_name) {
		this.agent_name = agent_name;
	}

	public String getUser_id() {
		return user_id;
	}

	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getVoice_file() {
		return voice_file;
	}

	public void setVoice_file(String voice_file) {
		this.voice_file = voice_file;
	}

	public String getCall_result_text() {
		String res = null;
		if (call_result != null) {
			switch (call_result) {
				case "0":
					res = "성공";
					break;
				case "1":
					res = "실패";
					break;
				case "2":
					res = "실패";
					break;
				default:
					break;
			}
		}
		return res;
	}

	public void setCall_result_text(String call_result_text) {
		this.call_result_text = call_result_text;
	}

	public String getSvc_duration_text() {
		String hour = "시간";
		String min = "분";
		String sec = "초";

		int time = svc_duration;
		int hour_val = time / 3600;
		time = time - hour_val * 3600;
		int min_val = time / 60;
		time = time - min_val * 60;
		int sec_val = time;

		String res = "";
		if (hour_val == 0) {
			if (min_val == 0) {
				res = sec_val + sec;
			} else {
				res = min_val + min + " " + sec_val + sec;
			}
		} else {
			res = hour_val + hour + " " + min_val + min + " " + sec_val + sec;
		}
		return res;
	}

	public void setSvc_duration_text(String svc_duration_text) {
		this.svc_duration_text = svc_duration_text;
	}

	public String getCall_result() {
		return call_result;
	}

	public void setCall_result(String call_result) {
		this.call_result = call_result;
	}

	public String getSearch_cate() {
		return search_cate;
	}

	public void setSearch_cate(String search_cate) {
		this.search_cate = search_cate;
	}

	public String getSearch_text() {
		return search_text;
	}

	public void setSearch_text(String search_text) {
		this.search_text = search_text;
	}

	public String getSearch_period_from() {
		return search_period_from;
	}

	public void setSearch_period_from(String search_period_from) {
		this.search_period_from = search_period_from;
	}

	public String getSearch_period_to() {
		return search_period_to;
	}

	public void setSearch_period_to(String search_period_to) {
		this.search_period_to = search_period_to;
	}

	public String getSearch_time_from() {
		return search_time_from ;
	}

	public void setSearch_time_from(String search_time_from) {
		this.search_time_from = search_time_from;
	}

	public String getSearch_time_to() {
		return search_time_to ;
	}

	public void setSearch_time_to(String search_time_to) {
		this.search_time_to = search_time_to;
	}

//	@Override
//	public String toString() {
//		return "CallLogVO{" +
//				"idx='" + idx + '\'' +
//				"ani='" + ani + '\'' +
//				", called_no='" + called_no + '\'' +
//				", cs_date='" + cs_date + '\'' +
//				", cs_time='" + cs_time + '\'' +
//				", ss_date='" + ss_date + '\'' +
//				", ss_time='" + ss_time + '\'' +
//				", se_date='" + se_date + '\'' +
//				", se_time='" + se_time + '\'' +
//				", call_duration=" + call_duration +
//				", svc_duration=" + svc_duration +
//				", call_result='" + call_result + '\'' +
//				", dn='" + dn + '\'' +
//				", dong_name='" + dong_name + '\'' +
//				", dong_yn='" + dong_yn + '\'' +
//				", agent_idx=" + agent_idx +
//				", agent_name='" + agent_name + '\'' +
//				", user_id='" + user_id + '\'' +
//				", call_result_text='" + call_result_text + '\'' +
//				", svc_duration_text='" + svc_duration_text + '\'' +
//				", search_cate='" + search_cate + '\'' +
//				", search_text='" + search_text + '\'' +
//				", search_period_from='" + search_period_from + '\'' +
//				", search_period_to='" + search_period_to + '\'' +
//				", search_time_from='" + search_time_from + '\'' +
//				", search_time_to='" + search_time_to + '\'' +
//				", view_mode='" + view_mode + '\'' +
//				", voice_file='" + voice_file + '\'' +
//				'}';
//	}

	public String getView_mode() {
		return view_mode;
	}

	public void setView_mode(String view_mode) {
		this.view_mode = view_mode;
	}
}