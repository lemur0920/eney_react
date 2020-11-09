package eney.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.util.ArrayList;

//import org.apache.commons.lang.time.DateUtils;

public class DateUtil {

//	public static Date getDate(int year, int month, boolean truncate) {
//    	Calendar cal = Calendar.getInstance();
//    	cal.set(Calendar.YEAR, year);
//    	cal.set(Calendar.MONTH, month);
//    	
//    	if( truncate ) {
//    		cal = DateUtils.truncate(cal, Calendar.MONTH);
//    	}
//    	
//    	return cal.getTime();
//    }

//    public static Date getDate(int year, int month) {
//    	return getDate(year, month, false); 
//    }

	public static String getTodateString(){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat f = new SimpleDateFormat("yyyyMMddhhmmss");
		return f.format(cal.getTime());
	}


	
	public static String getTodateString2(){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		return f.format(cal.getTime());
	}
	
	public static String getTodateStringByMonth(){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat f = new SimpleDateFormat("MM");
		return f.format(cal.getTime());
	}
	
	public static String getTodateString(String format){
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat f = new SimpleDateFormat(format);
		return f.format(cal.getTime());
	}

	public static String getDateString(Date date, String format){
		SimpleDateFormat f = new SimpleDateFormat(format);
		return f.format(date);
	}

	public static Date getStringToDate(String dateString)  {
		SimpleDateFormat transFormat = new SimpleDateFormat("yyyyMMdd");
		Date date = null;
		try {
			date = transFormat.parse(dateString);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return date;
	}
	
	public static boolean getCompareDate(String date) throws ParseException{
		String date_s = date;
		Date n_date = new Date();
		SimpleDateFormat dt = new SimpleDateFormat("yyyymmdd");
							
		return n_date.after(dt.parse(date_s));
	}

	/** 이번주 월요일 리턴
	 * */
	public static String getCurMonday(){
		SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
		Calendar c = Calendar.getInstance();
		c.set(Calendar.DAY_OF_WEEK,Calendar.MONDAY);
		return formatter.format(c.getTime());
	}

	/** 오늘 날짜 리턴
	* */
	public static String getStringToday(){
		SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyyMMdd");
		Calendar c1 = Calendar.getInstance();
		String strToday = formatter.format(c1.getTime());
		return strToday;
	}

	/**
	 * 두날짜 사이의 일수를 리턴
	 * @param fromDate yyyyMMdd 형식의 시작일
	 * @param toDate yyyyMMdd 형식의 종료일
	 * @return 두날짜 사이의 일수
	 */
	public static int getDiffDayCount(String fromDate, String toDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		try {
			return (int) ((sdf.parse(toDate).getTime() - sdf.parse(fromDate)
					.getTime()) / 1000 / 60 / 60 / 24);
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 시작일부터 종료일까지 사이의 날짜를 배열에 담아 리턴
	 * ( 시작일과 종료일을 모두 포함한다 )
	 * @param fromDate yyyyMMdd 형식의 시작일
	 * @param toDate yyyyMMdd 형식의 종료일
	 * @return yyyyMMdd 형식의 날짜가 담긴 배열
	 */
	public static String[] getDiffDays(String fromDate, String toDate) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

		Calendar cal = Calendar.getInstance();

		try {
			cal.setTime(sdf.parse(fromDate));
		} catch (Exception e) {
		}

		int count = getDiffDayCount(fromDate, toDate);

		// 시작일부터
		cal.add(Calendar.DATE, -1);

		// 데이터 저장
		ArrayList<String> list = new ArrayList<String>();

		for (int i = 0; i <= count; i++) {
			cal.add(Calendar.DATE, 1);

			list.add(sdf.format(cal.getTime()));
		}

		String[] result = new String[list.size()];

		list.toArray(result);

		System.out.println(result);

		return result;
	}
	
    /**
	 * 1년 후의 날을 구한다.
	 */
	public static Date getNextYear(Date date) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	
    	cal.add(Calendar.YEAR, 1);
    	
    	return cal.getTime();
    }

	/**
	 * 1년 전의 날을 구한다.
	 */
    public static Date getPreviousYear(Date date) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	
    	cal.add(Calendar.YEAR, -1);
    	
    	return cal.getTime();
    }
    
    /**
	 * 한달 후의 날을 구한다.
	 */
    public static Date getNextMonth(Date date) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	
    	cal.add(Calendar.MONTH, 1);
    	
