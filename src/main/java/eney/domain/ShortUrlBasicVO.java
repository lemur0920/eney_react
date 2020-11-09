package eney.domain;


import java.util.Date;

/**
 * CREATE TABLE `n_shorturl` (
 `url_idx` int(11) NOT NULL AUTO_INCREMENT COMMENT '아이디 자동생성', 아이디
 `n_original_url` varchar(100) COLLATE utf8_bin DEFAULT NULL, 원본 url
 `uesr_id` varchar(45) COLLATE utf8_bin DEFAULT NULL,
 `title` varchar(45) COLLATE utf8_bin DEFAULT NULL, 단축 url 제목
 `description` varchar(100) COLLATE utf8_bin DEFAULT NULL, 단축 url 설명
 `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP, 생성시간 하지만 String 으로 하기 쉽게 변형
 `enable` varchar(1) COLLATE utf8_bin DEFAULT '0', 사용 여부
 PRIMARY KEY (`url_id`),
 UNIQUE KEY `url_id_UNIQUE` (`url_id`)
 )
 */
public class ShortUrlBasicVO {
    private Integer url_idx;
    private String original_url;
    private String user_id;
    private String title;
    private String description;
    private Date created_date;
    private String enable;

    public ShortUrlBasicVO() {
    }

    public ShortUrlBasicVO(String original_url, String user_id, String title, String description) {
        this.original_url = original_url;
        this.user_id = user_id;
        this.title = title;
        this.description = description;
    }

    public Integer getUrl_idx() {
        return url_idx;
    }

    public void setUrl_idx(int url_idx) {
        this.url_idx = url_idx;
    }

    public String getOriginal_url() {
        return original_url;
    }

    public void setOriginal_url(String original_url) {
        this.original_url = original_url;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreated_date() {
        return created_date;
    }

    public void setCreated_date(Date created_date) {
        this.created_date = created_date;
    }

    public String getEnable() {
        return enable;
    }

    public void setEnable(String enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "ShortUrlVO{" +
                "url_idx=" + url_idx +
                ", original_url='" + original_url + '\'' +
                ", user_id='" + user_id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", created_date=" + created_date +
                ", enable='" + enable + '\'' +
                '}';
    }
}
