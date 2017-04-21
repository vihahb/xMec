package com.xtelsolution.xmec.presenter;

import android.util.Log;

import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.utils.SharedUtils;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.MedicalDirectoryModel;
import com.xtelsolution.xmec.model.RESP_List_Medical;
import com.xtelsolution.xmec.model.RESP_Medical;
import com.xtelsolution.xmec.model.RESP_User;
import com.xtelsolution.xmec.model.SharedPreferencesUtils;
import com.xtelsolution.xmec.model.UserModel;
import com.xtelsolution.xmec.xmec.views.inf.IHomeView;

import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by phimau on 2/15/2017.
 */

public class HomePresenter extends BasePresenter {
    private static final String TAG = "HomePresenter";
    private IHomeView view;
    private final int GETUSER = 1;
    private final int GETMEDICAL = 2;


    public HomePresenter(IHomeView view) {
        this.view = view;
    }

    private void getUser(final Object... param) {
        String url = Constant.SERVER_XMEC + Constant.GET_USER;
        xLog.e(TAG, "getUser: URL  " + url);
        UserModel.getintance().getUser(url, LoginManager.getCurrentSession(), new ResponseHandle<RESP_User>(RESP_User.class) {
            @Override
            public void onSuccess(RESP_User obj) {
                SharedPreferencesUtils.getInstance().saveUser(obj);
                view.onGetUerSusscess(obj);
            }

            @Override
            public void onError(Error error) {
                handlerError(view, error, GETUSER);
            }
        });
    }

    private void getMedicalReportBooks(Object... param) {
        String url = Constant.SERVER_XMEC + Constant.GET_MEDIACAL_REPORT_BOOK;
        Log.d("URL", "getMedicalReportBooks: " + url);
        MedicalDirectoryModel.getinstance().getMedicalReportBooks(url, LoginManager.getCurrentSession(), new ResponseHandle<RESP_List_Medical>(RESP_List_Medical.class) {
            @Override
            public void onSuccess(RESP_List_Medical obj) {
                xLog.e(TAG, "getMedicalReportBooks: PHIMH" + obj.toString());
                view.onGetMediacalListSusscess(false, obj);
            }

            @Override
            public void onError(Error error) {
                xLog.e(TAG, "getMedicalReportBooks: onError" + error.toString());
                handlerError(view, error, GETMEDICAL);
            }
        });
    }

    public void checkGetUser(RESP_User userModel) {
        if (!checkConnnecttion(view))
            return;
        if (SharedPreferencesUtils.getInstance().isLogined()) {
            if (userModel == null) {
                getUser(GETUSER);
            } else view.onGetUerSusscess(userModel);
        } else {
            view.onGetUerSusscess(null);
        }

    }

    public void checkGetMedical(ArrayList<RESP_Medical> mlistMedica) {
        if (!checkConnnecttion(view))
            return;
        if (SharedPreferencesUtils.getInstance().isLogined()) {
            if (mlistMedica.size() == 0) {
                getMedicalReportBooks(GETMEDICAL);
            } else
                view.onGetMediacalListSusscess(true, new RESP_List_Medical(mlistMedica));
        } else {
            view.onGetMediacalListSusscess(true,null);
        }
    }

    @Override
    public void onGetNewSessionSuccess(Object... param) {
        switch ((int) param[0]) {
            case GETUSER:
                getUser(param);
                break;
            case GETMEDICAL:
                getMedicalReportBooks(param);
                break;
        }
    }
}
