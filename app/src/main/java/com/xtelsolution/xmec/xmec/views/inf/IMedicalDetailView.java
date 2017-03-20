package com.xtelsolution.xmec.xmec.views.inf;

import com.xtelsolution.xmec.model.RESP_Disease;
import com.xtelsolution.xmec.model.RESP_List_Disease;
import com.xtelsolution.xmec.model.RESP_Medical_Detail;

import java.util.List;

/**
 * Created by phimau on 3/6/2017.
 */

public interface IMedicalDetailView extends BaseView {
    void onLoadMedicalFinish(RESP_Medical_Detail obj);
    void onLoadListIllnessFinish(List<RESP_Disease> data);
    void onUpdateMedicalFinish();
    void onRemoveMedicalSuccess();

}
