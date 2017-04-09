package com.xtelsolution.xmec.xmec.views.activity;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.presenter.IllnessDetailPresenter;
import com.xtelsolution.xmec.xmec.views.inf.IIllnessDetailview;

/**
 * Created by HUNGNT on 3/14/2017.
 */

public class IllnessDetailActivity extends BasicActivity implements IIllnessDetailview {

    private static final String TAG = "IllnessDetailActivity";
    private WebView webViewNews;
    private LinearLayout loLoading;
    IllnessDetailPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        init();
        initWebView();
        String mainUrl = getIntent().getStringExtra(Constant.ILLNESS_URL);
        xLog.e(TAG, "onCreate: " + Constant.LOGPHI + mainUrl);
        if (mainUrl != null) {
            mainUrl.replace("http://diendan.songkhoe.vn/dien-dan", "http://diendan.songkhoe.vn/chi-tiet-tong-quan-ve");
            xLog.e(TAG, "onCreate: " + Constant.LOGPHI + "url " + mainUrl);
            presenter.loadIllnessDetail(mainUrl);
        } else {
            showToast("Có lỗi xảy ra");

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
        webViewNews.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                showToast("Các link trong bài viết đã bị vô hiệu hóa");
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
            loLoading.setVisibility(View.GONE);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home)
            finish();
        return true;
    }
}
