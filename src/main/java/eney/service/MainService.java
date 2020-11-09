package eney.service;

import java.util.Calendar;
import javax.annotation.Resource;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import eney.mapper.MainDao;

@Service
public class MainService{
	
	@Resource
	private MainDao mainDao;
	
	/**
	 * 전날의 GA 데이터를 DB에 저장
	 * 
	 */
	
	
	public String calendarToString(Calendar oCalendar){
		String year = String.valueOf(oCalendar.get(Calendar.YEAR));
		String month = String.valueOf(oCalendar.get(Calendar.MONTH)+1);
		String day = String.valueOf(oCalendar.get(Calendar.DAY_OF_MONTH));
		
		if(Integer.parseInt(month)<10)
			month = "0" + String.valueOf(oCalendar.get(Calendar.MONTH)+1);
		
		if(Integer.parseInt(day)<10)
			day = "0" + String.valueOf(oCalendar.get(Calendar.DAY_OF_MONTH));
		
		return year+month+day;
	}

}
