package eney.domain;

import java.util.List;

public class KakaoTemplateVO {
    private String senderKey;
    private String templateCode;
    private String senderKeyType;
    private String uuid;
    private String name;
    private String templateName;
    private String templateContent;
    private List<KakaoAttachVO> buttons;

    @Override
    public String toString() {
        return "KakaoTemplateVO{" +
                "senderKey='" + senderKey + '\'' +
                ", templateCode='" + templateCode + '\'' +
                ", senderKeyType='" + senderKeyType + '\'' +
                ", uuid='" + uuid + '\'' +
                ", name='" + name + '\'' +
                ", templateName='" + templateName + '\'' +
                ", templateContent='" + templateContent + '\'' +
                ", buttons=" + buttons +
                '}';
    }

    public String getSenderKey() {
        return senderKey;
    }

    public void setSenderKey(String senderKey) {
        this.senderKey = senderKey;
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

    public String getTemplateName() {
        return templateName;
    }

    public void setTemplateName(String templateName) {
        this.templateName = templateName;
    }

    public String getTemplateContent() {
        return templateContent;
    }

    public void setTemplateContent(String templateContent) {
        this.templateContent = templateContent;
    }

    public String getTemplateCode() {
        return templateCode;
    }

    public void setTemplateCode(String templateCode) {
        this.templateCode = templateCode;
    }

    public String getSenderKeyType() {
        return senderKeyType;
    }

    public void setSenderKeyType(String senderKeyType) {
        this.senderKeyType = senderKeyType;
    }

    public List<KakaoAttachVO> getButtons() {
        return buttons;
    }

    public void setButtons(List<KakaoAttachVO> buttons) {
        this.buttons = buttons;
    }
}
