package com.xtelsolution.xmec.xmec.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xtelsolution.xmec.R;

/**
 * Created by phimau on 1/22/2017.
 */

public class HealtRecoderAdapter  extends RecyclerView.Adapter<HealtRecoderAdapter.HealthRecoderViewHoder> {

    Context context;

    public HealtRecoderAdapter(Context context) {
        this.context = context;
    }

    @Override
    public HealthRecoderViewHoder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_health_recoder,null);

        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return  new HealthRecoderViewHoder(view);
    }

    @Override
    public void onBindViewHolder(HealthRecoderViewHoder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 3;
    }

    class HealthRecoderViewHoder extends RecyclerView.ViewHolder{

        public HealthRecoderViewHoder(View itemView) {
            super(itemView);
        }
    }
}
