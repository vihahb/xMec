package com.xtelsolution.xmec.callbacks;

import android.os.AsyncTask;
import android.util.Log;

import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.entity.NewsFeed;
import com.xtelsolution.xmec.model.entity.NewsFeeds;
import com.xtelsolution.xmec.listener.OnNewsFeedLoadedListener;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HUNGNT on 3/2/2017.
 */

public class RSSGetter extends AsyncTask<String, Integer, ArrayList<NewsFeed>> {
    private static final String TAG = "RSSGetter";
    private OnNewsFeedLoadedListener callback;

    public RSSGetter(OnNewsFeedLoadedListener callback) {
        this.callback = callback;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected ArrayList<NewsFeed> doInBackground(String... params) {
        ArrayList<NewsFeed> list = new ArrayList<>();
        for (String param : params) {
            ArrayList<NewsFeed> listSub = loadUrl(param);
            if (listSub != null)
                list.addAll(listSub);
        }
//        try {
//            OkHttpClient client = new OkHttpClient();
//            Request.Builder builder = new Request.Builder();
//            builder.url(params[0]);
//            Request request = builder.build();
//            Response response = client.newCall(request).execute();
//            ArrayList<NewsFeed> list = NewsFeeds.parse(response.body().byteStream());
//            return list;
//        } catch (IOException e) {
//            e.printStackTrace();
//            xLog.e("RSSGetter-IOException:" + e.getMessage());
//            return null;
//        }
        return list;
    }

    @Override
    protected void onPostExecute(ArrayList<NewsFeed> list) {
        callback.onSucess(list);
    }

    private ArrayList<NewsFeed> loadUrl(String url) {
        try {
            Log.e(TAG, "loadUrl: " + url);
            OkHttpClient client = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            builder.url(url);
            Request request = builder.build();
            Response response = client.newCall(request).execute();
            ArrayList<NewsFeed> list = NewsFeeds.parse(response.body().byteStream());
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            xLog.e(TAG, "RSSGetter-IOException:" + e.getMessage());
            return null;
        }
    }
}
