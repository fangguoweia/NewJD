package com.bw.movie.mvp.view.fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bw.movie.R;
import com.bw.movie.mvp.view.activity.LoginActivity;
import com.bw.movie.mvp.view.activity.MyOrderActivity;
import com.bw.movie.mvp.view.activity.UserSetActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * author:Created by YangYong on 2018/7/16 0016.
 */
public class MyFragment extends Fragment {
    @BindView(R.id.my_icon)
    SimpleDraweeView myIcon;
    @BindView(R.id.my_name)
    TextView myName;
    @BindView(R.id.my_head)
    RelativeLayout myHead;
    Unbinder unbinder;
    @BindView(R.id.my_order)
    Button myOrder;
    private SharedPreferences preferences;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.my_fg, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //每次加载，判断是否已登录
//        preferences = getActivity().getSharedPreferences("user", 0);
//        if (getIsLogin()==1){
//            String icon = preferences.getString("icon", "null");
//            String nickname = preferences.getString("nickname", "null");
//            myHead.setBackground(getResources().getDrawable(R.drawable.loginafter));
//            myIcon.setImageURI(Uri.parse(icon));
//            myName.setText(nickname);
//            //*****************************************
//
//        }else {
//            myHead.setBackground(getResources().getDrawable(R.drawable.loginbefore));
//            //设置本地图片
//            myIcon.setActualImageResource(R.drawable.user);
//            myName.setText("登录/注册>");
//            myHead.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    startActivity(new Intent(getActivity(), LoginActivity.class));
//                }
//            });
//        }

    }

    private int getIsLogin() {
//        SharedPreferences preferences = getActivity().getSharedPreferences("user", 0);
        return preferences.getInt("islogin", 0);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onResume() {
        super.onResume();
        preferences = getActivity().getSharedPreferences("user", 0);
        if (getIsLogin() == 1) {
            String icon = preferences.getString("icon", "null");
            String nickname = preferences.getString("nickname", "null");
            myHead.setBackground(getResources().getDrawable(R.drawable.loginafter));
            myIcon.setImageURI(Uri.parse(icon));
            myName.setText(nickname);
            //*****************************************
            myHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), UserSetActivity.class));
                }
            });
        } else {
            myHead.setBackground(getResources().getDrawable(R.drawable.loginbefore));
            //设置本地图片
            myIcon.setActualImageResource(R.drawable.user);
            myName.setText("登录/注册>");
            myHead.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                }
            });
        }
    }

    @OnClick(R.id.my_order)
    public void onViewClicked() {
        startActivity(new Intent(getActivity(), MyOrderActivity.class));
    }
}
