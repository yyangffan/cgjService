package com.lhkj.cgjservice.reponse;

import java.util.ArrayList;

/**
 * Created by 浩琦 on 2017/7/17.
 */

public class CouponListResponse extends HttpReponse {
    public ArrayList<Info> info;
    public class Info{
        public String state_id;
        public String state;
        public String name;
        public String use_end_time;
        public String money;
        public String coupon_id;
        public String xuman_price;
        public String exps;
    }
}
