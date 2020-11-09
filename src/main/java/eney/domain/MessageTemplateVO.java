package eney.domain;

import java.io.Serializable;

//Serializable
public class MessageTemplateVO extends PageVO {
    private int idx;
    private String userid;
    private String msg_type;
    private String subject;
    private String text;

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
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

    @Override
    public String toString() {
        return "MessageTemplateVO{" +
                "idx=" + idx +
                ", userid='" + userid + '\'' +
                ", msg_type='" + msg_type + '\'' +
                ", subject='" + subject + '\'' +
                ", text='" + text + '\'' +
                '}';
    }
}
