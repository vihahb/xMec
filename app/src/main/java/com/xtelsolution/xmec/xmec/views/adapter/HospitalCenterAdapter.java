package com.xtelsolution.xmec.xmec.views.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.RESP_Map_Healthy_Care;
import com.xtelsolution.xmec.xmec.views.activity.DetailHospitalActivity;

import java.util.List;

/**
 * Created by phimau on 1/19/2017.
 */

public class HospitalCenterAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private Activity mActivity;
    private List<RESP_Map_Healthy_Care> mListHeathCare;
    private ItemClickListener itemClickListener;

    public HospitalCenterAdapter(Context mContext, List<RESP_Map_Healthy_Care> mListHeathCare) {
        this.mContext = mContext;
        this.mListHeathCare = mListHeathCare;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_hospital_center, null);
        return new HospitalCenterAdapter.HospitalHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        HospitalHolder hospitalHolder = (HospitalHolder) holder;
        RESP_Map_Healthy_Care hospital = mListHeathCare.get(position);
        setIcon(hospitalHolder.imgAvatar, hospital.getType());
        hospitalHolder.tvAddress.setText(hospital.getAddress());
        hospitalHolder.tvName.setText(hospital.getName());
        hospitalHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickListener.onItemClickListener(mListHeathCare.get(position), position);
            }
        });

    }

    public void addAll(List<RESP_Map_Healthy_Care> data) {
        int startIndex = getItemCount();

        mListHeathCare.addAll(data);
        notifyItemRangeRemoved(startIndex, data.size());
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemCount() {
        return mListHeathCare.size();
    }

    private class HospitalHolder extends RecyclerView.ViewHolder {
        public ImageView imgAvatar;
        public TextView tvName;
        public TextView tvAddress;

        public HospitalHolder(View itemView) {
            super(itemView);
            imgAvatar = (ImageView) itemView.findViewById(R.id.img_hosiptal);
            tvName = (TextView) itemView.findViewById(R.id.tv_name_center);
            tvAddress = (TextView) itemView.findViewById(R.id.tv_address_hospital);
        }
    }

    public void setIcon(ImageView avt, int type) {
        if (type == 0)
            avt.setImageResource(R.drawable.ic_hospital);
        else
            avt.setImageResource(R.drawable.ic_drug_store);
    }
}

