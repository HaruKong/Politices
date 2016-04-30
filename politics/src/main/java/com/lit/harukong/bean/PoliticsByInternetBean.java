package com.lit.harukong.bean;

import java.sql.Timestamp;

/**
 * Created by haru on 2016/4/29.
 */
public class PoliticsByInternetBean {
    private String title;
    private int politicsType;
    private Timestamp announceTime;
    private Timestamp findTime;
    private String url;
    private String webSiteNmae;
    private String announceUser;
    private String jbRen;
    private String jbRenTel;
    private String content;
    private int add_uid;
    private Timestamp add_time;

    public PoliticsByInternetBean() {
        super();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getWebSiteNmae() {
        return webSiteNmae;
    }

    public void setWebSiteNmae(String webSiteNmae) {
        this.webSiteNmae = webSiteNmae;
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
}
