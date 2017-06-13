package com.xtelsolution.xmec.views.inf;

import com.xtelsolution.xmec.model.entity.Disease;
import com.xtelsolution.xmec.model.entity.NewsFeed;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HUNGNT on 3/21/2017.
 */

public interface ISearchNewsView extends BaseView {
    void updateResult(ArrayList<NewsFeed> listNewsFeeds);
    void onFindDiseaseFinish(List<Disease> data);
}
