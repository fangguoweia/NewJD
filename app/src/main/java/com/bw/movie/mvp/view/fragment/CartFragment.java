package com.bw.movie.mvp.view.fragment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.CartAdapter;
import com.bw.movie.api.Api;
import com.bw.movie.api.ApiService;
import com.bw.movie.bean.Cart;
import com.bw.movie.bean.DeleteCart;
import com.bw.movie.bean.LoginInfo;
import com.bw.movie.bean.OrderEvent;
import com.bw.movie.bean.UpdateCart;
import com.bw.movie.mvp.presenter.CartPresenter;
import com.bw.movie.mvp.view.CartView;
import com.bw.movie.mvp.view.activity.CreateOrderActivity;
import com.bw.movie.mvp.view.activity.LoginActivity;
import com.bw.movie.util.AESEncryptUtil;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subscribers.DisposableSubscriber;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * author:Created by YangYong on 2018/7/16 0016.
 */
public class CartFragment extends Fragment implements CartView {
    @BindView(R.id.cart_ex)
    ExpandableListView cartEx;
    Unbinder unbinder;
    @BindView(R.id.cart_unlogin)
    LinearLayout cartUnlogin;
    @BindView(R.id.cart_quanxuan)
    CheckBox cartQuanxuan;
    @BindView(R.id.cart_total)
    TextView cartTotal;
    @BindView(R.id.cart_foot)
    LinearLayout cartFoot;
    @BindView(R.id.cart_gologin)
    TextView cartGologin;
    @BindView(R.id.cart_sum)
    TextView cartSum;
    private SharedPreferences preferences;
    private CartPresenter presenter = new CartPresenter(this);
    private CartAdapter cartAdapter;
    private int uid;
    private int islogin;
    private List<Cart.DataBean> list = new ArrayList<>();
    private int sum;
    private float totalPrice;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.cart_fg, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        presenter=new CartPresenter(this);
        Log.d("aaa", "重新加载了onActivityCreated");
        initDialog();
    }

    private void initDialog() {
    }

    @Override
    public void onGetCartSuccess(Cart cart) {
        final List<Cart.DataBean> data = cart.getData();
        //***京东未解之谜2.使用请求数据得到的集合不行，会造成页面数据不更新
        list.clear();
        list.addAll(data);
        if (cartAdapter == null) {
            cartAdapter = new CartAdapter(list);
            cartEx.setAdapter(cartAdapter);
        } else {
            cartAdapter.notifyDataSetChanged();
        }

        //购物车接口的实现
        //***************************************************************
        cartAdapter.setOnCartListener(new CartAdapter.OnCartListener() {
            @Override
            public void onGroupChecked(int groupPosition) {
                boolean allCheckedOnGroup = cartAdapter.isAllCheckedOnGroup(groupPosition);
                cartAdapter.setGoodsCheckedOnGroup(groupPosition, !allCheckedOnGroup);
                cartAdapter.notifyDataSetChanged();
                refreshBottom();
            }

            @Override
            public void onGoodsChecked(int groupPosition, int childPosition) {
                cartAdapter.setGoodsChecked(groupPosition, childPosition);
                cartAdapter.notifyDataSetChanged();
                refreshBottom();
            }

            @Override
            public void onGoodsNumChang(int sellerid, int pid, int num, String selected, int groupPosition, int childPosition) {
                cartAdapter.setGoodsNum(groupPosition, childPosition, num);
                cartAdapter.notifyDataSetChanged();
                refreshBottom();
                //联网更新数据
                presenter.getUpdateCart(uid + "", sellerid + "", pid + "", selected, num + "");
            }

            @Override
            public void onRemoveGoods(final int groupPosition, final int childPosition, final String pid) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("删除当前商品？");
                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cartAdapter.removeGoods(groupPosition, childPosition);
                        //判断group的长度是否为0
                        cartAdapter.setGroup(groupPosition);
                        cartAdapter.notifyDataSetChanged();
                        refreshBottom();
                        //联网删除
                        presenter.deleteCart(uid, Integer.parseInt(pid));
                    }
                });
                builder.setNegativeButton("取消", null);
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        //************************************************************
        //展开所有一级条目
        for (int i = 0; i < data.size(); i++) {
            cartEx.expandGroup(i);
        }
        //刷新底部
        refreshBottom();
        //全选反选,不能使用setOnCheckedChangeListener监听，而要用点击监听
        cartQuanxuan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean allGoodsSelected = cartAdapter.isAllGoodsSelected();
                cartAdapter.setAllGoodsChecked(!allGoodsSelected);
                cartAdapter.notifyDataSetChanged();
                //更新ui
                refreshBottom();
            }
        });
    }

    @Override
    public void onGetUpdateCart(UpdateCart updateCart) {
        String code = updateCart.getCode();
        if (code.equals("0")) {
            Toast.makeText(getActivity(), "更新商品数量成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDeleteSuccess(DeleteCart deleteCart) {
        String code = deleteCart.getCode();
        if (code.equals("0")) {
            Toast.makeText(getActivity(), "删除商品成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDestroy();
        presenter = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d("aaa", "onResume");
        preferences = getActivity().getSharedPreferences("user", 0);
        presenter = new CartPresenter(this);

        //判断是否已经登录
        islogin = preferences.getInt("islogin", 0);
        uid = preferences.getInt("uid", 0);
        Log.i("aaa",uid+"");
        if (islogin == 1) {
            cartUnlogin.setVisibility(View.GONE);
            cartEx.setVisibility(View.VISIBLE);
            cartFoot.setVisibility(View.VISIBLE);
            //请求数据，设置二级列表适配器
            presenter.getCart(uid + "");
        } else {
            cartUnlogin.setVisibility(View.VISIBLE);
            cartEx.setVisibility(View.GONE);
            cartFoot.setVisibility(View.GONE);
        }
    }

//    @OnClick({R.id.cart_gologin, R.id.cart_sum})
//    public void onViewClicked() {
//        startActivity(new Intent(getActivity(), LoginActivity.class));
//    }


    //当fragment隐藏状态发生改变时回调
    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//判断是否已经登录
        if (!hidden) {
            Log.d("aaa", "被show出来了onHiddenChanged");
        }
        islogin = preferences.getInt("islogin", 0);
        uid = preferences.getInt("uid", 0);
        if (islogin == 1 && !hidden) {
            cartUnlogin.setVisibility(View.GONE);
            cartEx.setVisibility(View.VISIBLE);
            cartFoot.setVisibility(View.VISIBLE);
            //请求数据，设置二级列表适配器
            presenter.getCart(uid + "");
        } else {
            cartUnlogin.setVisibility(View.VISIBLE);
            cartEx.setVisibility(View.GONE);
            cartFoot.setVisibility(View.GONE);
        }
    }

    //    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        super.setUserVisibleHint(isVisibleToUser);
//        Log.d("aaa", "setUserVisibleHint");
//        if (isVisibleToUser) {
//            Log.d("aaa", "对用户可见了isVisibleToUser");
//            presenter.getCart(uid + "");
//        }
//    }
    public void refreshBottom() {
        //所有商品都选中，将全选按钮选中
        boolean allGoodsSelected = cartAdapter.isAllGoodsSelected();
        cartQuanxuan.setChecked(allGoodsSelected);
        //设置所有选中商品数量
        sum = cartAdapter.getSum();
        cartSum.setText("去结算(" + cartAdapter.getSum() + ")");
        //设置所有选中商品的总价
        totalPrice = cartAdapter.getTotalPrice();
        cartTotal.setText("合计：￥" + cartAdapter.getTotalPrice());
    }

    @OnClick({R.id.cart_gologin, R.id.cart_sum})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.cart_gologin:
                startActivity(new Intent(getActivity(), LoginActivity.class));
                break;
            case R.id.cart_sum:
                //商品数量大于0才创建订单
                int sum = cartAdapter.getSum();
                if (sum > 0) {
                    //使用rxjava过滤数据，选中的数据取出来
                    EventBus.getDefault().postSticky(new OrderEvent(list, totalPrice));
            //用户登录，获得userid,sessionid
                    userLogin();
            //                    getActivity().startActivity(new Intent(getActivity(), CreateOrderActivity.class));
                }
                Toast.makeText(getActivity(), "所选商品数量：" + this.sum, Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void userLogin() {
        OkHttpClient client = genericClient();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build();
        ApiService apiService = retrofit.create(ApiService.class);

        String phone = "13522219872";
        String pwd = "123456";
        //对称加密
        String pssword = AESEncryptUtil.encrypt(pwd);
        Log.i("aaa", "对称加密pssword:" + pssword);
        Flowable<LoginInfo> flowable = apiService.login(phone, pssword);
        flowable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSubscriber<LoginInfo>() {
                    @Override
                    public void onNext(LoginInfo loginInfo) {
                        LoginInfo.ResultBean result = loginInfo.getResult();
                        int userId = result.getUserId();
                        String sessionId = result.getSessionId();

//                        getActivity().startActivity(new Intent(getActivity(), CreateOrderActivity.class));

                        Intent intent = new Intent(getActivity(), CreateOrderActivity.class);
                        //正常是把登录成功后的用户信息保存在SharedPreferences需要的时候取
                        intent.putExtra("userId", userId);
                        intent.putExtra("sessionId", sessionId);
                        getActivity().startActivity(intent);
                        Log.i("aaa", "登录成功 userId:" + userId + ",登录成功sessionId:" + sessionId);
//                        Toast.makeText(UserActivity.this, loginInfo.getMessage(), Toast.LENGTH_SHORT).show();


                        //放在这里纯粹为了拿到 userId,sessionId
//                        push(token, userId, sessionId);
                    }

                    @Override
                    public void onError(Throwable t) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    //把请求头添加给OkHttpClient的拦截器
    public OkHttpClient genericClient() {
        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public Response intercept(Chain chain) throws IOException {
                        //把Request给Chain
                        Request request = chain.request()
                                .newBuilder()
                                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                                .addHeader("ak", "0110010010001")
                                .build();
                        return chain.proceed(request);
                    }

                })
                .build();

        return httpClient;
    }

}
