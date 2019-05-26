package com.cone.sunny.pojo;

import java.io.Serializable;
import java.util.Date;
//元数据采集实体表
@SuppressWarnings("serial")
public class MetaCollect implements Serializable {
	private String taskName;//任务名称
	private String lastTime;//最后一次运行时间
	private String runTime;//采集耗时
	private String result;//采集结果
	private int count;//采集数量
	private String workerName;//任务执行人
	
	public MetaCollect() {
		
	}
	
	public MetaCollect(String taskName, String lastTime, String runTime, String result, int count, String workerName) {
		this.taskName = taskName;
		this.lastTime = lastTime;
		this.runTime = runTime;
		this.result = result;
		this.count = count;
		this.workerName = workerName;
	}

	public String getTaskName() {
		return taskName;
	}

	public String getLastTime() {
		return lastTime;
	}

	public String getRunTime() {
		return runTime;
	}

	public String getResult() {
		return result;
	}

	public int getCount() {
		return count;
	}

	public String getWorkerName() {
		return workerName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setLastTime(String lastTime) {
		this.lastTime = lastTime;
	}

	public void setRunTime(String runTime) {
		this.runTime = runTime;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public void setWorkerName(String workerName) {
		this.workerName = workerName;
	}


	
	

}
