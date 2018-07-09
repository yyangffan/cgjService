package com.lhkj.cgjservice.reponse;

import java.util.ArrayList;

/**
 * Created by user on 2018/2/9.
 */

public class YouhuiQuanReponse extends HttpReponse {
    public ArrayList<Info> info;

    public class Info {
        public String coupon_id;
        public String copnum;
        public String name;
        public String xuman_price;
        public String money;
    }
}
