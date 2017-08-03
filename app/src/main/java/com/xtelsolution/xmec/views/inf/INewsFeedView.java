package com.xtelsolution.xmec.views.inf;

import com.xtelsolution.xmec.model.entity.Article;

import java.util.ArrayList;

/**
 * Created by HUNGNT on 3/2/2017.
 */

public interface INewsFeedView extends BaseView {
    void loadNewsFeed(ArrayList<Article> data);

    String getTitleString();
}
