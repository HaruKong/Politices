package com.lit.harukong.util;

import com.lit.harukong.AppContext;
import com.lit.harukong.bean.TB_GroupBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haru on 2016/5/18.
 */
public class JudgeBranch {
    public static String branch(int bid) {
        String branch = "";
        for (TB_GroupBean tbg : AppContext.getBranch()) {
            if (tbg.getId() == bid) {
                branch = tbg.getName();
            }
        }
        return branch;
    }
}