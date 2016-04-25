package com.lit.harukong.demo;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lit.harukong.AppContext;
import com.lit.harukong.R;
import com.lit.harukong.bean.TB_GroupBean;
import com.lit.harukong.util.ToastUtil;

import org.kymjs.kjframe.http.HttpCallBack;

public class BranchAtyDemo extends Activity {

    private List<TB_GroupBean> t_branchList;

    private TextView btnTv;
    private ExpandableListView listView;
    private ProgressBar progressBar;
    private List<TB_GroupBean> group;
    private List<List<TB_GroupBean>> child;
    private MyBranchAdapterDemo adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_branch);

        progressBar = (ProgressBar) findViewById(R.id.branch_ProgressBar_branch);
        btnTv = (TextView) findViewById(R.id.branch_BtnTv);
        listView = (ExpandableListView) findViewById(R.id.branch_ExpandListView);
        /**
         * 初始化数据
         */
        getBranchInfo();
        btnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showToast(BranchAtyDemo.this, "功能暂时未实现！");
            }
        });
    }

    protected void getBranchInfo() {
        String url = AppContext.url + "ApplicationServlet";
        AppContext.kjp.put("param0", "getT_Branch");
        AppContext.kjh.post(url, AppContext.kjp, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                if (t_branchList == null) {
                    t_branchList = new ArrayList<>();
                    t_branchList = AppContext.gson.fromJson(t, new TypeToken<List<TB_GroupBean>>() {
                    }.getType());
                    //父级部门
                    group = new ArrayList<>();
                    child = new ArrayList<>();
                    for (TB_GroupBean tb : t_branchList) {
                        if (tb.getPid() == 131) {
                            addInfo(tb, t_branchList);
                        }
                    }
                    adapter = new MyBranchAdapterDemo(BranchAtyDemo.this, group, child);
                    listView.setAdapter(adapter);
                    listView.setGroupIndicator(null);
                }
            }

            @Override
            public void onFinish() {
                super.onFinish();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }

    /**
     * 添加数据信息
     *
     * @param g
     * @param c
     */

    private void addInfo(TB_GroupBean g, List<TB_GroupBean> c) {
        group.add(g);
        List<TB_GroupBean> list = new ArrayList<>();

        for (TB_GroupBean t : c) {
            if (t.getPid() == g.getId()) {
                list.add(t);
            }
        }
        child.add(list);
    }
}
