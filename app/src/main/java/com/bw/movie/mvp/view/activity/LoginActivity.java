package com.bw.movie.mvp.view.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.Login;
import com.bw.movie.mvp.presenter.LoginPresenter;
import com.bw.movie.mvp.view.LoginView;

import butterknife.BindView;

public class LoginActivity extends BaseActivity<LoginPresenter> implements LoginView, View.OnClickListener {

    @BindView(R.id.login_name)
    EditText loginName;
    @BindView(R.id.login_pwd)
    EditText loginPwd;
    @BindView(R.id.login_login)
    Button loginLogin;
    @BindView(R.id.login_reg)
    TextView loginReg;
    private String name;
    private String pwd;

    @Override
    public LoginPresenter providePresenter() {
        return new LoginPresenter(this);
    }

    @Override
    public int provideLayoutId() {
        return R.layout.activity_login;
    }

    @Override
    public void initListener() {
        loginLogin.setOnClickListener(this);
        loginReg.setOnClickListener(this);
    }

    @Override
    public void initDate() {

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login_login:
                name = loginName.getText().toString().trim();
                pwd = loginPwd.getText().toString().trim();
                presenter.login(name, pwd);
                break;
            case R.id.login_reg:
                startActivity(new Intent(LoginActivity.this,RegisterActivity.class));
                break;
        }
    }

    @Override
    public void onLoginSuccess(Login login) {
        String code = login.getCode();
        if (code.equals("0")) {
            Login.DataBean data = login.getData();
            //登录成功
            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
            //将是否登录状态值和uid存入sp中
            SharedPreferences.Editor editor = getSharedPreferences("user", 0).edit();
            editor.putInt("islogin",1);
            editor.putString("name",name);
            editor.putString("pwd",pwd);
            editor.putInt("uid",data.getUid());
            editor.putString("icon",data.getIcon());
            editor.putString("nickname",data.getNickname());
            editor.commit();
            finish();
        } else {
            Toast.makeText(LoginActivity.this, "用户名或密码错误！", Toast.LENGTH_SHORT).show();
        }
    }

}
