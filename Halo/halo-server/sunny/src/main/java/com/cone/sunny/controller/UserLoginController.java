package com.cone.sunny.controller;

import java.text.ParseException;
import java.util.HashMap;
//import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cone.sunny.service.DataCollectService;
//import com.cone.sunny.pojo.Worker;
import com.cone.sunny.service.UserLoginService;


/**
 * 
 * @author Cone
 *用户登陆服务
 */
@RestController
@RequestMapping("/userlogin")
public class UserLoginController {
	
	@Autowired
	private UserLoginService userLoginService;
	@Autowired
	private DataCollectService dataCollectService;
	
	
	/**
	 * 
	 * @param 登陆时的用户名与密码
	 * 返回用户信息
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "login")
	public Map<String, Object> UserLogin(@RequestBody Map<String, Object> param) throws ParseException {
		Map<String, Object> userInfoMap = new HashMap<String, Object>();
		userInfoMap = (Map<String, Object>)param.get("loginForm");
		Map<String, Object> resultMap = userLoginService.SearchWorkerInfoByLogin(userInfoMap);
		Map<String, Object> userResultMap = new HashMap<String, Object>();
		if (resultMap == null) {
			//无法找到用户信息，登陆失败
			userResultMap.put("flag", "00");
			return userResultMap;
		}
		else {
			//登陆信息验证通过，登陆成功
			String token = resultMap.get("id").toString() + dataCollectService.getCurrentTime().toString();
			userResultMap.put("flag", "01");
			userResultMap.put("id", resultMap.get("id"));
			userResultMap.put("name", resultMap.get("name"));
			userResultMap.put("role", resultMap.get("role"));
			userResultMap.put("token", token);
			return userResultMap;	
		}

	}

}
