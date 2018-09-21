package com.bw.movie.mvp.presenter;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.Catagory;
import com.bw.movie.bean.ProductCatagory;
import com.bw.movie.mvp.model.TypeModel;
import com.bw.movie.mvp.view.TypeView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author:Created by YangYong on 2018/7/17 0017.
 */
public class TypePresenter extends BasePresenter<TypeView> {

    private TypeModel typeModel;

    public TypePresenter(TypeView view) {
        super(view);
    }

    @Override
    public void initModel() {
        typeModel = new TypeModel();
    }
    //获得分类
    public void getCatagory() {
        typeModel.getCatagory()
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
                        view.onCatagoryFaild();
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    //获得子分类
    public void getProductCatagory(String cid) {
        typeModel.getProductCatagory(cid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ProductCatagory>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(ProductCatagory productCatagory) {
                        view.onProductCatagorySuccess(productCatagory);
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
