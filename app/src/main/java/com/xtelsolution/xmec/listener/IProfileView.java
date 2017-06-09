package com.xtelsolution.xmec.listener;

import com.xtelsolution.xmec.views.inf.BaseView;

/**
 * Created by phimau on 2/18/2017.
 */

public interface IProfileView extends BaseView {
    void onLoadProfileSuccess(String name, long birthday, double height, double weight, String url, int sex);

    void onUpdateProfileSuccess();

    void onUploadImageSussces(String url);

    void onError();
}
