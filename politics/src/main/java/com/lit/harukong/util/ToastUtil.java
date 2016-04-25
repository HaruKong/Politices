package com.lit.harukong.util;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by haru on 2016/3/14.
 */
public class ToastUtil {
    public static void showToast(Context context, String str) {
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
}
