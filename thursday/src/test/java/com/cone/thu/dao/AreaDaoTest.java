package com.cone.thu.dao;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.cone.thu.BaseTest;
import com.cone.thu.entity.Area;

/**
*@author Cone
*@Version 2019年8月2日 上午10:14:48
*/
public class AreaDaoTest extends BaseTest {
	
	@Autowired
	private AreaDao areaDao;
	
	@Test
	public void queryAreaTest(Map<String, Object> map) {
		List<Area> areaList = areaDao.queryArea(map);
		for (Area area : areaList) {
			System.out.println(area.getAreaId());
			System.out.println(area.getAreaName());
		}
	}

}
