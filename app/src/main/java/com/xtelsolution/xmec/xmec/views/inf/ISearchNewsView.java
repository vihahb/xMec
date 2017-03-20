package com.xtelsolution.xmec.xmec.views.inf;

import com.xtelsolution.xmec.model.entity.NewsFeed;

import java.util.ArrayList;

/**
 * Created by HUNGNT on 3/21/2017.
 */

public interface ISearchNewsView extends BaseView {
    void updateResult(ArrayList<NewsFeed> listNewsFeeds);
}
