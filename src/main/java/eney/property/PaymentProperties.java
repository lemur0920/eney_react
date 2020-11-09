package eney.property;

public class PaymentProperties {
	
	private String serviceId;
	private Boolean testServer;
	private Integer giftcardCommission;
	private String paymentRequestUrlPrefix;
	private String paymentRequestUrlSuffix;
	private String returnUrlPrefix;
	private String returnUrlSuffix;
	
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public Boolean getTestServer() {
		return testServer;
	}
	public void setTestServer(Boolean testServer) {
		this.testServer = testServer;
	}
	public Integer getGiftcardCommission() {
		return giftcardCommission;
	}
	public void setGiftcardCommission(Integer giftcardCommission) {
		this.giftcardCommission = giftcardCommission;
	}
	public String getPaymentRequestUrlPrefix() {
		return paymentRequestUrlPrefix;
	}
	public void setPaymentRequestUrlPrefix(String paymentRequestUrlPrefix) {
		this.paymentRequestUrlPrefix = paymentRequestUrlPrefix;
	}
	public String getPaymentRequestUrlSuffix() {
		return paymentRequestUrlSuffix;
	}
	public void setPaymentRequestUrlSuffix(String paymentRequestUrlSuffix) {
		this.paymentRequestUrlSuffix = paymentRequestUrlSuffix;
	}
	public String getReturnUrlPrefix() {
		return returnUrlPrefix;
	}
	public void setReturnUrlPrefix(String returnUrlPrefix) {
		this.returnUrlPrefix = returnUrlPrefix;
	}
	public String getReturnUrlSuffix() {
		return returnUrlSuffix;
	}
	public void setReturnUrlSuffix(String returnUrlSuffix) {
		this.returnUrlSuffix = returnUrlSuffix;
	}
	
}
