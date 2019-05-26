package com.cone.sunny.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cone.sunny.dao.BookAuditDao;
import com.cone.sunny.pojo.ApplyInfo;
import com.cone.sunny.pojo.BookAudit;
import com.cone.sunny.pojo.Worker;
//import com.cone.sunny.pojo.BookMeta;
import com.github.pagehelper.Page;

@Service
public class BookWorkFlowService implements BookAuditDao {
	
	@Autowired
	private BookAuditDao bookAuditDao;
	
	@Override
	public Page<BookAudit> SearchAuditByWorkId(Map<String, Object> searchAuditMap) {
		return bookAuditDao.SearchAuditByWorkId(searchAuditMap);
	}
	
	@Override
	public List<Worker> SearchWorkerInfo() {
		return bookAuditDao.SearchWorkerInfo();
	}
	
	@Override
	public void AuditFail(Map<String, Object> auditResultMap) {
		bookAuditDao.AuditFail(auditResultMap);
	}
	
	@Override
	public void StoreBookMeta(Map<String, Object> bookMetaMap) {
		bookAuditDao.StoreBookMeta(bookMetaMap);
	}
	
	@Override
	public int SearchBookMetaId(Map<String, Object> bookMetaInfoMap) {
		return bookAuditDao.SearchBookMetaId(bookMetaInfoMap);
	}
	
	@Override
	public void AuditSuccess(Map<String, Object> auditHisResultMap) {
		bookAuditDao.AuditSuccess(auditHisResultMap);
	}
	
	@Override
	public void AuditDeleteSuccess(Map<String, Object> auditHisResultMap) {
		bookAuditDao.AuditDeleteSuccess(auditHisResultMap);
	}
	
	@Override
	public void RefreshAuditState(int workId) {
		bookAuditDao.RefreshAuditState(workId);
	}
	
	@Override
	public void SuccessChange(int successFlag) {
		bookAuditDao.SuccessChange(successFlag);
	}
	
	@Override
	public void FailChange(int workId) {
		bookAuditDao.FailChange(workId);
	}
	
	@Override
	public Page<ApplyInfo> CheckUserApplyInfoByWorkId(Map<String, Object> searchApplyMap) {
		return bookAuditDao.CheckUserApplyInfoByWorkId(searchApplyMap);
	}
	
	@Override
	public Map<String, Object> SearchSuccessAuditInfoByWorkId(String workId) {
		return bookAuditDao.SearchSuccessAuditInfoByWorkId(workId);
	}
	
	@Override
	public Map<String, Object> SearchFailAuditInfoByWorkId(String workId) {
		return bookAuditDao.SearchFailAuditInfoByWorkId(workId);
	}
	
	@Override
	public String SearchBookMetaModel(int workId) {
		return bookAuditDao.SearchBookMetaModel(workId);
	}
	
	@Override
	public void DeleteFromBookById(int bookId) {
		bookAuditDao.DeleteFromBookById(bookId);
	}
	
	@Override
	public void RecoveryBookMeta(int bookId) {
		bookAuditDao.RecoveryBookMeta(bookId);
	}
	
	@Override
	public int GetBookId(Map<String, Object> auditHisResultMap) {
		return bookAuditDao.GetBookId(auditHisResultMap);
	}
	
	@Override
	public void UpdateBookMetaInfoById(Map<String, Object> auditInfoMap) {
		bookAuditDao.UpdateBookMetaInfoById(auditInfoMap);
	}
	
	@Override
	public void excelAuditSuccess(Map<String, Object> auditHisResultMap) {
		bookAuditDao.excelAuditSuccess(auditHisResultMap);
	}
	
	
	//书籍信息写入book表
	public void StoreBook(Map<String, Object> auditInfoMap) {

		Map<String, Object> auditResultInfoMap = this.commonMap(auditInfoMap);
		this.StoreBookMeta(auditResultInfoMap);
	}
	
	
	//将审核成功信息记入历史表中
	public void StoreAuditHis(Map<String, Object> auditInfoMap, String auditTime,
			String model, int workId) {
		Map<String, Object> auditHisResultMap = new HashMap<String, Object>();
		this.SuccessChange(workId);//更新审核结果标志audit_result为01
		auditHisResultMap.put("worker_id", auditInfoMap.get("workerName"));
		auditHisResultMap.put("workid", auditInfoMap.get("workid"));
		auditHisResultMap.put("audit_time", auditTime);
		auditHisResultMap.put("model", model);
		if (model.equals("03")) {
			//图书信息删除
			this.AuditDeleteSuccess(auditHisResultMap);
			int bookId = SearchBookMetaId(commonMap(auditInfoMap));
			this.DeleteFromBookById(bookId);
		}
		else if (model.equals("02")) {
			//图书信息编辑
			this.AuditDeleteSuccess(auditHisResultMap);
			int bookId = this.GetBookId(auditHisResultMap);
			auditInfoMap.put("bookId", bookId);
			this.UpdateBookMetaInfoById(auditInfoMap);
			
		}
		else if (model.equals("04")) {
			//Excel导入
			this.StoreBook(auditInfoMap);
			int bookId = SearchBookMetaId(commonMap(auditInfoMap));
			auditHisResultMap.put("book_id", bookId);
			auditHisResultMap.put("excelModel", "04");
			this.excelAuditSuccess(auditHisResultMap);
			
		}
		else {
			//图书信息新增
			this.StoreBook(auditInfoMap);
			int bookId = SearchBookMetaId(commonMap(auditInfoMap));
			auditHisResultMap.put("book_id", bookId);
			this.AuditSuccess(auditHisResultMap);
		}

	}
	
	//审核不同意
	public void AuditFail(Map<String, Object> auditInfoMap, String auditTime, String model, int workId) {
		if (model.equals("03")) {
			int bookId = SearchBookMetaId(commonMap(auditInfoMap));
			this.RecoveryBookMeta(bookId);//恢复图书显示
		}
		Map<String, Object> auditResultMap = new HashMap<String, Object>();
		auditResultMap.put("worker_id", auditInfoMap.get("workerName"));
		auditResultMap.put("workid", auditInfoMap.get("workid"));
		auditResultMap.put("audit_time", auditTime);
		this.FailChange(workId);//更新审核结果标志audit_result为00
		this.AuditFail(auditResultMap);
	}
	
	public Map<String, Object> commonMap(Map<String, Object> auditInfoMap) {
		Map<String, Object> auditResultMap = new HashMap<String, Object>();
		auditResultMap.put("name", auditInfoMap.get("name"));
		auditResultMap.put("author_tag", auditInfoMap.get("author_tag"));
		auditResultMap.put("price", auditInfoMap.get("price"));
		auditResultMap.put("publish_company", auditInfoMap.get("publish_company"));
		auditResultMap.put("star_level", auditInfoMap.get("star_level"));
		auditResultMap.put("brief", auditInfoMap.get("brief"));
		auditResultMap.put("time", auditInfoMap.get("publishtime"));
		auditResultMap.put("productid", auditInfoMap.get("productid"));
		auditResultMap.put("is_show", "00");
		return auditResultMap;
		
	}
	

}
