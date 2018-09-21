package com.bw.movie.mvp.presenter;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.Cart;
import com.bw.movie.bean.DeleteCart;
import com.bw.movie.bean.UpdateCart;
import com.bw.movie.mvp.model.CartModel;
import com.bw.movie.mvp.view.CartView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * author:Created by YangYong on 2018/7/19 0019.
 */
public class CartPresenter extends BasePresenter<CartView> {

    private CartModel cartModel;

    public CartPresenter(CartView view) {
        super(view);
    }

    @Override
    public void initModel() {
        cartModel = new CartModel();
    }
    public void getCart(String uid) {
        cartModel.getCart(uid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Cart>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Cart cart) {
                        view.onGetCartSuccess(cart);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void getUpdateCart(String uid,String sellerid,String pid,String selected,String num) {
        cartModel.getUpdateCart(uid,sellerid,pid,selected,num)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpdateCart>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(UpdateCart updateCart) {
                        view.onGetUpdateCart(updateCart);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void deleteCart(int uid,int pid) {
        cartModel.deleteCart(uid,pid)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<DeleteCart>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(DeleteCart deleteCart) {
                        view.onDeleteSuccess(deleteCart);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
