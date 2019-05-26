package com.cone.sunny.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

//import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cone.sunny.api.FunKit;
import com.cone.sunny.dao.DataCollectDao;
import com.cone.sunny.pojo.MetaCollect;
import com.github.pagehelper.Page;

@Service
public class DataCollectService implements DataCollectDao {
	final SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Autowired
	private DataCollectDao dataCollectDao;
	
	@Override
	public Page<MetaCollect> searchTask(Map<String, Object> taskSearchMap) {
		return dataCollectDao.searchTask(taskSearchMap);
	}
	
	@Override
	public String getTaskAddress(String taskName) {
		return dataCollectDao.getTaskAddress(taskName);
	}
	
	@Override
	public int getBookCount() {
		return dataCollectDao.getBookCount();
	}
	
	@Override
	public void executeTask(Map<String, Object> executeResultMap) {
		dataCollectDao.executeTask(executeResultMap);
	}
	
	@Override
	public int getTaskId(String taskName) {
		return dataCollectDao.getTaskId(taskName);
	}
	
	@Override
	public void executeError(int taskId) {
		dataCollectDao.executeError(taskId);
	}
	
	/**
	 * 执行任务
	 * @param taskName 任务名称
	 * @param workerId  执行人
	 * @throws IOException
	 * @throws InterruptedException
	 * @throws ParseException
	 */
	public void executeTaskByTaskId(String taskName, int workerId, int taskId) throws IOException, InterruptedException, ParseException {
		try {
			//取得路径地址
			String address = this.getTaskAddress(taskName);
			File dir = new File(address);
			String[] cmd = new String[] {
					"cmd",
					"/c",
					"scrapy crawl basic"
			};
			int startCount = this.getBookCount();
			Date start = this.getCurrentTime();//任务开始时间
			final Process process = Runtime.getRuntime().exec(cmd,null,dir);
			printMessage(process.getInputStream());
			printMessage(process.getErrorStream());
			process.waitFor();//等待任务结束
			int endCount = this.getBookCount();
			int count = endCount - startCount;//采集数据量
			Date end = this.getCurrentTime();//任务结束时间
			String runTime = this.longTimeToDay(start, end);//任务运行时间
			this.generateExecuteMap(end, runTime, count, workerId, taskId);
			
		} catch (Exception e) {
			this.executeError(taskId);
		}
	}
	
	/**
	 * 输出流信息
	 * @param input
	 */
	public void printMessage(final InputStream input) {
		new Thread(new Runnable() {
			@Override
			public void run() {
			    Reader reader = new InputStreamReader(input);
			    BufferedReader bf = new BufferedReader(reader);
			    String line = null;
			    try {
					while ((line=bf.readLine())!=null) {
						System.out.println(line);
					}
				} catch (IOException e) {
					System.out.println(e.getMessage());
				}
				
			}
		}).start();
		
	}
	
	/**
	 * 
	 * @param start 开始时间
	 * @param end 结束时间
	 * @return 时间差
	 */
	public String longTimeToDay(Date start, Date end) {
		//获取毫秒数
		Long startLong = start.getTime();
		Long endLong = end.getTime();
		Long ms = endLong-startLong;
        Integer ss = 1000;  
        Integer mi = ss * 60;  
        Integer hh = mi * 60;  
        Integer dd = hh * 24;  
        Long day = ms / dd;  
        Long hour = (ms - day * dd) / hh;  
        Long minute = (ms - day * dd - hour * hh) / mi;  
        Long second = (ms - day * dd - hour * hh - minute * mi) / ss;  
        Long milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss;  

        StringBuffer sb = new StringBuffer();  
        if(day > 0) {  
            sb.append(day+"天");  
        }  
        if(hour > 0) {  
            sb.append(hour+"小时");  
        }  
        if(minute > 0) {  
            sb.append(minute+"分");  
        }  
        if(second > 0) {  
            sb.append(second+"秒");  
        }  
        if(milliSecond > 0) {  
            sb.append(milliSecond+"毫秒");  
        }  
        return sb.toString(); 

	}
	
	/**
	 * 获取当前时间
	 * @return
	 * @throws ParseException
	 */
	public Date getCurrentTime() throws ParseException {
		String time = df.format(new Date());
		Date date = df.parse(time);
		return date;
	}
	
	/**
	 * 将执行所得数据入库
	 * @param lastTime 任务执行结束时间
	 * @param runTime  任务耗时
	 * @param count  采集数量
	 * @param workerId  执行人
	 * @param taskId  任务id
	 */
	public void generateExecuteMap(Date lastTime, String runTime, int count, int workerId, int taskId) {
		Map<String, Object> executeResultMap = new HashMap<>();
		executeResultMap.put("taskId", taskId);
		executeResultMap.put("count", count);
		executeResultMap.put("workerId", workerId);
		executeResultMap.put("runTime", runTime);
		executeResultMap.put("lastTime", df.format(lastTime).toString());
		executeResultMap.put("result", "01");//标记为执行成功
		this.executeTask(executeResultMap);
	}
	
	public Map<String, Object> dealParam(Map<String, Object> param) {
		Map<String, Object> taskSearchMap = new HashMap<String, Object>();
		if (FunKit.collectKey(param, "taskName")) {
			if (!param.get("taskName").equals("") == true) {
				taskSearchMap.put("taskName", param.get("taskName"));
			}
		}
		else {
			taskSearchMap.put("taskName", null);
		}
		return taskSearchMap;
	}
	
}
