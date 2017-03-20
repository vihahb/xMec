package com.xtelsolution.xmec.presenter;

import com.xtelsolution.xmec.callbacks.RSSGetter;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.listener.OnNewsFeedLoadedListener;
import com.xtelsolution.xmec.model.entity.NewsFeed;
import com.xtelsolution.xmec.xmec.views.inf.ISearchNewsView;

import java.util.ArrayList;

/**
 * Created by HUNGNT on 3/21/2017.
 */

public class SearchNewsPresenter {
    private ISearchNewsView view;
    private ArrayList<NewsFeed> data;

    public SearchNewsPresenter(ISearchNewsView view) {
        this.view = view;
        data = new ArrayList<>();
        loadListNews();
    }


    public ArrayList<NewsFeed> searchNews(String querry) {
        ArrayList<NewsFeed> listResult = new ArrayList<>();
        if (data != null && data.size() > 0) {
            xLog.e("sssss"+data.size());
            for (NewsFeed newsFeed : data) {
                xLog.e("ssss"+newsFeed.getTitle());
                if (newsFeed.getTitle() != null && newsFeed.getTitle().contains(querry)) {
                    listResult.add(newsFeed);
                }
            }
        }
        view.updateResult(listResult);
        return listResult;
    }

    private void loadListNews() {
        new RSSGetter(new OnNewsFeedLoadedListener() {
            @Override
            public void onPrepare() {

            }

            @Override
            public void onSucess(ArrayList<NewsFeed> list) {
                data.clear();
                data.addAll(list);
            }

            @Override
            public void onError() {
                view.showToast("Có lỗi xảy ra khi tải RSS");
            }
        }).execute("http://songkhoe.vn/widget.rss",
                "http://songkhoe.vn/tam-su.rss",
                "http://songkhoe.vn/gioi-tinh.rss",
                "http://songkhoe.vn/dinh-duong.rss",
                "http://songkhoe.vn/thoi-su.rss",
                "http://songkhoe.vn/lam-dep.rss",
                "http://songkhoe.vn/lam-me.rss",
                "http://songkhoe.vn/vui-khoe.rss",
                "http://songkhoe.vn/can-biet.rss");
    }
}
