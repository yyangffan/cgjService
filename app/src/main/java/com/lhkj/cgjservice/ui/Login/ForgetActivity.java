package com.lhkj.cgjservice.ui.Login;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.base.ui.BaseActivity;
import com.lhkj.cgjservice.databinding.ActivityForgetBinding;
import com.lhkj.cgjservice.lock.AppBarLock;
import com.lhkj.cgjservice.lock.ForgetLock;


/**
 * Created by 浩琦 on 2017/6/20.
 */

public class ForgetActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityForgetBinding forgetBinding= DataBindingUtil.setContentView(this, R.layout.activity_forget);
        forgetBinding.setForgetLock(new ForgetLock(this,forgetBinding));
        forgetBinding.include3.setAppBarLock(new AppBarLock(this,R.string.forget,R.mipmap.icon_back,0,true,false));
    }
}
