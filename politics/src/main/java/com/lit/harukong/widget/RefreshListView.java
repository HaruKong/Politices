package com.lit.harukong.widget;

import java.text.SimpleDateFormat;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.lit.harukong.R;
import com.lit.harukong.interf.OnRefreshListener;

public class RefreshListView extends ListView implements OnScrollListener {

    private static final String TAG = "RefreshListView";
    private int firstVisibleItemPosition; //
    private int downY; //
    private int headerViewHeight; //
    private View headerView; //


    private final int DOWN_PULL_REFRESH = 0; //
    private final int RELEASE_REFRESH = 1; //
    private final int REFRESHING = 2; //
    private int currentState = DOWN_PULL_REFRESH; //

    private Animation upAnimation; //
    private Animation downAnimation; //

    private ImageView ivArrow; //
    private ProgressBar mProgressBar; //
    private TextView tvState; //
    private TextView tvLastUpdateTime; //

    private OnRefreshListener mOnRefershListener;
    private boolean isScrollToBottom; //
    private View footerView; //
    private int footerViewHeight; //
    private boolean isLoadingMore = false; //

    public RefreshListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initHeaderView();
        initFooterView();
        this.setOnScrollListener(this);
    }

    /**
     *
     */
    private void initFooterView() {
        footerView = View.inflate(getContext(), R.layout.listview_footer, null);
        footerView.measure(0, 0);
        footerViewHeight = footerView.getMeasuredHeight();
        footerView.setPadding(0, -footerViewHeight, 0, 0);
        this.addFooterView(footerView);
    }

    /**
     *
     */
    private void initHeaderView() {
        headerView = View.inflate(getContext(), R.layout.listview_header, null);
        ivArrow = (ImageView) headerView.findViewById(R.id.iv_list_header_arrow);
        mProgressBar = (ProgressBar) headerView.findViewById(R.id.pb_list_header);
        tvState = (TextView) headerView.findViewById(R.id.tv_header_state);
        tvLastUpdateTime = (TextView) headerView.findViewById(R.id.tv_list_header_last_update_time);

        //
        tvLastUpdateTime.setText(String.format("last refresh time: %s", getLastUpdateTime()));

        headerView.measure(0, 0); //
        headerViewHeight = headerView.getMeasuredHeight();
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        this.addHeaderView(headerView); //
        initAnimation();
    }

    /**
     *
     */
    private String getLastUpdateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(System.currentTimeMillis());
    }

    /**
     *
     */
    private void initAnimation() {
        upAnimation = new RotateAnimation(0f, -180f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        upAnimation.setDuration(500);
        upAnimation.setFillAfter(true); //

        downAnimation = new RotateAnimation(-180f, -360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        downAnimation.setDuration(500);
        downAnimation.setFillAfter(true); //
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                downY = (int) ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                int moveY = (int) ev.getY();
                //
                int diff = (moveY - downY) / 2;
                //
                int paddingTop = -headerViewHeight + diff;
                //
                if (firstVisibleItemPosition == 0 && -headerViewHeight < paddingTop) {
                    if (paddingTop > 0 && currentState == DOWN_PULL_REFRESH) { // ��
                        Log.i(TAG, "song kai  xin");
                        currentState = RELEASE_REFRESH;
                        refreshHeaderView();
                    } else if (paddingTop < 0 && currentState == RELEASE_REFRESH) { //
                        Log.i(TAG, "shang la  xin");
                        currentState = DOWN_PULL_REFRESH;
                        refreshHeaderView();
                    }
                    //
                    headerView.setPadding(0, paddingTop, 0, 0);
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                //
                if (currentState == RELEASE_REFRESH) {
                    Log.i(TAG, ".");
                    //
                    headerView.setPadding(0, 0, 0, 0);
                    //
                    currentState = REFRESHING;
                    refreshHeaderView();

                    if (mOnRefershListener != null) {
                        mOnRefershListener.onDownPullRefresh(); //
                    }
                } else if (currentState == DOWN_PULL_REFRESH) {
                    //
                    headerView.setPadding(0, -headerViewHeight, 0, 0);
                }
                break;
            default:
                break;
        }
        return super.onTouchEvent(ev);
    }

    /**
     *
     */
    private void refreshHeaderView() {
        switch (currentState) {
            case DOWN_PULL_REFRESH: //
                tvState.setText("xia la");
                ivArrow.startAnimation(downAnimation); //
                break;
            case RELEASE_REFRESH: //
                tvState.setText("song kai");
                ivArrow.startAnimation(upAnimation); //
                break;
            case REFRESHING: //
                ivArrow.clearAnimation();
                ivArrow.setVisibility(View.GONE);
                mProgressBar.setVisibility(View.VISIBLE);
                tvState.setText("zheng zai ...");
                break;
            default:
                break;
        }
    }

    /**
     *
     */
    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

        if (scrollState == SCROLL_STATE_IDLE || scrollState == SCROLL_STATE_FLING) {
            //
            if (isScrollToBottom && !isLoadingMore) {
                isLoadingMore = true;
                //
                Log.i(TAG, "load more");
                footerView.setPadding(0, 0, 0, 0);
                this.setSelection(this.getCount());

                if (mOnRefershListener != null) {
                    mOnRefershListener.onLoadingMore();
                }
            }
        }
    }

    /**
     *
     *
     * @param firstVisibleItem
     * @param visibleItemCount
     * @param totalItemCount
     */
    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        firstVisibleItemPosition = firstVisibleItem;

        isScrollToBottom = getLastVisiblePosition() == (totalItemCount - 1) ? true : false;
    }

    /**
     *
     */
    public void setOnRefreshListener(OnRefreshListener listener) {
        mOnRefershListener = listener;
    }

    /**
     *
     */
    public void hideHeaderView() {
        headerView.setPadding(0, -headerViewHeight, 0, 0);
        ivArrow.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.GONE);
        tvState.setText("xia la");
        tvLastUpdateTime.setText(String.format("zui hou : %s", getLastUpdateTime()));
        currentState = DOWN_PULL_REFRESH;
    }

    public void hideFooterView() {
        footerView.setPadding(0, -footerViewHeight, 0, 0);
        isLoadingMore = false;
    }
}