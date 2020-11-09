package eney.domain;

import java.util.Date;

import eney.domain.ItemVo.ItemCategory;

public class PaymentLogVo extends PageVO {

	public static final String DATA_SPLIT_CHAR = ",";
	
	public enum PayMethod {credit, mobile, happymoney, epoint, booklife, culture, coupon};
	public enum PayStatus {standby, paying, cancel, approve, error};
	
	private Integer orderid;
	private String userid;
	private String service_code;
	private String service_name;
	private Integer amount;
	private PayMethod pay_method;
	private Date order_date;
	private Date cancel_date;
	private PayStatus status;
	private String message;
	private String check_sum;
	private String data1;
	private String data2;
	private ItemCategory item_cate;
	private String serial_number;
	
	public Integer getOrderid() {
		return orderid;
	}
	public void setOrderid(Integer orderid) {
		this.orderid = orderid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getService_code() {
		return service_code;
	}
	public void setService_code(String service_code) {
		this.service_code = service_code;
	}
	public String getService_name() {
		return service_name;
	}
	public void setService_name(String service_name) {
		this.service_name = service_name;
	}
	public Integer getAmount() {
		return amount;
	}
	public void setAmount(Integer amount) {
		this.amount = amount;
	}
	public PayMethod getPay_method() {
		return pay_method;
	}
	public void setPay_method(PayMethod pay_method) {
		this.pay_method = pay_method;
	}
	public Date getOrder_date() {
		return order_date;
	}
	public void setOrder_date(Date order_date) {
		this.order_date = order_date;
	}
	public Date getCancel_date() {
		return cancel_date;
	}
	public void setCancel_date(Date cancel_date) {
		this.cancel_date = cancel_date;
	}
	public PayStatus getStatus() {
		return status;
	}
	public void setStatus(PayStatus status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getCheck_sum() {
		return check_sum;
	}
	public void setCheck_sum(String check_sum) {
		this.check_sum = check_sum;
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
	public ItemCategory getItem_cate() {
		return item_cate;
	}
	public void setItem_cate(ItemCategory item_cate) {
		this.item_cate = item_cate;
	}
	public String getSerial_number() {
		return serial_number;
	}
	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}
}
