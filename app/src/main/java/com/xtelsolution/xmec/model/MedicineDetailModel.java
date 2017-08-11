package com.xtelsolution.xmec.model;

import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.commons.Cts;
import com.xtelsolution.xmec.common.Constant;

/**
 * Created by HUNGNT on 4/3/2017.
 */

public class MedicineDetailModel extends BasicModel {
    private static MedicineDetailModel instance;

    static {
        instance = new MedicineDetailModel();
    }

    public static MedicineDetailModel getInstance() {
        return instance;
    }

    public void getMedicineDetail(String id, ResponseHandle<RESP_Medicine_Detail> handle) {
        String url = Constant.SERVER_XMEC + "user/detail-drug?id=" + id;
        if (SharedPreferencesUtils.getInstance().isLogined())
            requestServer.getApi(url, LoginManager.getCurrentSession(), handle);
        else
            requestServer.getApi(url, Cts.USER_SESSION, handle);
    }
}
