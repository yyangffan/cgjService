package com.lhkj.cgjservice.lock;

import android.content.Context;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.widget.Toast;

import com.lhkj.cgjservice.HttpRequset.HttpCallListener;
import com.lhkj.cgjservice.HttpRequset.HttpUtil;
import com.lhkj.cgjservice.databinding.ActivityAboutBinding;
import com.lhkj.cgjservice.entity.Operation;
import com.lhkj.cgjservice.entity.RunTime;
import com.lhkj.cgjservice.reponse.ProtocolReponse;
import com.lhkj.cgjservice.reponse.UserOilHisReponse;
import com.lhkj.cgjservice.ui.MainActivity;
import com.lhkj.cgjservice.ui.work.CouponActivity;
import com.lhkj.cgjservice.ui.work.CustomerActivity;
import com.lhkj.cgjservice.ui.work.SignPriceActivity;

import java.util.HashMap;

/**
 * 创建日期：2017/10/21 on 21:52
 * 描述：
 * 作者：郭士超
 * QQ：1169380200
 */

public class AboutLock {

    private Context context;
    private ActivityAboutBinding mBinding;

    public About about;

    public AboutLock(Context context, ActivityAboutBinding binding) {
        this.context = context;
        this.mBinding = binding;

        about = new About();

        getHttp();
    }

    private void getHttp() {
        HttpUtil.getInstance().postRequest(Operation.ABOUT, new HashMap<String, Object>(), ProtocolReponse.class,
                new HttpCallListener<ProtocolReponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final ProtocolReponse bean) {
                if (bean.code.equals("200")) {
                    ProtocolReponse protocolReponse = (ProtocolReponse) bean;
                    about.title = protocolReponse.info.title;
                    about.content = protocolReponse.info.content;

                    about.notifyChange();
                }else {
                    Toast.makeText(context, "获取失败，稍后再试", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void Error(String URL) {

            }
        });
    }

    public class About extends BaseObservable {

        public About() {
            this.title = "";
            this.content = "";
        }

        public String title = "";
        public String content = "";

    }

}
