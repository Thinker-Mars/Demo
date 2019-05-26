package com.cone.sunny.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.cone.sunny.service.BookinfoService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
//import com.github.pagehelper.PageInfo;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.DeleteMapping;
import com.cone.sunny.api.ExecutionResult;
//import com.cone.sunny.api.FunKit;
import com.cone.sunny.pojo.ProductItem;
import com.cone.sunny.pojo.BookMeta;


/**
 * 
 * @author Cone
 *图书元数据管理服务
 */
@ComponentScan({"com.cone.sunny.service.bookinfoService"})
@RestController
@RequestMapping("/bookinfo")
public class BookinfoController {
	
	@Autowired
	private BookinfoService bookinfoservice;

	/**
	 * 
	 * @param param
	 * @return返回图书类别列表
	 */
	@GetMapping(value = "/bookid")
	public List<ProductItem> searchBookId(@RequestParam Map<String, Object> param) {
		List<ProductItem> productItemList = bookinfoservice.serarchBookId();
		return productItemList;
	}
	
	//查询图书信息
	@GetMapping(value = "/bookmeta")
	public Map<String, Object> searchBookMeta(@RequestParam Map<String, Object> param) {
		
		int cur_page=Integer.parseInt(param.get("cur_page").toString());
		int pagesize = Integer.parseInt(param.get("pagesize").toString());
		PageHelper.startPage(cur_page, pagesize);
		Page<BookMeta> bookMetaPage = bookinfoservice.searchBookMeta(bookinfoservice.dealParam(param));
		Map<String, Object> bookMap = new HashMap<String, Object>();
		bookMap.put("bookMeta",bookMetaPage);
		bookMap.put("total", bookMetaPage.getTotal());
		return bookMap;
	}
	
	//图书信息更新
	@PostMapping(value = "bookmetauppdate")
	public Map<String, Object> uppdateBookMetaByInfo(@RequestBody Map<String, Object> param) {
		int code = 200;

		try {
			bookinfoservice.uppdateBookMeta(param);
			//bookinfoservice.uppdateBookMetaByInfo(uppdateBookMetaByInfoMap);
			
			return new ExecutionResult().Result(code);
		} catch (Exception e) {
			code = -1;
			
			System.out.println(e.getMessage());
			return new ExecutionResult().Result(code);
		}
	}
	//删除图书信息
	@DeleteMapping(value = "bookMetaDeleteById")
	public Map<String, Object> bookMetaDeleteById(@RequestParam Map<String, Object> param) {
		int code = 200;
		
		try {
			bookinfoservice.bookMetaDeleteById(param);//伪删除
			int productId = bookinfoservice.getBookMetaProductId(param);
			Map<String, Object> storeDeleteInfoMap = bookinfoservice.deleteParam(param, productId);
			bookinfoservice.storeDeleteInfo(storeDeleteInfoMap);
			return new ExecutionResult().Result(code);
		} catch (Exception e) {
			code = -1;
			
			System.out.println(e.getMessage());
			return new ExecutionResult().Result(code);
		}

	}
	//新增图书信息
	@SuppressWarnings("unchecked")
	@PostMapping(value = "addbookmeta")
	public Map<String, Object> addBookMetaByInfo(@RequestBody Map<String, Object> param) {
		Map<String, Object> InfoMap = new HashMap<String, Object>();
		int code = 200;
		InfoMap = (Map<String, Object>)param.get("bookAddForm");
		try {
			bookinfoservice.addBookMeta(InfoMap);
			//this.addBookMeta(InfoMap);
			return new ExecutionResult().Result(code);
					
		} catch (Exception e) {
			code = -1;
			System.out.println(e.getMessage());
			return new ExecutionResult().Result(code);
		}
		
		
	}
	
		

}
