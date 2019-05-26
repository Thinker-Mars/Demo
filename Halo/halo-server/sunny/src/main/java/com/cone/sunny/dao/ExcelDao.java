package com.cone.sunny.dao;

import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ExcelDao {
	//将Excel文件的数据写入新增中间表中(book_work_add)
	public void bookMetaExcel(Map<String, Object> bookMetaExcelMap);

}
