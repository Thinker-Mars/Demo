package com.cone.redis.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import com.cone.redis.entity.Product;
import com.cone.redis.service.serviceImpl.RedisServiceImpl;


@Controller
@RequestMapping(value="/redis")

public class RedisController {

	@Autowired
	private RedisServiceImpl redisServiceImpl;
	
	@SuppressWarnings("unchecked")
	@PostMapping(value = "/test")
	@ResponseBody
	public List<Product> testRedis() {
		
		return redisServiceImpl.searchProduct();

	}

}
