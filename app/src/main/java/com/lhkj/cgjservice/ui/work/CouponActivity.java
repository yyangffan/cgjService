package com.lhkj.cgjservice.ui.work;

import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.icu.text.SimpleDateFormat;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;
import com.bumptech.glide.Glide;
import com.lhkj.cgjservice.HttpRequset.HttpCallListener;
import com.lhkj.cgjservice.HttpRequset.HttpUtil;
import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.base.ui.BaseActivity;
import com.lhkj.cgjservice.base.ui.adapter.BaseTopAdapter;
import com.lhkj.cgjservice.databinding.ActivityCouponBinding;
import com.lhkj.cgjservice.databinding.PopCouponBinding;
import com.lhkj.cgjservice.entity.Operation;
import com.lhkj.cgjservice.entity.RunTime;
import com.lhkj.cgjservice.entity.User;
import com.lhkj.cgjservice.lock.AppBarLock;
import com.lhkj.cgjservice.printutiles.AidlUtil;
import com.lhkj.cgjservice.reponse.CouponReponse;
import com.lhkj.cgjservice.reponse.CouponRetpnse;
import com.lhkj.cgjservice.reponse.UserOilHisReponse;
import com.lhkj.cgjservice.ui.MainActivity;
import com.lhkj.cgjservice.utils.PopManager;
import com.lhkj.cgjservice.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/7/22.
 */

public class CouponActivity extends BaseActivity {
    private MainActivity.QrCodeJson qrCodeJson;
    private CouponLock couponLock;
    private ArrayList<CouponItem> couponList;
    private ActivityCouponBinding couponBinding;
    private SimpleDateFormat mSimpleDateFormat;
    private String nowTime = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        couponBinding = DataBindingUtil.setContentView(this, R.layout.activity_coupon);
        qrCodeJson = (MainActivity.QrCodeJson) RunTime.getRunTime(RunTime.CODE_TEXT);
        couponLock = new CouponLock();
        couponBinding.setCouponLock(couponLock);
        couponBinding.include4.setAppBarLock(new AppBarLock(this, R.string.oilbanner, R.mipmap.icon_back, R.mipmap.icon_mine, true, true).setRight(AppBarLock.LOGIN));
        couponList = new ArrayList<>();
        couponLock.couponAdapter = new BaseTopAdapter(this, couponList, R.layout.card_item, BR.cardItem);
        codeMain();
        codeCoupon();

