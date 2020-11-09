package eney.domain;

public class KakaoAttachVO {
    private String name;
    private String linkType;
    private int ordering;
    private String linkMo;
    private String linkPc;
    private String linkAnd;
    private String linkIos;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLinkType() {
        return linkType;
    }

    public void setLinkType(String linkType) {
        this.linkType = linkType;
    }

    public int getOrdering() {
        return ordering;
    }

    public void setOrdering(int ordering) {
        this.ordering = ordering;
    }

    public String getLinkMo() {
        return linkMo;
    }

    public void setLinkMo(String linkMo) {
        this.linkMo = linkMo;
    }

    public String getLinkPc() {
        return linkPc;
    }

    public void setLinkPc(String linkPc) {
        this.linkPc = linkPc;
    }

    public String getLinkAnd() {
        return linkAnd;
    }

    public void setLinkAnd(String linkAnd) {
        this.linkAnd = linkAnd;
    }

    public String getLinkIos() {
        return linkIos;
    }

    public void setLinkIos(String linkIos) {
        this.linkIos = linkIos;
    }

    @Override
    public String toString() {
        return "KakaoAttachVO{" +
                "name='" + name + '\'' +
                ", linkType='" + linkType + '\'' +
                ", ordering=" + ordering +
                ", linkMo='" + linkMo + '\'' +
                ", linkPc='" + linkPc + '\'' +
                ", linkAnd='" + linkAnd + '\'' +
                ", linkIos='" + linkIos + '\'' +
                '}';
    }
}
