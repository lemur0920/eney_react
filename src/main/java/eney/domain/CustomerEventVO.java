package eney.domain;

import lombok.*;

import java.util.Date;


@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomerEventVO extends PageVO {
    private int idx;
    private String event_type;
    private Date event_date;
    private int customer_idx;
    private String receiver_num;
    private String receiver_location;
    private String call_result;
    private String voice_file;
    private String browser;
    private String os;
    private String source;
    private String device_category;
    private String city;
    private String keyword;
    private Date ss_date;
    private Date se_date;
    private int call_duration;
}
