package com.bw.movie;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.uuzuche.lib_zxing.activity.ZXingLibrary;

/**
 * author:Created by YangYong on 2018/7/17 0017.
 */
public class MApp extends Application {

    public static MApp mApp;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        Fresco.initialize(this);
        ZXingLibrary.initDisplayOpinion(this);
    }

    public static Context getAppContext() {
        return mApp;
    }
}
