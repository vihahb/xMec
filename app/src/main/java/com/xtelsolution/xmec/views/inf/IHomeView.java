package com.xtelsolution.xmec.views.inf;

import com.xtelsolution.xmec.model.RESP_List_Medical;
import com.xtelsolution.xmec.model.RESP_User;

import java.util.ArrayList;

/**
 * Created by phimau on 2/15/2017.
 */

public interface IHomeView extends BaseView {
    void onLoadindView();

    void onGetUerSusscess(RESP_User user);

    void onGetMediacalListSusscess(boolean useCache, RESP_List_Medical list_medical);

    void onGetFriendActiveSuccess(ArrayList<RESP_User> arrayList);

    void getMedicalFromUIdSuccess(ArrayList arrayList);

    void getMedicalFromUIdError(String mes);

    void requireLogin();

}
