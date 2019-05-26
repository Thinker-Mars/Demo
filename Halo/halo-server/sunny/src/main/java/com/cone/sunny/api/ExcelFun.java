package com.cone.sunny.api;

import java.util.List;

import org.apache.poi.ss.usermodel.Row;

@FunctionalInterface
public interface ExcelFun {
	
	List<String[]> getCols(Row row, int k);
	
}
