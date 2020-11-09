package eney.domain;

public class EpointRefundVO extends Pagination {
	private int refund_id;
	private String userid;
	private String serial_number;
	private int epoint_present;
	private int epoint_to_refund;
	private int amount_to_refund;
	private int epoint_balance;
	private String which_bank;
	private String account_holder;
	private String account_number;
	private String phone_number;
	private String charge_date;
	private String complete_date;
	private String status;

	public int getEpoint_present() {
		return epoint_present;
	}

	public void setEpoint_present(int epoint_present) {
		this.epoint_present = epoint_present;
	}

	public int getEpoint_to_refund() {
		return epoint_to_refund;
	}

	public void setEpoint_to_refund(int epoint_to_refund) {
		this.epoint_to_refund = epoint_to_refund;
	}

	public String getWhich_bank() {
		return which_bank;
	}

	public void setWhich_bank(String which_bank) {
		this.which_bank = which_bank;
	}

	public String getAccount_holder() {
		return account_holder;
	}

	public void setAccount_holder(String account_holder) {
		this.account_holder = account_holder;
	}

	public String getAccount_number() {
		return account_number;
	}

	public void setAccount_number(String account_number) {
		this.account_number = account_number;
	}

	public String getPhone_number() {
		return phone_number;
	}

	public void setPhone_number(String phone_number) {
		this.phone_number = phone_number;
	}

	public int getRefund_id() {
		return refund_id;
	}

	public void setRefund_id(int refund_id) {
		this.refund_id = refund_id;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public int getAmount_to_refund() {
		return amount_to_refund;
	}

	public void setAmount_to_refund(int amount_to_refund) {
		this.amount_to_refund = amount_to_refund;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getEpoint_balance() {
		return epoint_balance;
	}

	public void setEpoint_balance(int epoint_balance) {
		this.epoint_balance = epoint_balance;
	}

	public String getCharge_date() {
		return charge_date;
	}

	public void setCharge_date(String charge_date) {
		this.charge_date = charge_date;
	}

	public String getComplete_date() {
		return complete_date;
	}

	public void setComplete_date(String complete_date) {
		this.complete_date = complete_date;
	}

	public String getSerial_number() {
		return serial_number;
	}

	public void setSerial_number(String serial_number) {
		this.serial_number = serial_number;
	}

}