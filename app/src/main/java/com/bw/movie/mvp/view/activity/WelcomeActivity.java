package com.bw.movie.mvp.view.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.bw.movie.R;

public class WelcomeActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView wel_time;
    private TextView wel_break;
    private int time=3;
    private Handler handler =new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(msg.what==1){
                time--;
                wel_time.setText(time+"");
                handler.sendEmptyMessageDelayed(1,1000);
                if (time==0){
                    wel_time.setText(time+"");
                    handler.removeMessages(1);
                    startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
                    finish();
                }
            }
        }

    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        initView();
        wel_break.setOnClickListener(this);
        handler.sendEmptyMessageDelayed(1,1000);
    }

    private void initView() {
        wel_time = (TextView) findViewById(R.id.wel_time);
        wel_break = (TextView) findViewById(R.id.wel_break);
    }

    @Override
    public void onClick(View v) {
        handler.removeMessages(1);
        startActivity(new Intent(WelcomeActivity.this,MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeMessages(1);
    }
}
