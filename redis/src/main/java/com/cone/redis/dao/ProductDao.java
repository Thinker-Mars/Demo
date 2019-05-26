package com.cone.redis.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.cone.redis.entity.Product;

@Mapper
public interface ProductDao {
	/**
	 * 查询图书列表
	 * @return
	 */
	public List<Product> searchProduct();
}
