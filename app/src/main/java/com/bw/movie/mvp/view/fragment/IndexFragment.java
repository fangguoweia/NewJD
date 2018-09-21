package com.bw.movie.mvp.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import com.bw.movie.R;
import com.bw.movie.adapter.JggAdapter;
import com.bw.movie.adapter.TuiJianAdapter;
import com.bw.movie.bean.Ad;
import com.bw.movie.bean.Catagory;
import com.bw.movie.diyview.MyScrollView;
import com.bw.movie.mvp.presenter.IndexPresenter;
import com.bw.movie.mvp.view.IndexView;
import com.bw.movie.mvp.view.activity.QueryActivity;
import com.bw.movie.util.MyLoader;
import com.youth.banner.Banner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * author:Created by YangYong on 2018/7/16 0016.
 */
public class IndexFragment extends Fragment implements IndexView {
    @BindView(R.id.index_banner)
    Banner indexBanner;
    Unbinder unbinder;
    @BindView(R.id.index_jgg)
    RecyclerView indexJgg;
    @BindView(R.id.index_tuijian)
    RecyclerView indexTuijian;
    @BindView(R.id.sousuokuang)
    LinearLayout sousuokuang;
    @BindView(R.id.index_sv)
    MyScrollView indexSv;
    @BindView(R.id.mysearch_sao)
    TextView mysearchSao;
    @BindView(R.id.mysearch_rl)
    RelativeLayout mysearchRl;
    @BindView(R.id.mysearch_info)
    TextView mysearchInfo;
    @BindView(R.id.index_even)
    TextView indexEven;
    @BindView(R.id.index_hour)
    TextView indexHour;
    @BindView(R.id.index_minute)
    TextView indexMinute;
    @BindView(R.id.index_second)
    TextView indexSecond;
    private IndexPresenter presenter;
    private List<Ad.DataBean> data;
    private List<Ad.TuijianBean.ListBean> tuijian;
    private CompositeDisposable compositeDisposable = new CompositeDisposable();
    private final int REQUEST_CODE = 200;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.index_fg, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new IndexPresenter(this);
        //初始化首页数据
        initData();
        sousuokuang.getBackground().setAlpha(0);
        indexSv.setOnScrollChanged(new MyScrollView.OnScrollChanged() {
            @Override
            public void onScrollChanged(int x, int y, int oldx, int oldy) {
//                Log.d("aaa", x + "," + y + "=====" + oldx + "," + oldy);
                if (y < 270) {
                    int i = (int) ((float) y / (float) 270 * 255);
                    sousuokuang.getBackground().setAlpha(i);
                } else {
                    sousuokuang.getBackground().setAlpha(255);
                }
            }
        });
    }

    private void initData() {
        initMiaosha();
        indexJgg.setLayoutManager(new GridLayoutManager(getActivity(), 2, LinearLayoutManager.HORIZONTAL, false));
        indexTuijian.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        //关闭嵌套滑动
        indexTuijian.setNestedScrollingEnabled(false);
        //固定recyclerview尺寸
        indexTuijian.setHasFixedSize(true);
        presenter.getAd();
        presenter.getCatagory();
    }

    private void initMiaosha() {
        Observable.interval(1, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        compositeDisposable.add(d);
                    }

                    @Override
                    public void onNext(Long aLong) {
                        upDateMiaosha();
                    }

                    @Override
                    public void onError(Throwable e) {
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void upDateMiaosha() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date curDate = new Date(System.currentTimeMillis());
        String format = df.format(curDate);
        StringBuffer buffer = new StringBuffer();
        String substring = format.substring(0, 11);
        buffer.append(substring);
        Log.d("ccc", substring);
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour % 2 == 0) {
            indexEven.setText(hour + "点场");
            buffer.append((hour + 2));
            buffer.append(":00:00");
        } else {
            indexEven.setText((hour - 1) + "点场");
            buffer.append((hour + 1));
            buffer.append(":00:00");
        }
        String totime = buffer.toString();

        try {
            Date date = df.parse(totime);
            Date date1 = df.parse(format);
            long defferenttime = date.getTime() - date1.getTime();
            long days = defferenttime / (1000 * 60 * 60 * 24);
            long hours = (defferenttime - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minute = (defferenttime - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            long seconds = defferenttime % 60000;
            long second = Math.round((float) seconds / 1000);
            indexHour.setText("0" + hours + "");
            if (minute >= 10) {
                indexMinute.setText(minute + "");
            } else {
                indexMinute.setText("0" + minute + "");
            }
            if (second >= 10) {
                indexSecond.setText(second + "");
            } else {
                indexSecond.setText("0" + second + "");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initBanner() {
//        List<String> title = new ArrayList<>();
        List<String> path = new ArrayList<>();
        for (Ad.DataBean bean : data) {
            path.add(bean.getIcon());
        }
        indexBanner.setImages(path)
                .isAutoPlay(true)
                .setImageLoader(new MyLoader())
                .start();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDestroy();
        presenter = null;
    }

    @Override
    public void onSuccess(Ad ad) {
        data = ad.getData();
        tuijian = ad.getTuijian().getList();
        //初始化banner
        initBanner();
        //初始化推荐列表
        initTuijian();
    }

    private void initTuijian() {
        TuiJianAdapter adapter = new TuiJianAdapter(tuijian);
        indexTuijian.setAdapter(adapter);
    }

    @Override
    public void onCatagorySuccess(Catagory catagory) {
        List<Catagory.DataBean> data = catagory.getData();
        //初始化九宫格
        JggAdapter adapter = new JggAdapter(data);
        indexJgg.setAdapter(adapter);
    }

    @OnClick({R.id.mysearch_sao, R.id.mysearch_rl, R.id.mysearch_info})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.mysearch_sao:
                Intent intent = new Intent(getContext(), CaptureActivity.class);
                startActivityForResult(intent, REQUEST_CODE);
                break;
            case R.id.mysearch_rl:
                startActivity(new Intent(getContext(), QueryActivity.class));
                break;
            case R.id.mysearch_info:
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter = null;
        compositeDisposable.clear();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    Toast.makeText(getContext(), "解析结果:" + result, Toast.LENGTH_LONG).show();
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    Toast.makeText(getContext(), "解析二维码失败", Toast.LENGTH_LONG).show();
                }
            }
        }
    }
}
