package com.lhkj.cgjservice.lock;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.lhkj.cgjservice.BR;
import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.base.ui.adapter.BaseTopAdapter;
import com.lhkj.cgjservice.databinding.ActivityMycardBinding;

import java.util.ArrayList;

/**
 * Created by 浩琦 on 2017/6/22.
 */

public class MyCardLock {
    private TextView selectView;
    private ArrayList cardListData;
    private Context context;
    public BaseTopAdapter cardAdapter;
    public MyCardLock(Context context,  ActivityMycardBinding mycardBinding){
        this.context=context;
        cardListData=new ArrayList();
        cardListData.add(new CardItem("jyj","2017-100-11","50"));
        cardAdapter=new BaseTopAdapter(context,cardListData, R.layout.card_item, BR.cardItem);
        selectView=mycardBinding.list0;
    }
    public void oilCard(View view){
        selectView((TextView) view);
    }
    public void shopCard(View view){
        selectView((TextView) view);
    }
    public void payCard(View view){
        selectView((TextView) view);
    }
    public void carCard(View view){
        selectView((TextView) view);
    }

    private void selectView(TextView textView) {
        if (selectView != textView) {
            selectView.setTextColor(context.getResources().getColor(R.color.gray));
            selectView.setBackgroundColor(context.getResources().getColor(R.color.white));
            textView.setTextColor(context.getResources().getColor(R.color.main_color));
            textView.setBackground(context.getResources().getDrawable(R.drawable.select_red));
            selectView = textView;
        }
    }

    private void refresh() {
        cardListData.clear();
        cardAdapter.notifyDataSetChanged();
    }
    public class CardItem{
        public CardItem(String cardName,String cardTime,String cardPay){
            this.cardName=cardName;
            this.cardTime=cardTime;
            this.cardPay=cardPay;
        }
       public String cardName;
        public String cardTime;
        public String cardPay;
    }
}
