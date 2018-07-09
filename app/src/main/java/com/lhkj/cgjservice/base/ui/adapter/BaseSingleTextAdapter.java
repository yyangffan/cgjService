package   com.lhkj.cgjservice.base.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by lizc on 2017/4/20.
 * 类描述：
 * 创建人：lizc
 * 创建时间：2017/4/20 17:08
 * 修改人：lizc
 * 修改时间：2017/4/20 17:08
 * 修改备注：
 */

public class BaseSingleTextAdapter extends BaseAdapter {
    private Context context;
    private ArrayList textList;
    public BaseSingleTextAdapter(Context context,ArrayList textList){
        this.context=context;
        this.textList=textList;
    }
    @Override
    public int getCount() {
        if (textList==null){
            return 0;
        }
        return textList.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView tv;
        if (convertView==null){
            tv=new TextView(context);
        }else {
            tv=(TextView)convertView;
        }
        tv.setText((String)textList.get(position));
        subTextView(tv,position);
        return tv;
    }

    protected void subTextView(TextView tv, int position) {
    }

}
