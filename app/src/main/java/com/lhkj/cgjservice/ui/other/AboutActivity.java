package com.lhkj.cgjservice.ui.other;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.base.ui.BaseActivity;
import com.lhkj.cgjservice.databinding.ActivityAboutBinding;
import com.lhkj.cgjservice.lock.AboutLock;
import com.lhkj.cgjservice.lock.AppBarLock;

/**
 * 创建日期：2017/10/21 on 21:47
 * 描述：
 * 作者：郭士超
 * QQ：1169380200
 */

public class AboutActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityAboutBinding aboutBinding= DataBindingUtil.setContentView(this, R.layout.activity_about);
        aboutBinding.setAboukLock(new AboutLock(this,aboutBinding));
        aboutBinding.include2.setAppBarLock(new AppBarLock(this,R.string.about,R.mipmap.icon_back,0,true,false));
    }
}