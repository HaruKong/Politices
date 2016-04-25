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

import com.lit.harukong.AppManager;
import com.lit.harukong.R;
import com.lit.harukong.util.ToastUtil;


public class MainAty extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private SharedPreferences sp;
    private Intent intent = new Intent();
    private String mLoginName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        sp = getSharedPreferences("PoliticsInfo", Activity.MODE_PRIVATE);
        mLoginName = sp.getString("mLogin", "");
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "选择删除问政列表子选项", Snackbar.LENGTH_LONG)
                        .setAction("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Toast.makeText(getApplicationContext(), "你选择了删除问政",
                                        Toast.LENGTH_LONG).show();
                            }
                        }).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);
        ImageView headerImage = (ImageView) headerView.findViewById(R.id.header_imageView);
        final TextView headerTextView = (TextView) headerView.findViewById(R.id.header_login_ID);
        TextView exitApp = (TextView) findViewById(R.id.nav_exit_to_app);
        headerImage.setOnClickListener(this);
        headerTextView.setOnClickListener(this);
        exitApp.setOnClickListener(this);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                headerTextView.setText(mLoginName);
                headerTextView.setTextSize(18);
            }
        });
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
            case R.id.header_imageView:
                Toast.makeText(getApplicationContext(), "点击了头像", Toast.LENGTH_SHORT).show();
                break;
            case R.id.header_login_ID:
                Toast.makeText(getApplicationContext(), "点击了登录名", Toast.LENGTH_SHORT).show();
                break;
            /**
             * 退出应用
             */
            case R.id.nav_exit_to_app:
                Toast.makeText(getApplicationContext(), "点击了退出", Toast.LENGTH_SHORT).show();
                AppManager.getAppManager().AppExit(this);
                break;
            default:
                break;
        }
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
