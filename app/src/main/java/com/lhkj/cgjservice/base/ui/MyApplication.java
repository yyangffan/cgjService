package com.lhkj.cgjservice.base.ui;

import android.app.Application;

import com.lhkj.cgjservice.printutiles.AidlUtil;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by user on 2018/1/3.
 */

public class MyApplication extends Application {

    private boolean isAidl;

    public boolean isAidl() {
        return isAidl;
    }

    public void setAidl(boolean aidl) {
        isAidl = aidl;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        isAidl = true;
        AidlUtil.getInstance().connectPrinterService(this);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }
}
