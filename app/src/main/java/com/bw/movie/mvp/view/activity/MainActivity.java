package com.bw.movie.mvp.view.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.hjm.bottomtabbar.BottomTabBar;
import com.bw.movie.R;
import com.bw.movie.mvp.view.fragment.CartFragment;
import com.bw.movie.mvp.view.fragment.FindFragment;
import com.bw.movie.mvp.view.fragment.TypeFragment;
import com.bw.movie.mvp.view.fragment.MyFragment;
import com.bw.movie.mvp.view.fragment.IndexFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.main_bt)
    BottomTabBar mainBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mainBt.init(getSupportFragmentManager())
                .setImgSize(200, 200)
                .setTabPadding(0, 0, 0)
                .setChangeColor(Color.BLUE, Color.DKGRAY)
                .setFontSize(0)
                .addTabItem("首页", R.drawable.shouye1, R.drawable.shouye0, IndexFragment.class)
                .addTabItem("分类", R.drawable.fenlei1, R.drawable.fenlei0, TypeFragment.class)
                .addTabItem("发现", R.drawable.faxian1, R.drawable.faxian0, FindFragment.class)
                .addTabItem("购物车", R.drawable.gouwu1, R.drawable.gouwu0, CartFragment.class)
                .addTabItem("我的", R.drawable.wode1, R.drawable.wode0, MyFragment.class)
                .isShowDivider(false);
    }
}
