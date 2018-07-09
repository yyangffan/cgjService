package com.lhkj.cgjservice.ui.mine;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.base.ui.BaseActivity;
import com.lhkj.cgjservice.databinding.ActivityMyhisBinding;
import com.lhkj.cgjservice.lock.AppBarLock;
import com.lhkj.cgjservice.lock.MyHisLock;


/**
 * Created by 浩琦 on 2017/6/22.
 */

public class MyHisActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMyhisBinding myhisBinding= DataBindingUtil.setContentView(this, R.layout.activity_myhis);
        myhisBinding.include.setAppBarLock(new AppBarLock(this, R.string.myhis,R.mipmap.icon_back,0,true,false));
        myhisBinding.setMyHisLock(new MyHisLock(this));

    }
}
