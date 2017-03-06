package com.xtelsolution.xmec.callbacks;

import android.os.AsyncTask;

import com.xtelsolution.xmec.listener.LoadNewsListener;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

/**
 * Created by HUNGNT on 3/6/2017.
 */

public class NewsHtmlLoader extends AsyncTask<String, Integer, Document> {

    private LoadNewsListener handle;

    public NewsHtmlLoader(LoadNewsListener handle) {
        this.handle = handle;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        handle.onPrepare();
    }

    @Override
    protected Document doInBackground(String... params) {
        Connection connection = Jsoup.connect(params[0]);
        try {
            Document document = connection.get();
            return document;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Document result) {
        if (result==null)
            handle.onError();
        else
            handle.onSucess(result);
    }
}
