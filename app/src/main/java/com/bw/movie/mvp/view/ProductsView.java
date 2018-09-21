package com.bw.movie.mvp.view;

import com.bw.movie.base.IView;
import com.bw.movie.bean.Products;

/**
 * author:Created by YangYong on 2018/7/17 0017.
 */
public interface ProductsView extends IView{
    void onSuccess(Products products);
}
