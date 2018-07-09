package com.lhkj.cgjservice.lock;

import android.content.Context;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.android.databinding.library.baseAdapters.BR;
import com.lhkj.cgjservice.HttpRequset.HttpCallListener;
import com.lhkj.cgjservice.HttpRequset.HttpUtil;
import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.base.ui.adapter.BaseTopAdapter;
import com.lhkj.cgjservice.databinding.ActivitySumPrintBinding;
import com.lhkj.cgjservice.entity.Operation;
import com.lhkj.cgjservice.entity.User;
import com.lhkj.cgjservice.printutiles.AidlUtil;
import com.lhkj.cgjservice.reponse.GoodsJlResponse;
import com.lhkj.cgjservice.reponse.YouHuiQuanResponse;
import com.lhkj.cgjservice.ui.views.CustomDatePicker;
import com.lhkj.cgjservice.ui.work.SumPrintActivity;
import com.lhkj.cgjservice.utils.DateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import static com.lhkj.cgjservice.lock.MainLock.getTime;

/**
 * Created by user on 2018/1/3.
 */

public class SumPrintLock {
    private Context mContext;
    private ActivitySumPrintBinding mBinding;
    public BaseTopAdapter mainAdapter;
    private ArrayList<MainLock.YHQItem> yhqList;
    private ArrayList<MainLock.GoodsItem> goodsList;
    private CustomDatePicker customDatePicker;
    private String staTime = "";
    private String endTime = "";
    private boolean isStart = true;
    private boolean isFirst = true;
    private List<RadioButton> mRadioButtons;
    private String mYhqAllPrice;
    private String mAllShangpinPrice;
    private String mYgName;
    private String mYgAccount;
    private String mNumber;


    public SumPrintLock(Context context, ActivitySumPrintBinding binding) {
        mContext = context;
        mBinding = binding;
        initDatePicker();
        init();
    }

