package com.xtelsolution.xmec.views.inf;

import android.content.Context;

/**
 * Created by vivhp on 7/24/2017.
 */

public interface IResetPasswordActivityView extends BaseView {

    void recoveryPasswordSuccess(String mes);

    Context getContext();

}
