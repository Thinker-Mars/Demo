package com.cone.redis.service.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cone.redis.dao.ProductDao;
//import com.cone.redis.entity.Product;
import com.cone.redis.service.RedisService;
import com.cone.redis.util.RedisUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RedisServiceImpl implements RedisService {
	
	@Autowired
	private RedisUtil.redisList redisList;
	
	@Autowired
	private RedisUtil redisUtil;
	
	@Autowired
	private ProductDao productDao;

	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public List searchProduct() {
		
		List list = new ArrayList<>();
		if (redisUtil.hasKey("productList")) {
			log.info("从redis中获取数据.");
			list = redisList.get("productList", 0, -1);
		
		}
		else {
			list = productDao.searchProduct();
			log.info("从数据库中获取数据.");
			log.info("将数据存入redis...");
			redisList.set("productList", list);
			log.info("成功存入redis.");
		}
		return list;
	}

}
