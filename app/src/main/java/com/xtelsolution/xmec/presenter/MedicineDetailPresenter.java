package com.xtelsolution.xmec.presenter;

import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.MedicineDetailModel;
import com.xtelsolution.xmec.model.RESP_Medicine_Detail;
import com.xtelsolution.xmec.views.inf.IMedicineDetailView;

/**
 * Created by HUNGNT on 4/3/2017.
 */

public class MedicineDetailPresenter {
    IMedicineDetailView view;

    public MedicineDetailPresenter(IMedicineDetailView view) {
        this.view = view;
    }

    public void getMedicineDetail(String id) {
        MedicineDetailModel.getInstance().getMedicineDetail(id, new ResponseHandle<RESP_Medicine_Detail>(RESP_Medicine_Detail.class) {
            @Override
            public void onSuccess(RESP_Medicine_Detail obj) {
                xLog.d("MedicineDetailPresenter", obj.toString());
                view.onDetailLoadedSuccess(obj);
            }

            @Override
            public void onError(Error error) {
                view.onDetailLoadedError();
            }
        });
    }
}
