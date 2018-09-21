package com.bw.movie.mvp.view;

import com.bw.movie.base.IView;
import com.bw.movie.bean.AddCart;
import com.bw.movie.bean.Detail;

/**
 * author:Created by YangYong on 2018/7/18 0018.
 */
public interface DetailView extends IView {
    void onDetailSuccess(Detail detail);
    void onAddCartSuccess(AddCart addCart);
}
