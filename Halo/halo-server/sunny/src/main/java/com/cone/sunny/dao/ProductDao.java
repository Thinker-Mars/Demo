package com.cone.sunny.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.cone.sunny.pojo.ProductItem;

@Mapper
public interface ProductDao {
	//获取图书类别信息
	public List<ProductItem> searchProduct();
	

}
