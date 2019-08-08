package com.cone.thu.service;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cone.thu.BaseTest;
import com.cone.thu.entity.Area;

/**
*@author Cone
*@Version 2019年8月2日 上午10:38:21
*/
public class AreaServiceTest extends BaseTest {
	
	@Autowired
	private AreaService areaService;
	
	@Test
	public void getAreaListTest(Map<String, Object> map) {
		
		List<Area> areaList = areaService.getAreaList(map);
		System.out.println(areaList.size());
		System.out.println(areaList.get(0).getAreaName());
	}

}
