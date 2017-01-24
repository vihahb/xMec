package com.xtelsolution.xmec.xmec.views.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jaredrummler.materialspinner.MaterialSpinner;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.xmec.views.activity.ProfileActivity;
import com.xtelsolution.xmec.xmec.views.adapter.DiseaseApdapter;
import com.xtelsolution.xmec.xmec.views.adapter.NewsAdapter;
import com.xtelsolution.xmec.xmec.views.smallviews.RecyclerViewMarginHorizontal;
import com.xtelsolution.xmec.xmec.views.smallviews.RecyclerViewMarginVertical;

/**
 * Created by HUNGNT on 1/18/2017.
 */

public class HomeFragment extends Fragment {
    private DiseaseApdapter adapter;
    private RecyclerView rvDisease;
    private TextView btnProfile;
    private Context mContext;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        adapter = new DiseaseApdapter(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initUI(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvDisease = (RecyclerView) view.findViewById(R.id.rvDisease);
        rvDisease.setAdapter(adapter);
        rvDisease.setLayoutManager(new LinearLayoutManager(getContext()));
        rvDisease.setNestedScrollingEnabled(false);
        MaterialSpinner spinner = (MaterialSpinner) view.findViewById(R.id.spcategorize);
        spinner.setItems("Bệnh", "Thuốc");

    }

    public void initUI(View view) {
        btnProfile = (TextView) view.findViewById(R.id.btnProfile);

        btnProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(mContext, ProfileActivity.class));
            }
        });
    }
}
