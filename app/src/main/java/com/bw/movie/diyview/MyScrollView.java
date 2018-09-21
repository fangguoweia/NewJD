package com.bw.movie.diyview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ScrollView;

/**
 * author:Created by YangYong on 2018/7/23 0023.
 */
public class MyScrollView extends ScrollView {
    public MyScrollView(Context context) {
        this(context,null,0);
    }

    public MyScrollView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        if (onScrollChanged!=null){
            onScrollChanged.onScrollChanged(l, t, oldl, oldt);
        }
    }
    public interface OnScrollChanged{
        void onScrollChanged(int x, int y, int oldx, int oldy);
    }
    private OnScrollChanged onScrollChanged;

    public void setOnScrollChanged(OnScrollChanged onScrollChanged) {
        this.onScrollChanged = onScrollChanged;
    }
}
