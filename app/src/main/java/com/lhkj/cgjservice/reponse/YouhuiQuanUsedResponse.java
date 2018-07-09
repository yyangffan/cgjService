package com.lhkj.cgjservice.reponse;

import java.util.ArrayList;

/**
 * Created by user on 2018/2/10.
 */

public class YouhuiQuanUsedResponse extends HttpReponse{
    public ArrayList<Info> info;

    public class Info {
        public String use_time;
        public String id;
        public String coupon_id;
        public String name;
        public String cate_name;
    }
}
