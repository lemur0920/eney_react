package eney.domain;

import org.springframework.web.multipart.MultipartFile;

public class WebSmsSendVO {
//    V_SMS_GUBUN VARCHAR(3), IN V_DSTADDR VARCHAR(20), IN V_CALLBACK VARCHAR(20), IN V_TEXT VARCHAR(4000), IN V_MMS_FILE VARCHAR(512),
    private String msg_type;
    private String dstaddr;
    private String callback;
    private String subject;
    private String text;
    private String text2;
    private String mms_file;
    private String userid;
    private String request_time;
    private String post_key;

    private int group_idx;

    public int getGroup_idx() {
        return group_idx;
    }

    public void setGroup_idx(int group_idx) {
        this.group_idx = group_idx;
    }

    private String fileloc1;

    public String getPost_key() {
        return post_key;
    }

    public void setPost_key(String post_key) {
        this.post_key = post_key;
    }

    public String getMsg_type() {
        return msg_type;
    }

    public void setMsg_type(String msg_type) {
        this.msg_type = msg_type;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public String getMms_file() {
        return mms_file;
    }

    public void setMms_file(String mms_file) {
        this.mms_file = mms_file;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getRequest_time() {
        return request_time;
    }

    public void setRequest_time(String request_time) {
        this.request_time = request_time;
    }

    public String getFileloc1() {
        return fileloc1;
    }

    public void setFileloc1(String fileloc1) {
        this.fileloc1 = fileloc1;
    }

    @Override
    public String toString() {
        return "WebSmsSendVO{" +
                "msg_type='" + msg_type + '\'' +
                ", dstaddr='" + dstaddr + '\'' +
                ", callback='" + callback + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                ", text2='" + text2 + '\'' +
                ", mms_file='" + mms_file + '\'' +
                ", userid='" + userid + '\'' +
                ", request_time='" + request_time + '\'' +
                ", post_key='" + post_key + '\'' +
                ", group_idx=" + group_idx +
                ", fileloc1=" + fileloc1 +
                '}';
    }
}
