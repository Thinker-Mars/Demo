package com.cone.sunny.pojo;

import java.io.Serializable;
//申请信息实体表
@SuppressWarnings("serial")
public class ApplyInfo implements Serializable {
	private int workid;//流程id
	private String productcategories;//图书上架分类
	private String apply_time;//申请时间
	private String audit;//审核状态
	private String model_cn_name;//信息来源
	private String is_audit;//审核状态
	private String audit_result;
	
	public ApplyInfo() {
		
	}
	
	public ApplyInfo(int workid, String productcategories, String apply_time, String audit,
			String model_cn_name, String is_audit, String audit_result)  {
		
		this.workid = workid;
		this.productcategories = productcategories;
		this.apply_time = apply_time;
		this.audit = audit;
		this.model_cn_name = model_cn_name;
		this.is_audit = is_audit;
		this.audit_result = audit_result;
	}
	
	public String getAuditResult() {
		return audit_result;
	}
	
	public String getAuditState() {
		return is_audit;
	}
	
	public int getWorkid() {
		return workid;
	}
	public String getProductcategories() {
		return productcategories;
	}
	public String getApply_time() {
		return apply_time;
	}
	public String getAudit() {
		return audit;
	}
	public String getModel_cn_name() {
		return model_cn_name;
	}
	public void setWorkid(int workid) {
		this.workid = workid;
	}
	public void setProductcategories(String productcategories) {
		this.productcategories = productcategories;
	}
	public void setApply_time(String apply_time) {
		this.apply_time = apply_time;
	}
	public void setAudit(String audit) {
		this.audit = audit;
	}
	public void setModel_cn_name(String model_cn_name) {
		this.model_cn_name = model_cn_name;
	}
	
	public void setAuditState(String is_audit) {
		this.is_audit = is_audit;
	}
	
	public void setAuditResult(String audit_result) {
		this.audit_result = audit_result;
	}

}
