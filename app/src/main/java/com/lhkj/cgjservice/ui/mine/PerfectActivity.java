package com.lhkj.cgjservice.ui.mine;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.base.ui.BaseActivity;
import com.lhkj.cgjservice.databinding.ActivityPerfectBinding;
import com.lhkj.cgjservice.lock.AppBarLock;
import com.lhkj.cgjservice.lock.PerfectLock;

/**
 * Created by 浩琦 on 2017/6/21.
 */

public class PerfectActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityPerfectBinding perfectBinding= DataBindingUtil.setContentView(this, R.layout.activity_perfect);
        perfectBinding.setPerfect(new PerfectLock(this,perfectBinding));
        perfectBinding.include.setAppBarLock(new AppBarLock(this,R.string.perfect,R.mipmap.icon_back,0,true,false));
    }
}
