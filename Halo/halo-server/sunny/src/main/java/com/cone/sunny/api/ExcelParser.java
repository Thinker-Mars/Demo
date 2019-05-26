package com.cone.sunny.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataValidation;
import org.apache.poi.ss.usermodel.DataValidationConstraint;
import org.apache.poi.ss.usermodel.DataValidationHelper;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import com.cone.sunny.api.FunKit;
import com.cone.sunny.api.ExcelFun;


public class ExcelParser {

	private Workbook workbook;

	private Sheet sheet;
	
	public static List<Map<String, Object>> parseExcel(InputStream inputStream,String secondSheetKey,String childKey,ExcelFun excelFun) throws Exception {

		List<Map<String, Object>> dataList = new ArrayList<Map<String, Object>>();
		
		Map<String, Object> cellMap = null;

		Workbook workbook = WorkbookFactory.create(inputStream);
		List<Map<String, Object>> sheetList = null;
		int sheetNum = workbook.getNumberOfSheets();
		for (int k = 0; k < sheetNum; k++) {
			// 取第一页数据
			Sheet sheet = workbook.getSheetAt(k);
		
			sheetList = new ArrayList<Map<String, Object>>();
			
			Row colRow = sheet.getRow(0);
			List<String[]> titleAndCol = excelFun.getCols(colRow,k);
			String[] title = titleAndCol.get(0);
			String[] col = titleAndCol.get(1);
			List<String> colList = Arrays.asList(title);
			for (int j = 0; j < title.length; j++) {
				Cell cell = colRow.getCell(j);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if (!colList.contains(cell.getStringCellValue().trim())) {
					throw new Exception("请确认模板是否正确，或重新下载模板填写");
				}
			}
			
			// 第一行设置标题
			int rowNum = sheet.getLastRowNum() - sheet.getFirstRowNum();

			for (int i = 0; i < rowNum; i++) {
				Row row = sheet.getRow(i + 2);
				if (row == null)
					continue;
				cellMap = new HashMap<String, Object>();
				for (int j = 0; j < col.length; j++) {
					Cell cell = row.getCell(j);
					if (cell == null) {
						cellMap.put(col[j], "");
					} else {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue();
						cellMap.put(col[j], StringUtils.isEmpty(cellValue) ? "" : cellValue.trim());
					}
					cellMap.put("sheetNum", k + 1);
					cellMap.put("row", i + 3);
					cellMap.put(col[j] + "_col", j + 1);
				}

				Object[] o = new Object[col.length];
				for (int h = 0; h < col.length; h++) {
					o[h] = cellMap.get(col[h]);
				}
				
				if(!FunKit.isAllEmpty(o)) {
					if(k == 0) {
						dataList.add(cellMap);
					}else {
						sheetList.add(cellMap);
					}
				}
			}
			
			if(k != 0) {
				Optional<Map<String,Object>>  optional = dataList.parallelStream().filter( d ->{
					return d.get(secondSheetKey).equals(sheet.getSheetName());
				}).findFirst();
				
				if(optional.isPresent()) {
					optional.get().put(childKey, sheetList);
				}
			}
		}

		return dataList;
	}

	public static List<Map<String, Object>> parseExcel(InputStream inputStream,String[] title, String... col) throws Exception {

		List<Map<String, Object>> rowList = new ArrayList<Map<String, Object>>();
		List<String> colList = Arrays.asList(title);
		Map<String, Object> cellMap = null;

		Workbook workbook = WorkbookFactory.create(inputStream);

		int sheetNum = workbook.getNumberOfSheets();
		for (int k = 0; k < sheetNum; k++) {
			// 取第一页数据
			Sheet sheet = workbook.getSheetAt(k);

			Row colRow = sheet.getRow(0);
			for (int j = 0; j < title.length; j++) {
				Cell cell = colRow.getCell(j);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				if (!colList.contains(cell.getStringCellValue().trim())) {
					throw new Exception("请确认模板是否正确，或重新下载模板填写");
				}
			}

			// 第一行设置标题
			int rowNum = sheet.getLastRowNum() - sheet.getFirstRowNum();

			for (int i = 0; i < rowNum; i++) {
				Row row = sheet.getRow(i + 2);
				if (row == null)
					continue;
				cellMap = new HashMap<String, Object>();
				for (int j = 0; j < col.length; j++) {
					Cell cell = row.getCell(j);
					if (cell == null) {
						cellMap.put(col[j], "");
					} else {
						cell.setCellType(Cell.CELL_TYPE_STRING);
						String cellValue = cell.getStringCellValue();
						cellMap.put(col[j], StringUtils.isEmpty(cellValue) ? "" : cellValue.trim());
					}
					cellMap.put("sheetNum", k + 1);
					cellMap.put("row", i + 3);
					cellMap.put(col[j] + "_col", j + 1);
				}

				rowList.add(cellMap);
			}

		}

		return rowList.parallelStream().filter((data) -> {
			Object[] o = new Object[col.length];
			for (int i = 0; i < col.length; i++) {
				o[i] = data.get(col[i]);
			}
			return !FunKit.isAllEmpty(o);
		}).collect(Collectors.toList());
	}

