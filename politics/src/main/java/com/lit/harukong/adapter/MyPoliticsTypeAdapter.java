package com.lit.harukong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lit.harukong.R;
import com.lit.harukong.bean.PoliticsTypeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by haru on 2016/3/21.
 */
public class MyPoliticsTypeAdapter extends BaseAdapter {

    private LayoutInflater inflater;
    private List<PoliticsTypeBean> list;

    public MyPoliticsTypeAdapter(String[] types, Context mContext) {
        list = new ArrayList<>();
        inflater = LayoutInflater.from(mContext);
        for (String type : types) {
            PoliticsTypeBean politicsType = new PoliticsTypeBean(type);
            list.add(politicsType);
        }
    }


    class ViewHolder {
        public TextView tv_type;
    }

    @Override
    public int getCount() {
        if (null != list) {
            return list.size();
        } else {
            return 0;
        }
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
            convertView = inflater.inflate(R.layout.item_type, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_type.setText(list.get(position).getType());

        return convertView;
    }
}
