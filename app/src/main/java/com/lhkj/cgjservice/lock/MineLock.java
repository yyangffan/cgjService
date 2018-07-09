package com.lhkj.cgjservice.lock;

import android.content.Context;
import android.databinding.BaseObservable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;

import com.bumptech.glide.Glide;
import com.lhkj.cgjservice.HttpRequset.HttpCallListener;
import com.lhkj.cgjservice.HttpRequset.HttpUtil;
import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.databinding.ActivityMineBinding;
import com.lhkj.cgjservice.entity.Operation;
import com.lhkj.cgjservice.entity.User;
import com.lhkj.cgjservice.reponse.AdsenseReponse;
import com.lhkj.cgjservice.ui.Login.LoginActivity;
import com.lhkj.cgjservice.ui.mine.MineActivity;
import com.lhkj.cgjservice.ui.mine.MyCardActivity;
import com.lhkj.cgjservice.ui.mine.MyErWeimaActivity;
import com.lhkj.cgjservice.ui.mine.MyHisActivity;
import com.lhkj.cgjservice.ui.mine.PerfectActivity;
import com.lhkj.cgjservice.ui.mine.ShareActivity;
import com.lhkj.cgjservice.ui.other.SetActivity;

import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/6/19.
 */

public class MineLock {
    private Context context;
    private ActivityMineBinding mineBinding;
    public MineData mineData;

    public MineLock(Context context, ActivityMineBinding mineBinding) {
        mineData = new MineData();
        this.mineBinding = mineBinding;
        this.context = context;
        fulsh();
        adsense();
    }

    private void adsense() {
        HashMap hashMap = new HashMap();
        hashMap.put("admin_id", User.getUser().adminId);
        //boo
        HttpUtil.getInstance().postRequest(Operation.ADSENSE, hashMap, AdsenseReponse.class, new HttpCallListener<AdsenseReponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final AdsenseReponse bean) {
                if (bean.code.equals("200")) {
                    if (bean.info != null) {

                        ((MineActivity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Glide.with(context).load(bean.info.img_url).into(mineBinding.adsen);
                                mineData.isAds = true;
                                mineData.notifyChange();
                            }
                        });
                    }
                }
            }

            @Override
            public void Error(String URL) {

            }
        });
    }

    public void ads() {
    }

    public void adsClose() {
        mineData.isAds = false;
        mineData.notifyChange();
    }

    public void fulsh() {
        if (!User.getUser().isLogin) {
            mineData.userName = context.getResources().getString(R.string.loginor);
        } else {
            mineData.userName = User.getUser().userName;
            mineData.userIcon = User.getUser().userIcon;
        }
        mineData.isLogin = User.getUser().isLogin;
        mineData.notifyChange();
    }

    public void finish() {
        ((MineActivity) context).finish();
    }

    public void toSet() {
        ((MineActivity) context).startActivity(SetActivity.class);
    }

    public void toLogin() {
        if (!mineData.isLogin) {
            ((MineActivity) context).startActivity(LoginActivity.class);
        }
    }

    public void toSign() {
//        ((MainActivity) context).startActivity(SignCalendarActivity.class);
    }

    public void myOrder() {
//        ((MainActivity) context).startActivity(MyOrderActivity.class);
    }

    public void myCard() {
        ((MineActivity) context).startActivity(MyCardActivity.class);
    }

    public void perfect() {
        ((MineActivity) context).startActivity(PerfectActivity.class);
    }

    public void mySpeak() {
//        ((MainActivity) context).startActivity(MySpeakActivity.class);
    }

    public void lllGame() {
//        ((MainActivity) context).startActivity(LllGameActivity.class);
    }

    public void myShare() {
        ((MineActivity) context).startActivity(ShareActivity.class);
    }

    public void myHis() {
        ((MineActivity) context).startActivity(MyHisActivity.class);
    }
    public void myErweima() {
        ((MineActivity) context).startActivity(MyErWeimaActivity.class);
    }

    public class MineData extends BaseObservable {
        public boolean isLogin;
        public String userName;
        public Drawable userIcon;
        public boolean isAds;
    }
}
