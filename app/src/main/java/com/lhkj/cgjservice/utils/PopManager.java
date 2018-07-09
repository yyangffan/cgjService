package com.lhkj.cgjservice.utils;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.PopupWindow;

import com.lhkj.cgjservice.R;


/**
 * Created by 浩琦 on 2017/6/20.
 */

public class PopManager {
    private PopupWindow window;
    private int wei;
    private ViewDataBinding dataBinding;
    private Context context;
    private boolean isOver;
    private MissLisenter lisenter;

    public PopManager(Context context) {
        this.context = context;
    }

    public ViewDataBinding showTransparentPop(View v,int resid) {
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), resid, null, false);
        windowAttribute(dataBinding.getRoot(),true);
        window.showAtLocation(v, Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
        setAhpla();
        return dataBinding;
    }
    public ViewDataBinding showPop(View v,int resid) {
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), resid, null, false);
        windowAttribute(dataBinding.getRoot(),false);
        window.showAtLocation(v, Gravity.CENTER_HORIZONTAL|Gravity.CENTER_VERTICAL, 0, 0);
        setAhpla();
        return dataBinding;
    }
    public void disPop(View v, final boolean isover) {
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (window != null) {
                    window.dismiss();
                    window = null;
                }
                isOver=isover;
                if (isover){
                    ((Activity) context).finish();
                    isOver=false;
                }
            }
        });
    }

    public void stop(){
        if (window != null) {
            window.dismiss();
            window = null;
        }
    }
    public void setMissLisenter(MissLisenter lisenter){
        this.lisenter=lisenter;
    }

    private void windowAttribute(View popupView,boolean isT) {
        window = new PopupWindow(popupView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        window.setAnimationStyle(R.style.popup_window_anim);
        if (isT){
            window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.apl)));
        }else {
            window.setBackgroundDrawable(new ColorDrawable(context.getResources().getColor(R.color.white)));
        }
//        window.setFocusable(true);
        window.setOutsideTouchable(true);
        window.update();
    }

    private void setAhpla() {
        WindowManager.LayoutParams lp = ((Activity) context).getWindow().getAttributes();
        lp.alpha = 0.6f; // 0.0-1.0
        ((Activity) context).getWindow().setAttributes(lp);
        ((Activity) context).getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                WindowManager.LayoutParams lp1 = ((Activity) context).getWindow().getAttributes();
                lp1.alpha = 1.0f; // 0.0-1.0
                ((Activity) context).getWindow().setAttributes(lp1);
                if (isOver){
                    ((Activity) context).finish();
                }
                if (lisenter!=null){
                    lisenter.miss();
                }
            }
        });
    }

    public interface MissLisenter{
        void miss();
    }
}
