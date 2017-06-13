package com.xtelsolution.xmec.presenter;

import android.util.Log;

import com.xtelsolution.xmec.callbacks.HtmlLoader;
import com.xtelsolution.xmec.callbacks.RSSGetter;
import com.xtelsolution.xmec.listener.LoadHtmlDetailListener;
import com.xtelsolution.xmec.model.entity.Article;
import com.xtelsolution.xmec.model.entity.NewsAuthor;
import com.xtelsolution.xmec.model.entity.NewsFeed;
import com.xtelsolution.xmec.views.inf.INewsFeedView;
import com.xtelsolution.xmec.listener.OnNewsFeedLoadedListener;

import org.jsoup.nodes.Document;

import java.util.ArrayList;

/**
 * Created by HUNGNT on 3/2/2017.
 */

public class NewsFeedPresenter {
    private INewsFeedView view;

    public NewsFeedPresenter(INewsFeedView view) {
        this.view = view;
    }

    public void loadNewsFeed(String url) {
        new RSSGetter(new OnNewsFeedLoadedListener() {
            @Override
            public void onPrepare() {

            }

            @Override
            public void onSucess(ArrayList<NewsFeed> list) {
//                view.loadNewsFeed(list);
//                TOI NAY BAT DAU TU DAY
                NewsAuthor author = new NewsAuthor("songkhoe.vn", "file:///android_asset/yte_suckhoe.png", "http://songkhoe.vn");
                ArrayList<Article> articleArrayList = new ArrayList<Article>();
                if (list != null) {
                    for (int i = 0; i < list.size(); i++) {
                        int type;
                        if (list.get(i).getLink().contains("http://songkhoe.vn/video"))
                            type = Article.TYPE_VIDEO;
                        else type = Article.TYPE_OTHER;

                        Article article = new Article(i, author, list.get(i), type, view.getTitleString(), "0", "0", "0");
                        articleArrayList.add(article);
                    }
                }
                view.loadNewsFeed(articleArrayList);
            }

            @Override
            public void onError() {

            }
        }).execute(url);
    }

    public void loadNewsFeed_html(String url) {
        new HtmlLoader(new LoadHtmlDetailListener() {
            @Override
            public void onPrepare() {
                Log.d("Res:", "onPrepare");
            }

            @Override
            public void onSucess(Document result) {
                NewsAuthor author = new NewsAuthor("songkhoe.vn", "file:///android_asset/yte_suckhoe.png", "http://songkhoe.vn");
                ArrayList<Article> articleArrayList = new ArrayList<Article>();
                String title, link, description, pubDate, total_view;
                int size = result.select(".assettile").size();
                if (size != 0){
                    int type;
                    for (int i = 0; i < size; i++){
                        title = result.select("div.asset_title h3").get(i).select("a").get(0).text();
                        link = result.select("div.asset_title a").get(i).attr("href");
                        if (link.contains("http://songkhoe.vn/video")) {type = Article.TYPE_VIDEO;}else{type = Article.TYPE_OTHER;}
                        description = result.select("div.assetdesc img").get(i).attr("src");
                        pubDate = result.select("div.asset_title").get(i).select(".publish_date").get(0).text();
                        total_view = parseStr(result.select("div.asset_title").get(i).select(".publish_date").get(1).text());
                        Article article = new Article(i, author, new NewsFeed(title, link, description, pubDate), type, view.getTitleString(), "0", total_view, "0");
                        articleArrayList.add(article);
                    }
                }
                view.loadNewsFeed(articleArrayList);
            }

            @Override
            public void onError() {
                Log.d("Res:", "onError");
            }
        }).execute(url);
    }

    private String parseStr(String str){
        String arr[] = str.split(" ");
        return arr[3];
    }
}
