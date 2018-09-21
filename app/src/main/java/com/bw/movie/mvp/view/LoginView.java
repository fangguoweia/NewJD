package com.bw.movie.mvp.view;

import com.bw.movie.base.IView;
import com.bw.movie.bean.Login;

/**
 * author:Created by YangYong on 2018/7/18 0018.
 */
public interface LoginView extends IView {
    void onLoginSuccess(Login login);
}
