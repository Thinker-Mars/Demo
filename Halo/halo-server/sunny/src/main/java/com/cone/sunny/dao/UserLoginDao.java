package com.cone.sunny.dao;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserLoginDao {
	
	//根据登陆数据返回用户信息
	public Map<String, Object> SearchWorkerInfoByLogin(Map<String, Object> userInfoMap);
	

}
