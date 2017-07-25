package com.tgp.erp.newsync.syncform.vo;

import java.util.Date;
import java.util.List;

public class ItemType {
	private Integer itemTypeId;   //种类ID
	private String itemTypeCode;	//种类描述（编号）
	private String itemTypeName;	//种类名
	private String parentId;    //上一级的种类ID
	private ItemType parent;    //上级种类
	private Integer lv;      // 种类等级
	private String itState;       //是否启用
	private Date updateTime;    // 更新时间
	private Integer updateId;   //更新者ID
	private Date entryTime;     //添加时间
	private Integer entryId;    //添加者ID
	private List<ItemType> children; //子itemtype
	private Integer id;		//没有实际意义，easyui树状显示
	private String text;	//没有实际意义，easyui树状图显示
	private String state;
	public Integer getItemTypeId() {
		return itemTypeId;
	}
	public void setItemTypeId(Integer itemTypeId) {
		this.itemTypeId = itemTypeId;
		this.id=this.itemTypeId;
	}
	public String getItemTypeCode() {
		return itemTypeCode;
	}
	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}
	public String getItemTypeName() {
		return itemTypeName;
	}
	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
		this.text = this.itemTypeCode+" "+this.itemTypeName;
	}
	public String getParentId() {
		return parentId;
	}
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
	public ItemType getParent() {
		return parent;
	}
	public void setParent(ItemType parent) {
		this.parent = parent;
	}
	public Integer getLv() {
		return lv;
	}
	public void setLv(Integer lv) {
		this.lv = lv;
	}
	public String getItState() {
		return itState;
	}
	public void setItState(String itState) {
		this.itState = itState;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public Integer getUpdateId() {
		return updateId;
	}
	public void setUpdateId(Integer updateId) {
		this.updateId = updateId;
	}
	public Date getEntryTime() {
		return entryTime;
	}
	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}
	public Integer getEntryId() {
		return entryId;
	}
	public void setEntryId(Integer entryId) {
		this.entryId = entryId;
	}
	public List<ItemType> getChildren() {
		return children;
	}
	public void setChildren(List<ItemType> children) {
		this.children = children;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
}
