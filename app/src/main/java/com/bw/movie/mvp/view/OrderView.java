package com.bw.movie.mvp.view;

import com.bw.movie.base.IView;
import com.bw.movie.bean.CreateOrder;
import com.bw.movie.bean.OrderList;

/**
 * author:Created by YangYong on 2018/8/20 0020.
 */
public interface OrderView extends IView {
    void onGetOrderSuccess(CreateOrder createOrder);
    void onGetOrderListSuccess(OrderList orderList);
}
