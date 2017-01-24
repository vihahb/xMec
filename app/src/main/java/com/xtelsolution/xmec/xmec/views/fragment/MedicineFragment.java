package com.xtelsolution.xmec.xmec.views.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.xmec.views.adapter.MedicineAdapter;

/**
 * Created by HUNGNT on 1/18/2017.
 */

public class MedicineFragment extends Fragment {
    private View view;
    private RecyclerView rvResultFindMediacine;
    private MedicineAdapter medicineAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view==null){
            view= inflater.inflate(R.layout.fragment_find_medicine, container, false);
            initview();
            rvResultFindMediacine.setAdapter(medicineAdapter);
            rvResultFindMediacine.setLayoutManager(new LinearLayoutManager(getContext()));
            return view;
        }
        return view;
    }

    private void initview() {
        rvResultFindMediacine= (RecyclerView) view.findViewById(R.id.rvResultFindMediacine);
        medicineAdapter = new MedicineAdapter();
    }


}
