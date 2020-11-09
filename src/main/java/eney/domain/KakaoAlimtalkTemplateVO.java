package eney.domain;

public class KakaoAlimtalkTemplateVO extends PageVO{
    private int idx;
    private String userid;
    private String template_code;
    private String template_name;
    private String template_content;
    private String sender_key;
    private String uuid;
    private String name;


    // sejongAPI
    private String inspectionStatus;
    private String status;
    private String createdAt;
    private String modifiedAt;



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

    public String getTemplate_code() {
        return template_code;
    }

    public void setTemplate_code(String template_code) {
        this.template_code = template_code;
    }

    public String getTemplate_name() {
        return template_name;
    }

    public void setTemplate_name(String template_name) {
        this.template_name = template_name;
    }

    public String getTemplate_content() {
        return template_content;
    }

    public void setTemplate_content(String template_content) {
        this.template_content = template_content;
    }

    public String getSender_key() {
        return sender_key;
    }

    public void setSender_key(String sender_key) {
        this.sender_key = sender_key;
    }

    public String getInspectionStatus() {
        return inspectionStatus;
    }

    public void setInspectionStatus(String inspectionStatus) {
        this.inspectionStatus = inspectionStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
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

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(String modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    @Override
    public String toString() {
        return "KakaoAlimtalkTemplateVO{" +
                "idx=" + idx +
                ", userid='" + userid + '\'' +
                ", template_code='" + template_code + '\'' +
                ", template_name='" + template_name + '\'' +
                ", template_content='" + template_content + '\'' +
                ", sender_key='" + sender_key + '\'' +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", inspectionStatus='" + inspectionStatus + '\'' +
                ", status='" + status + '\'' +
                ", createdAt='" + createdAt + '\'' +
                ", modifiedAt='" + modifiedAt + '\'' +
                '}';
    }
}
