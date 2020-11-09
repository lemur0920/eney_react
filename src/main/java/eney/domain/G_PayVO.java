package eney.domain;

import java.util.List;
import java.util.Map;

import eney.util.DateUtil;
import com.galaxia.api.util.ChecksumUtil;

/* 결제 호출부 + DB에 기록하는 부분이 섞여 있음 */
public class G_PayVO {
	/*API VALUABLE*/
	//상용 SID
//	private String serviceId = "M1509350";
	//TEST SID
	private String serviceId = "glx_api";
	private String orderId;
	private String orderDate;
	private String userId;
	private String userName;
	private String itemName;
	private String itemCode;
	private String amount;
	private String userIp;
	private String returnUrl;
	//private String returnUrl = "http://localhost:8080/eney/return.jsp";
	//private String returnUrl = "http://www.eney.co.kr/return.jsp";
	private String checkSum;
	private String temp;
	private String message;
	private String data1;
	private String data2;
	private String itemCate;
	private String service_code;
	private String transactionId;
	
	/*CUSTOM VALUABLE*/
	private int payment_id;
	private String pay_method;
	private String status;
	private String accumulate_point;
	private String expected_balance_point;
	private List<Map<String,String>> data1_list;
	
//	orderDate = year + month + date + hour + minute + second ; //주문일시
//	orderId = "" + orderDate ;  //주문번호
//	userName = "홍길동";
//	itemName = "테스트상품명";
//	itemCode = "TEST_CD1";
//	userIp = "127.0.0.1";
//	temp = serviceId + orderId + amount
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
//	public String getOrderId() {
//		return "test_" + DateUtil.getTodateString("yyyyMMddhhmmss");
//	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getOrderDate() {
		return DateUtil.getTodateString();
	}
	public void setOrderDate(String orderDate) {
		this.orderDate = orderDate;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public String getItemCode() {
		return itemCode;
	}
	public void setItemCode(String itemCode) {
		this.itemCode = itemCode;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getUserIp() {
		return userIp;
	}
	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}
	public String getReturnUrl() {
		//로컬(development)
		//return this.returnUrl = "http://localhost:8080/ROOT/payment/"+getPay_method()+"/return.do";
		//개발(beta)
		//return this.returnUrl = "http://192.168.0.101:8080/payment/"+getPay_method()+"/return.do";
		//운영(production)
		return this.returnUrl = "https://www.eney.co.kr/payment/"+getPay_method()+"/return.do";
	}
	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}
	public String getCheckSum() throws Exception {
		return ChecksumUtil.genCheckSum(getTemp());
	}
	/*public String getCheckSum() throws Exception {
		return checkSum;
	}*/
	public void setCheckSum(String checkSum) {
		this.checkSum = checkSum;
	}
	public String getTemp() {
		return getServiceId() + getOrderId() + getAmount();
	}
	public void setTemp(String temp) {
		this.temp = temp;
	}
	public int getPayment_id() {
		return payment_id;
	}
	public void setPayment_id(int payment_id) {
		this.payment_id = payment_id;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getPay_method() {
		return pay_method;
	}
	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getData1() {
		return data1;
	}
	public void setData1(String data1) {
		this.data1 = data1;
	}
	public String getData2() {
		return data2;
	}
	public void setData2(String data2) {
		this.data2 = data2;
	}
	public String getItemCate() {
		return itemCate;
	}
	public void setItemCate(String itemCate) {
		this.itemCate = itemCate;
	}
	@Override
	public String toString() {
		return "G_PayVO [serviceId=" + serviceId + ", orderId=" + orderId
				+ ", orderDate=" + orderDate + ", userId=" + userId
				+ ", userName=" + userName + ", itemName=" + itemName
				+ ", itemCode=" + itemCode + ", amount=" + amount + ", userIp="
				+ userIp + ", returnUrl=" + returnUrl + ", checkSum="
				+ checkSum + ", temp=" + temp + ", message=" + message
				+ ", data1=" + data1 + ", data2=" + data2 + ", itemCate="
				+ itemCate + ", payment_id=" + payment_id + ", pay_method="
				+ pay_method + ", status=" + status + "]";
	}
	public String getService_code() {
		return service_code;
	}
	public void setService_code(String service_code) {
		this.service_code = service_code;
	}
	public String getAccumulate_point() {
		return accumulate_point;
	}
	public void setAccumulate_point(String accumulate_point) {
		this.accumulate_point = accumulate_point;
	}
	public String getExpected_balance_point() {
		return expected_balance_point;
	}
	public void setExpected_balance_point(String expected_balance_point) {
		this.expected_balance_point = expected_balance_point;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public List<Map<String,String>> getData1_list() {
		return data1_list;
	}
	public void setData1_list(List<Map<String,String>> data1_list) {
		this.data1_list = data1_list;
	}
	
}
