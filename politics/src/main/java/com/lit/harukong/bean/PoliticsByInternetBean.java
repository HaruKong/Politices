package com.lit.harukong.bean;

import java.sql.Timestamp;

/**
 * Created by haru on 2016/4/29.
 */
public class PoliticsByInternetBean {
    /**
     * PoliticsByInternet表
     */
    private String title;// 标题
    private String url;// 访问链接
    private int politicsType;// 问政类型(分类)
    private Timestamp announceTime;// 发表时间
    private Timestamp findTime;// 监测时间
    private String webSiteName;// 网站名,点击之后需要
    private String announceUser;// 发表人
    private String jbRen;// 交办人
    private String jbRenTel;// 交办人联系方式
    private String content;// 交办说明（帖子正文）,点击之后需要
    private int add_uid;// 发布人ID
    private Timestamp add_time;// 录入时间

    /**
     * R_PoliticsBranch表
     */
    private int pid;// 问政ID（来自PoliticsByInternet表）
    private int bid;// 部门ID(被提醒的部门，此处部门几个，记录就有几条)
    private String users;// 被提醒的用户的ID,以逗号隔开。例：1,15
    private int state;// 状态（0：初始状态，1：已关注，2：已回复）
    private int delayLength;// 延期天数
    private Timestamp base_time;// 交领办时间
    private Timestamp s1_time;// 关注时间 时间
    private int s1_in_time;// 关注及时
    private Timestamp s2_time;// 回复时间 时间
    private int s2_in_time;// 回复及时
    private String s1_content;// 知晓性回应内容
    private String s2_content;// 正式回复内容
    private String is_lock;// 是否锁定


    public PoliticsByInternetBean() {
        super();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getPoliticsType() {
        return politicsType;
    }

    public void setPoliticsType(int politicsType) {
        this.politicsType = politicsType;
    }

    public Timestamp getAnnounceTime() {
        return announceTime;
    }

    public void setAnnounceTime(Timestamp announceTime) {
        this.announceTime = announceTime;
    }

    public Timestamp getFindTime() {
        return findTime;
    }

    public void setFindTime(Timestamp findTime) {
        this.findTime = findTime;
    }

    public String getWebSiteName() {
        return webSiteName;
    }

    public void setWebSiteName(String webSiteName) {
        this.webSiteName = webSiteName;
    }

    public String getAnnounceUser() {
        return announceUser;
    }

    public void setAnnounceUser(String announceUser) {
        this.announceUser = announceUser;
    }

    public String getJbRen() {
        return jbRen;
    }

    public void setJbRen(String jbRen) {
        this.jbRen = jbRen;
    }

    public String getJbRenTel() {
        return jbRenTel;
    }

    public void setJbRenTel(String jbRenTel) {
        this.jbRenTel = jbRenTel;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getAdd_uid() {
        return add_uid;
    }

    public void setAdd_uid(int add_uid) {
        this.add_uid = add_uid;
    }

    public Timestamp getAdd_time() {
        return add_time;
    }

    public void setAdd_time(Timestamp add_time) {
        this.add_time = add_time;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    public String getUsers() {
        return users;
    }

    public void setUsers(String users) {
        this.users = users;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public int getDelayLength() {
        return delayLength;
    }

    public void setDelayLength(int delayLength) {
        this.delayLength = delayLength;
    }

    public Timestamp getBase_time() {
        return base_time;
    }

    public void setBase_time(Timestamp base_time) {
        this.base_time = base_time;
    }

    public Timestamp getS1_time() {
        return s1_time;
    }

    public void setS1_time(Timestamp s1_time) {
        this.s1_time = s1_time;
    }

    public int getS1_in_time() {
        return s1_in_time;
    }

    public void setS1_in_time(int s1_in_time) {
        this.s1_in_time = s1_in_time;
    }

    public Timestamp getS2_time() {
        return s2_time;
    }

    public void setS2_time(Timestamp s2_time) {
        this.s2_time = s2_time;
    }

    public int getS2_in_time() {
        return s2_in_time;
    }

    public void setS2_in_time(int s2_in_time) {
        this.s2_in_time = s2_in_time;
    }

    public String getS1_content() {
        return s1_content;
    }

    public void setS1_content(String s1_content) {
        this.s1_content = s1_content;
    }

    public String getS2_content() {
        return s2_content;
    }

    public void setS2_content(String s2_content) {
        this.s2_content = s2_content;
    }

    public String getIs_lock() {
        return is_lock;
    }

    public void setIs_lock(String is_lock) {
        this.is_lock = is_lock;
    }
}
