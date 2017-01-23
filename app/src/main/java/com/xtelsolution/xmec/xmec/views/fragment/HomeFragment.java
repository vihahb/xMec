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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        adapter = new DiseaseApdapter(getContext());
        rvDisease = (RecyclerView) view.findViewById(R.id.rvDisease);
        rvDisease.setAdapter(adapter);
        rvDisease.setLayoutManager(new LinearLayoutManager(getContext()));
    }
}
