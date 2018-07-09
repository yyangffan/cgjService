package com.lhkj.cgjservice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.lhkj.cgjservice.R;
import com.lhkj.cgjservice.bean.LiPinItem;
import com.lhkj.cgjservice.entity.RunTime;

import java.util.List;

/**
 * Created by user on 2018/3/10.
 */

public class LiPinAdapter extends RecyclerView.Adapter<LiPinAdapter.ViewHolder> {
    private Context mContext;
    private List<LiPinItem> mLiPinItemList;
    private LayoutInflater mInflater;
    private OnItemClickListener mOnItemClickListener;


    public LiPinAdapter(Context context, List<LiPinItem> liPinItemList) {
        mContext = context;
        mLiPinItemList = liPinItemList;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = mInflater.inflate(R.layout.layout_lipin, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder vh, final int position) {
        LiPinItem liPinItem = mLiPinItemList.get(position);
        Glide.with(mContext).load(RunTime.BASEURL + liPinItem.getImgv_url()).into(vh.mImageView);
        vh.mtv_title.setText(liPinItem.getName());
        vh.mtv_time.setText(liPinItem.getTime());
        vh.mll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.OnItemClikcListner(position);
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return mLiPinItemList == null ? 0 : mLiPinItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView mImageView;
        private TextView mtv_title;
        private TextView mtv_time;
        private LinearLayout mll;

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.lipin_imgv);
            mtv_title = (TextView) itemView.findViewById(R.id.lipin_title);
            mtv_time = (TextView) itemView.findViewById(R.id.lipin_time);
            mll = (LinearLayout) itemView.findViewById(R.id.lipin_ll);

        }
    }

    public interface OnItemClickListener {
        void OnItemClikcListner(int position);
    }

}
