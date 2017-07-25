package com.tgp.erp.newsync.syncform.vo;

import java.util.Date;

public class Option {
	private Integer optionId;
	private Integer fieldId;
	private String optionCode;
	private String fieldValue;
	private Integer sortId;
	private Integer state;
	private Date updateTime;
	private Date entryTime;
	private Integer haveText;

	public Option(Integer optionID) {
		this.optionId = optionID;
	}

	public Integer getOptionId() {
		return optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}

	public Integer getFieldId() {
		return fieldId;
	}

	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
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

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public String getOptionCode() {
		return optionCode;
	}

	public void setOptionCode(String optionCode) {
		this.optionCode = optionCode;
	}

	public Integer getHaveText() {
		return haveText;
	}

	public void setHaveText(Integer haveText) {
		this.haveText = haveText;
	}
}
