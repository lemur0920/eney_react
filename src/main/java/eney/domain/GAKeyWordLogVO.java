package eney.domain;

import lombok.Data;

import java.util.Date;

public @Data
class GAKeyWordLogVO {

    private Integer idx;

    private String log;

    private Date logRegDate;

    private String viewId;

}