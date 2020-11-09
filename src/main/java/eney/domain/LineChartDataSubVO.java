package eney.domain;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LineChartDataSubVO {
	private String date;
	private int value = (int) (Math.random()*100);
	public String getDate() throws ParseException {
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMddHHmmss");
		if(date.length()==8){
			date += "000000";
		}
		Date to = transFormat.parse(date);
		
		SimpleDateFormat transFormat2 = new SimpleDateFormat("MM/dd");
		String res = transFormat2.format(to);
		return res;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public int getValue() {
		return value;
	}
	public void setValue(int value) {
		this.value = value;
	}
	@Override
	public String toString() {
		return "LineChartDataSubVO [date=" + date + ", value=" + value + "]";
	}
}
