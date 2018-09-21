package com.bw.movie.mvp.view.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.bean.UpDateNc;
import com.bw.movie.util.MyRetrofit;
import com.bw.movie.util.RetrofitUtil;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class NcSetActivity extends AppCompatActivity {

    @BindView(R.id.ncset_txt)
    EditText ncsetTxt;
    @BindView(R.id.ncset_update)
    Button ncsetUpdate;
    private SharedPreferences preferences;
    private String nc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nc_set);
        ButterKnife.bind(this);
        preferences = getSharedPreferences("user", 0);
        String nickname = preferences.getString("nickname", "");
        ncsetTxt.setText(nickname);

    }

    @OnClick(R.id.ncset_update)
    public void onViewClicked() {
        nc = ncsetTxt.getText().toString().trim();
        String uid = getSharedPreferences("user", 0).getInt("uid", 0)+"";
        RetrofitUtil.getDefault().create(MyRetrofit.class).getUpDateNc(uid, nc)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpDateNc>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UpDateNc upDateNc) {
                        String code = upDateNc.getCode();
                        if (code.equals("0")){
                            Toast.makeText(NcSetActivity.this,"昵称修改成功",Toast.LENGTH_SHORT).show();
                            //将sp中的昵称也修改了
                            SharedPreferences.Editor editor = preferences.edit();
                            editor.putString("nickname",nc);
                            editor.commit();
                            finish();
                        }else{
                            Toast.makeText(NcSetActivity.this,"昵称修改失败",Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
