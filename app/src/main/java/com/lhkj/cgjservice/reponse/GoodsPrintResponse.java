package com.lhkj.cgjservice.reponse;

import com.google.gson.annotations.SerializedName;

/**
 * 我的订单商品兑换打印数据
 */

public class GoodsPrintResponse extends HttpReponse {

    /**
     * code : 200
     * user : {"nickname":"用户20180206","mobile":"18731124090","car_guishu":3,"car_number":"冀646949","user_id":182}
     * order : {"order_sn":"H15120619874","user_id":182,"order_price":"16.00","goods_id":195,"jifen":10,"money":"16.00","add_time":"2017-09-29 15:12:06","goods_name":"测试商品"}
     * staff : {"account":"15614196807","name":"123123","number":"0071","s_id":1}
     */

    @SerializedName("user")
    private UserBean user;
    @SerializedName("order")
    private OrderBean order;
    @SerializedName("staff")
    private StaffBean staff;

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public StaffBean getStaff() {
        return staff;
    }

    public void setStaff(StaffBean staff) {
        this.staff = staff;
    }

    public static class UserBean {
        /**
         * nickname : 用户20180206
         * mobile : 18731124090
         * car_guishu : 3
         * car_number : 冀646949
         * user_id : 182
         */

        @SerializedName("nickname")
        private String nickname;
        @SerializedName("mobile")
        private String mobile;
        @SerializedName("car_guishu")
        private int carGuishu;
        @SerializedName("car_number")
        private String carNumber;
        @SerializedName("user_id")
        private int userId;

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public int getCarGuishu() {
            return carGuishu;
        }

        public void setCarGuishu(int carGuishu) {
            this.carGuishu = carGuishu;
        }

        public String getCarNumber() {
            return carNumber;
        }

        public void setCarNumber(String carNumber) {
            this.carNumber = carNumber;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }
    }

    public static class OrderBean {
        /**
         * order_sn : H15120619874
         * user_id : 182
         * order_price : 16.00
         * goods_id : 195
         * jifen : 10
         * money : 16.00
         * add_time : 2017-09-29 15:12:06
         * goods_name : 测试商品
         */

        @SerializedName("order_sn")
        private String orderSn;
        @SerializedName("user_id")
        private int userId;
        @SerializedName("order_price")
        private String orderPrice;
        @SerializedName("goods_id")
        private int goodsId;
        @SerializedName("jifen")
        private int jifen;
        @SerializedName("money")
        private String money;
        @SerializedName("add_time")
        private String addTime;
        @SerializedName("goods_name")
        private String goodsName;

        public String getOrderSn() {
            return orderSn;
        }

        public void setOrderSn(String orderSn) {
            this.orderSn = orderSn;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getOrderPrice() {
            return orderPrice;
        }

        public void setOrderPrice(String orderPrice) {
            this.orderPrice = orderPrice;
        }

        public int getGoodsId() {
            return goodsId;
        }

        public void setGoodsId(int goodsId) {
            this.goodsId = goodsId;
        }

        public int getJifen() {
            return jifen;
        }

        public void setJifen(int jifen) {
            this.jifen = jifen;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getAddTime() {
            return addTime;
        }

        public void setAddTime(String addTime) {
            this.addTime = addTime;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }
    }

    public static class StaffBean {
        /**
         * account : 15614196807
         * name : 123123
         * number : 0071
         * s_id : 1
         */

        @SerializedName("account")
        private String account;
        @SerializedName("name")
        private String name;
        @SerializedName("number")
        private String number;
        @SerializedName("s_id")
        private int sId;

        public String getAccount() {
            return account;
        }

        public void setAccount(String account) {
            this.account = account;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public int getSId() {
            return sId;
        }

        public void setSId(int sId) {
            this.sId = sId;
        }
    }
}
