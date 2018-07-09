package com.lhkj.cgjservice.reponse;

import java.util.ArrayList;

/**
 * Created by user on 2017/12/12.
 */

public class YouHuiQuanResponse extends HttpReponse {
    public String allfo;//面额总值
    public ArrayList<Info> info;
    public Staff staff;
    public class Info{
        public String id;
        public String nickname;
        public String c_name;
        public String add_time;
        public String money;
        public String cate_name;
        public String mobile;
    }

    public class Staff{
        public String account;
        public String name;
        public String number;

    }
     /*"id":"43",
      "s_id":"14",
      "coupon_id":"29",
      "add_time":"1513043978",
      "user_id":"179",
      "nickname":null,
      "mobile":null,
      "s_name":"靳鹏",
      "account":"18910181488",
      "number":"0001",
      "c_name":"满10000减200",
      "money":"200",
     "cate_name":"加油券"
            */


}
