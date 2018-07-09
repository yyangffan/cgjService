package com.lhkj.cgjservice.lock;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.IdRes;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.databinding.library.baseAdapters.BR;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.lhkj.cgjservice.HttpRequset.HttpCallListener;
import com.lhkj.cgjservice.HttpRequset.HttpUtil;
import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.base.ui.adapter.BaseTopAdapter;
import com.lhkj.cgjservice.databinding.ActivityMainBinding;
import com.lhkj.cgjservice.entity.Operation;
import com.lhkj.cgjservice.entity.RunTime;
import com.lhkj.cgjservice.entity.User;
import com.lhkj.cgjservice.printutiles.PrintUtil;
import com.lhkj.cgjservice.reponse.BannerResponse;
import com.lhkj.cgjservice.reponse.GoodsJlResponse;
import com.lhkj.cgjservice.reponse.HttpReponse;
import com.lhkj.cgjservice.reponse.OilHisResponse;
import com.lhkj.cgjservice.reponse.SumPrintResponse;
import com.lhkj.cgjservice.reponse.UserInfoReponse;
import com.lhkj.cgjservice.reponse.YhqAginReponse;
import com.lhkj.cgjservice.reponse.YouHuiQuanResponse;
import com.lhkj.cgjservice.ui.Login.LoginActivity;
import com.lhkj.cgjservice.ui.MainActivity;
import com.lhkj.cgjservice.ui.views.CustomDatePicker;
import com.lhkj.cgjservice.ui.work.CustomerActivity;
import com.lhkj.cgjservice.utils.DateUtil;
import com.lhkj.cgjservice.utils.NetworkImageHolderView;
import com.lhkj.cgjservice.utils.SharedPreferencesUtil;
import com.xys.libzxing.zxing.activity.CaptureActivity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * Created by 浩琦 on 2017/6/24.
 */

public class MainLock {
    public BaseTopAdapter mainAdapter;
    private ArrayList<HisItem> mainList;
    private ArrayList<YHQItem> yhqList;
    private ArrayList<GoodsItem> goodsList;
    private Context context;
    private ActivityMainBinding mainBinding;
    private String name, pwd;
    private ArrayList networkImages;
    private CustomDatePicker customDatePicker;
    private String staTime = "";
    private String endTime = "";
    private boolean isStart = true;
    private boolean isFirst = true;
    private List<RadioButton> mRadioButtons;
    private int whatIsIt = 0;
    private String mEdtPass = "";


    public MainLock(Context context, ActivityMainBinding mainBinding) {
        this.mainBinding = mainBinding;
        this.context = context;
        mainList = new ArrayList<>();
        yhqList = new ArrayList<>();
        goodsList = new ArrayList<>();
        mRadioButtons = new ArrayList<>();
        mainAdapter = new BaseTopAdapter(context, mainList, R.layout.his_item, BR.hisItem);
        mainBinding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        initDatePicker();
        init();
        mRadioButtons.add(mainBinding.mainRbOne);
        mRadioButtons.add(mainBinding.mainRbTwo);
        mRadioButtons.add(mainBinding.mainRbThree);
    }

    private void initDatePicker() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String end_now = sdf.format(new Date());
        String start_now= DateUtil.getOldDate(-1);
        staTime=start_now;
        endTime=end_now;
        /*初始赋值*/
//        if (isFirst) {
//            staTime = endTime = getTime(now);
//        }
        mainBinding.textStartTime.setText(start_now.split(" ")[0]);
        mainBinding.textStartTime.setText(start_now);

        mainBinding.textEndTime.setText(end_now.split(" ")[0]);
        mainBinding.textEndTime.setText(end_now);

