package com.xtelsolution.xmec.presenter;

import android.util.Log;

import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_None;
import com.xtelsolution.xmec.MyApplication;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.callbacks.ICommand;
import com.xtelsolution.xmec.common.NetWorkInfo;
import com.xtelsolution.xmec.model.UserModel;
import com.xtelsolution.xmec.views.activity.inf.ILoginActivity;

/**
 * Created by vivhp on 8/1/2017.
 */

public class LoginPresenter {

    private ILoginActivity view;

    private ICommand iCmd = new ICommand() {
        @Override
        public void excute(Object... params) {
            switch ((int) params[0]) {
                case 1:
                    String token = (String) params[1];
                    String session = (String) params[2];
                    UserModel.getintance().updateCloudMessageToken(token, session, new ResponseHandle<RESP_None>(RESP_None.class) {
                        @Override
                        public void onSuccess(RESP_None obj) {
                            view.updateTokenSuccess();
//                            view.showToast("Cập nhật Token thành công.");
                        }

                        @Override
                        public void onError(Error error) {
                            view.updateTokenError();
//                            view.showToast("Cập nhật Token thất bại.");
                            Log.e("Error fcm put token", error.toString());
                        }
                    });
                    break;
            }
        }
    };

    public LoginPresenter(ILoginActivity view) {
        this.view = view;
    }

    public void updateFcmToken(String token, String session) {
        if (!NetWorkInfo.isOnline(MyApplication.context)) {
            view.showToast(view.getActivity().getString(R.string.error_no_internet));
        } else {
            iCmd.excute(1, token, session);
        }
    }
}
