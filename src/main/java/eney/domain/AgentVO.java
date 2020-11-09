package eney.domain;

import java.util.List;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotEmpty;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties({"total_item_num", "total_page_num", "page_per_item_num", "present_page", "present_first_item_idx", "present_last_item_idx", "page_bar_idx_num", "page_bar_first_num", "page_bar_last_num",
				"idx", "login_id", "order_desc", "total_item_num", "total_page_num", "make_click_to_call", "reg_day", "sendmentIdx",
				"number_input", "rcvment_input", "reg_gubun", "closing_day", "search_cate", "search_text", "order", "etc_data1",
				"close_vno", "recYn","fileVO_list","del_date","pagenation_flag"})
@ApiModel(value="AgentInfo", description="050번호 설정")
public class AgentVO extends PageVO{
	/*DB COLUMN*/
	@ApiModelProperty(value="idx", hidden=true)
	private Integer idx;

	@NotNull(message="값을 입력해주세요.")
	@NotEmpty(message="값을 입력해주세요.")
	@JsonProperty("vno")
	@ApiModelProperty(value="050번호", example="05061911234")
	private String vno;

/*	@NotNull(message="값을 입력해주세요.")
	@NotEmpty(message="값을 입력해주세요.")*/
	@Pattern(regexp= "^01(?:0|1[6-9])(?:\\d{3}|\\d{4})\\d{4}$|^\\d{2,3}\\d{3,4}\\d{4}$|01(?:0|1[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$|^\\d{2,3}-\\d{3,4}-\\d{4}$", message="전화번호 형식에 맞게 입력해주세요.")
	@JsonProperty("rcvNo")
	@ApiModelProperty(value="착신번호", example="0212341234")
	private String rcv_no;

	@JsonProperty("name")
	@ApiModelProperty(value="기본값으로 '에네이'가 들어가 있으니 사용하지 않는 경우에는 '에네이'로 설정해주시기 바랍니다.", example="가맹점명" )
	private String name;

	@JsonProperty("dongName")
	@ApiModelProperty(value="발신지명", example="발신지명")
	private String dong_name;

	@JsonProperty("dongYn")
	@ApiModelProperty(value="발신지명 안내 사용 여부", allowableValues="Y,N")
	private String 	dong_yn;

	//@NotNull(message="값을 입력해주세요.")
	@JsonProperty("colorringIdx")
	@Min(value=0, message="선택메뉴에서 선택해주세요.")
	@ApiModelProperty(value="컬러링 번호", example="1")
	private Integer colorring_idx;

	@JsonProperty("rcvmentIdx")
	@ApiModelProperty(value="착신멘트 번호", example="1")
	private Integer rcvment_idx;

	@JsonProperty("sendmentIdx")
	@ApiModelProperty(value="수신멘트 번호", hidden=true)
	private Integer sendment_idx;

	@Size(max=100, message="100자 이하만 입력이 가능합니다")
	@JsonProperty("sms")
	@ApiModelProperty(value="CallBack SMS내용", example="SMS 내용")
	private String sms;

	@Size(max=100, message="100자 이하만 입력이 가능합니다")
	@JsonProperty("out_sms")
	@ApiModelProperty(value="부재중 CallBack SMS내용", example="SMS 내용")
	private String out_sms;

	//@NotNull(message="값을 입력해주세요.")
	//@NotEmpty(message="값을 입력해주세요.")
	@JsonProperty("smsYn")
	@ApiModelProperty(value="CallBack SMS 사용 여부", allowableValues="Y,N,M")
	private String sms_yn;
	@JsonProperty("outSmsYn")
	@ApiModelProperty(value="부재중 CallBack SMS 사용 여부", allowableValues="Y,N")
	private String out_sms_yn;
	@JsonProperty("callback_no")
	@ApiModelProperty(value="콜백 메시지 발송시 발신 번호")
	private String callback_no;

	@JsonProperty("out_sms_phone")
	@ApiModelProperty(value="부재중 SMS 착신 휴대폰 번호")
	private String out_sms_phone;

	@ApiModelProperty(hidden=true)
	private String login_id = "eney";

	@JsonProperty("userId")
	@ApiModelProperty(value="사용자 ID", example="test")
	private String user_id;

	@JsonProperty("regDate")
	@ApiModelProperty(value="등록일", example="2016-01-01 13:00:00.0")
	private String reg_date;

	@JsonProperty("limitDate")
	@ApiModelProperty(value="사용 만료일", example="2016-01-31 13:00:00.0")
	private String limit_date;

