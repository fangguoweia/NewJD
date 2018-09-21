package com.bw.movie.mvp.view.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.drawee.view.SimpleDraweeView;
import com.bw.movie.R;
import com.bw.movie.bean.Login;
import com.bw.movie.bean.UpLoadIcon;
import com.bw.movie.mvp.model.LoginModel;
import com.bw.movie.util.MyRetrofit;
import com.bw.movie.util.RetrofitUtil;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class UserSetActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.userset_icon)
    SimpleDraweeView usersetIcon;
    @BindView(R.id.userset_name)
    TextView usersetName;
    @BindView(R.id.userset_seticon)
    TextView usersetSeticon;
    @BindView(R.id.userset_setnick)
    TextView usersetSetnick;
    @BindView(R.id.userset_setaddres)
    TextView usersetSetaddres;
    @BindView(R.id.huiview)
    View huiview;
    @BindView(R.id.userset_unlogin)
    Button usersetUnlogin;
    private SharedPreferences preferences;
    private PopupWindow popupWindow;
    private View view;
    private File file;
    private String uid;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_set);
        ButterKnife.bind(this);
        //判断sd卡指定的目录是否存在
//        File files = new File(Environment.getExternalStorageDirectory() + "/img");
//        File files = new File(getFilesDir().getAbsolutePath() + "/img");
//        if (!files.exists()) {
//            files.mkdirs();
//        }
        file = new File(Environment.getExternalStorageDirectory() + "/1601v.png");
        initData();
        //点击监听
        usersetSeticon.setOnClickListener(this);
        usersetSetnick.setOnClickListener(this);
        usersetSetaddres.setOnClickListener(this);
        usersetUnlogin.setOnClickListener(this);
        //初始化alertdialog
        initDialog();
    }

    private void initDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setTitle("确定退出登录？");
        builder.setMessage("确定退出登录？");
        builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.clear();
                editor.commit();
                finish();
            }
        });
        builder.setNegativeButton("取消", null);
        alertDialog = builder.create();
    }

    private void initData() {
        preferences = getSharedPreferences("user", 0);
        String icon = preferences.getString("icon", "");
//        String nickname = preferences.getString("nickname", "");
        //设置数据
        usersetIcon.setImageURI(Uri.parse(icon));
//        usersetName.setText(nickname);
        //初始化头像popupwindow
        initPop();
    }

    private void initPop() {
        view = View.inflate(this, R.layout.activity_user_set, null);
        View popView = View.inflate(this, R.layout.icon_pop, null);
        popupWindow = new PopupWindow(popView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        //设置透明背景
        popupWindow.setBackgroundDrawable(new ColorDrawable(0));
        //设置pop外部可触摸
        popupWindow.setOutsideTouchable(true);
        //设置焦点事件
        popupWindow.setFocusable(true);
        //设置内部可触摸
        popupWindow.setTouchable(true);
//        popwindow消失监听,将huiview隐藏
        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                huiview.setVisibility(View.GONE);
            }
        });
        //拍照
        popView.findViewById(R.id.icon_capture).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
//                Toast.makeText(UserSetActivity.this, "点点点", Toast.LENGTH_SHORT).show();
                //调用系统相机
                //打开相机 MediaStore.ACTION_IMAGE_CAPTURE 打开相机的Action
                Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                //在Sdcard 中创建文件 存入图片
                it.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                //1.意图   2.requestCode 请求码
                startActivityForResult(it, 1000);
            }
        });
        //选取相册
        popView.findViewById(R.id.icon_photos).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
                Toast.makeText(UserSetActivity.this, "点点点1", Toast.LENGTH_SHORT).show();
            }
        });
        //取消
        popView.findViewById(R.id.icon_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userset_seticon:
                //显示popupwindow的位置
                popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
//                //弹出后将view显示出来
                huiview.setVisibility(View.VISIBLE);
                break;
            case R.id.userset_setnick:
                //修改昵称
                startActivity(new Intent(UserSetActivity.this, NcSetActivity.class));
                break;
            case R.id.userset_setaddres:

                break;
            case R.id.userset_unlogin:
                //注销

                alertDialog.show();
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        String nickname = preferences.getString("nickname", "");
        usersetName.setText(nickname);
    }

    //拍照完成后回调
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1000 && resultCode == RESULT_OK) {
            //调取裁剪功能  om.android.camera.action.CROP 裁剪的Action
            Intent it = new Intent("com.android.camera.action.CROP");
            //得到图片设置类型
            it.setDataAndType(Uri.fromFile(file), "image/*");
            //是否支持裁剪 设置 true 支持  false 不支持
            it.putExtra("CROP", true);
            //设置比例大小  1:1
            it.putExtra("aspectX", 1);
            it.putExtra("aspectY", 1);
            //输出的大小
            it.putExtra("outputX", 250);
            it.putExtra("outputY", 250);
            //将裁剪好的图片进行返回到Intent中
            it.putExtra("return-data", true);
            startActivityForResult(it, 2000);
        }
        //点击完裁剪的完成以后会执行的方法
        if (requestCode == 2000 && resultCode == RESULT_OK) {
            Bitmap bitmap = data.getParcelableExtra("data");
            usersetIcon.setImageBitmap(bitmap);
            //裁剪完成开始上传到服务器
            upLoadIcon();
        }
    }

    //上传头像
    private void upLoadIcon() {
        uid = preferences.getInt("uid", 0) + "";
        final RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
        RetrofitUtil.getDefault().create(MyRetrofit.class).upLoadIcon(uid, part)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<UpLoadIcon>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(UpLoadIcon upLoadIcon) {
                        String code = upLoadIcon.getCode();
                        if (code.equals("0")) {
                            Toast.makeText(UserSetActivity.this, "头像上传成功", Toast.LENGTH_SHORT).show();
                            //上传成功后，获得上传图片的url,存入sp
                            saveSp();
                        } else {
                            Toast.makeText(UserSetActivity.this, "头像上传失败", Toast.LENGTH_SHORT).show();
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

    private void saveSp() {
        //从sp中取出手机号，密码，登录，获得iconurl
        String name = preferences.getString("name", "");
        String pwd = preferences.getString("pwd", "");
        final SharedPreferences.Editor editor = preferences.edit();
        new LoginModel().login(name, pwd)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Login>() {
                    @Override
                    public void accept(Login login) throws Exception {
                        Login.DataBean data = login.getData();
                        String icon = data.getIcon();
                        editor.putString("icon", icon);
                        editor.commit();
                    }
                });
    }

}
