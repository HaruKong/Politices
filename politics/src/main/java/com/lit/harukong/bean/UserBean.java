package com.lit.harukong.bean;

import java.sql.Timestamp;

public class UserBean {
    private int userID;// 唯一标示一个用户，无实际意义
    private String loginID;// 登录名
    private String pwd;// 密码
    private int groupCode;// 所属用户组
    private String isInUse;// 是否可用。 0：否 1：是
    private String name;// 姓名
    private String sortCode;// 排序代码
    private String idNum;// 身份证号
    private String tel;// 电话
    private String sex;// 性别 0：女 ： 1：男
    private Timestamp birth;// 出生日期
    private int branchID;// 所属部门ID
    private String userJob;// 用户岗位 ,对应数据字典 UserJob
    private String position;// 用户职务
    private boolean isChecked;

    public UserBean() {
        super();
    }

    public UserBean(String loginID, String pwd) {
        super();
        this.loginID = loginID;
        this.pwd = pwd;

    }

    public UserBean(int userID, String loginID, int groupCode, String isInUse,
                    String name, String tel, String sex, int branchID,
                    String userJob, String position) {
        super();
        this.userID = userID;
        this.loginID = loginID;
        this.groupCode = groupCode;
        this.isInUse = isInUse;
        this.name = name;
        this.tel = tel;
        this.sex = sex;
        this.branchID = branchID;
        this.userJob = userJob;
        this.position = position;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getLoginID() {
        return loginID;
    }

    public void setLoginID(String loginID) {
        this.loginID = loginID;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public int getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(int groupCode) {
        this.groupCode = groupCode;
    }

    public String getIsInUse() {
        return isInUse;
    }

    public void setIsInUse(String isInUse) {
        this.isInUse = isInUse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSortCode() {
        return sortCode;
    }

    public void setSortCode(String sortCode) {
        this.sortCode = sortCode;
    }

    public String getIdNum() {
        return idNum;
    }

    public void setIdNum(String idNum) {
        this.idNum = idNum;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Timestamp getBirth() {
        return birth;
    }

    public void setBirth(Timestamp birth) {
        this.birth = birth;
    }

    public int getBranchID() {
        return branchID;
    }

    public void setBranchID(int branchID) {
        this.branchID = branchID;
    }

    public String getUserJob() {
        return userJob;
    }

    public void setUserJob(String userJob) {
        this.userJob = userJob;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean isChecked) {
        this.isChecked = isChecked;
    }

    public void changeChecked() {
        isChecked = !isChecked;
    }
}
