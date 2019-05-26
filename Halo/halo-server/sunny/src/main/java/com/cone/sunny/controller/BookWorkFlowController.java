package com.cone.sunny.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.joda.time.DateTime;

import com.cone.sunny.pojo.ApplyInfo;
import com.cone.sunny.pojo.BookAudit;
import com.cone.sunny.pojo.Worker;
import com.cone.sunny.service.BookWorkFlowService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.cone.sunny.api.ExecutionResult;

/**
 * 
 * @author Cone
 *书籍信息流程处理服务
 */
@RestController
@RequestMapping("/workflow")
public class BookWorkFlowController {
	
	private static String auditTime = DateTime.now().toString("yyyy-MM-dd");
	
	@Autowired
	private BookWorkFlowService bookWorkFlowService;
	/**
	 * 
	 * @param 流程号
	 * @return返回需要审核的图书列表以及总数(新增)
	 */
	@GetMapping(value = "/bookaudit")
	public Map<String, Object> SearchAuditByWorkId(@RequestParam Map<String, Object> param) {
		int cur_page=Integer.parseInt(param.get("cur_page").toString());
		int pagesize = Integer.parseInt(param.get("pagesize").toString());
		Map<String, Object> searchAuditMap = new HashMap<String, Object>();
		searchAuditMap.put("model", param.get("model"));
		searchAuditMap.put("workid", param.get("workid"));
		PageHelper.startPage(cur_page, pagesize);
		Page<BookAudit> bookAuditPage = bookWorkFlowService.SearchAuditByWorkId(searchAuditMap);
		Map<String, Object> bookAuditMap = new HashMap<String, Object>();
		bookAuditMap.put("bookAudit",bookAuditPage);
		bookAuditMap.put("total", bookAuditPage.getTotal());
		return bookAuditMap;
	}
	/**
	 * 
	 * 
	 * @return返回审核人id与name
	 */
	@GetMapping(value = "/workerinfo")
	public List<Worker> searchWorkerInfo() {
		List<Worker> workerList = bookWorkFlowService.SearchWorkerInfo();
		return workerList;
		
	}
	
	/**
	 * 
	 * @param param 对申请的内容进行处理
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@PostMapping(value = "auditprocess")
	public Map<String, Object> AuditProcess(@RequestBody Map<String, Object> param) {
		int code = 200;
		
		Map<String, Object> auditInfoMap = new HashMap<String, Object>();
		auditInfoMap = (Map<String, Object>)param.get("approveDataForm");
		int workId = (int)auditInfoMap.get("workid");
		String model = bookWorkFlowService.SearchBookMetaModel(workId);//取得图书信息来源
		bookWorkFlowService.RefreshAuditState(workId);//更新审核状态
		try {
			//审核同意
			if (auditInfoMap.get("label").toString().equals("00")) {
				
				bookWorkFlowService.StoreAuditHis(auditInfoMap,auditTime, model, workId);
			}
			else {
				//审核不同意
				bookWorkFlowService.AuditFail(auditInfoMap, auditTime, model, workId);

			}
			return new ExecutionResult().Result(code);
			
		} catch (Exception e) {
			code = -1;
			System.out.println(e.getMessage());
			return new ExecutionResult().Result(code);
		}
		
	}
	
	
	/**
	 * 查看我的申请(当前登陆用户)
	 * @param param 分页查询条件，以及员工id
	 * @return 返回属于当前登陆用户的申请信息map
	 */
	@GetMapping(value = "/myapply")
	public Map<String, Object> CheckMyApply(@RequestParam Map<String, Object> param) {
		int cur_page=Integer.parseInt(param.get("cur_page").toString());
		int pagesize = Integer.parseInt(param.get("pagesize").toString());
		Map<String, Object> searchApplyMap = new HashMap<String, Object>();
		searchApplyMap.put("id",param.get("id"));
		searchApplyMap.put("applyTime",param.get("applyTime"));
		PageHelper.startPage(cur_page, pagesize);
		Page<ApplyInfo> userApplyPage = bookWorkFlowService.CheckUserApplyInfoByWorkId(searchApplyMap);
		Map<String, Object> userApplyMap = new HashMap<String, Object>();
		userApplyMap.put("userApply",userApplyPage);
		userApplyMap.put("total", userApplyPage.getTotal());
		return userApplyMap;
	}
	
	/**
	 * 
	 * 
	 * @param param 审核结果标志，流程id
	 * @return具体申请及审核信息
	 */
	@GetMapping(value = "/myapplyInfo")
	public Map<String, Object> SearchAuditInfoByWorkId(@RequestParam Map<String, Object> param) {
		String auditResult = (String)param.get("auditResult");
		String workId = (String)param.get("workid");
		Map<String, Object> userApplyInfoMap = new HashMap<String, Object>();
		try {
			if (auditResult.equals("00")) {
				//审核不通过
				userApplyInfoMap = bookWorkFlowService.SearchFailAuditInfoByWorkId(workId);
			}
			else {
				//审核通过
				userApplyInfoMap = bookWorkFlowService.SearchSuccessAuditInfoByWorkId(workId);
			}
			return userApplyInfoMap;
		} catch (Exception e) {
			int code = -1;
			System.out.println(e.getMessage());
			return new ExecutionResult().Result(code);
		}
	}

}
