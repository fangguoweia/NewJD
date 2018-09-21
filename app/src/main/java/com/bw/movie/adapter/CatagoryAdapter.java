package com.bw.movie.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.bean.Catagory;

import java.util.List;

/**
 * author:Created by YangYong on 2018/7/16 0016.
 */
public class CatagoryAdapter extends RecyclerView.Adapter {
    private List<Catagory.DataBean> list;

    public CatagoryAdapter(List<Catagory.DataBean> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(parent.getContext(), R.layout.catagory_item, null);
        MViewHolder mViewHolder = new MViewHolder(view);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        MViewHolder mHolder = (MViewHolder) holder;
        final Catagory.DataBean bean = list.get(position);
        mHolder.catagory_name.setText(bean.getName());

        mHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCatagoryLisenter != null) {
                    onCatagoryLisenter.onNameClick(bean.getCid());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MViewHolder extends RecyclerView.ViewHolder {

        private final TextView catagory_name;

        public MViewHolder(View itemView) {
            super(itemView);
            catagory_name = itemView.findViewById(R.id.catagory_name);
        }
    }

    public interface OnCatagoryLisenter {
        void onNameClick(int cid);
    }

    private OnCatagoryLisenter onCatagoryLisenter;

    public void setOnCatagoryLisenter(OnCatagoryLisenter onCatagoryLisenter) {
        this.onCatagoryLisenter = onCatagoryLisenter;
    }
}
