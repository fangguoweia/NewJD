package com.bw.movie.mvp.view;

import com.bw.movie.base.IView;
import com.bw.movie.bean.QueryGoods;

/**
 * author:Created by YangYong on 2018/7/23 0023.
 */
public interface QueryGoodsView extends IView {
    void onQueryGoodsSuccess(QueryGoods queryGoods);
}
