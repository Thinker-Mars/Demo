package com.cone.sunny.pojo;
import java.io.Serializable;
//import java.util.Date;

//书籍信息实体表
@SuppressWarnings("serial")
public class BookMeta implements Serializable {
	
	private String name;
	private String author_tag;
	private String publish_company;
	private String star_level;
	private float price;
	private String time;
	private String productCategories;
	private Integer id;
	private String brief;
	
	public BookMeta() {
		
	}
	
	public BookMeta(String name, String author_tag, String publish_company,
			String star_level, float price, String time, String productCategories) {
		this.name = name;
		this.author_tag = author_tag;
		this.publish_company = publish_company;
		this.star_level = star_level;
		this.price = price;
		this.time = time;
		this.productCategories = productCategories;
	}
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getBrief() {
		return brief;
	}
	
	public String getName() {
		return name;
	}
	
	public void setBrief(String brief) {
		this.brief = brief;
	}

	public String getAutherTag() {
		return author_tag;
	}

	public String getPublishCompany() {
		return publish_company;
	}

	public String getStarLevel() {
		return star_level;
	}

	public float getPrice() {
		return price;
	}

	public String getTime() {
		return time;
	}

	public String getProductCategories() {
		return productCategories;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAutherTag(String author_tag) {
		this.author_tag = author_tag;
	}

	public void setPublishCompany(String publish_company) {
		this.publish_company = publish_company;
	}

	public void setStarLevel(String star_level) {
		this.star_level = star_level;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public void setProductCategories(String productCategories) {
		this.productCategories = productCategories;
	}

	

}


