package com.xtelsolution.xmec.xmec.views.inf;

import com.xtelsolution.xmec.model.RESP_List_Disease;
import com.xtelsolution.xmec.model.RESP_Medical_Detail;

/**
 * Created by phimau on 3/6/2017.
 */

public interface IMedicalDetailView extends BaseView {
    void onLoadMedicalFinish(RESP_Medical_Detail obj);
    void onLoadListIllnessFinish(RESP_List_Disease data);
    void onUpdateMedicalFinish();
    void onRemoveMedicalSuccess();

}
