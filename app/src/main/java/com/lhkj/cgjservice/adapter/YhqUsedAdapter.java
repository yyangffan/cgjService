package com.lhkj.cgjservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.reponse.YouhuiQuanUsedResponse;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by user on 2018/2/10.
 */

public class YhqUsedAdapter extends RecyclerView.Adapter<YhqUsedAdapter.ViewHolder> {
    private Context mContext;
    private List<YouhuiQuanUsedResponse.Info> mInfos;
    private LayoutInflater mInflater;
    private OnShuLianClickListener mOnShuLianClickListener;

    public YhqUsedAdapter(Context context, List<YouhuiQuanUsedResponse.Info> infos) {
        mContext = context;
        mInfos = infos;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.item_used_yhq, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        YouhuiQuanUsedResponse.Info info = mInfos.get(position);
        vh.mtv_title.setText(info.name);
        vh.mtv_time.setText(getTime(info.use_time));
        vh.mtv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnShuLianClickListener != null) {
                    mOnShuLianClickListener.OnShuLianClickListener(R.id.item_yhq_jia, position);
                }
            }
        });
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
        private TextView mtv_time;
        private TextView mtv_back;

        public ViewHolder(View itemView) {
            super(itemView);
            mtv_title = (TextView) itemView.findViewById(R.id.item_yhqu_title);
            mtv_time = (TextView) itemView.findViewById(R.id.item_yhqu_time);
            mtv_back = (TextView) itemView.findViewById(R.id.item_yhqu_back);

        }
    }

    public interface OnShuLianClickListener {
        void OnShuLianClickListener(int view_id, int position);
    }

    public String getTime(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy年MM月dd日HH时mm分ss秒");
       @SuppressWarnings("unused")
        long lcc = Long.valueOf(time);
        int i = Integer.parseInt(time);
        String times = sdr.format(new Date(i * 1000L));
        return times;
    }

}
