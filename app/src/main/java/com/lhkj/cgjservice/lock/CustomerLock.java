package com.lhkj.cgjservice.lock;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.databinding.BaseObservable;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lhkj.cgjservice.BR;
import com.lhkj.cgjservice.HttpRequset.HttpCallListener;
import com.lhkj.cgjservice.HttpRequset.HttpUtil;
import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.adapter.CustomerRcAdapter;
import com.lhkj.cgjservice.adapter.LiPinAdapter;
import com.lhkj.cgjservice.adapter.YhqAdapter;
import com.lhkj.cgjservice.adapter.YhqUsedAdapter;
import com.lhkj.cgjservice.base.ui.adapter.BaseTopAdapter;
import com.lhkj.cgjservice.bean.LiPinItem;
import com.lhkj.cgjservice.databinding.ActivityCustomerBinding;
import com.lhkj.cgjservice.databinding.PopToastBinding;
import com.lhkj.cgjservice.entity.Operation;
import com.lhkj.cgjservice.entity.RunTime;
import com.lhkj.cgjservice.entity.User;
import com.lhkj.cgjservice.printutiles.AidlUtil;
import com.lhkj.cgjservice.reponse.GoodsPrintResponse;
import com.lhkj.cgjservice.reponse.HttpReponse;
import com.lhkj.cgjservice.reponse.JYouYhqResponse;
import com.lhkj.cgjservice.reponse.LiPinPrintResponse;
import com.lhkj.cgjservice.reponse.LiPinResponse;
import com.lhkj.cgjservice.reponse.RanliaoResponse;
import com.lhkj.cgjservice.reponse.ShopBean;
import com.lhkj.cgjservice.reponse.UserOilHisReponse;
import com.lhkj.cgjservice.reponse.YouhuiQuanReponse;
import com.lhkj.cgjservice.reponse.YouhuiQuanUsedResponse;
import com.lhkj.cgjservice.ui.MainActivity;
import com.lhkj.cgjservice.ui.work.CustomerActivity;
import com.lhkj.cgjservice.utils.PopManager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static java.lang.Integer.parseInt;

/**
 * Created by 浩琦 on 2017/6/26.
 */

public class CustomerLock {

    public BaseTopAdapter customerAdapter;
    public ArrayList<CustomerItem> customerList;
    public CustomerData customerData;

    private Context context;
    private ActivityCustomerBinding customerBinding;
    private MainActivity.QrCodeJson qrCodeJson;

    private int factor;
    private int money;
    private int lll;
    private int type;
    private String oil_id = "";
    public TextView selectView;

    private List<RanLiaoInfo> mRanLiaoInfos;
    private CustomerRcAdapter mCustomerRcAdapter;
    private YhqAdapter mYhqAdapter;//未使用优惠券的列表
    private List<Info> mInfos;
    private String coupon_ids = ""; //优惠卷id  如下两个对应上传
    private String nums = ""; //  数量
    private YhqUsedAdapter mYhqUsedAdapter;//使用掉的优惠券的列表
    private List<YouhuiQuanUsedResponse.Info> mInfos_used;

    private int whatCoumn = 0;
    private String mEdtPass = "";
    private List<RanLiaoInfo> mDialogRanLiaoInfos;/*弹窗中的颜料集合*/
    private CustomerRcAdapter mDialogCustomerAdapter;
    private TextView mTv_jifen;
    private EditText mEdt_money;
    private String oil_id_agin = "";//弹窗使用的id
    private List<LiPinItem> mLiPinItems;
    private LiPinAdapter mLiPinAdapter;
    private String mIs_del = "1";
    private boolean isJifen = true;


    public CustomerLock(Context context, ActivityCustomerBinding customerBinding) {
        this.customerBinding = customerBinding;
        this.context = context;
        customerBinding.customerTvJifen.requestFocus();
        customerAdapter = new BaseTopAdapter(context, customerList, R.layout.customer_item, BR.customerItem);
        init();
        initData();
        getRanliao();
        getYouHuiQuan();
    }

