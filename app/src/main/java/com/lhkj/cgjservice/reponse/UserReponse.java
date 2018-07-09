package com.lhkj.cgjservice.reponse;

/**
 * Created by 浩琦 on 2017/7/21.
 */

public class UserReponse extends HttpReponse{
    public Info info;
    public class Info{
        public String admin_id;
        public String s_id;
        public String user_name;
        public String name;
        public String head_url;
        public String token;
        public String new_token;

        @Override
        public String toString() {
            return "Info{" +
                    "admin_id='" + admin_id + '\'' +
                    ", s_id='" + s_id + '\'' +
                    ", user_name='" + user_name + '\'' +
                    ", name='" + name + '\'' +
                    ", head_url='" + head_url + '\'' +
                    ", token='" + token + '\'' +
                    ", new_token='" + new_token + '\'' +
                    '}';
        }
    }
}
