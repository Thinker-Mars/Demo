package com.cone.sunny.dao;

import java.util.List;
//import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cone.sunny.pojo.WeekData;
import com.cone.sunny.pojo.bookCate;



@Mapper
public interface ChartInfoDao {
	//查询图书种类
	public List<bookCate> searchProductCategories();
	//根据时间查询图书活跃度
	public List<WeekData> getWeekData(String searchTime);

}
