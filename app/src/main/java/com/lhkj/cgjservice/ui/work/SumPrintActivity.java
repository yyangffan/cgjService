package com.lhkj.cgjservice.ui.work;

import android.databinding.DataBindingUtil;
import android.os.Bundle;

import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.base.ui.BaseActivity;
import com.lhkj.cgjservice.databinding.ActivitySumPrintBinding;
import com.lhkj.cgjservice.lock.AppBarLock;
import com.lhkj.cgjservice.lock.SumPrintLock;
import com.lhkj.cgjservice.printutiles.AidlUtil;


public class SumPrintActivity extends BaseActivity {

    private ActivitySumPrintBinding mBinding;
    private SumPrintLock mSumPrintLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_sum_print);
        mSumPrintLock=new SumPrintLock(this,mBinding);
        mBinding.include.setAppBarLock(new AppBarLock(this, R.string.dui_print));
        mBinding.setSumLock(mSumPrintLock);

    }
}
