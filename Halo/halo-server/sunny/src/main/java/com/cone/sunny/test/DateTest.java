package com.cone.sunny.test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Date;

import org.joda.time.DateTime;

public class DateTest {
	

	public static void main(String[] args) throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String startTime = "2019-04-19 14:15:30";
		String endTime = "2019-04-19 15:19:15";
		
		
		Date start = df.parse(startTime); //任务开始时间
		System.out.println(df.format(start).toString());

//		Date end = df.parse(endTime);
//		System.out.println(end.getTime());
		
		//System.out.println(DateTest.longTimeToDay(start, end));


	}
	
	public static Date getTime() throws ParseException {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = df.format(new Date());
		Date date = df.parse(time);
		return date;
		
	}
	
	public static String longTimeToDay(Date start, Date end) {
		//获取毫秒数
		Long startLong = start.getTime();
		Long endLong = end.getTime();
		Long ms = endLong-startLong;
        Integer ss = 1000;  
        Integer mi = ss * 60;  
        Integer hh = mi * 60;  
        Integer dd = hh * 24;  

        Long day = ms / dd;  
        Long hour = (ms - day * dd) / hh;  
        Long minute = (ms - day * dd - hour * hh) / mi;  
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;  
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;  

        StringBuffer sb = new StringBuffer();  
        if(day > 0) {  
            sb.append(day+"天");  
        }  
        if(hour > 0) {  
            sb.append(hour+"小时");  
        }  
        if(minute > 0) {  
            sb.append(minute+"分");  
        }  
        if(second > 0) {  
            sb.append(second+"秒");  
        }  
        if(milliSecond > 0) {  
            sb.append(milliSecond+"毫秒");  
        }  
        return sb.toString(); 

		
	}

}
