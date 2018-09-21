package com.bw.movie.util;

import android.content.Context;
import android.net.Uri;
import android.widget.ImageView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.youth.banner.loader.ImageLoader;

/**
 * author:Created by YangYong on 2018/7/18 0018.
 */
public class MyLoader extends ImageLoader {
    @Override
    public ImageView createImageView(Context context) {
        return new SimpleDraweeView(context);
    }

    @Override
    public void displayImage(Context context, Object path, ImageView imageView) {
        imageView.setImageURI(Uri.parse((String) path));
    }
}
