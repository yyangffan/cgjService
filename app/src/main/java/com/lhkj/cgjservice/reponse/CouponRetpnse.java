package com.lhkj.cgjservice.reponse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2018/1/2.
 */

public class CouponRetpnse extends HttpReponse {

    /**
     * code : 200
     * user : {"mobile":"15614196807","nickname":"用户20171011156"}
     * staff : {"account":"15200015653","name":"阿拉蕾","number":"1"}
     * coupon : {"coupon_name":null,"money":null,"num":1}
     * date : 2018-01-02 18:18:22
     * success : 使用成功
     */

    @SerializedName("user")
    private UserBean user;
    @SerializedName("staff")
    private StaffBean staff;
    @SerializedName("coupon")
    private CouponBean coupon;
    @SerializedName("date")
    private String date;
    @SerializedName("success")
    private String success;


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

    public CouponBean getCoupon() {
        return coupon;
    }

    public void setCoupon(CouponBean coupon) {
        this.coupon = coupon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
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

    public static class CouponBean {
        /**
         * coupon_name : null
         * money : null
         * num : 1
         */

        @SerializedName("coupon_name")
        private Object couponName;
        @SerializedName("money")
        private Object money;
        @SerializedName("num")
        private int num;

        public Object getCouponName() {
            return couponName;
        }

        public void setCouponName(Object couponName) {
            this.couponName = couponName;
        }

        public Object getMoney() {
            return money;
        }

        public void setMoney(Object money) {
            this.money = money;
        }

        public int getNum() {
            return num;
        }

        public void setNum(int num) {
            this.num = num;
        }
    }
}
