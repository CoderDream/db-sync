package com.tgp.erp.newsync.syncform.vo;

public class Flow {
	private Integer formFlowId; // 流程实例id
	private Integer flowId; // 流程id 人事受付
	private Integer formTypeId; // 表单类型id
	private Integer formId; // 表单id
	private Integer roleId; // 角色id
	private Integer sortId; // 顺序
	private Integer state; // 状态
	private String fieldName; // field名
	private Integer isNecessary;// 是否必要
	private Integer synUserId; // 承认者id 010143
	private Integer status; // 承认状态 受付済
	private String statusValue; // 承认结果
	private Integer handlerType;// 处理类型
	private String updateTime; // 更新时间（审批时间）2012/12/29 20:52:31

	public Integer getFlowId() {
		return flowId;
	}

	public void setFlowId(Integer flowId) {
		this.flowId = flowId;
	}

	public Integer getFormTypeId() {
		return formTypeId;
	}

	public void setFormTypeId(Integer formTypeId) {
		this.formTypeId = formTypeId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Integer getIsNecessary() {
		return isNecessary;
	}

	public void setIsNecessary(Integer isNecessary) {
		this.isNecessary = isNecessary;
	}

	public Integer getFormFlowId() {
		return formFlowId;
	}

	public void setFormFlowId(Integer formFlowId) {
		this.formFlowId = formFlowId;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getSynUserId() {
		return synUserId;
	}

	public void setSynUserId(Integer synUserId) {
		this.synUserId = synUserId;
	}

	public Integer getHandlerType() {
		return handlerType;
	}

	public void setHandlerType(Integer handlerType) {
		this.handlerType = handlerType;
	}

	public String getStatusValue() {
		return statusValue;
	}

	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}
}
