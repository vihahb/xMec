package com.xtelsolution.xmec.xmec.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xtelsolution.xmec.R;

/**
 * Created by HUNGNT on 1/18/2017.
 */

public class IllnessAdapter extends RecyclerView.Adapter {
    private Context mContext;

    public IllnessAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_illness, null);
        return new IllnessAdapter.IllnessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return 9;
    }

    private class IllnessViewHolder extends RecyclerView.ViewHolder {
        public IllnessViewHolder(View itemView) {
            super(itemView);
        }
    }
}
