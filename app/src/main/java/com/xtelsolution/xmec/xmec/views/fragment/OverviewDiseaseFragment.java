package com.xtelsolution.xmec.xmec.views.fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.LinearLayout;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.presenter.DiseaseByHTMLDetailPresenter;
import com.xtelsolution.xmec.xmec.views.inf.HtmlDetailView;

public class OverviewDiseaseFragment extends BasicFragment implements HtmlDetailView {

    private final String TAG = "OverviewDiseaseFragment";
    private Context mContext;
    //    private TextView tv_define;
//    private TextView tv_symptom;
//    private TextView tv_diagnose;
//    private TextView tv_treatment;
    Handler handler;
    private String url = "";
    private WebView webViewNews;
    private LinearLayout loLoading;
    private DiseaseByHTMLDetailPresenter presenter;
    View view;

    public static OverviewDiseaseFragment newInstance(String url) {

        Bundle args = new Bundle();
        args.putString(Constant.DISEASE_URL, url);
        OverviewDiseaseFragment fragment = new OverviewDiseaseFragment();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
        handler = new Handler();

        presenter = new DiseaseByHTMLDetailPresenter(this);

        Bundle bundle = this.getArguments();
        url = bundle.getString(Constant.DISEASE_URL);
        Log.e(TAG, "onCreate: " + url);
        presenter.loadNews(url);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.fragment_overview_disease, container, false);
        this.view = view;
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }

    private void initUI(View view) {
//        tv_define = (TextView) view.findViewById(R.id.tv_define);
//        tv_symptom = (TextView) view.findViewById(R.id.tv_symptom);
//        tv_diagnose = (TextView) view.findViewById(R.id.tv_diagnose);
//        tv_treatment = (TextView) view.findViewById(R.id.tv_treatment);
        loLoading = (LinearLayout) view.findViewById(R.id.loLoading);
        webViewNews = (WebView) view.findViewById(R.id.webviewNews);
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
        if (view == null) return;
        if (loLoading == null) {
            loLoading = (LinearLayout) view.findViewById(R.id.loLoading);
        }
        if (isLoading) {
            loLoading.setVisibility(View.VISIBLE);
        } else {
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    loLoading.setVisibility(View.GONE);
                    webViewNews.setVisibility(View.VISIBLE);
                }
            }, 500);
        }
    }

}
