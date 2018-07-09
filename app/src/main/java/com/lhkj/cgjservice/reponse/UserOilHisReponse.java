package com.lhkj.cgjservice.reponse;

import java.util.ArrayList;

/**
 * Created by 浩琦 on 2017/7/21.
 */

public class UserOilHisReponse extends HttpReponse {
    public Info info;
    public class Info{
        public String nickname;
        public String mobile;
        public String head_pic;
        public String points;
        public String type;
        public String money;
        public ArrayList<Jilu> jilu;
        public String bili;
        public String jifen;
        public String balance;

    }
    public class Jilu{
        public String money;
        public String add_time;
        public String id;
        public String oil_type;
    }
}
