package com.cone.sunny.pojo;
import java.io.Serializable;
//图书审核信息实体表
@SuppressWarnings("serial")
public class BookAudit implements Serializable {
	private Integer workid;
	private String name;
	private String author_tag;
	private float price;
	private String publish_company;
	private String star_level;
	private String brief;
	private String publish_time;
	private String productid;
	private String worker;//申请人
	
	public BookAudit() {
		
	}
	
	public BookAudit(Integer workid, String name, String author_tag, float price, String publish_company,
			String star_level, String brief, String publish_time, String worker) {
		this.workid = workid;
		this.name = name;
		this.author_tag = author_tag;
		this.price = price;
		this.publish_company = publish_company;
		this.star_level = star_level;
		this.brief = brief;
		this.publish_time = publish_time;
		this.worker = worker;
		
	}
	
	public String getWorker() {
		return worker;
	}
	
	public String getProductid() {
		return productid;
	}

	public Integer getWorkid() {
		return workid;
	}

	public String getName() {
		return name;
	}

	public String getAuthor_tag() {
		return author_tag;
	}

	public float getPrice() {
		return price;
	}

	public String getPublish_company() {
		return publish_company;
	}

	public String getStar_level() {
		return star_level;
	}

	public String getBrief() {
		return brief;
	}

	public String getPublishtime() {
		return publish_time;
	}

	public void setWorkid(Integer workid) {
		this.workid = workid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAuthor_tag(String author_tag) {
		this.author_tag = author_tag;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public void setPublish_company(String publish_company) {
		this.publish_company = publish_company;
	}

	public void setStar_level(String star_level) {
		this.star_level = star_level;
	}

	public void setBrief(String brief) {
		this.brief = brief;
	}

	public void setPublishtime(String publish_time) {
		this.publish_time = publish_time;
	}
	
	public void setProductid(String productid) {
		this.productid = productid;
	}
	
	public void setWorker(String worker) {
		this.worker = worker;
	}

}
