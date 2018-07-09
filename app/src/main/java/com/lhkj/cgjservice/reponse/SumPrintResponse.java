package com.lhkj.cgjservice.reponse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2018/1/3.
 */

public class SumPrintResponse extends HttpReponse {


    /**
     * code : 200
     * info : 1
     * staff : {"account":"15200015653","name":"阿拉蕾","number":"1"}
     * user : {"mobile":"15614196807","nickname":"用户20171011156"}
     * order : {"jifen":"300","money":"0.00","allmoney":300,"confirm_time":"1970-01-01 08:00:00","goods_name":"0℃ 汽车玻璃水 -25℃ 雨刮水 车用前挡风玻璃液 2升 雨刮液防冻玻璃水剂清洗剂 0度夏季玻璃水【买4送1】"}
     */

    @SerializedName("info")
    private int info;
    @SerializedName("staff")
    private StaffBean staff;
    @SerializedName("user")
    private UserBean user;
    @SerializedName("order")
    private OrderBean order;

    public int getInfo() {
        return info;
    }

    public void setInfo(int info) {
        this.info = info;
    }

    public StaffBean getStaff() {
        return staff;
    }

    public void setStaff(StaffBean staff) {
        this.staff = staff;
    }

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

    public static class StaffBean {
        /**
         * account : 15200015653
         * name : 阿拉蕾
         * number : 1
         */

        @SerializedName("account")
        private String account;
        @SerializedName("name")
        private String name;
        @SerializedName("number")
        private String number;

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
    }

    public static class UserBean {
        /**
         * mobile : 15614196807
         * nickname : 用户20171011156
         */

        @SerializedName("mobile")
        private String mobile;
        @SerializedName("nickname")
        private String nickname;
        @SerializedName("car_number")
        private String car_number;

        public String getCar_number() {
            return car_number;
        }

        public void setCar_number(String car_number) {
            this.car_number = car_number;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getNickname() {
            return nickname;
        }

        public void setNickname(String nickname) {
            this.nickname = nickname;
        }
    }

    public static class OrderBean {
        /**
         * jifen : 300
         * money : 0.00
         * allmoney : 300
         * confirm_time : 1970-01-01 08:00:00
         * goods_name : 0℃ 汽车玻璃水 -25℃ 雨刮水 车用前挡风玻璃液 2升 雨刮液防冻玻璃水剂清洗剂 0度夏季玻璃水【买4送1】
         */

        @SerializedName("jifen")
        private String jifen;
        @SerializedName("money")
        private String money;
        @SerializedName("allmoney")
        private int allmoney;
        @SerializedName("confirm_time")
        private String confirmTime;
        @SerializedName("goods_name")
        private String goodsName;

        public String getJifen() {
            return jifen;
        }

        public void setJifen(String jifen) {
            this.jifen = jifen;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public int getAllmoney() {
            return allmoney;
        }

        public void setAllmoney(int allmoney) {
            this.allmoney = allmoney;
        }

        public String getConfirmTime() {
            return confirmTime;
        }

        public void setConfirmTime(String confirmTime) {
            this.confirmTime = confirmTime;
        }

        public String getGoodsName() {
            return goodsName;
        }

        public void setGoodsName(String goodsName) {
            this.goodsName = goodsName;
        }
    }
}
