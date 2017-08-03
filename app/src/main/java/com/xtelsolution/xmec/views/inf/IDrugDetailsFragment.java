package com.xtelsolution.xmec.views.inf;

import com.xtelsolution.xmec.model.entity.DrugEntity;

import java.util.ArrayList;

/**
 * Created by vivhp on 8/2/2017.
 */

public interface IDrugDetailsFragment extends BaseView {

    void getDrugListSuccess(ArrayList<DrugEntity> arrayList);
    void getDrugListError(String mes);
    void requireLogin();
    void showDialogAdd();
    void addDrugSucces();
    void addDrugError();

}
