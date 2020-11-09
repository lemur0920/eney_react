package eney.domain;

/* 결제창 호출, 결과처리 부분이 섞여 있음 */
public class G_PayVO2 {
	private String SERVICE_ID;
	private String ORDER_ID;
	private String ORDER_DATE;
	private String USER_IP;
	private String ITEM_CODE;
	private String USER_ID;
	private String INSTALLMENT_PERIOD;
	private String RETURN_URL;
	private String CHECK_SUM;
	private String TRANSACTION_ID;
	private String message;
	private String detailMessage;
	
	/*custom log index*/
	private int LOG_ID;
	
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
	public String getUSER_IP() {
		return USER_IP;
	}
	public void setUSER_IP(String uSER_IP) {
		USER_IP = uSER_IP;
	}
	public String getITEM_CODE() {
		return ITEM_CODE;
	}
	public void setITEM_CODE(String iTEM_CODE) {
		ITEM_CODE = iTEM_CODE;
	}
	public String getUSER_ID() {
		return USER_ID;
	}
	public void setUSER_ID(String uSER_ID) {
		USER_ID = uSER_ID;
	}
	public String getINSTALLMENT_PERIOD() {
		return INSTALLMENT_PERIOD;
	}
	public void setINSTALLMENT_PERIOD(String iNSTALLMENT_PERIOD) {
		INSTALLMENT_PERIOD = iNSTALLMENT_PERIOD;
	}
	public String getRETURN_URL() {
		return RETURN_URL;
	}
	public void setRETURN_URL(String rETURN_URL) {
		RETURN_URL = rETURN_URL;
	}
	public String getCHECK_SUM() {
		return CHECK_SUM;
	}
	public void setCHECK_SUM(String cHECK_SUM) {
		CHECK_SUM = cHECK_SUM;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getDetailMessage() {
		return detailMessage;
	}
	public void setDetailMessage(String detailMessage) {
		this.detailMessage = detailMessage;
	}
	public String getTRANSACTION_ID() {
		return TRANSACTION_ID;
	}
	public void setTRANSACTION_ID(String tRANSACTION_ID) {
		TRANSACTION_ID = tRANSACTION_ID;
	}
	public int getLOG_ID() {
		return LOG_ID;
	}
	public void setLOG_ID(int lOG_ID) {
		LOG_ID = lOG_ID;
	}
	@Override
	public String toString() {
		return "G_PayVO2 [SERVICE_ID=" + SERVICE_ID + ", ORDER_ID=" + ORDER_ID
				+ ", ORDER_DATE=" + ORDER_DATE + ", USER_IP=" + USER_IP
				+ ", ITEM_CODE=" + ITEM_CODE + ", USER_ID=" + USER_ID
				+ ", INSTALLMENT_PERIOD=" + INSTALLMENT_PERIOD
				+ ", RETURN_URL=" + RETURN_URL + ", CHECK_SUM=" + CHECK_SUM
				+ ", TRANSACTION_ID=" + TRANSACTION_ID + ", message=" + message
				+ ", detailMessage=" + detailMessage + ", LOG_ID=" + LOG_ID
				+ "]";
	}
}
