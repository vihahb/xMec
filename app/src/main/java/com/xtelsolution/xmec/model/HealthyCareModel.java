package com.xtelsolution.xmec.model;

import com.xtel.nipservicesdk.callback.ResponseHandle;

/**
 * Created by phimau on 3/10/2017.
 */

public class HealthyCareModel extends BasicModel {
    private static HealthyCareModel instance = new HealthyCareModel();

    public static HealthyCareModel getInstance() {
        return instance;
    }
    public void getHospital(String url, String session, ResponseHandle<RESP_List_Map_Healthy_Care> responseHandle){
        requestServer.getApi(url,session,responseHandle);
    }
    public void getDetailHospital(String url,String session,ResponseHandle<RESP_Healthy_Care_Detail> responseHandle){
        requestServer.getApi(url,session,responseHandle);
    }

}
