package com.xtelsolution.xmec.xmec.views.inf;

import com.xtelsolution.xmec.model.RESP_List_Medical;
import com.xtelsolution.xmec.model.RESP_User;

/**
 * Created by phimau on 2/15/2017.
 */

public interface IHomeView  extends BaseView {
    void onGetUerSusscess(RESP_User user);
    void onGetMediacalListSusscess(RESP_List_Medical list_medical);
}
