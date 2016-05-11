package com.lit.harukong.ui;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.lit.harukong.AppContext;
import com.lit.harukong.R;
import com.lit.harukong.util.MD5Util;
import com.lit.harukong.util.TimeUtil;

import org.kymjs.kjframe.http.HttpCallBack;


/**
 * A login screen that offers login via login/password.
 */
public class LoginAty extends AppCompatActivity {


    private SharedPreferences sp;
    private JSONArray rs;//网络请求返回值

    private UserLoginTask mAuthTask = null;

    // UI references.
    private EditText mLoginView;
    private EditText mPasswordView;
    private View mLoginFormView;
    private ProgressDialog mDialogs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_login);
        // Set up the login form.
        mLoginView = (EditText) findViewById(R.id.loginID);

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });


        Button mLoginSignInButton = (Button) findViewById(R.id.login_sign_in_button);
        mLoginSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        judgeFirst();
    }


    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }
        // Reset errors.
        mLoginView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String login = mLoginView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
//            mPasswordView.setError(getString(R.string.error_invalid_password));
//            focusView = mPasswordView;
//            cancel = true;
//        } else
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_pwd));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid login.
        if (TextUtils.isEmpty(login)) {
            mLoginView.setError(getString(R.string.error_field_loginID));
            focusView = mLoginView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {

            showForm(true);
            a();
            mAuthTask = new UserLoginTask(login, password);
            mAuthTask.execute((Void) null);
        }
    }


//    private boolean isPasswordValid(String password) {
//        return password.length() > 4;
//    }


    private void showForm(final boolean isShow) {
        mLoginFormView.setVisibility(isShow ? View.GONE : View.VISIBLE);
    }

    private void a() {
        InputMethodManager inputMethodManager = (InputMethodManager) getApplicationContext().
                getSystemService(Context.INPUT_METHOD_SERVICE);


        inputMethodManager.hideSoftInputFromWindow(mLoginView.getWindowToken(), 0); //隐藏
        inputMethodManager.hideSoftInputFromWindow(mPasswordView.getWindowToken(), 0); //隐藏
    }

    public void VerifyLogin(String login, String password) {
        String url = AppContext.url + "UserServlet";

        AppContext.kjp.put("loginID", login);
        AppContext.kjp.put("pwd", password);

        AppContext.kjh.post(url, AppContext.kjp, false, new HttpCallBack() {


            @Override
            public void onSuccess(String t) {
                super.onSuccess(t);
                rs = JSON.parseArray(t);
            }

            @Override
            public void onFailure(int errorNo, String strMsg) {
                super.onFailure(errorNo, strMsg);
            }
        });
    }

    /**
     * 判断是否是第一次登录
     */
    public void judgeFirst() {
        sp = getSharedPreferences("PoliticsInfo", Activity.MODE_PRIVATE);
        String isFirst = sp.getString("isFirst", "");
        switch (isFirst) {
            case "": // 判断是否是第一次打开
                break;
            default:
                startActivity(new Intent(this, MainAty.class));
                this.finish();
                break;
        }
    }

    /**
     * 保存登录信息
     */
    public void saveLoginInfo(String userID, String mLogin, String mPassword) {
        SharedPreferences.Editor editor = sp.edit();

        editor.putString("isFirst", "false");
        editor.putString("userID", userID);
        editor.putString("mLogin", mLogin);
        editor.putString("mPassword", MD5Util.md5(mPassword));
        editor.putString("loginTime", TimeUtil.getFormat().systemDateTime());
        editor.apply();
    }

    public class UserLoginTask extends AsyncTask<Void, Void, JSONArray> {

        private final String mLogin;
        private final String mPassword;

        UserLoginTask(String login, String password) {
            mLogin = login;
            mPassword = password;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            mDialogs = ProgressDialog.show(LoginAty.this, "验证用户", "正在验证中，请稍等。。。", false, false);

        }

        @Override
        protected JSONArray doInBackground(Void... params) {
            VerifyLogin(mLogin, mPassword);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return rs;
        }

        @Override
        protected void onPostExecute(JSONArray objects) {
            super.onPostExecute(objects);
            mAuthTask = null;
//            String success = "2";
//            String userID = "432";
            String success = objects.get(0).toString();
            String userID = objects.get(1).toString();
            if (success != null) {
                switch (success) {
                    case "2":
                        mDialogs.dismiss();
                        finish();
                        saveLoginInfo(userID, mLogin, mPassword);
                        Intent intent = new Intent();
                        intent.setClass(getApplicationContext(), MainAty.class);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), "登录成功", Toast.LENGTH_SHORT).show();
                        break;
                    case "1":
                        mDialogs.dismiss();
                        showForm(false);
                        mPasswordView.setError(getString(R.string.error_incorrect_password));
                        mPasswordView.requestFocus();
                        break;
                    case "0":
                        mDialogs.dismiss();
                        showForm(false);
                        mLoginView.setError(getString(R.string.error_invalid_person));
                        mLoginView.requestFocus();
                        break;
                }
            } else {
                Toast.makeText(getApplicationContext(), "服务器请求失败！", Toast.LENGTH_SHORT).show();
                showForm(false);
                mDialogs.dismiss();
            }

        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showForm(false);
        }
    }
}

