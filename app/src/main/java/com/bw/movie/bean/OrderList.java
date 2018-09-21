package com.bw.movie.bean;

import java.util.List;

/**
 * author:Created by YangYong on 2018/8/20 0020.
 */
public class OrderList {
    /**
     * msg : 请求成功
     * code : 0
     * data : [{"createtime":"2018-08-20T20:04:14","orderid":12313,"price":23600,"status":0,"title":"订单测试标题15937","uid":15937},{"createtime":"2018-08-20T20:04:16","orderid":12314,"price":23600,"status":0,"title":"订单测试标题15937","uid":15937},{"createtime":"2018-08-20T20:04:18","orderid":12315,"price":23600,"status":0,"title":"订单测试标题15937","uid":15937}]
     * page : 3
     */

    private String msg;
    private String code;
    private String page;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * createtime : 2018-08-20T20:04:14
         * orderid : 12313
         * price : 23600.0
         * status : 0
         * title : 订单测试标题15937
         * uid : 15937
         */

        private String createtime;
        private int orderid;
        private double price;
        private int status;
        private String title;
        private int uid;

        public String getCreatetime() {
            return createtime;
        }

        public void setCreatetime(String createtime) {
            this.createtime = createtime;
        }

        public int getOrderid() {
            return orderid;
        }

        public void setOrderid(int orderid) {
            this.orderid = orderid;
        }

        public double getPrice() {
            return price;
        }

        public void setPrice(double price) {
            this.price = price;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public int getUid() {
            return uid;
        }

        public void setUid(int uid) {
            this.uid = uid;
        }
    }
}
