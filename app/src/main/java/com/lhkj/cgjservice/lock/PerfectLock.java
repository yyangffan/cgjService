package com.lhkj.cgjservice.lock;

import android.content.Context;
import android.view.View;

import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.databinding.ActivityPerfectBinding;
import com.lhkj.cgjservice.entity.User;


/**
 * Created by 浩琦 on 2017/6/21.
 */

public class PerfectLock {
    public PerfectData perfectData;
    private ActivityPerfectBinding perfectBinding;
    private Context context;
    public PerfectLock(Context context,ActivityPerfectBinding perfectBinding){
        this.context=context;
        this.perfectBinding=perfectBinding;
        perfectData=new PerfectData();
    }
    public void userInfo(){
        perfectBinding.perSelect.setVisibility(View.GONE);
        perfectBinding.perUser.setVisibility(View.VISIBLE);
        perfectBinding.include.setAppBarLock(new AppBarLock(context, R.string.userinfo,R.mipmap.icon_back,R.string.modify).setLeft(-1));
        perfectBinding.include.imsLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perfectBinding.perSelect.setVisibility(View.VISIBLE);
                perfectBinding.perUser.setVisibility(View.GONE);
                perfectBinding.include.setAppBarLock(new AppBarLock(context,R.string.perfect,R.mipmap.icon_back,0,true,false));
            }
        });
    }
    public void carInfo(){
        perfectBinding.perSelect.setVisibility(View.GONE);
        perfectBinding.perCar.setVisibility(View.VISIBLE);
        perfectBinding.include.setAppBarLock(new AppBarLock(context, R.string.carinfo,R.mipmap.icon_back,0,true,false).setLeft(-1));
        perfectBinding.include.imsLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                perfectBinding.perSelect.setVisibility(View.VISIBLE);
                perfectBinding.perCar.setVisibility(View.GONE);
                perfectBinding.include.setAppBarLock(new AppBarLock(context,R.string.perfect,R.mipmap.icon_back,0,true,false));
            }
        });
    }
    public void changeUser(){}
    public void changeCar(){}
    public class PerfectData{
        PerfectData(){
            username= User.getUser().userName;
//            usernike= User.getUser().usernike;
//            usersex= User.getUser().usersex;
        }
        public String username;
        public String usernike;
        public String usersex;

        public String brand;
        public String plateNumber;
        public String aftPlateNumber;
        public String plateAge;
        public String plateMin;
    }
}
