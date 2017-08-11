package com.xtelsolution.xmec.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.RESP_Medical;

import java.util.ArrayList;

/**
 * Created by phimau on 2/14/2017.
 */

public class MedicalDirectoryAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final int VIEW_TYPE_ITEM = 0;
    private final int VIEW_TYPE_ADD_BUTTON = 1;
    private ArrayList<RESP_Medical> list;
    private Context mContext;
    private ItemClickListener itemClickListener;
    private ItemClickListener.ButtonAdapterClickListener buttonAdapterClickListener;
    private boolean isMaster = false;

    public MedicalDirectoryAdapter(ArrayList<RESP_Medical> data, Context context) {
        this.list = data;
        this.mContext = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(final ViewGroup parent, int viewType) {
        View itemview = null;
        if (viewType == VIEW_TYPE_ITEM) {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            itemview = inflater.inflate(R.layout.item_illness_2, parent, false);
            return new MedicalDirectoryViewHolder(itemview);

        } else {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            itemview = inflater.inflate(R.layout.item_button_add, parent, false);
            return new MedicalDirectoryButtonViewHolder(itemview);
        }

    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {

        if (holder instanceof MedicalDirectoryViewHolder) {
            final RESP_Medical directory = list.get(position);
            MedicalDirectoryViewHolder viewhodlder = (MedicalDirectoryViewHolder) holder;
            viewhodlder.tvMedicalDirectoryName.setText(directory.getName());
            viewhodlder.tvStt.setText(position + 1 + "");
            viewhodlder.tvTimeStamp.setText(Constant.getDate(directory.getBegin_time()));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClickListener((RESP_Medical) (list.get(position)), position);
                }
            });
        }
        if (holder instanceof MedicalDirectoryButtonViewHolder) {
            final MedicalDirectoryButtonViewHolder viewhodlder = (MedicalDirectoryButtonViewHolder) holder;
            viewhodlder.btnAdd.setText("Thêm Y Bạ");
            viewhodlder.btnAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonAdapterClickListener.onButtonAdapterClickListener();
                }
            });

            if (isMaster) {
                viewhodlder.btnAdd.setVisibility(View.VISIBLE);
            } else {
                viewhodlder.btnAdd.setVisibility(View.GONE);
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (list.size() <= position) {
            return VIEW_TYPE_ADD_BUTTON;
        } else {
            return VIEW_TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        if (list == null || list.size() == 0) {
            return 1;
        } else {
            return list.size() + 1;
        }
    }

    public void addAll(ArrayList<RESP_Medical> data) {
        int startIndex = list.size();
        list.addAll(startIndex, data);
        notifyItemRangeInserted(startIndex, data.size());
    }

    public void addCleanAll(ArrayList<RESP_Medical> data, boolean isMaster) {
        list.clear();
        list.addAll(data);
        this.isMaster = isMaster;
        notifyDataSetChanged();
    }

    public void addDefault(ArrayList<RESP_Medical> data, boolean isMaster) {
        int startIndex = list.size();
        list.addAll(startIndex, data);
        this.isMaster = isMaster;
        notifyItemRangeChanged(startIndex, data.size());
    }

    public ArrayList<RESP_Medical> getList() {
        return list;
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void addAllItem(ArrayList<RESP_Medical> arrayList) {
        list.clear();
        list.addAll(arrayList);
        notifyDataSetChanged();
    }

    public void clearAll() {
        list.clear();
        notifyDataSetChanged();
    }

    public void addItem(RESP_Medical medical) {
        list.add(medical);
        notifyItemInserted(list.size());
    }

    public void removeItem(int index) {
//        int index = list.indexOf(medical);
        int size = list.size();
        list.remove(index);
        notifyDataSetChanged();
        xLog.e("SIZE", list.size() + "sdad");
    }

    public void setButtonAdapterClickListener(ItemClickListener.ButtonAdapterClickListener buttonAdapterClickListener) {
        this.buttonAdapterClickListener = buttonAdapterClickListener;
    }

    public void updateItem(String name, long beginTime, int index) {
        list.get(index).setBegin_time(beginTime);
        list.get(index).setName(name);
        notifyItemChanged(index);
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
//            btnAdd.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    Intent i = new Intent(mContext, AddMedicalDetailActivity.class);
//                    mContext.startActivity(i);
//                }
//            });

        }
    }

}
