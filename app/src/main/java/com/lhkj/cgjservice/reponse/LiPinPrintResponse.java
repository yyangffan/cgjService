package com.lhkj.cgjservice.reponse;

import com.google.gson.annotations.SerializedName;

/**
 * 领取记录礼品打印返回数据
 */

public class LiPinPrintResponse extends HttpReponse {

    /**
     * code : 200
     * success : 返回成功
     * order : {"goods_sn":"apple","goods_name":"Apple iPhone 6 32GB 金色 移动联通电信4G手机 【818手机狂欢节】iPhone为","shop_price":"10000.00","date":"2018-03-10 22:01:34"}
     * user : {"nickname":"用户20180206","mobile":"18731124090","car_guishu":3,"car_number":"冀646949","user_id":182}
     * staff : {"account":"15614196807","name":"123123","number":"0071","s_id":1}
     */

    @SerializedName("success")
    private String success;
    @SerializedName("order")
    private OrderBean order;
    @SerializedName("user")
    private UserBean user;
    @SerializedName("staff")
    private StaffBean staff;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public StaffBean getStaff() {
        return staff;
    }

    public void setStaff(StaffBean staff) {
        this.staff = staff;
    }

    public static class OrderBean {
        /**
         * goods_sn : apple
         * goods_name : Apple iPhone 6 32GB 金色 移动联通电信4G手机 【818手机狂欢节】iPhone为
         * shop_price : 10000.00
         * date : 2018-03-10 22:01:34
         */

        @SerializedName("goods_sn")
        private String goodsSn;
        @SerializedName("goods_name")
        private String goodsName;
        @SerializedName("shop_price")
        private String shopPrice;
        @SerializedName("date")
        private String date;

        public String getGoodsSn() {
            return goodsSn;
        }

        public void setGoodsSn(String goodsSn) {
            this.goodsSn = goodsSn;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }

        public String getShopPrice() {
            return shopPrice;
        }

        public void setShopPrice(String shopPrice) {
            this.shopPrice = shopPrice;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
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
