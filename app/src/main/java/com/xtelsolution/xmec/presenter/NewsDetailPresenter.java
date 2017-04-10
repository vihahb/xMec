package com.xtelsolution.xmec.presenter;

import com.xtelsolution.xmec.callbacks.HtmlLoader;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.listener.LoadHtmlDetailListener;
import com.xtelsolution.xmec.xmec.views.inf.HtmlDetailView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by HUNGNT on 3/6/2017.
 */

public class NewsDetailPresenter {
    private static final String TAG = "NewsDetailPresenter";
    private HtmlDetailView view;

    public NewsDetailPresenter(HtmlDetailView view) {
        this.view = view;
    }

    public void loadNews(String url) {
        new HtmlLoader(new LoadHtmlDetailListener() {
            @Override
            public void onPrepare() {
                view.showProgressView(true);
            }

            @Override
            public void onSucess(Document result) {
                view.loadWebView(getNewsBoxFromPage(result));
                view.showProgressView(false);
                xLog.d(TAG, "loadNews: onSucess: " + getNewsBoxFromPage(result));
            }

            @Override
            public void onError() {
                view.showToast("Có lỗi xảy ra!");
                view.showProgressView(false);
            }
        }).execute(url);
    }

    private String getNewsBoxFromPage(Document document) {

        try {

            InputStream inputStream = view.getActivity().getAssets().open("news-detail.html");
            Document mainHtml = Jsoup.parse(inputStream, "UTF-8", "news-detail.html");
            if (document.select("p.wtc-p-user").size() > 0) {
                Element titleNewsElement = document.select("p.wtc-p-user").get(1);
                Element newsElement = document.select("div.wtc-div-title").first();

                mainHtml.select("div#wrapper").first().append(titleNewsElement.outerHtml() + newsElement.outerHtml()).outerHtml();
                return mainHtml.outerHtml();
            } else {
                return mainHtml.select("div#wrapper").first().append("<h3>Xin lỗi, chúng tôi không tìm thấy trang bạn yêu cầu !</h3>").outerHtml();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }
}
