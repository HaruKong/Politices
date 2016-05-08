package com.lit.harukong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lit.harukong.R;
import com.lit.harukong.bean.PoliticsByInternetBean;

import java.util.List;

public class MyPoliticsListAdapter extends BaseAdapter {
    LayoutInflater inflater;
    private List<PoliticsByInternetBean> reList;

    public MyPoliticsListAdapter(Context mContext, List<PoliticsByInternetBean> reList) {
        this.reList = reList;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_list_politics, null);
            viewHolder = new ViewHolder();
            viewHolder.tv_Title = (TextView) convertView.findViewById(R.id.item_politics);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tv_Title.setText(reList.get(position).getTitle());
        return convertView;
    }

    class ViewHolder {
        TextView tv_Title;
    }

}
