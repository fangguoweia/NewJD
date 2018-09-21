package com.bw.movie.adapter;

import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.bw.movie.R;
import com.bw.movie.bean.ProductCatagory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author:Created by YangYong on 2018/7/16 0016.
 */
class GridAdapter extends BaseAdapter {
    private List<ProductCatagory.DataBean.ListBean> list;

    public GridAdapter(List<ProductCatagory.DataBean.ListBean> list) {
        this.list = list;
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
            convertView = View.inflate(parent.getContext(), R.layout.child_item, null);
            viewHolder=new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder= (ViewHolder) convertView.getTag();
        }
        viewHolder.childImg.setImageURI(Uri.parse(list.get(position).getIcon()));
        viewHolder.childName.setText(list.get(position).getName());
        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.child_img)
        SimpleDraweeView childImg;
        @BindView(R.id.child_name)
        TextView childName;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
