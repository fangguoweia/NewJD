package com.bw.movie.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.bw.movie.diyview.AddDeleteView;
import com.bw.movie.R;
import com.bw.movie.bean.Cart;

import java.util.List;

/**
 * author:Created by YangYong on 2018/7/16 0016.
 */
public class CartAdapter extends BaseExpandableListAdapter {
    private List<Cart.DataBean> list;
    private CheckBox seller_check;
    private CheckBox goods_check;
    private Cart.DataBean.ListBean bean;
    private Context context;

    public CartAdapter(List<Cart.DataBean> list) {
        this.list = list;
    }

    @Override
    public int getGroupCount() {
        return list.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return list.get(groupPosition).getList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return list.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return list.get(groupPosition).getList().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(final int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        context = parent.getContext();
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.seller_item, null);
        }
        TextView seller_name = convertView.findViewById(R.id.seller_name);
        seller_check = convertView.findViewById(R.id.seller_check);
        //************************************group点击监听
        seller_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCartListener != null) {
                    onCartListener.onGroupChecked(groupPosition);
                }
            }
        });
        //如果当前group中的所有商品都被选中，那就把group选中
        boolean selectedOnGroup = isSelectedOnGroup(groupPosition);
        seller_check.setChecked(selectedOnGroup);
        seller_name.setText(list.get(groupPosition).getSellerName());
        return convertView;
    }

    private boolean isSelectedOnGroup(int groupPosition) {
        List<Cart.DataBean.ListBean> list = this.list.get(groupPosition).getList();
        for (Cart.DataBean.ListBean bean : list) {
            if (bean.getSelected() == 0) {
                return false;
            }
        }
        return true;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView, final ViewGroup parent) {
        if (convertView == null) {
            convertView = View.inflate(parent.getContext(), R.layout.goods_item, null);
        }
        bean = list.get(groupPosition).getList().get(childPosition);
        SimpleDraweeView goods_img = convertView.findViewById(R.id.goods_img);
        TextView goods_title = convertView.findViewById(R.id.goods_title);
        TextView goods_price = convertView.findViewById(R.id.goods_price);
        goods_check = convertView.findViewById(R.id.goods_check);
        //处理imgurl
        String imgUrl = getImgUrl(bean.getImages());
        goods_img.setImageURI(Uri.parse(imgUrl));
        goods_title.setText(bean.getTitle());
        goods_price.setText("￥"+bean.getBargainPrice());
        //根据集合数据设置复选框选中状态
        goods_check.setChecked(bean.getSelected() == 1);
        //************************************
        goods_check.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onCartListener != null) {
                    onCartListener.onGoodsChecked(groupPosition, childPosition);
                }
            }
        });
        //设置加减器监听
        AddDeleteView goods_ad = convertView.findViewById(R.id.goods_ad);
        goods_ad.setOnAddDeleteViewClick(new AddDeleteView.OnAddDeleteViewClick() {
            @Override
            public void onNumChange(int num) {
                if (onCartListener != null) {
                    Cart.DataBean.ListBean bean = list.get(groupPosition).getList().get(childPosition);
                    onCartListener.onGoodsNumChang(bean.getSellerid(), bean.getPid(), num,bean.getSelected()+"", groupPosition, childPosition);
                }
            }
        });
        //长按删除当前条目
        convertView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Cart.DataBean.ListBean bean = CartAdapter.this.list.get(groupPosition).getList().get(childPosition);
                if (onCartListener!=null){
                    onCartListener.onRemoveGoods(groupPosition,childPosition,bean.getPid()+"");
                }
                return true;
            }
        });
        goods_ad.setSum(bean.getNum());
        return convertView;
    }

    private String getImgUrl(String images) {
        int i = images.indexOf("!");
        return images.substring(0, i);
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    //计算总数
    public int getSum() {
        int sum = 0;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getList().size(); j++) {
                if (list.get(i).getList().get(j).getSelected() == 1) {
                    int num = list.get(i).getList().get(j).getNum();
                    sum += num;
                }
            }
        }
        return sum;
    }

    //结算总价
    public float getTotalPrice() {
        int sum = 0;
        float price = 0f;
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getList().size(); j++) {
                if (list.get(i).getList().get(j).getSelected() == 1) {
                    int num = list.get(i).getList().get(j).getNum();
                    double bargainPrice = list.get(i).getList().get(j).getBargainPrice();
                    price += (float) (num * bargainPrice);
                }
            }
        }
        return price;
    }

    //判断所有商品是都都被选中
    public boolean isAllGoodsSelected() {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getList().size(); j++) {
                if (list.get(i).getList().get(j).getSelected() == 0) {
                    //如果有一个没选中返回false
                    return false;
                }
            }
        }
        return true;
    }

    //改变单个group中所有商品的选中状态
    public void setGoodsCheckedOnGroup(int groupPosition, boolean groupChecked) {
        List<Cart.DataBean.ListBean> list = this.list.get(groupPosition).getList();
        for (Cart.DataBean.ListBean listBean : list) {
            listBean.setSelected(groupChecked ? 1 : 0);
        }
    }

    //判断单个group中所有商品是否都被选中
    public boolean isAllCheckedOnGroup(int groupPosition) {
        List<Cart.DataBean.ListBean> list = this.list.get(groupPosition).getList();
        for (Cart.DataBean.ListBean listBean : list) {
            if (listBean.getSelected() == 0) {
                return false;
            }
        }
        return true;
    }

    //改变集合中所有商品的状态
    public void setAllGoodsChecked(boolean check) {
        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < list.get(i).getList().size(); j++) {
                list.get(i).getList().get(j).setSelected(check ? 1 : 0);
            }
        }
    }

    //改变某个商品的状态
    public void setGoodsChecked(int groupPosition, int childPosition) {
        //***京东未解之谜1.复用bean会造成复选框取消不了
//        list.get(groupPosition).getList().get(childPosition).setSelected(bean.getSelected() == 0 ? 1 : 0);
        Cart.DataBean.ListBean bean = list.get(groupPosition).getList().get(childPosition);
        bean.setSelected(bean.getSelected() == 0 ? 1 : 0);
    }

    //修改某个商品的数量
    public void setGoodsNum(int groupPosition, int childPosition, int num) {
        list.get(groupPosition).getList().get(childPosition).setNum(num);
    }

    //从集合删除某个商品
    public void removeGoods( int groupPosition,  int childPosition){
        list.get(groupPosition).getList().remove(childPosition);
    }
    //判断某个group长度是否为0
    public void setGroup(int groupPosition){
        List<Cart.DataBean.ListBean> listBeans =list.get(groupPosition).getList();
        if(listBeans.size()==0){
            list.remove(groupPosition);
        }
    }
    public interface OnCartListener {
        void onGroupChecked(int groupPosition);

        void onGoodsChecked(int groupPosition, int childPosition);

        void onGoodsNumChang(int sellerid, int pid, int num,String selected, int groupPosition, int childPosition);
        void onRemoveGoods(int groupPosition, int childPosition,String pid);
    }

    private OnCartListener onCartListener;

    public void setOnCartListener(OnCartListener onCartListener) {
        this.onCartListener = onCartListener;
    }
}
