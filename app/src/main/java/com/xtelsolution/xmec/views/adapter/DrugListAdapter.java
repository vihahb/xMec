package com.xtelsolution.xmec.views.adapter;

import android.annotation.SuppressLint;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.entity.DrugEntity;

import java.util.ArrayList;

/**
 * Created by vivhp on 8/2/2017.
 */

public class DrugListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private ArrayList<DrugEntity> arrayList;
    private ItemClickListener itemClickListener;
    private ItemClickListener.ItemIconClickListener iconClickListener;
    private ItemClickListener.ButtonAdapterClickListener buttonAdapterClickListener;
    private int ITEM = 1;
    private int ADD_DRUG = 2;

    public DrugListAdapter(ArrayList<DrugEntity> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == ITEM) {
            view = inflater.inflate(R.layout.drug_item_layout, parent, false);
            return new DrugItemHolder(view);
        } else if (viewType == ADD_DRUG){
            view = inflater.inflate(R.layout.item_button_add, parent, false);
            return new AddDrugHolder(view);
        }
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        if (holder instanceof DrugItemHolder){
            DrugItemHolder itemHolder = (DrugItemHolder) holder;
            itemHolder.tvNameDrug.setText(arrayList.get(position).getDrug_name());
            itemHolder.btnEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    iconClickListener.onItemIconClickListener(arrayList.get(position), position);
                }
            });
        }

        if (holder instanceof AddDrugHolder){
            AddDrugHolder addDrugHolder = (AddDrugHolder) holder;
            addDrugHolder.btnAdd.setText("Thêm thuốc");
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (arrayList.get(position).isAdd()){
            return ADD_DRUG;
        } else {
             return ITEM;
        }

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    private class AddDrugHolder extends RecyclerView.ViewHolder {
        Button btnAdd;
        public AddDrugHolder(View itemView) {
            super(itemView);
            btnAdd = (Button) itemView.findViewById(R.id.it_btn_add);
            btnAdd.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonAdapterClickListener.onButtonAdapterClickListener();
                }
            });
        }
    }

    private class DrugItemHolder extends RecyclerView.ViewHolder {

        public TextView tvNameDrug;
        public ImageView btnEdit;

        public DrugItemHolder(View itemView) {
            super(itemView);
            tvNameDrug = (TextView) itemView.findViewById(R.id.tvIllnessName);
            btnEdit = (ImageView) itemView.findViewById(R.id.btn_edit);
        }
    }

    public void refreshAdapter(ArrayList<DrugEntity> arrayList) {
        int size = this.arrayList.size();
        this.arrayList.clear();
        this.arrayList.addAll(arrayList);
        this.arrayList.add(new DrugEntity(true));
        notifyItemChanged(size, arrayList.size());
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setIconClickListener(ItemClickListener.ItemIconClickListener iconClickListener) {
        this.iconClickListener = iconClickListener;
    }

    public void setButtonAdapterClickListener(ItemClickListener.ButtonAdapterClickListener buttonAdapterClickListener) {
        this.buttonAdapterClickListener = buttonAdapterClickListener;
    }
}
