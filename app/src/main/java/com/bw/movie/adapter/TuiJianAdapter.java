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
import com.bw.movie.bean.Ad;
import com.bw.movie.mvp.view.activity.DetailActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author:Created by YangYong on 2018/7/17 0017.
 */
public class TuiJianAdapter extends RecyclerView.Adapter {


    private List<Ad.TuijianBean.ListBean> list;
    private Context context;

    public TuiJianAdapter(List<Ad.TuijianBean.ListBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = View.inflate(context, R.layout.tuijian_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        final Ad.TuijianBean.ListBean bean = list.get(position);
        //处理图片url
        String imgUrl = getImgUrl(bean.getImages());
        mHolder.tuijianImg.setImageURI(Uri.parse(imgUrl));
        mHolder.tuijianTitle.setText(bean.getTitle());
        mHolder.tuijianPrice.setText("￥" + bean.getBargainPrice());

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
        String[] split = images.split("\\|");
        return split[0];
    }

    @Override
    public int getItemCount() {
        return list.size();
    }


//    public interface OnCatagoryLisenter {
//        void onNameClick(int cid);
//    }
//
//    private CatagoryAdapter.OnCatagoryLisenter onCatagoryLisenter;
//
//    public void setOnCatagoryLisenter(CatagoryAdapter.OnCatagoryLisenter onCatagoryLisenter) {
//        this.onCatagoryLisenter = onCatagoryLisenter;
//    }


    class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tuijian_img)
        SimpleDraweeView tuijianImg;
        @BindView(R.id.tuijian_title)
        TextView tuijianTitle;
        @BindView(R.id.tuijian_price)
        TextView tuijianPrice;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