	@JsonProperty("recYn")
	@ApiModelProperty(value="녹취 사용 여부", hidden=true, allowableValues="Y,N")
	private String rec_yn;

	@JsonProperty("files")
	@ApiModelProperty(value="MMS사용 시 이미지 파일", hidden=true)
	private List<MultipartFile> files;

	@JsonProperty("mmsFile")
	@ApiModelProperty(value="MMS사용 시 이미지 경로", hidden=true)
	private String mms_file;

	@ApiModelProperty(hidden=true)
	private List<FileVO> fileVO_list;


	@Getter @Setter
	@JsonProperty("agentLocation")
	private String agent_location;

	public String getRec_yn() {
		return rec_yn;
	}
	public void setRec_yn(String rec_yn) {
		this.rec_yn = rec_yn;
	}
	/*CUSTOM VALUABLE*/
	@ApiModelProperty(hidden=true)
	private String number_input;
	@ApiModelProperty(hidden=true)
	private String rcvment_input;
	@ApiModelProperty(hidden=true)
	private String reg_gubun;
	@ApiModelProperty(hidden=true)
	private String reg_day;
	@ApiModelProperty(hidden=true)
	private String closing_day;

	/*SEARCH_VALUABLE*/
	@ApiModelProperty(hidden=true)
	private String search_cate;
	@ApiModelProperty(hidden=true)
	private String search_text;

	/*ORDER*/
	@ApiModelProperty(hidden=true)
	private String order;
	@ApiModelProperty(hidden=true)
	private String order_desc="true";

	/*ETC*/
	@ApiModelProperty(hidden=true)
	private String etc_data1;
	@ApiModelProperty(hidden=true)
	private List<String> close_vno;
	@ApiModelProperty(hidden=true)
	private Boolean make_click_to_call;

	@ApiModelProperty(hidden=true)
	private String del_date;
	@ApiModelProperty(hidden=true)
	private boolean pagination_flag;

	@ApiModelProperty(hidden=true)
	@JsonProperty("agent_address_info")
	private String agent_address_info;



	/*@ApiModelProperty(hidden=true, value="050번호 (-가 들어간 문자열)", example="0506-191-1234")
	public String getVnoString(){
		String temp = "";
		temp += vno.substring(0, 4);
		temp += "-";
		temp += vno.substring(4, 7);
		temp += "-";
		temp += vno.substring(7);
		return temp;
	}*/

