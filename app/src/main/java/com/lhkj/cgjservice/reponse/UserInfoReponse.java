package com.lhkj.cgjservice.reponse;


import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * 创建日期:2017/9/19 on 17:54
 * 描述:
 * 作者:郭士超
 * QQ:1169380200
 */

public class UserInfoReponse extends HttpReponse {

    /**
     * code : 200
     * info : {"nickname":"用户在线播放的音乐风格流行男鞋","mobile":"18813139617","head_pic":"http://yph.linghangnc.com/uploads/Head_pic/2017-09-06/59afb9f8ce2e9.jpg","bind_oil":"13","user_id":"139","jifen":"1","money":"1","bili":"10","type":"1","jilu":[{"id":"72","money":"200","jifen":"20","add_time":"1504693294"},{"id":"71","money":"200","jifen":"20","add_time":"1504693289"},{"id":"70","money":"200","jifen":"20","add_time":"1504693281"},{"id":"69","money":"30","jifen":"3","add_time":"1504692630"},{"id":"68","money":"3","jifen":"0","add_time":"1504692401"},{"id":"67","money":"30","jifen":"3","add_time":"1504691703"},{"id":"66","money":"20","jifen":"2","add_time":"1504691677"},{"id":"65","money":"3","jifen":"0","add_time":"1504691630"},{"id":"64","money":"5","jifen":"0","add_time":"1504691499"},{"id":"63","money":"3","jifen":"0","add_time":"1504690578"},{"id":"62","money":"60","jifen":"6","add_time":"1504690532"},{"id":"61","money":"80","jifen":"8","add_time":"1504690409"},{"id":"60","money":"1","jifen":"0","add_time":"1504689224"},{"id":"59","money":"2","jifen":"0","add_time":"1504689180"},{"id":"58","money":"100","jifen":"10","add_time":"1504687016"},{"id":"57","money":"50","jifen":"5","add_time":"1504684840"},{"id":"56","money":"100","jifen":"10","add_time":"1504684713"},{"id":"55","money":"100","jifen":"10","add_time":"1504684641"},{"id":"54","money":"100","jifen":"10","add_time":"1504684549"}],"balance":"137"}
     * message : 操作成功
     */

    private InfoBean info;

    public InfoBean getInfo() {
        return info;
    }

    public void setInfo(InfoBean info) {
        this.info = info;
    }

    public static class InfoBean {
        /**
         * nickname : 用户在线播放的音乐风格流行男鞋
         * mobile : 18813139617
         * head_pic : http://yph.linghangnc.com/uploads/Head_pic/2017-09-06/59afb9f8ce2e9.jpg
         * bind_oil : 13
         * user_id : 139
         * jifen : 1
         * money : 1
         * bili : 10
         * type : 1
         * jilu : [{"id":"72","money":"200","jifen":"20","add_time":"1504693294"},{"id":"71","money":"200","jifen":"20","add_time":"1504693289"},{"id":"70","money":"200","jifen":"20","add_time":"1504693281"},{"id":"69","money":"30","jifen":"3","add_time":"1504692630"},{"id":"68","money":"3","jifen":"0","add_time":"1504692401"},{"id":"67","money":"30","jifen":"3","add_time":"1504691703"},{"id":"66","money":"20","jifen":"2","add_time":"1504691677"},{"id":"65","money":"3","jifen":"0","add_time":"1504691630"},{"id":"64","money":"5","jifen":"0","add_time":"1504691499"},{"id":"63","money":"3","jifen":"0","add_time":"1504690578"},{"id":"62","money":"60","jifen":"6","add_time":"1504690532"},{"id":"61","money":"80","jifen":"8","add_time":"1504690409"},{"id":"60","money":"1","jifen":"0","add_time":"1504689224"},{"id":"59","money":"2","jifen":"0","add_time":"1504689180"},{"id":"58","money":"100","jifen":"10","add_time":"1504687016"},{"id":"57","money":"50","jifen":"5","add_time":"1504684840"},{"id":"56","money":"100","jifen":"10","add_time":"1504684713"},{"id":"55","money":"100","jifen":"10","add_time":"1504684641"},{"id":"54","money":"100","jifen":"10","add_time":"1504684549"}]
         * balance : 137
         */

        private String nickname;
        private String mobile;
        private String head_pic;
        private String bind_oil;
        private String user_id;
        private String jifen;
        private String money;
        private String bili;
        private String type;
        private String balance;
        private List<JiluBean> jilu;

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

        public String getHead_pic() {
            return head_pic;
        }

        public void setHead_pic(String head_pic) {
            this.head_pic = head_pic;
        }

        public String getBind_oil() {
            return bind_oil;
        }

        public void setBind_oil(String bind_oil) {
            this.bind_oil = bind_oil;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

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

        public String getBili() {
            return bili;
        }

        public void setBili(String bili) {
            this.bili = bili;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getBalance() {
            return balance;
        }

        public void setBalance(String balance) {
            this.balance = balance;
        }

        public List<JiluBean> getJilu() {
            return jilu;
        }

        public void setJilu(List<JiluBean> jilu) {
            this.jilu = jilu;
        }

        public static class JiluBean {
            /**
             * id : 72
             * money : 200
             * jifen : 20
             * add_time : 1504693294
             */

            private String id;
            private String money;
            private String jifen;
            private String add_time;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getMoney() {
                return money;
            }

            public void setMoney(String money) {
                this.money = money;
            }

            public String getJifen() {
                return jifen;
            }

            public void setJifen(String jifen) {
                this.jifen = jifen;
            }

            public String getAdd_time() {
                return add_time;
            }

            public void setAdd_time(String add_time) {
                this.add_time = add_time;
            }
        }
    }
}
