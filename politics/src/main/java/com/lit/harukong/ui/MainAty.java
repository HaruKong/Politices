package com.lit.harukong.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lit.harukong.AppContext;
import com.lit.harukong.R;
import com.lit.harukong.bean.PoliticsByInternetBean;
import com.lit.harukong.util.ToastUtil;

import org.kymjs.kjframe.http.HttpCallBack;

import java.util.List;


public class MainAty extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    //    private static final String ARG_COLUMN_COUNT = "column-count";
    private List<PoliticsByInternetBean> reList;
//    private int mColumnCount = 1;
//    private MyRecyclerViewAdapter adapter;
//    private MyLoadMoreRecyclerView recyclerView;
//    private SwipeRefreshLayout swipeRefreshLayout;
//    private int page = 0;
    protected Toolbar toolbar;
    protected FloatingActionButton fab;
    protected DrawerLayout drawer;
    protected NavigationView navigationView;
    protected ImageView headerImage;
    private TextView headerTextView;
    protected TextView exitApp;
    private SharedPreferences sp;
    private Intent intent = new Intent();
    private String mLoginName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_main);
        initData();
        initWidget();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                headerTextView.setText(mLoginName);
                headerTextView.setTextSize(18);
            }
        });
    }

    public void initData() {
        sp = getSharedPreferences("PoliticsInfo", Activity.MODE_PRIVATE);
        mLoginName = sp.getString("mLogin", "");
        politicsInfo();
    }

    public void initWidget() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        headerImage = (ImageView) headerView.findViewById(R.id.header_imageView);
        headerTextView = (TextView) headerView.findViewById(R.id.header_login_ID);
        exitApp = (TextView) findViewById(R.id.nav_exit_to_app);
//        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh_layout);
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(false);
//                page = 0;
//                adapter.setData(reList);
//                recyclerView.setAutoLoadMoreEnable(false);
//                adapter.notifyDataSetChanged();
//            }
//        });
//        recyclerView = (MyLoadMoreRecyclerView) findViewById(R.id.re_list);
//        recyclerView.setHasFixedSize(true);
        navigationView.setNavigationItemSelectedListener(this);
        headerImage.setOnClickListener(this);
        headerTextView.setOnClickListener(this);
        exitApp.setOnClickListener(this);
        fab.setOnClickListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_new_politics) {
            newPolitics();
        } else if (id == R.id.nav_statistics_politics) {
            statisticsPolitics();
        } else if (id == R.id.nav_sqc_platform) {
            sqcPlatform();
        } else if (id == R.id.nav_settings) {
            settings();
        } else if (id == R.id.nav_swap_user) {
            swapUser();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fab:
                Snackbar.make(v, "选择删除问政列表子选项", Snackbar.LENGTH_LONG)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "你选择了删除问政",
                                        Toast.LENGTH_LONG).show();
                            }
                        }).show();
                break;
            case R.id.header_imageView:
                ToastUtil.showToast(getApplicationContext(), "点击了头像");
                break;
            case R.id.header_login_ID:
                ToastUtil.showToast(getApplicationContext(), mLoginName);
                break;
            /**
             * 退出应用
             */
            case R.id.nav_exit_to_app:
//                Toast.makeText(getApplicationContext(), "点击了退出", Toast.LENGTH_SHORT).show();
                android.os.Process.killProcess(android.os.Process.myPid());//获取PID
                System.exit(0);//常规java、c#的标准退出法，返回值为0代表正常退出
                break;
            default:
                break;
        }
    }

    /**
     * 获取登录账号下的问政信息
     */
    public void politicsInfo() {
        String url = AppContext.url + "ViewPoliticsServlet";
        AppContext.kjp.put("param0", "getPolitics");
        AppContext.kjp.put("loginID", mLoginName);
        AppContext.kjh.post(url, AppContext.kjp, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                reList = JSON.parseArray(t, PoliticsByInternetBean.class);
                ToastUtil.showToast(getApplicationContext(), reList.toString());
//                recyclerView.setLayoutManager(new LinearLayoutManager(MainAty.this));
//                adapter = new MyRecyclerViewAdapter(reList);
//                recyclerView.setAutoLoadMoreEnable(true);
//                recyclerView.setLoadMoreListener(new MyLoadMoreRecyclerView.LoadMoreListener() {
//                    @Override
//                    public void onLoadMore() {
//                        recyclerView.postDelayed(new Runnable() {
//                            @Override
//                            public void run() {
//                                swipeRefreshLayout.setRefreshing(false);
//                                adapter.addDatas(reList);
//                                recyclerView.notifyMoreFinish(false);
//                            }
//                        }, 1000);
//                    }
//                });
//                adapter.notifyDataSetChanged();
            }
        });
    }

    /**
     * 新建问政
     */
    public void newPolitics() {
        intent.setClass(getApplicationContext(), NewPoliticsAty.class);
        startActivity(intent);
    }

    /**
     * 统计问政
     */
    public void statisticsPolitics() {
        if (!mLoginName.equals("admin")) {
            ToastUtil.showToast(getApplicationContext(), "您所在的用户组没有权限使用统计功能!");
        } else {
            ToastUtil.showToast(getApplicationContext(), "管理员可以使用统计功能，但是工程师还没有写好");
        }
    }

    /**
     * 宿迁论坛
     */
    public void sqcPlatform() {
        intent.setClass(getApplicationContext(), SqcPlatFormAty.class);
        startActivity(intent);
    }

    /**
     * 设置
     */
    public void settings() {
        ToastUtil.showToast(getApplicationContext(), "暂时不知道要做哪些功能，先放一放！");
    }

    /**
     * 切换用户
     */
    public void swapUser() {
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(MainAty.this);
        builder.setTitle(R.string.nav_swap_user);
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                SharedPreferences.Editor editor = sp.edit();
                editor.clear().apply();
                MainAty.this.finish();
                intent.setClass(MainAty.this, LoginAty.class);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int whichButton) {
            }
        });
        builder.show();
    }

}