        AidlUtil.getInstance().initPrinter();
    }

    private void codeMain() {
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", qrCodeJson.userId);
        hashMap.put("s_id", User.getUser().sId);
        HttpUtil.getInstance().postRequest(Operation.USER_OIL_HIS, hashMap, UserOilHisReponse.class, new HttpCallListener<UserOilHisReponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final UserOilHisReponse bean) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(CouponActivity.this)
                                .load(bean.info.head_pic)
                                .into(couponBinding.userIcon)
                                .onLoadFailed(getResources().getDrawable(R.mipmap.def_usericon));
                        couponLock.userLll = bean.info.balance;  // 积分
                        couponLock.userName = bean.info.nickname;
                        couponLock.notifyChange();
                    }
                });
            }

            @Override
            public void Error(String URL) {

            }
        });
    }

    private void codeCoupon() {
        couponList.clear();
        HashMap hashMap = new HashMap();
        hashMap.put("coupon_id", qrCodeJson.couponId);
        HttpUtil.getInstance().postRequest(Operation.COUPON_ONE, hashMap, CouponReponse.class, new HttpCallListener<CouponReponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull CouponReponse bean) {
                if (bean.info == null) {
                    return;
                }
                Date date = new Date();
                date.setTime(Long.parseLong(bean.info.use_end_time + "000"));
                bean.info.use_end_time = (date.getYear() + 1900) + "-" + (date.getMonth() + 1) + "-" + (date.getDate());
                couponList.add(new CouponItem(bean.info.name, bean.info.use_end_time, bean.info.money));
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        couponLock.couponAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void Error(String URL) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CouponActivity.this, "加载优惠卷失败，请重新扫描", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public void couponEnter(View view) {
        final PopManager popManager = new PopManager(CouponActivity.this);
        PopCouponBinding popCouponBinding = (PopCouponBinding) popManager.showPop(
                couponBinding.linearLayout2, R.layout.pop_coupon);
        popCouponBinding.setCouponNum(couponList.get(0).cardPay + "元");
        popCouponBinding.setCouponName(couponList.get(0).cardName);
        popCouponBinding.couponEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                couponPush();
                popManager.stop();
            }
        });
        popCouponBinding.couponCan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popManager.stop();
            }
        });

    }

    private void couponPush() {
        HashMap hashMap = new HashMap();
//        hashMap.put("user_id", qrCodeJson.userId);
//        hashMap.put("coupon_id", qrCodeJson.couponId);
        hashMap.put("s_id", User.getUser().sId);
        hashMap.put("state_id", qrCodeJson.stateId);
        Log.i("state_id", qrCodeJson.stateId + "  " + User.getUser().sId);
        HttpUtil.getInstance().postRequest(Operation.COUPON_PUSH, hashMap, CouponRetpnse.class, new HttpCallListener<CouponRetpnse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final CouponRetpnse bean) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean.code.equals("200")) {
                            isPrint(bean);
                            Toast.makeText(CouponActivity.this, "使用优惠卷成功", Toast.LENGTH_LONG).show();
                            finish();
                        } else if (bean.code.equals("302")) {
                            Toast.makeText(CouponActivity.this, "优惠券已被使用", Toast.LENGTH_SHORT).show();
                        } else if (bean.code.equals("401")) {
                            Toast.makeText(CouponActivity.this, "此券不能在其它站使用", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(CouponActivity.this, bean.getSuccess(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }

            @Override
            public void Error(String URL) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(CouponActivity.this, "网络不畅请重试", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public class CouponLock extends BaseObservable {
        public BaseTopAdapter couponAdapter;
        public String userName;
        public String userLll;
    }

    public class CouponItem {
        public CouponItem(String cardName, String cardTime, String cardPay) {
            this.cardName = cardName;
            this.cardTime = cardTime;
            this.cardPay = cardPay;
        }

        public String cardName;
        public String cardTime;
        public String cardPay;
    }

    /*这里我有集成Aidl以及蓝牙打印的资料直接进行判断即可已使用*/
    public void isPrint(CouponRetpnse bean) {
//        if(true){
        boolean isOil = SharedPreferencesUtil.getSharePreBoolean(this, "isOil", true);
        if (isOil) {
            AidlUtil.getInstance().printText(getPrintContent(bean), 24, false, false);/*Aidl打印*/
        }
//        }else {
        //        if (BluetoothUtil.connectBlueTooth(this)) {
//            Toast.makeText(this, "蓝牙连接成功", Toast.LENGTH_SHORT).show();
//        } else {
//               Toast.makeText(this, "蓝牙连接失败无法实现打印", Toast.LENGTH_SHORT).show();
//        }
//        StartPrint.getInstance(CouponActivity.this).printByBluTooth(getPrintContent(bean));/*蓝牙打印--需要开启并连接成功蓝牙*/
//        }
    }

    public String getPrintContent(CouponRetpnse bean) {
        String couponName = (String) bean.getCoupon().getCouponName();
//        if (couponName != null) {
//            if (couponName.length() > 8) {
//                couponName = couponName.substring(0, 6) + "...";
//            } else {
//                for (int i = 0; i < 20 - couponName.length(); i++) {
//                    couponName += " ";
//                }
//            }
//        } else {
//            couponName = "                    ";
//        }
        StringBuilder builder = new StringBuilder();
        builder.append("      油品惠兑换凭证");
        builder.append("\n兑换日期:" + bean.getDate());
        builder.append("\n账号:" + bean.getUser().getMobile());
        builder.append("\n车牌号:" + bean.getUser().getCar_number());
        builder.append("\n客户姓名:" + bean.getUser().getNickname());
        builder.append("\n\n员工编号:" + bean.getStaff().getNumber());
        builder.append("\n员工姓名:" + bean.getStaff().getName());
        builder.append("\n账号:" + bean.getStaff().getAccount());
        builder.append("\n优惠券名称" + "  数量" + " 单价" + " 金额\n");
        builder.append(couponName + "\n");
        builder.append("             " + bean.getCoupon().getNum() + "  " + bean.getCoupon().getMoney() + "  " + bean.getCoupon().getMoney());
//        builder.append("\n\n\n\n兑换商品使用现金:" + "200.00  " + "使用积分:" + "200");
        builder.append("\n客户签名:\n\n\n\n\n\n\n");
        return builder.toString();
    }


}