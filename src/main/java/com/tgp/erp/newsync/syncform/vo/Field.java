package com.tgp.erp.newsync.syncform.vo;

import java.util.Date;
import java.util.List;

public class Field {
	private Integer fieldId; // 主键ID
	private String fieldName; // 自定义字段名
	private Integer dataType; // 数据类型 10.number 11.int 12.decimal 20. string 30.
														// boolean 40. date 41. datetime
	private String dataTypeValue; // 数据类型值
	private String valueExpression; // 验证表达式（正则表达式）
	private String defaultValue; // 默认値
	private String remark; // 备注描述（用于显示提示）
	private Integer editorId; // 编辑框类型 1. inputText 2. select 3. checkBox 4. Radio
														// 5. textArea
	private String editorType; // 编辑框类型值
	private Integer state; // 状态 (1.正常;2.禁用; 3.删除)
	private Integer sortId; // 顺序
	private Date updateTime; // 更新时间
	private Integer updateId; // 更新者ID
	private Date entryTime; // 添加时间
	private Integer entryId; // 添加者ID
	private Integer repeatable; // 是否可重复
	private String repeatablev; // 是否可重复值
	private Integer sfId; // syn_section_field id用于区分可重复字段
	private List<Option> options;
	private Integer isRequired; // 判断是否是必填项
	private Integer optionId; // select，radio，checkbox选中选项id
	private String fieldValue; // textarea,input输入选中id
	private String fieldText; // 表单实例radio-input类型使用，存放input内容
	private Integer formId; // 所属表单id
	private Integer sectionId; // field实例对应的段落id
	private Integer groupId; // 组id
	private Integer fileId; // 文件ID
	private String groupName; // 组名字
	private Integer isAddible; // 是否可添加
	private List<Field> children; // 子fieldId(group)使用
	private Integer formGroupId; // 表单组id
	private Integer groupType; // 组类型（用于表格展示使用）

	/**
	 * 针对组内有类型的field构造方法
	 * 
	 * @param fieldId
	 * @param sectionId
	 * @param sortId
	 * @param fieldValue
	 * @param editorType
	 * @param groupId
	 */
	public Field(Integer fieldId, Integer sectionId, Integer sortId,
			String fieldValue, String editorType, Integer groupId) {
		super();
		this.fieldId = fieldId;
		this.sortId = sortId;
		this.fieldValue = fieldValue;
		this.sectionId = sectionId;
		this.groupId = groupId;
		this.editorType = editorType;
	}

	/**
	 * 普通field构造方法
	 * 
	 * @param fieldId
	 * @param sectionId
	 * @param sortId
	 * @param fieldValue
	 */
	public Field(Integer fieldId, Integer sectionId, Integer sortId,
			String fieldValue) {
		super();
		this.fieldId = fieldId;
		this.sortId = sortId;
		this.fieldValue = fieldValue;
		this.sectionId = sectionId;
	}

	/**
	 * 非组内有类型field构造方法
	 * 
	 * @return
	 */
	public Field(Integer fieldId, Integer sectionId, Integer sortId,
			String fieldValue, String editorType) {
		super();
		this.fieldId = fieldId;
		this.sortId = sortId;
		this.sectionId = sectionId;
		this.fieldValue = fieldValue;
		this.editorType = editorType;
	}

	/**
	 * 非组内选项类型
	 * 
	 * @param fieldId
	 * @param sectionId
	 * @param sortId
	 * @param options
	 */
	public Field(Integer fieldId, Integer sectionId, Integer sortId,
			List<Option> options) {
		super();
		this.fieldId = fieldId;
		this.sortId = sortId;
		this.sectionId = sectionId;
		this.options = options;
	}

	/**
	 * 组添加
	 * 
	 * @param groupId
	 * @param sectionId
	 * @param sortId
	 */
	public Field(Integer groupId, Integer sectionId, Integer sortId) {
		this.groupId = groupId;
		this.sectionId = sectionId;
		this.sortId = sortId;
	}

	public Integer getFileId() {
		return fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getFieldId() {
		return fieldId;
	}

	public void setFieldId(Integer fieldId) {
		this.fieldId = fieldId;
	}

	public String getFieldName() {
		return fieldName;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public Integer getDataType() {
		return dataType;
	}

	public void setDataType(Integer dataType) {
		this.dataType = dataType;
	}

	public String getValueExpression() {
		return valueExpression;
	}

	public void setValueExpression(String valueExpression) {
		this.valueExpression = valueExpression;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public Integer getEditorId() {
		return editorId;
	}

	public void setEditorId(Integer editorId) {
		this.editorId = editorId;
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

	public List<Option> getOptions() {
		return options;
	}

	public void setOptions(List<Option> options) {
		this.options = options;
	}

	public String getEditorType() {
		return editorType;
	}

	public void setEditorType(String editorType) {
		this.editorType = editorType;
	}

	public String getDataTypeValue() {
		return dataTypeValue;
	}

	public void setDataTypeValue(String dataTypeValue) {
		this.dataTypeValue = dataTypeValue;
	}

	public Integer getRepeatable() {
		return repeatable;
	}

	public void setRepeatable(Integer repeatable) {
		this.repeatable = repeatable;
	}

	public Integer getSfId() {
		return sfId;
	}

	public void setSfId(Integer sfId) {
		this.sfId = sfId;
	}

	public String getRepeatablev() {
		return repeatablev;
	}

	public void setRepeatablev(String repeatablev) {
		this.repeatablev = repeatablev;
	}

	public Integer getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(Integer isRequired) {
		this.isRequired = isRequired;
	}

	public Integer getOptionId() {
		return optionId;
	}

	public void setOptionId(Integer optionId) {
		this.optionId = optionId;
	}

	public String getFieldValue() {
		return fieldValue;
	}

	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}

	public Integer getFormId() {
		return formId;
	}

	public void setFormId(Integer formId) {
		this.formId = formId;
	}

	public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public List<Field> getChildren() {
		return children;
	}

	public void setChildren(List<Field> children) {
		this.children = children;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Integer getIsAddible() {
		return isAddible;
	}

	public void setIsAddible(Integer isAddible) {
		this.isAddible = isAddible;
	}

	public Integer getFormGroupId() {
		return formGroupId;
	}

	public void setFormGroupId(Integer formGroupId) {
		this.formGroupId = formGroupId;
	}

	public String getFieldText() {
		return fieldText;
	}

	public void setFieldText(String fieldText) {
		this.fieldText = fieldText;
	}

	public Integer getGroupType() {
		return groupType;
	}

	public void setGroupType(Integer groupType) {
		this.groupType = groupType;
	}
}
