package com.lhkj.cgjservice.entity;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.lhkj.cgjservice.HttpRequset.HttpCallListener;
import com.lhkj.cgjservice.HttpRequset.HttpUtil;
import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.reponse.UserReponse;
import com.lhkj.cgjservice.utils.SharedPreferencesUtil;

import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/6/20.
 */

public class User {
    private static User user = new User();
    public String adminId = "";
    public String userName = "";
    //    public String oilName = "";
    public String sId = "";
    public Drawable userIcon;
    public boolean isLogin;

    public final String SAVE_NAME = "userName";
    public final String SAVE_PWD = "userPwd";
    public static final String OLD_TOKEN="OLDTOKEN";
    public UserReponse bean;

    public String token;
    public String new_token;

    private User() {

    }

    public static User getUser() {
        if (user == null) {
            user = new User();
        }
        return user;
    }

    public UserReponse getBean() {
        return bean;
    }



    public void tryLogin(final Activity activity, String name, String pwd, final AuthorizationListener listener) {
        HashMap hashMap = new HashMap();
        hashMap.put("user_name", name);
        hashMap.put("password", pwd);
        HttpUtil.getInstance().postRequest(Operation.LOGIN, hashMap, UserReponse.class, new HttpCallListener<UserReponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final UserReponse bean) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initUser(bean);
                        listener.authorization(isLogin);
                    }
                });

            }

            @Override
            public void Error(String URL) {
                listener.authorization(isLogin);
            }
        });

    }

    public void tryLoginTwo(final Activity activity, String name, String pwd, final AuthorizationListener listener) {
        HashMap hashMap = new HashMap();
        hashMap.put("user_name", name);
        hashMap.put("password", pwd);
        HttpUtil.getInstance().postRequest(Operation.LOGIN_TWO, hashMap, UserReponse.class, new HttpCallListener<UserReponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final UserReponse bean) {
                activity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        initUserTwo(bean);
                        listener.authorization(isLogin);
                    }
                });

            }

            @Override
            public void Error(String URL) {
                listener.authorization(isLogin);
            }
        });

    }

    public void trySign(String name, String code, String pwd, String repwd, final AuthorizationListener listener) {

    }

    public void tryForget(String name, String code, String pwd, String repwd, final AuthorizationListener listener) {

    }

    public void trySendCode(String name, final AuthorizationListener listener) {

    }

    public void clearUser() {
        isLogin = false;
        sId = "";
        adminId = "";
        userName = "";
//        oilName="";
        userIcon = RunTime.application.getResources().getDrawable(R.mipmap.def_usericon);
        SharedPreferencesUtil.saveSharePreString(RunTime.application, SAVE_NAME, "");
        SharedPreferencesUtil.saveSharePreString(RunTime.application, SAVE_PWD, "");
        SharedPreferencesUtil.saveSharePreString(RunTime.application,OLD_TOKEN,"");
    }

    public void initUser(final UserReponse bean) {
        this.bean=bean;
        if (bean.info == null) {
            isLogin = false;
        } else {
            sId = bean.info.s_id;
            adminId = bean.info.admin_id;
            userName = bean.info.name;
            token=bean.info.token;
            SharedPreferencesUtil.saveSharePreString(RunTime.application,OLD_TOKEN,bean.info.token);
            new_token=bean.info.new_token;
//            oilName = bean.info.name;
            Glide.with(RunTime.application).asDrawable().load(bean.info.head_url).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                    userIcon = resource;
                }
            });
            isLogin = true;
        }
        userIcon = RunTime.application.getResources().getDrawable(R.mipmap.def_usericon);

    }
    public void initUserTwo(final UserReponse bean) {
        this.bean=bean;
        if (bean.info == null) {
            isLogin = false;
        } else {
            sId = bean.info.s_id;
            adminId = bean.info.admin_id;
            userName = bean.info.name;
//            oilName = bean.info.name;
            Glide.with(RunTime.application).asDrawable().load(bean.info.head_url).into(new SimpleTarget<Drawable>() {
                @Override
                public void onResourceReady(Drawable resource, Transition<? super Drawable> transition) {
                    userIcon = resource;
                }
            });
            isLogin = true;
        }
        userIcon = RunTime.application.getResources().getDrawable(R.mipmap.def_usericon);

    }

    public interface AuthorizationListener {
        public void authorization(boolean isOk);
    }

}
