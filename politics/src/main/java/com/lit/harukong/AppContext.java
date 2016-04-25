package com.lit.harukong;

import android.app.Application;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
    public static final Gson gson = new Gson();
    public static final KJHttp kjh = new KJHttp();
    public static final HttpParams kjp = new HttpParams();
    public static final String url = "http://www.lit402.top:8080/PoliticsService/";
    public static final int POLITICS_TYPE = 0;//问政类型
    public static final int BRANCH = 1;//接收部门
    public static final int BRANCH_USER = 2;//部门接收人


    public static List<UserBean> userList;


    /**
     * 单一实例
     */
    public static List<UserBean> getUser() {
        if (userList == null) {
            userList = new ArrayList<>();
        }
        return userList;
    }


    @Override
    public void onCreate() {
        super.onCreate();
        userInfo();

    }

    protected void userInfo() {
        String url1 = url + "ApplicationServlet";
        kjp.put("param0", "getUser");
        kjh.post(url1, kjp, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                userList = gson.fromJson(t, new TypeToken<List<UserBean>>() {
                }.getType());
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                ToastUtil.showToast(getApplicationContext(), "服务器连接失败");
            }
        });
    }


}
