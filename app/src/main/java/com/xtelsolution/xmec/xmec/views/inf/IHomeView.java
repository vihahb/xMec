package com.xtelsolution.xmec.xmec.views.inf;

import com.xtelsolution.xmec.model.RESP_LIST_MEDICAL;
import com.xtelsolution.xmec.model.RESP_User;

/**
 * Created by phimau on 2/15/2017.
 */

public interface IHomeView   {
    void showToast(String msg);
    void onGetUerSusscess(RESP_User user);
    void onGetMediacalListSusscess(RESP_LIST_MEDICAL user);
}
