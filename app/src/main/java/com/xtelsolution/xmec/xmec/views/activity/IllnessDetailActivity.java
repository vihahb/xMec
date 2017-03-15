package com.xtelsolution.xmec.xmec.views.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.presenter.IllnessDetailPresenter;
import com.xtelsolution.xmec.xmec.views.inf.IIllnessDetailview;

/**
 * Created by HUNGNT on 3/14/2017.
 */

public class IllnessDetailActivity extends BasicActivity implements IIllnessDetailview{

    public static final String ILLNESS_URL = "illness_url";
    private WebView webViewNews;
    private LinearLayout loLoading;
    IllnessDetailPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        init();
        initWebView();
//        String mainUrl = getIntent().getExtras().getString(ILLNESS_URL);
        String mainUrl = "http://diendan.songkhoe.vn/chi-tiet-trieu-chung-cua-benh-bai-liet-s2531-1136-451328.html";
        if (mainUrl!=null){
            presenter.loadIllnessDetail(mainUrl);
        }else {
            showToast("Có lỗi xảy ra");
            finish();
        }
    }

    private void init() {
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Chi tiết bệnh");
        presenter = new IllnessDetailPresenter(this);
        loLoading = (LinearLayout) findViewById(R.id.loLoading);
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

    @Override
    public void showProgressView(boolean isLoading) {
        if (isLoading){
            loLoading.setVisibility(View.VISIBLE);
        }else {
            loLoading.setVisibility(View.GONE);
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home)
            finish();
        return true;
    }
}
