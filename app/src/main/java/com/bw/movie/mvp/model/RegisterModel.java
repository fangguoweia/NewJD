package com.bw.movie.mvp.model;


import com.bw.movie.bean.Register;
import com.bw.movie.util.MyRetrofit;
import com.bw.movie.util.RetrofitUtil;

import io.reactivex.Observable;

/**
 * author:Created by YangYong on 2018/7/18 0018.
 */
public class RegisterModel {
    public Observable<Register> Register(String name, String pwd) {
        return RetrofitUtil.getDefault().create(MyRetrofit.class).getRegister(name, pwd);
    }
}
