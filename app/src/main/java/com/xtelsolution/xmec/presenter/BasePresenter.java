package com.xtelsolution.xmec.presenter;

import android.content.Intent;

import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.commons.Cts;
import com.xtel.nipservicesdk.model.LoginModel;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.utils.SharedUtils;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.NetWorkInfo;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.xmec.views.activity.LoginActivity;
import com.xtelsolution.xmec.xmec.views.inf.BaseView;


/**
 * Created by phimau on 3/22/2017.
 */

public abstract class BasePresenter {
    private int count = 0;
    private static final String TAG = BasePresenter.class.getSimpleName();
    public boolean checkConnnecttion(BaseView view) {
        if (NetWorkInfo.isOnline(view.getActivity()))
            return true;
        else {
            view.showToast("Không kết nối mạng");
            return false;
        }


    }

    protected void handlerError(final BaseView view, Error error, final Object... param) {
        xLog.e(TAG,"handlerError" + error.toString());
        switch (error.getCode()) {
            case 2:
                count++;
                synchronized (Constant.iGetNewSession) {
//                    if (Constant.iGetNewSession) {
//                        onGetNewSessionSuccess(param);
//                        Constant.iGetNewSession = false;
//                    } else {
                        String service_code = LoginModel.getInstance().getServiceCode(view.getActivity());
                        xLog.e(TAG,Constant.LOGPHI + "service_code" + service_code);
                        LoginModel.getInstance().getNewSession(service_code, new ResponseHandle<RESP_Login>(RESP_Login.class) {
                            @Override
                            public void onSuccess(RESP_Login obj) {
                                view.dismissProgressDialog();
                                xLog.e(TAG,"So lan het han "+count);
                                SharedUtils.getInstance().putStringValue(Cts.USER_SESSION, obj.getSession());
                                onGetNewSessionSuccess(param);
                                Constant.iGetNewSession = true;
                                xLog.e(TAG,"SESSION " + obj.toString());

                            }

                            @Override
                            public void onError(Error error) {
                                view.showToast("Session hết hạn");
                                xLog.e(TAG,"SESSION " + error.toString());
                                Constant.iGetNewSession = false;
                                Intent i = new Intent(view.getActivity(), LoginActivity.class);
                                view.getActivity().startActivity(i);
                            }
                        });
//                    }
                }

                break;
            case -1:
                view.dismissProgressDialog();
                xLog.e(TAG,"Loi he thong" + error.getMessage());
                view.showToast("Lỗi hệ thống");
        }
    }

    //    protected void getNewSession(BaseView view) {
//        String service_code = LoginModel.getInstance().getServiceCode(view.getActivity());
//        LoginModel.getInstance().getNewSession(service_code, new ResponseHandle<RESP_Login>(RESP_Login.class) {
//            @Override
//            public void onSuccess(RESP_Login obj) {
//                SharedUtils.getInstance().putStringValue(Cts.USER_SESSION, obj.getSession());
//
//            }
//
//            @Override
//            public void onError(Error error) {
//                xLog.e(TAG,Constant.LOGPHI + " getNewSession " + error.toString());
//            }
//        });
//    }
    public abstract void onGetNewSessionSuccess(Object... param);
}
