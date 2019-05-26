package com.cone.sunny.service;

//import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cone.sunny.dao.UserLoginDao;
//import com.cone.sunny.pojo.Worker;

@Service
public class UserLoginService implements UserLoginDao {
	
	@Autowired
	private UserLoginDao userLoginDao;
	
	@Override
	public Map<String, Object> SearchWorkerInfoByLogin(Map<String, Object> userInfoMap) {
		return userLoginDao.SearchWorkerInfoByLogin(userInfoMap);
	}

}
