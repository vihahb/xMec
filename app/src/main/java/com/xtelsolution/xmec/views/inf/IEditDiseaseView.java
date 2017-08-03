package com.xtelsolution.xmec.views.inf;

import com.xtelsolution.xmec.model.RESP_User_Medicine;

/**
 * Created by phimau on 4/4/2017.
 */

public interface IEditDiseaseView extends BaseView {
    void onEditDiseaseSuccess();

    void onRemoveMedicineSuccess(int index);

    void onAddMedicineSuccess(RESP_User_Medicine id);

    void onRemoveDiseaseSuccess();
}
