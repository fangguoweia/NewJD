package com.bw.movie.mvp.presenter;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.AddCart;
import com.bw.movie.bean.Detail;
import com.bw.movie.mvp.model.IndexModel;
import com.bw.movie.mvp.view.DetailView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author:Created by YangYong on 2018/7/18 0018.
 */
public class DetailPresenter extends BasePresenter<DetailView> {

    private IndexModel indexModel;

    public DetailPresenter(DetailView view) {
        super(view);
    }

    @Override
    public void initModel() {
        indexModel = new IndexModel();
    }
    public void getDetai(String pid) {
        indexModel.getDetail(pid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Detail>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Detail detail) {
                        view.onDetailSuccess(detail);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
    public void getAddCart(String uid,String pid) {
        indexModel.getAddCart(uid,pid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddCart>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(AddCart addCart) {
                        view.onAddCartSuccess(addCart);
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
