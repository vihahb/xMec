package com.xtelsolution.xmec.views.activity;

import android.os.Bundle;
import android.os.Handler;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.presenter.NewsDetailPresenter;
import com.xtelsolution.xmec.views.inf.HtmlDetailView;

public class NewsDetailActivity extends BasicActivity implements HtmlDetailView {

    public static final String TAG_NEWS_URL = "news_url";
    private WebView webViewNews;
    private LinearLayout loLoading;
    private Handler handler;
    private NewsDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        handler = new Handler();
        String url = getIntent().getExtras().getString(TAG_NEWS_URL);
        init();
        initWebView();
        //presenter.loadNews(url);
        presenter.loadNewsHTML(url);
    }

    private void init() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Tin tức");
        presenter = new NewsDetailPresenter(this);
        loLoading = (LinearLayout) findViewById(R.id.loLoading);
    }

    private void initWebView() {
        webViewNews = (WebView) findViewById(R.id.webviewNews);
        webViewNews.setWebChromeClient(new WebChromeClient());
        webViewNews.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                showToast("Bài viết không tồn tại.");
                return true;
            }
        });
    }


    @Override
    public void loadWebView(String html) {
        webViewNews.loadData(html, "text/html; charset=UTF-8", null);
    }

    @Override
    public void showProgressView(boolean isLoading) {
        if (isLoading) {

            loLoading.setVisibility(View.VISIBLE);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loLoading.setVisibility(View.GONE);
                }
            }, 500);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return true;
    }
}
