package eney.domain;

import lombok.Data;

import java.util.Date;

public @Data class GAKeyWordVO {

    private Integer idx;

    private String keyword;

    private Integer keywordCnt;

    private String source;
    private String device_category;
    private String city;

    private String os;

    private String browser;

    private String vno;

    private String userId;

    private String searchDate;

    private Date successTimeStamp;

    private String usedViewId;
}