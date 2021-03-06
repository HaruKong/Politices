package com.lit.harukong;

import android.app.Application;
import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.lit.harukong.bean.TB_GroupBean;
import com.lit.harukong.bean.UserBean;
import com.lit.harukong.util.ToastUtil;

import org.kymjs.kjframe.KJHttp;
import org.kymjs.kjframe.http.HttpCallBack;
import org.kymjs.kjframe.http.HttpParams;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haru on 2016/3/10.
 */
public class AppContext extends Application {
    static Context _context;
    public static final KJHttp kjh = new KJHttp();
    public static final HttpParams kjp = new HttpParams();
    public static final String url = "http://www.lit402.top:8080/PoliticsService/";
    public static final int POLITICS_TYPE = 0;//问政类型
    public static final int BRANCH = 1;//接收部门
    public static final int BRANCH_USER = 2;//部门接收人

    public static List<UserBean> userList;
    public static List<TB_GroupBean> branchList;

    private static AppContext instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        userInfo();
        branchInfo();
    }

    public static synchronized AppContext context() {
        return (AppContext) _context;
    }

    /**
     * 单一实例
     */
    public static List<UserBean> getUser() {
        if (userList == null) {
            userList = new ArrayList<>();
        }
        return userList;
    }

    /**
     * 单一实例
     */
    public static List<TB_GroupBean> getBranch() {
        if (branchList == null) {
            branchList = new ArrayList<>();
        }
        return branchList;
    }


    /**
     * 获得当前app运行的AppContext
     *
     * @return
     */
    public static AppContext getInstance() {
        return instance;
    }

    protected void userInfo() {
        String url1 = url + "ApplicationUserServlet";
        kjp.put("param0_user", "getUser");
        kjh.post(url1, kjp, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                userList = JSON.parseArray(t, UserBean.class);

            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                ToastUtil.showToast(getApplicationContext(), "服务器异常");
            }
        });
    }

    protected void branchInfo() {
        String url1 = url + "ApplicationBranchServlet";
        kjp.put("param0_branch", "getT_Branch");
        kjh.post(url1, kjp, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                branchList = JSON.parseArray(t, TB_GroupBean.class);
            }
        });
    }


}
