package eney.domain;

import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CouponVO extends PageVO {
//    private Integer rownum;
    private int idx;
    private String coupon_num;
    private int point;
    private Date create_date;
    private boolean used;
    private Date used_date;
    private String used_user;

    public boolean getUsed(){
        return this.used;
    }
}
