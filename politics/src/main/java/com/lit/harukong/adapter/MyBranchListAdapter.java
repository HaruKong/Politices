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
import com.lit.harukong.ui.BranchAty;

import java.util.List;

/**
 * Created by haru on 2016/4/12.
 */
public class MyBranchListAdapter extends BaseExpandableListAdapter {

    private Context mContext;
    private List<TB_GroupBean> group;
    LayoutInflater inflater;

    public MyBranchListAdapter(Context mContext, List<TB_GroupBean> group) {
        this.mContext = mContext;
        this.group = group;
        inflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getGroupCount() {

        return group.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return group.get(groupPosition).getChildList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return group.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return group.get(groupPosition).getChildList().get(childPosition);
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
        final TB_GroupBean t_branchGroupBean = (TB_GroupBean) getGroup(groupPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_group_branch, null);
        }
        ImageView imageViewArrow = (ImageView) convertView.findViewById(R.id.img_arrow);
        TextView textViewGroup = (TextView) convertView
                .findViewById(R.id.id_group_branch);
        TextView textViewNum = (TextView) convertView
                .findViewById(R.id.id_child_num);
        CheckBox checkBoxGroup = (CheckBox) convertView.findViewById(R.id.chbGroup);
        if (isExpanded) {
            imageViewArrow.setImageResource(R.drawable.skin_indicator_expanded);

        } else {
            imageViewArrow.setImageResource(R.drawable.skin_indicator_unexpanded);
        }
        textViewGroup.setText(group.get(groupPosition).getName());
        textViewNum.setText(String.format("%d", group.get(groupPosition).getChildList().size()));
        checkBoxGroup.setChecked(t_branchGroupBean.isChecked());
        checkBoxGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                t_branchGroupBean.changeChecked();
                notifyDataSetChanged();
                ((BranchAty) mContext).reFlashGridView();
            }
        });
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        TB_ChildBean t_branchChildBean = (TB_ChildBean) getChild(groupPosition, childPosition);
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.item_child_branch, null);
        }

        TextView textViewChild = (TextView) convertView
                .findViewById(R.id.id_child_branch);
        CheckBox checkBoxChild = (CheckBox) convertView.findViewById(R.id.chbChild);
        textViewChild.setText(t_branchChildBean.getName());
        checkBoxChild.setChecked(t_branchChildBean.isChecked());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
