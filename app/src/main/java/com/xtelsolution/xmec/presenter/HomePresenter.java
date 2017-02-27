package com.xtelsolution.xmec.presenter;

import android.util.Log;

import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.utils.SharedUtils;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.model.MedicalDirectoryModel;
import com.xtelsolution.xmec.model.RESP_LIST_MEDICAL;
import com.xtelsolution.xmec.model.RESP_User;
import com.xtelsolution.xmec.model.SharedPreferencesUtils;
import com.xtelsolution.xmec.model.UserModel;
import com.xtelsolution.xmec.xmec.views.inf.IHomeView;

/**
 * Created by phimau on 2/15/2017.
 */

public class HomePresenter {
    private IHomeView view;

    public HomePresenter(IHomeView view) {
        this.view = view;
    }

    public void getUser() {
        view.showProgressDialog("Đang tải...");
//        String sesstion = SharedUtils.getInstance().getStringValue(Constant.USER_SESSION);
        String url = Constant.SERVER_XMEC + Constant.GET_USER;
        Log.e("USer", "getUser: " +url);
        UserModel.getintance().getUser(url,"V5BDuS4BFpiMjgfAZBrkQpb2FUFGX8owdAxh9G77o9dE6kXfyuhPss7M5NxyNTgKwxns6SMStxlVERmOH1n05RTvbOUOC0TBWMKR" , new ResponseHandle<RESP_User>(RESP_User.class) {
            @Override
            public void onSuccess(RESP_User obj) {
                Log.d("USer", "onSuccess: " + obj.toString());
                SharedPreferencesUtils.getInstance().saveUser(obj);
                view.onGetUerSusscess(obj);
                getMedicalReportBooks();
            }
            @Override
            public void onError(Error error) {
                view.dismissProgressDialog();
                switch (error.getCode()) {
                    case 2:
                        view.showToast("Session không hợp lệ");
                        break;
                    case -1:
                        view.showToast(error.getMessage());
                }
            }
        });

    }
    private void getMedicalReportBooks(){
        String session = SharedPreferencesUtils.getInstance().getStringValue(Constant.USER_SESSION);
        String url = Constant.SERVER_XMEC+Constant.GET_MEDIACAL_REPORT_BOOK;
        Log.d("URL", "getMedicalReportBooks: ");
        MedicalDirectoryModel.getinstance().getMedicalReportBooks(url, "V5BDuS4BFpiMjgfAZBrkQpb2FUFGX8owdAxh9G77o9dE6kXfyuhPss7M5NxyNTgKwxns6SMStxlVERmOH1n05RTvbOUOC0TBWMKR", new ResponseHandle<RESP_LIST_MEDICAL>(RESP_LIST_MEDICAL.class) {
            @Override
            public void onSuccess(RESP_LIST_MEDICAL obj) {
                view.onGetMediacalListSusscess(obj);
                view.dismissProgressDialog();
            }
            @Override
            public void onError(Error error) {
                view.dismissProgressDialog();
                switch (error.getCode()) {
                    case 2:
                        view.showToast("Session không hợp lệ");
                        break;
                    case -1:
                        view.showToast(error.getMessage());
                        Log.d("sdasdasd", "onError: "+error.getMessage());
                        break;
                }
            }
        });
    }
}
