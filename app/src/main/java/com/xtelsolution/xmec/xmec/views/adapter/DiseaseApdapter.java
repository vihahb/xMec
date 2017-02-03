package com.xtelsolution.xmec.xmec.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.model.Stick;
import com.xtelsolution.xmec.xmec.views.activity.DetailDiseaseActivity;

import java.util.List;

/**
 * Created by phimau on 1/18/2017.
 */

public class DiseaseApdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<Stick> data;

    public DiseaseApdapter(Context mContext, List<Stick> data) {
        this.mContext = mContext;
        this.data = data;
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
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof NewsViewHolder) {
            NewsViewHolder holder1 = (NewsViewHolder) holder;
            holder1.tvIllnessName.setText(data.get(position).getName());
            holder1.stt.setText("" + (position + 1));
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class NewsViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIllnessName, stt;

        public NewsViewHolder(View itemView) {
            super(itemView);
            tvIllnessName = (TextView) itemView.findViewById(R.id.tvIllnessName);
            stt = (TextView) itemView.findViewById(R.id.stt);
        }
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    public void add(Stick stick) {
        insert(stick, data.size());
    }

    public void insert(Stick stick, int position) {
        data.add(position, stick);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    public void clear() {
        int size = data.size();
        data.clear();
        notifyItemRangeRemoved(0, size);
    }

    public void addAll(List<Stick> sticks) {
        int startIndex = data.size();
        data.addAll(startIndex, sticks);
        notifyItemRangeInserted(startIndex, sticks.size());
    }
}
