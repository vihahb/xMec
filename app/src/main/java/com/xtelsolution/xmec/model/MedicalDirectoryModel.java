package com.xtelsolution.xmec.model;

import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;
import com.xtelsolution.xmec.model.entity.MedicalDirectory;

/**
 * Created by phimau on 2/21/2017.
 */

public class MedicalDirectoryModel extends com.xtel.nipservicesdk.model.BasicModel {
    private static MedicalDirectoryModel instance = new MedicalDirectoryModel();
    public static  MedicalDirectoryModel getInstance(){
        return instance;
    }
    public void getListMedicalDirectory(String url , String obj, String session, ResponseHandle<MedicalDirectory> responseHandle){
        requestServer.putApi(url,obj,session,responseHandle);
    }
    public void getMedicalDirectoryDetail(String url,String obj,String session,ResponseHandle<MedicalDirectory> responseHandle){
        requestServer.putApi(url,obj,session,responseHandle);
    }
    public void addMedicalDirectory(String url,String obj ,String session,ResponseHandle<RESP_ID> responseHandle) {
        requestServer.postApi(url,obj,session,responseHandle);
    }
    public void deleteMedicalDirectory(String url, String ojb , String session, ResponseHandle<RESP_Basic> responseHandle){

    }
}
