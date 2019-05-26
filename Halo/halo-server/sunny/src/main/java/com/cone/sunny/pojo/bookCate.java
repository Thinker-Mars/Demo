package com.cone.sunny.pojo;

import java.io.Serializable;
//资产总览实体表
@SuppressWarnings("serial")
public class bookCate implements Serializable {
	
	private String productcategories;
	private int count;
	
	public bookCate() {
		
	}
	
	public bookCate(String productcategories, int count) {
		this.productcategories = productcategories;
		this.count = count;
	}

	public String getProductcategories() {
		return productcategories;
	}

	public int getCount() {
		return count;
	}

	public void setProductcategories(String productcategories) {
		this.productcategories = productcategories;
	}

	public void setCount(int count) {
		this.count = count;
	}


}
