package com.xtelsolution.xmec.presenter;

import com.xtel.nipservicesdk.LoginManager;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.NetWorkInfo;
import com.xtelsolution.xmec.model.SharedPreferencesUtils;
import com.xtelsolution.xmec.views.inf.BaseView;


/**
 * Created by phimau on 3/22/2017
 */

public abstract class BasePresenter {
    private static final String TAG = BasePresenter.class.getSimpleName();
    private int count = 0;

    public boolean checkConnnecttion(BaseView view) {
        if (NetWorkInfo.isOnline(view.getActivity()))
            return true;
        else {
            view.showToast("Không kết nối mạng");
            return false;
        }


    }

    protected String getSession() {
        if (!SharedPreferencesUtils.getInstance().isLogined()) {
            return Constant.LOCAL_SECCION;
        } else {
            return LoginManager.getCurrentSession();
        }
    }

    /**
     * @param view
     * @param error
     * @param param
     */
//    protected void handlerError(final BaseView view, Error error, final Object... param) {
//        xLog.e(TAG, "handlerError" + error.toString());
//        switch (error.getCode()) {
//            case 2:
////                count++;
//                if (Constant.iGetNewSession) {
////                    if (Constant.iGetNewSession) {
////                        onGetNewSessionSuccess(param);
////                        Constant.iGetNewSession = false;
////                    } else {
//                    String service_code = LoginModel.getInstance().getServiceCode(view.getActivity());
//                    xLog.e(TAG, Constant.LOGPHI + "service_code" + service_code);
//                    LoginModel.getInstance().getNewSession(service_code, new ResponseHandle<RESP_Login>(RESP_Login.class) {
//                        @Override
//                        public void onSuccess(RESP_Login obj) {
////                        view.dismissProgressDialog();
//                            xLog.e(TAG, "So lan het han " + count);
//                            SharedUtils.getInstance().putStringValue(Cts.USER_SESSION, obj.getSession());
//                            SharedUtils.getInstance().putStringValue(Cts.USER_AUTH_ID, obj.getAuthenticationid());
//                            onGetNewSessionSuccess(param);
//                            Constant.iGetNewSession = true;
//                            xLog.e(TAG, "SESSION " + obj.toString());
//
//                        }
//
//                        @Override
//                        public void onError(Error error) {
////                            view.showToast("Session hết hạn");
//                            xLog.e(TAG, "SESSION " + error.toString());
//                            Constant.iGetNewSession = false;
//                            Intent i = new Intent(view.getActivity(), LoginActivity.class);
//                            view.getActivity().startActivity(i);
//                        }
//                    });
////                    }
//                }
//
//                break;
//            case -1:
//                view.dismissProgressDialog();
//                xLog.e(TAG, "Loi he thong" + error.getMessage());
//                view.showToast("Lỗi hệ thống");
//        }
//    }

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
//    public abstract void onGetNewSessionSuccess(Object... param);
}
