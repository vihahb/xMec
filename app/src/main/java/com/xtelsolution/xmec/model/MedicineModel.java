package com.xtelsolution.xmec.model;

import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

/**
 * Created by phimau on 3/19/2017.
 */

public class MedicineModel extends BasicModel {
    private static MedicineModel instance = new MedicineModel();

    public static MedicineModel getInstance() {
        return instance;
    }

    public void addMedicine(String url, String obj, String session, ResponseHandle<RESP_ID> responseHandle) {
        requestServer.postApi(url, obj, session, responseHandle);
    }

    public void findMedicine(String url, String session, ResponseHandle<RESP_List_Medicine_Compact> responseHandle) {
        requestServer.getApi(url, session, responseHandle);

    }

    public void removeMedicine(String url, String session, ResponseHandle<RESP_Basic> responseHandle) {
        requestServer.deleteApi(url, "", session, responseHandle);
    }


}
