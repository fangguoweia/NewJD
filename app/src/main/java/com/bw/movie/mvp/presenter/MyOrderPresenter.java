package com.bw.movie.mvp.presenter;

import com.bw.movie.base.BasePresenter;
import com.bw.movie.bean.CreateOrder;
import com.bw.movie.bean.OrderList;
import com.bw.movie.mvp.model.OrderModel;
import com.bw.movie.mvp.view.OrderView;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

/**
 * author:Created by YangYong on 2018/8/20 0020.
 */
public class MyOrderPresenter extends BasePresenter<OrderView> {

    private OrderModel model;

    public MyOrderPresenter(OrderView view) {
        super(view);
    }

    @Override
    public void initModel() {
        model = new OrderModel();
    }

    public void getOrder(String uid, String price) {
        model.getCreateOrder(uid, price)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<CreateOrder>() {
                    @Override
                    public boolean test(CreateOrder createOrder) throws Exception {
                        if (createOrder.getCode().equals("0"))
                            return true;
                        return false;
                    }
                })
                .subscribe(new Observer<CreateOrder>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(CreateOrder createOrder) {
                        view.onGetOrderSuccess(createOrder);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    public void getOrderList(String uid, String page) {
        model.getOrderList(uid,page)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .filter(new Predicate<OrderList>() {
                    @Override
                    public boolean test(OrderList orderList) throws Exception {
                        if (orderList.getCode().equals("0"))
                            return true;
                        return false;
                    }
                })
                .subscribe(new Observer<OrderList>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(OrderList orderList) {
                        view.onGetOrderListSuccess(orderList);
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
