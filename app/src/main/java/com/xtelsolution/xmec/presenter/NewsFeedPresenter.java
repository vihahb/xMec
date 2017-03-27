package com.xtelsolution.xmec.presenter;

import com.xtelsolution.xmec.callbacks.RSSGetter;
import com.xtelsolution.xmec.model.entity.Article;
import com.xtelsolution.xmec.model.entity.NewsAuthor;
import com.xtelsolution.xmec.model.entity.NewsFeed;
import com.xtelsolution.xmec.xmec.views.inf.INewsFeedView;
import com.xtelsolution.xmec.listener.OnNewsFeedLoadedListener;

import java.util.ArrayList;

/**
 * Created by HUNGNT on 3/2/2017.
 */

public class NewsFeedPresenter {
    private INewsFeedView view;

    public NewsFeedPresenter(INewsFeedView view) {
        this.view = view;
    }

    public void loadNewsFeed(String url){
        new RSSGetter(new OnNewsFeedLoadedListener() {
            @Override
            public void onPrepare() {

            }

            @Override
            public void onSucess(ArrayList<NewsFeed> list) {
//                view.loadNewsFeed(list);
//                TOI NAY BAT DAU TU DAY
                NewsAuthor author = new NewsAuthor("songkhoe.vn","file:///android_asset/yte_suckhoe.png","http://songkhoe.vn");
                ArrayList<Article> articleArrayList = new ArrayList<Article>();
                if (list!=null){
                    for (int i = 0; i < list.size(); i++) {
                        Article article = new Article(i,author,list.get(i),i,view.getTitleString(),0,0,0);
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
}
