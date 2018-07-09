package com.lhkj.cgjservice.reponse;

import java.util.ArrayList;

/**
 * Created by 浩琦 on 2017/7/22.
 */

public class BannerResponse extends HttpReponse{
    public ArrayList<Info> info;
    public class Info{
        public String link;
        public String img_url;
    }


}