	public static List<Map<String, Object>> parseExcel(File file, String... col) throws Exception {
		return parseExcel(new FileInputStream(file), col);
	}

	public ExcelParser createWorkbook() throws Exception {
		this.workbook = new SXSSFWorkbook();
		return this;
	}

	public ExcelParser createSheet(String sheetName) {
		this.sheet = this.workbook.createSheet(sheetName);
		return this;
	}

	public ExcelParser createRow(String[] rowData, boolean isZeroHeight, int rowNum) {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setWrapText(true);
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		
		Font font = workbook.createFont();
		font.setBold(true);
		cellStyle.setFont(font);
		
		if (rowData != null && rowData.length > 0) {
			Row row = sheet.createRow(rowNum);
			row.setZeroHeight(isZeroHeight);
			for (int i = 0; i < rowData.length; i++) {
				Cell cell = row.createCell(i);
				cell.setCellStyle(cellStyle);
				cell.setCellValue(rowData[i]);
			}
		}
		
		return this;
	}
	
	public ExcelParser autoSizeColumn(int columnIndex){
//		if(sheet instanceof SXSSFSheet) {
//			((SXSSFSheet)sheet).trackAllColumnsForAutoSizing();
//		}
		
		for (int i = 0; i < columnIndex; i++) {
			sheet.autoSizeColumn(i);
		}
		
		return this;
	}
	

	public ExcelParser createRow(String[] rowData, int rowNum) {
		return this.createRow(rowData, false, rowNum);
	}

	public ExcelParser fillData(List<Map<String, Object>> list,int startFillRowNum, String... col) {
		
		CellStyle cellStyle = this.workbook.createCellStyle();
		cellStyle.setWrapText(true);
		for (int i = 0, size = list.size(); i < size; i++) {
			Row row = getRow(i + startFillRowNum);
			for (int j = 0; j < col.length; j++) {
				Cell cell = row.createCell(j);
				cell.setCellStyle(cellStyle);
				cell.setCellType(Cell.CELL_TYPE_STRING);
				Object v = list.get(i).get(col[j]);
				cell.setCellValue(v == null ? "" : v.toString());
			}
		}

//		if(sheet instanceof SXSSFSheet) {
//			((SXSSFSheet)sheet).trackAllColumnsForAutoSizing();
//		}
		
		for (int i = 0; i < col.length; i++) {
			sheet.autoSizeColumn(i);
		}
		return this;
	}
	
	public ExcelParser fillData(List<Map<String, Object>> list, String... col) {
		
		return fillData(list, 1, col);
	}
	
	public Row getRow(int rowNum) {
		Row row = sheet.getRow(rowNum);
		return row == null ? sheet.createRow(rowNum) : row;
	}
	
	public ExcelParser fillDataColumn(String[] data,int colIndex) {
		
		for (int i = 0; i < data.length; i++) {
			Row row = getRow(i);
			Cell cell = row.createCell(colIndex);
			cell.setCellType(Cell.CELL_TYPE_STRING);
			cell.setCellValue(data[i]);
		}
		
		return this;
	}
	
	public ExcelParser setColumnHidden(int columnIndex, boolean hidden) {
		sheet.setColumnHidden(columnIndex, hidden);
		return this;
	}

	public ExcelParser createValidationData(String[] validationData, int firstRow, int lastRow, int firstCol,
			int lastCol) {
		DataValidationHelper helper = sheet.getDataValidationHelper();
		// 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);

		DataValidationConstraint constraint = helper.createExplicitListConstraint(validationData);
		DataValidation dataValidation = helper.createValidation(constraint, regions);
		dataValidation.setSuppressDropDownArrow(true);
		dataValidation.setShowErrorBox(true);

		sheet.addValidationData(dataValidation);

		return this;
	}

	
	public ExcelParser createValidationDataFormula(String formula, int firstRow, int lastRow, int firstCol,
			int lastCol) {
		DataValidationHelper helper = sheet.getDataValidationHelper();
		// 设置数据有效性加载在哪个单元格上,四个参数分别是：起始行、终止行、起始列、终止列
		CellRangeAddressList regions = new CellRangeAddressList(firstRow, lastRow, firstCol, lastCol);

		DataValidationConstraint constraint = helper.createFormulaListConstraint(formula);
		DataValidation dataValidation = helper.createValidation(constraint, regions);
		dataValidation.setSuppressDropDownArrow(true);
		dataValidation.setShowErrorBox(true);

		sheet.addValidationData(dataValidation);

		return this;
	}
	
	public void toExcel(OutputStream outputStream) throws IOException {
		this.workbook.write(outputStream);
		this.workbook.close();
	}
	
	public void toExcel(HttpServletResponse response,String exportName) throws IOException {
		 response.reset();
	     response.addHeader("Content-Disposition", "attachment;filename="+URLEncoder.encode(exportName, "UTF-8"));
	     response.addHeader("Content-type", "text/html;charset=UTF-8");
		this.toExcel(response.getOutputStream());
	}

}

