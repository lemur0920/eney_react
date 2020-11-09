package eney.domain;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;

@Setter
@Getter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CustomerGroupVO extends PageVO{
    private int idx;
    private String user_id;
    private String group_name;
    private Date reg_date;
    private ArrayList<Integer> use_customer_idx_list;
    private ArrayList<Integer> unused_customer_idx_list;
}
