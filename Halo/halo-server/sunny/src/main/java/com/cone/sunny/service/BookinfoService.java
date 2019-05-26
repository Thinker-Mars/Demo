package com.cone.sunny.service;

import java.util.HashMap;
//import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RequestParam;

//import com.cone.sunny.api.AjaxResult;
//import com.cone.sunny.api.FunKit;
import com.cone.sunny.dao.ProductDao;
import com.cone.sunny.api.FunKit;
import com.cone.sunny.dao.BookMetaDao;

import com.cone.sunny.pojo.ProductItem;
import com.cone.sunny.pojo.BookMeta;

import com.github.pagehelper.Page;

@Service
public class BookinfoService implements BookMetaDao {
	
	private static String applyTime = DateTime.now().toString("yyyy-MM-dd");
	
	@Autowired
	private ProductDao productdao;
	@Autowired
	private BookMetaDao bookmetadao;
	
	
	public List<ProductItem> serarchBookId() {
		return productdao.searchProduct();
	}
	
	@Override
	public Page<BookMeta> searchBookMeta(Map<String, Object> bookSearchByInfoMap) {
		return bookmetadao.searchBookMeta(bookSearchByInfoMap);
	}
	
	@Override
	public void uppdateBookMetaByInfo(Map<String, Object> uppdateBookMetaByInfoMap) {
		
		bookmetadao.uppdateBookMetaByInfo(uppdateBookMetaByInfoMap);
		
	}
	
	@Override
	public void bookMetaDeleteById(Map<String, Object> bookMetaDeleteMap) {
		bookmetadao.bookMetaDeleteById(bookMetaDeleteMap);
	}
	
	@Override
	public void addBookMetaByInfo(Map<String, Object> insertBookMetaByInfoMap) {
		bookmetadao.addBookMetaByInfo(insertBookMetaByInfoMap);
	}
	
	@Override
	public void storeDeleteInfo(Map<String, Object> storeDeleteInfoMap) {
		bookmetadao.storeDeleteInfo(storeDeleteInfoMap);
	}
	
	@Override
	public void addBookId(Map<String, Object> uppdateBookMetaByInfoMap) {
		bookmetadao.addBookId(uppdateBookMetaByInfoMap);
	}
	
	//对查询条件进行解析
	public Map<String, Object> dealParam(Map<String, Object> param) {
		Map<String, Object> bookSearchByInfoMap = new HashMap<String, Object>();
		if (FunKit.collectKey(param, "productId")) {
			if (!param.get("productId").equals("") == true) {
				bookSearchByInfoMap.put("productId", param.get("productId"));
			}
		}
		else {
			bookSearchByInfoMap.put("productId", null);
		}
		if (FunKit.collectKey(param, "autherName")) {
			if (!param.get("autherName").equals("") == true) {
				bookSearchByInfoMap.put("autherName", param.get("autherName"));
			}
		}
		else {
			bookSearchByInfoMap.put("autherName", null);
		}
		if (FunKit.collectKey(param, "bookName")) {
			if (!param.get("bookName").equals("") == true) {
				bookSearchByInfoMap.put("bookName", param.get("bookName"));
			}
		}
		else {
			bookSearchByInfoMap.put("bookName", null);
		}
		if (FunKit.collectKey(param, "publishCompany")) {
			if (!param.get("publishCompany").equals("") == true) {
				bookSearchByInfoMap.put("publishCompany", param.get("publishCompany"));
			}
		}
		else {
			bookSearchByInfoMap.put("publishCompany", null);
		}

		return bookSearchByInfoMap;
	}
	
