package com.xtelsolution.xmec.presenter;

import android.util.Log;

import com.xtel.nipservicesdk.model.entity.Error;
import com.xtelsolution.xmec.common.NetWorkInfo;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.xmec.views.inf.BaseView;

/**
 * Created by phimau on 3/22/2017.
 */

public class BasePresenter {
    public boolean checkConnnecttion(BaseView view){
        if (NetWorkInfo.isOnline(view.getActivity()))
            return true;
        else {
            view.showToast("Không kết nối mạng");
            return false;
        }


    }
    protected void handlerError(BaseView view, Error error){
        xLog.e("handlerError"+error.toString());
        view.dismissProgressDialog();
        switch (error.getCode()) {
            case 2:
                view.showToast("Session không hợp lệ");
                break;
            case -1:
                view.showToast(error.getMessage());
        }
    }
}
