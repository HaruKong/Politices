package com.lit.harukong.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lit.harukong.AppContext;
import com.lit.harukong.R;
import com.lit.harukong.adapter.MyUserGridAdapter;
import com.lit.harukong.adapter.MyUserListAdapter;
import com.lit.harukong.bean.TB_ChildBean;
import com.lit.harukong.bean.TB_GroupBean;
import com.lit.harukong.bean.UserBean;
import com.lit.harukong.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haru on 2016/3/21.
 */
public class BranchUserAty extends Activity {


    private GridView gridView;
    private TextView btnTv;
    private ExpandableListView expandableListView;
    private MyUserListAdapter listAdapter;
    private MyUserGridAdapter gridAdapter;

    private List<Object> branchList = new ArrayList<>();
    private List<Object> group = new ArrayList<>();
    private List<List<UserBean>> child = new ArrayList<>();
    private List<UserBean> gridUserList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_branch_user);
        setUpViews();
        setUpListeners();
        initData();
        listAdapter = new MyUserListAdapter(BranchUserAty.this, group, child);
        expandableListView.setAdapter(listAdapter);
    }


    public void setUpViews() {
        gridView = (GridView) findViewById(R.id.user_GridView);
        btnTv = (TextView) findViewById(R.id.user_btnTv);
        expandableListView = (ExpandableListView) findViewById(R.id.user_ExpandListView);
    }

    public void setUpListeners() {
        btnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                UserBean userBean = child.get(groupPosition).get(childPosition);
                userBean.changeChecked();
                listAdapter.notifyDataSetChanged();
                reFlashGridView();
                return false;
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                UserBean ub = gridUserList.get(position);
                ub.changeChecked();
                listAdapter.notifyDataSetChanged();
                reFlashGridView();
            }
        });
    }

    public void reFlashGridView() {
        gridUserList.clear();
        for (int i = 0; i < group.size(); i++) {
            for (UserBean ub : child.get(i)) {
                if (ub.isChecked())
                    gridUserList.add(ub);
            }
        }
        gridAdapter.notifyDataSetChanged();
    }

    public void initData() {
        Intent intent = getIntent();
        List<TB_GroupBean> listG = (List<TB_GroupBean>) intent.getSerializableExtra("listG");
        List<TB_ChildBean> listC = (List<TB_ChildBean>) intent.getSerializableExtra("listC");
        if (listG != null) {
            branchList.addAll(listG);
        }
        if (listC != null) {
            branchList.addAll(listC);
        }
        if (branchList != null) {
            btnTv.setVisibility(View.VISIBLE);
            for (Object obj : branchList) {
                addInfo(obj, AppContext.userList);
            }
        }

        gridAdapter = new MyUserGridAdapter(this, gridUserList);
        gridView.setAdapter(gridAdapter);
        gridView.setNumColumns(2);
        reFlashGridView();
    }

    /**
     * 添加数据信息
     *
     * @param g
     * @param c
     */

    private void addInfo(Object g, List<UserBean> c) {
        List<UserBean> list = new ArrayList<>();
        group.add(g);
        if (g instanceof TB_GroupBean) {
            for (UserBean user : c) {
                if (user.getBranchID() == ((TB_GroupBean) g).getId()) {
                    list.add(user);
                }
            }
        } else if (g instanceof TB_ChildBean) {
            for (UserBean user : c) {
                if (user.getBranchID() == ((TB_ChildBean) g).getId()) {
                    list.add(user);
                }
            }
        }
        child.add(list);

    }
}
