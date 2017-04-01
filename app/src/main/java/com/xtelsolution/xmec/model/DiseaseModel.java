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
    public void getListIllness(String url, String session, ResponseHandle<RESP_List_Disease_With_Link> responseHandle){
        requestServer.getApi(url,session,responseHandle);
    }
    public void findDisease(String url, String session, ResponseHandle<RESP_List_Disease> responseHandle){
        requestServer.getApi(url,session,responseHandle);
    }
    public void addDisease(String url,String obj,String session,ResponseHandle<RESP_ID> responseHandle){
        requestServer.postApi(url,obj,session,responseHandle);
    }
    public void addMedicine(String url,String obj,String session,ResponseHandle<RESP_ID> responseHandle){
        requestServer.postApi(url,obj,session,responseHandle);
    }
    public void getDisease(String url,String sesion,ResponseHandle<RESP_Disease_Detail> responseHandle){
        requestServer.getApi(url,sesion,responseHandle);
    }
    public void findMedicine(String url,String session ,ResponseHandle<RESP_List_Medicine_Compact> responseHandle){
        requestServer.getApi(url,session,responseHandle);

    }
}
