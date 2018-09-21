package com.bw.movie.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bw.movie.R;
import com.bw.movie.adapter.OrderAllAdapter;
import com.bw.movie.bean.CreateOrder;
import com.bw.movie.bean.OrderList;
import com.bw.movie.mvp.presenter.MyOrderPresenter;
import com.bw.movie.mvp.view.OrderView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author:Created by YangYong on 2018/8/20 0020.
 */
public class OrderWaitFragment extends Fragment implements OrderView {
    Unbinder unbinder;
    @BindView(R.id.orderwait_rv)
    RecyclerView orderwaitRv;
    private MyOrderPresenter presenter;
    private int uid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.orderwait_fg, container, false);
        presenter = new MyOrderPresenter(this);
        uid = getActivity().getSharedPreferences("user", 0).getInt("uid", 0);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        orderwaitRv.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        orderwaitRv.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        presenter.getOrderList(uid + "", 3 + "");
    }

    @Override
    public void onGetOrderSuccess(CreateOrder createOrder) {

    }

    @Override
    public void onGetOrderListSuccess(OrderList orderList) {
        List<OrderList.DataBean> data = orderList.getData();
        for (int i = 0; i <data.size() ; i++) {
            OrderList.DataBean dataBean = data.get(i);
            if (dataBean.getStatus()!=0){
                data.remove(i);
                i--;
            }
        }
        OrderAllAdapter allAdapter = new OrderAllAdapter(data);
        orderwaitRv.setAdapter(allAdapter);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDestroy();
        presenter = null;
    }
}
