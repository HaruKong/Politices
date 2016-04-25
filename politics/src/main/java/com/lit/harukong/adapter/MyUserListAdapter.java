package com.lit.harukong.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.lit.harukong.R;
import com.lit.harukong.bean.TB_ChildBean;
import com.lit.harukong.bean.TB_GroupBean;
import com.lit.harukong.bean.UserBean;
import com.lit.harukong.util.JudgeUserJob;

import java.util.List;

/**
 * Created by haru on 2016/4/15.
 */
public class MyUserListAdapter extends BaseExpandableListAdapter {
    private Context mContext;
    private List<Object> group;
    private List<List<UserBean>> child;

    public MyUserListAdapter(Context mContext, List<Object> group, List<List<UserBean>> child) {
        this.mContext = mContext;
        this.group = group;
        this.child = child;
    }

    @Override
    public int getGroupCount() {
        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return child.get(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return child.get(groupPosition).get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_group_branch, null);
            holder = new ViewHolder();
            holder.imageViewArrow = (ImageView) convertView.findViewById(R.id.img_arrow);
            holder.textViewGroup = (TextView) convertView
                    .findViewById(R.id.id_group_branch);
            holder.textViewNum = (TextView) convertView
                    .findViewById(R.id.id_child_num);
            holder.checkBoxGroup = (CheckBox) convertView.findViewById(R.id.chbGroup);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (isExpanded) {
            holder.imageViewArrow.setImageResource(R.drawable.skin_indicator_expanded);

        } else {
            holder.imageViewArrow.setImageResource(R.drawable.skin_indicator_unexpanded);
        }
        Object obj = getGroup(groupPosition);
        if (obj instanceof TB_GroupBean) {
            holder.textViewGroup.setText(((TB_GroupBean) obj).getName());
        } else if (obj instanceof TB_ChildBean) {
            holder.textViewGroup.setText(((TB_ChildBean) obj).getName());
        }
        holder.textViewNum.setText(String.format("%d", child.get(groupPosition).size()));
        holder.checkBoxGroup.setVisibility(View.GONE);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        UserBean userBean = (UserBean) getChild(groupPosition, childPosition);
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_child_branch, null);
            holder = new ViewHolder();
            holder.textViewChild = (TextView) convertView
                    .findViewById(R.id.id_child_branch);
            holder.checkBoxChild = (CheckBox) convertView.findViewById(R.id.chbChild);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        UserBean user = child.get(groupPosition).get(childPosition);
        String job = user.getUserJob();
        holder.textViewChild.setText(String.format("【%s】%s-%s", JudgeUserJob.jobBranch(job), user.getTel(), user.getName()));
        holder.checkBoxChild.setChecked(userBean.isChecked());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    class ViewHolder {
        TextView textViewGroup;
        TextView textViewChild;
        TextView textViewNum;
        ImageView imageViewArrow;
        CheckBox checkBoxGroup;
        CheckBox checkBoxChild;
    }
}
