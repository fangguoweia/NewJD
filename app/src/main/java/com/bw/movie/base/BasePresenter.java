package com.bw.movie.base;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Administrator on 2018/7/11 0011.
 */

public abstract class BasePresenter<V extends IView> {
    protected CompositeDisposable compositeDisposable = new CompositeDisposable();
    protected V view;

    public BasePresenter(V view) {
        this.view = view;
        initModel();
    }

    public abstract void initModel();

    //内存优化
    public void onDestroy() {
        view = null;
        compositeDisposable.clear();
    }

}
