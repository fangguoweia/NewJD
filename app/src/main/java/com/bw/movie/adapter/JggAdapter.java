package com.bw.movie.adapter;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.bw.movie.R;
import com.bw.movie.bean.Catagory;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author:Created by YangYong on 2018/7/17 0017.
 */
public class JggAdapter extends RecyclerView.Adapter {

    private List<Catagory.DataBean> list;

    public JggAdapter(List<Catagory.DataBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.jgg_item, null);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ViewHolder mHolder = (ViewHolder) holder;
        final Catagory.DataBean bean = list.get(position);
        mHolder.jggName.setText(bean.getName());
        mHolder.jggImg.setImageURI(Uri.parse(bean.getIcon()));

//        mHolder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (onCatagoryLisenter != null) {
//                    onCatagoryLisenter.onNameClick(bean.getCid());
//                }
//            }
//        });
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

    static class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.jgg_img)
        SimpleDraweeView jggImg;
        @BindView(R.id.jgg_name)
        TextView jggName;

        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }
}
