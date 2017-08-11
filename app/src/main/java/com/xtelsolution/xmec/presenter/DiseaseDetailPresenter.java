package com.xtelsolution.xmec.presenter;

import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.DiseaseModel;
import com.xtelsolution.xmec.model.RESP_Disease_Detail;
import com.xtelsolution.xmec.views.inf.IAddIllnessView;
import com.xtelsolution.xmec.views.inf.IDiseaseDetailView;

/**
 * Created by phimau on 4/1/2017.
 */

public class DiseaseDetailPresenter extends BasePresenter {
    private final int GETDETAILDISEASE = 1;
    private IDiseaseDetailView view;
    private IAddIllnessView iAddIllnessView;

    public DiseaseDetailPresenter(IDiseaseDetailView view) {
        this.view = view;
    }

    private void getDiseaseDetail(final Object... param) {
        final int id = (int) param[1];
        String url = Constant.SERVER_XMEC + Constant.DETAIL_DISEASE + id;
        xLog.e("URL", url);
        DiseaseModel.getInstance().getDisease(url, LoginManager.getCurrentSession(), new ResponseHandle<RESP_Disease_Detail>(RESP_Disease_Detail.class) {
            @Override
            public void onSuccess(RESP_Disease_Detail obj) {
                xLog.e("DiseaseDetailPresenter", obj.toString());
                view.onLoadDiseaseDetailSuccess(obj);
            }

            @Override
            public void onError(Error error) {
//                handlerError(view, error, param);
                if (error.getCode() == 2) {
//                                handlerError(view, error, DeleteFriend, friend_uid);
                    CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                        @Override
                        public void onSuccess(RESP_Login success) {
                            getDiseaseDetail(GETDETAILDISEASE, id);
                        }

                        @Override
                        public void onError(Error error) {
                            view.requireLogin();
                            view.showToast("Vui lòng đăng nhập để tiếp tục.");
                        }
                    });
                } else {
                    view.showToast("Có lỗi xảy ra.");
                }
            }
        });

    }

    public void checkGetDiseaseDetail(int id) {
        if (!checkConnnecttion(view))
            return;
        getDiseaseDetail(GETDETAILDISEASE, id);
    }

}
