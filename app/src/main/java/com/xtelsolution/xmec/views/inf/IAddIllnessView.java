package com.xtelsolution.xmec.views.inf;

import com.xtelsolution.xmec.model.RESP_Disease_Detail;

/**
 * Created by phimau on 3/7/2017.
 */

public interface IAddIllnessView extends BaseView {
    void onAddDiseaseSuccess(int id);

    void onAddMedicineSuccess(int id);

    void onLoadDiseaseSuccess(RESP_Disease_Detail diseaseDetail);
}
