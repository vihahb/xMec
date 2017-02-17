package com.xtelsolution.xmec.xmec.views.inf;

import android.app.Activity;

import com.xtelsolution.xmec.model.RESP_User;
import com.xtelsolution.xmec.xmec.views.inf.BaseView;

/**
 * Created by phimau on 2/17/2017.
 */

public interface IUpdateInforView extends BaseView {
    void onUpdateInforSuccess(RESP_User user);
}
