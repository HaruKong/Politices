package com.lit.harukong.util;

/**
 * Created by haru on 2016/5/10.
 */
public class JudgePoliticsType {
    public static String politicsType(int type) {
        String politics_type = "";
        switch (type) {
            case 77:
                politics_type = "咨询";
                break;
            case 78:
                politics_type = "投诉";
                break;
            case 79:
                politics_type = "求助";
                break;
            case 92:
                politics_type = "举报";
                break;
            case 93:
                politics_type = "建议";
                break;
            case 94:
                politics_type = "表扬";
                break;
            case 95:
                politics_type = "其他";
                break;
            default:
                politics_type = "";
                break;
        }
        return politics_type;
    }
}
