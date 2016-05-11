package com.lit.harukong.util;

/**
 * Created by haru on 2016/5/10.
 */
public class JudgeIsLock {
    public static String IsLock(String lock) {
        String isLock = "";
        if (null != lock)
            switch (lock) {
                case "0":
                    isLock = "否";
                    break;
                case "1":
                    isLock = "锁定";
                    break;
                default:
                    isLock = "";
                    break;
            }
        return isLock;
    }
}
