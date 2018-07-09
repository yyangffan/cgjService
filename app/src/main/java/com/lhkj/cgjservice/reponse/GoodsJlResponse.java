package com.lhkj.cgjservice.reponse;

import java.util.ArrayList;

/**
 * Created by user on 2017/12/12.
 */

public class GoodsJlResponse extends HttpReponse {
    public String all_shop;//商品总额
    public String all_excha;//积分总额
    public ArrayList<Info> info;
    public class Info{
        public String id;
        public String nickname;
        public String goods_name;
        public String shop_price;
        public String time;
        public String exchange_integral;
        public String mobile;
        public String allfo;
    }
    /*    "id":"83",
            "uid":"146",
            "type":"1",
            "goods_id":"193",
            "jifen":null,
            "time":"1508060065",
            "s_id":"14",
            "nickname":null,
            "mobile":null,
            "s_name":"靳鹏",
            "number":"0001",
            "account":"18910181488",
            "goods_name":"古代的宫女穿的鞋子，“”三寸金莲“”，复古风一双非常华贵的鞋子",
            "shop_type":"2",
            "shop_price":"350.00",
            "exchange_integral":"40"*/
}
