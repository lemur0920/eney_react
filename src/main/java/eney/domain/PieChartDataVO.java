package eney.domain;

public class PieChartDataVO {
	private int value;
	private String  color;
	private String highlight;
	private String label;
	private String latest_ss_date;
	
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	public String getColor() {
		return color;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public String getHighlight() {
		return highlight;
	}
	public void setHighlight(String highlight) {
		this.highlight = highlight;
	}
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	@Override
	public String toString() {
		return "{value:\"" + value + "\", color:\"" + color
				+ "\", highlight:\"" + highlight + "\", label:\"" + label + "\"}";
	}
	public String getLatest_ss_date() {
		return latest_ss_date;
	}
	public void setLatest_ss_date(String latest_ss_date) {
		this.latest_ss_date = latest_ss_date;
	}
}
