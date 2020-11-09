package eney.domain;


import lombok.*;

@ToString
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DashboardDataVO {
    //전체콜
    private int total_call;
    //평균 통화시간
    private int svc_duration;
    //콜 결과에 따른 콜 카운트
    private int result_call;
    //전환율
    private Double conversion_rate;



    //비교 할 날짜의 데이터
    private int compare_total_call;
    private int compare_svc_duration;
    private int compare_result_call;

    //증감율
    private Double total_call_change_rate;
    private Double result_call_change_rate;
    private Double conversion_rate_change_rate;
    private Double svc_duration_change_rate;
}
