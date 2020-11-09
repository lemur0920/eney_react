package eney.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * 빌게이트 PG 결제창 처리 후 전송받는 데이터
 * 순서: 결제창 호출 - <b>인증결과처리</b> - 승인요청응답
 */
@ToString
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BillgatePayResponseVo {
	
	public static final String RESPONSE_CODE_SUCCESS = "0000";
	
	private String SERVICE_ID;
	private String SERVICE_CODE;
	private String ORDER_ID;
	private String ORDER_DATE;
	private String TRANSACTION_ID;
	private String RESPONSE_CODE;
	private String RESPONSE_MESSAGE;
	private String AUTH_AMOUNT;
	private String DETAIL_RESPONSE_CODE;
	private String DETAIL_RESPONSE_MESSAGE;
	private String CHECK_SUM;
    @JsonProperty("MESSAGE")
	private String MESSAGE;
	private String AUTH_NUMBER;
	private String AUTH_DATE;
	private String RESERVED1;
	private String test;

	
//	@Override
//	public String toString() {
//		return "BillgatePayResponseVo [SERVICE_ID=" + SERVICE_ID + ", SERVICE_CODE=" + SERVICE_CODE + ", ORDER_ID="
//				+ ORDER_ID + ", ORDER_DATE=" + ORDER_DATE + ", TRANSACTION_ID=" + TRANSACTION_ID + ", RESPONSE_CODE="
//				+ RESPONSE_CODE + ", RESPONSE_MESSAGE=" + RESPONSE_MESSAGE + ", AUTH_AMOUNT=" + AUTH_AMOUNT
//				+ ", DETAIL_RESPONSE_CODE=" + DETAIL_RESPONSE_CODE + ", DETAIL_RESPONSE_MESSAGE="
//				+ DETAIL_RESPONSE_MESSAGE + ", CHECK_SUM=" + CHECK_SUM + ", MESSAGE=" + MESSAGE + ", AUTH_NUMBER="
//				+ AUTH_NUMBER + ", AUTH_DATE=" + AUTH_DATE + "]";
//	}
}
