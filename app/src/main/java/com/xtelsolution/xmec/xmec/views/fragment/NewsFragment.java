package com.xtelsolution.xmec.xmec.views.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.xmec.views.adapter.NewsFeedAdapter;
import com.xtelsolution.xmec.xmec.views.smallviews.RecyclerViewMarginVertical;

/**
 * Created by HUNGNT on 1/20/2017.
 */

public class NewsFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_inside_news_feed, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        NewsFeedAdapter adapter = new NewsFeedAdapter(getContext());
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.rvNewsFeed);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        RecyclerViewMarginVertical decoration = new RecyclerViewMarginVertical((int)(getContext().getResources().getDisplayMetrics().density*8f+0.5f));
        recyclerView.addItemDecoration(decoration);
    }
}
