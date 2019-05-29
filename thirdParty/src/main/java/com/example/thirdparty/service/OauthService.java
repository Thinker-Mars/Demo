package com.example.thirdparty.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.thirdparty.utils.RandomUtils;
import com.example.thirdparty.utils.RedisUtil;

/**
*@author Cone
*@Version 2019年5月29日 下午1:12:46
*/
@Service
public class OauthService {
	
	@Autowired
	private RedisUtil.redisSet redisSet;
	
	private String OAUTH_STATE_KEY = "OAUTH_STATE";

	public String saveState() {
		//先生成state
		String state = RandomUtils.getRandomStringByTime();
		//判断缓存中是否有相关数据，有的话重新生成一个state
		if (redisSet.hasKey(OAUTH_STATE_KEY, state)) {
			state = RandomUtils.getRandomStringByTime();
		}
		//存入缓存
		redisSet.set(OAUTH_STATE_KEY, state);
		
		return state;
		
	}
	
	/**
	 * 校验state是否合法
	 * @param state
	 * @return true合法，false不合法
	 */
	public boolean checkState(String state) {
		boolean flag = redisSet.hasKey(OAUTH_STATE_KEY, state);
		//若缓存中已有数据，则清空缓存，state合法
		if (flag) {
			redisSet.remove(OAUTH_STATE_KEY, state);
			return true;
		} else {
			//否则缓存中没有相关数据，state不合法
			return false;
		}
	}
}
