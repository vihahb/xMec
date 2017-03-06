package com.xtelsolution.xmec.presenter;

import com.xtelsolution.xmec.callbacks.NewsHtmlLoader;
import com.xtelsolution.xmec.listener.LoadNewsListener;
import com.xtelsolution.xmec.xmec.views.inf.INewsDetailView;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

/**
 * Created by HUNGNT on 3/6/2017.
 */

public class NewsDetailPresenter {
    private INewsDetailView view;

    public NewsDetailPresenter(INewsDetailView view) {
        this.view = view;
    }

    public void loadNews(String url) {
        new NewsHtmlLoader(new LoadNewsListener() {
            @Override
            public void onPrepare() {
                view.showProgressDialog("Đang tải....");
            }

            @Override
            public void onSucess(Document result) {

                view.loadWebView(getNewsBoxFromPage(result));
                view.dismissProgressDialog();
            }

            @Override
            public void onError() {
                view.showToast("Có lỗi xảy ra!");
            }
        }).execute(url);
    }

    private String getNewsBoxFromPage(Document document){
        Element element = document.select("div.wtc-div-title").first();
        return element.outerHtml();
    }
}
