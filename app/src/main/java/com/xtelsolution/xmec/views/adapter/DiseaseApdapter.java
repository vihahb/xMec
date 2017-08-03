package com.xtelsolution.xmec.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.Stick;

import java.util.List;

/**
 * Created by phimau on 1/18/2017.
 */

public class DiseaseApdapter extends RecyclerView.Adapter {
    private final int ITEM_TYPE = 0, ITEM_BUTTON = 1;
    private Context mContext;
    private List<Stick> data;
    private ItemClickListener itemClickListener;

    public DiseaseApdapter(Context mContext, List<Stick> data) {
        this.mContext = mContext;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE) {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_illness_2, null);

            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            return new DiseaseViewHolder(view);
        } else {
            View view = LayoutInflater.from(mContext).inflate(R.layout.item_button_add, null);

            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            return new ButtonAddViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof DiseaseViewHolder) {
            DiseaseViewHolder holder1 = (DiseaseViewHolder) holder;
            holder1.tvIllnessName.setText(data.get(position).getName());
            holder1.stt.setText("" + (position + 1));
            holder1.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClickListener(data.get(position), position);
                }
            });
        } else {
            ButtonAddViewHolder addViewHolder = (ButtonAddViewHolder) holder;
            addViewHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClickListener(null, position);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public int getItemViewType(int position) {
        return data.size() > position ? ITEM_TYPE : ITEM_BUTTON;
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

    private class DiseaseViewHolder extends RecyclerView.ViewHolder {
        private TextView tvIllnessName, stt;

        public DiseaseViewHolder(View itemView) {
            super(itemView);
            tvIllnessName = (TextView) itemView.findViewById(R.id.tvIllnessName);
            stt = (TextView) itemView.findViewById(R.id.tv_stt);
        }
    }

    private class ButtonAddViewHolder extends RecyclerView.ViewHolder {
        private Button btnAdd;

        public ButtonAddViewHolder(View itemView) {
            super(itemView);
            btnAdd = (Button) itemView.findViewById(R.id.it_btn_add);
        }
    }
}
