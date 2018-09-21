package com.bw.movie.diyview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * author:Created by YangYong on 2018/7/16 0016.
 */
public class MyGridView extends GridView {
    public MyGridView(Context context) {
        this(context, null, 0);
    }

    public MyGridView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MyGridView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        //重新计算高度
        int newHeight = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, newHeight);
    }
}
