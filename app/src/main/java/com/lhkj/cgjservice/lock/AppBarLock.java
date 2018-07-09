package com.lhkj.cgjservice.lock;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.graphics.drawable.Drawable;

import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.ui.mine.MineActivity;


/**
 * Created by 浩琦 on 2017/6/16.
 */

public class AppBarLock {
    private Context context;
    private int leftType;
    private int rightType;
    public AppBarData barData;
    public static final int BACK = 1000;
    public static final int BIND = 1001;
    public static final int SHARE = 2000;
    public static final int LOGIN = 2001;

    public AppBarLock(Context context, int title){
        this.context=context;
        barData = new AppBarData(context.getResources().getString(title), context.getResources().getDrawable(R.mipmap.icon_back),null , null, null, true, false);
        leftType = BACK;
    }

    public AppBarLock(Context context, int title, int imsLeft, int imsRight, boolean isLeft, boolean isRight) {
        this.context = context;
        if (imsLeft == 0) {
            barData = new AppBarData(context.getResources().getString(title), null, context.getResources().getDrawable(imsRight), null, null, isLeft, isRight);
        } else if (imsRight == 0) {
            barData = new AppBarData(context.getResources().getString(title), context.getResources().getDrawable(imsLeft), null, null, null, isLeft, isRight);

        } else {
            barData = new AppBarData(context.getResources().getString(title), context.getResources().getDrawable(imsLeft), context.getResources().getDrawable(imsRight), null, null, isLeft, isRight);
        }
        leftType = BACK;
    }

    public AppBarLock(Context context, int title, int titleLeft, int titleRight, boolean isLeft, boolean isRight, boolean over) {
        this.context = context;
        if (titleLeft == 0) {
            barData = new AppBarData(context.getResources().getString(title), null, null, null, context.getResources().getString(titleRight), isLeft, isRight);

        } else if (titleRight == 0) {
            barData = new AppBarData(context.getResources().getString(title), null, null, context.getResources().getString(titleLeft), null, isLeft, isRight);

        } else {
            barData = new AppBarData(context.getResources().getString(title), null, null, context.getResources().getString(titleLeft), context.getResources().getString(titleRight), isLeft, isRight);
        }
    }

    public AppBarLock(Context context, int title, int imsLeft, int titleRight) {
        this.context = context;
        barData = new AppBarData(context.getResources().getString(title), context.getResources().getDrawable(imsLeft), null , null, context.getResources().getString(titleRight), true, true);
    }
    public AppBarLock(Context context, String title, int imsLeft, int titleRight){
        this.context=context;
        barData = new AppBarData(title, context.getResources().getDrawable(imsLeft),context.getResources().getDrawable(titleRight) , null, null, true, true);
    }

    public AppBarLock setRight(int type) {
        this.rightType = type;
        return this;
    }

    public AppBarLock setLeft(int type) {
        this.leftType = type;
        return this;
    }

    public void leftClick() {
        switch (leftType) {
            case BACK:
                back();
                break;
            case BIND:
                bind();
                break;
        }
    }


    public void rightClick() {
        switch (rightType) {
            case SHARE:
                share();
                break;
            case LOGIN:
                login();
                break;
        }
    }

    private void back() {
        ((Activity)context).finish();
    }

    private void share() {

    }

    private void bind() {

    }

    private void login() {
        ((Activity)context).startActivity(new Intent(context,MineActivity.class));
    }

    public class AppBarData extends BaseObservable {
        public AppBarData(String title, Drawable imsLeft, Drawable imsRight, String titleLeft, String titleRight, boolean isLeft, boolean isRight) {
            this.title = title;
            this.imsLeft = imsLeft;
            this.imsRight = imsRight;
            this.isLeft = isLeft;
            this.isRight = isRight;
            this.titleLeft = titleLeft;
            this.titleRight = titleRight;
        }

        public String title;
        public String titleLeft;
        public String titleRight;
        public Drawable imsLeft;
        public Drawable imsRight;
        public boolean isLeft;
        public boolean isRight;
    }
}
