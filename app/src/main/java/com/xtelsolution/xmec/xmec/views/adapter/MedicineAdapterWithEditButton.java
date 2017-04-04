package com.xtelsolution.xmec.xmec.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.REQ_Medicine;

import java.util.List;

/**
 * Created by HUNGNT on 2/14/2017.
 */

public class MedicineAdapterWithEditButton extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private static final String TAG = "MedicineAdapterWithEditButton";
    public static final int BUTTON = 2;
    public static final int NORMAL = 1;
    private Context mContext;
    private List<REQ_Medicine> mList;
    private ItemClickListener itemClickListener;
    private ItemClickListener.ItemIconClickListener iconClickListener;
    private ItemClickListener.ButtonAdapterClickListener buttonAdapterClickListener;

    public MedicineAdapterWithEditButton(Context mContext, List<REQ_Medicine> mList) {
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
            view = inflater.inflate(R.layout.item_button_add_medicine, parent, false);
            view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
            return new AddMedicineViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MedicineViewHolder) {
            MedicineViewHolder medicineViewHolder = (MedicineViewHolder) holder;
            ((MedicineViewHolder) holder).tvNameMedical.setText(mList.get(position).getName());
            ((MedicineViewHolder) holder).btnRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    xLog.e(TAG, "onBindViewHolder: " + position + "VITRI");
                    iconClickListener.onItemIconClickListener(mList.get(position), position);
                }
            });
            medicineViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClickListener(mList.get(position), position);
                }
            });
        } else {
            AddMedicineViewHolder addMedicineViewHolder = (AddMedicineViewHolder) holder;
            addMedicineViewHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonAdapterClickListener.onButtonAdapterClickListener((Button) v);
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
        public TextView tvNameMedical;
        public ImageView btnRemove;

        public MedicineViewHolder(View itemView) {
            super(itemView);
            tvNameMedical = (TextView) itemView.findViewById(R.id.tv_medicine_name);
            btnRemove = (ImageView) itemView.findViewById(R.id.btn_remove);
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

    //    public void addItem(){
//        mList.add(new Medicine(0,"Tên thuốc (Thêm)","Type"));
//        notifyDataSetChanged();
//    }
    public void addAll(List<REQ_Medicine> data) {
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public void add(REQ_Medicine medicine) {
        mList.add(medicine);
        notifyItemInserted(mList.size() - 1);
    }

    public void removeItem(int index) {
        mList.remove(index);
        notifyItemRemoved(index);
        notifyItemRangeChanged(index, getItemCount() - 1);
//        getItem(100);
    }

    public void setIconClickListener(ItemClickListener.ItemIconClickListener iconClickListener) {
        this.iconClickListener = iconClickListener;
    }

    public void setButtonAdapterClickListener(ItemClickListener.ButtonAdapterClickListener buttonAdapterClickListener) {
        this.buttonAdapterClickListener = buttonAdapterClickListener;
    }

    public void getItem(int index) {
        for (REQ_Medicine medicine : mList) {
            xLog.e(TAG, "getItem: " + "ITEM " + medicine.toString());

        }
    }
}
