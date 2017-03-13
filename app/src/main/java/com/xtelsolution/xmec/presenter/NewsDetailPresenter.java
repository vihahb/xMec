package com.xtelsolution.xmec.presenter;

import com.xtelsolution.xmec.callbacks.NewsHtmlLoader;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.listener.LoadNewsDetailListener;
import com.xtelsolution.xmec.xmec.views.inf.INewsDetailView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by HUNGNT on 3/6/2017.
 */

public class NewsDetailPresenter {
    private INewsDetailView view;

    public NewsDetailPresenter(INewsDetailView view) {
        this.view = view;
    }

    public void loadNews(String url) {
        new NewsHtmlLoader(new LoadNewsDetailListener() {
            @Override
            public void onPrepare() {
                view.showProgressDialog("Đang tải....");
            }

            @Override
            public void onSucess(Document result) {
                view.loadWebView(getNewsBoxFromPage(result));
                view.dismissProgressDialog();
                xLog.d(getNewsBoxFromPage(result));
            }

            @Override
            public void onError() {
                view.showToast("Có lỗi xảy ra!");
            }
        }).execute(url);
    }

    private String getNewsBoxFromPage(Document document) {
        Element titleNewsElement = document.select("p.wtc-p-user").get(1);
        Element newsElement = document.select("div.wtc-div-title").first();
        try {
            InputStream inputStream = view.getActivity().getAssets().open("news-detail.html");
            Document mainHtml = Jsoup.parse(inputStream,"UTF-8","news-detail.html");
            mainHtml.select("div#wrapper").first().append(titleNewsElement.outerHtml()+newsElement.outerHtml()).outerHtml();
            return mainHtml.outerHtml();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
