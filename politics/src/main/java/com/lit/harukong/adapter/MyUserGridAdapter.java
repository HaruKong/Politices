package com.lit.harukong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lit.harukong.R;
import com.lit.harukong.bean.TB_ChildBean;
import com.lit.harukong.bean.TB_GroupBean;
import com.lit.harukong.bean.UserBean;
import com.lit.harukong.util.JudgeUserJob;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haru on 2016/4/15.
 */
public class MyUserGridAdapter extends BaseAdapter {

    public List<UserBean> list = new ArrayList<>();
    private LayoutInflater inflater;

    public MyUserGridAdapter(Context mContext, List<UserBean> list) {
        this.list = list;
        inflater = inflater.from(mContext);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_grid_view, null);
            viewHolder = new ViewHolder();
            viewHolder.gridTv = (TextView) convertView.findViewById(R.id.textView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            UserBean user = list.get(position);

            String job = list.get(position).getUserJob();
            viewHolder.gridTv.setText(String.format("【%s】%s-%s", JudgeUserJob.jobBranch(job),
                    user.getTel(), user.getName()));
        }

        return convertView;
    }

    class ViewHolder {
        TextView gridTv;
    }
}
