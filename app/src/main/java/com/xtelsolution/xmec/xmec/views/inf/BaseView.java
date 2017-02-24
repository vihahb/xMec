package com.xtelsolution.xmec.xmec.views.inf;

import android.app.Activity;

/**
 * Created by HUNGNT on 1/17/2017.
 */

public interface BaseView {
    Activity getActivity();
    void showToast(String msg);
    void showLog(String msg);
    void showProgressDialog(String title);
    void dismissProgressDialog();
}
