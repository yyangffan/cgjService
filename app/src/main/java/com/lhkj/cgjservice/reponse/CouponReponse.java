package com.lhkj.cgjservice.reponse;

/**
 * Created by 浩琦 on 2017/7/22.
 */

public class CouponReponse extends HttpReponse {
    public Info info;
    public class Info{
        public String coupon_id;
        public String name;
        public String money;
        public String use_end_time;
    }
}
