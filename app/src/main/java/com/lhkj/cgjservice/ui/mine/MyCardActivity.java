package com.lhkj.cgjservice.ui.mine;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.base.ui.BaseActivity;
import com.lhkj.cgjservice.databinding.ActivityMycardBinding;
import com.lhkj.cgjservice.lock.AppBarLock;
import com.lhkj.cgjservice.lock.MyCardLock;


/**
 * Created by 浩琦 on 2017/6/22.
 */

public class MyCardActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMycardBinding mycardBinding= DataBindingUtil.setContentView(this, R.layout.activity_mycard);
        mycardBinding.setMyCardLock(new MyCardLock(this,mycardBinding));
        mycardBinding.include5.setAppBarLock(new AppBarLock(this,R.string.mycard,R.mipmap.icon_back,0,true,false));
    }
}
