package com.bw.movie.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.CreateOrderAdapter;
import com.bw.movie.bean.Cart;
import com.bw.movie.bean.CreateOrder;
import com.bw.movie.bean.OrderEvent;
import com.bw.movie.bean.OrderList;
import com.bw.movie.mvp.presenter.MyOrderPresenter;
import com.bw.movie.mvp.view.OrderView;
import com.bw.movie.util.MD5Utils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CreateOrderActivity extends AppCompatActivity implements OrderView {

    @BindView(R.id.create_order_rv)
    RecyclerView createOrderRv;
    @BindView(R.id.create_order_zongjia)
    TextView createOrderZongjia;
    @BindView(R.id.create_order_commit)
    TextView createOrderCommit;
    private Unbinder unbinder;
    private List<Cart.DataBean> data;
    private float totalPrice = 0;
    private int userId;
    private String sessionId;//http://172.17.8.100/movieApi/movie/v1/findMovieScheduleList
    private String path = "http://172.17.8.100/movieApi/movie/v1/verify/buyMovieTicket";
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                String json = (String) msg.obj;
                try {
                    JSONObject jsonObject = new JSONObject(json);
                    String orderId = jsonObject.getString("orderId");
                    Intent intent = new Intent(CreateOrderActivity.this, PayActivity.class);
                    intent.putExtra("orderId", orderId);
                    intent.putExtra("userId", userId);
                    intent.putExtra("sessionId", sessionId);
                    intent.putExtra("totalPrice", totalPrice);
                    startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    };
    private MyOrderPresenter presenter;
    private int uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_order);
        presenter = new MyOrderPresenter(this);
        uid = getSharedPreferences("user", 0).getInt("uid", 0);

        unbinder = ButterKnife.bind(this);
//获取传过来的userId，sessionId
        Intent intent = getIntent();
        userId = intent.getIntExtra("userId", 0);
        sessionId = intent.getStringExtra("sessionId");

        EventBus.getDefault().register(this);

    }

    @OnClick(R.id.create_order_commit)
    public void onViewClicked() {
        //跳转到支付页面，传递价格
        if (totalPrice != 0) {
            buy();
            //同时创建订单，请求服务器
            presenter.getOrder(uid + "", totalPrice + "");
        }
    }

    @Subscribe(sticky = true, threadMode = ThreadMode.MAIN)
    public void getOrder(OrderEvent event) {
        data = event.data;
        totalPrice = event.totalPrice;
        int size = data.size();
        createOrderZongjia.setText("￥" + totalPrice);
//        Log.d("aaa", "size:" + size);
//        int selected = data.get(2).getList().get(0).getSelected();
//        Log.d("aaa","selected2:"+selected);
        //观察者取被选中的数据
        for (int i = 0; i < data.size(); i++) {
            //selected为0将当前商品从group中删除
            for (int j = 0; j < data.get(i).getList().size(); j++) {
                List<Cart.DataBean.ListBean> list = data.get(i).getList();
                Cart.DataBean.ListBean listBean = data.get(i).getList().get(j);
                if (listBean.getSelected() == 0) {
                    list.remove(j);
                    j--;
                }
            }
            //group没有数据将group删除
            if (data.get(i).getList().size() == 0) {
                data.remove(i);
                i--;
            }
        }
//        for (int i = 0; i < data.size(); i++) {
//            String sellerName = data.get(i).getSellerName();
//            int size1 = data.get(i).getList().size();
//
//            Log.d("aaa", "sellerName:" + sellerName + "==" + size1);
//        }
//        Log.d("aaa", "data.size:" + data.size());
//使用recyclerview多条目展示订单
        initRv();
    }

    private void initRv() {
        createOrderRv.setLayoutManager(new LinearLayoutManager(CreateOrderActivity.this, LinearLayoutManager.VERTICAL, false));
        createOrderRv.setAdapter(new CreateOrderAdapter(data));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }

    //下单
    private void buy() {
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.SECONDS)
                .writeTimeout(5000, TimeUnit.SECONDS)
                .readTimeout(5000, TimeUnit.SECONDS)
                .build();

        String scheduleId = "1";
        String amount = "3";
        //对这个字符串进行MD5运算
        String sign = MD5Utils.getSign(userId + scheduleId + amount + "movie");
        Log.i("xxx", "进行MD5运算sign:" + sign);


        //3.x版本post请求换成FormBody 封装键值对参数
        FormBody.Builder builder = new FormBody.Builder();
        builder.add("scheduleId", scheduleId);
        builder.add("amount", amount);
        builder.add("sign", sign);


        Request request = new Request.Builder()
                .url(path)
                .addHeader("userId", userId + "")
                .addHeader("sessionId", sessionId)
                .addHeader("ak", "0110010010001")
                .addHeader("Content-Type", "application/x-www-form-urlencoded")
                .post(builder.build())
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(okhttp3.Call call, IOException e) {
                Log.i("xxx", "onFailure:" + e.getMessage());
            }

            @Override
            public void onResponse(okhttp3.Call call, Response response) throws IOException {
                //成功的回调方法里返回预支付订单信息
                String json = response.body().string();
                Log.i("aaa", "onResponse:" + json.toString());
                Message message = new Message();
                message.what = 0;
                message.obj = json;
                handler.sendMessage(message);
            }

        });

    }

    @Override
    public void onGetOrderSuccess(CreateOrder createOrder) {
        Toast.makeText(CreateOrderActivity.this, "创建订单成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onGetOrderListSuccess(OrderList orderList) {

    }
}
