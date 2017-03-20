package com.xtelsolution.xmec.xmec.views.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.xtelsolution.xmec.listener.list.ButtonAdapterClickListener;
import com.xtelsolution.xmec.model.RESP_Disease;
import com.xtelsolution.xmec.model.RESP_List_Disease;
import com.xtelsolution.xmec.model.entity.Disease;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.xmec.views.activity.AddIllnessActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phimau on 1/22/2017.
 */

public class IllnessAdapter2 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public static final int BUTTON = 2;
    public static final int NORMAL = 1;
    private Context mContext;
    private ArrayList<RESP_Disease> mList;
    private ItemClickListener itemClickListener;
    private ButtonAdapterClickListener buttonAdapterClickListener;

    public IllnessAdapter2(Context mContext, ArrayList<RESP_Disease> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == NORMAL) {
            view = inflater.inflate(R.layout.list_item_illness, parent,false);
            return new IllnessViewHolder(view);

        } else
            view = inflater.inflate(R.layout.item_button_add, parent, false);
            return new IllnessButtonViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof IllnessViewHolder){
            IllnessViewHolder viewHolder = (IllnessViewHolder) holder;
            viewHolder.tvNameIllness.setText(mList.get(position).getName());
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    itemClickListener.onItemClickListener(mList.get(position),position);
                }
            });
        }
        if (holder instanceof IllnessButtonViewHolder){

        }
    }

    @Override
    public int getItemCount() {
        return mList.size()+1;
    }

    class IllnessViewHolder extends RecyclerView.ViewHolder {
        public TextView tvNameIllness;
        public IllnessViewHolder(View itemView) {
            super(itemView);
            tvNameIllness = (TextView) itemView.findViewById(R.id.tvIllnessName);
        }
    }

    class IllnessButtonViewHolder extends RecyclerView.ViewHolder {
        Button btnAdd;
        public IllnessButtonViewHolder(View itemView) {
            super(itemView);
            btnAdd = (Button) itemView.findViewById(R.id.it_btn_add);
            btnAdd.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    buttonAdapterClickListener.onButtonAdapterClickListener(btnAdd);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
         return mList.size() <= position ? BUTTON : NORMAL;
    }

    public  void addAll(List<RESP_Disease> data){
        int startIndex = mList.size();
        mList.addAll(startIndex,data);
        notifyItemRangeInserted(startIndex,data.size());
    }

    public void clearData(){
        int size = mList.size();
        mList.clear();
        notifyItemRangeRemoved(0,size);
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setButtonAdapterClickListener(ButtonAdapterClickListener buttonAdapterClickListener) {
        this.buttonAdapterClickListener = buttonAdapterClickListener;
    }
}
