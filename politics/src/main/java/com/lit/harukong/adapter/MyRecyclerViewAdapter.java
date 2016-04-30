package com.lit.harukong.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lit.harukong.R;
import com.lit.harukong.bean.PoliticsByInternetBean;

import java.util.List;

/**
 * Created by haru on 2016/4/29.
 */
public class MyRecyclerViewAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<PoliticsByInternetBean> reList;


    public MyRecyclerViewAdapter(List<PoliticsByInternetBean> items) {
        this.reList = items;
    }

    public void setData(List<PoliticsByInternetBean> datas) {
        this.reList = datas;
    }

    public void addDatas(List<PoliticsByInternetBean> datas) {
        this.reList.addAll(datas);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_recycler_view, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        mHolder.title.setText(reList.get(position).getTitle());
    }

    @Override
    public int getItemCount() {
        return reList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;

        public ViewHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.politics_title);
        }
    }


}
