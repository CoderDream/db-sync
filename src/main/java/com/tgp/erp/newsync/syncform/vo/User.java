package com.tgp.erp.newsync.syncform.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class User {
    private Integer userId;     // 用户id
    private String crewId;     // 用户名
    private String pwd;        // 密码
    private Integer state;    // 状态
    private String stateValue; //状态值
    private Integer entryId;    //输入者id
    private String entryTime;  //添加时间
    private String updateId;  //更新者id
    //	@JsonFormat(pattern = "yyyy-MM-dd",timezone="GMT+8")//要根据所在国家选择时区，中国是东8区，日本是东9区
//	private Date updateTime;  //更新时间
    private String updateTime;    //更新时间
    private String lastName;    // 姓
    private String firstName;   // 名
    private String name;        // 姓名
    private String nameKa;        // 假姓名
    private String lastNameKa;  // 姓片假名
    private String firstNameKa; // 名片假名
    private String position;    //职位
    private String sex;         //性別
    private Date birthdate;     //出生年月
    private String zipCode;     //邮编
    private String addr1;       //地址1
    private String addr2;       //地址2
    private String tel;         //自家电话
    private String mob;         //个人手机
    private String email;       //PC邮箱
    private String email2;      //手机邮箱
    private Integer logId;        //登陆日志id
    /*private String orgId;*/
    private Integer orgId;        //主部门信息
    private String orgName;        //主部门名字
    private Organization mainOrg;    //主部门
    private List<Organization> orgs = new ArrayList<Organization>();   //组织机构
    private Integer keyPost;
    private String bankName;
    private String branchName;
    private Integer accountType;
    private String accountName;
    private Integer bankCode;
    private Integer brankCode;
    private String accountNo;
    private String paymentTerm;
    private Integer paymentType;
    private String operation;  //对用户进行的操作
    private Integer userOrgCode;
    private String userOrgName;
    private String selCrewId;

    public User(Integer userId, Integer orgId, String name) {
        this.userId = userId;
        this.orgId = orgId;
        this.name = name;
    }

    public User(Integer userId) {
        this.userId = userId;
    }


    public Integer getKeyPost() {
        return keyPost;
    }

    public void setKeyPost(Integer keyPost) {
        this.keyPost = keyPost;
    }

    public String getSelCrewId() {
        return selCrewId;
    }

    public void setSelCrewId(String selCrewId) {
        this.selCrewId = selCrewId;
    }

    public String getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(String entryTime) {
        this.entryTime = entryTime;
    }

    public String getUserOrgName() {
        return userOrgName;
    }

    public void setUserOrgName(String userOrgName) {
        this.userOrgName = userOrgName;
    }

    public Integer getUserOrgCode() {
        return userOrgCode;
    }

    public void setUserOrgCode(Integer userOrgCode) {
        this.userOrgCode = userOrgCode;
    }


    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }


    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastNameKa() {
        return lastNameKa;
    }

    public void setLastNameKa(String lastNameKa) {
        this.lastNameKa = lastNameKa;
    }

    public String getFirstNameKa() {
        return firstNameKa;
    }

    public void setFirstNameKa(String firstNameKa) {
        this.firstNameKa = firstNameKa;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getAddr1() {
        return addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    public String getAddr2() {
        return addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getMob() {
        return mob;
    }

    public void setMob(String mob) {
        this.mob = mob;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail2() {
        return email2;
    }

    public void setEmail2(String email2) {
        this.email2 = email2;
    }


    public List<Organization> getOrgs() {
        return orgs;
    }

    public void setOrgs(List<Organization> orgs) {
        this.orgs = orgs;
    }

    public Integer getLogId() {
        return logId;
    }

    public void setLogId(Integer logId) {
        this.logId = logId;
    }

    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    public Integer getAccountType() {
        return accountType;
    }

    public void setAccountType(Integer accountType) {
        this.accountType = accountType;
    }

    public Integer getEntryId() {
        return entryId;
    }

    public void setEntryId(Integer entryId) {
        this.entryId = entryId;
    }

    public String getCrewId() {
        return crewId;
    }

    public void setCrewId(String crewId) {
        this.crewId = crewId;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Integer getBankCode() {
        return bankCode;
    }

    public void setBankCode(Integer bankCode) {
        this.bankCode = bankCode;
    }

    public Integer getBrankCode() {
        return brankCode;
    }

    public void setBrankCode(Integer brankCode) {
        this.brankCode = brankCode;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getPaymentTerm() {
        return paymentTerm;
    }

    public void setPaymentTerm(String paymentTerm) {
        this.paymentTerm = paymentTerm;
    }

    public Integer getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(Integer paymentType) {
        this.paymentType = paymentType;
    }

    public String getStateValue() {
        return stateValue;
    }

    public void setStateValue(String stateValue) {
        this.stateValue = stateValue;
    }

    public String getUpdateId() {
        return updateId;
    }

    public void setUpdateId(String updateId) {
        this.updateId = updateId;
    }

//	public Date getUpdateTime() {
//		return updateTime;
//	}
//
//	public void setUpdateTime(Date updateTime) {
//		this.updateTime = updateTime;
//	}

    public String getOrgName() {
        return orgName;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public Organization getMainOrg() {
        return mainOrg;
    }

    public void setMainOrg(Organization mainOrg) {
        this.mainOrg = mainOrg;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNameKa() {
        return nameKa;
    }

    public void setNameKa(String nameKa) {
        this.nameKa = nameKa;
    }


}
