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
 * Created by user on 2018/2/9.
 */

public class YhqAdapter extends RecyclerView.Adapter<YhqAdapter.ViewHolder> {
    private Context mContext;
    private List<CustomerLock.Info> mInfos;
    private LayoutInflater mInflater;
    private OnShuLianClickListener mOnShuLianClickListener;

    public YhqAdapter(Context context, List<CustomerLock.Info> infos) {
        mContext = context;
        mInfos = infos;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.item_yhq_rc, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        CustomerLock.Info info = mInfos.get(position);
        vh.mtv_title.setText(info.name);
        vh.mtv_all.setText(info.copnum);
        vh.mtv_num.setText(info.getUse_num()+"");
        vh.mtv_jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnShuLianClickListener != null) {
                    mOnShuLianClickListener.OnShuLianClickListener(R.id.item_yhq_jia,position);
                }
            }
        });
        vh.mtv_jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnShuLianClickListener != null) {
                    mOnShuLianClickListener.OnShuLianClickListener(R.id.item_yhq_jian,position);
                }
            }
        });
        boolean can_use=info.isCan_use();
        if(can_use){
            vh.mtv_title.setTextColor(mContext.getResources().getColor(R.color.black));
            vh.mtv_all.setTextColor(mContext.getResources().getColor(R.color.black));
            vh.mtv_num.setTextColor(mContext.getResources().getColor(R.color.black));
            vh.mtv_jia.setTextColor(mContext.getResources().getColor(R.color.black));
            vh.mtv_jian.setTextColor(mContext.getResources().getColor(R.color.black));
        }else {
            vh.mtv_title.setTextColor(mContext.getResources().getColor(R.color.gray_text));
            vh.mtv_all.setTextColor(mContext.getResources().getColor(R.color.gray_text));
            vh.mtv_num.setTextColor(mContext.getResources().getColor(R.color.gray_text));
            vh.mtv_jia.setTextColor(mContext.getResources().getColor(R.color.gray_text));
            vh.mtv_jian.setTextColor(mContext.getResources().getColor(R.color.gray_text));
        }
    }

    public void setOnShuLianClickListener(OnShuLianClickListener onShuLianClickListener) {
        mOnShuLianClickListener = onShuLianClickListener;
    }

    @Override
    public int getItemCount() {
        return mInfos == null ? 0 : mInfos.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView mtv_title;
        private TextView mtv_all;
        private TextView mtv_jian;
        private TextView mtv_jia;
        private TextView mtv_num;

        public ViewHolder(View itemView) {
            super(itemView);
            mtv_title = (TextView) itemView.findViewById(R.id.item_yhq_title);
            mtv_all = (TextView) itemView.findViewById(R.id.item_yhq_all);
            mtv_jian = (TextView) itemView.findViewById(R.id.item_yhq_jian);
            mtv_jia = (TextView) itemView.findViewById(R.id.item_yhq_jia);
            mtv_num = (TextView) itemView.findViewById(R.id.item_yhq_num);
        }
    }

    public interface OnShuLianClickListener {
        void OnShuLianClickListener(int view_id,int position);
    }


}
