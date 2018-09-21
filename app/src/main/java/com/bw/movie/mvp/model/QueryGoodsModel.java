package com.bw.movie.mvp.model;

import com.bw.movie.bean.QueryGoods;
import com.bw.movie.util.MyRetrofit;
import com.bw.movie.util.RetrofitUtil;

import io.reactivex.Observable;

/**
 * author:Created by YangYong on 2018/7/23 0023.
 */
public class QueryGoodsModel {
    public Observable<QueryGoods> getQueryGoods(String keywords){
        return RetrofitUtil.getDefault().create(MyRetrofit.class).getQueryGoods(keywords);
    }
}
