package eney.domain;

import java.util.List;

public class LineChartDataVO {
	private String label = "My Second dataset";
	private String fillColor = "rgba(213,243,221,0.2)";
	private String strokeColor = "rgba(50,197,84,1)";
	private String pointColor = "rgba(0,165,16,1)";
	private String pointStrokeColor = "#10a831";
	private String pointHighlightFill = "#10a831";
	private String pointHighlightStroke = "rgba(151,187,205,1)";
	private List<LineChartDataSubVO> dataList;
	
	public String getLabel() {
		return label;
	}
	public void setLabel(String label) {
		this.label = label;
	}
	public String getFillColor() {
		return fillColor;
	}
	public void setFillColor(String fillColor) {
		this.fillColor = fillColor;
	}
	public String getStrokeColor() {
		return strokeColor;
	}
	public void setStrokeColor(String strokeColor) {
		this.strokeColor = strokeColor;
	}
	public String getPointColor() {
		return pointColor;
	}
	public void setPointColor(String pointColor) {
		this.pointColor = pointColor;
	}
	public String getPointStrokeColor() {
		return pointStrokeColor;
	}
	public void setPointStrokeColor(String pointStrokeColor) {
		this.pointStrokeColor = pointStrokeColor;
	}
	public String getPointHighlightFill() {
		return pointHighlightFill;
	}
	public void setPointHighlightFill(String pointHighlightFill) {
		this.pointHighlightFill = pointHighlightFill;
	}
	public String getPointHighlightStroke() {
		return pointHighlightStroke;
	}
	public void setPointHighlightStroke(String pointHighlightStroke) {
		this.pointHighlightStroke = pointHighlightStroke;
	}
	public List<LineChartDataSubVO> getDataList() {
		return dataList;
	}
	public void setDataList(List<LineChartDataSubVO> dataList) {
		this.dataList = dataList;
	}
	@Override
	public String toString() {
		return "LineChartDataVO [label=" + label + ", fillColor=" + fillColor
				+ ", strokeColor=" + strokeColor + ", pointColor=" + pointColor
				+ ", pointStrokeColor=" + pointStrokeColor
				+ ", pointHighlightFill=" + pointHighlightFill
				+ ", pointHighlightStroke=" + pointHighlightStroke
				+ ", dataList=" + dataList + "]";
	}
}
