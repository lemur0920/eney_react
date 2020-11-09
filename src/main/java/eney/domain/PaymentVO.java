package eney.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;


@ToString
public class PaymentVO extends PageVO{
	
	private int service_id;
	private int rownum;
	private String category;
	private String gubun;
	private String service_name;
	private String service_code;
	private int sale_price;
	private int normal_price;
	private String description;
	private String service_period;
	private int discount_rate;
	@Getter
	@Setter
	private int price;

	@Getter
	@Setter
	private String install_cost;

	@Getter
	@Setter
	private String charge_basis;

	@Getter
	@Setter
	private String service_type;
	
	private String service_data;
	private String userid;
	
	private int orderId;
	private String order;
	private String order_desc="true";
	
	private String taxIncludedAmount;
	
	private int charge_epoint;
	private int balance_epoint;
	private String charge_date;
	
	private String status;
	private String item_cate;
	private String amount;
	private String pay_method;
	private String cancel_date;
	private String message;
	
	private int epoint_log_id;
	private String serial_number;
	
	private String transactionId;
	@Getter
	@Setter
	private String pay_session_id;

	public int getRownum() {
		return rownum;
	}

	public void setRownum(int rownum) {
		this.rownum = rownum;
	}
	
	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public int getService_id() {
		return service_id;
	}

	public void setService_id(int service_id) {
		this.service_id = service_id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getGubun() {
		return gubun;
	}

	public void setGubun(String gubun) {
		this.gubun = gubun;
	}

	public String getService_name() {
		return service_name;
	}

	public void setService_name(String service_name) {
		this.service_name = service_name;
	}

	public String getService_code() {
		return service_code;
	}

	public void setService_code(String service_code) {
		this.service_code = service_code;
	}

	public int getSale_price() {
		return sale_price;
	}

	public void setSale_price(int sale_price) {
		this.sale_price = sale_price;
	}

	public int getNormal_price() {
		return normal_price;
	}

	public void setNormal_price(int normal_price) {
		this.normal_price = normal_price;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getService_period() {
		return service_period;
	}

	public void setService_period(String service_period) {
		this.service_period = service_period;
	}

	public String getService_data() {
		return service_data;
	}

	public void setService_data(String service_data) {
		this.service_data = service_data;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrder_desc() {
		return order_desc;
	}

	public void setOrder_desc(String order_desc) {
		this.order_desc = order_desc;
	}

	public String getTaxIncludedAmount() {
		return StringUtils.substringBefore(String.valueOf(sale_price + sale_price*0.1), ".");
	}
	public void setTaxIncludedAmount(String taxIncludedAmount) {
		this.taxIncludedAmount = taxIncludedAmount;
	}

	public int getCharge_epoint() {
		return charge_epoint;
	}

	public void setCharge_epoint(int charge_epoint) {
		this.charge_epoint = charge_epoint;
	}

	public int getBalance_epoint() {
		return balance_epoint;
	}

	public void setBalance_epoint(int balance_epoint) {
		this.balance_epoint = balance_epoint;
	}

	public String getCharge_date() {
		return charge_date;
	}

	public void setCharge_date(String charge_date) {
		this.charge_date = charge_date;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getItem_cate() {
		return item_cate;
	}

	public void setItem_cate(String item_cate) {
		this.item_cate = item_cate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getPay_method() {
		return pay_method;
	}

	public void setPay_method(String pay_method) {
		this.pay_method = pay_method;
	}

	public String getCancel_date() {
		return cancel_date;
	}

	public void setCancel_date(String cancel_date) {
		this.cancel_date = cancel_date;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getSerial_number() {
		return serial_number;
	}

	public void setSerial_number(String serialNumber) {
		this.serial_number = serialNumber;
	}
	
	public int getEpoint_log_id() {
		return epoint_log_id;
	}

	public void setEpoint_log_id(int epoint_log_id) {
		this.epoint_log_id = epoint_log_id;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public int getDiscount_rate() {
		return discount_rate;
	}

	public void setDiscount_rate(int discount_rate) {
		this.discount_rate = discount_rate;
	}

	
}
