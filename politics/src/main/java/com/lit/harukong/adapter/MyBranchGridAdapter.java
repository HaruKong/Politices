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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haru on 2016/4/12.
 */
public class MyBranchGridAdapter extends BaseAdapter {

    public List<Object> list = new ArrayList<>();
    private LayoutInflater inflater;

    public MyBranchGridAdapter(Context mContext,List<Object> list) {
        this.list = list;
        inflater = LayoutInflater.from(mContext);
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
            Object obj = getItem(position);
            if (obj instanceof TB_GroupBean) {
                viewHolder.gridTv.setText(((TB_GroupBean) obj).getName());
            } else if (obj instanceof TB_ChildBean) {
                viewHolder.gridTv.setText(((TB_ChildBean) obj).getName());
            }
        }

        return convertView;
    }

    class ViewHolder {
        TextView gridTv;
    }
}
