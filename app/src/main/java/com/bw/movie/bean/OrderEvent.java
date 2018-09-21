package com.bw.movie.bean;

import java.util.List;

/**
 * author:Created by YangYong on 2018/8/17 0017.
 */
public class OrderEvent {
    public List<Cart.DataBean> data;
    public float totalPrice;

    public OrderEvent(List<Cart.DataBean> data, float totalPrice) {
        this.data = data;
        this.totalPrice = totalPrice;
    }
}
