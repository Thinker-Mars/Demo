package com.cone.thu.web;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.cone.thu.entity.Area;
import com.cone.thu.service.AreaService;
import com.cone.thu.util.PageBean;
import com.cone.thu.util.ResponseUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
*@author Cone
*@Version 2019年8月2日 上午10:51:40
*/
@Controller
@RequestMapping(value="/list")
public class AreaController {
	
	@Autowired
	private AreaService areaService;
	
	/**
	 * 查询所有区域信息
	 * @param page
	 * @param rows
	 * @param res
	 * @param areaName
	 * @throws Exception
	 */
	@PostMapping("/listarea")
	private void getListArea(@RequestParam(value="page",required=false) String page,
			@RequestParam(value="rows",required=false) String rows, HttpServletResponse res, String areaName) throws Exception {
		
		PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));//分页
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("start", pageBean.getStart());
		map.put("size", pageBean.getPageSize());
		map.put("areaName", (String)areaName);
		List<Area> list = areaService.getAreaList(map);//查询
		int total = areaService.queryCount();//查询记录总数
		JSONObject result=new JSONObject();
		JSONArray jsonArray=JSONArray.fromObject(list);
		result.put("rows", jsonArray);
		result.put("total", total);
		ResponseUtil.write(res, result);//返回结果

	}
	
	/**
	 * 删除区域信息
	 * @param ids
	 * @param res
	 * @throws Exception
	 */
	@PostMapping("/delete")
	private void deletearea(@RequestParam(value="ids") String ids, HttpServletResponse res) throws Exception {
		
		String[] idStr = ids.split(",");//将id用逗号分割存入数组
		JSONObject result = new JSONObject();
		for (String id : idStr) {
			areaService.deleteAreaById(id);//不应该在for循环里执行数据库相关操作
		}                                  //当数据量大，用户数多时，数据库连接会爆，这里只是简单演示用
		result.put("flag", true);
		ResponseUtil.write(res, result);
	}
	
	
	/**
	 * 新增区域信息
	 * @param request
	 * @param res
	 * @throws Exception
	 */
	@PostMapping("/save")
	private void saveArea(HttpServletRequest request, HttpServletResponse res) throws Exception {
		
		JSONObject result = new JSONObject();
		String priority = new String((request.getParameter("priority")).getBytes("ISO-8859-1"),"UTF-8");//中文字符乱码，尝试解决但无效
		String areaName = request.getParameter("areaName");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaName", areaName);
		map.put("priority", priority);
		map.put("createTime", new Date());
		areaService.addArea(map);//添加
		result.put("flag", true);
		ResponseUtil.write(res, result);
	}
	
	/**
	 * 编辑区域信息
	 * @param request
	 * @param res
	 * @throws Exception
	 */
	@PostMapping("/edit")
	private void editArea(HttpServletRequest request, HttpServletResponse res) throws Exception {
		
		JSONObject result = new JSONObject();
		String priority = request.getParameter("priority");
		String areaName = request.getParameter("areaName");
		String areaId = request.getParameter("areaId");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("areaName", areaName);
		map.put("priority", priority);
		map.put("editTime", new Date());
		map.put("areaId", areaId);
		areaService.editArea(map);
		result.put("flag", true);
		ResponseUtil.write(res, result);
	}
}
