package com.lit.harukong.ui;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TimePicker;

import com.alibaba.fastjson.JSON;
import com.lit.harukong.AppContext;
import com.lit.harukong.R;
import com.lit.harukong.bean.PartPoliticalInfoBean;
import com.lit.harukong.bean.PoliticsByInternetBean;
import com.lit.harukong.bean.TB_ChildBean;
import com.lit.harukong.bean.TB_GroupBean;
import com.lit.harukong.bean.UserBean;
import com.lit.harukong.util.JudgeBranch;
import com.lit.harukong.util.JudgePoliticsType;
import com.lit.harukong.util.JudgeUserJob;
import com.lit.harukong.util.JudgeUsers;
import com.lit.harukong.util.ToastUtil;
import com.lit.harukong.util.UrlManager;

import org.kymjs.kjframe.http.HttpCallBack;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NewOrEditPoliticsAty extends AppCompatActivity implements View.OnTouchListener, View.OnFocusChangeListener, View.OnClickListener {


    private PartPoliticalInfoBean partPoliticalInfo;
    private String selectAnnDate, selectAnnTime, selectFindDate, selectFindTime;
    private FloatingActionButton fab;
    private LinearLayout btn_DateTime;
    private Button btnModifyDate;
    private Button btnModifyTime;
    private Button btnBranch;
    private EditText etUrl;
    private EditText etTitle;
    private EditText etAnnounceTime;
    private EditText etFindTime;
    private EditText etWebSiteTime;
    private EditText etAnnounceUser;
    private EditText etContent;
    private EditText etJbr;
    private EditText etJbrTel;
    private EditText etPoliticsType;
    private EditText etBID;
    private EditText etBUsers;
    private ProgressDialog mDialogs;
    private Calendar calendar;
    private String secondStr;//秒

    private List<UserBean> userList = new ArrayList<>();
    private List<TB_GroupBean> listForResultG;
    private List<TB_ChildBean> listForResultC;
    private List<UserBean> listForResultU;
    private String sourceIntent;
    private PoliticsByInternetBean beanIntent;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_new_or_new_politics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "确定所填信息无误", Snackbar.LENGTH_LONG)
                        .setAction("保存", null).show();
            }
        });

        assert toolbar != null;
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewOrEditPoliticsAty.this.finish();
            }
        });
        initData();
        initWidget();
        beanIntent = (PoliticsByInternetBean) getIntent().getSerializableExtra("edit_bean");
        sourceIntent = getIntent().getStringExtra("source_politics");
        if (sourceIntent.equals("new_politics")) {
            etAnnounceTime.setText(String.format("%s%s", selectAnnDate, selectAnnTime));
            etFindTime.setText(String.format("%s%s", selectFindDate, selectFindTime));
            etJbr.setEnabled(false);
            etJbrTel.setEnabled(false);
            getSupportActionBar().setSubtitle(R.string.subtitle_activity_new_politics);
            SharedPreferences sp = getSharedPreferences("PoliticsInfo", Activity.MODE_PRIVATE);
            if (null != userList) {
                for (UserBean u : userList) {
                    if (u.getLoginID().equals(sp.getString("mLogin", ""))) {
                        etJbr.setText(u.getName());
                        etJbrTel.setText(u.getTel());
                        break;
                    }
                }
            }
        } else {
            SimpleDateFormat myFmt = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            getSupportActionBar().setSubtitle(R.string.subtitle_activity_edit_politics_aty);
            etUrl.setText(beanIntent.getUrl());
            etTitle.setText(beanIntent.getTitle());
            etAnnounceTime.setText(String.format("%s", myFmt.format(beanIntent.getAnnounceTime()).toString()));
            etFindTime.setText(String.format("%s", myFmt.format(beanIntent.getFindTime()).toString()));
            etWebSiteTime.setText(beanIntent.getWebSiteName());
            etPoliticsType.setText(JudgePoliticsType.politicsType(beanIntent.getPoliticsType()));
            etBID.setText(JudgeBranch.branch(beanIntent.getPoliticsType()));
            etBUsers.setText(JudgeUsers.user(beanIntent.getUsers()));
            etAnnounceUser.setText(beanIntent.getAnnounceUser());
            etJbr.setText(beanIntent.getJbRen());
            etJbrTel.setText(beanIntent.getJbRenTel());
            etContent.setText(beanIntent.getContent());
        }

    }


    public void initData() {
        /**
         * 进入NewPoliticsAty的时间
         */

        long time = System.currentTimeMillis();
        SimpleDateFormat myFmtDate = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat myFmtTime;
        myFmtTime = new SimpleDateFormat(" HH:mm:ss");
        selectAnnDate = myFmtDate.format(time);
        selectAnnTime = myFmtTime.format(time);
        selectFindDate = myFmtDate.format(time);
        selectFindTime = myFmtTime.format(time);
        userList = AppContext.getUser();
    }

    public void initWidget() {
        ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView);
        btn_DateTime = (LinearLayout) findViewById(R.id.btn_DateTime);
        Button btnPaste = (Button) findViewById(R.id.btnPaste);
        Button btnCrawl = (Button) findViewById(R.id.btnCrawlInfo);
        Button btnDelete = (Button) findViewById(R.id.btnDelete);
        btnModifyDate = (Button) findViewById(R.id.btnModifyDate);
        btnModifyTime = (Button) findViewById(R.id.btnModifyTime);
        btnBranch = (Button) findViewById(R.id.btnBranch);

        scrollView.setOnTouchListener(this);
        btnPaste.setOnClickListener(this);
        btnCrawl.setOnClickListener(this);
        btnDelete.setOnClickListener(this);


        etUrl = (EditText) findViewById(R.id.et_url);
        etTitle = (EditText) findViewById(R.id.et_title);
        etAnnounceTime = (EditText) findViewById(R.id.et_announceTime);
        etFindTime = (EditText) findViewById(R.id.et_findTime);
        etWebSiteTime = (EditText) findViewById(R.id.et_webSiteName);
        etAnnounceUser = (EditText) findViewById(R.id.et_announceUser);
        etContent = (EditText) findViewById(R.id.et_content);
        etJbr = (EditText) findViewById(R.id.et_Jbr);
        etJbrTel = (EditText) findViewById(R.id.et_JbrTel);
        etPoliticsType = (EditText) findViewById(R.id.et_politicsType);
        etBID = (EditText) findViewById(R.id.et_bID);
        etBUsers = (EditText) findViewById(R.id.et_bUsers);

        etContent.setOnFocusChangeListener(this);
        etAnnounceTime.setOnFocusChangeListener(this);
        etFindTime.setOnFocusChangeListener(this);
        etPoliticsType.setOnFocusChangeListener(this);
        etBID.setOnFocusChangeListener(this);
        etBUsers.setOnFocusChangeListener(this);

        calendar = Calendar.getInstance();
    }


    @Override
    public boolean onTouch(View v, MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                fab.setVisibility(View.GONE);
                break;
            case MotionEvent.ACTION_UP:
                fab.setVisibility(View.VISIBLE);
                break;
            default:
                break;
        }
        return false;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        switch (v.getId()) {
            case R.id.et_content:
                if (hasFocus) {
                    fab.setVisibility(View.GONE);
                } else {
                    fab.setVisibility(View.VISIBLE);
                }
                break;
            case R.id.et_announceTime:
                if (hasFocus) {
                    btnModifyDate.setText("修改发表日期");
                    btnModifyTime.setText("修改发表时间");
                    btn_DateTime.setVisibility(View.VISIBLE);
                    btnModifyDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            mDatePickerDialog(etAnnounceTime);
                        }
                    });
                    btnModifyTime.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mTimePickerDialog(etAnnounceTime);
                        }
                    });
                } else {
                    btn_DateTime.setVisibility(View.GONE);
                }
                break;
            case R.id.et_findTime:
                if (hasFocus) {
                    btnModifyDate.setText("修改监测日期");
                    btnModifyTime.setText("修改监测时间");
                    btn_DateTime.setVisibility(View.VISIBLE);
                    btnModifyDate.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mDatePickerDialog(etFindTime);
                        }
                    });
                    btnModifyTime.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mTimePickerDialog(etFindTime);
                        }
                    });
                } else {
                    btn_DateTime.setVisibility(View.GONE);
                }
                break;
            case R.id.et_politicsType:
                if (hasFocus) {
                    btnBranch.setText("选择问政类型");
                    btnBranch.setVisibility(View.VISIBLE);
                    btnBranch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setClass(NewOrEditPoliticsAty.this, PoliticsTypeAty.class);
                            startActivityForResult(intent, AppContext.POLITICS_TYPE);
                        }
                    });
                } else {
                    btnBranch.setVisibility(View.GONE);
                }
                break;
            case R.id.et_bID:
                if (hasFocus) {
                    btnBranch.setText("选择接收部门");
                    btnBranch.setVisibility(View.VISIBLE);
                    btnBranch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            etBUsers.setText("");
                            Intent intent = new Intent();
                            intent.setClass(NewOrEditPoliticsAty.this, BranchAty.class);
                            startActivityForResult(intent, AppContext.BRANCH);
                        }
                    });
                } else {
                    btnBranch.setVisibility(View.GONE);
                }
                break;
            case R.id.et_bUsers:
                if (hasFocus) {
                    btnBranch.setText("选择接收人");
                    btnBranch.setVisibility(View.VISIBLE);
                    btnBranch.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if ("".equals(etBID.getText().toString()) || "请选择接收部门"
                                    .equals(etBID.getText().toString())) {
                                ToastUtil.showToast(getApplicationContext(), "请先选择接收部门");
                            } else {
                                Intent intent = new Intent();
                                intent.putExtra("listG", (Serializable) listForResultG);
                                intent.putExtra("listC", (Serializable) listForResultC);
                                intent.setClass(NewOrEditPoliticsAty.this, BranchUserAty.class);
                                startActivityForResult(intent, AppContext.BRANCH_USER);
                            }
                        }
                    });
                } else {
                    btnBranch.setVisibility(View.GONE);
                }
                break;
            default:
                break;
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            //粘贴URL
            case R.id.btnPaste:
                etUrl.setText(UrlManager.paste(getApplicationContext()));
                etUrl.clearFocus();

                break;
            //抓取URL信息
            case R.id.btnCrawlInfo:
                boolean cancel = false;
                View focusView = null;
                String url = etUrl.getText().toString();
                if (TextUtils.isEmpty(url)) {
                    etUrl.setError("Url不能为空");
                    focusView = etUrl;
                    cancel = true;
                }
                if (cancel) {
                    focusView.requestFocus();
                } else {
                    String CrawlUrl = urlChange(etUrl.getText().toString());
                    CrawlInfo(CrawlUrl);
                }
                break;
            case R.id.btnDelete:
                etUrl.setText("");
                break;

            default:
                break;
        }
    }

    /**
     * 问政类型、接收部门、接收人的数据返回
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case AppContext.POLITICS_TYPE:
                if (!data.getStringExtra("return").equals("")) {
                    etPoliticsType.setText(data.getStringExtra("return"));
                } else {
                    ToastUtil.showToast(getApplicationContext(), "你取消了问政类型选择！");
                    etPoliticsType.setText("请选择问政类型");
                }
                break;
            case AppContext.BRANCH:
                Bundle bundle = data.getExtras();
                StringBuffer sb = new StringBuffer();
                if (!"".equals(bundle.getString("return"))) {
                    listForResultG = (List<TB_GroupBean>) bundle.getSerializable("returnG");
                    listForResultC = (List<TB_ChildBean>) bundle.getSerializable("returnC");
                    if (listForResultG != null) {
                        for (TB_GroupBean tg : listForResultG) {
                            sb.append(tg.getName()).append("、");
                        }
                    }
                    if (listForResultC != null) {
                        for (TB_ChildBean tc : listForResultC) {
                            sb.append(tc.getName()).append("、");
                        }
                    }
                    while (sb.charAt(sb.length() - 1) == '、') {
                        sb.deleteCharAt(sb.length() - 1);
                    }
                } else {
//                    ToastUtil.showToast(getApplicationContext(), "你取消了部门选择！");
                    sb.append("请选择接收部门");
                }

                etBID.setText(sb.toString());
                break;
            case AppContext.BRANCH_USER:
                StringBuffer sbUser = new StringBuffer();
                if (!"".equals(data.getExtras().getString("returnU"))) {
                    listForResultU = (List<UserBean>) data.getSerializableExtra("gridUserList");
                    for (UserBean ub : listForResultU) {
                        sbUser.append("【").append(JudgeUserJob.jobBranch(ub.getUserJob())).
                                append("】").append(ub.getTel()).append("-").
                                append(ub.getName()).append("\n");
                    }
                    while (sbUser.charAt(sbUser.length() - 1) == '\n') {
                        sbUser.deleteCharAt(sbUser.length() - 1);
                    }
                } else {
                    sbUser.append("请选择接收人");
                }

                etBUsers.setText(sbUser.toString());
                break;
            default:
                break;
        }

    }

    /**
     * 网络请求
     */

    public void CrawlInfo(String CrawlUrl) {
        mDialogs = ProgressDialog.show(NewOrEditPoliticsAty.this, "抓取信息", "正在抓取中，请稍等。。。", false, false);

        String url = AppContext.url + "PartPoliticsServlet";
        AppContext.kjp.put("param0", "getSqcForum");
        AppContext.kjp.put("url", CrawlUrl);
        AppContext.kjh.post(url, AppContext.kjp, false, new HttpCallBack() {

            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);

                partPoliticalInfo = JSON.parseObject(t, PartPoliticalInfoBean.class);
                if (null == partPoliticalInfo) {
                    ToastUtil.showToast(NewOrEditPoliticsAty.this, "没有获取到信息");
                } else {
                    initTextView(partPoliticalInfo);
                }
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
                ToastUtil.showToast(NewOrEditPoliticsAty.this, "服务器连接中断");
            }

            @Override
            public void onFinish() {
                super.onFinish();
                mDialogs.dismiss();
            }
        });
    }

    /**
     * 修改上传服务器的url格式
     */
    public String urlChange(String str) {
        String a = null;
        Pattern p = Pattern.compile("[0-9]+");
        Matcher m = p.matcher(str);
        while (m.find()) {
            a = m.group();
            if (!a.isEmpty())
                break;
        }
        str = "http://www.sqee.cn/thread-" + a + "-1-1.html";
        return str;
    }

    /**
     * 修改edittext的text
     */
    public void initTextView(PartPoliticalInfoBean p) {
        etTitle.setText(p.getTitle());
        etAnnounceTime.setText(p.getAnnounceTime());
        etWebSiteTime.setText(p.getWebSiteName());
        etAnnounceUser.setText(p.getAnnounceUser());
        etContent.setText(p.getContent());
    }

    /**
     * 日期选择对话框
     */
    public void mDatePickerDialog(final View v) {
        DatePickerDialog datePicker = new DatePickerDialog(NewOrEditPoliticsAty.this, new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendar.setTimeInMillis(System.currentTimeMillis());

                switch (v.getId()) {
                    case R.id.et_announceTime:
                        selectAnnDate = setDate(year, monthOfYear, dayOfMonth);
                        etAnnounceTime.setText(String.format("%s%s", selectAnnDate, selectAnnTime));
                        break;
                    case R.id.et_findTime:
                        selectFindDate = setDate(year, monthOfYear, dayOfMonth);
                        etFindTime.setText(String.format("%s%s", selectFindDate, selectFindTime));
                        break;
                    default:
                        break;
                }

            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePicker.show();
    }

    /**
     * 时间选择对话框
     */
    public void mTimePickerDialog(final View v) {
        TimePickerDialog time = new TimePickerDialog(NewOrEditPoliticsAty.this, new TimePickerDialog.OnTimeSetListener() {

            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                calendar.setTimeInMillis(System.currentTimeMillis());
                int second = calendar.get(Calendar.SECOND);

                switch (v.getId()) {
                    case R.id.et_announceTime:
                        secondStr = second < 10 ? "0" + second : "" + second;
                        selectAnnTime = setTime(hourOfDay, minute) + ":" + secondStr;
                        etAnnounceTime.setText(String.format("%s%s", selectAnnDate, selectAnnTime));
                        break;
                    case R.id.et_findTime:
                        secondStr = second < 10 ? "0" + second : "" + second;
                        selectFindTime = setTime(hourOfDay, minute) + ":" + secondStr;
                        etFindTime.setText(String.format("%s%s", selectFindDate, selectFindTime));
                        break;
                    default:
                        break;
                }

            }
        }, calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE), true);
        time.show();
    }

    /**
     * 设置日期和时间
     */
    public String setDate(int year, int monthOfYear,
                          int dayOfMonth) {
        String monthStr = monthOfYear < 10 ? "0" + ++monthOfYear : "" + monthOfYear;
        String dayStr = dayOfMonth < 10 ? "0" + dayOfMonth : "" + dayOfMonth;
        return year + "/" + monthStr + "/" + dayStr;
    }

    public String setTime(int hourOfDay, int minute) {
        String hourStr = hourOfDay < 10 ? "0" + hourOfDay : "" + hourOfDay;
        String minuteStr = minute < 10 ? "0" + minute : "" + minute;
        return " " + hourStr + ":" + minuteStr;
    }


}
