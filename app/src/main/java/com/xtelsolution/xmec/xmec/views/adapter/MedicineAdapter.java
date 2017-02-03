package com.xtelsolution.xmec.xmec.views.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.model.Medicine;
import com.xtelsolution.xmec.model.Stick;
import com.xtelsolution.xmec.xmec.views.activity.DetailHospitalActivity;

import java.util.List;

/**
 * Created by phimau on 1/24/2017.
 */

public class MedicineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    List<Medicine> data;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;

    public MedicineAdapter(List<Medicine> data) {
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_ITEM) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mediacine, null);
            return new MedicineAdapterViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_more_progress, null);
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof MedicineAdapterViewHolder) {
            MedicineAdapterViewHolder holder1 = (MedicineAdapterViewHolder) holder;
            holder1.tvIllnessName.setText(data.get(position).getName());
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    class MedicineAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIllnessName;

        public MedicineAdapterViewHolder(View itemView) {
            super(itemView);
            tvIllnessName = (TextView) itemView.findViewById(R.id.tvIllnessName);
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
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

    public void add(Medicine medicine) {
        insert(medicine, data.size());
    }

    public void insert(Medicine medicine, int position) {
        data.add(position, medicine);
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

    public void addAll(List<Medicine> medicines) {
        int startIndex = data.size();
        data.addAll(startIndex, medicines);
        notifyItemRangeInserted(startIndex, medicines.size());
    }
}
