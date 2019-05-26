package com.cone.sunny.service;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFDateUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cone.sunny.dao.ExcelDao;

@Service
public class ExcelService implements ExcelDao {
	
	@Autowired
	private ExcelDao excelDao;
	
	
	public List<Map<String, Object>> getListByExcel (InputStream inputstream, String fileName) throws Exception {
		StringBuffer errorMsg = new StringBuffer();
		Map<String, Object> resultMap = new HashMap<>();
		@SuppressWarnings("unused")
		String[] bookMetaRowTitle = new String[] { "图书名称", "作者", "出版日期","售价","出版社","评分","图书简介","图书上架分类","申请人"};
		String[] bookMetaKey = new String[] { "name", "author", "time","price","publishCompany","star","brief","productid","workerid"};
		Workbook workbook = this.getWorkBook(inputstream, fileName);
		if (null == workbook) {
			throw new Exception("请确认Excel内有数据!");
		}
		
		int sheetNum = workbook.getNumberOfSheets();
		if (sheetNum != 1) {
			errorMsg.append("请确认模板是否正确，或重新下载模板填写！");
			resultMap.put("errorMsg", errorMsg.toString());
		}
		
		// 取第一页数据
		Sheet modSheet = workbook.getSheetAt(0);
		//取得当前页末尾行的索引（从0开始）
		int lastRowNum = modSheet.getLastRowNum();
		//取得当前页第一行的索引（从0开始）
		//int firstRowNum = modSheet.getFirstRowNum();
		
		//末尾行索引至少为2
		if (lastRowNum < 2) {
			errorMsg.append("请确认模板内模型是否有数据！");
			resultMap.put("errorMsg", errorMsg.toString());
		}
		List<Map<String, Object>> bookMetalist = new ArrayList<Map<String, Object>>();//导入数据的list集合
		for (int i = 2; i <= lastRowNum; i++) {
			Map<String, Object> bookMetaMap = new HashMap<>();//存入每一行的数据
			Row row = modSheet.getRow(i);
			for (int j = 0; j < bookMetaKey.length; j++) { 

				Cell cell = row.getCell(j);
				if (cell.getCellType() != Cell.CELL_TYPE_STRING && HSSFDateUtil.isCellDateFormatted(cell)) {
	                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	                Date date = HSSFDateUtil.getJavaDate(cell.getNumericCellValue());
	                String value = sdf.format(date);
	                bookMetaMap.put("time", value);
	                continue;
				}
				cell.setCellType(Cell.CELL_TYPE_STRING);
				String cellValue = cell.getStringCellValue();
				switch (bookMetaKey[j]) {
				case "name":
					bookMetaMap.put("name", cellValue.trim());
					break;
				case "author":
					bookMetaMap.put("author", cellValue.trim());
					break;
				case "price":
					bookMetaMap.put("price", cellValue.trim());
					break;
				case "publishCompany":
					bookMetaMap.put("publishCompany", cellValue.trim());
					break;
				case "star":
					bookMetaMap.put("star", cellValue.trim());
					break;
				case "brief":
					bookMetaMap.put("brief", cellValue.trim());
					break;
				case "productid":
					bookMetaMap.put("productid", cellValue.trim());
					break;
				case "workerid":
					bookMetaMap.put("workerid", cellValue.trim());
					break;
				}
			}
			bookMetalist.add(bookMetaMap);
		}
		return bookMetalist;
		
		
	}
	
	public Workbook getWorkBook (InputStream inputstream, String fileName) throws Exception {
		Workbook workbook = null;
		String fileType = fileName.substring(fileName.lastIndexOf("."));
		if (".xls".equals(fileType)) {
			workbook = new HSSFWorkbook(inputstream);
		}else if (".xlsx".equals(fileType)) {
			workbook = new XSSFWorkbook(inputstream);
		}else {
			throw new Exception("请上传Excel文件!");
		}
		
		return workbook;
	}
	
	public void transformExcelList(List<Map<String, Object>> bookMetaExcelList) {
		String applyTime = DateTime.now().toString("yyyy-MM-dd");
		for (int i = 0; i < bookMetaExcelList.size(); i++) {
			bookMetaExcelList.get(i).put("model", "04");
			bookMetaExcelList.get(i).put("is_audit", "00");
			bookMetaExcelList.get(i).put("apply_time", applyTime);
			this.bookMetaExcel(bookMetaExcelList.get(i));
		}
	}
	
	@Override
	public void bookMetaExcel(Map<String, Object> bookMetaExcelMap) {
		excelDao.bookMetaExcel(bookMetaExcelMap);
	}
	

}
