package eney.domain;

public class CommonVO {
	public static final String CATEGORY_COLORING_SAMPLE_CATE = "COLORING_SAMPLE_CATE";
	public static final String CATEGORY_050_NUMBER_ASSIGNED = "050_NUMBER_ASSIGNED";
	public static final String CATEGORY_SWITCH_ALERT = "SWITCH_ALERT";
	public static final String CATEGORY_SWITCH_DEAD_ALERT = "CATEGORY_SWITCH_DEAD_ALERT";
	
	private int common_id;
	private String category;
	private String keyword;
	private String value;
	
	public int getCommon_id() {
		return common_id;
	}
	public void setCommon_id(int common_id) {
		this.common_id = common_id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "CommonVO [common_id=" + common_id + ", category=" + category
				+ ", key=" + keyword + ", value=" + value + "]";
	}
}
