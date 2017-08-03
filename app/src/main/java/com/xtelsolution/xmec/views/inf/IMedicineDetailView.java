package com.xtelsolution.xmec.views.inf;

import com.xtelsolution.xmec.model.RESP_Medicine_Detail;

/**
 * Created by HUNGNT on 4/3/2017.
 */

public interface IMedicineDetailView extends BaseView {
    void onDetailLoadedSuccess(RESP_Medicine_Detail medicine);

    void onDetailLoadedError();
}
