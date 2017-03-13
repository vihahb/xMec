package com.xtelsolution.xmec.xmec.views.activity;

import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.presenter.NewsDetailPresenter;
import com.xtelsolution.xmec.xmec.views.inf.INewsDetailView;

public class NewsDetailActivity extends BasicActivity implements INewsDetailView{

    public static final String TAG_NEWS_URL = "news_url";
    private WebView webViewNews;
    private NewsDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        String url = getIntent().getExtras().getString(TAG_NEWS_URL);
        init();
        initWebView();
        presenter.loadNews(url);
    }

    private void init() {
        presenter = new NewsDetailPresenter(this);
    }

    private void initWebView() {
        webViewNews = (WebView) findViewById(R.id.webviewNews);
        webViewNews.setWebChromeClient(new WebChromeClient());
        webViewNews.setWebViewClient(new WebViewClient(){
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                showToast("Các link trong bài viết đã bị vô hiệu hóa");
                return true;
            }
        });
    }


    @Override
    public void loadWebView(String html) {
        webViewNews.loadData(html,"text/html; charset=UTF-8", null);
    }
}
