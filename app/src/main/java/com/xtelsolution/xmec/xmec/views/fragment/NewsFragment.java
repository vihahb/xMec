package com.xtelsolution.xmec.xmec.views.fragment;

import android.content.Context;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.OnMoreListener;
import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.model.Article;
import com.xtelsolution.xmec.sdk.utils.Utils;
import com.xtelsolution.xmec.xmec.views.adapter.NewsFeedAdapter;
import com.xtelsolution.xmec.xmec.views.smallviews.RecyclerViewMarginVertical;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUNGNT on 1/20/2017.
 *
 */

public class NewsFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener, OnMoreListener {
    private View mainView;
    private int position;
    private Context mContext;
    private NewsFeedAdapter adapter;
    private SuperRecyclerView recyclerView;
    private List<Article> articleList;
    private Handler mHandler;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        mHandler = new Handler();

        articleList = new ArrayList<>();
        adapter = new NewsFeedAdapter(articleList);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mainView == null) {
            mainView = inflater.inflate(R.layout.fragment_inside_news_feed, container, false);
        }
        initUI(mainView);
        return mainView;
    }

    private void initUI(View view) {
        recyclerView = (SuperRecyclerView) view.findViewById(R.id.rvNewsFeed);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
//        RecyclerViewMarginVertical decoration = new RecyclerViewMarginVertical((int) (getContext().getResources().getDisplayMetrics().density * 8f + 0.5f));
//        recyclerView.addItemDecoration(decoration);
        recyclerView.setRefreshing(true);
        recyclerView.setRefreshListener(this);
        recyclerView.setLoadingMore(true);
        recyclerView.setupMoreListener(this, 10);

        setDataToView();
    }

    private void setDataToView() {
        recyclerView.setAdapter(adapter);
    }

    private List<Article> createTempleatData() {
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Article article = new Article();
            article.setId(i);
            articles.add(article);
        }
        return articles;
    }


    @Override
    public void onRefresh() {
        mHandler.postDelayed(new Runnable() {
            public void run() {
                adapter.clear();
                adapter.addAll(createTempleatData());
            }
        }, 1000);
    }

    @Override
    public void onMoreAsked(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        mHandler.postDelayed(new Runnable() {
            public void run() {
                adapter.addAll(createTempleatData());
            }
        }, 1000);
    }
}
