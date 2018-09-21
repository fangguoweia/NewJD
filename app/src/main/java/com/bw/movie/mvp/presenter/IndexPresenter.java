package com.bw.movie.mvp.presenter;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.Ad;
import com.bw.movie.bean.Catagory;
import com.bw.movie.mvp.model.IndexModel;
import com.bw.movie.mvp.view.IndexView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author:Created by YangYong on 2018/7/17 0017.
 */
public class IndexPresenter extends BasePresenter<IndexView> {


    private IndexModel indexModel;

    public IndexPresenter(IndexView view) {
        super(view);
    }

    @Override
    public void initModel() {
        indexModel = new IndexModel();
    }
    public void getAd() {
        indexModel.getAd()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Ad>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Ad ad) {
                        view.onSuccess(ad);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    public void getCatagory() {
        indexModel.getCatagory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Catagory>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Catagory catagory) {
                        view.onCatagorySuccess(catagory);
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
