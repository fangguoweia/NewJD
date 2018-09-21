package com.bw.movie.mvp.view.activity;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.base.BaseActivity;
import com.bw.movie.bean.Register;
import com.bw.movie.mvp.presenter.RegisterPresenter;
import com.bw.movie.mvp.view.RegisterView;

import butterknife.BindView;
import butterknife.OnClick;

public class RegisterActivity extends BaseActivity<RegisterPresenter> implements RegisterView {

    @BindView(R.id.reg_name)
    EditText regName;
    @BindView(R.id.reg_pwd)
    EditText regPwd;
    @BindView(R.id.reg_reg)
    Button regReg;

    @Override
    public RegisterPresenter providePresenter() {
        return new RegisterPresenter(this);
    }

    @Override
    public int provideLayoutId() {
        return R.layout.activity_register;
    }

    @Override
    public void initListener() {

    }

    @Override
    public void initDate() {
        openDialog();
    }

    @Override
    public void onRegisterSuccess(Register register) {
        String code = register.getCode();
        if (code.equals("0")) {
            Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(RegisterActivity.this, register.getMsg(), Toast.LENGTH_SHORT).show();
        }
    }


    @OnClick(R.id.reg_reg)
    public void onViewClicked() {
        String name = regName.getText().toString().trim();
        String pwd = regPwd.getText().toString().trim();
        presenter.register(name, pwd);
    }

    private void openDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("注册协议及隐私政策");
        builder.setMessage("给我同意");
        builder.setPositiveButton("同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.setNegativeButton("不同意", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        AlertDialog alertDialog = builder.create();
        //设置点击返回按钮不能关闭AlertDialog
        alertDialog.setCancelable(false);
        //设置点击alertDialog外部区域不能关闭AlertDialog
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();
    }
}
