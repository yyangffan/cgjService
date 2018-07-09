package com.lhkj.cgjservice.ui.work;

import android.databinding.BaseObservable;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.lhkj.cgjservice.HttpRequset.HttpCallListener;
import com.lhkj.cgjservice.HttpRequset.HttpUtil;
import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.base.ui.BaseActivity;
import com.lhkj.cgjservice.databinding.ActivitySignPriceBinding;
import com.lhkj.cgjservice.databinding.PopToastBinding;
import com.lhkj.cgjservice.entity.Operation;
import com.lhkj.cgjservice.entity.RunTime;
import com.lhkj.cgjservice.entity.User;
import com.lhkj.cgjservice.lock.AppBarLock;
import com.lhkj.cgjservice.printutiles.AidlUtil;
import com.lhkj.cgjservice.reponse.ExchaneDetailReponse;
import com.lhkj.cgjservice.reponse.SumPrintResponse;
import com.lhkj.cgjservice.ui.MainActivity;
import com.lhkj.cgjservice.utils.NetworkImageHolderView;
import com.lhkj.cgjservice.utils.PopManager;
import com.lhkj.cgjservice.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/8/11.
 */

public class SignPriceActivity extends BaseActivity {
    private SignPriceData signPriceData;
    private MainActivity.QrCodeJson qrCodeJson;
    private ActivitySignPriceBinding signPriceBinding;
    private ArrayList<String> networkImages;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        signPriceBinding = DataBindingUtil.setContentView(this, R.layout.activity_sign_price);
        signPriceBinding.appBar.setAppBarLock(new AppBarLock(this, R.string.exchange_detail));
        signPriceData = new SignPriceData("", "", "￥0.00", "0积分", "", "0000");
        qrCodeJson = (MainActivity.QrCodeJson) RunTime.getRunTime(RunTime.CODE_TEXT);
        signPriceBinding.setSignPriceData(signPriceData);
        init();
        AidlUtil.getInstance().initPrinter();
    }

    private void init() {
        HashMap hashMap = new HashMap();
        hashMap.put("order_id", qrCodeJson.orderSn);
        hashMap.put("admin_id", User.getUser().adminId);
        hashMap.put("s_id", User.getUser().sId);
        HttpUtil.getInstance().postRequest(Operation.EXCHANGE_DETAIL, hashMap, ExchaneDetailReponse.class, new HttpCallListener<ExchaneDetailReponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final ExchaneDetailReponse bean) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean.info == null) {
                            return;
                        }
                        if (bean.code.equals("200")) {
                            switch (bean.info.type) {
                                case "1":
                                    signPriceData.pricePay = "￥" + bean.info.goods_price;
                                    signPriceData.priceNote = "直接现金购买";
                                    signPriceData.pricePayAndLll = "￥" + bean.info.order_price;
                                    break;
                                case "2":
                                    signPriceData.pricePay = bean.info.exchange_integral + "积分";
                                    signPriceData.priceNote = "直接积分兑换";
                                    signPriceData.pricePayAndLll = bean.info.jifen + "积分";
                                    break;
                                case "3":
                                    signPriceData.pricePay = "￥" + bean.info.goods_price + "\n" + bean.info.exchange_integral + "积分";
                                    signPriceData.priceNote = bean.info.jifen_lv + "积分可抵￥" + bean.info.order_price;
                                    signPriceData.pricePayAndLll = "￥" + bean.info.order_price + "\n" + bean.info.jifen + "积分";
                                    break;
                            }
                            networkImages = new ArrayList<>();
                            if (bean.info != null) {
                                if(bean.info.images != null) {
                                    for (String string : bean.info.images) {
                                        networkImages.add(string);
                                    }
                                }
                            }
                            signPriceBinding.shopDetailsIms.startTurning(2500);
                            signPriceBinding.shopDetailsIms.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                                @Override
                                public NetworkImageHolderView createHolder() {
                                    return new NetworkImageHolderView();
                                }
                            }, networkImages);
                            SignPriceActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    signPriceBinding.shopDetailsIms.notifyDataSetChanged();
                                }
                            });
                            //                signPriceData.priceName=bean.info.;
                            signPriceData.priceLll = bean.info.exchange_integral + "积分";
                            signPriceData.priceOrder = "订单账号 " + bean.info.order_sn;

                            if (bean.info.is_hot != null && bean.info.is_hot.equals("1")) {
                                signPriceData.isHot = true;
                            }
                            signPriceData.userName = "兑换人姓名 " + bean.info.nickname;
                            signPriceData.priceName = bean.info.goods_name;
                            signPriceData.notifyChange();
                        }
                    }
                });

            }

            //><p><img src=\"http:\/\/yph.177678.top\/Public\/upload\/goods\/2017\/08-14\/59918040f366d.jpg\" title=\"593a82d8N9c46b980.jpg\"\/><\/p><
            @Override
            public void Error(String URL) {

            }
        });
    }

    public void signPriceEnter(View view) {
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", qrCodeJson.userId);
        hashMap.put("order_id", qrCodeJson.orderSn);
        hashMap.put("s_id", User.getUser().sId);
        Log.e("上传参数","user_id:"+qrCodeJson.userId+"  order_id:"+qrCodeJson.orderSn+"  s_id:"+User.getUser().sId);
        HttpUtil.getInstance().postRequest(Operation.PAY_PUSH, hashMap, SumPrintResponse.class, new HttpCallListener<SumPrintResponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final SumPrintResponse bean) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean.code.equals("200")) {
                            PopManager popManager = new PopManager(SignPriceActivity.this);
                            PopToastBinding popToastBinding = (PopToastBinding) popManager.showPop(signPriceBinding.shopDetailsLll, R.layout.pop_toast);
                            popToastBinding.popText.setText(getResources().getString(R.string.exchange_succ));
                            popManager.disPop(popToastBinding.popEnter, true);
                            isPrint(bean);

                        } else if (bean.code.equals("201")) {
                            Toast.makeText(SignPriceActivity.this, "此商品已经领取过了", Toast.LENGTH_SHORT).show();
                        } else{
                            Toast.makeText(SignPriceActivity.this, "提交失败，请重新提交", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void Error(String URL) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(SignPriceActivity.this, "网络不畅，请重新提交", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    public class SignPriceData extends BaseObservable {
        public SignPriceData(String priceNote, String priceName, String pricePay, String priceLll, String userName, String priceOrder) {
            this.priceNote = priceNote;
            this.priceName = priceName;
            this.pricePay = pricePay;
            this.priceLll = priceLll;
            this.userName = userName;
            this.priceOrder = priceOrder;
            pricePayAndLll = pricePay + "\n" + priceLll;
        }

        public String priceNote;
        public String priceName;
        public String pricePay;
        public String priceLll;
        public String userName;
        public String priceOrder;
        public String pricePayAndLll;
        public boolean isHot;
    }
    /*这里我有集成Aidl以及蓝牙打印的资料直接进行判断即可已使用*/
    public void isPrint(SumPrintResponse bean) {
//        if(true){
        boolean isSP = SharedPreferencesUtil.getSharePreBoolean(this, "isSp", true);
        if(isSP) {
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

    public String getPrintContent(SumPrintResponse bean) {
        String couponName = (String) bean.getOrder().getGoodsName();
        if (couponName != null) {
//            if (couponName.length() > 10) {
//                couponName = couponName.substring(0, 10) + "...";
//            } else {
//                for (int i = 0; i < 20 - couponName.length(); i++) {
//                    couponName += " ";
//                }
//            }
        } else {
            couponName = "                    ";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("      油品惠兑换凭证");
        builder.append("\n兑换日期:" + bean.getOrder().getConfirmTime());
        builder.append("\n账号:" + bean.getUser().getMobile());
        builder.append("\n车牌号:" + bean.getUser().getCar_number());
        builder.append("\n客户姓名:" + bean.getUser().getNickname());
        builder.append("\n\n员工编号:" + bean.getStaff().getNumber());
        builder.append("\n员工姓名:" + bean.getStaff().getName());
        builder.append("\n账号:" + bean.getStaff().getAccount());
        builder.append("\n商品名称" + "  数量" + " 单价" + " 金额\n");
        builder.append(couponName+"\n");
        builder.append("           1  " + bean.getOrder().getMoney() + "  " + bean.getOrder().getMoney() );
        builder.append("\n\n\n\n"+"兑换商品\n"+"使用现金:" +bean.getOrder().getMoney() + "\n使用积分:" + bean.getOrder().getJifen());
        builder.append("\n客户签名:\n\n\n\n\n\n\n");
        return builder.toString();
    }



}
