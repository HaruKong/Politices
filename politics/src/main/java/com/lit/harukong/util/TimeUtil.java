package com.lit.harukong.util;

import java.text.SimpleDateFormat;

/**
 * Created by haru on 2016/3/14.
 */
public class TimeUtil {

    public static TimeUtil instance;
    private long time = System.currentTimeMillis();


    public static TimeUtil getFormat() {
        if (instance == null) {
            instance = new TimeUtil();
        }
        return instance;
    }

    public String systemDateTime() {
        SimpleDateFormat myFmtDate = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        return myFmtDate.format(time);
    }

}
