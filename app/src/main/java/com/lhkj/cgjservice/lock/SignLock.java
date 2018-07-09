package com.lhkj.cgjservice.lock;

import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;

import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.databinding.ActivitySignBinding;
import com.lhkj.cgjservice.databinding.PopToastBinding;
import com.lhkj.cgjservice.entity.User;
import com.lhkj.cgjservice.ui.Login.LoginActivity;
import com.lhkj.cgjservice.ui.Login.SignActivity;
import com.lhkj.cgjservice.utils.PopManager;
import com.lhkj.cgjservice.utils.SharedPreferencesUtil;
import com.lhkj.cgjservice.utils.ToolUtils;


/**
 * Created by 浩琦 on 2017/6/20.
 */

public class SignLock {
    private Context context;
    public SignData signData;
    private ActivitySignBinding signBinding;
    private int timer;

    public SignLock(Context context, ActivitySignBinding signBinding) {
        this.context = context;
        this.signBinding = signBinding;
        signData = new SignData();
        signData.isArgee = true;
        timer = 30;
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            timer--;
            if (timer <= 0) {
                signBinding.sendcode.setText(context.getResources().getString(R.string.sendcode));
                timer = 30;
            } else {
                signBinding.sendcode.setText("请稍后" + timer);
                sendTime(1000);
            }
        }
    };

    private void sendTime(long time) {
        handler.sendEmptyMessageDelayed(0, time);
    }

    public void sendCode() {
        if (ToolUtils.isMobileNO(signData.signPhone)) {
            if (timer == 30) {
                sendTime(1000);
                timer--;
                signBinding.sendcode.setText("请稍后" + timer);
                User.getUser().trySendCode(signData.signPhone, new User.AuthorizationListener() {
                    @Override
                    public void authorization(boolean isOk) {

                    }
                });
            }
        }else {
            Toast.makeText(context, "请填写有效的手机号", Toast.LENGTH_SHORT).show();
        }
    }

    public void next() {
        if (ToolUtils.isMobileNO(signData.signPhone) && signData.signPwd.equals(signData.signRepwd)) {
            if (!signData.isArgee) {
                Toast.makeText(context, "请同意协议", Toast.LENGTH_SHORT).show();
                return;
            }
            User.getUser().trySign(signData.signPhone, signData.signCode, signData.signPwd, signData.signRepwd, new User.AuthorizationListener() {
                @Override
                public void authorization(boolean isOk) {
                    if (isOk){
                        PopManager popManager = new PopManager(context);
                        PopToastBinding toastBinding = (PopToastBinding) popManager.showPop(signBinding.linearLayout15, R.layout.pop_toast);
                        toastBinding.popIms.setImageResource(R.mipmap.success);
                        toastBinding.popText.setText(context.getResources().getString(R.string.signsuccess));
                        ((SignActivity)context).setResult(200);
                        LoginActivity.loginActivity.finish();
                        LoginActivity.loginActivity=null;
                        popManager.disPop(toastBinding.popEnter, true);
                        SharedPreferencesUtil.saveSharePreString(context,User.getUser().SAVE_NAME,signData.signPhone);
                        SharedPreferencesUtil.saveSharePreString(context,User.getUser().SAVE_PWD,signData.signPwd);
                    }else {
                        Toast.makeText(context, "注册失败", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        } else {
            Toast.makeText(context, "账号或密码填写错误", Toast.LENGTH_SHORT).show();
        }
    }

    public void sign() {
        ((SignActivity) context).finish();
    }

    public class SignData {
        public String signPhone;
        public String signCode="";
        public String signPwd="";
        public String signRepwd="";
        public boolean isArgee;
    }
}
