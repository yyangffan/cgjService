package com.lhkj.cgjservice.reponse;

/**
 * Created by 浩琦 on 2017/8/11.
 */

public class SignPriceReponse extends HttpReponse {
    public Info info;
    public class Info{
//       id =        该记录id ,使用商品时需传
//    state :      1未使用，2已使用    
//                    send_time :    领取时间
//    use_time :    使用时间
//    type :      1商品奖励（有商品信息 goods_name ,goods_img） ，2积分奖励，无商品信息
//    jifen_num :   积分奖励时奖励的积分
        public String id;
        public String state;
        public String use_time;
        public String type;
        public String jifen_num;
        public String send_time;
    }
}