    /*各种初始化操作*/
    public void init() {
        mRadioButtons = new ArrayList<>();
        mRadioButtons.add(mBinding.sumPrintYhq);
        mRadioButtons.add(mBinding.sumPrintSp);
        yhqList = new ArrayList<>();
        goodsList = new ArrayList<>();
        mainAdapter = new BaseTopAdapter(mContext, yhqList, R.layout.his_item_youhuiquan, BR.yhqItem);
        mBinding.listView.setAdapter(mainAdapter);
        youhuiquanData();
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String end_now = sdf.format(new Date());
        String start_now = DateUtil.getOldDate(-1);
        staTime = start_now;
        endTime = end_now;

        /*初始赋值*/
        mBinding.textStartTime.setText(start_now.split(" ")[0]);
        mBinding.textStartTime.setText(start_now);

        mBinding.textEndTime.setText(end_now.split(" ")[0]);
        mBinding.textEndTime.setText(end_now);

        customDatePicker = new CustomDatePicker(mContext, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间

                if (isStart) {
                    mBinding.textStartTime.setText(time);
                    staTime = getTime(time);
                } else {
                    mBinding.textEndTime.setText(time);
                    endTime = getTime(time);
                }
                if (mRadioButtons.get(0).isChecked()) {
                    youhuiquanData();
                } else if (mRadioButtons.get(1).isChecked()) {
                    shangpinData();
                }
            }
        }, "2010-01-01 00:00", end_now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(true); // 显示时和分
        customDatePicker.setIsLoop(true); // 允许循环滚动
        mBinding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (mRadioButtons.get(0).isChecked()) {
                    youhuiquanData();
                } else if (mRadioButtons.get(1).isChecked()) {
                    shangpinData();
                }
            }
        });
    }

    public void timeGetStart() {
        isFirst = false;
        isStart = true;
        customDatePicker.show(mBinding.textStartTime.getText().toString());
    }

    public void timeGetEnd() {
        isFirst = false;
        isStart = false;
        customDatePicker.show(mBinding.textEndTime.getText().toString());
    }

    public void startBt() {
        if (mRadioButtons.get(0).isChecked()) {
            youhuiquanData();
        } else if (mRadioButtons.get(1).isChecked()) {
            shangpinData();
        }
    }

    /*这里我有集成Aidl以及蓝牙打印的资料直接进行判断即可已使用*/
    public void startPrint() {
        AidlUtil.getInstance().initPrinter();
//        if(true){
        AidlUtil.getInstance().printText(getPrintContent(), 16, false, false);/*Aidl打印*/
//        }else {
        //        if (BluetoothUtil.connectBlueTooth(this)) {
//            Toast.makeText(this, "蓝牙连接成功", Toast.LENGTH_SHORT).show();
//        } else {
//               Toast.makeText(this, "蓝牙连接失败无法实现打印", Toast.LENGTH_SHORT).show();
//        }
//        StartPrint.getInstance(CouponActivity.this).printByBluTooth(getPrintContent(bean));/*蓝牙打印--需要开启并连接成功蓝牙*/
//        }

    }


    public String getPrintContent() {

        StringBuilder builder = new StringBuilder();
        builder.append("          油品惠员工兑换汇总");
        builder.append("\n员工编号:" + mNumber + "    员工姓名:" + mYgName);
        builder.append("\n账号:" + mYgAccount);
        if (mRadioButtons.get(0).isChecked()) {/*优惠券*/
            builder.append("\n汇总时段:" + mBinding.textStartTime.getText().toString() + " 至 " + mBinding.textEndTime.getText().toString());
            builder.append("\n优惠券:" + yhqList.size() + "张  " + "  合计:金额" + mYhqAllPrice + "元");
            builder.append("\n" + "优惠券名称" + "      数量" + "   单价" + "  金额\n");
            for (int i = 0; i < yhqList.size(); i++) {
                MainLock.YHQItem yhq = yhqList.get(i);
                String couponName = yhq.cname;
                builder.append((i + 1) + " " + couponName + "\n");
                builder.append(yhq.sname == null || yhq.sname.equals("") ? "           " : yhq.sname + "  " + "    1    " + yhq.money + "  " + yhq.money + "\n");
            }
        } else if (mRadioButtons.get(1).isChecked()) {/*商品*/
            builder.append("\n汇总时段:" + mBinding.textStartTime.getText().toString() + "至" + mBinding.textEndTime.getText().toString());
            builder.append("\n兑换商品:" + goodsList.size() + "   " + "合计:金额" + mAllShangpinPrice + "元");
            builder.append("\n商品名称" + "      数量" + "    单价" + "   金额\n");
            for (int i = 0; i < goodsList.size(); i++) {
                MainLock.GoodsItem gi = goodsList.get(i);
                String couponName = gi.goodsname;
                builder.append((i + 1) + " " + couponName + "\n");
                builder.append(gi.sname == null || gi.sname.equals("") ? "           " : gi.sname + "   1    " + gi.shopprice + "   " + gi.shopprice + "\n");
            }
        }

//        builder.append(couponName +   "1  " + bean.getOrder().getAllmoney() + "  " + bean.getOrder().getAllmoney() );
//        builder.append("\n\n\n\n兑换商品使用现金:" +bean.getOrder().getMoney() + "\n使用积分:" + bean.getOrder().getJifen());
        builder.append("\n\n\n员工签名:\n\n\n");
        Log.e("汇总时的数据:", builder.toString());
        return builder.toString();
    }

    private void youhuiquanData() {
//        yhqList.clear();
//        mainAdapter = new BaseTopAdapter(mContext, yhqList, R.layout.his_item_youhuiquan, BR.yhqItem);
//        mBinding.listView.setAdapter(mainAdapter);
        HashMap hashMap = new HashMap();
        hashMap.put("s_id", User.getUser().sId);
        hashMap.put("s_time", getTime(mBinding.textStartTime.getText().toString()));
        hashMap.put("o_time", getTime(mBinding.textEndTime.getText().toString()));

        HttpUtil.getInstance().postRequest(Operation.YHQJL, hashMap, YouHuiQuanResponse.class, new HttpCallListener<YouHuiQuanResponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final YouHuiQuanResponse bean) {
                yhqList.clear();

                mYgName = bean.staff.name;
                mYgAccount = bean.staff.account;
                mNumber = bean.staff.number;
                final Date date = new Date();
                final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (bean.info.size() == 0) {
                    ((SumPrintActivity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mYhqAllPrice = "0";
                            mBinding.listView.setVisibility(View.GONE);
                            mBinding.textViewNoe.setVisibility(View.VISIBLE);

                        }
                    });
                } else {
                    ((SumPrintActivity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mYhqAllPrice = bean.allfo == null ? "" : bean.allfo;
                            mBinding.listView.setVisibility(View.VISIBLE);
                            mBinding.textViewNoe.setVisibility(View.GONE);
                        }
                    });
                }
