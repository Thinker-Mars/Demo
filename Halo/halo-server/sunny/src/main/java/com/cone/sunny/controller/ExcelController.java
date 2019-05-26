package com.cone.sunny.controller;

import java.net.URLEncoder;
//import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.cone.sunny.service.ExcelService;
import com.cone.sunny.api.ExcelParser;
import com.cone.sunny.api.ExecutionResult;

/**
 * 
 * @author Cone
 *图书新增信息Excel处理
 */
@RestController
@RequestMapping("/import")
public class ExcelController {
	
	@Autowired
	private ExcelService excelservice;
	
	/**
	 * 
	 * 
	 * @param file 上传Excel文件
	 * @throws Exception
	 */
	@PostMapping(value = "/Excel")
	public Map<String, Object> uploadExcel (@RequestParam MultipartFile file) throws Exception {
		int code = 200;
		try {
			//取得Excel中的内容
			List<Map<String, Object>> bookMetalist = excelservice.getListByExcel(file.getInputStream(), file.getOriginalFilename());
			//将Excel数据写入数据库
			excelservice.transformExcelList(bookMetalist);
			return new ExecutionResult().Result(code);
		} catch (Exception e) {
			code = -1;
			System.out.println(e.getMessage());
			return new ExecutionResult().Result(code);
		}

		
		
	}
	
	
	/**
	 * 
	 * @param param 下载Excel模板
	 * @param response
	 * @throws Exception
	 */
	@PostMapping(value="downloadExcel")
	public void downloadExcelExport(@RequestBody Map<String, Object> param, HttpServletResponse response) throws Exception {
		
		 response.reset();
         response.addHeader("Content-Disposition", "attachment;filename="+ URLEncoder.encode("图书新增信息模板.xlsx", "UTF-8"));
         response.addHeader("Content-type", "text/html;charset=UTF-8");
         String[] bookMetaRowTitle = new String[]{"图书名称", "作者","出版日期","售价","出版社","评分","图书简介","图书上架分类"};
         String[] bookMetaDesc = new String[]{"填写说明:text文本,必填","填写说明:text文本,必填","比如:2019/04/11,必填"
        		 ,"填写说明:数字类型,必填","填写说明:text文本,必填","填写说明:数字类型,必填","填写说明:text文本,必填","填写说明:01-50,必填"};
         new ExcelParser().createWorkbook().createSheet("图书信息").createRow(bookMetaRowTitle,0).createRow(bookMetaDesc,1)
         .setColumnHidden(8, true).toExcel(response.getOutputStream());
	}
	
	


}
