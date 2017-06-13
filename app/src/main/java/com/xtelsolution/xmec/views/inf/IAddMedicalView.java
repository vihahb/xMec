package com.xtelsolution.xmec.views.inf;

import com.xtelsolution.xmec.model.RESP_Medical;

/**
 * Created by phimau on 2/21/2017.
 */

public interface IAddMedicalView extends BaseView {
    void onUploadImageSussces(String url);
    void onAddMedicalSuccess(RESP_Medical medical);


}
