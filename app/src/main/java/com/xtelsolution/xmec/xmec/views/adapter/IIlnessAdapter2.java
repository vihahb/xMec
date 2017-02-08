package com.xtelsolution.xmec.xmec.views.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xtelsolution.xmec.entity.IIlness;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.listener.list.ItemClickListener;

import java.util.ArrayList;

/**
 * Created by phimau on 1/22/2017.
 */

public class IIlnessAdapter2 extends RecyclerView.Adapter<IIlnessAdapter2.IllnessViewHolder> {
    public static final int mButton = 2;
    public static final int Normal = 1;
    private Context mContext;
    private ArrayList<IIlness> mList;
    private ItemClickListener itemClickListener;

    public IIlnessAdapter2(Context mContext, ArrayList<IIlness> mList) {
        this.mContext = mContext;
        this.mList = mList;
    }

    @Override
    public IllnessViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if (viewType == Normal) {
            view = inflater.inflate(R.layout.list_item_illness, null);

        } else
            view = inflater.inflate(R.layout.item_button_add, parent, false);

        view.setLayoutParams(new RecyclerView.LayoutParams(RecyclerView.LayoutParams.MATCH_PARENT, RecyclerView.LayoutParams.WRAP_CONTENT));
        return new IllnessViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IllnessViewHolder holder, final int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                itemClickListener.onItemClickListener(mList.get(position), position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size() + 1;
    }

    class IllnessViewHolder extends RecyclerView.ViewHolder {
        public IllnessViewHolder(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return mList.size() > position ? Normal : mButton;
    }

    public void setOnItemClickListener(ItemClickListener itemClickListener) {

        this.itemClickListener = itemClickListener;
    }
}
