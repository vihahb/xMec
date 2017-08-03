package com.xtelsolution.xmec.presenter;

import android.util.Log;

import com.xtelsolution.xmec.callbacks.HtmlLoader;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.listener.LoadHtmlDetailListener;
import com.xtelsolution.xmec.model.entity.Article;
import com.xtelsolution.xmec.views.inf.HtmlDetailView;

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
    int type;
    private HtmlDetailView view;

    public NewsDetailPresenter(HtmlDetailView view) {
        this.view = view;
    }

    public void loadNews(String url) {
        if (url.contains("http://songkhoe.vn/video")) type = 1;
        else type = 2;
        new HtmlLoader(new LoadHtmlDetailListener() {
            @Override
            public void onPrepare() {
                Log.d("Res:", "onPrepare");
                view.showProgressView(true);
            }

            @Override
            public void onSucess(Document result) {
                Log.d("Res onsucess:", result.outerHtml());

                if (type == Article.TYPE_VIDEO)
                    view.loadWebView(getVideoBoxFromPage(result));
                else
                    view.loadWebView(getNewsBoxFromPage(result));
                view.showProgressView(false);
                xLog.d(TAG, "loadNews: onSucess: " + getVideoBoxFromPage(result));
            }

            @Override
            public void onError() {
                Log.d("Res:", "onError");
                view.showToast("Có lỗi xảy ra!");
                view.showProgressView(false);
            }
        }).execute("http://suckhoe.vn/dinh-duong/1");
    }

    public void loadNewsHTML(String url) {
        if (url.contains("http://songkhoe.vn/video")) type = 1;
        else type = 2;
        new HtmlLoader(new LoadHtmlDetailListener() {
            @Override
            public void onPrepare() {
                Log.d("Res:", "onPrepare");
                view.showProgressView(true);
            }

            @Override
            public void onSucess(Document result) {
                if (type == Article.TYPE_VIDEO)
                    view.loadWebView(getVideoBoxFromPageHTML(result));
                else
                    view.loadWebView(getNewsBoxFromPageHTML(result));
                view.showProgressView(false);
            }

            @Override
            public void onError() {
                Log.d("Res:", "onError");
                view.showToast("Có lỗi xảy ra!");
                view.showProgressView(false);
            }
        }).execute(url);
    }


    private String getNewsBoxFromPage(Document document) {
        try {
            InputStream inputStream = view.getActivity().getAssets().open("news-detail.html");
            Document mainHtml = Jsoup.parse(inputStream, "UTF-8", "news-detail.html");
            if (document.select("p.wtc-p-user").size() > 1) {
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

    private String getVideoBoxFromPage(Document document) {
        try {
            InputStream inputStream = view.getActivity().getAssets().open("news-detail.html");
            Document mainHtml = Jsoup.parse(inputStream, "UTF-8", "news-detail.html");
            if (document.select("div.flash-playing").size() > 0) {
                Element videoBox = document.select("div.flash-playing").first();
                mainHtml.select("div#wrapper").first().append(videoBox.outerHtml()).outerHtml();
                mainHtml.select("div#wrapper").append("<h3>" + document.select(".player-title").text() + "</h3>");
                mainHtml.select("div#wrapper").append("<p>" + document.select(".tag-div-wrap ").text() + "</p>");
                mainHtml.select("div#wrapper").append(document.select(".source-other ").outerHtml());
                return mainHtml.outerHtml();
            } else {
                return mainHtml.select("div#wrapper").first().append("<h3>Xin lỗi! Chúng tôi không tìm thấy trang bạn yêu cầu !</h3>").outerHtml();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "<h3>Xin lỗi, chúng tôi không tìm thấy trang bạn yêu cầu !</h3>";
        }
    }


    // Parse Html
    private String getNewsBoxFromPageHTML(Document document) {

        try {
            InputStream inputStream = view.getActivity().getAssets().open("news-detail.html");
            Document mainHtml = Jsoup.parse(inputStream, "UTF-8", "news-detail.html");

            if (document.select("div.journal-content-article").size() > 0) {
                Element titleNewsElement = document.select("div.title-content h1").get(0);
                Element shortDes = document.select("div.journal-content-article").get(0);
                Log.e(TAG, "getNewsBoxFromPageHTML: " + shortDes.toString());
                Element titleElement = null;
                try {
                    titleElement = document.select("div.journal-content-article h2").get(0);
                } catch (Exception e) {

                }

                String html = (titleNewsElement != null) ? titleNewsElement.outerHtml() : "" + shortDes.ownText() + titleElement.outerHtml();

                int size = document.select("div.journal-content-article p").size();
                for (int i = 0; i < size; i++) {
                    if (document.select("div.journal-content-article p").get(i).select("img").size() > 0) {
                        String urlImg = document.select("div.journal-content-article p").get(i).select("img").attr("src");
                        html += "<img style=\"display: block; margin-left: auto; margin-right: auto; width: 100%;\" src=\"" + urlImg + "\">";
                        String em = document.select("div.journal-content-article p").get(i).select("em").text();
                        html += "<p style=\"text-align: center \">" + em + "</p>";
                    } else {
                        html += document.select("div.journal-content-article p").get(i).outerHtml();
                    }
                }

                return mainHtml.select("div#wrapper").first().append(html).outerHtml();
            } else {
                return mainHtml.select("div#wrapper").first().append("<h3>Xin lỗi, chúng tôi không tìm thấy trang bạn yêu cầu !</h3>").outerHtml();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "<h3>Xin lỗi, chúng tôi không tìm thấy trang bạn yêu cầu !</h3>";
        }
    }

    private String getVideoBoxFromPageHTML(Document document) {
        try {
            InputStream inputStream = view.getActivity().getAssets().open("news-detail.html");
            Document mainHtml = Jsoup.parse(inputStream, "UTF-8", "news-detail.html");
            if (document.select("div.flash-playing").size() > 0) {
                Element videoBox = document.select("div.flash-playing").first();
                mainHtml.select("div#wrapper").first().append(videoBox.outerHtml()).outerHtml();
                mainHtml.select("div#wrapper").append("<h3>" + document.select(".player-title").text() + "</h3>");
                mainHtml.select("div#wrapper").append("<p>" + document.select(".tag-div-wrap ").text() + "</p>");
                mainHtml.select("div#wrapper").append(document.select(".source-other ").outerHtml());
                return mainHtml.outerHtml();
            } else {
                return mainHtml.select("div#wrapper").first().append("<h3>Xin lỗi! Chúng tôi không tìm thấy trang bạn yêu cầu !</h3>").outerHtml();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "<h3>Xin lỗi, chúng tôi không tìm thấy trang bạn yêu cầu !</h3>";
        }
    }

}
