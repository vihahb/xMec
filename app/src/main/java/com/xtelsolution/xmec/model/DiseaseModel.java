package com.xtelsolution.xmec.model;

import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.BasicModel;

/**
 * Created by phimau on 3/6/2017.
 */

public class DiseaseModel extends BasicModel {
    private static DiseaseModel instance = new DiseaseModel();
    public static DiseaseModel getInstance(){
        return instance;
    }
    public void getListIllness(String url, String session, ResponseHandle<RESP_List_Disease> responseHandle){
        requestServer.getApi(url,session,responseHandle);
    }
    public void findDisease(String url, String session, ResponseHandle<RESP_List_Disease> responseHandle){
        requestServer.getApi(url,session,responseHandle);
    }
}
