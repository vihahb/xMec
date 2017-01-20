package com.xtelsolution.xmec.xmec.views.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.xmec.views.activity.DetailHospitalActivity;

/**
 * Created by phimau on 1/19/2017.
 */

public class HospitalCenterAdapter extends RecyclerView.Adapter{
    private Context mContext;
    private Activity mActivity;
    public HospitalCenterAdapter(Context mContext,Activity activity) {
        this.mContext = mContext;
        this.mActivity = activity;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_hospital_center,null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext, "Mau ", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(mActivity,DetailHospitalActivity.class);
                mActivity.startActivity(i);
            }
        });
        return new HospitalCenterAdapter.NewsViewHolder1(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }
    private class NewsViewHolder1 extends RecyclerView.ViewHolder {
        public NewsViewHolder1(View itemView) {
            super(itemView);
        }
    }
}

