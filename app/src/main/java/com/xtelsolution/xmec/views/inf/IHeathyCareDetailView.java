package com.xtelsolution.xmec.views.inf;

import com.xtelsolution.xmec.model.RESP_Healthy_Care_Detail;

/**
 * Created by phimau on 3/16/2017.
 */

public interface IHeathyCareDetailView extends BaseView {
    void onGetHeathyCareSuccess(RESP_Healthy_Care_Detail healthyCareD);

    void onGetHealCareSuccess(String opentime, String description, String phone, String url);
}
