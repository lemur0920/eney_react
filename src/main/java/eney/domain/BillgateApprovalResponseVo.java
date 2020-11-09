package eney.domain;

/**
 * 빌게이트 PG 결제 승인 처리 후 받는 Message를 연동 모듈에 전송해 받는 데이터
 * 순서: 결제창 호출 - 인증결과처리 - <b>승인요청응답</b>
 */
public class BillgateApprovalResponseVo {
	
	public static final String RESPONSE_CODE_SUCCESS = "0000";
	
	private String serviceId;
	private String serviceCode;
	private String orderId;
	private String orderDate;
	private String transactionId;
	private String responseCode;
	private String responseMesage;
	private String authAmount;
	private String detailResponseCode;
	private String detailResponseMesage;
	private String authNumber;
	private String authDate;
	private String quota;
	private String cardCompanyCode;
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceCode() {
		return serviceCode;
	}
	public void setServiceCode(String serviceCode) {
		this.serviceCode = serviceCode;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderDate() {
		return orderDate;
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public String getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(String responseCode) {
		this.responseCode = responseCode;
	}
	public String getResponseMesage() {
		return responseMesage;
	}
	public void setResponseMesage(String responseMesage) {
		this.responseMesage = responseMesage;
	}
	public String getAuthAmount() {
		return authAmount;
	}
	public void setAuthAmount(String authAmount) {
		this.authAmount = authAmount;
	}
	public String getDetailResponseCode() {
		return detailResponseCode;
	}
	public void setDetailResponseCode(String detailResponseCode) {
		this.detailResponseCode = detailResponseCode;
	}
	public String getDetailResponseMesage() {
		return detailResponseMesage;
	}
	public void setDetailResponseMesage(String detailResponseMesage) {
		this.detailResponseMesage = detailResponseMesage;
	}
	public String getAuthNumber() {
		return authNumber;
	}
	public void setAuthNumber(String authNumber) {
		this.authNumber = authNumber;
	}
	public String getAuthDate() {
		return authDate;
	}
	public void setAuthDate(String authDate) {
		this.authDate = authDate;
	}
	public String getQuota() {
		return quota;
	}
	public void setQuota(String quota) {
		this.quota = quota;
	}
	public String getCardCompanyCode() {
		return cardCompanyCode;
	}
	public void setCardCompanyCode(String cardCompanyCode) {
		this.cardCompanyCode = cardCompanyCode;
	}
	
}
