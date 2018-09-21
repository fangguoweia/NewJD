package com.bw.movie.mvp.presenter;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.Products;
import com.bw.movie.mvp.model.TypeModel;
import com.bw.movie.mvp.view.ProductsView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author:Created by YangYong on 2018/7/17 0017.
 */
public class ProductsPresenter extends BasePresenter<ProductsView> {

    private TypeModel typeModel;

    public ProductsPresenter(ProductsView view) {
        super(view);
    }

    @Override
    public void initModel() {
        typeModel = new TypeModel();
    }
    public void getProducts(String pscid) {
        typeModel.getProducts(pscid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Products>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Products products) {
                        view.onSuccess(products);
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
