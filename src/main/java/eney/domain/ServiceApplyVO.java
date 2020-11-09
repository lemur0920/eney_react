package eney.domain;
import eney.domain.PaymentLogVo.PayStatus;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ServiceApplyVO extends PageVO{

//    //DB n_item 테이블 참고
//    public enum service_type {web_young,web_word,web_snap,web_single,web_double,web_fixip,web_imgback,web_definder,sectigo_basic,sectigo_basic_wildcard,sectigo_MDC,symantec_secure_site,symantec_secure_site_EV,ssl_vpn,
//        ssl_vpn_equip,definder_80e,definder_100e,prtg};

    private int patchcall_idx;
    private int idx;
    private String userid;
    private String username;
    private String service_type;
    private int service_amount;
    private int service_vat;
    private String service_period;
    private String pay_way;
    private String corporate_address;
    private String ceo_name;
    private String end_date;
    private String generate_yn;
    private PaymentLogVo.PayStatus pay_state;
    private String publish_email;
    private String reg_date;
    private String status;
    private String note;

}
