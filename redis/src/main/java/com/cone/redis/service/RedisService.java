package com.cone.redis.service;

import java.util.List;
import com.cone.redis.entity.Product;


public interface RedisService {
	
	public List<Product> searchProduct();

}