//                for (YouHuiQuanResponse.Info info : bean.info) {
//                    date.setTime(Long.parseLong(info.add_time + "000"));
//                    info.add_time = formatter.format(date);
////                    if (info.nickname == null) {
//                        info.nickname = info.mobile == null ? "" : info.mobile;
////                    }
//                    yhqList.add(new MainLock.YHQItem(info.nickname == "" ? "" : (info.nickname.length() > 11 ? info.nickname.substring(0, 11) + "..." : info.nickname), info.c_name, info.add_time, "￥" + info.money, info.cate_name));
//                }
                ((SumPrintActivity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (YouHuiQuanResponse.Info info : bean.info) {
                            date.setTime(Long.parseLong(info.add_time + "000"));
                            info.add_time = formatter.format(date);
                            info.nickname = info.mobile == null ? "" : info.mobile;
                            yhqList.add(new MainLock.YHQItem(info.id, info.nickname == "" ? "" : (info.nickname.length() > 11 ? info.nickname.substring(0, 11) + "..." : info.nickname), info.c_name, info.add_time, "￥" + info.money, info.cate_name));
                        }
                        mainAdapter = new BaseTopAdapter(mContext, yhqList, R.layout.his_item_youhuiquan, BR.yhqItem);
                        mBinding.listView.setAdapter(mainAdapter);
                        mainAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void Error(String URL) {

            }
        });
    }

    private void shangpinData() {
        HashMap hashMap = new HashMap();
        hashMap.put("s_id", User.getUser().sId);
        hashMap.put("s_time", getTime(mBinding.textStartTime.getText().toString()));
        hashMap.put("o_time", getTime(mBinding.textEndTime.getText().toString()));

        HttpUtil.getInstance().postRequest(Operation.GOODSJL, hashMap, GoodsJlResponse.class, new HttpCallListener<GoodsJlResponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final GoodsJlResponse bean) {
                goodsList.clear();
                final Date date = new Date();
                final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                if (bean.info.size() == 0) {
                    ((SumPrintActivity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mBinding.listView.setVisibility(View.GONE);
                            mBinding.textViewNoe.setVisibility(View.VISIBLE);
                        }
                    });
                } else {
                    ((SumPrintActivity) mContext).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mAllShangpinPrice = bean.all_shop == null ? "" : bean.all_shop;
                            mBinding.listView.setVisibility(View.VISIBLE);
                            mBinding.textViewNoe.setVisibility(View.GONE);
                        }
                    });
                }
                /*需要跟notification放到一起*/
//                for (GoodsJlResponse.Info info : bean.info) {
//                    date.setTime(Long.parseLong(info.time + "000"));
//                    info.time = formatter.format(date);
//                    if (info.shop_price == null) {
//                        info.shop_price = "";
//                    }
//                    if (info.exchange_integral == null) {
//                        info.exchange_integral = "";
//                    }
////                    if (info.nickname == null) {
//                    info.nickname = info.mobile == null ? "" : info.mobile;
////                    }
//                    MainLock.GoodsItem goodsItem = new MainLock.GoodsItem(info.nickname == "" ? "" :
//                            (info.nickname.length() > 11 ? info.nickname.substring(0, 11) + "..." : info.nickname), info.goods_name, "￥" + info.shop_price, info.time, "积分:" + info.exchange_integral);
//                    goodsItem.setAllfo(info.allfo);
//                    goodsList.add(goodsItem);
//                }
                ((SumPrintActivity) mContext).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        for (GoodsJlResponse.Info info : bean.info) {
                            date.setTime(Long.parseLong(info.time + "000"));
                            info.time = formatter.format(date);
                            if (info.shop_price == null) {
                                info.shop_price = "";
                            }
                            if (info.exchange_integral == null) {
                                info.exchange_integral = "";
                            }
                            info.nickname = info.mobile == null ? "" : info.mobile;
                            MainLock.GoodsItem goodsItem = new MainLock.GoodsItem(info.id, info.nickname == "" ? "" :
                                    (info.nickname.length() > 11 ? info.nickname.substring(0, 11) + "..." : info.nickname), info.goods_name, "￥" + info.shop_price, info.time, "积分:" + info.exchange_integral);
                            goodsItem.setAllfo(info.allfo);
                            goodsList.add(goodsItem);
                        }
                        mainAdapter = new BaseTopAdapter(mContext, goodsList, R.layout.his_item_shangpin, BR.item);
                        mBinding.listView.setAdapter(mainAdapter);
                        mainAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void Error(String URL) {

            }
        });
    }


}
