package com.bw.movie.mvp.view.activity;

import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.AddCart;
import com.bw.movie.bean.Detail;
import com.bw.movie.mvp.presenter.DetailPresenter;
import com.bw.movie.mvp.view.DetailView;
import com.bw.movie.util.MyLoader;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class DetailActivity extends BaseActivity<DetailPresenter> implements DetailView {


    @BindView(R.id.detail_banner)
    Banner detailBanner;
    @BindView(R.id.detail_price)
    TextView detailPrice;
    @BindView(R.id.detail_title)
    TextView detailTitle;
    @BindView(R.id.detail_addcart)
    Button detailAddcart;
    private Detail.DataBean data;
    private String pid;
    private int uid;

    @Override
    public DetailPresenter providePresenter() {
        return new DetailPresenter(this);
    }

    @Override
    public int provideLayoutId() {
        return R.layout.activity_detail;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initDate() {
        pid = getIntent().getStringExtra("pid");
        uid = getSharedPreferences("user", 0).getInt("uid", 0);
//        Log.d("aaa", pid);
        presenter.getDetai(pid);

    }

    private void initBanner() {

        List<String> path = new ArrayList<>();
        String images = data.getImages();
        String[] split = images.split("\\|");
        for (String s : split) {
            path.add(s);
        }
        detailBanner.setImages(path)
                .isAutoPlay(false)
                .setImageLoader(new MyLoader())
                .setBannerStyle(BannerConfig.NUM_INDICATOR)
                .start();
    }

    @Override
    public void onDetailSuccess(Detail detail) {
        data = detail.getData();
        //初始化banner
        initBanner();
        detailPrice.setText("￥" + data.getBargainPrice());
        detailTitle.setText(data.getTitle());
    }

    @Override
    public void onAddCartSuccess(AddCart addCart) {
        Toast.makeText(DetailActivity.this,addCart.getMsg(),Toast.LENGTH_SHORT).show();
    }


    @OnClick(R.id.detail_addcart)
    public void onViewClicked() {
        //添加之前先判断是否已登录
        int i = getSharedPreferences("user", 0).getInt("islogin", 0);
        if(i==1){
            presenter.getAddCart(uid+"",pid);
        }else {
            Toast.makeText(DetailActivity.this,"登录后可以使用购物车",Toast.LENGTH_SHORT).show();
        }

    }
}
