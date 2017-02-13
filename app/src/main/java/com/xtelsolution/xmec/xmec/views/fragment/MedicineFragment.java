package com.xtelsolution.xmec.xmec.views.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.listener.EndlessParentScrollListener;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.Medicine;
import com.xtelsolution.xmec.xmec.views.adapter.MedicineAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUNGNT on 1/18/2017.
 */

public class MedicineFragment extends Fragment {

    private static final String TAG = MedicineFragment.class.getName();

    private RecyclerView rvResultFindMediacine;

    private MedicineAdapter medicineAdapter;

    private List<Medicine> list;

    private Context mContext;

    private Handler handler;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_find_medicine, container, false);

        initView(view);

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        list = new ArrayList<>();
        medicineAdapter = new MedicineAdapter(mContext, list);
        handler = new Handler();
    }

    private void initView(View view) {

        rvResultFindMediacine = (RecyclerView) view.findViewById(R.id.rvResultFindMediacine);

        rvResultFindMediacine.setAdapter(medicineAdapter);

        LinearLayoutManager manager = new LinearLayoutManager(mContext);

        rvResultFindMediacine.setLayoutManager(manager);

        rvResultFindMediacine.setNestedScrollingEnabled(false);

        NestedScrollView scrollView = (NestedScrollView) view.findViewById(R.id.nesstedScrollView);

        scrollView.setOnScrollChangeListener(new EndlessParentScrollListener(manager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {

                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {

                        medicineAdapter.addAll(createTempData(medicineAdapter.getItemCount()));
                    }
                }, 1500);

            }

            @Override
            public void onHide() {

                mContext.sendBroadcast(new Intent(Constant.ACTION_HIDE_BOTTOM_BAR));
            }

            @Override
            public void onShow() {

                mContext.sendBroadcast(new Intent(Constant.ACTION_SHOW_BOTTOM_BAR));
            }
        });

        initControl();

        medicineAdapter.addAll(createTempData(0));
    }

    private void initControl() {
        medicineAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(Object item, int position) {
                Toast.makeText(mContext, "" + position, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private List<Medicine> createTempData(int size) {

        List<Medicine> list = new ArrayList<>();

        for (int i = size; i < size + 10; i++) {

            list.add(new Medicine(i, "Tên thuốc số " + i, "a"));
        }

        return list;
    }


}
