package com.xtelsolution.xmec.xmec.views.inf;

import com.xtelsolution.xmec.model.RESP_List_IIlness;
import com.xtelsolution.xmec.model.REQ_Medical_Detail;
import com.xtelsolution.xmec.model.RESP_Medical_Detail;

/**
 * Created by phimau on 3/6/2017.
 */

public interface IDetailMedicalView extends BaseView {
    void onLoadMedicalFinish(RESP_Medical_Detail obj);
    void onLoadListIllnessFinish(RESP_List_IIlness data);
    void onUpdateMedicalFinish();

}
