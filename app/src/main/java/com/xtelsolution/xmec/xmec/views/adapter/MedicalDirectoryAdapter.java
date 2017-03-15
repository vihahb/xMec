package com.xtelsolution.xmec.xmec.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.RESP_Medical;
import com.xtelsolution.xmec.xmec.views.activity.AddMedicalActivity;

import java.util.ArrayList;

/**
 * Created by phimau on 2/14/2017.
 */

public class MedicalDirectoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<RESP_Medical> list;
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_ADD_BUTTON = 1;
    private Context mContext;
    private ItemClickListener itemClickListener;
    public MedicalDirectoryAdapter(ArrayList<RESP_Medical> data, Context context) {
        this.list = data;
        this.mContext =context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemview = null;
        if (viewType == VIEW_TYPE_ITEM) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            itemview = inflater.inflate(R.layout.item_illness_2, parent,false);
            return new MedicalDirectoryViewHolder(itemview);

        } else {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            itemview = inflater.inflate(R.layout.item_button_add, parent,false);
            return new MedicalDirectoryButtonViewHolder(itemview);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MedicalDirectoryViewHolder) {
            final RESP_Medical directory = list.get(position);
            MedicalDirectoryViewHolder viewhodlder = (MedicalDirectoryViewHolder) holder;
            viewhodlder.tvMedicalDirectoryName.setText(directory.getName());
            viewhodlder.tvStt.setText(position+ 1+"");
            viewhodlder.tvTimeStamp.setText(Constant.getDate(directory.getBegin_time()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClickListener((RESP_Medical)(list.get(position)),position);
                }
            });
        }
        if (holder instanceof MedicalDirectoryButtonViewHolder){
            final MedicalDirectoryButtonViewHolder viewhodlder = (MedicalDirectoryButtonViewHolder) holder;
            viewhodlder.btnAdd.setText("Thêm Y Bạ");
        }
    }

    class MedicalDirectoryViewHolder extends RecyclerView.ViewHolder {
        public TextView tvMedicalDirectoryName;
        public TextView tvTimeStamp;
        public TextView tvStt;

        public MedicalDirectoryViewHolder(View itemView) {
            super(itemView);
            tvStt = (TextView) itemView.findViewById(R.id.tv_stt);
            tvMedicalDirectoryName = (TextView) itemView.findViewById(R.id.tvIllnessName);
            tvTimeStamp = (TextView) itemView.findViewById(R.id.tv_time_stamp);

        }
    }
    class MedicalDirectoryButtonViewHolder extends RecyclerView.ViewHolder {
        public Button btnAdd;

        public MedicalDirectoryButtonViewHolder(View itemView) {
            super(itemView);
            btnAdd = (Button) itemView.findViewById(R.id.it_btn_add);
            btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent i = new Intent(mContext,AddMedicalActivity.class);
                    mContext.startActivity(i);
                }
            });

        }
    }

    @Override
    public int getItemViewType(int position) {
        return list.size() <= position ? VIEW_TYPE_ADD_BUTTON : VIEW_TYPE_ITEM;
    }

    public void addAll(ArrayList<RESP_Medical> data){
        int startIndex = list.size();
        list.addAll(startIndex,data);
        notifyItemRangeInserted(startIndex,data.size());
    }

    @Override
    public int getItemCount() {
        return list.size()+1;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
}
