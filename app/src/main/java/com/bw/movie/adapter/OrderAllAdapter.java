package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.OrderList;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author:Created by YangYong on 2018/8/20 0020.
 */
public class OrderAllAdapter extends RecyclerView.Adapter {
    List<OrderList.DataBean> list;
    private Context context;

    public OrderAllAdapter(List<OrderList.DataBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.orderall_item, null, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        OrderList.DataBean bean = list.get(position);
        viewHolder.orderallTitle.setText(bean.getTitle());
        viewHolder.orderallOrderid.setText("订单号：" + bean.getOrderid());
        viewHolder.orderallPrice.setText("总价：" + bean.getPrice() + "");
        viewHolder.orderallStatus.setText("订单状态：" + bean.getStatus());
        viewHolder.orderallTime.setText("创建时间：" + bean.getCreatetime());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.orderall_title)
        TextView orderallTitle;
        @BindView(R.id.orderall_price)
        TextView orderallPrice;
        @BindView(R.id.orderall_orderid)
        TextView orderallOrderid;
        @BindView(R.id.orderall_time)
        TextView orderallTime;
        @BindView(R.id.orderall_status)
        TextView orderallStatus;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
