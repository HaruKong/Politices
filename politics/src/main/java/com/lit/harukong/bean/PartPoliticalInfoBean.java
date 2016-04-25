package com.lit.harukong.bean;


public class PartPoliticalInfoBean {
    private String title;// 标题
    private String announceTime;// 发表时间
    private String webSiteName;// 网站名
    private String announceUser;// 发表人
    private String content;// 内容（帖子正文）
    private String url;// 访问链接

    public PartPoliticalInfoBean() {
        super();
    }

    public PartPoliticalInfoBean(String title, String announceTime, String webSiteName, String announceUser, String content) {
        this.title = title;
        this.announceTime = announceTime;
        this.webSiteName = webSiteName;
        this.announceUser = announceUser;
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAnnounceTime() {
        return announceTime;
    }

    public void setAnnounceTime(String announceTime) {
        this.announceTime = announceTime;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }


}
