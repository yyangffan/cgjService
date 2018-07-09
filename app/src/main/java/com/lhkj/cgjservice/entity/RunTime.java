package com.lhkj.cgjservice.entity;

import android.app.Application;

import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/6/21.
 */

public class RunTime {
    public static final int CODE_TEXT = 1000;
    //    public static final String BASEURL="http://yph.177678.top/";
//    public static final String BASEURL = "http://yph.linghangnc.com/";
    public static final String BASEURL = "http://www.hbbfjt.top/";
    private static HashMap<Integer, Object> runTime = new HashMap<>();
    public static Application application;

    public static Object getRunTime(Integer key) {
        return runTime.get(key);
    }

    public static void setData(Integer key, Object values) {
        runTime.put(key, values);
    }
}
