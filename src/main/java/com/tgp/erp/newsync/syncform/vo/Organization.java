package com.tgp.erp.newsync.syncform.vo;

import java.util.Date;
import java.util.List;

public class Organization {
	private Integer id;
	private String text;
	private Integer orgId; // 组织机构id
	private Integer parentId;
	private Organization parent; // 上级组织机构id
	private String orgName;
	private String corpCode; // 法人code
	private String orgCode; // 组织的code
	private String orgType; // 组织机构类型
	private Integer orgState; // 状态
	private Integer lv; // 组织机构级别
	private Integer sortId; // 组织机构顺序
	// @DateTimeFormat( pattern = "yyyy-MM-dd")
	// @JsonFormat(pattern =
	// "yyyy-MM-dd",timezone="GMT+8")//要根据所在国家选择时区，中国是东8区，日本是东9区
	private Date updateTime; // 更改时间
	private Integer updateUser; // 更改者id
	// @DateTimeFormat( pattern = "yyyy-MM-dd")
	// @JsonFormat(pattern =
	// "yyyy-MM-dd",timezone="GMT+8")//要根据所在国家选择时区，中国是东8区，日本是东9区
	private Date entryTime; // 添加时间
	private Integer entryUser; // 添加者id
	private List<Organization> children; // 子部门
	private String orgChain;// org_chain
	private Integer leaderID;// 负责人Id
	// private List<User> users; //部门员工
	private Integer orgRegion;

	public void setOrganization(String orgName, String corpCode, String orgCode,
			String orgType, Integer orgState, Integer lv, Integer sortId,
			Integer orgRegion) {
		this.orgName = orgName;
		this.corpCode = corpCode;
		this.orgCode = orgCode;
		this.orgType = orgType;
		this.orgState = orgState;
		this.lv = lv;
		this.sortId = sortId;
		this.orgRegion = orgRegion;
	}

	public Organization(Integer id) {
		this.id = id;
	}

	public Integer getLeaderID() {
		return leaderID;
	}

	public void setLeaderID(Integer leaderID) {
		this.leaderID = leaderID;
	}

	public String getOrgChain() {
		return orgChain;
	}

	public void setOrgChain(String orgChain) {
		this.orgChain = orgChain;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getOrgId() {
		return orgId;
	}

	public void setOrgId(Integer orgId) {
		this.orgId = orgId;
		this.id = orgId;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public Organization getParent() {
		return parent;
	}

	public void setParent(Organization parent) {
		this.parent = parent;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;

		this.text = ((corpCode != null) && (corpCode.trim() != "") ? corpCode + "-"
				: "")
				+ ((orgCode != null) && (orgCode.trim() != "") ? orgCode + " / " : "")
				+ orgName;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public Integer getOrgState() {
		return orgState;
	}

	public void setOrgState(Integer orgState) {
		this.orgState = orgState;
	}

	public Integer getLv() {
		return lv;
	}

	public void setLv(Integer lv) {
		this.lv = lv;
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

	public Integer getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(Integer updateUser) {
		this.updateUser = updateUser;
	}

	public Date getEntryTime() {
		return entryTime;
	}

	public void setEntryTime(Date entryTime) {
		this.entryTime = entryTime;
	}

	public Integer getEntryUser() {
		return entryUser;
	}

	public void setEntryUser(Integer entryUser) {
		this.entryUser = entryUser;
	}

	public List<Organization> getChildren() {
		return children;
	}

	public void setChildren(List<Organization> children) {
		this.children = children;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCorpCode() {
		return corpCode;
	}

	public void setCorpCode(String corpCode) {
		if (corpCode.length() == 1) {
			corpCode = "0" + corpCode;
		}
		this.corpCode = corpCode;
	}

	public Integer getOrgRegion() {
		return orgRegion;
	}

	public void setOrgRegion(Integer orgRegion) {
		this.orgRegion = orgRegion;
	}

}
