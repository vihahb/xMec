package com.xtelsolution.xmec.views.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.model.RESP_Map_Healthy_Care;
import com.xtelsolution.xmec.views.inf.IMapView;

import java.util.List;

/**
 * Created by vivhp on 8/11/2017.
 */

public class AdapterFindResult extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Activity activity;
    private List<RESP_Map_Healthy_Care> arrayList;
    private IMapView view;


    public AdapterFindResult(Activity activity, List<RESP_Map_Healthy_Care> arrayList, IMapView view) {
        this.activity = activity;
        this.arrayList = arrayList;
        this.view = view;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.item_find_result, parent, false);
        return new FindResultHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        FindResultHolder findResultHolder = (FindResultHolder) holder;
        final RESP_Map_Healthy_Care care = arrayList.get(position);
        findResultHolder.tv_name.setText(care.getName());
        findResultHolder.tv_address.setText(care.getAddress());
        findResultHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.onCLickRecycleItem(care);
            }
        });
        if (care.getName().contains("thuốc"))
            findResultHolder.setHospitalIcon(false);
        else
            findResultHolder.setHospitalIcon(true);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class FindResultHolder extends RecyclerView.ViewHolder {

        private ImageView img_icon;
        private TextView tv_name, tv_address, tv_phone;


        public FindResultHolder(View itemView) {
            super(itemView);
            img_icon = (ImageView) itemView.findViewById(R.id.image_icon);
            tv_name = (TextView) itemView.findViewById(R.id.tv_name);
            tv_address = (TextView) itemView.findViewById(R.id.tv_address);
            tv_phone = (TextView) itemView.findViewById(R.id.tv_phone);
        }

        public void setHospitalIcon(boolean isHospital) {
            if (isHospital)
                img_icon.setImageResource(R.mipmap.ic_hospital);
            else
                img_icon.setImageResource(R.mipmap.ic_medicine_store);
        }
    }
}
