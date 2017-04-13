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
import com.xtelsolution.xmec.model.RESP_Disease;
import com.xtelsolution.xmec.model.RESP_User_Medicine;

import java.util.List;

/**
 * Created by phimau on 4/3/2017.
 */

public class MedicineAdapterOptionButton extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public final int BUTTON = 2;
    public final int NORMAL = 1;
    private List<RESP_User_Medicine> mList;
    private boolean ishaveButton;
    private Context mContext;
    private ItemClickListener itemClickListener;
    private ItemClickListener.ItemIconClickListener iconClickListener;
    private ItemClickListener.ButtonAdapterClickListener buttonAdapterClickListener;

    public MedicineAdapterOptionButton(List<RESP_User_Medicine> mList, boolean ishaveButton, Context context) {
        this.mList = mList;
        this.ishaveButton = ishaveButton;
        mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == NORMAL) {
            if (ishaveButton) {
                view = inflater.inflate(R.layout.list_item_medicine_with_editbtn, null);
                return new MedicineAdapterOptionButton.MedicineViewHolder(view);
            } else {
                view = inflater.inflate(R.layout.item_mediacine, null);
                return new MedicineAdapterOptionButton.MedicineViewHolder(view);
            }
        } else {
            view = inflater.inflate(R.layout.item_button_add_medicine, parent, false);
            return new MedicineAdapterOptionButton.AddMedicineViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof MedicineAdapterOptionButton.MedicineViewHolder) {
            MedicineViewHolder medicineViewHolder = (MedicineViewHolder) holder;
            medicineViewHolder.tvNameMedical.setText(mList.get(position).getName());
            if (ishaveButton) {
                medicineViewHolder.btnRemove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
//                    xLog.e(TAG, "onBindViewHolder: " + position + "VITRI");
                        iconClickListener.onItemIconClickListener(mList.get(position), position);
                    }
                });
            }
            medicineViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClickListener(mList.get(position), position);
                }
            });
        } else {
            MedicineAdapterOptionButton.AddMedicineViewHolder addMedicineViewHolder = (MedicineAdapterOptionButton.AddMedicineViewHolder) holder;
            addMedicineViewHolder.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    buttonAdapterClickListener.onButtonAdapterClickListener( );
                }
            });
        }
    }

    private class MedicineViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNameMedical;
        public ImageView btnRemove;

        public MedicineViewHolder(View itemView) {
            super(itemView);
            if (ishaveButton) {
                tvNameMedical = (TextView) itemView.findViewById(R.id.tv_medicine_name);
                btnRemove = (ImageView) itemView.findViewById(R.id.btn_remove);
            } else {
                tvNameMedical = (TextView) itemView.findViewById(R.id.tvIllnessName);
            }
        }
    }

    private class AddMedicineViewHolder extends RecyclerView.ViewHolder {
        private Button btnAdd;

        public AddMedicineViewHolder(View itemView) {
            super(itemView);
            btnAdd = (Button) itemView.findViewById(R.id.it_btn_add);
        }
    }

    @Override
    public int getItemCount() {
        return ishaveButton ? mList.size() + 1 : mList.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == mList.size() ? BUTTON : NORMAL;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setIconClickListener(ItemClickListener.ItemIconClickListener iconClickListener) {
        this.iconClickListener = iconClickListener;
    }

    public void setButtonAdapterClickListener(ItemClickListener.ButtonAdapterClickListener buttonAdapterClickListener) {
        this.buttonAdapterClickListener = buttonAdapterClickListener;
    }

    public void removeItem(int index) {
        mList.remove(index);
        notifyItemRemoved(index);
        notifyItemRangeChanged(index, getItemCount() - 1);
    }

    public void addItem(RESP_User_Medicine medicine) {
        mList.add(medicine);
        notifyItemRangeInserted(mList.size() - 1, 1);
    }

    public void addAll(List<RESP_User_Medicine> data) {
        xLog.e("ADDALL", data.toString());
        mList.addAll(data);
        notifyItemRangeInserted(0, data.size());
    }
}
