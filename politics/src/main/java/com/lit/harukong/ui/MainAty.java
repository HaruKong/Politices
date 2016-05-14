package com.lit.harukong.ui;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
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
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lit.harukong.AppContext;
import com.lit.harukong.R;
import com.lit.harukong.adapter.MyPoliticsListAdapter;
import com.lit.harukong.bean.PoliticsByInternetBean;
import com.lit.harukong.bean.TB_GroupBean;
import com.lit.harukong.interf.OnRefreshListener;
import com.lit.harukong.util.ToastUtil;
import com.lit.harukong.widget.RefreshListView;

import org.kymjs.kjframe.http.HttpCallBack;

import java.util.ArrayList;
import java.util.List;


public class MainAty extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, OnRefreshListener {

    private MyPoliticsListAdapter adapter;
    private RefreshListView rListView;
    private LinearLayout loading_data;
    private boolean isShowDetail = false;

    private List<PoliticsByInternetBean> reList;
    protected Toolbar toolbar;
    protected FloatingActionButton fab;
    protected DrawerLayout drawer;
    protected NavigationView navigationView;
    protected ImageView headerImage;
    private TextView headerTextView;
    protected TextView exitApp;

    protected TextView politics_bar_btn_delete;
    protected TextView politics_show_list;
    protected TextView politics_show_detail;


    private SharedPreferences sp;
    private Intent intent = new Intent();
    private String mLoginName;
    private String mUsers;
    private LinearLayout bottom_bar;

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
                initAdminOrNope();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void initData() {
        sp = getSharedPreferences("PoliticsInfo", Activity.MODE_PRIVATE);
        mLoginName = sp.getString("mLogin", "");
        mUsers = sp.getString("userID", "");
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
        rListView = (RefreshListView) findViewById(R.id.refresh_list);
        loading_data = (LinearLayout) findViewById(R.id.loading_data);
        bottom_bar = (LinearLayout) findViewById(R.id.bottom_bar);
        politics_bar_btn_delete = (TextView) findViewById(R.id.politics_bar_btn_delete);
        politics_show_list = (TextView) findViewById(R.id.politics_show_list);
        politics_show_detail = (TextView) findViewById(R.id.politics_show_detail);

        navigationView.setNavigationItemSelectedListener(this);
        headerImage.setOnClickListener(this);
        headerTextView.setOnClickListener(this);
        exitApp.setOnClickListener(this);
        fab.setOnClickListener(this);
        politics_show_list.setOnClickListener(this);
        politics_show_detail.setOnClickListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
    }

    /**
     * 初始化时管理员还是其他
     */
    public void initAdminOrNope() {
        if (!mLoginName.equals("admin")) {
            politics_bar_btn_delete.setVisibility(View.GONE);
        } else {
            politics_bar_btn_delete.setVisibility(View.VISIBLE);
        }
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
            case R.id.politics_show_detail:
                politics_show_detail.setVisibility(View.GONE);
                politics_show_list.setVisibility(View.VISIBLE);
                isShowDetail = true;
                adapter = new MyPoliticsListAdapter(MainAty.this, reList, mLoginName, isShowDetail);
                rListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
            case R.id.politics_show_list:
                politics_show_list.setVisibility(View.GONE);
                politics_show_detail.setVisibility(View.VISIBLE);
                isShowDetail = false;
                adapter = new MyPoliticsListAdapter(MainAty.this, reList, mLoginName, isShowDetail);
                rListView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
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
        AppContext.kjp.put("Users", mUsers);
        AppContext.kjp.put("mLoginName", mLoginName);
        AppContext.kjh.post(url, AppContext.kjp, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                reList = JSON.parseArray(t, PoliticsByInternetBean.class);
                adapter = new MyPoliticsListAdapter(MainAty.this, reList, mLoginName, isShowDetail);
                rListView.setAdapter(adapter);
                rListView.setOnRefreshListener(MainAty.this);
                rListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
                rListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        ToastUtil.showToast(getApplicationContext(), "你点击了" + position + "项");
                    }
                });
                bottom_bar.setVisibility(View.VISIBLE);
            }

            @Override
            public void onFinish() {
                super.onFinish();
                loading_data.setVisibility(View.GONE);
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
            politics_bar_btn_delete.setVisibility(View.GONE);
        } else {
            ToastUtil.showToast(getApplicationContext(), "管理员可以使用统计功能，但是工程师还没有写好");
            politics_bar_btn_delete.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 宿迁论坛
     */
    public void sqcPlatform() {
        intent.setClass(getApplicationContext(), SqcPlatFormAty.class);
        intent.putExtra("url", "http://www.sqee.cn/forum-8-1.html");
        startActivity(intent);
    }

    /**
     * 设置
     */
    public void settings() {
//        intent.setClass(getApplicationContext(), AppSettings.class);
//        startActivity(intent);

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

    @Override
    public void onDownPullRefresh() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(2000);
                PoliticsByInternetBean politicsByInternetBean = new PoliticsByInternetBean();
                politicsByInternetBean.setTitle("这是下拉刷新出来的数据");
                PoliticsByInternetBean politicsByInternetBean1 = new PoliticsByInternetBean();
                politicsByInternetBean1.setTitle("这是下拉刷新出来的数据1");
                reList.add(politicsByInternetBean);
                reList.add(politicsByInternetBean1);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                adapter.notifyDataSetChanged();
                rListView.hideHeaderView();
            }
        }.execute(new Void[]{});
    }

    @Override
    public void onLoadingMore() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected Void doInBackground(Void... params) {
                SystemClock.sleep(5000);

                PoliticsByInternetBean politicsByInternetBean = new PoliticsByInternetBean();
                politicsByInternetBean.setTitle("这是加载更多出来的数据1");
                PoliticsByInternetBean politicsByInternetBean1 = new PoliticsByInternetBean();
                politicsByInternetBean1.setTitle("这是加载更多出来的数据2");
                reList.add(politicsByInternetBean);
                reList.add(politicsByInternetBean1);
                return null;
            }

            @Override
            protected void onPostExecute(Void result) {
                adapter.notifyDataSetChanged();

                // 控制脚布局隐藏
                rListView.hideFooterView();
            }
        }.execute(new Void[]{});
    }
}
