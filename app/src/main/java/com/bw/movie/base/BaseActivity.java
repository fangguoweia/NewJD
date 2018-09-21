package com.bw.movie.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2018/7/11 0011.
 */

public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity {
    protected  P presenter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(provideLayoutId());
        ButterKnife.bind(this);
        presenter = providePresenter();
//        initView();
        initListener();
        initDate();
    }

    public abstract P providePresenter();
    public abstract int provideLayoutId();
//    public void initView(){
//
//    }
    public abstract void initListener();
    public abstract void initDate();

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
        presenter = null;
    }
}
