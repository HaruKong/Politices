package com.lit.harukong.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.lit.harukong.R;
import com.lit.harukong.util.ToastUtil;
import com.lit.harukong.util.UrlManager;

public class SqcPlatFormAty extends AppCompatActivity implements View.OnClickListener {
    String url = "http://www.sqee.cn/forum-8-1.html";
    private EditText toolbarTitle;
    private WebView webView;
    private ProgressBar progressBar;
    private TextView copy;
    private ImageView web_back;
    private ImageView web_forward;
    private ImageView web_refresh;
    private ImageView web_export;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_sqc_plat_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(null);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SqcPlatFormAty.this.finish();
            }
        });
        copy = (TextView) findViewById(R.id.copy);
        copy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = toolbarTitle.getText().toString();
                UrlManager.copy(url, SqcPlatFormAty.this);
                Toast.makeText(getApplicationContext(), "复制成功", Toast.LENGTH_SHORT).show();
            }
        });
        toolbarTitle = (EditText) findViewById(R.id.toolbar_title);

        webView = (WebView) findViewById(R.id.web_view);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        web_back = (ImageView) findViewById(R.id.web_back);
        web_forward = (ImageView) findViewById(R.id.web_forward);
        web_refresh = (ImageView) findViewById(R.id.web_refresh);
        web_export = (ImageView) findViewById(R.id.web_export);
        web_back.setOnClickListener(this);
        web_forward.setOnClickListener(this);
        web_refresh.setOnClickListener(this);
        web_export.setOnClickListener(this);
        init();
    }

    public void init() {
        toolbarTitle.setText(url);
        //启用支持javascript
        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                // TODO Auto-generated method stub
                SqcPlatFormAty.this.setProgress(newProgress * 100);
                if (newProgress == 100) {
                    // 网页加载完成
                    progressBar.setVisibility(View.GONE);
                    copy.setVisibility(View.VISIBLE);
                } else {
                    // 加载中
                    progressBar.setVisibility(View.VISIBLE);
                    copy.setVisibility(View.GONE);
                }
            }
        });
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                // TODO Auto-generated method stub
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                String urlText = view.getUrl();
                toolbarTitle.setText(urlText);
                return true;
            }
        });
        webView.loadUrl(url);
    }

    //改写物理按键——返回的逻辑
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (webView.canGoBack()) {
                webView.goBack();//返回上一页面
                toolbarTitle.setText(webView.getUrl());
                return true;

            } else {
                System.exit(0);//退出程序
            }
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.web_back:
                if (webView.canGoBack()) {
                    webView.goBack();
                    web_back.setImageDrawable(getResources().getDrawable(R.drawable.btn_back_pressed));
                } else {
                    web_back.setImageDrawable(getResources().getDrawable(R.drawable.btn_back_normal));
                    ToastUtil.showToast(getApplicationContext(), "不能在后退了");
                }
                break;

            case R.id.web_forward:
                if (webView.canGoForward()) {
                    webView.goForward();
                    web_forward.setImageDrawable(getResources().getDrawable(R.drawable.btn_forward_pressed));
                } else {
                    web_forward.setImageDrawable(getResources().getDrawable(R.drawable.btn_forward_normal));
                    ToastUtil.showToast(getApplicationContext(), "不能在前进了");
                }
                break;

            case R.id.web_refresh:
                webView.reload();
                break;

            case R.id.web_export:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse(toolbarTitle.getText().toString());
                intent.setData(content_url);
                startActivity(intent);
                break;
            default:
                break;
        }
    }
}
