package com.xtelsolution.xmec.model;

import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

/**
 * Created by phimau on 2/21/2017.
 */

public class MedicalDirectoryModel extends BasicModel {
    private static MedicalDirectoryModel instance = new MedicalDirectoryModel();

    public static MedicalDirectoryModel getinstance() {
        return instance;
    }

    public void getMedicalReportBooks(String url, String session, ResponseHandle<RESP_List_Medical> userResponseHandle) {
        requestServer.getApi(url, session, userResponseHandle);
    }

    public void getMedicalDirectoryDetail(String url, String session, ResponseHandle<RESP_Medical_Detail> responseHandle) {
        requestServer.getApi(url, session, responseHandle);
    }

    public void addMedicalDirectory(String url, String obj, String session, ResponseHandle<RESP_ID> responseHandle) {
        requestServer.postApi(url, obj, session, responseHandle);
    }

    public void deleteMedicalDirectory(String url, String session, ResponseHandle<RESP_Basic> responseHandle) {
        requestServer.deleteApi(url, "", session, responseHandle);
    }

    public void updateMedicalDirectory(String url, String jsonObject, String session, ResponseHandle<RESP_Basic> responseHandle) {
        requestServer.putApi(url, jsonObject, session, responseHandle);
    }
}
