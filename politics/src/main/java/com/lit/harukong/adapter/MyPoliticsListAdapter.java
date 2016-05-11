package com.lit.harukong.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.lit.harukong.R;
import com.lit.harukong.bean.PoliticsByInternetBean;
import com.lit.harukong.ui.SqcPlatFormAty;
import com.lit.harukong.util.JudgeIsLock;
import com.lit.harukong.util.JudgePoliticsType;
import com.lit.harukong.util.JudgeState;

import java.util.List;

public class MyPoliticsListAdapter extends BaseAdapter {
    LayoutInflater inflater;
    private List<PoliticsByInternetBean> reList;
    private String loginID;
    private boolean isShowDetail;
    private Context mContext;

    public MyPoliticsListAdapter(Context mContext, List<PoliticsByInternetBean> reList, String loginID, boolean isShowDetail) {
        this.mContext = mContext;
        this.reList = reList;
        this.loginID = loginID;
        this.isShowDetail = isShowDetail;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return reList.size();
    }

    @Override
    public Object getItem(int position) {
        return reList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list_politics, null);
            viewHolder = new ViewHolder();
            /**
             * 文本显示
             */
            viewHolder.tv_Title = (TextView) convertView.findViewById(R.id.item_politics_title);
            viewHolder.tv_Branch = (TextView) convertView.findViewById(R.id.item_politics_branch);
            viewHolder.tv_JBRen = (TextView) convertView.findViewById(R.id.item_politics_jbr);
            viewHolder.tv_State = (TextView) convertView.findViewById(R.id.item_politics_state);
            viewHolder.tv_Type = (TextView) convertView.findViewById(R.id.item_politics_type);
            viewHolder.tv_AnnounceTime = (TextView) convertView.findViewById(R.id.item_politics_announce_time);
            viewHolder.tv_FindTime = (TextView) convertView.findViewById(R.id.item_politics_find_time);
            viewHolder.tv_AddTime = (TextView) convertView.findViewById(R.id.item_politics_add_time);
            viewHolder.tv_BaseTime = (TextView) convertView.findViewById(R.id.item_politics_base_time);
            viewHolder.tv_s1Time = (TextView) convertView.findViewById(R.id.item_politics_s1_time);
            viewHolder.tv_s1InTime = (TextView) convertView.findViewById(R.id.item_politics_s1_in_time);
            viewHolder.tv_s2Time = (TextView) convertView.findViewById(R.id.item_politics_s2_time);
            viewHolder.tv_s2InTime = (TextView) convertView.findViewById(R.id.item_politics_s2_in_time);
            viewHolder.tv_AnnounceUser = (TextView) convertView.findViewById(R.id.item_politics_announce_user);
            viewHolder.tv_s1Content = (TextView) convertView.findViewById(R.id.item_politics_s1_content);
            viewHolder.tv_s2Content = (TextView) convertView.findViewById(R.id.item_politics_s2_content);
            viewHolder.tv_isLock = (TextView) convertView.findViewById(R.id.item_politics_is_lock);
            viewHolder.tv_delayLength = (TextView) convertView.findViewById(R.id.item_politics_delayLength);

