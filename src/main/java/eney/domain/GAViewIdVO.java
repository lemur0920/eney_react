package eney.domain;

import lombok.*;

import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor

@ToString
@Builder
@Getter @Setter
public class GAViewIdVO extends PageVO{
    private Integer idx;
    private String viewId;
    private String userId;
    private Date regDate;
    private String domainAddress;
    private String comments;
}