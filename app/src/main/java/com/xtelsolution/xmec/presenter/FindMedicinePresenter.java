package com.xtelsolution.xmec.presenter;

import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.MedicineModel;
import com.xtelsolution.xmec.model.RESP_List_Medicine_Compact;

import com.xtelsolution.xmec.xmec.views.inf.ISearchMedicineView;

/**
 * Created by phimau on 3/21/2017.
 */

public class FindMedicinePresenter extends BasePresenter {
    private static final String TAG = "FindMedicinePresenter";
    private ISearchMedicineView view;
    private final int SEARCHMEDICINE = 1;

    public FindMedicinePresenter(ISearchMedicineView view) {
        this.view = view;
    }

    public void searchMedicine(final Object... param) {
        String key = (String) param[1];
        String url = Constant.SERVER_XMEC + Constant.MEDICINE_SEARCH + "?name=" + key + "&size=15";
        xLog.e(TAG, "searchMedicine:" + url);
        xLog.e(TAG, "searchMedicine: secsion: " + LoginManager.getCurrentSession());
        MedicineModel.getInstance().findMedicine(url, getSession(), new ResponseHandle<RESP_List_Medicine_Compact>(RESP_List_Medicine_Compact.class) {
            @Override
            public void onSuccess(RESP_List_Medicine_Compact obj) {
                view.onFindMedicienFinish(obj.getData());
                xLog.e(TAG, "searchMedicine: SUCCESS: " + obj.toString());

            }

            @Override
            public void onError(Error error) {
                view.onError();
                handlerError(view, error, param);
            }
        });
    }

    public void checkSearchMedicine(String key) {
        if (!checkConnnecttion(view))
            return;
        if (key.length() < 1)
            return;
        searchMedicine(SEARCHMEDICINE, key);
    }

    @Override
    public void onGetNewSessionSuccess(Object... param) {
        switch ((int) param[0]) {
            case SEARCHMEDICINE:
                searchMedicine(param);
                break;

        }
    }
}
