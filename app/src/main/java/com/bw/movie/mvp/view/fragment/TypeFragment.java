package com.bw.movie.mvp.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.Toast;

import com.bw.movie.R;
import com.bw.movie.adapter.CatagoryAdapter;
import com.bw.movie.adapter.ExAdapter;
import com.bw.movie.bean.Catagory;
import com.bw.movie.bean.ProductCatagory;
import com.bw.movie.mvp.presenter.TypePresenter;
import com.bw.movie.mvp.view.TypeView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * author:Created by YangYong on 2018/7/16 0016.
 */
public class TypeFragment extends Fragment implements TypeView {
    @BindView(R.id.type_Catagory)
    RecyclerView typeCatagory;
    @BindView(R.id.type_exl)
    ExpandableListView typeExl;
    Unbinder unbinder;
    private TypePresenter presenter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getActivity(), R.layout.type_fg, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        presenter = new TypePresenter(this);
        //初始化分类
        initCatagory();
    }

    private void initCatagory() {
        typeCatagory.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        presenter.getCatagory();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        presenter.onDestroy();
        presenter = null;
    }

    @Override
    public void onCatagorySuccess(Catagory catagory) {

        List<Catagory.DataBean> data = catagory.getData();
        CatagoryAdapter adapter = new CatagoryAdapter(data);
        typeCatagory.setAdapter(adapter);
        //默认请求cid=1的数据
        presenter.getProductCatagory("1");
        //分类点击回调
        adapter.setOnCatagoryLisenter(new CatagoryAdapter.OnCatagoryLisenter() {
            @Override
            public void onNameClick(int cid) {
                //调用p层请求子分类
                presenter.getProductCatagory(cid + "");
            }
        });
    }

    @Override
    public void onCatagoryFaild() {
        Toast.makeText(getActivity(), "获得数据失败", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onProductCatagorySuccess(ProductCatagory productCatagory) {
        if (productCatagory.getData().size() == 0) {
            Toast.makeText(getActivity(), "该分类暂时没有商品", Toast.LENGTH_SHORT).show();
        } else {
            List<ProductCatagory.DataBean> data = productCatagory.getData();
            ExAdapter adapter = new ExAdapter(data);
            typeExl.setAdapter(adapter);
            //默认展开所有一级item
            for (int i = 0; i < data.size(); i++) {
                typeExl.expandGroup(i);
            }
        }
    }
}
