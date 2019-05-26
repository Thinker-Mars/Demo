package com.cone.sunny.pojo;

import java.io.Serializable;
//图书类别的实体表
@SuppressWarnings("serial")
public class ProductItem implements Serializable {
	
	private Integer productId;
	private String productCategories;
	
	public ProductItem() {
		
	}
	
	public ProductItem(Integer productId, String productCategories) {
		this.productId = productId;
		this.productCategories = productCategories;
		
	}
	
	public Integer getproductId() {
		return productId;
	}
	
	public void setProduct_id(Integer productId) {
		this.productId = productId;
	}
	
	public String getProductCategories() {
		return productCategories;
	}
	
	public void setProductCategories(String productCategories) {
		this.productCategories = productCategories;
	}
	
	@Override
	public String toString() {
		return "productItem{"
				+ "productId: " + productId + ","
				+ "productCategories: " + productCategories + ","
				+ "}";
	}
	

}