	public Integer getIdx() {
		return idx;
	}
	public void setIdx(Integer idx) {
		this.idx = idx;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getVno() {
		return vno;
	}
	public void setVno(String vno) {
		this.vno = vno;
	}
	public String getRcv_no() {
		return rcv_no;
	}
	public void setRcv_no(String rcv_no) {
		this.rcv_no = rcv_no;
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
	public Integer getColorring_idx() {
		return colorring_idx;
	}
	public void setColorring_idx(Integer colorring_idx) {
		this.colorring_idx = colorring_idx;
	}
	public Integer getRcvment_idx() {
		return rcvment_idx;
	}
	public void setRcvment_idx(Integer rcvment_idx) {
		this.rcvment_idx = rcvment_idx;
	}
	public Integer getSendment_idx() {
		return sendment_idx;
	}
	public void setSendment_idx(Integer sendment_idx) {
		this.sendment_idx = sendment_idx;
	}
	public String getSms() {
		return sms;
	}
	public void setSms(String sms) {
		this.sms = sms;
	}
	public String getSms_yn() {
		return sms_yn;
	}
	public void setSms_yn(String sms_yn) {
		this.sms_yn = sms_yn;
	}
	public String getOut_sms() {
		return out_sms;
	}

	public void setOut_sms(String out_sms) {
		this.out_sms = out_sms;
	}

	public String getOut_sms_yn() {
		return out_sms_yn;
	}

	public void setOut_sms_yn(String out_sms_yn) {
		this.out_sms_yn = out_sms_yn;
	}

	public String getLogin_id() {
		return login_id;
	}
	public void setLogin_id(String login_id) {
		this.login_id = login_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getReg_gubun() {
		return reg_gubun;
	}
	public void setReg_gubun(String reg_gubun) {
		this.reg_gubun = reg_gubun;
	}
	public String getClosing_day()/* throws ParseException */{
		/*String res = getReg_day();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
		Date d = sdf.parse(res);
		d = DateUtil.getNextMonth(d);
		return sdf.format(d);*/
		return closing_day;
	}
	public void setClosing_day(String closing_day) {
		this.closing_day = closing_day;
	}
	public String getReg_date() {
		return reg_date;
	}
	public void setReg_date(String reg_date) {
		this.reg_date = reg_date;
	}
	public String getReg_day() {
		if(reg_date==null)
			return null;
		return reg_date.substring(0, 10).replaceAll("-", ".");
	}
	public void setReg_day(String reg_day) {
		this.reg_day = reg_day;
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
	public String getNumber_input() {
		return number_input;
	}
	public void setNumber_input(String number_input) {
		this.number_input = number_input;
	}
	public String getRcvment_input() {
		return rcvment_input;
	}
	public void setRcvment_input(String rcvment_input) {
		this.rcvment_input = rcvment_input;
	}
	public String getLimit_date() {
		return limit_date;
	}
	public void setLimit_date(String limit_date) {
		this.limit_date = limit_date;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getOrder_desc() {
		return order_desc;
	}
	public void setOrder_desc(String order_desc) {
		this.order_desc = order_desc;
	}
	public List<String> getClose_vno() {
		return close_vno;
	}
	public void setClose_vno(List<String> close_vno) {
		this.close_vno = close_vno;
	}
	public String getEtc_data1() {
		return etc_data1;
	}
	public void setEtc_data1(String etc_data1) {
		this.etc_data1 = etc_data1;
	}
	public Boolean getMake_click_to_call() {
		return make_click_to_call;
	}
	public void setMake_click_to_call(Boolean make_click_to_call) {
		this.make_click_to_call = make_click_to_call;
	}

	public String getCallback_no() {
		return callback_no;
	}

	public void setCallback_no(String callback_no) {
		this.callback_no = callback_no;
	}

	public String getOut_sms_phone() {
		return out_sms_phone;
	}
	public void setOut_sms_phone(String out_sms_phone) {
		this.out_sms_phone = out_sms_phone;
	}
	public String getMms_file() {
		return mms_file;
	}
	public void setMms_file(String mms_file) {
		this.mms_file = mms_file;
	}
	public List<MultipartFile> getFiles() {
		return files;
	}
	public void setFiles(List<MultipartFile> files) {
		this.files = files;
	}
	public List<FileVO> getFileVO_list() {
		return fileVO_list;
	}
	public void setFileVO_list(List<FileVO> fileVO_list) {
		this.fileVO_list = fileVO_list;
	}
	public String getDel_date() {
		return del_date;
	}
	public void setDel_date(String del_date) {
		this.del_date = del_date;
	}
	public boolean isPagination_flag() {
		return pagination_flag;
	}
	public void setPagination_flag(boolean pagination_flag) {
		this.pagination_flag = pagination_flag;
	}

	public String getAgent_address_info() {
		return agent_address_info;
	}
	public void setAgent_address_info(String agent_address_info) {
		this.agent_address_info = agent_address_info;
	}




	@Override
	public String toString() {
		return "AgentVO [idx=" + idx + ", vno=" + vno + ", rcv_no=" + rcv_no + ", name=" + name + ", dong_name="
				+ dong_name + ", dong_yn=" + dong_yn + ", colorring_idx=" + colorring_idx + ", rcvment_idx="
				+ rcvment_idx + ", sendment_idx=" + sendment_idx + ", sms=" + sms + ", out_sms=" + out_sms + ", sms_yn="
				+ sms_yn + ", out_sms_yn=" + out_sms_yn + ", callback_no=" + callback_no + ", out_sms_phone="
				+ out_sms_phone + ", login_id=" + login_id + ", user_id=" + user_id + ", reg_date=" + reg_date
				+ ", limit_date=" + limit_date + ", rec_yn=" + rec_yn + ", files=" + files + ", mms_file=" + mms_file
				+ ", fileVO_list=" + fileVO_list + ", number_input=" + number_input + ", rcvment_input=" + rcvment_input
				+ ", reg_gubun=" + reg_gubun + ", reg_day=" + reg_day + ", closing_day=" + closing_day
				+ ", search_cate=" + search_cate + ", search_text=" + search_text + ", order=" + order + ", order_desc="
				+ order_desc + ", etc_data1=" + etc_data1 + ", close_vno=" + close_vno + ", make_click_to_call="
				+ make_click_to_call + ", del_date=" + del_date + ", pagination_flag=" + pagination_flag + ", agent_location="+ agent_location+"agent_address_info"+agent_address_info+"]";
	}




}