package com.lit.harukong.demo;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.lit.harukong.R;
import com.lit.harukong.bean.TB_GroupBean;
import com.lit.harukong.util.ToastUtil;

/**
 * expandableListView适配器
 */
public class MyBranchAdapterDemo extends BaseExpandableListAdapter {
    private Context context;
    private List<TB_GroupBean> group;
    private List<List<TB_GroupBean>> child;

    public MyBranchAdapterDemo(Context context, List<TB_GroupBean> group,
                               List<List<TB_GroupBean>> child) {
        this.context = context;
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

    /**
     * 显示：group
     */
    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_group_branch, null);
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
        holder.textViewGroup.setText(group.get(groupPosition).getName());
        holder.textViewNum.setText("" + child.get(groupPosition).size());
        holder.checkBoxGroup.setOnClickListener(new Group_CheckBox_Click(groupPosition));
        return convertView;
    }

    /**
     * 勾選 Group CheckBox 時，存 Group CheckBox 的狀態，以及改變 Child CheckBox 的狀態
     */
    class Group_CheckBox_Click implements View.OnClickListener {
        private int groupPosition;

        Group_CheckBox_Click(int groupPosition) {
            this.groupPosition = groupPosition;
        }

        public void onClick(View v) {
            ToastUtil.showToast(context, group.get(groupPosition).getName());
            // 注意，一定要通知 ExpandableListView 資料已經改變，ExpandableListView 會重新產生畫面
            notifyDataSetChanged();
        }
    }

    /**
     * 显示：child
     */
    @Override
    public View getChildView(int groupPosition, int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(
                    R.layout.item_child_branch, null);
            holder = new ViewHolder();
            holder.textViewChild = (TextView) convertView
                    .findViewById(R.id.id_child_branch);
            holder.checkBoxChild = (CheckBox) convertView.findViewById(R.id.chbChild);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textViewChild.setText(child.get(groupPosition).get(childPosition).getName());
        holder.checkBoxChild.setOnClickListener(new Child_CheckBox_Click(groupPosition, childPosition));
        return convertView;
    }


    /**
     * 勾選 Child CheckBox 時，存 Child CheckBox 的狀態
     */
    class Child_CheckBox_Click implements View.OnClickListener {
        private int groupPosition;
        private int childPosition;

        Child_CheckBox_Click(int groupPosition, int childPosition) {
            this.groupPosition = groupPosition;
            this.childPosition = childPosition;
        }

        public void onClick(View v) {
            ToastUtil.showToast(context, child.get(groupPosition).get(childPosition).getName());
            ToastUtil.showToast(context, groupPosition + "   " + childPosition);
            // 注意，一定要通知 ExpandableListView 資料已經改變，ExpandableListView 會重新產生畫面
            notifyDataSetChanged();
        }
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
