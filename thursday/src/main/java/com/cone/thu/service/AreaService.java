package com.cone.thu.service;

import java.util.List;
import java.util.Map;

import com.cone.thu.entity.Area;

/**
*@author Cone
*@Version 2019年8月2日 上午10:32:01
*/
public interface AreaService {
	
	/**
	 * 取得arealist
	 * @param map
	 * @return
	 */
	List<Area> getAreaList(Map<String, Object> map);
	
	/**
	 * 通过id去删除记录
	 * @param id
	 */
	void deleteAreaById(String id);
	
	/**
	 * 添加区域信息
	 * @param map
	 */
	void addArea(Map<String, Object> map);
	
	/**
	 * 根据id编辑区域信息
	 * @param map
	 */
	void editArea(Map<String, Object> map);
	
	/**
	 * 查询area信息总数
	 * @return
	 */
	int queryCount();

}
