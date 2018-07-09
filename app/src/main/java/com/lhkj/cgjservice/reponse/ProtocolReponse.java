package com.lhkj.cgjservice.reponse;

/**
 * Created by 浩琦 on 2017/8/11.
 */

public class ProtocolReponse extends HttpReponse {
    public Info info;
    public class Info{
        public String title;
        public String content;
    }
}
