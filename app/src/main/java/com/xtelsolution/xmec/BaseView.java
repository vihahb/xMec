package com.xtelsolution.xmec;

import android.app.Activity;

/**
 * Created by HUNGNT on 1/17/2017.
 */

public interface BaseView {
    Activity getActivity();
    void showToast(String msg);
}
