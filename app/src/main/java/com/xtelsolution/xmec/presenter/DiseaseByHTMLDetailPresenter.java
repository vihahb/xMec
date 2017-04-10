package com.xtelsolution.xmec.presenter;

import com.xtelsolution.xmec.callbacks.HtmlLoader;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.listener.LoadHtmlDetailListener;
import com.xtelsolution.xmec.xmec.views.inf.HtmlDetailView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Admin on 4/10/2017.
 */

public class DiseaseByHTMLDetailPresenter {
    private static final String TAG = "DiseaseByHTMLDetailPresenter";
    private HtmlDetailView view;

    public DiseaseByHTMLDetailPresenter(HtmlDetailView view) {
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
                String load = getDiseaseBoxFromPage(result);
                view.loadWebView(load);
                view.showProgressView(false);
                xLog.d(TAG, "loadNews: onSucess: " + load);
            }

            @Override
            public void onError() {
                view.showToast("Có lỗi xảy ra!");
                view.showProgressView(false);
            }
        }).execute(url);
    }

    private String getDiseaseBoxFromPage(Document document) {

        try {

            Elements select = document.select("#disease-detail div.position div.content .body");

            InputStream inputStream = view.getActivity().getAssets().open("news-detail.html");
            Document mainHtml = Jsoup.parse(inputStream, "UTF-8", "news-detail.html");
            if (select.size() > 0) {
                Element element = select.get(1);
                mainHtml.select("div#wrapper").first().append(element.outerHtml()).outerHtml();
                return mainHtml.outerHtml();
            } else {
                return mainHtml.select("div#wrapper").first().append("<h3>Xin lỗi, chúng tôi không tìm thấy trang bạn yêu cầu !</h3>").outerHtml();
            }

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }
}
