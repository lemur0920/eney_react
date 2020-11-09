package eney.domain;

import java.util.Date;

import eney.domain.PaymentLogVo.PayStatus;

public class UserHometaxVO {
	private int idx;
	private String userid;
	private String tax_number;
	private int hometax_amount;
	private int hometax_vat;
	private String hometax_service_name;
	private Date hometax_issue_date;
	private int invoice_idx;
	private String generate_yn;
	private PaymentLogVo.PayStatus pay_state;
	private Integer hometax_idx;
	private String invoice_issue_date;
	private String pay_history;
	public int getIdx() {
		return idx;
	}
	public void setIdx(int idx) {
		this.idx = idx;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTax_number() {
		return tax_number;
	}
	public void setTax_number(String tax_number) {
		this.tax_number = tax_number;
	}
	public int getHometax_amount() {
		return hometax_amount;
	}
	public void setHometax_amount(int hometax_amount) {
		this.hometax_amount = hometax_amount;
	}
	public int getHometax_vat() {
		return hometax_vat;
	}
	public void setHometax_vat(int hometax_vat) {
		this.hometax_vat = hometax_vat;
	}
	public String getHometax_service_name() {
		return hometax_service_name;
	}
	public void setHometax_service_name(String hometax_service_name) {
		this.hometax_service_name = hometax_service_name;
	}
	public Date getHometax_issue_date() {
		return hometax_issue_date;
	}
	public void setHometax_issue_date(Date hometax_issue_date) {
		this.hometax_issue_date = hometax_issue_date;
	}
	public int getInvoice_idx() {
		return invoice_idx;
	}
	public void setInvoice_idx(int invoice_idx) {
		this.invoice_idx = invoice_idx;
	}
	public String getGenerate_yn() {
		return generate_yn;
	}
	public void setGenerate_yn(String generate_yn) {
		this.generate_yn = generate_yn;
	}
	public PaymentLogVo.PayStatus getPay_state() {
		return pay_state;
	}
	public void setPay_state(PaymentLogVo.PayStatus pay_state) {
		this.pay_state = pay_state;
	}
	public Integer getHometax_idx() {
		return hometax_idx;
	}
	public void setHometax_idx(Integer hometax_idx) {
		this.hometax_idx = hometax_idx;
	}
	public String getInvoice_issue_date() {
		return invoice_issue_date;
	}
	public void setInvoice_issue_date(String invoice_issue_date) {
		this.invoice_issue_date = invoice_issue_date;
	}
	public String getPay_history() {
		return pay_history;
	}
	public void setPay_history(String pay_history) {
		this.pay_history = pay_history;
	}
	@Override
	public String toString() {
		return "UserHometaxVO [idx=" + idx + ", userid=" + userid + ", tax_number=" + tax_number + ", hometax_amount="
				+ hometax_amount + ", hometax_vat=" + hometax_vat + ", hometax_service_name=" + hometax_service_name
				+ ", hometax_issue_date=" + hometax_issue_date + ", invoice_idx=" + invoice_idx + ", generate_yn="
				+ generate_yn + ", pay_state=" + pay_state + ", hometax_idx=" + hometax_idx + ", invoice_issue_date="
				+ invoice_issue_date + ", pay_history=" + pay_history + "]";
	}
}
