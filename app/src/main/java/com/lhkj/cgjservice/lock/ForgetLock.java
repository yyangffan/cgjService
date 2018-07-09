package com.lhkj.cgjservice.lock;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.databinding.ActivityForgetBinding;
import com.lhkj.cgjservice.databinding.PopToastBinding;
import com.lhkj.cgjservice.entity.User;
import com.lhkj.cgjservice.utils.PopManager;
import com.lhkj.cgjservice.utils.ToolUtils;


/**
 * Created by 浩琦 on 2017/6/20.
 */

public class ForgetLock {
    private Context context;
    public ForgetData forgetData;
    private ActivityForgetBinding forgetBinding;
    private int timer;

    public ForgetLock(Context context, ActivityForgetBinding forgetBinding) {
        this.context = context;
        this.forgetBinding = forgetBinding;
        forgetData = new ForgetData();
    }
    Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            timer--;
            if (timer<=0){
                forgetBinding.sendcode.setText(context.getResources().getString(R.string.sendcode));
                timer=30;
            }else {
                forgetBinding.sendcode.setText("请稍后"+timer);
                sendTime(1000);
            }
        }
    };
    private void sendTime(long time){
        handler.sendEmptyMessageDelayed(0,time);
    }
    public void sendCode() {
        if (ToolUtils.isMobileNO(forgetData.forgetPhone)) {
            if (timer == 30) {
                sendTime(1000);
                timer--;
                forgetBinding.sendcode.setText("请稍后" + timer);
                User.getUser().trySendCode(forgetData.forgetPhone, new User.AuthorizationListener() {
                    @Override
                    public void authorization(boolean isOk) {

                    }
                });
            }
        }
    }

    public void next() {
        if (ToolUtils.isMobileNO(forgetData.forgetPhone)&&forgetData.forgetPwd.equals(forgetData.forgetRepwd)) {
            User.getUser().tryForget(forgetData.forgetPhone, forgetData.forgetCode, forgetData.forgetPwd, forgetData.forgetRepwd, new User.AuthorizationListener() {
                @Override
                public void authorization(boolean isOk) {
                    PopManager popManager = new PopManager(context);
                    PopToastBinding toastBinding = (PopToastBinding) popManager.showPop(forgetBinding.linearLayout15, R.layout.pop_toast);
                    toastBinding.popIms.setImageResource(R.mipmap.success);
                    toastBinding.popText.setText(context.getResources().getString(R.string.forsuccess));
                    popManager.disPop(toastBinding.popEnter, true);
                }
            });
        }
    }

    public class ForgetData {
        public String forgetPhone;
        public String forgetCode;
        public String forgetPwd;
        public String forgetRepwd;
    }
}
