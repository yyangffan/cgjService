package com.lhkj.cgjservice.ui;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.lhkj.cgjservice.HttpRequset.HttpCallListener;
import com.lhkj.cgjservice.HttpRequset.HttpUtil;
import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.base.ui.BaseActivity;
import com.lhkj.cgjservice.databinding.ActivityMainBinding;
import com.lhkj.cgjservice.databinding.SeePriceBinding;
import com.lhkj.cgjservice.entity.Operation;
import com.lhkj.cgjservice.entity.RunTime;
import com.lhkj.cgjservice.entity.User;
import com.lhkj.cgjservice.lock.AppBarLock;
import com.lhkj.cgjservice.lock.MainLock;
import com.lhkj.cgjservice.reponse.HttpReponse;
import com.lhkj.cgjservice.reponse.UserOilHisReponse;
import com.lhkj.cgjservice.ui.work.CouponActivity;
import com.lhkj.cgjservice.ui.work.CustomerActivity;
import com.lhkj.cgjservice.ui.work.SignPriceActivity;
import com.lhkj.cgjservice.utils.PopManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class MainActivity extends BaseActivity {
    private ActivityMainBinding mainBinding;
    private Gson gson;
    private QrCodeJson qrCodeJson;
    private MainLock mainLock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mainLock = new MainLock(this, mainBinding);
        mainBinding.setMainLock(mainLock);
        mainBinding.include.setAppBarLock(new AppBarLock(this, R.string.main, 0, R.mipmap.icon_mine, false, true).setRight(AppBarLock.LOGIN));
        gson = new Gson();
        init();
    }

    private void init() {

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        if (User.getUser().isLogin) {
            mainLock.flush();
        } else {
            finish();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
                Bundle bundle = data.getExtras();
                String codeText = bundle.getString("result");
                if (isJson(codeText)) {
                    qrCodeJson = gson.fromJson(codeText, QrCodeJson.class);

                // 现在先用这个接口，看是不是本站的，但是非常费流量
                HashMap hashMap = new HashMap();
                hashMap.put("user_id", qrCodeJson.userId);
                hashMap.put("s_id", User.getUser().sId);
                HttpUtil.getInstance().postRequest(Operation.USER_OIL_HIS, hashMap, UserOilHisReponse.class, new HttpCallListener<UserOilHisReponse>() {
                    @Override
                    public void Start(String URL) {

                    }

                    @Override
                    public void Success(String URL, @NonNull final UserOilHisReponse bean) {
                        if (bean.code.equals("200")) {

                            // 是本站的这样处理
                            RunTime.setData(RunTime.CODE_TEXT, qrCodeJson);
                            switch (qrCodeJson.type) {
                                case 0:
                                    // 加油二维码
                                    startActivity(CustomerActivity.class);
                                    break;
                                case 1:
                                    // 商品二维码
                                    startActivity(SignPriceActivity.class);
                                    break;
                                case 2:
                                    // 优惠券二维码
                                    startActivity(CouponActivity.class);
                                    break;
                                case 3:
                                    // 签到二维码
                                    seePrice();
                                    break;
                                default:
                                    Toast.makeText(MainActivity.this, "不是指定的二维码", Toast.LENGTH_SHORT).show();
                                    break;
                            }

                        } else if (bean.code.equals("201")) {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "用户绑定的不是该加油站", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else if (bean.code.equals("202")) {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "用户未绑定加油站", Toast.LENGTH_SHORT).show();
                                }
                            });
                        } else {
                            MainActivity.this.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(MainActivity.this, "未知问题，请联系后台", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }

                    }

                    @Override
                    public void Error(String URL) {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(MainActivity.this, "用户未绑定加油站或绑定的不是该加油站", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }

        } else {

        }

    }


    private void seePrice() {
        MainActivity.this.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                final PopManager popManager;
                SeePriceBinding seePriceBinding;
                popManager = new PopManager(MainActivity.this);
                seePriceBinding = (SeePriceBinding) popManager.showTransparentPop(mainBinding.convenientBanner, R.layout.see_price);
                Glide.with(MainActivity.this).load(qrCodeJson.imgUrl).into(seePriceBinding.priceImg);
                seePriceBinding.setIsPrice("同意兑换");
                seePriceBinding.priceEnter.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        signPush(popManager);
                    }
                });
            }
        });

    }

    private void signPush(final PopManager popManager) {
        HashMap hashMap = new HashMap();
        hashMap.put("user_id", qrCodeJson.userId);
        hashMap.put("id", qrCodeJson.signId);
        hashMap.put("s_id", User.getUser().sId);
        HttpUtil.getInstance().postRequest(Operation.SIGN_PUSH, hashMap, HttpReponse.class, new HttpCallListener<HttpReponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull final HttpReponse bean) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (bean.code.equals("200")) {
                            Toast.makeText(MainActivity.this, "商品兑换成功，请给予对应商品", Toast.LENGTH_SHORT).show();
                            popManager.stop();
                        } else if (bean.code.equals("201")) {
                            Toast.makeText(MainActivity.this, "商品已被兑换", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "商品兑换失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }

            @Override
            public void Error(String URL) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this, "网络不畅请重试", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    private boolean isJson(String jsonString) {
        try {
            JSONObject jsonObject = new JSONObject(jsonString);
            jsonObject = null;
            return true;
        } catch (JSONException e) {
            try {
                JSONArray jsonArray = new JSONArray(jsonString);
                jsonArray = null;
                return true;
            } catch (JSONException e1) {
                return false;
            }
        }
    }

    public static class QrCodeJson {
        public String userId;
        public String orderSn;
        public String couponId;
        public String signId;
        public String imgUrl;
        public String stateId;
        public int type;


    }
}
