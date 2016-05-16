package com.lit.harukong.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import com.lit.harukong.ui.CountAty;

/**
 * Created by haru on 2016/5/16.
 */
public class CountScrollView extends HorizontalScrollView {

    CountAty countAty;

    public CountScrollView(Context context) {
        super(context);
        countAty = (CountAty) context;
    }

    public CountScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        countAty = (CountAty) context;
    }

    public CountScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        countAty = (CountAty) context;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        /**
         * 进行触摸赋值
         */
        countAty.mTouchView = this;
        return super.onTouchEvent(ev);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        //当当前的CountScrollView被触摸时，滑动其它
        if (countAty.mTouchView == this) {
            countAty.onScrollChanged(l, t, oldl, oldt);
        } else {
            super.onScrollChanged(l, t, oldl, oldt);
        }
    }
}