        customDatePicker = new CustomDatePicker(context, new CustomDatePicker.ResultHandler() {
            @Override
            public void handle(String time) { // 回调接口，获得选中的时间

                if (isStart) {
                    mainBinding.textStartTime.setText(time);
                    staTime = getTime(time);
                } else {
                    mainBinding.textEndTime.setText(time);
                    endTime = getTime(time);
                }
                if (mRadioButtons.get(0).isChecked()) {
                    mainData();
                } else if (mRadioButtons.get(1).isChecked()) {
                    youhuiquanData();
                } else if (mRadioButtons.get(2).isChecked()) {
                    shangpinData();
                }
                Log.e("开始Or结束", "时间戳:" + getTime(time));
            }
        }, "2010-01-01 00:00", end_now); // 初始化日期格式请用：yyyy-MM-dd HH:mm，否则不能正常运行
        customDatePicker.showSpecificTime(true); // 显示时和分
        customDatePicker.setIsLoop(true); // 允许循环滚动
    }

    public void timeGetStart() {
        isFirst = false;
        isStart = true;
        customDatePicker.show(mainBinding.textStartTime.getText().toString());
    }

    public void timeGetEnd() {
        isFirst = false;
        isStart = false;
        customDatePicker.show(mainBinding.textEndTime.getText().toString());
    }

    // 将字符串转为时间戳
    public static String getTime(String user_time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d;
        try {
            d = sdf.parse(user_time);
            long l = d.getTime();
            String str = String.valueOf(l);
            re_time = str.substring(0, 10);
        } catch (ParseException e) {
            // TODO Auto-generated catch block e.printStackTrace();
        }
        return re_time;
    }

    private void init() {
        name = SharedPreferencesUtil.getSharePreString(context, User.getUser().SAVE_NAME, "");
        pwd = SharedPreferencesUtil.getSharePreString(context, User.getUser().SAVE_PWD, "");
        if (!name.equals("")) {
            User.getUser().tryLoginTwo((MainActivity) context, name, pwd, new User.AuthorizationListener() {
                @Override
                public void authorization(boolean isOk) {
                    if (isOk) {
                        mainData();
                    } else {
                        ((MainActivity) context).startActivity(LoginActivity.class);
                        ((MainActivity) context).finish();
                    }
                }
            });
        } else {
            ((MainActivity) context).startActivity(LoginActivity.class);
            ((MainActivity) context).finish();
        }

        mainBinding.mainRg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                switch (checkedId) {
                    case R.id.main_rbOne:
                        mainData();
                        mainBinding.mainLlTime.setVisibility(View.GONE);
                        mainBinding.mainRbOne.setChecked(true);
                        mainBinding.mainRbTwo.setChecked(false);
                        mainBinding.mainRbThree.setChecked(false);
                        break;
                    case R.id.main_rbTwo:
                        youhuiquanData();
                        mainBinding.mainLlTime.setVisibility(View.VISIBLE);
                        mainBinding.mainRbOne.setChecked(false);
                        mainBinding.mainRbTwo.setChecked(true);
                        mainBinding.mainRbThree.setChecked(false);
                        break;
                    case R.id.main_rbThree:
                        shangpinData();
                        mainBinding.mainLlTime.setVisibility(View.VISIBLE);
                        mainBinding.mainRbOne.setChecked(false);
                        mainBinding.mainRbTwo.setChecked(false);
                        mainBinding.mainRbThree.setChecked(true);
                        break;
                }
            }
        });
        mainBinding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (whatIsIt) {
                    case 0:/*加油记录不进行打印*/
                        break;
                    case 1:
                        showRemindDialog(yhqList.get(position).id);//传入每一条的id
                        break;
                    case 2:
                        showRemindDialog(goodsList.get(position).id);//传入每一条的id
                        break;
                }
            }
        });

    }

    /**
     * @param id 条目的id
     */
    public void showRemindDialog(final String id) {
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
                isYesPwd(mEdtPass, id);

            }
        });
        tv_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    public void isYesPwd(String pwd, final String id) {
        HashMap hashMap = new HashMap();
        hashMap.put("admin_id", User.getUser().adminId);
        hashMap.put("er_pwd", pwd);
        Log.e("密码验证数据:", "?admin_id&" + User.getUser().adminId + "&er_pwd=" + pwd);
        HttpUtil.getInstance().postRequest(Operation.ERJIMIMA, hashMap, HttpReponse.class, new HttpCallListener<HttpReponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final HttpReponse bean) {
                ((MainActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean.code.equals("200")) {
                            if (whatIsIt == 1) {
                                getYhqPrintAgin(id);
                            } else if (whatIsIt == 2) {
                                getGoodsPrintAgin(id);
                            }
                        } else if (bean.code.equals("100")) {
                            Toast.makeText(context, "密码错误", Toast.LENGTH_SHORT).show();

                        }
                    }
                });

            }

            @Override
            public void Error(String URL) {
                ((MainActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "网络不畅请重试", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    /*优惠券再次打印*/
    public void getYhqPrintAgin(String id) {

        HashMap hashMap = new HashMap();
        hashMap.put("id", id);
        HttpUtil.getInstance().postRequest(Operation.YHQPRINTAGIN, hashMap, YhqAginReponse.class, new HttpCallListener<YhqAginReponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final YhqAginReponse bean) {
                ((MainActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean.code.equals("200")) {
                            PrintUtil.getInstance().PrintYhq(bean);
                        } else {

                        }
                    }
                });

            }

            @Override
            public void Error(String URL) {
                ((MainActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "网络不畅请重试", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }

    /*商品再次打印*/
    public void getGoodsPrintAgin(String id) {
        HashMap hashMap = new HashMap();
        hashMap.put("id", id);
        HttpUtil.getInstance().postRequest(Operation.GOODSPRINTAGIN, hashMap, SumPrintResponse.class, new HttpCallListener<SumPrintResponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final SumPrintResponse bean) {
                ((MainActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean.code.equals("200")) {
                            PrintUtil.getInstance().PrintGoods(bean);
                        } else {

                        }
                    }
                });

            }

            @Override
            public void Error(String URL) {
                ((MainActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(context, "网络不畅请重试", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    public void startBt() {
        if (mRadioButtons.get(0).isChecked()) {
            mainData();
        } else if (mRadioButtons.get(1).isChecked()) {
            youhuiquanData();
        } else if (mRadioButtons.get(2).isChecked()) {
            shangpinData();
        }
    }

    /*优惠券兑换记录*/
    private void youhuiquanData() {
        whatIsIt = 1;
        yhqList.clear();
        mainAdapter = new BaseTopAdapter(context, yhqList, R.layout.his_item_youhuiquan, BR.yhqItem);
        mainBinding.listView.setAdapter(mainAdapter);
        HashMap hashMap = new HashMap();
        hashMap.put("s_id", User.getUser().sId);
        hashMap.put("s_time", getTime(mainBinding.textStartTime.getText().toString()));
        hashMap.put("o_time", getTime(mainBinding.textEndTime.getText().toString()));

        HttpUtil.getInstance().postRequest(Operation.YHQJL, hashMap, YouHuiQuanResponse.class, new HttpCallListener<YouHuiQuanResponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final YouHuiQuanResponse bean) {

                if (bean.info.size() == 0) {
                    ((MainActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mainBinding.mainTvRight.setVisibility(View.GONE);
                            mainBinding.mainTvLeft.setVisibility(bean.allfo == null ? View.GONE : View.VISIBLE);
                            mainBinding.mainTvLeft.setText(bean.allfo == null ? "" : "面额总值:" + bean.allfo);
                            mainBinding.listView.setVisibility(View.GONE);
                            mainBinding.textViewNoe.setVisibility(View.VISIBLE);
                        }
                    });
                } else {
                    ((MainActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mainBinding.mainTvRight.setVisibility(View.GONE);
                            mainBinding.mainTvLeft.setVisibility(bean.allfo == null ? View.GONE : View.VISIBLE);
                            mainBinding.mainTvLeft.setText(bean.allfo == null ? "" : "面额总值:" + bean.allfo);
                            mainBinding.listView.setVisibility(View.VISIBLE);
                            mainBinding.textViewNoe.setVisibility(View.GONE);
                        }
                    });
                }
                ((MainActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Date date = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        for (YouHuiQuanResponse.Info info : bean.info) {
                            date.setTime(Long.parseLong(info.add_time + "000"));
                            info.add_time = formatter.format(date);
                            if (info.nickname == null) {
                                info.nickname = info.mobile == null ? "" : info.mobile;
                            } else {
                                info.nickname = info.mobile == null ? "" : info.mobile;
                            }
                            yhqList.add(new YHQItem(info.id, info.nickname == "" ? "" : (info.nickname.length() > 12 ? info.nickname.substring(0, 12) + "..." : info.nickname), info.c_name, info.add_time, "￥" + info.money, info.cate_name));
                        }
                        mainAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void Error(String URL) {

            }
        });
    }

    /*商品兑换记录*/
    private void shangpinData() {
        whatIsIt = 2;
        goodsList.clear();
        mainAdapter = new BaseTopAdapter(context, goodsList, R.layout.his_item_shangpin, BR.item);
        mainBinding.listView.setAdapter(mainAdapter);
        HashMap hashMap = new HashMap();
        hashMap.put("s_id", User.getUser().sId);
        hashMap.put("s_time",getTime(mainBinding.textStartTime.getText().toString()));
        hashMap.put("o_time", getTime(mainBinding.textEndTime.getText().toString()));

        HttpUtil.getInstance().postRequest(Operation.GOODSJL, hashMap, GoodsJlResponse.class, new HttpCallListener<GoodsJlResponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final GoodsJlResponse bean) {

                if (bean.info.size() == 0) {
                    ((MainActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mainBinding.mainTvLeft.setVisibility(bean.all_shop == null ? View.GONE : View.VISIBLE);
                            mainBinding.mainTvRight.setVisibility(bean.all_excha == null ? View.GONE : View.VISIBLE);
                            mainBinding.mainTvLeft.setText(bean.all_shop == null ? "" : "现金总额:" + bean.all_shop);
                            mainBinding.mainTvRight.setText(bean.all_excha == null ? "" : "积分总额:" + bean.all_excha);
                            mainBinding.listView.setVisibility(View.GONE);
                            mainBinding.textViewNoe.setVisibility(View.VISIBLE);
                        }
                    });
                } else {
                    ((MainActivity) context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mainBinding.mainTvLeft.setVisibility(bean.all_shop == null ? View.GONE : View.VISIBLE);
                            mainBinding.mainTvRight.setVisibility(bean.all_excha == null ? View.GONE : View.VISIBLE);
                            mainBinding.mainTvLeft.setText(bean.all_shop == null ? "" : "现金总额:" + bean.all_shop);
                            mainBinding.mainTvRight.setText(bean.all_excha == null ? "" : "积分总额:" + bean.all_excha);
                            mainBinding.listView.setVisibility(View.VISIBLE);
                            mainBinding.textViewNoe.setVisibility(View.GONE);
                        }
                    });
                }
                ((MainActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Date date = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        for (GoodsJlResponse.Info info : bean.info) {
                            date.setTime(Long.parseLong(info.time + "000"));
                            info.time = formatter.format(date);
                            if (info.shop_price == null) {
                                info.shop_price = "";
                            }
                            if (info.exchange_integral == null) {
                                info.exchange_integral = "";
                            }
                            if (info.nickname == null) {
                                info.nickname = info.mobile == null ? "" : info.mobile;
                            } else {
                                info.nickname = info.mobile == null ? "" : info.mobile;
                            }
                            goodsList.add(new GoodsItem(info.id, info.nickname == "" ? "" : "客户姓名:" + (info.nickname.length() > 12 ? info.nickname.substring(0, 12) + "..." : info.nickname), info.goods_name, "￥" + info.shop_price, info.time, "积分:" + info.exchange_integral));
                        }
                        mainAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void Error(String URL) {

            }
        });
    }

    /*加油记录*/
    private void mainData() {
        whatIsIt = 0;
        mainList.clear();
        mainAdapter = new BaseTopAdapter(context, mainList, R.layout.his_item, BR.hisItem);
        mainBinding.listView.setAdapter(mainAdapter);
        mainBinding.textViewNoe.setVisibility(View.GONE);
        mainBinding.listView.setVisibility(View.VISIBLE);
        HashMap hashMap = new HashMap();
        hashMap.put("admin_id", User.getUser().adminId);
        hashMap.put("s_id", User.getUser().sId);
        HttpUtil.getInstance().postRequest(Operation.OIL_HIS, hashMap, OilHisResponse.class, new HttpCallListener<OilHisResponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final OilHisResponse bean) {
                ((MainActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Date date = new Date();
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                        for (OilHisResponse.Info info : bean.info) {
                            date.setTime(Long.parseLong(info.add_time + "000"));
                            info.add_time = formatter.format(date);
                            info.nickname = info.mobile == null ? "" : info.mobile;
                            mainList.add(new HisItem(info.nickname.length() > 11 ? info.nickname.substring(0, 11) : info.nickname, info.jifen, "￥" + info.money, info.add_time));
                        }
                        mainAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void Error(String URL) {

            }
        });
        networkImages = new ArrayList();
        HashMap hashMap1 = new HashMap();
        hashMap1.put("admin_id", User.getUser().adminId);
        //boo
        HttpUtil.getInstance().postRequest(Operation.MAIN_BANNER, hashMap1, BannerResponse.class, new HttpCallListener<BannerResponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final BannerResponse bean) {
                ((MainActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean.info != null) {
                            for (BannerResponse.Info info : bean.info) {
                                networkImages.add(info.img_url);
                            }
                        }
                        mainBinding.convenientBanner.startTurning(2500);
                        mainBinding.convenientBanner.setPages(new CBViewHolderCreator<NetworkImageHolderView>() {
                            @Override
                            public NetworkImageHolderView createHolder() {
                                return new NetworkImageHolderView();
                            }
                        }, networkImages);
//                .setPageIndicator(new int[]{com.bigkoo.convenientbanner.R.drawable.ic_page_indicator, R.drawable.ic_page_indicator_focused});
                    }
                });
            }

            @Override
            public void Error(String URL) {

            }
        });
    }

    public void flush() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.CHINA);
        String end_now = sdf.format(new Date());
        endTime=end_now;
        mainBinding.textEndTime.setText(end_now.split(" ")[0]);
        mainBinding.textEndTime.setText(end_now);
        if(whatIsIt==0){
            mainData();
        }else if(whatIsIt==1){
            youhuiquanData();
        }else if(whatIsIt==2){
            shangpinData();
        }


    }

    public void toZxing() {
        ((MainActivity) context).startActivityForResult(new Intent(context, CaptureActivity.class), 200);
    }

    public void infoPhone() {
        showDialog();
    }

    private void showDialog() {

        final EditText inputPhone = new EditText(context);
        inputPhone.setInputType(InputType.TYPE_CLASS_NUMBER);
        inputPhone.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});
        inputPhone.setFocusable(true);

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(context.getString(R.string.please_output_your_cell_phone_number))
//                .setIcon(R.drawable.dialog_logo)
                .setView(inputPhone)
                .setNegativeButton(context.getString(R.string.canl), null);
        builder.setPositiveButton(context.getString(R.string.enter),
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        String phoneNumber = inputPhone.getText().toString();
                        getUserInfo(phoneNumber);
                        dialog.dismiss();
                    }
                });
        builder.show();
    }

    private void getUserInfo(String phoneNumber) {
        HashMap hashMap = new HashMap();
        hashMap.put("mobile", phoneNumber);
        hashMap.put("s_id", User.getUser().sId);
        HttpUtil.getInstance().postRequest(Operation.USER_INFO, hashMap, UserInfoReponse.class, new HttpCallListener<UserInfoReponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final UserInfoReponse bean) {

                ((MainActivity) context).runOnUiThread(new Runnable() {
                    public void run() {

                        if (bean.code.equals("200")) {
                            MainActivity.QrCodeJson qrCodeJson = new MainActivity.QrCodeJson();
                            qrCodeJson.userId = bean.getInfo().getUser_id();
                            RunTime.setData(RunTime.CODE_TEXT, qrCodeJson);
                            ((MainActivity) context).startActivity(new Intent(context, CustomerActivity.class));
                        } else if (bean.code.equals("201")) {
//                    code 201 绑定的不是该加油站
//                    code 202 绑定的不是该加油站
//                    code 203 客户不存在
                            Toast.makeText(context, "用户绑定的不是该加油站", Toast.LENGTH_SHORT).show();
                        } else if (bean.code.equals("203")) {
                            Toast.makeText(context, "用户不存在", Toast.LENGTH_SHORT).show();
                        } else if (bean.code.equals("202")) {
                            Toast.makeText(context, "用户未绑定加油站", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(context, "输入的手机号不正确", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }

            @Override
            public void Error(String URL) {

            }
        });
    }

    public class HisItem {
        public HisItem(String userName, String getlll, String payNum, String payTime) {
            this.userName = userName;
            this.getlll = getlll;
            this.payNum = payNum;
            this.payTime = payTime;
        }

        public String userName;
        public String getlll;
        public String payNum;
        public String payTime;
    }

    public static class YHQItem {
        public YHQItem(String id, String sname, String cname, String addtime, String money, String catename) {
            this.id = id;
            this.sname = sname;
            this.cname = cname;
            this.addtime = addtime;
            this.money = money;
            this.catename = catename;
        }

        public String id;
        public String sname;
        public String cname;
        public String addtime;
        public String money;
        public String catename;
    }

    public static class GoodsItem {
        public GoodsItem(String id, String sname, String goodsname, String shopprice, String time, String exchangeintegral) {
            this.id = id;
            this.sname = sname;
            this.goodsname = goodsname;
            this.shopprice = shopprice;
            this.time = time;
            this.exchangeintegral = exchangeintegral;
        }

        public String id;
        public String sname;
        public String goodsname;
        public String shopprice;
        public String time;
        public String exchangeintegral;
        public String allfo;

        public String getAllfo() {
            return allfo;
        }

        public void setAllfo(String allfo) {
            this.allfo = allfo;
        }
    }

}
