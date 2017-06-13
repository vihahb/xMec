package com.xtelsolution.xmec.views.inf;

import com.xtelsolution.xmec.model.RESP_Disease_Detail;

/**
 * Created by phimau on 4/1/2017.
 */

public interface IDiseaseDetailView extends BaseView {
    void onLoadDiseaseDetailSuccess(RESP_Disease_Detail diseaseDetail);
}
