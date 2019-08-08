package com.cone.thu.dao;

import java.util.List;
import java.util.Map;

import com.cone.thu.entity.Area;

/**
*@author Cone
*@Version 2019年8月2日 上午9:57:24
*/
public interface AreaDao {
	
	List<Area> queryArea(Map<String, Object> map);
	
	void deleteAreaById(String id);
	
	void addArea(Map<String, Object> map);
	
	void editArea(Map<String, Object> map);
	
	int queryCount();
	

}
