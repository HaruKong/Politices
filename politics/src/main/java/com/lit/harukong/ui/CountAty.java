package com.lit.harukong.ui;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.lit.harukong.AppContext;
import com.lit.harukong.R;
import com.lit.harukong.adapter.ScrollAdapter;
import com.lit.harukong.bean.CountBean;
import com.lit.harukong.interf.OnRefreshListener;
import com.lit.harukong.util.ToastUtil;
import com.lit.harukong.widget.CountScrollView;

import org.kymjs.kjframe.http.HttpCallBack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class CountAty extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    private List<CountBean> list = new ArrayList<>();
    private ListView mListView;
    private LinearLayout count_view;
    private SwipeRefreshLayout swipeRefreshLayout;
    //方便测试，直接写的public
    public HorizontalScrollView mTouchView;
    //装入所有的HScrollView
    protected List<CountScrollView> mHScrollViews = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_count);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle("左右滑动查看更多");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        assert toolbar != null;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CountAty.this.finish();
            }
        });
        setCountData();
    }

    public void setCountData() {

        String url = AppContext.url + "CountServlet";
        AppContext.kjp.put("param0", "getCount");
        AppContext.kjh.post(url, AppContext.kjp, false, new HttpCallBack() {
            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                list = JSON.parseArray(t, CountBean.class);
                if (null != list) {
                    intiViews(list);
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                ToastUtil.showToast(getApplicationContext(), "服务器异常");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                count_view.setVisibility(View.GONE);
            }
        });
    }


    public void intiViews(List<CountBean> list) {
        count_view = (LinearLayout) findViewById(R.id.count_view);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.count_refresh_list);
        swipeRefreshLayout.setColorSchemeResources(R.color.swipe_color_1,
                R.color.swipe_color_2,
                R.color.swipe_color_3,
                R.color.swipe_color_4);
        swipeRefreshLayout.setSize(SwipeRefreshLayout.DEFAULT);
        swipeRefreshLayout.setProgressViewEndTarget(true, 100);
        swipeRefreshLayout.setOnRefreshListener(this);

        List<Map<String, String>> mDates = new ArrayList<Map<String, String>>();
        Map<String, String> data = null;
        CountScrollView headerScroll = (CountScrollView) findViewById(R.id.item_scroll_title);
        //添加头滑动事件
        mHScrollViews.add(headerScroll);
        mListView = (ListView) findViewById(R.id.scroll_list);
        for (CountBean c : list) {
            data = new HashMap<String, String>();
            data.put("部门", c.getName());
            data.put("问政总数", String.valueOf(c.getTotal()));
            data.put("未回应", String.valueOf(c.getState0()));
            data.put("已关注", String.valueOf(c.getState1()));
            data.put("关注及时", String.valueOf(c.getS1_in_time0()));
            data.put("关注超时", String.valueOf(c.getS1_in_time1()));
            data.put("已回复", String.valueOf(c.getState2()));
            data.put("回复及时", String.valueOf(c.getS2_in_time0()));
            data.put("回复超时", String.valueOf(c.getS2_in_time1()));
            mDates.add(data);
        }

        SimpleAdapter adapter = new ScrollAdapter(this, CountAty.this, mDates, R.layout.item_count
                , new String[]{"部门", "问政总数", "未回应", "已关注", "关注及时", "关注超时", "已回复",
                "回复及时", "回复超时",}
                , new int[]{R.id.item_branch
                , R.id.item_total
                , R.id.item_state0
                , R.id.item_state1
                , R.id.item_s1_in_time0
                , R.id.item_s1_in_time1
                , R.id.item_state2
                , R.id.item_s2_in_time0
                , R.id.item_s2_in_time1});
        mListView.setAdapter(adapter);
    }

    public void addHViews(final CountScrollView hScrollView) {
        if (!mHScrollViews.isEmpty()) {
            int size = mHScrollViews.size();
            CountScrollView scrollView = mHScrollViews.get(size - 1);
            final int scrollX = scrollView.getScrollX();
            //第一次满屏后，向下滑动，有一条数据在开始时未加入
            if (scrollX != 0) {
                mListView.post(new Runnable() {
                    @Override
                    public void run() {
                        //当listView刷新完成之后，把该条移动到最终位置
                        hScrollView.scrollTo(scrollX, 0);
                    }
                });
            }
        }
        mHScrollViews.add(hScrollView);
    }

    public void onScrollChanged(int l, int t, int oldl, int oldt) {
        for (CountScrollView scrollView : mHScrollViews) {
            //防止重复滑动
            if (mTouchView != scrollView)
                scrollView.smoothScrollTo(l, t);
        }
    }


    @Override
    public void onRefresh() {
        new Thread(new Runnable() {//下拉触发的函数，这里是谁1s然后加入一个数据，然后更新界面
            @Override
            public void run() {
                setCountData();

                try {
                    Thread.sleep(1000);

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                handler.sendEmptyMessage(0);
            }
        }).start();
    }

    private MyHandler handler = new MyHandler();

    class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    swipeRefreshLayout.setRefreshing(false);
                    break;
                default:
                    break;
            }
        }
    }
}
