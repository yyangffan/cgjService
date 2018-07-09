package com.lhkj.cgjservice.reponse;

import com.google.gson.annotations.SerializedName;

/**
 * Created by user on 2018/3/2.
 */

public class YhqAginReponse extends HttpReponse{

    /**
     * user : {"nickname":"用户20171122167","mobile":"13785135527","car_guishu":"3","car_number":"冀123456","user_id":"146"}
     * staff : {"account":"18731124090","name":"ssdasfasas","number":"0001","s_id":"14"}
     * order : {"name":"满10000减200","money":"200","coupon_id":"29"}
     * date : 2017-12-12 09:59:38
     * success : 数据返回
     */

    @SerializedName("user")
    private UserBean user;
    @SerializedName("staff")
    private StaffBean staff;
    @SerializedName("order")
    private OrderBean order;
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

    public OrderBean getOrder() {
        return order;
    }

    public void setOrder(OrderBean order) {
        this.order = order;
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
         * nickname : 用户20171122167
         * mobile : 13785135527
         * car_guishu : 3
         * car_number : 冀123456
         * user_id : 146
         */

        @SerializedName("nickname")
        private String nickname;
        @SerializedName("mobile")
        private String mobile;
        @SerializedName("car_guishu")
        private String carGuishu;
        @SerializedName("car_number")
        private String carNumber;
        @SerializedName("user_id")
        private String userId;

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

        public String getCarGuishu() {
            return carGuishu;
        }

        public void setCarGuishu(String carGuishu) {
            this.carGuishu = carGuishu;
        }

        public String getCarNumber() {
            return carNumber;
        }

        public void setCarNumber(String carNumber) {
            this.carNumber = carNumber;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }
    }

    public static class StaffBean {
        /**
         * account : 18731124090
         * name : ssdasfasas
         * number : 0001
         * s_id : 14
         */

        @SerializedName("account")
        private String account;
        @SerializedName("name")
        private String name;
        @SerializedName("number")
        private String number;
        @SerializedName("s_id")
        private String sId;

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

        public String getSId() {
            return sId;
        }

        public void setSId(String sId) {
            this.sId = sId;
        }
    }

    public static class OrderBean {
        /**
         * name : 满10000减200
         * money : 200
         * coupon_id : 29
         */

        @SerializedName("name")
        private String name;
        @SerializedName("money")
        private String money;
        @SerializedName("coupon_id")
        private String couponId;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getCouponId() {
            return couponId;
        }

        public void setCouponId(String couponId) {
            this.couponId = couponId;
        }
    }
}
