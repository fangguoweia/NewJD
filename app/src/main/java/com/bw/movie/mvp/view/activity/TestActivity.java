package com.bw.movie.mvp.view.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.LinearLayout;

import com.facebook.drawee.view.SimpleDraweeView;
import com.bw.movie.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TestActivity extends AppCompatActivity {

    @BindView(R.id.test_ll)
    LinearLayout testLl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.test_create)
    public void onViewClicked() {
        SimpleDraweeView simpleDraweeView = new SimpleDraweeView(this);
        simpleDraweeView.setImageURI(Uri.parse("https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1534695647192&di=21cadb044a678c8160bf1a2e1cea829a&imgtype=0&src=http%3A%2F%2F5b0988e595225.cdn.sohucs.com%2Fimages%2F20180429%2F43d1a4f2d8b74a5ea6d29d5479541cbf.jpg"));
        testLl.addView(simpleDraweeView);
        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) simpleDraweeView.getLayoutParams();
        layoutParams.width = 100*3;
        layoutParams.height = 150*3;
        simpleDraweeView.setLayoutParams(layoutParams);

//        TextView textView = new TextView(this);
//        textView.setText("中华人民共和国");
//        textView.setBackgroundColor(Color.parseColor("#F23030"));
//        testLl.addView(textView);
//        LinearLayout.LayoutParams params= (LinearLayout.LayoutParams) textView.getLayoutParams();
//        params.width=200;
//        params.height=200;
//        textView.setLayoutParams(params);


    }
}
