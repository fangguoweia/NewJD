package com.bw.movie.mvp.model;

import com.bw.movie.bean.Cart;
import com.bw.movie.bean.DeleteCart;
import com.bw.movie.bean.UpdateCart;
import com.bw.movie.util.MyRetrofit;
import com.bw.movie.util.RetrofitUtil;

import io.reactivex.Observable;

/**
 * author:Created by YangYong on 2018/7/19 0019.
 */
public class CartModel {
    public Observable<Cart> getCart(String uid){
        return RetrofitUtil.getDefault().create(MyRetrofit.class).getCart(uid);
    }
    public Observable<UpdateCart> getUpdateCart(String uid,String sellerid,String pid,String selected,String num){
        return RetrofitUtil.getDefault().create(MyRetrofit.class).getUpdateCart(uid,sellerid,pid,selected,num);
    }
    public Observable<DeleteCart> deleteCart(int uid,int pid){
        return RetrofitUtil.getDefault().create(MyRetrofit.class).deleteCart(uid,pid);
    }

}
