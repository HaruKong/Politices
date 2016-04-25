package com.lit.harukong.util;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;

/**
 * Created by haru on 2016/3/14.
 */
public class UrlManager {
    /**
     * 实现文本复制功能
     */
    public static void copy(String content, Context context) {
        // 得到剪贴板管理器
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData mClipData = ClipData.newPlainText("Label", content);
        cmb.setPrimaryClip(mClipData);
    }

    /**
     * 实现粘贴功能
     */
    public static String paste(Context context) {
        // 得到剪贴板管理器
        String a = null;
        ClipboardManager cmb = (ClipboardManager) context.getSystemService(Context.CLIPBOARD_SERVICE);
        if (cmb.hasPrimaryClip()) {
            ClipData abc = cmb.getPrimaryClip();
            ClipData.Item item = abc.getItemAt(0);
            a = item.getText().toString();
        } else {
            ToastUtil.showToast(context, "无可粘贴的条目");
        }

        return a;
    }

}
