package com.xtelsolution.xmec.model;

import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.BasicModel;

/**
 * Created by phimau on 3/6/2017.
 */

public class IllnessModel extends BasicModel {
    private static IllnessModel instance = new IllnessModel();
    public static IllnessModel getInstance(){
        return instance;
    }
    public void getListIllness(String url, String session, ResponseHandle<RESP_List_IIlness> responseHandle){
        requestServer.getApi(url,session,responseHandle);
    }
}
