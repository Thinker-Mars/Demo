package com.cone.sunny.dao;

//import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import com.cone.sunny.pojo.BookMeta;
import com.github.pagehelper.Page;

@Mapper
public interface BookMetaDao {
	//获取图书详细信息
	public Page<BookMeta> searchBookMeta(Map<String, Object> bookSearchByInfoMap);
	//修改图书信息
	public void uppdateBookMetaByInfo(Map<String, Object> uppdateBookMetaByInfoMap);
	//根据图书的唯一标识id删除图书信息(伪删除)
	public void bookMetaDeleteById(Map<String, Object> bookMetaDeleteMap);
	//将删除图书信息写入book_work_add
	public void storeDeleteInfo(Map<String, Object> storeDeleteInfoMap);
	//新增图书信息
	public void addBookMetaByInfo(Map<String, Object> insertBookMetaByInfoMap);
	//取得图书上架分类的id
	public int getBookMetaProductId(Map<String, Object> bookMetaDeleteByIdMap);
	//编辑图书信息
	public void addBookId(Map<String, Object> uppdateBookMetaByInfoMap);


}
