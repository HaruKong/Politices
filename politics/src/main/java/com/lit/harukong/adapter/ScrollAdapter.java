package com.lit.harukong.adapter;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.lit.harukong.R;
import com.lit.harukong.ui.CountAty;
import com.lit.harukong.widget.CountScrollView;

import java.util.List;
import java.util.Map;

/**
 * Created by haru on 2016/5/17.
 */
public class ScrollAdapter extends SimpleAdapter {

    private CountAty countAty;
    private List<? extends Map<String, ?>> mData;
    private int res;
    private String[] from;
    private int[] to;
    private Context mContext;

    public ScrollAdapter(CountAty countAty, Context mContext,
                         List<? extends Map<String, ?>> data, int resource,
                         String[] from, int[] to) {
        super(mContext, data, resource, from, to);
        this.countAty = countAty;
        this.mContext = mContext;
        this.mData = data;
        this.res = resource;
        this.from = from;
        this.to = to;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            v = LayoutInflater.from(mContext).inflate(res, null);
            //第一次初始化的时候装进来
            countAty.addHViews((CountScrollView) v.findViewById(R.id.item_scroll));
            View[] views = new View[to.length];
            for (int i = 0; i < to.length; i++) {
                View tv = v.findViewById(to[i]);
                tv.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String item = ((TextView) v).getText().toString();
                        if (item.equals(mData.get(position).get(from[0]))) {
                            StringBuffer sb = new StringBuffer();
                            sb.append("问政总数：").append(mData.get(position).get(from[1])).append('\n');
                            sb.append("未回应：").append(mData.get(position).get(from[2])).append('\n');
                            sb.append("已关注：").append(mData.get(position).get(from[3])).append('\n');
                            sb.append("关注及时：").append(mData.get(position).get(from[4])).append('\n');
                            sb.append("关注超时：").append(mData.get(position).get(from[5])).append('\n');
                            sb.append("已回复：").append(mData.get(position).get(from[6])).append('\n');
                            sb.append("回复及时：").append(mData.get(position).get(from[7])).append('\n');
                            sb.append("回复超时：").append(mData.get(position).get(from[8])).append('\n');
                            new AlertDialog.Builder(mContext)
                                    .setTitle("部门：" + item)
                                    .setMessage(sb)
                                    .setPositiveButton("Ok", null)
                                    .show();
                        } else {
                            Toast.makeText(mContext, item, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                views[i] = tv;
            }
            v.setTag(views);
        }
        View[] holders = (View[]) v.getTag();
        int len = holders.length;
        for (int i = 0; i < len; i++) {
            ((TextView) holders[i]).setText(this.mData.get(position).get(from[i]).toString());
        }
        return v;
    }


}
