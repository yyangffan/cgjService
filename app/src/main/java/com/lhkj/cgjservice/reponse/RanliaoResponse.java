package com.lhkj.cgjservice.reponse;

import java.util.ArrayList;

/**
 * Created by user on 2018/2/3.
 */

public class RanliaoResponse extends HttpReponse {

    public ArrayList<Info> info;

    public class Info {
        public String id;
        public String you_name;
        public String add_time;
        public String is_del;

    }
}
