package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.bw.movie.R;
import com.bw.movie.bean.QueryGoods;
import com.bw.movie.mvp.view.activity.DetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author:Created by YangYong on 2018/7/24 0024.
 */
public class QueryAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<QueryGoods.DataBean> list;
    private View view;

    public QueryAdapter(List<QueryGoods.DataBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context=parent.getContext();
        view = View.inflate(context, R.layout.query_item, null);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        final QueryGoods.DataBean bean = list.get(position);
        String imgUrl = getImgUrl(bean.getImages());
        mHolder.queryImg.setImageURI(Uri.parse(imgUrl));
        mHolder.queryTitle.setText(bean.getTitle());
        mHolder.queryPrice.setText("￥"+bean.getBargainPrice());

        mHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("pid",bean.getPid()+"");
                context.startActivity(intent);
            }
        });
    }

    private String getImgUrl(String images) {
        int i = images.indexOf("!");
        return images.substring(0, i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.query_img)
        SimpleDraweeView queryImg;
        @BindView(R.id.query_title)
        TextView queryTitle;
        @BindView(R.id.query_price)
        TextView queryPrice;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
