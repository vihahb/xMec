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
import com.xtelsolution.xmec.xmec.views.adapter.IllnessAdapter;
import com.xtelsolution.xmec.xmec.views.adapter.NewsAdapter;
import com.xtelsolution.xmec.xmec.views.smallviews.RecyclerViewMargin;

/**
 * Created by HUNGNT on 1/18/2017.
 */

public class SearchFragment extends Fragment {

    private RecyclerView rvResultFindNews;
    private RecyclerView rvResultFindIllness;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.search_fragment, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initRecyclerView();
    }

    private void initRecyclerView() {
        //////////////////////////////////////////////////
        NewsAdapter adapter = new NewsAdapter(getContext());
        rvResultFindNews.setAdapter(adapter);
        rvResultFindNews.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        RecyclerViewMargin decoration = new RecyclerViewMargin((int)(getContext().getResources().getDisplayMetrics().density*8f+0.5f));
        rvResultFindNews.addItemDecoration(decoration);
        ////
        IllnessAdapter illnessAdapter = new IllnessAdapter(getContext());
        rvResultFindIllness.setAdapter(illnessAdapter);
        rvResultFindIllness.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    private void initView() {
        rvResultFindNews = (RecyclerView) getActivity().findViewById(R.id.rvResultFindNews);
        rvResultFindIllness = (RecyclerView) getActivity().findViewById(R.id.rvResultFindIllnesses);

    }
}
