package com.lit.harukong.util;

/**
 * Created by haru on 2016/5/10.
 */
public class JudgeState {
    public static String state(int states) {
        String state = null;

        switch (states) {
            case 0:
                state = "未回应";
                break;
            case 1:
                state = "已关注";
                break;
            case 2:
                state = "已回复";
                break;
            default:
                state = "";
                break;
        }
        return state;
    }


}
