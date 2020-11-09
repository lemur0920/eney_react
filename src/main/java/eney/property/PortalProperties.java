package eney.property;

public class PortalProperties {
	
	private String portalUrl;
	private String portalDomain;
	private Integer apiTokenLen;
	private String mailHost;
	private Integer mailPort;
	private FileProperties fileProperties;
	private PaymentProperties paymentProperties;
	private UserCertifyProperties userCertifyProperties;


	
	public String getPortalUrl() {
		return portalUrl;
	}
	public void setPortalUrl(String portalUrl) {
		this.portalUrl = portalUrl;
	}
	public String getPortalDomain() {
		return portalDomain;
	}
	public void setPortalDomain(String portalDomain) {
		this.portalDomain = portalDomain;
	}
	public Integer getApiTokenLen() {
		return apiTokenLen;
	}
	public void setApiTokenLen(Integer apiTokenLen) {
		this.apiTokenLen = apiTokenLen;
	}
	public String getMailHost() {
		return mailHost;
	}
	public void setMailHost(String mailHost) {
		this.mailHost = mailHost;
	}
	public Integer getMailPort() {
		return mailPort;
	}
	public void setMailPort(Integer mailPort) {
		this.mailPort = mailPort;
	}
	public FileProperties getFileProperties() {
		return fileProperties;
	}
	public void setFileProperties(FileProperties fileProperties) {
		this.fileProperties = fileProperties;
	}
	public PaymentProperties getPaymentProperties() {
		return paymentProperties;
	}
	public void setPaymentProperties(PaymentProperties paymentProperties) {
		this.paymentProperties = paymentProperties;
	}
	public UserCertifyProperties getUserCertifyProperties() {
		return userCertifyProperties;
	}
	public void setUserCertifyProperties(UserCertifyProperties userCertifyProperties) {
		this.userCertifyProperties = userCertifyProperties;
	}
	
	@Override
	public String toString() {
		return "PortalProperties [portalUrl=" + portalUrl + ", portalDomain=" + portalDomain + ", apiTokenLen="
				+ apiTokenLen + ", mailHost=" + mailHost + ", mailPort=" + mailPort + ", fileProperties="
				+ fileProperties + ", paymentProperties=" + paymentProperties + ", userCertifyProperties="
				+ userCertifyProperties + "]";
	}
}
