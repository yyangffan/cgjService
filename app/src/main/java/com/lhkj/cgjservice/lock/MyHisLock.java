package com.lhkj.cgjservice.lock;

import android.content.Context;
import android.support.annotation.NonNull;

import com.lhkj.cgjservice.BR;
import com.lhkj.cgjservice.HttpRequset.HttpCallListener;
import com.lhkj.cgjservice.HttpRequset.HttpUtil;
import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.base.ui.adapter.BaseTopAdapter;
import com.lhkj.cgjservice.entity.Operation;
import com.lhkj.cgjservice.entity.User;
import com.lhkj.cgjservice.reponse.OilHisResponse;
import com.lhkj.cgjservice.ui.mine.MyHisActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by 浩琦 on 2017/6/22.
 */

public class MyHisLock {
    public BaseTopAdapter myHisAdapter;
    private ArrayList<MyHisItem> myHisData;
    private Context context;

    public MyHisLock(Context context) {
        this.context = context;
        myHisData = new ArrayList<>();
        myHisAdapter = new BaseTopAdapter(context, myHisData, R.layout.myhis_item, BR.myHisItem);
        mainData();
    }

    private void mainData() {
        myHisData.clear();
        HashMap hashMap = new HashMap();
        hashMap.put("admin_id", User.getUser().adminId);
        hashMap.put("s_id", User.getUser().sId);
        HttpUtil.getInstance().postRequest(Operation.OIL_HIS, hashMap, OilHisResponse.class, new HttpCallListener<OilHisResponse>() {
            @Override
            public void Start(String URL) {

            }

            @Override
            public void Success(String URL, @NonNull OilHisResponse bean) {
                for (OilHisResponse.Info info : bean.info) {
                    Date date = new Date();
                    date.setTime(Long.parseLong(info.add_time + "000"));
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    info.add_time = formatter.format(date);
                    myHisData.add(new MyHisItem(info.nickname, info.jifen, "￥" + info.money, info.add_time));
                }
                ((MyHisActivity) context).runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        myHisAdapter.notifyDataSetChanged();
                    }
                });
            }

            @Override
            public void Error(String URL) {

            }
        });
    }

    public class MyHisItem {
        public MyHisItem(String userName, String getlll, String payNum, String payTime) {
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
}
