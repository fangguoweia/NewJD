package com.bw.movie.mvp.presenter;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.Login;
import com.bw.movie.mvp.model.LoginModel;
import com.bw.movie.mvp.view.LoginView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author:Created by YangYong on 2018/7/18 0018.
 */
public class LoginPresenter extends BasePresenter<LoginView> {

    private LoginModel loginModel;

    public LoginPresenter(LoginView view) {
        super(view);
    }

    @Override
    public void initModel() {
        loginModel = new LoginModel();
    }
    public void login(String name, String pwd){
        loginModel.login(name,pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Login>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Login login) {
                        view.onLoginSuccess(login);
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
