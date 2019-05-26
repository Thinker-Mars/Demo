package com.cone.sunny.pojo;

import java.io.Serializable;
//图书活跃度实体表
@SuppressWarnings("serial")
public class WeekData implements Serializable {
	
	private int addCount;
	private int checkCount;
	private int deleteCount;
	private int excelCount;
	
	public WeekData() {
		
	}
	
	public WeekData(int addCount, int checkCount, int deleteCount, int excelCount) {
		this.addCount = addCount;
		this.checkCount = checkCount;
		this.deleteCount = deleteCount;
		this.excelCount = excelCount;
		
	}

	public int getAddCount() {
		return addCount;
	}

	public int getCheckCount() {
		return checkCount;
	}

	public int getDeleteCount() {
		return deleteCount;
	}

	public int getExcelCount() {
		return excelCount;
	}

	public void setAddCount(int addCount) {
		this.addCount = addCount;
	}

	public void setCheckCount(int checkCount) {
		this.checkCount = checkCount;
	}

	public void setDeleteCount(int deleteCount) {
		this.deleteCount = deleteCount;
	}

	public void setExcelCount(int excelCount) {
		this.excelCount = excelCount;
	}
	
	
	

	
	

}
