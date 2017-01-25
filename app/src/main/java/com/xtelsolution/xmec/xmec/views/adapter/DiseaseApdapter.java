package com.xtelsolution.xmec.xmec.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.xmec.views.activity.DetailDiseaseActivity;

/**
 * Created by phimau on 1/18/2017.
 */

public class DiseaseApdapter extends RecyclerView.Adapter {
    private Context mContext;

    public DiseaseApdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_illness_2, null);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(mContext, DetailDiseaseActivity.class);
                mContext.startActivity(i);
            }
        });
        return new DiseaseApdapter.NewsViewHolder1(view);
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
