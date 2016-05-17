package com.lit.harukong;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.lit.harukong.ui.LoginAty;

import org.kymjs.kjframe.http.KJAsyncTask;
import org.kymjs.kjframe.utils.FileUtils;
import org.kymjs.kjframe.utils.SystemTool;

import java.io.File;

/**
 * Created by haru on 2016/3/11.
 */
public class AppStart extends Activity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 防止第三方跳转时出现双实例
        Activity aty = AppManager.getActivity(LoginAty.class);
        if (aty != null && !aty.isFinishing()) {
            finish();
        }
        SystemTool.gc(this); //针对性能好的手机使用，加快应用相应速度

        final View view = View.inflate(this, R.layout.app_start, null);
        setContentView(view);
        // 渐变展示启动屏
        AlphaAnimation aa = new AlphaAnimation(0.5f, 1.0f);
        aa.setDuration(800);
        view.startAnimation(aa);
        aa.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                redirectTo();
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void cleanImageCache() {
        final File folder = FileUtils.getSaveFolder("Politics/imagecache");
        KJAsyncTask.execute(new Runnable() {
            @Override
            public void run() {
                for (File file : folder.listFiles()) {
                    file.delete();
                }
            }
        });
    }

    /**
     * 跳转到...
     */
    private void redirectTo() {
        Intent intent = new Intent(this, LoginAty.class);
        startActivity(intent);
        finish();
    }
}
