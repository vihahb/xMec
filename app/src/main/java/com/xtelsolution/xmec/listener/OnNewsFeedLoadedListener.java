package com.xtelsolution.xmec.listener;

import com.xtelsolution.xmec.model.entity.NewsFeed;

import java.util.ArrayList;

/**
 * Created by HUNGNT on 3/2/2017.
 */

public interface OnNewsFeedLoadedListener {
    void onPrepare();

    void onSucess(ArrayList<NewsFeed> list);

    void onError();
}
