package com.lhkj.cgjservice.HttpRequset;

import com.google.gson.Gson;

/**
 * Created by Administrator on 2017/2/5.
 */

public class JsonForm<T> {
    public static <T> T formJson(String json, Class<T> tClass) {
        Gson gson = new Gson();
        return gson.fromJson(json, tClass);
    }
}
