package com.lhkj.cgjservice.ui.mine;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.bumptech.glide.Glide;
import com.lhkj.cgjservice.HttpRequset.HttpCallListener;
import com.lhkj.cgjservice.HttpRequset.HttpUtil;
import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.databinding.ActivityMyErWeimaBinding;
import com.lhkj.cgjservice.entity.Operation;
import com.lhkj.cgjservice.entity.RunTime;
import com.lhkj.cgjservice.entity.User;
import com.lhkj.cgjservice.lock.AppBarLock;
import com.lhkj.cgjservice.reponse.MyErweimaResponse;

import java.util.HashMap;

public class MyErWeimaActivity extends AppCompatActivity {
    private ActivityMyErWeimaBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_my_er_weima);
        mBinding.include5.setAppBarLock(new AppBarLock(this,R.string.myerweima,R.mipmap.icon_back,0,true,false));
        initData();
    }

    private void initData() {
        HashMap hashMap = new HashMap();
        hashMap.put("s_id", User.getUser().sId);
        HttpUtil.getInstance().postRequest(Operation.ERWEIMA, hashMap, MyErweimaResponse.class, new HttpCallListener<MyErweimaResponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final MyErweimaResponse bean) {
                Log.e("二维码数据","code: "+bean.getCode()+"  message:"+bean.getMessage());
                if(bean.img_url!=null) {
                    MyErWeimaActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Glide.with(MyErWeimaActivity.this).load(RunTime.BASEURL + bean.img_url).into(mBinding.erweima);
                        }
                    });
                }
            }

            @Override
            public void Error(String URL) {

            }
        });

    }
}
