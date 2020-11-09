package eney.domain;

public class MessageVO {
	/*DB COLUMN*/
	private int mseq;
	
	private String userid;
	
	private String msg_type;
	private String send_type;
	private String dkey;
	private int dcnt;
	private String dstaddr;
	private String callback;
	private String stat;
	private String subject;
	private String text_type;
	private String text;
	private String text2;
	private int expiretime;
	private String k_template_code;
	private String k_expiretime;

	private String k_button_type;
	private String k_button_name;
	private String k_button_url;
	private String k_button_url2;

	private String k_button2_type;
	private String k_button2_name;
	private String k_button2_url;
	private String k_button2_url2;

	private String k_button3_type;
	private String k_button3_name;
	private String k_button3_url;
	private String k_button3_url2;

	private String k_button4_type;
	private String k_button4_name;
	private String k_button4_url;
	private String k_button4_url2;

	private String k_button5_type;
	private String k_button5_name;
	private String k_button5_url;
	private String k_button5_url2;

	private String k_img_link_url;
	private String k_next_type;


	private String filecnt;
	private String fileloc1;
	private int filesize1;
	private String fileloc2;
	private int filesize2;
	private String fileloc3;
	private int filesize3;
	private String fileloc4;
	private int filesize4;
	private String fileloc5;
	private int filesize5;
	private int filecnt_checkup;

	private String insert_time;
	private String request_time;
	private String send_time;
	private String report_time;
	private String tcprecv_time;
	private String save_time;
	private String telecom;
	private String result;
	private int repcnt;
	private int server_id;
	private String opt_id;
	private String opt_cmp;
	private String opt_post;
	private String opt_name;
	private int ext_col0;
	private String ext_col2;
	private String ext_col3;

	private String pseq;
	private String sender_key;
	private String k_at_send_type;
	private String k_ad_flag;
	
	/*CUSTOM VALUE*/
	private String yyyymm;
	private String date;
	private String success;
	private String fail;
	private String total;
	private Integer charge_point;

	public int getMseq() {
		return mseq;
	}

