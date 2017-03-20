package com.xtelsolution.xmec.presenter;

import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.MedicineModel;
import com.xtelsolution.xmec.model.RESP_List_Medicine_Compact;
import com.xtelsolution.xmec.model.RESP_Medicine;
import com.xtelsolution.xmec.xmec.views.inf.ISearchMedicineView;

/**
 * Created by phimau on 3/21/2017.
 */

public class FindMedicinePresenter {
    private ISearchMedicineView view;

    public FindMedicinePresenter(ISearchMedicineView view) {
        this.view = view;
    }

    public void searchMedicine(String key) {
        String url = Constant.SERVER_XMEC + Constant.MEDICINE_SEARCH + "?name=" + key + "&size=15";
        MedicineModel.getInstance().findMedicine(url, Constant.LOCAL_SECCION, new ResponseHandle<RESP_List_Medicine_Compact>(RESP_List_Medicine_Compact.class) {
            @Override
            public void onSuccess(RESP_List_Medicine_Compact obj) {
                view.onFindMedicienFinish(obj.getData());
                xLog.e("SUCCESS"+obj.toString());
            }

            @Override
            public void onError(Error error) {

            }
        });
    }
}
