package com.xtelsolution.xmec.xmec.views.inf;

/**
 * Created by HUNGNT on 3/6/2017.
 */

public interface HtmlDetailView extends BaseView {
    void loadWebView(String html);
    void showProgressView(boolean isLoading);
}
