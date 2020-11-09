package eney.domain;

import lombok.Getter;
import lombok.Setter;

/**
 * 빌게이트 PG 결제창 호출시 필요한 파라미터
 * 순서: <b>결제창 호출</b> - 인증결과처리 - 승인요청응답
 */

public class BillgatePayRequestVo {
	
	/**
	 * 국내 카드 결제
	 */
	public static final String USING_TYPE_LOCAL = "0000";
	/**
	 * 해외 카드 결제
	 */
	public static final String USING_TYPE_OVERSEAS = "0001";
	/**
	 * 원화 결제
	 */
	public static final String CURRENCY_WON = "0000";
	/**
	 * 달러 결제
	 */
	public static final String CURRENCY_DOLLAR = "0000";
	/**
	 * 인증 밴더를 바로 호출하지 않음
	 */
	public static final String DIRECT_USE_FALSE = "0000";
	/**
	 * 인증 밴더 바로 호출
	 */
	public static final String DIRECT_USE_TRUE = "0001";
	
	/**
	 * 가맹점 ID
	 */
	private String SERVICE_ID;
	
	/**
	 * 주문번호
	 */
	private String ORDER_ID;
	
	/**
	 * 주문일시
	 */
	private String ORDER_DATE;
	
	/**
	 * 사용자 ID
	 */
	private String USER_ID;
	
	/**
	 * 구매 상품코드
	 */
	private String ITEM_CODE;
	
	/**
	 * 구매 상품명
	 */
	private String ITEM_NAME;
	
	/**
	 * 사용자 IP
	 */
	private String USER_IP;
	
	/**
	 * 결제 금액
	 */
	private Integer AMOUNT;
	
	/**
	 * 결제 결과  처리 URL
	 */
	private String RETURN_URL;
	/**
	 * 국내/해외 카드 구분 - 카드 결제
	 * {@value #USING_TYPE_LOCAL, #USING_TYPE_OVERSEAS}
	 */
	private String USING_TYPE;
	/**
	 * 승인 통화 구분 - 카드 결제
	 * 
	 * {@value #CURRENCY_WON, #CURRENCY_DOLLAR}
	 */
	private String CURRENCY;
	/**
	 * 결제 카드사<br>
	 * <p>갤럭시아컴즈 가맹점 메뉴얼 참고</p>
	 */
	private String CARD_TYPE;
	/**
	 * 카드사 인증 벤더 직접 호출 - 카드 결제
	 * {@value #DIRECT_USE_FALSE, #DIRECT_USE_TRUE}
	 */
	private String DIRECT_USE;
	/**
	 * 할부 가능 개월 수 - 카드 결제
	 */
	private String INSTALLMENT_PERIOD;
	/**
	 * 데이터 변조 방지용 - 카드 결제
	 */
	private String CHECK_SUM;

	@Setter
	@Getter
	private String RESERVED1;
	
	public BillgatePayRequestVo(){
		this.USING_TYPE = USING_TYPE_LOCAL;
		this.CURRENCY = CURRENCY_WON;
		this.CARD_TYPE = DIRECT_USE_FALSE;
		this.INSTALLMENT_PERIOD = "0";
		
	}
	
	public String getSERVICE_ID() {
		return SERVICE_ID;
	}
	public void setSERVICE_ID(String sERVICE_ID) {
		SERVICE_ID = sERVICE_ID;
	}
	public String getORDER_ID() {
		return ORDER_ID;
	}
	public void setORDER_ID(String oRDER_ID) {
		ORDER_ID = oRDER_ID;
	}
	public String getORDER_DATE() {
		return ORDER_DATE;
	}
	public void setORDER_DATE(String oRDER_DATE) {
		ORDER_DATE = oRDER_DATE;
	}
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getITEM_CODE() {
		return ITEM_CODE;
	}
	public void setITEM_CODE(String iTEM_CODE) {
		ITEM_CODE = iTEM_CODE;
	}
	public String getITEM_NAME() {
		return ITEM_NAME;
	}
	public void setITEM_NAME(String iTEM_NAME) {
		ITEM_NAME = iTEM_NAME;
	}
	public String getUSER_IP() {
		return USER_IP;
	}
	public void setUSER_IP(String uSER_IP) {
		USER_IP = uSER_IP;
	}
	public Integer getAMOUNT() {
		return AMOUNT;
	}
	public void setAMOUNT(Integer aMOUNT) {
		AMOUNT = aMOUNT;
	}
	public String getRETURN_URL() {
		return RETURN_URL;
	}
	public void setRETURN_URL(String rETURN_URL) {
		RETURN_URL = rETURN_URL;
	}
	public String getUSING_TYPE() {
		return USING_TYPE;
	}
	public void setUSING_TYPE(String uSING_TYPE) {
		USING_TYPE = uSING_TYPE;
	}
	public String getCURRENCY() {
		return CURRENCY;
	}
	public void setCURRENCY(String cURRENCY) {
		CURRENCY = cURRENCY;
	}
	public String getCARD_TYPE() {
		return CARD_TYPE;
	}
	public void setCARD_TYPE(String cARD_TYPE) {
		CARD_TYPE = cARD_TYPE;
	}
	public String getDIRECT_USE() {
		return DIRECT_USE;
	}
	public void setDIRECT_USE(String dIRECT_USE) {
		DIRECT_USE = dIRECT_USE;
	}
	public String getINSTALLMENT_PERIOD() {
		return INSTALLMENT_PERIOD;
	}
	public void setINSTALLMENT_PERIOD(String iNSTALLMENT_PERIOD) {
		INSTALLMENT_PERIOD = iNSTALLMENT_PERIOD;
	}
	public String getCHECK_SUM() {
		return CHECK_SUM;
	}
	public void setCHECK_SUM(String cHECK_SUM) {
		CHECK_SUM = cHECK_SUM;
	}
	
	@Override
	public String toString() {
		return "BillgatePayCallVo [SERVICE_ID=" + SERVICE_ID + ", ORDER_ID=" + ORDER_ID + ", ORDER_DATE=" + ORDER_DATE
				+ ", USER_ID=" + USER_ID + ", ITEM_CODE=" + ITEM_CODE + ", ITEM_NAME=" + ITEM_NAME + ", USER_IP="
				+ USER_IP + ", AMOUNT=" + AMOUNT + ", RETURN_URL=" + RETURN_URL + ", USING_TYPE=" + USING_TYPE
				+ ", CURRENCY=" + CURRENCY + ", CARD_TYPE=" + CARD_TYPE + ", DIRECT_USE=" + DIRECT_USE
				+ ", INSTALLMENT_PERIOD=" + INSTALLMENT_PERIOD + ", CHECK_SUM=" + CHECK_SUM + "]";
	}
}
