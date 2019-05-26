package com.cone.sunny.dao;

import java.util.Map;
import org.apache.ibatis.annotations.Mapper;

import com.cone.sunny.pojo.MetaCollect;
import com.github.pagehelper.Page;

@Mapper
public interface DataCollectDao {
	//查询采集任务
	public Page<MetaCollect> searchTask(Map<String, Object> taskSearchMap);
	//根据任务名称返回路径地址
	public String getTaskAddress(String taskName);
	//查询当前数据量
	public int getBookCount();
	//查询采集任务id
	public int getTaskId(String taskName);
	//更新任务执行表数据
	public void executeTask(Map<String, Object> executeResultMap);
	//采集任务遇到异常
	public void executeError(int taskId);

}
