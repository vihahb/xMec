package com.xtelsolution.xmec.views.inf;

import android.graphics.Bitmap;

import com.xtelsolution.xmec.model.RESP_Medical;

/**
 * Created by phimau on 2/21/2017.
 */

public interface IAddMedicalView extends BaseView {
    void onUploadImageSussces(String url, Bitmap bitmap);

    void onUploadImageError();

    void onAddMedicalSuccess(RESP_Medical medical);


}
