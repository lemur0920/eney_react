package eney.domain;

/**
 * 판매중인 서비스 내용
 */
public class ItemVo {
	
	public enum ItemCategory {utilization_050, utilization_ad, epoint, adpower, refund, colorring_music,
		register_050, web_hosting, patchcall,patchcall_other,callback_sms,record, patchcall_bi, coupon};
	
	private Integer service_id;
	private String gubun;
	private ItemCategory category;
	private String service_name;
	private String service_code;
	private Integer sale_price;
	private Integer normal_price;
	private String description;
	private Integer service_period;
	private Integer discount_rate;
	
	public Integer getService_id() {
		return service_id;
	}
	public void setService_id(Integer service_id) {
		this.service_id = service_id;
	}
	public String getGubun() {
		return gubun;
	}
	public void setGubun(String gubun) {
		this.gubun = gubun;
	}
	public ItemCategory getCategory() {
		return category;
	}
	public void setCategory(ItemCategory category) {
		this.category = category;
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
	public Integer getSale_price() {
		return sale_price;
	}
	public void setSale_price(Integer sale_price) {
		this.sale_price = sale_price;
	}
	public Integer getNormal_price() {
		return normal_price;
	}
	public void setNormal_price(Integer normal_price) {
		this.normal_price = normal_price;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Integer getService_period() {
		return service_period;
	}
	public void setService_period(Integer service_period) {
		this.service_period = service_period;
	}
	public Integer getDiscount_rate() {
		return discount_rate;
	}
	public void setDiscount_rate(Integer discount_rate) {
		this.discount_rate = discount_rate;
	}
	
	@Override
	public String toString() {
		return "ItemVo [service_id=" + service_id + ", gubun=" + gubun + ", category=" + category + ", service_name="
				+ service_name + ", service_code=" + service_code + ", sale_price=" + sale_price + ", normal_price="
				+ normal_price + ", description=" + description + ", service_period=" + service_period
				+ ", discount_rate=" + discount_rate + "]";
	}
}
