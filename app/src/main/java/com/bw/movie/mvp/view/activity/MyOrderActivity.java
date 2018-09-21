package com.bw.movie.mvp.view.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.bw.movie.R;
import com.bw.movie.adapter.OrderVpAdapter;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.CreateOrder;
import com.bw.movie.bean.OrderList;
import com.bw.movie.mvp.presenter.MyOrderPresenter;
import com.bw.movie.mvp.view.OrderView;
import com.bw.movie.mvp.view.fragment.OrderAllFragment;
import com.bw.movie.mvp.view.fragment.OrderAlreadyFragment;
import com.bw.movie.mvp.view.fragment.OrderCancelFragment;
import com.bw.movie.mvp.view.fragment.OrderWaitFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrderActivity extends BaseActivity<MyOrderPresenter> implements OrderView {


    @BindView(R.id.myorder_tab)
    TabLayout myorderTab;
    @BindView(R.id.myorder_vp)
    ViewPager myorderVp;

    @Override
    public MyOrderPresenter providePresenter() {
        return new MyOrderPresenter(this);
    }

    @Override
    public int provideLayoutId() {
        return R.layout.activity_my_order;
    }

    @Override
    public void initListener() {

    }

    private void initVp() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new OrderAllFragment());
        fragments.add(new OrderWaitFragment());
        fragments.add(new OrderAlreadyFragment());
        fragments.add(new OrderCancelFragment());
        List<String> titles = new ArrayList<>();
        titles.add("全部");
        titles.add("待支付");
        titles.add("已支付");
        titles.add("已取消");
        OrderVpAdapter vpAdapter = new OrderVpAdapter(getSupportFragmentManager(), fragments, titles);
        myorderVp.setAdapter(vpAdapter);
        myorderTab.setupWithViewPager(myorderVp);
    }

    @Override
    public void initDate() {
        initVp();
    }

    @Override
    public void onGetOrderSuccess(CreateOrder createOrder) {

    }

    @Override
    public void onGetOrderListSuccess(OrderList orderList) {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
