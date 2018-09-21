package com.bw.movie.mvp.model;

import com.bw.movie.bean.CreateOrder;
import com.bw.movie.bean.OrderList;
import com.bw.movie.util.MyRetrofit;
import com.bw.movie.util.RetrofitUtil;

import io.reactivex.Observable;

/**
 * author:Created by YangYong on 2018/8/20 0020.
 */
public class OrderModel {
    public Observable<CreateOrder> getCreateOrder(String uid, String price) {
        return RetrofitUtil.getDefault().create(MyRetrofit.class).getCreateOrder(uid, price);
    }

    public Observable<OrderList> getOrderList(String uid, String page) {
        return RetrofitUtil.getDefault().create(MyRetrofit.class).getOrderList(uid, page);
    }
}
