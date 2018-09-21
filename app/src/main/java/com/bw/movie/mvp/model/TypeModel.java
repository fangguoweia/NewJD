package com.bw.movie.mvp.model;

import com.bw.movie.bean.Catagory;
import com.bw.movie.bean.ProductCatagory;
import com.bw.movie.bean.Products;
import com.bw.movie.util.MyRetrofit;
import com.bw.movie.util.RetrofitUtil;

import io.reactivex.Observable;

/**
 * author:Created by YangYong on 2018/7/17 0017.
 */
public class TypeModel {

    //获取分类
    public Observable<Catagory> getCatagory() {
        return RetrofitUtil.getDefault().create(MyRetrofit.class).getCatagory();
    }

    //获取子分类
    public Observable<ProductCatagory> getProductCatagory(String cid) {
        return RetrofitUtil.getDefault().create(MyRetrofit.class).getProductCatagory(cid);
    }
    //获取商品列表
    public Observable<Products> getProducts(String pscid) {
        return RetrofitUtil.getDefault().create(MyRetrofit.class).getProducts(pscid);
    }
}
