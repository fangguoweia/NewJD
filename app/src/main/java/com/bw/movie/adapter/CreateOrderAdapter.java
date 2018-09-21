package com.bw.movie.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.bw.movie.R;
import com.bw.movie.bean.Cart;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author:Created by YangYong on 2018/8/19 0019.
 */
public class CreateOrderAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Cart.DataBean> list;

    public CreateOrderAdapter(List<Cart.DataBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        switch (viewType) {
            case 0:
                View view = LayoutInflater.from(context).inflate(R.layout.create_one, null,false);
                OneHolder oneHolder = new OneHolder(view);
                return oneHolder;
            case 1:
                View view1 = LayoutInflater.from(context).inflate(R.layout.create_more, null,false);
                MoreHolder moreHolder = new MoreHolder(view1);
                return moreHolder;
        }
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OneHolder) {
            Cart.DataBean.ListBean bean = list.get(position).getList().get(0);
            OneHolder oneHolder = (OneHolder) holder;
            String sellerName = list.get(position).getSellerName();
            oneHolder.createOneSellerName.setText(sellerName);
            String imgUrl = getImgUrl(bean.getImages());
            oneHolder.createOneImg.setImageURI(imgUrl);
            oneHolder.createOneTitle.setText(bean.getTitle());
            oneHolder.createOnePrice.setText("￥" + bean.getBargainPrice());
        } else if (holder instanceof MoreHolder) {
            MoreHolder moreHolder = (MoreHolder) holder;
            moreHolder.createMoreSellerName.setText(list.get(position).getSellerName());
            int size = list.get(position).getList().size();
            //动态创建simpleDraweeView
            for (int i = 0; i < size; i++) {
                SimpleDraweeView simpleDraweeView = new SimpleDraweeView(context);
                String imgUrl = getImgUrl(list.get(position).getList().get(i).getImages());
                simpleDraweeView.setImageURI(imgUrl);
                moreHolder.createMoreLl.addView(simpleDraweeView);

                ViewGroup.LayoutParams params = simpleDraweeView.getLayoutParams();
                params.width = 100*3;
                params.height = 150*3;
                simpleDraweeView.setLayoutParams(params);


            }
        }
    }

    private String getImgUrl(String images) {
        int i = images.indexOf("!");
        return images.substring(0, i);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    @Override
    public int getItemViewType(int position) {
        int size = list.get(position).getList().size();
        if (size == 1) {
            return 0;
        } else {
            return 1;
        }
    }

    static class OneHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.create_one_sellerName)
        TextView createOneSellerName;
        @BindView(R.id.create_one_img)
        SimpleDraweeView createOneImg;
        @BindView(R.id.create_one_title)
        TextView createOneTitle;
        @BindView(R.id.create_one_price)
        TextView createOnePrice;

        OneHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    static class MoreHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.create_more_sellerName)
        TextView createMoreSellerName;
        @BindView(R.id.create_more_ll)
        LinearLayout createMoreLl;

        MoreHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
