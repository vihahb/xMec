package com.xtelsolution.xmec.xmec.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.model.Medicine;
import com.xtelsolution.xmec.model.Stick;
import com.xtelsolution.xmec.xmec.views.activity.DetailDiseaseActivity;

import java.util.List;

/**
 * Created by HUNGNT on 1/18/2017.
 */

public class IllnessAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<Stick> data;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public IllnessAdapter(Context mContext, List<Stick> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == VIEW_TYPE_ITEM) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.list_item_illness, null);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mContext, DetailDiseaseActivity.class);
                    mContext.startActivity(i);
                }
            });
            return new IllnessAdapter.IllnessViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.layout_more_progress, null);
            return new IllnessAdapter.LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof IllnessViewHolder) {
            IllnessViewHolder viewHolder = (IllnessViewHolder) holder;
            viewHolder.tvIllnessName.setText(data.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    private class IllnessViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIllnessName;

        public IllnessViewHolder(View itemView) {
            super(itemView);
            tvIllnessName = (TextView) itemView.findViewById(R.id.tvIllnessName);
        }
    }

    private class LoadingViewHolder extends RecyclerView.ViewHolder {
        public LoadingViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.size() <= position ? VIEW_TYPE_LOADING : VIEW_TYPE_ITEM;
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
