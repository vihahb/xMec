package com.xtelsolution.xmec.model;

import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtelsolution.xmec.common.Constant;

/**
 * Created by phimau on 2/15/2017.
 */

public class UserModel extends BasicModel{
    private static UserModel instance = new UserModel();
    public static UserModel getintance(){
        return instance;
    }
    public String getSession(){
       return SharedPreferencesUtils.getInstance().getStringValue(Constant.USER_SESSION);
    }
    public void getUser(String url, String session, ResponseHandle<RESP_User> userResponseHandle){
        requestServer.getApi(url,session,userResponseHandle);
    }
    public void getMedicalReportBooks(String url, String session, ResponseHandle<RESP_LIST_MEDICAL> userResponseHandle){
        requestServer.getApi(url,session,userResponseHandle);
    }

}
