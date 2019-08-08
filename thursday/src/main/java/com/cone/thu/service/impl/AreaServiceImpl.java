package com.cone.thu.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cone.thu.dao.AreaDao;
import com.cone.thu.entity.Area;
import com.cone.thu.service.AreaService;

/**
*@author Cone
*@Version 2019年8月2日 上午10:33:51
*/
@Service
public class AreaServiceImpl implements AreaService {
	
	@Autowired
	private AreaDao areaDao;

	@Override
	public List<Area> getAreaList(Map<String, Object> map) {
		
		return areaDao.queryArea(map);
	}

	@Override
	public void deleteAreaById(String id) {
		
		areaDao.deleteAreaById(id);
	}

	@Override
	public void addArea(Map<String, Object> map) {
		
		areaDao.addArea(map);
	}

	@Override
	public void editArea(Map<String, Object> map) {
		
		areaDao.editArea(map);
	}

	@Override
	public int queryCount() {
		
		return areaDao.queryCount();
	}

}
