package com.bw.movie.mvp.view.activity;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.bw.movie.R;
import com.bw.movie.adapter.QueryAdapter;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.QueryGoods;
import com.bw.movie.mvp.presenter.QueryGoodsPresenter;
import com.bw.movie.mvp.view.QueryGoodsView;

import java.util.List;

import butterknife.BindView;

public class QueryGoodsActivity extends BaseActivity<QueryGoodsPresenter> implements QueryGoodsView {


    @BindView(R.id.query_rv)
    RecyclerView queryRv;

    @Override
    public QueryGoodsPresenter providePresenter() {
        return new QueryGoodsPresenter(this);
    }

    @Override
    public int provideLayoutId() {
        return R.layout.activity_query_goods;
    }

    @Override
    public void initListener() {
    }

    @Override
    public void initDate() {
        String keywords = getIntent().getStringExtra("keywords");
        queryRv.setLayoutManager(new LinearLayoutManager(QueryGoodsActivity.this,LinearLayoutManager.VERTICAL,false));
        presenter.getQueryGoods(keywords);
    }

    @Override
    public void onQueryGoodsSuccess(QueryGoods queryGoods) {
        List<QueryGoods.DataBean> data = queryGoods.getData();
        QueryAdapter adapter = new QueryAdapter(data);
        queryRv.setAdapter(adapter);
    }

}
