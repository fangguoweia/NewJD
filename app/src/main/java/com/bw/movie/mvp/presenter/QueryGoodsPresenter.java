package com.bw.movie.mvp.presenter;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.QueryGoods;
import com.bw.movie.mvp.model.QueryGoodsModel;
import com.bw.movie.mvp.view.QueryGoodsView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author:Created by YangYong on 2018/7/23 0023.
 */
public class QueryGoodsPresenter extends BasePresenter<QueryGoodsView> {

    private QueryGoodsModel model;

    public QueryGoodsPresenter(QueryGoodsView view) {
        super(view);
    }

    @Override
    public void initModel() {
        model = new QueryGoodsModel();
    }
    public void getQueryGoods(String keywords) {
        model.getQueryGoods(keywords)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<QueryGoods>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(QueryGoods queryGoods) {
                        view.onQueryGoodsSuccess(queryGoods);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
