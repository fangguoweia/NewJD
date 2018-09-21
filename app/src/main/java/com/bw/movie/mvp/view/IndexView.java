package com.bw.movie.mvp.view;

import com.bw.movie.base.IView;
import com.bw.movie.bean.Ad;
import com.bw.movie.bean.Catagory;

/**
 * author:Created by YangYong on 2018/7/17 0017.
 */
public interface IndexView extends IView{
    void onSuccess(Ad ad);
    void onCatagorySuccess(Catagory catagory);
}
