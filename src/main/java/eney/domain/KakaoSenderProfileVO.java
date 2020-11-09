package eney.domain;

public class KakaoSenderProfileVO extends PageVO{
    private int idx;
    private String userid;
    private String sender_key;
    private String uuid;
    private String name;
    private String created_at;

    @Override
    public String toString() {
        return "KakaoSenderProfileVO{" +
                "idx=" + idx +
                ", userid='" + userid + '\'' +
                ", sender_key='" + sender_key + '\'' +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", created_at='" + created_at + '\'' +
                '}';
    }

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

    public String getSender_key() {
        return sender_key;
    }

    public void setSender_key(String sender_key) {
        this.sender_key = sender_key;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
