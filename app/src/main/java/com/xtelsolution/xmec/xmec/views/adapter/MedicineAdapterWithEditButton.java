package com.xtelsolution.xmec.xmec.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.Medicine;

import java.util.ArrayList;

/**
 * Created by HUNGNT on 2/14/2017.
 */

public class MedicineAdapterWithEditButton extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public static final int BUTTON = 2;
    public static final int NORMAL = 1;
    private Context mContext;
    private ArrayList<Medicine> mList;
    private ItemClickListener itemClickListener;

    public MedicineAdapterWithEditButton(Context mContext, ArrayList<Medicine> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == NORMAL) {
            view = inflater.inflate(R.layout.list_item_medicine_with_editbtn, null);
            return new MedicineViewHolder(view);
        } else {
            view = inflater.inflate(R.layout.item_button_add, parent, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            return new AddMedicineViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MedicineViewHolder) {
            MedicineViewHolder medicineViewHolder = (MedicineViewHolder) holder;
            medicineViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClickListener(mList.get(position),position);
                }
            });
        }else {
            AddMedicineViewHolder addMedicineViewHolder= (AddMedicineViewHolder) holder;
            addMedicineViewHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(mContext, "AddMedicineViewHolder clicked", Toast.LENGTH_SHORT).show();
                    addItem();
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    @Override
    public int getItemViewType(int position) {
        return mList.size() > position ? NORMAL : BUTTON;
    }

    private class MedicineViewHolder extends RecyclerView.ViewHolder {
        public MedicineViewHolder(View itemView) {
            super(itemView);
        }
    }

    private class AddMedicineViewHolder extends RecyclerView.ViewHolder {
        private Button btnAdd;

        public AddMedicineViewHolder(View itemView) {
            super(itemView);
            btnAdd = (Button) itemView.findViewById(R.id.it_btn_add);
        }
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }
    public void addItem(){
        mList.add(new Medicine(0,"Tên thuốc (Thêm)","Type"));
        notifyDataSetChanged();
    }
}
