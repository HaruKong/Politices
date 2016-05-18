package com.lit.harukong.util;

import com.lit.harukong.AppContext;
import com.lit.harukong.bean.UserBean;

/**
 * Created by haru on 2016/5/18.
 */
public class JudgeUsers {
    public static String user(String users) {

        StringBuffer sb = new StringBuffer();
        for (UserBean ub : AppContext.getUser()) {
            String arr[] = users.split(",");
            int len = arr.length;
            for (int i = 0; i < len; i++) {
                int id = Integer.valueOf(arr[0]);
                if (ub.getUserID() == id) {
                    sb.append("【").append(JudgeUserJob.jobBranch(ub.getUserJob())).
                            append("】").append(ub.getTel()).append("-").
                            append(ub.getName()).append("\n");
                }
            }
        }
        while (sb.charAt(sb.length() - 1) == '\n') {
            sb.deleteCharAt(sb.length() - 1);
        }
        return sb.toString();
    }
}
