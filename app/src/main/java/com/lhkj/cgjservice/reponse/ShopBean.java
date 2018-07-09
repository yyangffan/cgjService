package com.lhkj.cgjservice.reponse;

import java.util.ArrayList;

/**
 * Created by user on 2018/5/14.
 */

public class ShopBean extends HttpReponse {

    public ArrayList<Info> info;

    public class Info {
        public String cx_id;
        public String cx_name;
        public String cx_add_time;

    }
}