    public void initData() {

        mLiPinAdapter = new LiPinAdapter(context, mLiPinItems);

        /*燃料*/
        mCustomerRcAdapter = new CustomerRcAdapter(context, mRanLiaoInfos);
        mDialogCustomerAdapter = new CustomerRcAdapter(context, mDialogRanLiaoInfos);

        LinearLayoutManager layoutManager = new LinearLayoutManager(context);/*设置为横向滚动*/
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        customerBinding.customerRcType.setLayoutManager(layoutManager);
        customerBinding.customerRcType.setAdapter(mCustomerRcAdapter);
        /*未使用优惠券*/
        mInfos = new ArrayList<>();
        mYhqAdapter = new YhqAdapter(context, mInfos);
        /*已使用优惠券*/
        mInfos_used = new ArrayList<>();
        mYhqUsedAdapter = new YhqUsedAdapter(context, mInfos_used);
        /*燃料的选项监听*/
        mCustomerRcAdapter.setOnItemClickListener(new CustomerRcAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                for (int i = 0; i < mRanLiaoInfos.size(); i++) {
                    RanLiaoInfo ranLiaoInfo = mRanLiaoInfos.get(i);
                    if (i == position) {
                        ranLiaoInfo.isC = true;
                        oil_id = ranLiaoInfo.id;
                        mIs_del = ranLiaoInfo.is_del;
                    } else {
                        ranLiaoInfo.isC = false;
                    }
                }
                getJifen();
                mCustomerRcAdapter.notifyDataSetChanged();
            }
        });
        /*弹出的dialog的监听*/
        mDialogCustomerAdapter.setOnItemClickListener(new CustomerRcAdapter.OnItemClickListener() {
            @Override
            public void OnItemClickListener(int position) {
                for (int i = 0; i < mDialogRanLiaoInfos.size(); i++) {
                    RanLiaoInfo ranLiaoInfo = mDialogRanLiaoInfos.get(i);
                    if (i == position) {
                        ranLiaoInfo.isC = true;
                        oil_id_agin = ranLiaoInfo.id;
                    } else {
                        ranLiaoInfo.isC = false;
                    }
                }
                getJifenAgin(mTv_jifen, mEdt_money.getText().toString(), oil_id_agin);
                mDialogCustomerAdapter.notifyDataSetChanged();
            }
        });

        /*优惠券加减的监听*/
        mYhqAdapter.setOnShuLianClickListener(new YhqAdapter.OnShuLianClickListener() {
            @Override
            public void OnShuLianClickListener(int view_id, int position) {
                Info info = mInfos.get(position);
                boolean can_use = info.isCan_use();
                if (can_use) {
                    int use_num = info.use_num;
                    String copnum = info.copnum;
                    int allnum = Integer.parseInt(copnum);
                    switch (view_id) {
                        case R.id.item_yhq_jia:
                            if (use_num < allnum) {
                                info.setUse_num(use_num + 1);
                            }
                            break;
                        case R.id.item_yhq_jian:
                            if (use_num > 0) {
                                info.setUse_num(use_num - 1);
                                info.setUse_num(use_num - 1);
                            }
                            break;
                    }
                    mYhqAdapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(context, "需满" + info.xuman_price + "才可使用", Toast.LENGTH_SHORT).show();
                }
            }
        });
        /*恢复程序*/
        mYhqUsedAdapter.setOnShuLianClickListener(new YhqUsedAdapter.OnShuLianClickListener() {
            @Override
            public void OnShuLianClickListener(int view_id, final int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示").setMessage("是否恢复").setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        toRecover(mInfos_used.get(position).coupon_id, mInfos_used.get(position).id);
                    }
                }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();
            }
        });
        customerBinding.tableLayout.addTab(customerBinding.tableLayout.newTab().setText("优惠券"));
        customerBinding.tableLayout.addTab(customerBinding.tableLayout.newTab().setText("加油记录"));
        customerBinding.tableLayout.addTab(customerBinding.tableLayout.newTab().setText("礼品兑换"));
        customerBinding.tableLayout.addTab(customerBinding.tableLayout.newTab().setText("修改"));
        customerBinding.tableLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        whatCoumn = 0;
                        customerBinding.customerYhqLl.setVisibility(View.VISIBLE);
                        customerBinding.customerList.setVisibility(View.GONE);
                        customerBinding.cardCatList.setVisibility(View.VISIBLE);
                        customerBinding.customerTvt.setText("剩余数量");
                        customerBinding.customerTvtt.setText("使用数量");
                        getYouHuiQuan();
                        break;
                    case 1:
                        whatCoumn = 1;
                        customerBinding.customerYhqLl.setVisibility(View.GONE);
                        customerBinding.customerList.setVisibility(View.VISIBLE);
                        customerBinding.cardCatList.setVisibility(View.GONE);
                        break;
                    case 2:
                        whatCoumn = 2;
                        customerBinding.customerYhqLl.setVisibility(View.GONE);
                        customerBinding.customerList.setVisibility(View.GONE);
                        customerBinding.cardCatList.setVisibility(View.VISIBLE);
                        getLiPin();
                        break;
                    case 3:
                        showRemindDialog();
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        customerBinding.customerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomerItem customerItem = customerList.get(position);
                showRemindJyjlDialog(customerItem);
            }
        });

        mLiPinAdapter.setOnItemClickListener(new LiPinAdapter.OnItemClickListener() {
            @Override
            public void OnItemClikcListner(final int position) {
                final LiPinItem liPinItem = mLiPinItems.get(position);
                final String type = liPinItem.getType();
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("提示").setMessage("是否兑换").setPositiveButton("是", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (type.equals("1")) {/*订单*/
                            toDuihuanDd(liPinItem.getId());
                        } else {
                            toDuihuanQD(liPinItem.getId());
                        }
                    }
                }).setNegativeButton("否", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create().show();

            }
        });
    }

    /*是否积分*/
    public void jifen() {
        isJifen = !isJifen;
        if (!isJifen) {/*不积分*/
            customerBinding.customerJf.setText("0");
        } else {/*积分*/
            getJifen();
        }
    }

    /*订单兑换*/
    public void toDuihuanDd(final String order_id) {
        HashMap hashMap = new HashMap();
        hashMap.put("order_id", order_id);
        hashMap.put("s_id", User.getUser().sId);

        HttpUtil.getInstance().postRequest(Operation.PAY_PUSH, hashMap, HttpReponse.class, new HttpCallListener<HttpReponse>() {
            @Override
            public void Start(String URL) {
            }

            @Override
            public void Success(String URL, @NonNull final HttpReponse bean) {
                ((CustomerActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean.code.equals("200")) {
                            Toast.makeText(context, "兑换成功", Toast.LENGTH_SHORT).show();
                            getLiPin();
                            goToPrintLipin(order_id);
                        } else {
                            Toast.makeText(context, "兑换失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void Error(String URL) {
            }
        });
    }

    /*签到兑换*/
    public void toDuihuanQD(final String prize_id) {
        HashMap hashMap = new HashMap();
        hashMap.put("id", prize_id);
        hashMap.put("s_id", User.getUser().sId);
        if (qrCodeJson == null) {
            Toast.makeText(context, "用户信息获取失败,请重新进行扫描", Toast.LENGTH_SHORT).show();
            return;
        }
        hashMap.put("user_id", qrCodeJson.userId);
        Log.e("签到兑换", "?prize_id=" + prize_id + "&s_id=" + User.getUser().sId + "&user_id=" + qrCodeJson.userId);
        HttpUtil.getInstance().postRequest(Operation.SIGN_PUSH, hashMap, HttpReponse.class, new HttpCallListener<HttpReponse>() {
            @Override
            public void Start(String URL) {
            }

            @Override
            public void Success(String URL, @NonNull final HttpReponse bean) {
                ((CustomerActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean.code.equals("200")) {
                            Toast.makeText(context, "兑换成功", Toast.LENGTH_SHORT).show();
                            getLiPin();
                            gotoPrintQDLipin(prize_id);
                        } else {
                            Toast.makeText(context, "兑换失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void Error(String URL) {
            }
        });
    }

    /*订单商品打印*/
    public void goToPrintLipin(String id) {
        HashMap hashMap = new HashMap();
        hashMap.put("id", id);
        hashMap.put("types", "1");
        hashMap.put("s_id", User.getUser().sId);
        HttpUtil.getInstance().postRequest(Operation.DAYINLIST, hashMap, GoodsPrintResponse.class, new HttpCallListener<GoodsPrintResponse>() {
            @Override
            public void Start(String URL) {
            }

            @Override
            public void Success(String URL, @NonNull final GoodsPrintResponse bean) {
                ((CustomerActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean.code.equals("200")) {
                            PrintPrintGoods(bean);
                        } else {
                            Toast.makeText(context, "参数丢失", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void Error(String URL) {
            }
        });
    }

    /*签到商品打印*/
    public void gotoPrintQDLipin(String id) {
        HashMap hashMap = new HashMap();
        hashMap.put("id", id);
        hashMap.put("types", "2");
        hashMap.put("s_id", User.getUser().sId);
        HttpUtil.getInstance().postRequest(Operation.DAYINLIST, hashMap, LiPinPrintResponse.class, new HttpCallListener<LiPinPrintResponse>() {
            @Override
            public void Start(String URL) {
            }

            @Override
            public void Success(String URL, @NonNull final LiPinPrintResponse bean) {
                ((CustomerActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean.code.equals("200")) {
                            DDPrintGoods(bean);
                        } else {
                            Toast.makeText(context, "参数丢失", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void Error(String URL) {
            }
        });
    }


    /*获取用户可以兑换的礼品*/
    public void getLiPin() {
        LinearLayoutManager ms = new LinearLayoutManager(context);
        ms.setOrientation(LinearLayoutManager.VERTICAL);
        customerBinding.cardCatList.setLayoutManager(ms);
        customerBinding.cardCatList.setAdapter(mLiPinAdapter);
        HashMap hashMap = new HashMap();
        if (qrCodeJson == null) {
            Toast.makeText(context, "用户信息获取失败,请重新进行扫描", Toast.LENGTH_SHORT).show();
            return;
        }
        hashMap.put("user_id", qrCodeJson.userId);

        HttpUtil.getInstance().postRequest(Operation.PROLIST, hashMap, LiPinResponse.class, new HttpCallListener<LiPinResponse>() {
            @Override
            public void Start(String URL) {
            }

            @Override
            public void Success(String URL, @NonNull final LiPinResponse bean) {
                ((CustomerActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean.code.equals("200")) {
                            whatCoumn = 2;
                            mLiPinItems.clear();
                            for (LiPinResponse.OrderBean orderBean : bean.getOrder()) {
                                mLiPinItems.add(new LiPinItem(orderBean.getOrderId(), orderBean.getOriginalImg(), orderBean.getGoodsName(), orderBean.getAddTime(), orderBean.getTypes()));
                            }
                            for (LiPinResponse.QiandaoBean qiandaoBean : bean.getQiandao()) {
                                mLiPinItems.add(new LiPinItem(qiandaoBean.getId(), qiandaoBean.getGoodsImg(), qiandaoBean.getGoodsName(), qiandaoBean.getSendTime(), qiandaoBean.getTypes()));
                            }
                            mLiPinAdapter.notifyDataSetChanged();

                        }
                    }
                });
            }

            @Override
            public void Error(String URL) {
            }
        });
    }


    /*展示输入密码弹窗*/
    public void showRemindDialog() {
//        whatCoumn
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_remind, null);
        final EditText edt_pass = (EditText) v.findViewById(R.id.dialog_pass);
        TextView tv_cancel = (TextView) v.findViewById(R.id.dialog_cancel);
        TextView tv_sure = (TextView) v.findViewById(R.id.dialog_sure);
        builder.setView(v);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdtPass = edt_pass.getText().toString();
                if (mEdtPass == null || mEdtPass.equals("")) {
                    Toast.makeText(context, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                alertDialog.dismiss();
                getUseYHQ(mEdtPass);

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerBinding.tableLayout.getTabAt(whatCoumn).select();
                alertDialog.dismiss();
            }
        });
    }

    /*点击加油记录条目时弹出修改金额的弹窗*/
    public void showRemindJyjlDialog(CustomerItem customerItem) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        View v = LayoutInflater.from(context).inflate(R.layout.dialog_remind_jyjl, null);
        final EditText edt_pass = (EditText) v.findViewById(R.id.dialog_pass);
        mEdt_money = (EditText) v.findViewById(R.id.dialog_money);
        TextView tv_cancel = (TextView) v.findViewById(R.id.dialog_cancel);
        TextView tv_sure = (TextView) v.findViewById(R.id.dialog_sure);
        mTv_jifen = (TextView) v.findViewById(R.id.dialog_tv_jifen);
        RecyclerView mrc = (RecyclerView) v.findViewById(R.id.customer_rc_type);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);/*设置为横向滚动*/
        layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mrc.setLayoutManager(layoutManager);
        mrc.setAdapter(mDialogCustomerAdapter);
        final String id = customerItem.id;
        oil_id_agin = customerItem.oil_type;
        for (RanLiaoInfo ranLiaoInfo : mDialogRanLiaoInfos) {
            if (ranLiaoInfo.id.equals(oil_id_agin)) {
                ranLiaoInfo.isC = true;
            } else {
                ranLiaoInfo.isC = false;
            }
        }
        mDialogCustomerAdapter.notifyDataSetChanged();


        builder.setView(v);
        final AlertDialog alertDialog = builder.create();
        alertDialog.setCanceledOnTouchOutside(false);
        alertDialog.show();

        mEdt_money.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    getJifenAgin(mTv_jifen, mEdt_money.getText().toString(), oil_id_agin);
                }
            }
        });

        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEdtPass = edt_pass.getText().toString();
                if (mEdtPass == null || mEdtPass.equals("")) {
                    Toast.makeText(context, "密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                alertDialog.dismiss();
                Altermsg(mEdtPass, mEdt_money.getText().toString(), mTv_jifen.getText().toString(), id, oil_id_agin);

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customerBinding.tableLayout.getTabAt(whatCoumn).select();
                alertDialog.dismiss();
            }
        });
    }

    /*修改加油记录的金额*/
    public void Altermsg(String pwd, String money, String jifen, String jilu_id, String oil_type) {
        HashMap hashMap = new HashMap();
        hashMap.put("er_pwd", pwd);
        hashMap.put("jilu_id", jilu_id);
        hashMap.put("oil_type", oil_type);
        hashMap.put("money", money);
        hashMap.put("jifen", jifen);
        HttpUtil.getInstance().postRequest(Operation.XIUOILJILU, hashMap, HttpReponse.class, new HttpCallListener<HttpReponse>() {
            @Override
            public void Start(String URL) {
            }

            @Override
            public void Success(String URL, @NonNull final HttpReponse bean) {
                ((CustomerActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean.code.equals("200")) {
                            codeMain();
                        }
                    }
                });
            }

            @Override
            public void Error(String URL) {
            }
        });
    }


    private void init() {
        customerData = new CustomerData();
        customerList = new ArrayList();
        mRanLiaoInfos = new ArrayList<>();
        mDialogRanLiaoInfos = new ArrayList<>();
        mLiPinItems = new ArrayList<>();

        qrCodeJson = (MainActivity.QrCodeJson) RunTime.getRunTime(RunTime.CODE_TEXT);
        if (qrCodeJson != null) {
            codeMain();
        } else {

        }
        customerBinding.customerEdt.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s != null && s.length() != 0) {
                    int money = Integer.parseInt(s + "");
                    for (Info info : mInfos) {
                        int xuman_money = Integer.parseInt(info.xuman_price);
                        if (money < xuman_money) {
//                            info.setCan_use(false);
                            info.setCan_use(true);
                        } else {
                            info.setCan_use(true);
                        }
                    }
                } else {
                    for (Info info : mInfos) {
                        if (info.xuman_price.equals("0")) {
                            info.setCan_use(true);
                        } else {
//                            info.setCan_use(false);
                            info.setCan_use(true);
                        }
                    }
                }
                mYhqAdapter.notifyDataSetChanged();

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() != 0) {
                    if(isJifen) {
                        getJifen();
                    }
                }
            }
        });

    }

    /*获取燃料数据*/
    public void getRanliao() {
        HashMap hashMap = new HashMap();
        HttpUtil.getInstance().postRequest(Operation.GETRANLIAO, hashMap, RanliaoResponse.class, new HttpCallListener<RanliaoResponse>() {
            @Override
            public void Start(String URL) {
            }

            @Override
            public void Success(String URL, @NonNull final RanliaoResponse bean) {
                if (bean.code.equals("100")) {
                    ((CustomerActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mRanLiaoInfos.clear();
                            mDialogRanLiaoInfos.clear();
                            for (int i = 0; i < bean.info.size(); i++) {/*添加到集合当中*/
                                RanliaoResponse.Info info = bean.info.get(i);
                                if (i == 0) {
                                    mRanLiaoInfos.add(new RanLiaoInfo(info.id, info.you_name, info.add_time, info.is_del, true));
                                } else {
                                    mRanLiaoInfos.add(new RanLiaoInfo(info.id, info.you_name, info.add_time, info.is_del, false));
                                }
                                mDialogRanLiaoInfos.add(new RanLiaoInfo(info.id, info.you_name, info.add_time, info.is_del, false));/*弹窗中的颜料集合*/
                            }
                            mCustomerRcAdapter.notifyDataSetChanged();
                            mDialogCustomerAdapter.notifyDataSetChanged();/*弹窗中的进行更新*/
                            oil_id = bean.info.get(0).id;
                        }
                    });
                    getShop();
                }
            }

            @Override
            public void Error(String URL) {
            }
        });
    }

    /*获取燃料后面的*/
    public void getShop() {
        HashMap hashMap = new HashMap();
        HttpUtil.getInstance().postRequest(Operation.GETSHOP, hashMap, ShopBean.class, new HttpCallListener<ShopBean>() {
            @Override
            public void Start(String URL) {
            }

            @Override
            public void Success(String URL, @NonNull final ShopBean bean) {
                if (bean.code.equals("100")) {
                    ((CustomerActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            mRanLiaoInfos.clear();
//                            mDialogRanLiaoInfos.clear();
                            for (int i = 0; i < bean.info.size(); i++) {/*添加到集合当中*/
                                ShopBean.Info info = bean.info.get(i);
                                mRanLiaoInfos.add(new RanLiaoInfo(info.cx_id, info.cx_name, info.cx_add_time, "6", false));
                                mDialogRanLiaoInfos.add(new RanLiaoInfo(info.cx_id, info.cx_name, info.cx_add_time, "6", false));/*弹窗中的颜料集合*/
                            }
                            mCustomerRcAdapter.notifyDataSetChanged();
                            mDialogCustomerAdapter.notifyDataSetChanged();/*弹窗中的进行更新*/
                        }
                    });
                }
            }

            @Override
            public void Error(String URL) {
            }
        });
    }


    /*恢复程序*/
    public void toRecover(String coup_id, String state_id) {
        HashMap hashMap = new HashMap();
        if (qrCodeJson == null) {
            Toast.makeText(context, "用户信息获取失败,请重新进行扫描", Toast.LENGTH_SHORT).show();
            return;
        }
        hashMap.put("user_id", qrCodeJson.userId);
        hashMap.put("coupon_id", coup_id);
        hashMap.put("state_id", state_id);
        HttpUtil.getInstance().postRequest(Operation.RECOVER, hashMap, HttpReponse.class, new HttpCallListener<HttpReponse>() {
            @Override
            public void Start(String URL) {
            }

            @Override
            public void Success(String URL, @NonNull final HttpReponse bean) {
                ((CustomerActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean.code.equals("200")) {
                            Toast.makeText(context, "恢复成功", Toast.LENGTH_SHORT).show();
                            getUseYHQ(mEdtPass);
                        }
                    }
                });
            }

            @Override
            public void Error(String URL) {
            }
        });
    }

    /*获取使用掉的优惠券列表(为了防止使用错误恢复)*/
    public void getUseYHQ(String passW) {
        LinearLayoutManager ms = new LinearLayoutManager(context);
        ms.setOrientation(LinearLayoutManager.VERTICAL);
        customerBinding.cardCatList.setLayoutManager(ms);
        customerBinding.cardCatList.setAdapter(mYhqUsedAdapter);
        HashMap hashMap = new HashMap();
        if (qrCodeJson == null) {
            Toast.makeText(context, "用户信息获取失败,请重新进行扫描", Toast.LENGTH_SHORT).show();
            return;
        }
        hashMap.put("user_id", qrCodeJson.userId);
        hashMap.put("er_pwd", passW);

        HttpUtil.getInstance().postRequest(Operation.CARDLIST, hashMap, YouhuiQuanUsedResponse.class, new HttpCallListener<YouhuiQuanUsedResponse>() {
            @Override
            public void Start(String URL) {
            }

            @Override
            public void Success(String URL, @NonNull final YouhuiQuanUsedResponse bean) {
                ((CustomerActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean.code.equals("200")) {
                            mInfos_used.clear();
                            mInfos_used.addAll(bean.info);
                            mYhqUsedAdapter.notifyDataSetChanged();

                            whatCoumn = 3;
                            customerBinding.customerYhqLl.setVisibility(View.VISIBLE);
                            customerBinding.customerList.setVisibility(View.GONE);
                            customerBinding.cardCatList.setVisibility(View.VISIBLE);
                            customerBinding.customerTvt.setText("使用时间");
                            customerBinding.customerTvtt.setText("操作");

                        } else if (bean.code.equals("400")) {
                            customerBinding.tableLayout.getTabAt(whatCoumn).select();
                        }
                    }
                });
            }

            @Override
            public void Error(String URL) {
            }
        });
    }

    /*获取用户优惠券列表*/
    public void getYouHuiQuan() {
        customerBinding.customerEdt.setText("");
        LinearLayoutManager ms = new LinearLayoutManager(context);
        ms.setOrientation(LinearLayoutManager.VERTICAL);
        customerBinding.cardCatList.setLayoutManager(ms);
        customerBinding.cardCatList.setAdapter(mYhqAdapter);
        HashMap hashMap = new HashMap();
        if (qrCodeJson == null) {
            Toast.makeText(context, "用户信息获取失败,请重新进行扫描", Toast.LENGTH_SHORT).show();
            return;
        }
        hashMap.put("user_id", qrCodeJson.userId);
        hashMap.put("s_id", User.getUser().sId);
        HttpUtil.getInstance().postRequest(Operation.CARDFENLEI, hashMap, YouhuiQuanReponse.class, new HttpCallListener<YouhuiQuanReponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final YouhuiQuanReponse bean) {
                ((CustomerActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean.code.equals("200")) {
                            mInfos.clear();
                            for (YouhuiQuanReponse.Info info : bean.info) {
                                Info inf = new Info(info.coupon_id, info.copnum, info.name, info.xuman_price, info.money, 0);
                                if (info.xuman_price.equals("0")) {
                                    inf.setCan_use(true);
                                } else {
                                    inf.setCan_use(true);
//                                    inf.setCan_use(false);
                                }
                                mInfos.add(inf);
                            }
                            mYhqAdapter.notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "暂无优惠券", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void Error(String URL) {

            }
        });
    }

    /*获取需要显示的积分(加油赠积分)数据*/
    public void getJifen() {
        HashMap hashMap = new HashMap();
        if (qrCodeJson == null) {
            Toast.makeText(context, "用户信息获取失败,请重新进行扫描", Toast.LENGTH_SHORT).show();
            return;
        }
        hashMap.put("user_id", qrCodeJson.userId);
        hashMap.put("s_id", User.getUser().sId);
        hashMap.put("money", customerData.payNum);
        hashMap.put("oil_type", oil_id);
        if (mIs_del.equals("1")) {
            hashMap.put("list_type", "1");
        } else if (mIs_del.equals("6")) {
            hashMap.put("list_type", "2");
        }
        HttpUtil.getInstance().postRequest(Operation.GETJIFEN, hashMap, JifenResponse.class, new HttpCallListener<JifenResponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final JifenResponse bean) {
                ((CustomerActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean.code.equals("200")) {
                            if (bean.jifen == null) {
                                customerBinding.customerJf.setText("0");
                            } else {
                                customerBinding.customerJf.setText(bean.jifen);
                            }
                        } else {
                            customerBinding.customerJf.setText("0000");
                            Toast.makeText(context, "提交失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void Error(String URL) {

            }
        });
    }

    /*弹出的Dialog修改计算积分*/
    public void getJifenAgin(final TextView mtv, String payMoney, String oil_id) {
        HashMap hashMap = new HashMap();
        if (qrCodeJson == null) {
            Toast.makeText(context, "用户信息获取失败,请重新进行扫描", Toast.LENGTH_SHORT).show();
            return;
        }
        hashMap.put("user_id", qrCodeJson.userId);
        hashMap.put("s_id", User.getUser().sId);
        hashMap.put("money", payMoney);
        hashMap.put("oil_type", oil_id);
        HttpUtil.getInstance().postRequest(Operation.GETJIFEN, hashMap, JifenResponse.class, new HttpCallListener<JifenResponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final JifenResponse bean) {
                ((CustomerActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean.code.equals("200")) {
                            if (bean.jifen == null) {
                                mtv.setText("0");
                            } else {
                                mtv.setText(bean.jifen);
                            }
                        } else {
                            mtv.setText("0000");
                            Toast.makeText(context, "提交失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void Error(String URL) {

            }
        });
    }

    /*初始化数据(加油记录)*/
    private void codeMain() {
        customerList.clear();
        // 解决惊天大BUG
        customerBinding.customerList.setVisibility(View.GONE);
        customerAdapter = new BaseTopAdapter(context, customerList, R.layout.customer_item, BR.customerItem);
        customerBinding.customerList.setAdapter(customerAdapter);
        HashMap hashMap = new HashMap();
        if (qrCodeJson == null) {
            Toast.makeText(context, "用户信息获取失败,请重新进行扫描", Toast.LENGTH_SHORT).show();
            return;
        }
        hashMap.put("user_id", qrCodeJson.userId);
        hashMap.put("s_id", User.getUser().sId);
        HttpUtil.getInstance().postRequest(Operation.USER_OIL_HIS, hashMap, UserOilHisReponse.class, new HttpCallListener<UserOilHisReponse>() {
            @Override
            public void Start(String URL) {
                customerBinding.couponNull.setText("暂无加油记录");
            }

            @Override
            public void Success(String URL, @NonNull final UserOilHisReponse bean) {
                if (bean.code.equals("201")) {
                    Toast.makeText(context, "用户绑定的不是该加油站，无法获取积分", Toast.LENGTH_SHORT).show();
                }
                customerData.userLll = bean.info.balance;
                customerData.userName = bean.info.nickname;
                type = parseInt(bean.info.type);
                if (type == 1) {
                    money = parseInt(bean.info.money);
                    lll = parseInt(bean.info.jifen);
                } else {
                    factor = parseInt(bean.info.bili);
                }
                ((CustomerActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean.info.jilu.size() == 0) {
                            customerBinding.couponNull.setVisibility(View.VISIBLE);//没有加油记录时
                        } else {
                            customerBinding.couponNull.setVisibility(View.GONE);
                        }
                    }
                });

                Date date = new Date();
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                for (UserOilHisReponse.Jilu jilu : bean.info.jilu) {
                    date.setTime(Long.parseLong(jilu.add_time + "000"));
                    jilu.add_time = formatter.format(date);
                    customerList.add(new CustomerItem(bean.info.nickname, jilu.add_time, "￥" + jilu.money, jilu.id, jilu.oil_type));
                }

                // 有时候这个不行
                ((CustomerActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(context)
                                .load(bean.info.head_pic)
                                .apply(new RequestOptions().placeholder(R.mipmap.def_usericon))
                                .into(customerBinding.userIcon)
                                .onLoadFailed(context.getResources().getDrawable(R.mipmap.def_usericon));

                        customerAdapter.notifyDataSetChanged();
                        if (whatCoumn == 1) {
                            customerBinding.customerYhqLl.setVisibility(View.GONE);
                            customerBinding.customerList.setVisibility(View.VISIBLE);
                            customerBinding.cardCatList.setVisibility(View.GONE);
                        }
                        customerData.notifyChange();
                    }
                });

            }

            @Override
            public void Error(String URL) {

            }
        });
    }

    /*获取选择使用的的优惠券的总金额*/
    public String getUserYHQMoney() {
        int allmoney = 0;
        for (int i = 0; i < mInfos.size(); i++) {
            Info info = mInfos.get(i);
            if (info.getUse_num() != 0 && info.isCan_use()) {
                allmoney += (info.use_num * Integer.parseInt(info.money));
            }
        }
        return allmoney + "";
    }

    /*获取选择的提交优惠券的字段数据*/
    public void getCommitData() {
        coupon_ids = "";
        nums = "";
        for (int i = 0; i < mInfos.size(); i++) {
            Info info = mInfos.get(i);
            if (info.getUse_num() != 0 && info.isCan_use()) {
                coupon_ids += info.coupon_id + ",";
                nums += info.getUse_num() + ",";
            }
        }
        if (!coupon_ids.equals("")) {
            coupon_ids = coupon_ids.substring(0, coupon_ids.length() - 1);
        }
        if (!nums.equals("")) {
            nums = nums.substring(0, nums.length() - 1);
        }
    }

    /*点击确定*/
    public void oilEnter() {
        getCommitData();
        if (customerData.payNum == null || customerData.payNum.equals("")) {
            Toast.makeText(context, "还未输入加油金额", Toast.LENGTH_SHORT).show();
            return;
        }
        if (qrCodeJson == null) {
            Toast.makeText(context, "用户信息获取失败,请重新进行扫描", Toast.LENGTH_SHORT).show();
            return;
        }
        customerBinding.textView2.setEnabled(false);
        customerBinding.textView2.setText("提交中,请稍后");
        customerBinding.textView2.setBackgroundColor(context.getResources().getColor(R.color.gray));
        HashMap hashMap = new HashMap();
        hashMap.put("admin_id", User.getUser().adminId);
        hashMap.put("oil_type", oil_id);
        hashMap.put("s_id", User.getUser().sId);
        hashMap.put("money", customerBinding.customerEdt.getText().toString());
//        hashMap.put("cop_money", getUserYHQMoney());
        hashMap.put("coupon_ids", coupon_ids);
        hashMap.put("nums", nums);
        if (mIs_del.equals("1")) {
            hashMap.put("list_type", "1");
        } else if (mIs_del.equals("6")) {
            hashMap.put("list_type", "2");
        }
//        Log.e("新街口上传参数", "?admin_id=" + User.getUser().adminId + "&oil_type=" + oil_id + "&money=" +
//                customerBinding.customerEdt.getText().toString() + "&cop_money=" + getUserYHQMoney());
        HttpUtil.getInstance().postRequest(Operation.OILXIANZHI, hashMap, HttpReponse.class, new HttpCallListener<HttpReponse>() {
            @Override
            public void Start(String URL) {
            }

            @Override
            public void Success(String URL, @NonNull final HttpReponse bean) {
                ((CustomerActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean.code.equals("200")) {
                            toJiaYou();
                        } else {
                            customerBinding.textView2.setEnabled(true);
                            customerBinding.textView2.setText("确定");
                            customerBinding.textView2.setBackgroundColor(context.getResources().getColor(R.color.main_color));
                            Toast.makeText(context, bean.message, Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void Error(String URL) {
                ((CustomerActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        customerBinding.textView2.setEnabled(true);
                        customerBinding.textView2.setText("确定");
                        customerBinding.textView2.setBackgroundColor(context.getResources().getColor(R.color.main_color));
                        Toast.makeText(context, "提交失败，网络不通或用户未登录", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }

    /*进行加油*/
    public void toJiaYou() {
        getCommitData();
        HashMap hashMap = new HashMap();
        if (qrCodeJson == null) {
            Toast.makeText(context, "用户信息获取失败,请重新进行扫描", Toast.LENGTH_SHORT).show();
            return;
        }
        hashMap.put("user_id", qrCodeJson.userId);
        hashMap.put("admin_id", User.getUser().adminId);
        hashMap.put("money", customerData.payNum);
        hashMap.put("jifen", customerBinding.customerJf.getText());
        hashMap.put("s_id", User.getUser().sId);
        hashMap.put("oil_type", oil_id);
        hashMap.put("coupon_ids", coupon_ids);
        hashMap.put("nums", nums);
        if (mIs_del.equals("1")) {
            hashMap.put("list_type", "1");
        } else if (mIs_del.equals("6")) {
            hashMap.put("list_type", "2");
        }
        Log.e("加油提交数据:", "?user_id=" + qrCodeJson.userId + "&admin_id=" + User.getUser().adminId + "&money=" + customerData.payNum + "&jifen=" +
                customerBinding.customerJf.getText() + "&s_id=" + User.getUser().sId
                + "&oil_type=" + oil_id + "&coupon_ids=" + coupon_ids + "&nums=" + nums);
        HttpUtil.getInstance().postRequest(Operation.OIL_PUSH, hashMap, JYouYhqResponse.class, new HttpCallListener<JYouYhqResponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final JYouYhqResponse bean) {
                ((CustomerActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean.code.equals("200")) {
                            // TODO: 2018/3/8 添加打印功能 （PrintUtil 中有打印代码)
                            if (bean.getOrder().size() != 0) {
                                gotoPrint(bean);/*前去打印*/
                            }
                            PopManager popManager = new PopManager(context);
                            PopToastBinding popToastBinding = (PopToastBinding) popManager.showPop(customerBinding.textView, R.layout.pop_toast);
                            popToastBinding.popText.setText(context.getResources().getString(R.string.infosucc));
                            popManager.setMissLisenter(new PopManager.MissLisenter() {
                                @Override
                                public void miss() {
                                    ((CustomerActivity) context).finish();
                                }
                            });
                            popToastBinding.popEnter.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    ((CustomerActivity) context).finish();
                                }
                            });
                            sendTS(bean);
                        } else {
                            customerBinding.textView2.setEnabled(true);
                            customerBinding.textView2.setText("确定");
                            customerBinding.textView2.setBackgroundColor(context.getResources().getColor(R.color.main_color));
                            Toast.makeText(context, bean.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void Error(String URL) {
                ((CustomerActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        customerBinding.textView2.setEnabled(true);
                        customerBinding.textView2.setText("确定");
                        customerBinding.textView2.setBackgroundColor(context.getResources().getColor(R.color.main_color));
                        Toast.makeText(context, "提交失败，网络不通或用户未登录", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    /*调用加油成功的推送*/
    public void sendTS(JYouYhqResponse bean) {
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", bean.getSends().getUserId());
        hashMap.put("jifen", bean.getSends().getJifen());
        hashMap.put("cop_num", bean.getSends().getCopNum());
        HttpUtil.getInstance().postRequest(Operation.SENDS, hashMap, HttpReponse.class, new HttpCallListener<HttpReponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final HttpReponse bean) {
                ((CustomerActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                    }
                });
            }

            @Override
            public void Error(String URL) {
            }
        });

    }


    /*订单商品打印*/
    public void PrintPrintGoods(GoodsPrintResponse bean) {
        AidlUtil.getInstance().printText(getPrintPrictContent(bean), 24, false, false);/*Aidl打印*/
    }

    private String getPrintPrictContent(GoodsPrintResponse bean) {
        String couponName = (String) bean.getOrder().getGoodsName();
        if (couponName != null) {
        } else {
            couponName = "                    ";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("      油品惠兑换凭证");
        builder.append("\n兑换日期:" + bean.getOrder().getAddTime());
        builder.append("\n账号:" + bean.getUser().getMobile());
        builder.append("\n车牌号:" + bean.getUser().getCarNumber());
        builder.append("\n客户姓名:" + bean.getUser().getNickname());
        builder.append("\n\n员工编号:" + bean.getStaff().getNumber());
        builder.append("\n员工姓名:" + bean.getStaff().getName());
        builder.append("\n账号:" + bean.getStaff().getAccount());
        builder.append("\n商品名称" + "  数量" + " 单价" + " 金额\n");
        builder.append(couponName + "\n");
        builder.append("           1  " + bean.getOrder().getMoney() + "  " + bean.getOrder().getMoney());
        builder.append("\n\n\n\n" + "兑换商品\n" + "使用现金:" + bean.getOrder().getMoney() + "\n使用积分:" + bean.getOrder().getJifen());
        builder.append("\n客户签名:\n\n\n\n\n\n\n");
        return builder.toString();
    }

    /*签到礼品商品打印*/
    public void DDPrintGoods(LiPinPrintResponse bean) {
        AidlUtil.getInstance().printText(getPrintContent(bean), 24, false, false);/*Aidl打印*/
    }

    private String getPrintContent(LiPinPrintResponse bean) {
        String couponName = (String) bean.getOrder().getGoodsName();
        if (couponName != null) {
        } else {
            couponName = "                    ";
        }
        StringBuilder builder = new StringBuilder();
        builder.append("      油品惠兑换凭证");
        builder.append("\n兑换日期:" + bean.getOrder().getDate());
        builder.append("\n账号:" + bean.getUser().getMobile());
        builder.append("\n车牌号:" + bean.getUser().getCarNumber());
        builder.append("\n客户姓名:" + bean.getUser().getNickname());
        builder.append("\n\n员工编号:" + bean.getStaff().getNumber());
        builder.append("\n员工姓名:" + bean.getStaff().getName());
        builder.append("\n账号:" + bean.getStaff().getAccount());
        builder.append("\n商品名称" + "  数量" + " 单价" + " 金额\n");
        builder.append(couponName + "\n");
        builder.append("           1  " + bean.getOrder().getShopPrice() + "  " + bean.getOrder().getShopPrice());
        builder.append("\n\n\n\n" + "兑换商品\n" + "商品价格:" + bean.getOrder().getShopPrice() + "\n");
        builder.append("\n客户签名:\n\n\n\n\n\n\n");
        return builder.toString();
    }


    /*使用加油券进行打印操作*/
    public void gotoPrint(JYouYhqResponse bean) {
        AidlUtil.getInstance().printText(getPrintContent(bean), 24, false, false);/*Aidl打印*/
        Log.e("打印的数据", getPrintContent(bean));

    }

    private String getPrintContent(JYouYhqResponse bean) {
        StringBuilder builder = new StringBuilder();
        builder.append("      油品惠兑换凭证");
        builder.append("\n兑换日期:" + bean.getDate());
        builder.append("\n账号:" + bean.getUser().getMobile());
        builder.append("\n车牌号:" + bean.getUser().getCarNumber());
        builder.append("\n客户姓名:" + bean.getUser().getNickname());
        builder.append("\n\n员工编号:" + bean.getStaff().getNumber());
        builder.append("\n员工姓名:" + bean.getStaff().getName());
        builder.append("\n账号:" + bean.getStaff().getAccount());
        builder.append("\n优惠券名称" + "  数量" + " 单价" + " 金额\n");
        List<JYouYhqResponse.OrderBean> order = bean.getOrder();
        for (JYouYhqResponse.OrderBean orderBean : order) {
            String couponName = orderBean.getName();
            builder.append(couponName + "\n");
            builder.append("             " + orderBean.getNum() + "  " + orderBean.getMoney() + "  " + orderBean.getAllmoney() + "\n");
        }
//        builder.append("\n\n\n\n兑换商品使用现金:" + "200.00  " + "使用积分:" + "200");
        builder.append("\n客户签名:\n\n\n\n\n\n\n");
        return builder.toString();
    }

    public void flush() {
        init();
    }

    public class CustomerData extends BaseObservable {
        public String userName;
        public String userLll;
        private String payNum = "";
        public String userGetLll = "0";

        public String getPayNum() {
            return payNum;
        }

        public void setPayNum(String payNum) {
            if (payNum != null && !payNum.equals("")) {
                if (type == 1) {
                    if (parseInt(payNum) >= money) {
                        userGetLll = lll + "";
                    } else {
                        userGetLll = "0";
                    }
                } else {
                    if (factor != 0) {
                        userGetLll = (parseInt(payNum) / factor + "");
                    }
                }
            } else {
                userGetLll = "0";
            }
            this.payNum = payNum;
            this.notifyChange();
        }

    }


    public class CustomerItem {
        public CustomerItem(String userName, String userTime, String userPay, String id, String oil_type) {
            this.userName = userName;
            this.userTime = userTime;
            this.userPay = userPay;
            this.id = id;
            this.oil_type = oil_type;
        }

        public String userName;
        public String userTime;
        public String userPay;
        public String id;
        public String oil_type;
    }

    public class JifenResponse extends HttpReponse {
        public String jifen;

        public JifenResponse(String jifen) {
            this.jifen = jifen;
        }
    }


    public class CardItem {
        public CardItem(String cardId, String cardName, String cardTime, String cardPay, String state, String couponId, String stateId, String exps) {
            this.cardId = cardId;
            this.cardName = cardName;
            this.cardTime = cardTime;
            this.cardPay = cardPay;
            this.state = state;
            this.couponId = couponId;
            this.stateId = stateId;
            this.exps = exps;
        }

        public String cardId;
        public String cardName;
        public String cardTime;
        public String cardPay;
        public String state;
        public String couponId;
        public String stateId;
        public String exps;
    }

    public class RanLiaoInfo {
        public String id;
        public String you_name;
        public String add_time;
        public String is_del;
        public boolean isC;

        public RanLiaoInfo(String id, String you_name, String add_time, String is_del, boolean isC) {
            this.id = id;
            this.you_name = you_name;
            this.add_time = add_time;
            this.is_del = is_del;
            this.isC = isC;
        }
    }

    public class Info {
        public String coupon_id;
        public String copnum;
        public String name;
        public String xuman_price;
        public String money;
        public int use_num;
        public boolean can_use;

        public Info(String coupon_id, String copnum, String name, String xuman_price, String money, int use_num) {
            this.coupon_id = coupon_id;
            this.copnum = copnum;
            this.name = name;
            this.xuman_price = xuman_price;
            this.money = money;
            this.use_num = use_num;
        }

        public boolean isCan_use() {
            return can_use;
        }

        public void setCan_use(boolean can_use) {
            this.can_use = can_use;
        }

        public int getUse_num() {
            return use_num;
        }

        public void setUse_num(int use_num) {
            this.use_num = use_num;
        }
    }

}
