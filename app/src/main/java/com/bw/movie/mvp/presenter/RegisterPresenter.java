package com.bw.movie.mvp.presenter;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.Register;
import com.bw.movie.mvp.model.RegisterModel;
import com.bw.movie.mvp.view.RegisterView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author:Created by YangYong on 2018/7/18 0018.
 */
public class RegisterPresenter extends BasePresenter<RegisterView> {

    private RegisterModel registerModel;

    public RegisterPresenter(RegisterView view) {
        super(view);
    }

    @Override
    public void initModel() {
        registerModel = new RegisterModel();
    }
    public void register(String name, String pwd){
        registerModel.Register(name,pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Register>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Register register) {
                        view.onRegisterSuccess(register);
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
