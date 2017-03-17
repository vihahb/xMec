package com.xtelsolution.xmec.presenter;

import android.os.AsyncTask;

import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.LoginModel;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.model.DiseaseModel;
import com.xtelsolution.xmec.model.RESP_List_Disease;
import com.xtelsolution.xmec.model.entity.Disease;
import com.xtelsolution.xmec.xmec.views.inf.IAddIllnessView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phimau on 3/7/2017.
 */

public class AddDiseasePresenter {
    private IAddIllnessView view;

    public AddDiseasePresenter(IAddIllnessView view) {
        this.view = view;
    }
}