            /**
             * 多选或其他的按钮组件
             */
            viewHolder.item_checkbox = (CheckBox) convertView.findViewById(R.id.item_checkbox);
            viewHolder.item_list_look = (TextView) convertView.findViewById(R.id.item_list_look);
            viewHolder.item_list_change = (TextView) convertView.findViewById(R.id.item_list_change);
            viewHolder.list_detail = (LinearLayout) convertView.findViewById(R.id.list_detail);
            viewHolder.s1 = (LinearLayout) convertView.findViewById(R.id.item_politics_s1);
            viewHolder.s2 = (LinearLayout) convertView.findViewById(R.id.item_politics_s2);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_Title.setText(String.format("标题：%s", reList.get(position).getTitle()));
        viewHolder.tv_Branch.setText("部门：");
        int state = reList.get(position).getState();
        viewHolder.tv_State.setText(String.format("状态：%s", JudgeState.state(state)));
        viewHolder.tv_JBRen.setText(String.format("交办人：%s--%s", reList.get(position).getJbRen(), reList.get(position).getJbRenTel()));
        int type = reList.get(position).getPoliticsType();
        viewHolder.tv_Type.setText(String.format("分类：%s", JudgePoliticsType.politicsType(type)));
        viewHolder.tv_AnnounceTime.setText(String.format("发表时间：%s", reList.get(position).getAnnounceTime()));
        viewHolder.tv_FindTime.setText(String.format("监测时间：%s", reList.get(position).getFindTime()));
        viewHolder.tv_AddTime.setText(String.format("录入时间：%s", reList.get(position).getAdd_time()));
        viewHolder.tv_BaseTime.setText(String.format("交(领)办时间：%s", reList.get(position).getBase_time()));
        viewHolder.tv_s1Time.setText(String.format("关注时间：%s", reList.get(position).getS1_time()));
        viewHolder.tv_s1InTime.setText(String.format("关注及时：%d", reList.get(position).getS1_in_time()));
        viewHolder.tv_s2Time.setText(String.format("回复时间：%s", reList.get(position).getS2_time()));
        viewHolder.tv_s2InTime.setText(String.format("回复及时：%d", reList.get(position).getS2_in_time()));
        viewHolder.tv_AnnounceUser.setText(String.format("发表人：%s", reList.get(position).getAnnounceUser()));
        viewHolder.tv_s1Content.setText(String.format("知晓性回复：%s", reList.get(position).getS1_content()));
        viewHolder.tv_s2Content.setText(String.format("正式回复：%s", reList.get(position).getS2_content()));
        String isLock = reList.get(position).getIs_lock();
        viewHolder.tv_isLock.setText(String.format("是否锁定：%s", JudgeIsLock.IsLock(isLock)));
        viewHolder.tv_delayLength.setText(String.format("延期天数：%s", reList.get(position).getDelayLength()));

        /**
         *
         */
        viewHolder.item_list_look.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(mContext, SqcPlatFormAty.class);
                intent.putExtra("url", reList.get(position).getUrl());
                mContext.startActivity(intent);
            }
        });
        if (!loginID.equals("admin")) {
            viewHolder.item_checkbox.setVisibility(View.GONE);
            viewHolder.item_list_change.setVisibility(View.GONE);
            viewHolder.tv_s1Content.setVisibility(View.GONE);
            viewHolder.tv_s2Content.setVisibility(View.GONE);
            viewHolder.tv_isLock.setVisibility(View.GONE);
            viewHolder.tv_delayLength.setVisibility(View.GONE);
        } else {
            viewHolder.item_checkbox.setVisibility(View.VISIBLE);
            viewHolder.item_list_change.setVisibility(View.VISIBLE);
            viewHolder.tv_s1Content.setVisibility(View.VISIBLE);
            viewHolder.tv_s2Content.setVisibility(View.VISIBLE);
            viewHolder.tv_isLock.setVisibility(View.VISIBLE);
            viewHolder.tv_delayLength.setVisibility(View.VISIBLE);
        }
        if (isShowDetail) {
            viewHolder.list_detail.setVisibility(View.VISIBLE);
        } else {
            viewHolder.list_detail.setVisibility(View.GONE);
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_Title;
        TextView tv_Branch;
        TextView tv_JBRen;
        TextView tv_State;
        TextView tv_Type;
        TextView tv_AnnounceTime;
        TextView tv_FindTime;
        TextView tv_AddTime;
        TextView tv_BaseTime;
        TextView tv_AnnounceUser;
        CheckBox item_checkbox;
        TextView item_list_look;
        TextView item_list_change;
        LinearLayout list_detail;
        LinearLayout s1;
        TextView tv_s1Time;
        TextView tv_s1InTime;
        LinearLayout s2;
        TextView tv_s2Time;
        TextView tv_s2InTime;
        TextView tv_s1Content;
        TextView tv_s2Content;
        TextView tv_isLock;
        TextView tv_delayLength;
    }

}
