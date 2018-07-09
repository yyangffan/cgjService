package com.lhkj.cgjservice.reponse;

import java.util.ArrayList;

/**
 * Created by 浩琦 on 2017/8/11.
 */

public class ExchaneDetailReponse extends HttpReponse {
    public Info info;
    public class Info{
        public String order_id;
        public String order_sn;
        public String nickname;
        public String order_price ;
        public String jifen;
        public String pay_status;
        public String is_hot ;
        public String goods_price;
        public String goods_name;
        public String exchange_integral;
        public String shop_type ;
        public String jifen_lv;
        public String type;
        public ArrayList<String> images;
    }
}
