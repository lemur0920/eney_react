package eney.domain;

public class MsgResultVO extends PageVO{
    private String msg_Type;
    private String send_type;
    private String dkey;
    private int dcnt;
    private String dstaddr;
    private String callback;
    private String stat;
    private String subject;
    private String text;
    private String text2;
    private int expiretime;
    private String insert_time;
    private String request_time;
    private String send_time;
    private String report_time;
    private String tcprecv_time;
    private String save_time;
    private String telecom;
    private String result;
    private String repcnt;
    private String opt_id;
    private String opt_cmp;
    private String opt_post;
    private String opt_name;
    private int ext_col0;
    private String ext_col1;
    private String ext_col2;
    private String ext_col3;
    private String filecnt;
    private int success;
    private int fail;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getFail() {
        return fail;
    }

    public void setFail(int fail) {
        this.fail = fail;
    }

    private int count;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getFilecnt() {
        return filecnt;
    }

    public void setFilecnt(String filecnt) {
        this.filecnt = filecnt;
    }

    public String getMsg_Type() {
        return msg_Type;
    }

    public void setMsg_Type(String msg_Type) {
        this.msg_Type = msg_Type;
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

    public String getRepcnt() {
        return repcnt;
    }

    public void setRepcnt(String repcnt) {
        this.repcnt = repcnt;
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

    public String getExt_col1() {
        return ext_col1;
    }

    public void setExt_col1(String ext_col1) {
        this.ext_col1 = ext_col1;
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

    @Override
    public String toString() {
        return "MsgResultVO{" +
                "msg_Type='" + msg_Type + '\'' +
                ", send_type='" + send_type + '\'' +
                ", dkey='" + dkey + '\'' +
                ", dcnt=" + dcnt +
                ", dstaddr='" + dstaddr + '\'' +
                ", callback='" + callback + '\'' +
                ", stat='" + stat + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", text2='" + text2 + '\'' +
                ", expiretime=" + expiretime +
                ", insert_time='" + insert_time + '\'' +
                ", request_time='" + request_time + '\'' +
                ", send_time='" + send_time + '\'' +
                ", report_time='" + report_time + '\'' +
                ", tcprecv_time='" + tcprecv_time + '\'' +
                ", save_time='" + save_time + '\'' +
                ", telecom='" + telecom + '\'' +
                ", result='" + result + '\'' +
                ", repcnt='" + repcnt + '\'' +
                ", opt_id='" + opt_id + '\'' +
                ", opt_cmp='" + opt_cmp + '\'' +
                ", opt_post='" + opt_post + '\'' +
                ", opt_name='" + opt_name + '\'' +
                ", ext_col0=" + ext_col0 +
                ", ext_col1='" + ext_col1 + '\'' +
                ", ext_col2='" + ext_col2 + '\'' +
                ", ext_col3='" + ext_col3 + '\'' +
                '}';
    }
}
