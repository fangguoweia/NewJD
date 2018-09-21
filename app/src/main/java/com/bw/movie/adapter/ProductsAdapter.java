package com.bw.movie.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.bw.movie.R;
import com.bw.movie.bean.Products;
import com.bw.movie.mvp.view.activity.DetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author:Created by YangYong on 2018/7/16 0016.
 */
public class ProductsAdapter extends RecyclerView.Adapter {

    private List<Products.DataBean> list;
    private Context context;

    public ProductsAdapter(List<Products.DataBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = View.inflate(context, R.layout.products, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder viewHolder = (ViewHolder) holder;
        final Products.DataBean bean = list.get(position);
        String imgUrl = getImgUrl(list.get(position).getImages());
        viewHolder.productsImg.setImageURI(Uri.parse(imgUrl));
        viewHolder.productsTitle.setText(bean.getTitle());
        viewHolder.productsYuanjia.setText("原价：￥" + bean.getPrice() + "");
        viewHolder.productsXianjia.setText("现价：￥" + bean.getBargainPrice() + "");

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String pid = bean.getPid() + "";
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("pid", pid);
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

    public interface OnCatagoryLisenter {
        void onNameClick(int cid);
    }

    private CatagoryAdapter.OnCatagoryLisenter onCatagoryLisenter;

    public void setOnCatagoryLisenter(CatagoryAdapter.OnCatagoryLisenter onCatagoryLisenter) {
        this.onCatagoryLisenter = onCatagoryLisenter;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.products_img)
        SimpleDraweeView productsImg;
        @BindView(R.id.products_title)
        TextView productsTitle;
        @BindView(R.id.products_yuanjia)
        TextView productsYuanjia;
        @BindView(R.id.products_xianjia)
        TextView productsXianjia;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            productsYuanjia.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        }
    }
}
