package com.lhkj.cgjservice.lock;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.lhkj.cgjservice.HttpRequset.HttpCallListener;
import com.lhkj.cgjservice.HttpRequset.HttpUtil;
import com.lhkj.cgjservice.bean.SetJPushAlias;
import com.lhkj.cgjservice.databinding.ActivitySetBinding;
import com.lhkj.cgjservice.entity.Operation;
import com.lhkj.cgjservice.entity.User;
import com.lhkj.cgjservice.reponse.HttpReponse;
import com.lhkj.cgjservice.ui.Login.LoginActivity;
import com.lhkj.cgjservice.ui.other.AboutActivity;
import com.lhkj.cgjservice.ui.other.SetActivity;
import com.lhkj.cgjservice.ui.work.SumPrintActivity;
import com.lhkj.cgjservice.utils.SharedPreferencesUtil;

import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/6/20.
 */

public class SetLock {
    private Context context;
    private ActivitySetBinding setBinding;
    private final boolean mIsOil;
    private final boolean mIsSP;

    public SetLock(Context context, ActivitySetBinding setBinding) {
        this.context = context;
        this.setBinding = setBinding;
        mIsOil = SharedPreferencesUtil.getSharePreBoolean(context, "isOil", true);
        setBinding.switchOil.setChecked(mIsOil);
        mIsSP = SharedPreferencesUtil.getSharePreBoolean(context, "isSp", true);
        setBinding.switchShangpin.setChecked(mIsSP);
        setBinding.mineBanben.setText(getAppVersionName(context).equals("") ? "1.0" : getAppVersionName(context));
    }

//    public void sendMsg() {
//        if (setBinding.togButton.isChecked()) {
//            setBinding.togButton.setChecked(true);
//        } else {
//            setBinding.togButton.setChecked(false);
//        }
//    }

    public void cleanCache() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Glide.get(context).clearDiskCache();
            }
        }).start();
        Toast.makeText(context, "清理完成", Toast.LENGTH_SHORT).show();
    }

    public void about() {
        ((SetActivity) context).startActivity(AboutActivity.class);
    }

    public void edition() {
    }

    public void exit() {
        outOfWork();
//        User.getUser().clearUser();
//        ((SetActivity) context).finish();
//        ((SetActivity) context).startActivity(LoginActivity.class);
    }

    /*是否自动打印加油券小票*/
    public void changeOilPrint() {
        if (SharedPreferencesUtil.getSharePreBoolean(context, "isOil", true)) {
            SharedPreferencesUtil.saveSharePreBoolean(context, "isOil", false);
            setBinding.switchOil.setChecked(false);
        } else {
            SharedPreferencesUtil.saveSharePreBoolean(context, "isOil", true);
            setBinding.switchOil.setChecked(true);
        }
    }

    /*是否自动打印商品小票*/
    public void changeShangPinPring() {
        if (SharedPreferencesUtil.getSharePreBoolean(context, "isSp", true)) {
            SharedPreferencesUtil.saveSharePreBoolean(context, "isSp", false);
            setBinding.switchShangpin.setChecked(false);
        } else {
            SharedPreferencesUtil.saveSharePreBoolean(context, "isSp", true);
            setBinding.switchShangpin.setChecked(true);
        }
    }

    /**
     * 2.  * 返回当前程序版本名
     * 3.
     */
    public static String getAppVersionName(Context context) {
        String versionName = "";
        int versioncode = 0;
        try {
            // ---get the package info---
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            versionName = pi.versionName;
            versioncode = pi.versionCode;
            if (versionName == null || versionName.length() <= 0) {
                return "";
            }
        } catch (Exception e) {
            Log.e("VersionInfo", "Exception", e);
        }
        return versionName;
    }


    /*跳转到打印汇总界面*/
    public void goToPrint() {
        context.startActivity(new Intent(context, SumPrintActivity.class));
    }


    public void outOfWork() {
        HashMap hashMap = new HashMap();
        hashMap.put("s_id", User.getUser().sId);
        hashMap.put("type", "2");
        HttpUtil.getInstance().postRequest(Operation.GOWORK, hashMap, HttpReponse.class, new HttpCallListener<HttpReponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final HttpReponse bean) {
                if (bean.code.equals("200")) {
                    Log.d("下班", "成功下班");

                } else {
                    Log.d("下班", "下班失败");
                }
            }

            @Override
            public void Error(String URL) {
                Log.d("下班", "下班错误");
            }
        });
        new SetJPushAlias("", context).cancleAlias();
        User.getUser().clearUser();
        ((SetActivity) context).finish();
        ((SetActivity) context).startActivity(LoginActivity.class);
    }
}
