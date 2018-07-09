package com.lhkj.cgjservice.reponse;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by user on 2018/3/10.
 */

public class JYouYhqResponse extends HttpReponse{

    /**
     * user : {"nickname":"用户20180206","mobile":"18731124090","car_guishu":"3","car_number":"冀3","user_id":"182"}
     * staff : {"account":"18731124090","name":"ssdasfasas","number":"0001","s_id":"14"}
     * order : [{"name":"红红火火恍恍惚惚","money":"120","xuman_price":"100","num":"1","allmoney":120},{"name":"测试一","money":"66","xuman_price":"55","num":"2","allmoney":132}]
     * date : 2018-03-16 10:38:29
     * code : 200
     * sends : {"user_id":"及","jifen":"及","cop_num":"及"}
     */

    @SerializedName("user")
    private UserBean user;
    @SerializedName("staff")
    private StaffBean staff;
    @SerializedName("date")
    private String date;
    @SerializedName("sends")
    private SendsBean sends;
    @SerializedName("order")
    private List<OrderBean> order;

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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public SendsBean getSends() {
        return sends;
    }

    public void setSends(SendsBean sends) {
        this.sends = sends;
    }

    public List<OrderBean> getOrder() {
        return order;
    }

    public void setOrder(List<OrderBean> order) {
        this.order = order;
    }

    public static class UserBean {
        /**
         * nickname : 用户20180206
         * mobile : 18731124090
         * car_guishu : 3
         * car_number : 冀3
         * user_id : 182
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

    public static class SendsBean {
        /**
         * user_id : 及
         * jifen : 及
         * cop_num : 及
         */

        @SerializedName("user_id")
        private String userId;
        @SerializedName("jifen")
        private String jifen;
        @SerializedName("cop_num")
        private String copNum;

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public String getJifen() {
            return jifen;
        }

        public void setJifen(String jifen) {
            this.jifen = jifen;
        }

        public String getCopNum() {
            return copNum;
        }

        public void setCopNum(String copNum) {
            this.copNum = copNum;
        }
    }

    public static class OrderBean {
        /**
         * name : 红红火火恍恍惚惚
         * money : 120
         * xuman_price : 100
         * num : 1
         * allmoney : 120
         */

        @SerializedName("name")
        private String name;
        @SerializedName("money")
        private String money;
        @SerializedName("xuman_price")
        private String xumanPrice;
        @SerializedName("num")
        private String num;
        @SerializedName("allmoney")
        private int allmoney;

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

        public String getXumanPrice() {
            return xumanPrice;
        }

        public void setXumanPrice(String xumanPrice) {
            this.xumanPrice = xumanPrice;
        }

        public String getNum() {
            return num;
        }

        public void setNum(String num) {
            this.num = num;
        }

        public int getAllmoney() {
            return allmoney;
        }

        public void setAllmoney(int allmoney) {
            this.allmoney = allmoney;
        }
    }
}
