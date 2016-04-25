package com.lit.harukong.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.reflect.TypeToken;
import com.lit.harukong.AppContext;
import com.lit.harukong.R;
import com.lit.harukong.adapter.MyBranchGridAdapter;
import com.lit.harukong.adapter.MyBranchListAdapter;
import com.lit.harukong.bean.TB_ChildBean;
import com.lit.harukong.bean.TB_GroupBean;
import com.lit.harukong.util.ToastUtil;

import org.kymjs.kjframe.http.HttpCallBack;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by haru on 2016/4/12.
 */
public class BranchAty extends AppCompatActivity {


    private Intent intent = new Intent();
    private Bundle bundle = new Bundle();
    private List<TB_GroupBean> t_branchList;
    private List<TB_GroupBean> listGroup;
    private List<List<TB_GroupBean>> listChild;

    private GridView gridView;
    private TextView btnTv;
    private ExpandableListView expandableListView;
    private ProgressBar progressBar;
    private List<TB_GroupBean> groupList = new ArrayList<>();
    private MyBranchListAdapter listAdapter;
    private MyBranchGridAdapter gridAdapter;
    private List<Object> gridList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_branch);


        setUpViews();
        setUpListeners();
        getBranchInfo();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        bundle.putString("return", "");
        intent.putExtras(bundle);
        setResult(AppContext.BRANCH, intent);
        return super.onKeyDown(keyCode, event);
    }

    private void setUpViews() {
        gridView = (GridView) findViewById(R.id.branch_GridView);
        btnTv = (TextView) findViewById(R.id.branch_BtnTv);
        expandableListView = (ExpandableListView) findViewById(R.id.branch_ExpandListView);
        progressBar = (ProgressBar) findViewById(R.id.branch_ProgressBar_branch);
    }

    private void setUpListeners() {
        btnTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bundle.putString("return", "1");
                List<TB_GroupBean> listForResultG = new ArrayList<>();
                List<TB_ChildBean> listForResultC = new ArrayList<>();
                int size = gridAdapter.list.size();
                if (size > 0) {
                    for (int i = 0; i < size; i++) {
                        Object obj = gridAdapter.getItem(i);
                        if (obj instanceof TB_GroupBean) {
                            listForResultG.add((TB_GroupBean) obj);
                        } else if (obj instanceof TB_ChildBean) {
                            listForResultC.add((TB_ChildBean) obj);
                        }
                    }
                    bundle.putSerializable("returnG", (Serializable) listForResultG);
                    bundle.putSerializable("returnC", (Serializable) listForResultC);
                    intent.putExtras(bundle);
                    setResult(AppContext.BRANCH, intent);
                    BranchAty.this.finish();
                } else {
                    ToastUtil.showToast(getApplicationContext(), "至少要选一个部门！按返回键退出！");
                }
            }
        });
        expandableListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                TB_ChildBean child = groupList.get(groupPosition).getChildList().get(childPosition);
                child.changeChecked();
                listAdapter.notifyDataSetChanged();
                reFlashGridView();
                return false;
            }
        });
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                Object obj = gridList.get(position);
                if (obj instanceof TB_GroupBean) {
                    ((TB_GroupBean) obj).changeChecked();
                } else if (obj instanceof TB_ChildBean) {
                    ((TB_ChildBean) obj).changeChecked();
                }
                listAdapter.notifyDataSetChanged();
                reFlashGridView();
            }
        });
    }


    public void reFlashGridView() {
        gridList.clear();
        for (TB_GroupBean group : groupList) {
            if (group.isChecked()) {
                gridList.add(group);
            }
            for (TB_ChildBean child : group.getChildList()) {
                if (child.isChecked()) {
                    gridList.add(child);
                }
            }
        }
        gridAdapter.notifyDataSetChanged();
    }

    private void initData() {
        for (int i = 0; i < listGroup.size(); i++) {
            TB_GroupBean group = new TB_GroupBean();
            group.setId(listGroup.get(i).getId());
            group.setPid(listGroup.get(i).getPid());
            group.setName(listGroup.get(i).getName());
            group.setChecked(false);
            List<TB_ChildBean> childList = new ArrayList<>();
            int size = listChild.get(i).size();
            for (int j = 0; j < size; j++) {
                TB_ChildBean child = new TB_ChildBean();
                child.setId(listChild.get(i).get(j).getId());
                child.setPid(listChild.get(i).get(j).getPid());
                child.setName(listChild.get(i).get(j).getName());
                child.setChecked(false);
                childList.add(child);
//                child.addObserver(group);
//                group.addObserver(child);
            }
            group.setChildList(childList);
            groupList.add(group);
        }
    }

    protected void getBranchInfo() {
        String url = AppContext.url + "ApplicationServlet";
        AppContext.kjp.put("param0", "getT_Branch");
        AppContext.kjh.post(url, AppContext.kjp, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                btnTv.setVisibility(View.VISIBLE);
                if (t_branchList == null) {
                    t_branchList = new ArrayList<>();
                    t_branchList = AppContext.gson.fromJson(t, new TypeToken<List<TB_GroupBean>>() {
                    }.getType());
                    //父级部门
                    listGroup = new ArrayList<>();
                    listChild = new ArrayList<>();
                    for (TB_GroupBean tb : t_branchList) {

                        if (tb.getPid() == 131) {
                            tb.setChecked(false);
                            addInfo(tb, t_branchList);
                        }
                    }
                    initData();
                    listAdapter = new MyBranchListAdapter(BranchAty.this, groupList);
                    expandableListView.setAdapter(listAdapter);

                    gridAdapter = new MyBranchGridAdapter(BranchAty.this, gridList);
                    gridView.setAdapter(gridAdapter);
                    reFlashGridView();
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                bundle.putString("return", "");
                intent.putExtras(bundle);
                setResult(AppContext.BRANCH, intent);
                BranchAty.this.finish();
                ToastUtil.showToast(getApplicationContext(), "与服务器断开连接！请检查网络连接是否正常！");
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
     */

    private void addInfo(TB_GroupBean g, List<TB_GroupBean> c) {
        listGroup.add(g);
        List<TB_GroupBean> list = new ArrayList<>();
        for (TB_GroupBean t : c) {
            if (t.getPid() == g.getId()) {
                list.add(t);
            }
        }
        listChild.add(list);
    }
}
