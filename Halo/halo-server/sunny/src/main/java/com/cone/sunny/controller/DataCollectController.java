package com.cone.sunny.controller;

import java.io.IOException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cone.sunny.api.ExecutionResult;
import com.cone.sunny.pojo.MetaCollect;
import com.cone.sunny.service.DataCollectService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

/**
 * 
 * @author Cone
 *元数据采集服务
 */
@RestController
@RequestMapping("/collect")
public class DataCollectController {
	
	@Autowired
	private DataCollectService dataCollectService;
	
	/**
	 * 查询采集任务
	 * @param param
	 * @return
	 */
	@GetMapping(value = "/task")
	public Map<String, Object> searchTask(@RequestParam Map<String, Object> param) {
		
		int cur_page=Integer.parseInt(param.get("cur_page").toString());
		int pagesize = Integer.parseInt(param.get("pagesize").toString());
		PageHelper.startPage(cur_page, pagesize);
		Page<MetaCollect> taskPage = dataCollectService.searchTask(dataCollectService.dealParam(param));
		Map<String, Object> TaskMap = new HashMap<String, Object>();
		TaskMap.put("task",taskPage);
		TaskMap.put("total", taskPage.getTotal());
		return TaskMap;
	}
	
	/**
	 * 执行采集任务
	 * @param param
	 * @return
	 * @throws InterruptedException 
	 * @throws IOException 
	 * @throws ParseException 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "execute")
	public Map<String, Object> UserLogin(@RequestBody Map<String, Object> param) throws IOException, InterruptedException, ParseException {
		int code = 200;
		Map<String, Object> taskMap = (Map<String, Object>)param.get("taskName");
		//取得采集任务名称
		String taskName = taskMap.get("taskName").toString();
		int workerId = Integer.parseInt(taskMap.get("workerId").toString());
		try {
			//取得任务id
			int taskId = dataCollectService.getTaskId(taskName);
			//执行任务
			dataCollectService.executeTaskByTaskId(taskName, workerId, taskId);
			return new ExecutionResult().Result(code);
		} catch (Exception e) {
			code = -1;
			System.out.println(e.getMessage());
			return new ExecutionResult().Result(code);
		}

	}
	

}
