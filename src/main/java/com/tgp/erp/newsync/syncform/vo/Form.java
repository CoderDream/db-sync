package com.tgp.erp.newsync.syncform.vo;

import java.util.Date;
import java.util.List;

public class Form {
	private Integer id;		//主键，没有其他实际意义，仅用于树状图展示
	private Integer formId;		//表单id
	private Integer userId;		//申请者id  1
	private String agId;		//申请者agid
	private String userName;	//申请者名字
	private String formNo;		//表单号b    1
	private String title;		//表单标题（件名）1
	private Integer formTypeId;	//表单类型id	1
	private String formTypeName;	//表单类型名
	private Integer itemTypeId;	//申请表对应项目id	1 map缓存查询
	private String itemTypeName;	//项目名
	private Organization org;	//组织部门		1
	private String remark;		//备注
	private Integer state;		//状态
	private Integer flowFormId; //当前完成的note_id（为head申请加的）
	private Flow headFlow;  //head中的Flow_form_id对应（为head申请加的）
	private String fieldName;
	private String value;
	private Integer status;		//承认状态		1
	private String statusValue;	//承认状态对应码表字段
	private Date updateTime;	//更新时间		1
	private User updateUser;	//更新者		1
	private Date entryTime;		//添加时间		1
	private User entryUser;		//添加者id	1
	private List<Field> fields;	//fieldlist	1
	private List<Section> sections;	//段落list	
	private List<Flow> flows;	//流程	1
	private List<Flow> flowInsts;	//流程实例（申请真正的执行流程，区别于申请类型的通用流程）	1
	private ItemType itemType;	//申请表对应的项目
	private String itemTypeCode;	//项目代码
	private List<Form> children;	//树状展示时的children
	private Integer parentId;	//formTypeId,用于树状展示时使用
	private Integer synUserId;	//当前承认者id	
	private Integer synFFlowId;	//当前承认id，用于承认时判断承认流程	1
	private Flow synFlow;		//当前承认流程
	private String eventName;	//件名	1
	public Integer getFormId() {
		return formId;
	}
	public void setFormId(Integer formId) {
		this.formId = formId;
	}
	public String getFormNo() {
		return formNo;
	}
	public void setFormNo(String formNo) {
		this.formNo = formNo;
	}
	public String getTitle() {
		return title;
	}
	
	public Flow getHeadFlow() {
		return headFlow;
	}
	public void setHeadFlow(Flow headFlow) {
		this.headFlow = headFlow;
	}
	public Integer getFlowFormId() {
		return flowFormId;
	}
	
	public String getFieldName() {
		return fieldName;
	}
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public void setFlowFormId(Integer flowFormId) {
		this.flowFormId = flowFormId;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public Integer getFormTypeId() {
		return formTypeId;
	}
	public void setFormTypeId(Integer formTypeId) {
		this.formTypeId = formTypeId;
	}
	public Integer getItemTypeId() {
		return itemTypeId;
	}
	public void setItemTypeId(Integer itemTypeId) {
		this.itemTypeId = itemTypeId;
	}
	public Organization getOrg() {
		return org;
	}
	public void setOrg(Organization org) {
		this.org = org;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
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
	public List<Section> getSections() {
		return sections;
	}
	public void setSections(List<Section> sections) {
		this.sections = sections;
	}
	public List<Field> getFields() {
		return fields;
	}
	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getFormTypeName() {
		return formTypeName;
	}
	public void setFormTypeName(String formTypeName) {
		this.formTypeName = formTypeName;
	}
	public List<Flow> getFlows() {
		return flows;
	}
	public void setFlows(List<Flow> flows) {
		this.flows = flows;
	}
	public List<Flow> getFlowInsts() {
		return flowInsts;
	}
	public void setFlowInsts(List<Flow> flowInsts) {
		this.flowInsts = flowInsts;
	}
	public ItemType getItemType() {
		return itemType;
	}
	public void setItemType(ItemType itemType) {
		this.itemType = itemType;
	}
	public String getItemTypeName() {
		return itemTypeName;
	}
	public void setItemTypeName(String itemTypeName) {
		this.itemTypeName = itemTypeName;
	}
	public String getItemTypeCode() {
		return itemTypeCode;
	}
	public void setItemTypeCode(String itemTypeCode) {
		this.itemTypeCode = itemTypeCode;
	}
	public Integer getParentId() {
		return parentId;
	}
	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public List<Form> getChildren() {
		return children;
	}
	public void setChildren(List<Form> children) {
		this.children = children;
	}
	public Integer getSynUserId() {
		return synUserId;
	}
	public void setSynUserId(Integer synUserId) {
		this.synUserId = synUserId;
	}
	public Integer getSynFFlowId() {
		return synFFlowId;
	}
	public void setSynFFlowId(Integer synFFlowId) {
		this.synFFlowId = synFFlowId;
	}
	public Flow getSynFlow() {
		return synFlow;
	}
	public void setSynFlow(Flow synFlow) {
		this.synFlow = synFlow;
	}
	public String getAgId() {
		return agId;
	}
	public void setAgId(String agId) {
		this.agId = agId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getStatusValue() {
		return statusValue;
	}
	public void setStatusValue(String statusValue) {
		this.statusValue = statusValue;
	}
}