	//将新增图书信息存入中间表book_work_add,并标记为信息新增
	public void addBookMeta(Map<String, Object> InfoMap) {
		//String applyTime = DateTime.now().toString("yyyy-MM-dd");
		Map<String, Object> insertBookMetaByInfoMap = new HashMap<String, Object>();
		insertBookMetaByInfoMap.put("productid", InfoMap.get("productCategories"));
		insertBookMetaByInfoMap.put("autherName", InfoMap.get("autherTag"));
		insertBookMetaByInfoMap.put("publish_time", InfoMap.get("time").toString());
		insertBookMetaByInfoMap.put("publishCompany", InfoMap.get("publishCompany"));
		insertBookMetaByInfoMap.put("brief", InfoMap.get("brief"));
		insertBookMetaByInfoMap.put("starLevel", InfoMap.get("starLevel"));
		insertBookMetaByInfoMap.put("name", InfoMap.get("name"));
		insertBookMetaByInfoMap.put("price", InfoMap.get("price"));
		insertBookMetaByInfoMap.put("is_audit", "00");
		insertBookMetaByInfoMap.put("apply_time", applyTime);
		insertBookMetaByInfoMap.put("apply_worker_id", InfoMap.get("applyWorkerId"));
		insertBookMetaByInfoMap.put("model", "01");//标记为信息新增
		this.addBookMetaByInfo(insertBookMetaByInfoMap);
	}
	
	@SuppressWarnings("unchecked")
	public void uppdateBookMeta(Map<String, Object> param) {
		//String auditTime = DateTime.now().toString("yyyy-MM-dd");
		Map<String, Object> InfoMap = (Map<String, Object>)param.get("bookMetaForm");
		int productId = this.getBookMetaProductId(InfoMap);
		Map<String, Object> uppdateBookMetaByInfoMap = new HashMap<String, Object>();
		//InfoMap = (Map<String, Object>)param.get("bookMetaForm");
		//uppdateBookMetaByInfoMap.put("id", InfoMap.get("id"));
		uppdateBookMetaByInfoMap.put("productid", productId);
		uppdateBookMetaByInfoMap.put("autherName", InfoMap.get("autherTag"));
		uppdateBookMetaByInfoMap.put("publish_time", InfoMap.get("time").toString());
		uppdateBookMetaByInfoMap.put("publishCompany", InfoMap.get("publishCompany"));
		uppdateBookMetaByInfoMap.put("brief", InfoMap.get("brief"));
		uppdateBookMetaByInfoMap.put("starLevel", InfoMap.get("starLevel"));
		uppdateBookMetaByInfoMap.put("name", InfoMap.get("name"));
		uppdateBookMetaByInfoMap.put("price", InfoMap.get("price"));
		uppdateBookMetaByInfoMap.put("is_audit", "00");
		uppdateBookMetaByInfoMap.put("model", "02");//标记为信息编辑
		uppdateBookMetaByInfoMap.put("apply_time", applyTime);
		uppdateBookMetaByInfoMap.put("apply_worker_id", InfoMap.get("applyWorkerId"));
		//this.addBookMetaByInfo(uppdateBookMetaByInfoMap);
		uppdateBookMetaByInfoMap.put("bookid", InfoMap.get("id"));
		this.addBookId(uppdateBookMetaByInfoMap);
		//this.uppdateBookMetaByInfo(uppdateBookMetaByInfoMap);
		//return uppdateBookMetaByInfoMap;
	}
	
	@Override
	public int getBookMetaProductId(Map<String, Object> bookMetaDeleteByIdMap) {
		return bookmetadao.getBookMetaProductId(bookMetaDeleteByIdMap);
	}
	
	public Map<String, Object> deleteParam(Map<String, Object> param, int productId) {
		Map<String, Object> storeDeleteInfoMap = new HashMap<String, Object>();
		storeDeleteInfoMap.put("name", param.get("name"));
		storeDeleteInfoMap.put("productid", productId);
		storeDeleteInfoMap.put("authorTag", param.get("autherTag"));
		storeDeleteInfoMap.put("price", param.get("price"));
		storeDeleteInfoMap.put("publishCompany", param.get("publishCompany"));
		storeDeleteInfoMap.put("starLevel", param.get("starLevel"));
		storeDeleteInfoMap.put("time", param.get("time"));
		storeDeleteInfoMap.put("brief", param.get("brief"));
		storeDeleteInfoMap.put("apply_time", applyTime);
		storeDeleteInfoMap.put("is_audit", "00");
		storeDeleteInfoMap.put("model", "03");//标记为信息删除
		storeDeleteInfoMap.put("apply_worker_id", param.get("applyWorkerId"));
		return storeDeleteInfoMap;
	}
	

	

	

}