    	return cal.getTime();
    }
    
    /**
	 * 한달 전의 날을 구한다.
	 */
	public static Date getPreviousMonth(Date date) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	
    	cal.add(Calendar.MONTH, -1);
    	
    	return cal.getTime();
    }
	
	/**
	 * 7일 후의 날을 구한다.
	 */
	public static Date getNextWeek(Date date) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	
    	cal.add(Calendar.DATE, 7);
    	
    	return cal.getTime();
    }
    
	/**
	 * 7일전의 날을 구한다.
	 */
	public static Date getPreviousWeek(Date date) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	
    	cal.add(Calendar.DATE, -7);
    	
    	return cal.getTime();
    }
	
	/**
	 * 다음 날을 구한다.
	 */
	public static Date getNextDate(Date date) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	
    	cal.add(Calendar.DATE, 1);
    	
    	return cal.getTime();
    }
	
	/**
	 * 몇일 후의 날을 구한다.
	 */
	public static Date getNextDate(Date date, int i) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	
    	cal.add(Calendar.DATE, i);
    	
    	return cal.getTime();
    }
	
	public static String getNextDate(String date_str) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = format.parse(date_str);
		
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	
    	cal.add(Calendar.DATE, 1);
    	
    	return format.format(cal.getTime());
    }
    
	/**
	 * 하루 전 날을 구한다.
	 */
	public static Date getPreviousDate(Date date) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	
    	cal.add(Calendar.DATE, -1);
    	
    	return cal.getTime();
    }
	
	/**
	 * 몇일 전 날을 구한다.
	 */
	public static Date getPreviousDate(Date date,int i) {
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	
    	cal.add(Calendar.DATE, -i);
    	
    	return cal.getTime();
    }
	
	/**
	 * 해당 주의 첫번째 날을 구한다.
	 */
	public static Date getFirstDateOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	
    	int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    	cal.add(Calendar.DATE, (dayOfWeek-1)*-1);
    	
    	return cal.getTime();
	}
	
	/**
	 * 해당 주의 마지막 날을 구한다.
	 */
	public static Date getLastDateOfWeek(Date date) {
		Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	
    	int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
    	cal.add(Calendar.DATE, 7-dayOfWeek);
    	
    	return cal.getTime();
	}
	
	/**
	 * 해당 연도 달의 첫번째 날을 구한다.
	 */
	public static Date getFirstDateOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	
    	cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}
	
	public static Date getToday() {
    	Calendar cal = Calendar.getInstance();
    	SimpleDateFormat sd = new SimpleDateFormat("yyyyMMdd");
    	
    	cal.setTime(new Date());    	
    	return cal.getTime();
    }
	
	/**
	 * 해당 연도 달의 첫번째 날을 구한다.
	 */
