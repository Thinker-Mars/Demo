package com.cone.sunny.service;

import java.time.LocalDate;
import java.util.List;
//import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cone.sunny.dao.ChartInfoDao;
import com.cone.sunny.pojo.WeekData;
import com.cone.sunny.pojo.bookCate;

@Service
public class ChartInfoService implements ChartInfoDao {
	
	@Autowired
	private ChartInfoDao chartInfoDao;
	
	@Override
	public List<bookCate> searchProductCategories() {
		return chartInfoDao.searchProductCategories();
	}
	
	@Override
	public List<WeekData> getWeekData(String searchTime) {
		return chartInfoDao.getWeekData(searchTime);
	}
	
	//返回一个LocalDate时间，便于之后操作
	public LocalDate generateTime(String timeString) {
		String[] genTime = timeString.split("-");
		int year = Integer.parseInt(genTime[0]);
		int month = Integer.parseInt(genTime[1]);
		int day = Integer.parseInt(genTime[2]);
		LocalDate date = LocalDate.of(year, month, day);
		return date;
	}

}
