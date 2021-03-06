package com.bw.movie.adapter;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

import com.bw.movie.diyview.MyGridView;
import com.bw.movie.R;
import com.bw.movie.bean.ProductCatagory;
import com.bw.movie.mvp.view.activity.ProductsActivity;

import java.util.List;

/**
 * author:Created by YangYong on 2018/7/16 0016.
 */
public class ExAdapter extends BaseExpandableListAdapter {
    private List<ProductCatagory.DataBean> list;

    public ExAdapter(List<ProductCatagory.DataBean> list) {
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getList().get(childPosition);
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
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.group_item, null);
        }
        TextView group_name = convertView.findViewById(R.id.group_name);
        group_name.setText(list.get(groupPosition).getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.grid_view, null);
        }
        //二级item加载一个gridview布局
        MyGridView grid = convertView.findViewById(R.id.grid);
        final List<ProductCatagory.DataBean.ListBean> list = this.list.get(groupPosition).getList();
        grid.setAdapter(new GridAdapter(list));
        //为gridview设置条目点击监听
        grid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(parent.getContext(), list.get(position).getPscid() + "==", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(parent.getContext(), ProductsActivity.class);
                intent.putExtra("pscid", list.get(position).getPscid());
                parent.getContext().startActivity(intent);
            }
        });
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
