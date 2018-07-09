package com.lhkj.cgjservice.ui.Login;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.base.ui.BaseActivity;
import com.lhkj.cgjservice.databinding.ActivityLoginBinding;
import com.lhkj.cgjservice.lock.AppBarLock;
import com.lhkj.cgjservice.lock.LoginLock;

/**
 * Created by 浩琦 on 2017/6/20.
 */

public class LoginActivity extends BaseActivity {

    public static LoginActivity loginActivity;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        new SetJPushAlias("",this).cancleAlias();
        ActivityLoginBinding loginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        loginBinding.setLoginLock(new LoginLock(this));
        loginBinding.include3.setAppBarLock(new AppBarLock(this, R.string.login, R.mipmap.icon_back, 0, true, false));
        loginActivity = this;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (resultCode) {
            case 200:
                finish();
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
