package com.xtelsolution.xmec.views.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.model.ControlDetailDisease;
import com.xtelsolution.xmec.views.activity.DetailControlDiseaseActivity;
import com.xtelsolution.xmec.views.adapter.ControlDetailDiseaseAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phimau on 1/22/2017.
 */

public class DetailDiseaseFragment extends Fragment {
    private View view;
    private ListView list_control_detail;
    private ControlDetailDiseaseAdapter adapter;
    private List<ControlDetailDisease> list;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initData();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_detail_disease, container, false);
        }
        initUI(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initData() {
        list = new ArrayList<>();
        list.add(new ControlDetailDisease(1, getContext().getResources().getString(R.string.control_disease_trieu_trung), R.mipmap.ic_sym));
        list.add(new ControlDetailDisease(2, getContext().getResources().getString(R.string.control_disease_nguyen_nhan), R.mipmap.ic_nguyen_nhan));
        list.add(new ControlDetailDisease(3, getContext().getResources().getString(R.string.control_disease_phong_ngua), R.mipmap.ic_prevent));
        list.add(new ControlDetailDisease(4, getContext().getResources().getString(R.string.control_disease_dieu_tri), R.mipmap.ic_remediation));
        adapter = new ControlDetailDiseaseAdapter(getActivity(), list);
    }

    private void initUI(View view) {
        list_control_detail = (ListView) view.findViewById(R.id.list_control_detail);
        initControl();
    }

    private void initControl() {
        list_control_detail.setAdapter(adapter);
        list_control_detail.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), DetailControlDiseaseActivity.class);
                String action = list.get(i).getName();
                intent.putExtra(Constant.INTENT_ACTION_DETAIL_CONTROL_DISEASE, action);
                startActivity(intent);
            }
        });

    }


}
