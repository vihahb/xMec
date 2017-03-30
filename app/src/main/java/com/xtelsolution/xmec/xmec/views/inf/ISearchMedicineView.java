package com.xtelsolution.xmec.xmec.views.inf;

import com.xtelsolution.xmec.model.Medicine;
import com.xtelsolution.xmec.model.RESP_Medicine;

import java.util.List;

/**
 * Created by phimau on 3/21/2017.
 */

public interface ISearchMedicineView extends BaseView {
    void onFindMedicienFinish(List<Medicine> data);

    void onError();
}