	public void setMseq(int mseq) {
		this.mseq = mseq;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getMsg_type() {
		return msg_type;
	}

	public void setMsg_type(String msg_type) {
		this.msg_type = msg_type;
	}

	public String getSend_type() {
		return send_type;
	}

	public void setSend_type(String send_type) {
		this.send_type = send_type;
	}

	public String getDkey() {
		return dkey;
	}

	public void setDkey(String dkey) {
		this.dkey = dkey;
	}

	public int getDcnt() {
		return dcnt;
	}

	public void setDcnt(int dcnt) {
		this.dcnt = dcnt;
	}

	public String getDstaddr() {
		return dstaddr;
	}

	public void setDstaddr(String dstaddr) {
		this.dstaddr = dstaddr;
	}

	public String getCallback() {
		return callback;
	}

	public void setCallback(String callback) {
		this.callback = callback;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getText_type() {
		return text_type;
	}

	public void setText_type(String text_type) {
		this.text_type = text_type;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getText2() {
		return text2;
	}

	public void setText2(String text2) {
		this.text2 = text2;
	}

	public int getExpiretime() {
		return expiretime;
	}

	public void setExpiretime(int expiretime) {
		this.expiretime = expiretime;
	}

	public String getK_template_code() {
		return k_template_code;
	}

	public void setK_template_code(String k_template_code) {
		this.k_template_code = k_template_code;
	}

	public String getK_expiretime() {
		return k_expiretime;
	}

	public void setK_expiretime(String k_expiretime) {
		this.k_expiretime = k_expiretime;
	}

	public String getK_button_type() {
		return k_button_type;
	}

	public void setK_button_type(String k_button_type) {
		this.k_button_type = k_button_type;
	}

	public String getK_button_name() {
		return k_button_name;
	}

	public void setK_button_name(String k_button_name) {
		this.k_button_name = k_button_name;
	}

	public String getK_button_url() {
		return k_button_url;
	}

	public void setK_button_url(String k_button_url) {
		this.k_button_url = k_button_url;
	}

	public String getK_button_url2() {
		return k_button_url2;
	}

	public void setK_button_url2(String k_button_url2) {
		this.k_button_url2 = k_button_url2;
	}

	public String getK_button2_type() {
		return k_button2_type;
	}

	public void setK_button2_type(String k_button2_type) {
		this.k_button2_type = k_button2_type;
	}

	public String getK_button2_name() {
		return k_button2_name;
	}

	public void setK_button2_name(String k_button2_name) {
		this.k_button2_name = k_button2_name;
	}

	public String getK_button2_url() {
		return k_button2_url;
	}

	public void setK_button2_url(String k_button2_url) {
		this.k_button2_url = k_button2_url;
	}

	public String getK_button2_url2() {
		return k_button2_url2;
	}

	public void setK_button2_url2(String k_button2_url2) {
		this.k_button2_url2 = k_button2_url2;
	}

	public String getK_button3_type() {
		return k_button3_type;
	}

	public void setK_button3_type(String k_button3_type) {
		this.k_button3_type = k_button3_type;
	}

	public String getK_button3_name() {
		return k_button3_name;
	}

	public void setK_button3_name(String k_button3_name) {
		this.k_button3_name = k_button3_name;
	}

	public String getK_button3_url() {
		return k_button3_url;
	}

	public void setK_button3_url(String k_button3_url) {
		this.k_button3_url = k_button3_url;
	}

	public String getK_button3_url2() {
		return k_button3_url2;
	}

	public void setK_button3_url2(String k_button3_url2) {
		this.k_button3_url2 = k_button3_url2;
	}

	public String getK_button4_type() {
		return k_button4_type;
	}

	public void setK_button4_type(String k_button4_type) {
		this.k_button4_type = k_button4_type;
	}

	public String getK_button4_name() {
		return k_button4_name;
	}

	public void setK_button4_name(String k_button4_name) {
		this.k_button4_name = k_button4_name;
	}

	public String getK_button4_url() {
		return k_button4_url;
	}

	public void setK_button4_url(String k_button4_url) {
		this.k_button4_url = k_button4_url;
	}

	public String getK_button4_url2() {
		return k_button4_url2;
	}

	public void setK_button4_url2(String k_button4_url2) {
		this.k_button4_url2 = k_button4_url2;
	}

	public String getK_button5_type() {
		return k_button5_type;
	}

	public void setK_button5_type(String k_button5_type) {
		this.k_button5_type = k_button5_type;
	}

	public String getK_button5_name() {
		return k_button5_name;
	}

	public void setK_button5_name(String k_button5_name) {
		this.k_button5_name = k_button5_name;
	}

	public String getK_button5_url() {
		return k_button5_url;
	}

	public void setK_button5_url(String k_button5_url) {
		this.k_button5_url = k_button5_url;
	}

	public String getK_button5_url2() {
		return k_button5_url2;
	}

	public void setK_button5_url2(String k_button5_url2) {
		this.k_button5_url2 = k_button5_url2;
	}

	public String getK_img_link_url() {
		return k_img_link_url;
	}

	public void setK_img_link_url(String k_img_link_url) {
		this.k_img_link_url = k_img_link_url;
	}

	public String getK_next_type() {
		return k_next_type;
	}

	public void setK_next_type(String k_next_type) {
		this.k_next_type = k_next_type;
	}

	public String getFilecnt() {
		return filecnt;
	}

	public void setFilecnt(String filecnt) {
		this.filecnt = filecnt;
	}

	public String getFileloc1() {
		return fileloc1;
	}

	public void setFileloc1(String fileloc1) {
		this.fileloc1 = fileloc1;
	}

	public int getFilesize1() {
		return filesize1;
	}

	public void setFilesize1(int filesize1) {
		this.filesize1 = filesize1;
	}

	public String getFileloc2() {
		return fileloc2;
	}

	public void setFileloc2(String fileloc2) {
		this.fileloc2 = fileloc2;
	}

	public int getFilesize2() {
		return filesize2;
	}

	public void setFilesize2(int filesize2) {
		this.filesize2 = filesize2;
	}

	public String getFileloc3() {
		return fileloc3;
	}

	public void setFileloc3(String fileloc3) {
		this.fileloc3 = fileloc3;
	}

	public int getFilesize3() {
		return filesize3;
	}

	public void setFilesize3(int filesize3) {
		this.filesize3 = filesize3;
	}

	public String getFileloc4() {
		return fileloc4;
	}

	public void setFileloc4(String fileloc4) {
		this.fileloc4 = fileloc4;
	}

	public int getFilesize4() {
		return filesize4;
	}

	public void setFilesize4(int filesize4) {
		this.filesize4 = filesize4;
	}

	public String getFileloc5() {
		return fileloc5;
	}

	public void setFileloc5(String fileloc5) {
		this.fileloc5 = fileloc5;
	}

	public int getFilesize5() {
		return filesize5;
	}

	public void setFilesize5(int filesize5) {
		this.filesize5 = filesize5;
	}

	public int getFilecnt_checkup() {
		return filecnt_checkup;
	}

	public void setFilecnt_checkup(int filecnt_checkup) {
		this.filecnt_checkup = filecnt_checkup;
	}

	public String getInsert_time() {
		return insert_time;
	}

	public void setInsert_time(String insert_time) {
		this.insert_time = insert_time;
	}

	public String getRequest_time() {
		return request_time;
	}

	public void setRequest_time(String request_time) {
		this.request_time = request_time;
	}

	public String getSend_time() {
		return send_time;
	}

	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}

	public String getReport_time() {
		return report_time;
	}

	public void setReport_time(String report_time) {
		this.report_time = report_time;
	}

	public String getTcprecv_time() {
		return tcprecv_time;
	}

	public void setTcprecv_time(String tcprecv_time) {
		this.tcprecv_time = tcprecv_time;
	}

	public String getSave_time() {
		return save_time;
	}

	public void setSave_time(String save_time) {
		this.save_time = save_time;
	}

	public String getTelecom() {
		return telecom;
	}

	public void setTelecom(String telecom) {
		this.telecom = telecom;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public int getRepcnt() {
		return repcnt;
	}

	public void setRepcnt(int repcnt) {
		this.repcnt = repcnt;
	}

	public int getServer_id() {
		return server_id;
	}

	public void setServer_id(int server_id) {
		this.server_id = server_id;
	}

	public String getOpt_id() {
		return opt_id;
	}

	public void setOpt_id(String opt_id) {
		this.opt_id = opt_id;
	}

	public String getOpt_cmp() {
		return opt_cmp;
	}

	public void setOpt_cmp(String opt_cmp) {
		this.opt_cmp = opt_cmp;
	}

	public String getOpt_post() {
		return opt_post;
	}

	public void setOpt_post(String opt_post) {
		this.opt_post = opt_post;
	}

	public String getOpt_name() {
		return opt_name;
	}

	public void setOpt_name(String opt_name) {
		this.opt_name = opt_name;
	}

	public int getExt_col0() {
		return ext_col0;
	}

	public void setExt_col0(int ext_col0) {
		this.ext_col0 = ext_col0;
	}

	public String getExt_col2() {
		return ext_col2;
	}

	public void setExt_col2(String ext_col2) {
		this.ext_col2 = ext_col2;
	}

	public String getExt_col3() {
		return ext_col3;
	}

	public void setExt_col3(String ext_col3) {
		this.ext_col3 = ext_col3;
	}

	public String getPseq() {
		return pseq;
	}

	public void setPseq(String pseq) {
		this.pseq = pseq;
	}

	public String getSender_key() {
		return sender_key;
	}

	public void setSender_key(String sender_key) {
		this.sender_key = sender_key;
	}

	public String getK_at_send_type() {
		return k_at_send_type;
	}

	public void setK_at_send_type(String k_at_send_type) {
		this.k_at_send_type = k_at_send_type;
	}

	public String getK_ad_flag() {
		return k_ad_flag;
	}

	public void setK_ad_flag(String k_ad_flag) {
		this.k_ad_flag = k_ad_flag;
	}

	public String getYyyymm() {
		return yyyymm;
	}

	public void setYyyymm(String yyyymm) {
		this.yyyymm = yyyymm;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSuccess() {
		return success;
	}

	public void setSuccess(String success) {
		this.success = success;
	}

	public String getFail() {
		return fail;
	}

	public void setFail(String fail) {
		this.fail = fail;
	}

	public String getTotal() {
		return total;
	}

	public void setTotal(String total) {
		this.total = total;
	}

	public String getAggregate() {
		return aggregate;
	}

	public void setAggregate(String aggregate) {
		this.aggregate = aggregate;
	}

	public String getMms_success() {
		return mms_success;
	}

	public void setMms_success(String mms_success) {
		this.mms_success = mms_success;
	}

	public Integer getCharge_point() {
		return charge_point;
	}

	public void setCharge_point(Integer charge_point) {
		this.charge_point = charge_point;
	}

	private String aggregate;
	private String mms_success;

	@Override
	public String toString() {
		return "MessageVO{" +
				"mseq=" + mseq +
				", userid='" + userid + '\'' +
				", msg_type='" + msg_type + '\'' +
				", send_type='" + send_type + '\'' +
				", dkey='" + dkey + '\'' +
				", dcnt=" + dcnt +
				", dstaddr='" + dstaddr + '\'' +
				", callback='" + callback + '\'' +
				", stat='" + stat + '\'' +
				", subject='" + subject + '\'' +
				", text_type='" + text_type + '\'' +
				", text='" + text + '\'' +
				", text2='" + text2 + '\'' +
				", expiretime=" + expiretime +
				", k_template_code='" + k_template_code + '\'' +
				", k_expiretime='" + k_expiretime + '\'' +
				", k_button_type='" + k_button_type + '\'' +
				", k_button_name='" + k_button_name + '\'' +
				", k_button_url='" + k_button_url + '\'' +
				", k_button_url2='" + k_button_url2 + '\'' +
				", k_button2_type='" + k_button2_type + '\'' +
				", k_button2_name='" + k_button2_name + '\'' +
				", k_button2_url='" + k_button2_url + '\'' +
				", k_button2_url2='" + k_button2_url2 + '\'' +
				", k_button3_type='" + k_button3_type + '\'' +
				", k_button3_name='" + k_button3_name + '\'' +
				", k_button3_url='" + k_button3_url + '\'' +
				", k_button3_url2='" + k_button3_url2 + '\'' +
				", k_button4_type='" + k_button4_type + '\'' +
				", k_button4_name='" + k_button4_name + '\'' +
				", k_button4_url='" + k_button4_url + '\'' +
				", k_button4_url2='" + k_button4_url2 + '\'' +
				", k_button5_type='" + k_button5_type + '\'' +
				", k_button5_name='" + k_button5_name + '\'' +
				", k_button5_url='" + k_button5_url + '\'' +
				", k_button5_url2='" + k_button5_url2 + '\'' +
				", k_img_link_url='" + k_img_link_url + '\'' +
				", k_next_type='" + k_next_type + '\'' +
				", filecnt='" + filecnt + '\'' +
				", fileloc1='" + fileloc1 + '\'' +
				", filesize1=" + filesize1 +
				", fileloc2='" + fileloc2 + '\'' +
				", filesize2=" + filesize2 +
				", fileloc3='" + fileloc3 + '\'' +
				", filesize3=" + filesize3 +
				", fileloc4='" + fileloc4 + '\'' +
				", filesize4=" + filesize4 +
				", fileloc5='" + fileloc5 + '\'' +
				", filesize5=" + filesize5 +
				", filecnt_checkup=" + filecnt_checkup +
				", insert_time='" + insert_time + '\'' +
				", request_time='" + request_time + '\'' +
				", send_time='" + send_time + '\'' +
				", report_time='" + report_time + '\'' +
				", tcprecv_time='" + tcprecv_time + '\'' +
				", save_time='" + save_time + '\'' +
				", telecom='" + telecom + '\'' +
				", result='" + result + '\'' +
				", repcnt=" + repcnt +
				", server_id=" + server_id +
				", opt_id='" + opt_id + '\'' +
				", opt_cmp='" + opt_cmp + '\'' +
				", opt_post='" + opt_post + '\'' +
				", opt_name='" + opt_name + '\'' +
				", ext_col0=" + ext_col0 +
				", ext_col2='" + ext_col2 + '\'' +
				", ext_col3='" + ext_col3 + '\'' +
				", pseq='" + pseq + '\'' +
				", sender_key='" + sender_key + '\'' +
				", k_at_send_type='" + k_at_send_type + '\'' +
				", k_ad_flag='" + k_ad_flag + '\'' +
				", yyyymm='" + yyyymm + '\'' +
				", date='" + date + '\'' +
				", success='" + success + '\'' +
				", fail='" + fail + '\'' +
				", total='" + total + '\'' +
				", charge_point=" + charge_point +
				", aggregate='" + aggregate + '\'' +
				", mms_success='" + mms_success + '\'' +
				'}';
	}
}
