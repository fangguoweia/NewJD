package com.bw.movie.mvp.model;

import com.bw.movie.bean.Ad;
import com.bw.movie.bean.AddCart;
import com.bw.movie.bean.Catagory;
import com.bw.movie.bean.Detail;
import com.bw.movie.util.MyRetrofit;
import com.bw.movie.util.RetrofitUtil;

import io.reactivex.Observable;

/**
 * author:Created by YangYong on 2018/7/17 0017.
 */
public class IndexModel {
    public Observable<Ad> getAd() {
        return RetrofitUtil.getDefault().create(MyRetrofit.class).getAd();
    }

    public Observable<Catagory> getCatagory() {
        return RetrofitUtil.getDefault().create(MyRetrofit.class).getCatagory();
    }

    public Observable<Detail> getDetail(String pid) {
        return RetrofitUtil.getDefault().create(MyRetrofit.class).getDetail(pid);
    }

    public Observable<AddCart> getAddCart(String uid, String pid) {
        return RetrofitUtil.getDefault().create(MyRetrofit.class).getAddCart(uid, pid);
    }
}
