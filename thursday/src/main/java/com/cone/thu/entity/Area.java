package com.cone.thu.entity;

import java.util.Date;

/**
*@author Cone
*@Version 2019年8月2日 上午9:55:48
*/
public class Area {
	
    //id
	private Integer areaId;
	//名称
	private String areaName;
	//权重
	private Integer priority;
	//创建时间
	private Date creatTime;
	//更新时间
	private Date lastEditTime;
	
	public Integer getAreaId() {
		return areaId;
	}
	
	public void setAreaId(Integer areaId) {
		this.areaId = areaId;
	}
	
	public String getAreaName() {
		return areaName;
	}
	
	public void setAreaName(String areaName) {
		this.areaName = areaName;
	}
	
	public Integer getPriority() {
		return priority;
	}
	
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	public Date getCreatTime() {
		return creatTime;
	}
	
	public void setCreatTime(Date creatTime) {
		this.creatTime = creatTime;
	}
	
	public Date getLastEditTime() {
		return lastEditTime;
	}
	
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}

}
