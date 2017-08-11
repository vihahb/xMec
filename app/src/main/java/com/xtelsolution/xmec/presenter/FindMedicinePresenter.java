package com.xtelsolution.xmec.presenter;

import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.commons.Cts;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.utils.SharedUtils;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.MedicineModel;
import com.xtelsolution.xmec.model.RESP_List_Medicine_Compact;
import com.xtelsolution.xmec.views.inf.ISearchMedicineView;

/**
 * Created by phimau on 3/21/2017.
 */

public class FindMedicinePresenter extends BasePresenter {
    private static final String TAG = "FindMedicinePresenter";
    private final int SEARCHMEDICINE = 1;
    private ISearchMedicineView view;


    public FindMedicinePresenter(ISearchMedicineView view) {
        this.view = view;
    }

    public void searchMedicine(final Object... param) {
        final String key = (String) param[1];
        String url = Constant.SERVER_XMEC + Constant.MEDICINE_SEARCH + "?name=" + key + "&size=15";
        xLog.e(TAG, "searchMedicine:" + url);
        xLog.e(TAG, "searchMedicine: secsion: " + LoginManager.getCurrentSession());
        MedicineModel.getInstance().findMedicine(url, SharedUtils.getInstance().getStringValue(Cts.USER_SESSION), new ResponseHandle<RESP_List_Medicine_Compact>(RESP_List_Medicine_Compact.class) {
            @Override
            public void onSuccess(RESP_List_Medicine_Compact obj) {
                view.onFindMedicienFinish(obj.getData());
                xLog.e(TAG, "searchMedicine: SUCCESS: " + obj.toString());

            }

            @Override
            public void onError(Error error) {
                view.onError();
//                handlerError(view, error, param);
                if (error.getCode() == 2) {
                    CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                        @Override
                        public void onSuccess(RESP_Login success) {
                            searchMedicine(SEARCHMEDICINE, key);
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

    public void checkSearchMedicine(String key) {
        if (!checkConnnecttion(view))
            return;
        if (key.length() < 1)
            return;
        searchMedicine(SEARCHMEDICINE, key);
    }

}
