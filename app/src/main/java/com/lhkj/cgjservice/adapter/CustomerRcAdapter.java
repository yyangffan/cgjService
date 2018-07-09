package com.lhkj.cgjservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.lock.CustomerLock;

import java.util.List;

/**
 * Created by user on 2018/2/7.
 */

public class CustomerRcAdapter extends RecyclerView.Adapter<CustomerRcAdapter.ViewHolder>{
    private Context mContext;
    private List<CustomerLock.RanLiaoInfo> mInfos;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;

    public CustomerRcAdapter(Context context, List<CustomerLock.RanLiaoInfo> infos) {
        mContext = context;
        mInfos = infos;
        mInflater=LayoutInflater.from(mContext);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=mInflater.inflate(R.layout.item_lv,parent,false);
        ViewHolder vh=new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        CustomerLock.RanLiaoInfo ranLiaoInfo = mInfos.get(position);
        holder.mtv_title.setText(ranLiaoInfo.you_name);
        boolean isC = ranLiaoInfo.isC;
        if(isC){
            holder.mtv_title.setBackground(mContext.getResources().getDrawable(R.drawable.text_cuss_bg));
        }else {
            holder.mtv_title.setBackground(mContext.getResources().getDrawable(R.drawable.text_cus_bg));
        }
        holder.mtv_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mOnItemClickListener!=null){
                    mOnItemClickListener.OnItemClickListener(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mInfos==null?0:mInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mtv_title;


        public ViewHolder(View itemView) {
            super(itemView);
            mtv_title= (TextView) itemView.findViewById(R.id.item_lv_title);
        }
    }


    public interface OnItemClickListener{
        void OnItemClickListener(int position);
    }
}
