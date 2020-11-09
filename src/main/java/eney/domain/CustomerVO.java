package eney.domain;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CustomerVO extends PageVO{
    private int idx;
    private String phone_number;
    private String user_id;
    private String name;
    private String email;
    private String birthday;
    private String gender;
    private String team_name;
    private String address;
    private Date reg_date;
    private Date last_update;
    private int group_idx;


}
