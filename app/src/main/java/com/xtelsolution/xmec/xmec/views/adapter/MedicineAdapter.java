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
 * Created by phimau on 1/24/2017.
 */

public class MedicineAdapter extends RecyclerView.Adapter<MedicineAdapter.MedicineAdapterViewHolder> {

    @Override
    public MedicineAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mediacine,parent,false);
        return  new MedicineAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(MedicineAdapterViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 5;
    }

    class MedicineAdapterViewHolder extends RecyclerView.ViewHolder {
        public MedicineAdapterViewHolder(View itemView) {
            super(itemView);
        }
    }
}
