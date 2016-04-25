package com.lit.harukong.util;

/**
 * Created by haru on 2016/4/15.
 */
public class JudgeUserJob {
    public static String jobBranch(String job) {
        switch (job) {
            case "01":
                job = "新闻发言人";
                break;
            case "02":
                job = "新闻发言人助理";
                break;
            case "03":
                job = "网络发言人";
                break;
            case "04":
                job = "网络发言人助理";
                break;
            case "05":
                job = "新闻（网络发言人）";
                break;
            case "06":
                job = "新闻（网络发言人）助理";
                break;
            case "07":
                job = "其它";
                break;
            case "08":
                job = "发言人";
                break;
            case "09":
                job = "联络员";
                break;
            case "10":
                job = "值班人";
                break;
        }
        return job;
    }
}
