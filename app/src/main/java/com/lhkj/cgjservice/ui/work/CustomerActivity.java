package com.lhkj.cgjservice.ui.work;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.base.ui.BaseActivity;
import com.lhkj.cgjservice.databinding.ActivityCustomerBinding;
import com.lhkj.cgjservice.entity.RunTime;
import com.lhkj.cgjservice.lock.AppBarLock;
import com.lhkj.cgjservice.lock.CustomerLock;

/**
 * Created by 浩琦 on 2017/6/26.
 */

public class CustomerActivity extends BaseActivity {

    private CustomerLock customerLock;
    private ActivityCustomerBinding customerBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        customerBinding = DataBindingUtil.setContentView(this, R.layout.activity_customer);
        customerLock = new CustomerLock(this, customerBinding);
        customerBinding.include4.setAppBarLock(new AppBarLock(this, R.string.oilbanner, R.mipmap.icon_back, R.mipmap.icon_mine, true, true).setRight(AppBarLock.LOGIN));
        customerBinding.setCustomerLock(customerLock);

    }


    @Override
    protected void onRestart() {
        super.onRestart();
        customerLock.selectView=null;
        customerLock.flush();
        RunTime.setData(RunTime.CODE_TEXT, null);
    }
}