//	public static Date getFirstDateOfMonth(int year, int month) {
//		Calendar cal = Calendar.getInstance();
//    	
//		cal.set(Calendar.YEAR, year);
//    	cal.set(Calendar.MONTH, month-1);
//    	cal.set(Calendar.DATE, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
//		
//		return minimized(cal.getTime());
//	}
	
	/**
	 * 해당 연도 달의 마지막 날을 구한다.
	 */
	public static Date getLastDateOfMonth(Date date) {
		Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	
    	cal.set(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}
	
	/**
	 * 해당 연도 달의 마지막 날을 구한다.
	 */
	public static Date getLastDateOfMonth(int year, int month) {
		Calendar cal = Calendar.getInstance();
    	
		cal.set(Calendar.YEAR, year);
    	cal.set(Calendar.MONTH, month-1);
    	cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		return maximize(cal.getTime());
	}
	
	/**
	 * 시,분,초를 모두 최소치로 초기화한다.
	 */
//	public static Date minimized(Date date) {
//		return DateUtils.truncate(date, Calendar.DATE);
//	}
	
	/**
	 * 시,분,초를 모두 최대치로 초기화한다.
	 */
	public static Date maximize(Date date) {
		Calendar cal = Calendar.getInstance();
    	cal.setTime(date);
    	
    	cal.set(Calendar.HOUR_OF_DAY, 23);
    	cal.set(Calendar.MINUTE, 59);
    	cal.set(Calendar.SECOND, 59);
		
		return cal.getTime();
	}
	
	/**
	 * 두 날짜 사이의 차이를 구한다.
	 */
	
	public static long diffOfDate(String begin, String end) throws Exception {
	    SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	 
	    Date beginDate = formatter.parse(begin);
	    Date endDate = formatter.parse(end);
	 
	    long diff = endDate.getTime() - beginDate.getTime();
	    long diffDays = diff / (24 * 60 * 60 * 1000);
	 
	    return diffDays;
	}
	
	/**
	 * 두 날짜(시간) 사이의 차이를 구한다.
	 */
	public static long diffOfTime(String begin, String end) throws Exception{
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		 
	    Date beginDate = formatter.parse(begin);
	    Date endDate = formatter.parse(end);
	 
	    long diff = endDate.getTime() - beginDate.getTime();
	 
	    return diff / 1000 ;
	}
	
	
	/**
	 * 문자열로 되어있는 날짜 계산 메소드
	 * @param date_str 계산 대상이 될 날짜 문자열
	 * @param filed 계산할 필드(y: 년, M: 달, d: 일)
	 * @param addNumber 연산할 수 (음수는 이전, 양수는 이후로 연산됨)
	 * @return 계산된 날짜 문자열
	 * @throws ParseException
	 */
	public static String getCalculatorDateToString(Date targetDate, String filed, Integer addNumber) throws ParseException {
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		
    	Calendar cal = Calendar.getInstance();
    	cal.setTime(targetDate);
    	switch (filed) {
		case "y":
		case "Y":
			cal.add(Calendar.YEAR, addNumber);
			break;
		case "M":
			cal.add(Calendar.MONTH, addNumber);
			break;
		case "d":
		case "D":
			cal.add(Calendar.DATE, addNumber);
			break;
		default:
			break;
		}
    	
    	return format.format(cal.getTime());
    }

	/** ga:week를 월 주차로 변경
	 * https://m.blog.naver.com/PostView.nhn?blogId=odh1414&logNo=150172218194&proxyReferer=https%3A%2F%2Fwww.google.co.kr%2F
	 * @param yyyymmdd
	 * @return
	 * @throws Exception
	 */
	public static Integer weekCalendar(String yyyymmdd) throws Exception{

		String yy = yyyymmdd.substring(0,4);
		String mm = yyyymmdd.substring(4,6);
		String dd = yyyymmdd.substring(6,8);

		int year = Integer.parseInt(yy);
		int month = Integer.parseInt(mm) -1;
		int day = Integer.parseInt(dd);

		int monday = 0;
		int friday = 0;

		Calendar to_day = Calendar.getInstance();

		to_day.set(year,month,day);

		Date time = to_day.getTime();

		int today_week = to_day.get(Calendar.DAY_OF_WEEK);

		if(today_week == Calendar.MONDAY){
			monday = day;
			friday = day + (Calendar.FRIDAY - Calendar.MONDAY);
		}else if(today_week == Calendar.FRIDAY){
			monday = day - (Calendar.FRIDAY - Calendar.MONDAY);
			friday = day;
		}else if(today_week == Calendar.SUNDAY){
			monday = day - Calendar.SUNDAY;
			friday = day + (Calendar.FRIDAY - Calendar.SUNDAY);

		}else if(today_week == Calendar.SATURDAY){
			monday = day - (Calendar.SATURDAY - Calendar.MONDAY);
			friday = day - (Calendar.SATURDAY - Calendar.FRIDAY);
		}else{
			monday = day - (today_week - Calendar.MONDAY);
			friday = day + (Calendar.FRIDAY - today_week);
		}

		to_day = Calendar.getInstance();
		to_day.set(year, month, monday);

		int this_week = to_day.get(Calendar.WEEK_OF_MONTH);

		return this_week;

	}

	//ga:dayOfWeekName을 한글 요일명으로 변경
	public static String getWeekNameKr(String weekName) {
		String weekNamekr = null;

		switch (weekName){
			case "Monday":weekNamekr = "1.월요일";break;
			case "Tuesday":weekNamekr = "2.화요일";break;
			case "Wednesday":weekNamekr = "3.수요일";break;
			case "Thursday":weekNamekr = "4.목요일";break;
			case "Friday":weekNamekr = "5.금요일";break;
			case "Saturday":weekNamekr = "6.토요일";break;
			case "Sunday":weekNamekr = "7.일요일";break;
			case "1.월요일":weekNamekr = "1.월요일";break;
			case "2.화요일":weekNamekr = "2.화요일";break;
			case "3.수요일":weekNamekr = "3.수요일";break;
			case "4.목요일":weekNamekr = "4.목요일";break;
			case "5.금요일":weekNamekr = "5.금요일";break;
			case "6.토요일":weekNamekr = "6.토요일";break;
			case "7.일요일":weekNamekr = "7.일요일";break;
		}

		return weekNamekr;

	}

	public static String convertDateToString(Calendar start, Calendar end) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		return format.format(start.getTime()) + "~" + format.format(end.getTime());

	}

	public static String convertDateToString2(Calendar start) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		return format.format(start.getTime());

	}

}