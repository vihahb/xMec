package com.xtelsolution.xmec.views.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.presenter.DiseaseByHTMLDetailPresenter;
import com.xtelsolution.xmec.views.adapter.ViewPagerAdapter;
import com.xtelsolution.xmec.views.fragment.OverviewDiseaseFragment;
import com.xtelsolution.xmec.views.inf.HtmlDetailView;

public class DetailDiseaseActivity extends BasicActivity implements HtmlDetailView {
    private final String TAG = "DetailDiseaseActivity";
    Handler handler;
    private Toolbar mToolbar;
    private TextView titleToolbar;
    //    private ViewPager mViewPager;
//    private TabLayout mTabLayout;
    private String url_disease = "";
    private WebView webViewNews;
    private LinearLayout loLoading;
    private DiseaseByHTMLDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_disease);
        url_disease = getIntent().getStringExtra(Constant.ILLNESS_URL);
        init();
        setSupportActionBar(mToolbar);
        titleToolbar.setText(getIntent().getStringExtra(Constant.MEDICAL_NAME));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
//        setupViewPager(mViewPager);
//        mTabLayout.setupWithViewPager(mViewPager);
        handler = new Handler();

        presenter = new DiseaseByHTMLDetailPresenter(this);


        Log.e(TAG, "onCreate: " + url_disease);
        presenter.loadNews(url_disease);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter tabAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(getResources().getString(R.string.overview_text), OverviewDiseaseFragment.newInstance(url_disease));
//        tabAdapter.addFragment(getResources().getString(R.string.detail_text), new DetailDiseaseFragment());
        viewPager.setAdapter(tabAdapter);

    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        titleToolbar = (TextView) findViewById(R.id.toolbar_title);
//        mViewPager = (ViewPager) findViewById(R.id.viewpager);
//        mTabLayout = (TabLayout) findViewById(R.id.tab);
        loLoading = (LinearLayout) findViewById(R.id.loLoading);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
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
//                    webViewNews.setVisibility(View.VISIBLE);
                }
            }, 200);
        }
    }
}
