package com.xtelsolution.xmec.callbacks;

import android.os.AsyncTask;

import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.entity.NewsFeed;
import com.xtelsolution.xmec.model.entity.NewsFeeds;
import com.xtelsolution.xmec.xmec.views.inf.OnNewsFeedLoadedListener;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by HUNGNT on 3/2/2017.
 */

public class RSSGetter extends AsyncTask<String, Integer, ArrayList<NewsFeed>> {

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
        xLog.d("URL: " + params[0]);
        try {
            OkHttpClient client = new OkHttpClient();
            Request.Builder builder = new Request.Builder();
            builder.url(params[0]);
            Request request = builder.build();
            Response response = client.newCall(request).execute();
            ArrayList<NewsFeed> list = NewsFeeds.parse(response.body().byteStream());
            return list;
        } catch (IOException e) {
            e.printStackTrace();
            xLog.e("RSSGetter-IOException:" + e.getMessage());
            return null;
        }
    }

    @Override
    protected void onPostExecute(ArrayList<NewsFeed> list) {
        callback.onSucess(list);
    }
}
