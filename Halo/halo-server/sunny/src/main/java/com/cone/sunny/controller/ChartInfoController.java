package com.cone.sunny.controller;

//import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cone.sunny.pojo.WeekData;
import com.cone.sunny.pojo.bookCate;
import com.cone.sunny.service.ChartInfoService;

/**
 * 
 * @author Cone
 * 图表信息展示
 */
@RestController
@RequestMapping("/chart")
public class ChartInfoController {
	
	@Autowired
	private ChartInfoService chartInfoService;
	
/**
 * 
 * 查询图书种类
 * @return
 */
	@GetMapping(value = "/bookcate")
	public List<bookCate> searchProductCategories() {
		List<bookCate> bookCategoriesList = chartInfoService.searchProductCategories();
		//bookCate[] bookCateArray = chartInfoService.dealBookCate(bookCategoriesList);
		return bookCategoriesList;
		
	}
	
	/**
	 * 
	 * @param param 开始查询时间,时间间隔
	 * @return 时间段内的数据变更详情
	 */
	@GetMapping(value = "/weekdata")
	public List<Object> weekDataActivity(@RequestParam Map<String, Object> param) {
		int day = Integer.parseInt(param.get("day").toString());
		LocalDate date = chartInfoService.generateTime(param.get("startTime").toString());
		List<Object> allDataList = new ArrayList<>();
		for (int i = 0; i <= day; i++) {
			List<WeekData> weekDataList = chartInfoService.getWeekData(date.plusDays(i).toString());
			allDataList.add(weekDataList);
			
		}
		return allDataList;

	}
	
	/**
	 * 
	 * @param param 截止时间,时间间隔
	 * @return 时间数组
	 */
	@GetMapping(value = "/gettime")
	public LocalDate[] getInitializeTime(@RequestParam Map<String, Object> param) {
		LocalDate date = chartInfoService.generateTime(param.get("endTime").toString());//截至时间
		int day = Integer.parseInt(param.get("days").toString());//时间差
		LocalDate[] timeArray = new LocalDate[day + 1];
		for (int i = 0; i <= day; i++) {
			timeArray[i] = date.plusDays(i - day);
		}
		return timeArray;
	}

}
