package com.lhkj.cgjservice.lock;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.lhkj.cgjservice.HttpRequset.HttpCallListener;
import com.lhkj.cgjservice.HttpRequset.HttpUtil;
import com.lhkj.cgjservice.bean.SetJPushAlias;
import com.lhkj.cgjservice.entity.Operation;
import com.lhkj.cgjservice.entity.RunTime;
import com.lhkj.cgjservice.entity.User;
import com.lhkj.cgjservice.reponse.HttpReponse;
import com.lhkj.cgjservice.ui.Login.ForgetActivity;
import com.lhkj.cgjservice.ui.Login.LoginActivity;
import com.lhkj.cgjservice.ui.Login.SignActivity;
import com.lhkj.cgjservice.ui.MainActivity;
import com.lhkj.cgjservice.utils.SharedPreferencesUtil;

import java.util.HashMap;

import static com.lhkj.cgjservice.entity.User.OLD_TOKEN;


/**
 * Created by 浩琦 on 2017/6/20.
 */

public class LoginLock {
    public LoginData loginData;
    private Context context;

    public LoginLock(Context context) {
        this.context = context;
        loginData = new LoginData();
    }

    public void login() {
        User.getUser().tryLogin((LoginActivity) context, loginData.loginName, loginData.loginPwd, new User.AuthorizationListener() {
            @Override
            public void authorization(final boolean isOk) {
                ((LoginActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (isOk) {
                            ((LoginActivity) context).setResult(200);
                            ((LoginActivity) context).finish();
                            SharedPreferencesUtil.saveSharePreString(context, User.getUser().SAVE_NAME, loginData.loginName);
                            SharedPreferencesUtil.saveSharePreString(context, User.getUser().SAVE_PWD, loginData.loginPwd);

                            new SetJPushAlias(User.getUser().new_token, context).setAlias();
                            Log.e("各种Token","new_token="+User.getUser().new_token+" token="+User.getUser().token);
                            ((LoginActivity) context).startActivity(MainActivity.class);
                            goToWork();
                            fasongTuisong();
                        } else {
                            if (User.getUser().getBean().code.equals("201")) {
                                Toast.makeText(context, "账号已冻结", Toast.LENGTH_SHORT).show();
                            } else if (User.getUser().getBean().code.equals("100")) {
                                Toast.makeText(context, "用户密码错误", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "登陆失败", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
            }
        });
    }

    /*发送推送给上一台登陆的设备*/
    public void fasongTuisong() {
        HashMap hashMap = new HashMap();
        String old_token = SharedPreferencesUtil.getSharePreString(RunTime.application, OLD_TOKEN, "");
        hashMap.put("old_token", old_token);
        Log.e("old_token",User.getUser().token);
        HttpUtil.getInstance().postRequest(Operation.NOTLOGIN, hashMap, HttpReponse.class, new HttpCallListener<HttpReponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final HttpReponse bean) {
//                ((LoginActivity) context).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        if (bean.code.equals("200")) {
//                            Toast.makeText(context, "推送成功", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//                });
            }

            @Override
            public void Error(String URL) {
//                ((LoginActivity) context).runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Toast.makeText(context, "错误", Toast.LENGTH_SHORT).show();
//                    }
//                });
            }
        });

    }

    public void sign() {
        ((LoginActivity) context).startActivityForResult(new Intent(context, SignActivity.class), 200);
    }

    public void forget() {
        ((LoginActivity) context).startActivity(ForgetActivity.class);
    }

    public class LoginData {
        public String loginName = "";
        public String loginPwd = "";
    }

    public void goToWork() {
        HashMap hashMap = new HashMap();
        hashMap.put("s_id", User.getUser().sId);
        hashMap.put("type", "1");
        HttpUtil.getInstance().postRequest(Operation.GOWORK, hashMap, HttpReponse.class, new HttpCallListener<HttpReponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final HttpReponse bean) {
                if (bean.code.equals("200")) {
                    Log.d("上班", "成功上班");
                } else {
                    Log.d("上班", "上班失败");
                }
            }

            @Override
            public void Error(String URL) {
                Log.d("上班", "上班错误");
            }
        });


    }


}
