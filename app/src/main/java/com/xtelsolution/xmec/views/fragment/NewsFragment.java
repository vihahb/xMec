package com.xtelsolution.xmec.views.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.malinskiy.superrecyclerview.SuperRecyclerView;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.model.entity.Article;
import com.xtelsolution.xmec.listener.RecyclerOnScrollListener;
import com.xtelsolution.xmec.presenter.NewsFeedPresenter;
import com.xtelsolution.xmec.views.adapter.NewsFeedAdapter;
import com.xtelsolution.xmec.views.inf.INewsFeedView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUNGNT on 1/20/2017.
 */

public class NewsFragment extends BasicFragment implements SwipeRefreshLayout.OnRefreshListener, /*OnMoreListener,*/ INewsFeedView {
    private static final String TAG = "NewsFragment";
    private Context mContext;
    private NewsFeedAdapter adapter;
    private SuperRecyclerView recyclerView;
    private List<Article> articleList;
    private LinearLayoutManager manager;
    private NewsFeedPresenter presenter;
    private String rss_url, typeName;
    private static final String TYPE_NAME = "typeName";
    private static final String RSS_URL = "rss_url";

    public static NewsFragment newInstance(String rss_url, String typeName) {
        NewsFragment myFragment = new NewsFragment();

        Bundle args = new Bundle();
        args.putString(RSS_URL, rss_url);
        args.putString(TYPE_NAME, typeName);
        myFragment.setArguments(args);

        return myFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getArguments();
        rss_url = bundle.getString(RSS_URL);
        typeName = bundle.getString(TYPE_NAME);
        presenter = new NewsFeedPresenter(this);

        mContext = getContext();

        articleList = new ArrayList<>();

        adapter = new NewsFeedAdapter(mContext, articleList);

        Log.e(TAG, "onCreate: ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View mainView = inflater.inflate(R.layout.fragment_inside_news_feed, container, false);
        Log.e(TAG, "onCreateView: ");

        return mainView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initUI(view);
    }

    private void initUI(View view) {

        recyclerView = (SuperRecyclerView) view.findViewById(R.id.rvNewsFeed);


        manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);

        recyclerView.setLayoutManager(manager);
//        RecyclerViewMarginVertical decoration = new RecyclerViewMarginVertical((int) (getContext().getResources().getDisplayMetrics().density * 8f + 0.5f));
//        recyclerView.addItemDecoration(decoration);
        recyclerView.setRefreshing(true);

        recyclerView.setRefreshListener(this);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
//        recyclerView.setLoadingMore(false);

//        recyclerView.setupMoreListener(this, 10);

        setDataToView();
        initControll();
//        presenter.loadNewsFeed(rss_url);
    }

    private void initControll() {

        recyclerView.setOnScrollListener(new RecyclerOnScrollListener(manager) {
            @Override
            public void onHide() {
//                mContext.sendBroadcast(new Intent(Constant.ACTION_HIDE_BOTTOM_BAR));
            }

            @Override
            public void onShow() {
//                mContext.sendBroadcast(new Intent(Constant.ACTION_SHOW_BOTTOM_BAR));
            }

            @Override
            public void onLoadMore() {
                recyclerView.setLoadingMore(false);
            }
        });
    }

    private void setDataToView() {
        Log.e(TAG, "setDataToView: " + adapter.getItemCount());
        if (adapter.getItemCount() == 0) {
            onRefresh();
        }
    }


    @Override
    public void onRefresh() {
        presenter.loadNewsFeed(rss_url);
        recyclerView.setRefreshing(true);
    }

    @Override
    public void loadNewsFeed(ArrayList<Article> data) {
        adapter.clear();
        adapter.addAll(data);
        adapter.notifyDataSetChanged();
        recyclerView.setRefreshing(false);
    }

    @Override
    public String getTitleString() {
        return typeName;
    }
}
