package com.xtelsolution.xmec.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.Medicine;

import java.util.List;

/**
 * Created by phimau on 1/24/2017.
 */

public class MedicineAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_LOADING = 1;
    private List<Medicine> data;
    private Context mContext;
    private ItemClickListener mItemClickListener;

    private boolean isLoadMore = false;

    public MedicineAdapter(Context mContext, List<Medicine> data) {
        this.data = data;
        this.mContext = mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_ITEM) {
            view = LayoutInflater.from(mContext).inflate(R.layout.item_mediacine, parent, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            return new MedicineAdapterViewHolder(view);
        } else if (viewType == VIEW_TYPE_LOADING) {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.progressbar, parent, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            return new LoadingViewHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MedicineAdapterViewHolder) {
            MedicineAdapterViewHolder hd = (MedicineAdapterViewHolder) holder;
            hd.tvIllnessName.setText(data.get(position).getName());
            // action
            hd.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClickListener(data.get(position), position);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return isLoadMore ? data.size() + 1 : data.size();
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

    public void setOnItemClickListener(ItemClickListener itemViewHolderClickListener) {
        this.mItemClickListener = itemViewHolderClickListener;
    }

    public void setLoadMore(boolean isLoadMore) {
        this.isLoadMore = isLoadMore;
        notifyDataSetChanged();
    }

    class MedicineAdapterViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIllnessName;

        public MedicineAdapterViewHolder(View itemView) {
            super(itemView);
            tvIllnessName = (TextView) itemView.findViewById(R.id.tvIllnessName);
        }
    }

    class LoadingViewHolder extends RecyclerView.ViewHolder {
        public ProgressBar progressBar;

        public LoadingViewHolder(View itemView) {
            super(itemView);
            progressBar = (ProgressBar) itemView.findViewById(R.id.progress_bar);
        }

        public void hideProressBar() {
            progressBar.setVisibility(View.GONE);
        }
    }
}
