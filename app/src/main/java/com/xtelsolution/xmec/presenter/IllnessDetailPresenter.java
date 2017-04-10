package com.xtelsolution.xmec.presenter;

import com.xtelsolution.xmec.callbacks.HtmlLoader;
import com.xtelsolution.xmec.listener.LoadHtmlDetailListener;
import com.xtelsolution.xmec.xmec.views.inf.IIllnessDetailview;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by HUNGNT on 3/14/2017.
 */

public class IllnessDetailPresenter extends BasePresenter{
    IIllnessDetailview view;


    public IllnessDetailPresenter(IIllnessDetailview view) {
        this.view = view;
    }

    public void loadIllnessDetail(String url) {
        if (!checkConnnecttion(view))
            return;
        new HtmlLoader(new LoadHtmlDetailListener() {
            @Override
            public void onPrepare() {
                view.showProgressView(true);
            }

            @Override
            public void onSucess(Document result) {
                getLinkDetail(result);
            }

            @Override
            public void onError() {
                view.showToast("Có lỗi xảy ra!");
                view.showProgressView(false);
            }
        }).execute(url);
    }

    private String parseDocument(Document document) {
        String name = document.select("a.wtc-link-shapo").first().outerHtml();
        String detail = document.select("div.wtc-div-title").first().outerHtml();
        try {
            InputStream inputStream = view.getActivity().getAssets().open("news-detail.html");
            Document mainHtml = Jsoup.parse(inputStream, "UTF-8", "news-detail.html");
            mainHtml.select("div#wrapper").first().append(name + detail).outerHtml();
            return mainHtml.outerHtml();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void getLinkDetail(Document document){
        String url = document.select("a.top-total-lnk").first().attr("abs:href");
        new HtmlLoader(new LoadHtmlDetailListener() {
            @Override
            public void onPrepare() {

            }

            @Override
            public void onSucess(Document result) {
                view.loadWebView(parseDocument(result));
                view.showProgressView(false);
            }

            @Override
            public void onError() {
                view.showToast("Có lỗi xảy ra!");
                view.showProgressView(false);
            }
        }).execute(url);
    }

    @Override
    public void onGetNewSessionSuccess(Object... param) {
//        switch ((int)param[0]){
//            case GETUSER:
//                getUser(param);
//                break;
//            case GETMEDICAL:
//                getMedicalReportBooks();
//                break;
//        }
    }
}

