package com.cone.sunny.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.cone.sunny.pojo.ApplyInfo;
import com.cone.sunny.pojo.BookAudit;
import com.cone.sunny.pojo.Worker;
//import com.cone.sunny.pojo.BookMeta;
import com.github.pagehelper.Page;

@Mapper
public interface BookAuditDao {
	//查询需要审核的图书信息
	public Page<BookAudit> SearchAuditByWorkId(Map<String, Object> searchAuditMap);
	//查询审批人信息
	public List<Worker> SearchWorkerInfo();
	//更新book_work_add表的审核状态
	public void RefreshAuditState(int workId);
	//审核失败
	public void AuditFail(Map<String, Object> auditResultMap);
	//将新增书籍信息入库
	public void StoreBookMeta(Map<String, Object> bookMetaMap);
	//查询新增图书信息id
	public int SearchBookMetaId(Map<String, Object> bookMetaInfoMap);
	//将审核信息记录在历史表中(新增)
	public void AuditSuccess(Map<String, Object> auditHisResultMap);
	//图书删除审批成功(删除)
	public void AuditDeleteSuccess(Map<String, Object> auditHisResultMap);
	//审核通过将book_work_add表的audit_result字段置为01
	public void SuccessChange(int successFlag);
	//审核不通过将book_work_add表的audit_result字段置为00
	public void FailChange(int workId);
	//查询当前登陆用户的申请信息
	public Page<ApplyInfo> CheckUserApplyInfoByWorkId(Map<String, Object> searchApplyMap);
	//查询申请的具体审核信息(审核成功)
	public Map<String, Object> SearchSuccessAuditInfoByWorkId(String workId);
	//查询申请的具体审核信息(审核失败)
	public Map<String, Object> SearchFailAuditInfoByWorkId(String workId);
	//通过流程号查询图书信息来源
	public String SearchBookMetaModel(int workId);
	//从book表删除图书信息
	public void DeleteFromBookById(int bookId);
	//删除审核失败，恢复图书状态
	public void RecoveryBookMeta(int bookId);
	//编辑图书信息时取得bookid
	public int GetBookId(Map<String, Object> auditHisResultMap);
	//更新图书信息
	public void UpdateBookMetaInfoById(Map<String, Object> auditInfoMap);
	//Excel导入信息审核成功
	public void excelAuditSuccess(Map<String, Object> auditHisResultMap);
	
	

}
