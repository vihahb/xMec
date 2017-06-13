package com.xtelsolution.xmec.presenter;

import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtelsolution.xmec.callbacks.RSSGetter;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.listener.OnNewsFeedLoadedListener;
import com.xtelsolution.xmec.model.DiseaseModel;
import com.xtelsolution.xmec.model.RESP_List_Disease;
import com.xtelsolution.xmec.model.entity.NewsFeed;
import com.xtelsolution.xmec.views.inf.ISearchNewsView;

import java.util.ArrayList;

/**
 * Created by HUNGNT on 3/21/2017.
 */

public class SearchNewsPresenter extends BasePresenter {
    private final String TAG = "SearchNewsPresenter";
    private ISearchNewsView view;
    private ArrayList<NewsFeed> data;
    private final int SEARDISEASE=1;

    public SearchNewsPresenter(ISearchNewsView view) {
        this.view = view;
        data = new ArrayList<>();
        loadListNews();
    }


    public ArrayList<NewsFeed> searchNews(String querry) {
        if (querry.length() < 4)
            return null;
        ArrayList<NewsFeed> listResult = new ArrayList<>();
        if (data != null && data.size() > 0) {
            xLog.e(TAG, "searchNews:" + data.size());
            for (NewsFeed newsFeed : data) {
                xLog.e(TAG, "searchNews" + newsFeed.getTitle());
                if (newsFeed.getTitle() != null && newsFeed.getTitle().contains(querry)) {
                    listResult.add(newsFeed);
                }
            }
        }
        view.updateResult(listResult);
        return listResult;
    }

    private void loadListNews() {
        new RSSGetter(new OnNewsFeedLoadedListener() {
            @Override
            public void onPrepare() {

            }

            @Override
            public void onSucess(ArrayList<NewsFeed> list) {
                data.clear();
                data.addAll(list);
            }

            @Override
            public void onError() {
                view.showToast("Có lỗi xảy ra khi tải RSS");
            }
        }).execute("http://songkhoe.vn/widget.rss",
                "http://songkhoe.vn/tam-su.rss",
                "http://songkhoe.vn/gioi-tinh.rss",
                "http://songkhoe.vn/dinh-duong.rss",
                "http://songkhoe.vn/thoi-su.rss",
                "http://songkhoe.vn/lam-dep.rss",
                "http://songkhoe.vn/lam-me.rss",
                "http://songkhoe.vn/vui-khoe.rss",
                "http://songkhoe.vn/can-biet.rss");
    }


    private void findDisease(final Object... param) {
        String key = (String) param[1];
        String url = Constant.SERVER_XMEC + Constant.Disease + "?name=" + key + "&size=15";
        xLog.e(TAG, "Sear Disease:" + url);
        xLog.e(TAG, "searchMedicine: secsion: " + LoginManager.getCurrentSession());
        DiseaseModel.getInstance().findDisease(url, getSession(), new ResponseHandle<RESP_List_Disease>(RESP_List_Disease.class) {
            @Override
            public void onSuccess(RESP_List_Disease obj) {
                    view.onFindDiseaseFinish(obj.getList());
            }

            @Override
            public void onError(Error error) {
                handlerError(view,error,param);
            }
        });
    }

    public void checkSearchDisease(String key) {
        if (!checkConnnecttion(view))
            return;
        findDisease(SEARDISEASE, key);
    }

    @Override
    public void onGetNewSessionSuccess(Object... param) {
        switch ((int)param[0]){
            case SEARDISEASE:
                findDisease(param);
                break;
        }
    }
}
