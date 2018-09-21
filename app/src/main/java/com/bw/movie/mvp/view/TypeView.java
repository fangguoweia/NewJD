package com.bw.movie.mvp.view;

import com.bw.movie.base.IView;
import com.bw.movie.bean.Catagory;
import com.bw.movie.bean.ProductCatagory;

/**
 * author:Created by YangYong on 2018/7/17 0017.
 */
public interface TypeView extends IView{
    void onCatagorySuccess(Catagory catagory);
    void onCatagoryFaild();
    void onProductCatagorySuccess(ProductCatagory productCatagory);
}
