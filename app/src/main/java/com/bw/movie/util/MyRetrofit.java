package com.bw.movie.util;

import com.bw.movie.bean.Ad;
import com.bw.movie.bean.AddCart;
import com.bw.movie.bean.Cart;
import com.bw.movie.bean.Catagory;
import com.bw.movie.bean.CreateOrder;
import com.bw.movie.bean.DeleteCart;
import com.bw.movie.bean.Detail;
import com.bw.movie.bean.Login;
import com.bw.movie.bean.OrderList;
import com.bw.movie.bean.ProductCatagory;
import com.bw.movie.bean.Products;
import com.bw.movie.bean.QueryGoods;
import com.bw.movie.bean.Register;
import com.bw.movie.bean.UpDateNc;
import com.bw.movie.bean.UpLoadIcon;
import com.bw.movie.bean.UpdateCart;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * author:Created by YangYong on 2018/7/17 0017.
 */
public interface MyRetrofit {
    @GET("product/getCatagory")
    Observable<Catagory> getCatagory();

    @GET("product/getProductCatagory")
    Observable<ProductCatagory> getProductCatagory(@Query("cid") String cid);

    @GET("product/getProducts")
    Observable<Products> getProducts(@Query("pscid") String pscid);

    @GET("ad/getAd")
    Observable<Ad> getAd();

    //商品详情
    @GET("product/getProductDetail")
    Observable<Detail> getDetail(@Query("pid") String pid);

    //登录
    @FormUrlEncoded
    @POST("user/login")
    Observable<Login> getLogin(@Field("mobile") String mobile, @Field("password") String password);

    //注册
    @FormUrlEncoded
    @POST("user/reg")
    Observable<Register> getRegister(@Field("mobile") String mobile, @Field("password") String password);

    //修改昵称
    @FormUrlEncoded
    @POST("user/updateNickName")
    Observable<UpDateNc> getUpDateNc(@Field("uid") String uid, @Field("nickname") String nickname);

    //上传头像
    @Multipart
    @POST("file/upload")
    Observable<UpLoadIcon> upLoadIcon(@Query("uid") String uid, @Part MultipartBody.Part part);

    //查询购物车
    @FormUrlEncoded
    @POST("product/getCarts")
    Observable<Cart> getCart(@Field("uid") String uid);

    //加入购物车
    @FormUrlEncoded
    @POST("product/addCart")
    Observable<AddCart> getAddCart(@Field("uid") String uid, @Query("pid") String pid);

    //更新购物车数量
    @FormUrlEncoded
    @POST("product/updateCarts")
    Observable<UpdateCart> getUpdateCart(@Field("uid") String uid, @Field("sellerid") String sellerid, @Field("pid") String pid, @Field("selected") String selected, @Field("num") String num);

    //删除购物车商品
    @FormUrlEncoded
    @POST("product/deleteCart")
    Observable<DeleteCart> deleteCart(@Field("uid") int uid, @Field("pid") int pid);

    //搜索商品
    @GET("product/searchProducts")
    Observable<QueryGoods> getQueryGoods(@Query("keywords") String keywords);

    //创建订单
    @FormUrlEncoded
    @POST("product/createOrder")
    Observable<CreateOrder> getCreateOrder(@Field("uid") String uid, @Field("price") String price);

    //获得订单列表
    @GET("product/getOrders")
    Observable<OrderList> getOrderList(@Query("uid") String uid, @Query("page") String page);
}
