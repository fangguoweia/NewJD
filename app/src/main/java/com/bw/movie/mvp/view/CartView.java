package com.bw.movie.mvp.view;

import com.bw.movie.base.IView;
import com.bw.movie.bean.Cart;
import com.bw.movie.bean.DeleteCart;
import com.bw.movie.bean.UpdateCart;

/**
 * author:Created by YangYong on 2018/7/19 0019.
 */
public interface CartView extends IView {
    void onGetCartSuccess(Cart cart);
    void onGetUpdateCart(UpdateCart updateCart);
    void onDeleteSuccess(DeleteCart deleteCart);
}
