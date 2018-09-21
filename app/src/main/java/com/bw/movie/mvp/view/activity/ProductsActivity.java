package com.bw.movie.mvp.view.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.adapter.ProductsAdapter;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.Products;
import com.bw.movie.mvp.presenter.ProductsPresenter;
import com.bw.movie.mvp.view.ProductsView;

import java.util.List;

import butterknife.BindView;

public class ProductsActivity extends BaseActivity<ProductsPresenter> implements ProductsView {


    @BindView(R.id.products_rv)
    RecyclerView productsRv;

    @Override
    public ProductsPresenter providePresenter() {
        return new ProductsPresenter(this);
    }

    @Override
    public int provideLayoutId() {
        return R.layout.activity_products;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initDate() {
        int pscid = getIntent().getIntExtra("pscid", 0);
        productsRv.setLayoutManager(new LinearLayoutManager(ProductsActivity.this, LinearLayoutManager.VERTICAL, false));
        presenter.getProducts(pscid + "");
    }

    @Override
    public void onSuccess(Products products) {
        List<Products.DataBean> data = products.getData();
        productsRv.setAdapter(new ProductsAdapter(data));
    }

}
