package com.lhkj.cgjservice.reponse;

import java.util.ArrayList;

/**
 * Created by 浩琦 on 2017/7/21.
 */

public class OilHisResponse extends HttpReponse {
    public ArrayList<Info> info;
    public class Info{
        public String money;
        public String jifen;
        public String add_time;
        public String nickname;
        public String mobile;
    }
}
