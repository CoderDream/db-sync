package com.tgp.erp.newsync.syncform.vo;

import java.util.Date;
import java.util.List;

public class Section {
	private Integer sectionId;
	private String sectionTitle;
	private Integer formType;
	private Integer state;
	private Integer sortId;
	private Date updateTime;
	private User updateUser;
	private Date entryTime;
	private User entryUser;
	private String sectionName;
	private Integer formId;
	private List<Field> groups;
	private List<Field> fields;
	private Integer selectable;
	private Integer hidden;
	private String remark;

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public String getSectionTitle() {
		return sectionTitle;
	}

	public void setSectionTitle(String sectionTitle) {
		this.sectionTitle = sectionTitle;
	}

	public Integer getFormType() {
		return formType;
	}

	public void setFormType(Integer formType) {
		this.formType = formType;
	}

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Integer getSortId() {
		return sortId;
	}

	public void setSortId(Integer sortId) {
		this.sortId = sortId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public User getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(User updateUser) {
		this.updateUser = updateUser;
	}

	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public User getEntryUser() {
		return entryUser;
	}

	public void setEntryUser(User entryUser) {
		this.entryUser = entryUser;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		// if(this.groups!=null){
		// fields.addAll(this.groups);
		//// groups = null;
		//// Collections.sort(fields);
		// }
		this.fields = fields;
	}

	public String getSectionName() {
		return sectionName;
	}

	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public List<Field> getGroups() {
		return groups;
	}

	public void setGroups(List<Field> groups) {
		this.groups = groups;
	}

	public Integer getSelectable() {
		return selectable;
	}

	public void setSelectable(Integer selectable) {
		this.selectable = selectable;
	}

	public Integer getHidden() {
		return hidden;
	}

	public void setHidden(Integer hidden) {
		this.hidden = hidden;
	}
}
