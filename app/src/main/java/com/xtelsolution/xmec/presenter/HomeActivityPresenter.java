package com.xtelsolution.xmec.presenter;

import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_None;
import com.xtelsolution.xmec.MyApplication;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.callbacks.ICommand;
import com.xtelsolution.xmec.common.NetWorkInfo;
import com.xtelsolution.xmec.model.RESP_ListFriend;
import com.xtelsolution.xmec.model.UserModel;
import com.xtelsolution.xmec.views.activity.inf.IHomeActivityView;

/**
 * Created by vivhp on 8/1/2017.
 */

public class HomeActivityPresenter {

    private IHomeActivityView view;

    private ICommand iCmd = new ICommand() {
        @Override
        public void excute(Object... params) {
            switch ((int) params[0]) {
                case 1:
                    String token = (String) params[1];
                    UserModel.getintance().updateCloudMessageToken(token, LoginManager.getCurrentSession(), new ResponseHandle<RESP_None>(RESP_None.class) {
                        @Override
                        public void onSuccess(RESP_None obj) {
                            view.updateTokenSuccess();
                        }

                        @Override
                        public void onError(Error error) {
                            view.updateTokenError();
                        }
                    });
                    break;

                case 2:
                    UserModel.getintance().getFriendRequest(LoginManager.getCurrentSession(), new ResponseHandle<RESP_ListFriend>(RESP_ListFriend.class) {
                        @Override
                        public void onSuccess(RESP_ListFriend obj) {
                            view.setNotificationSize(obj.getData().size());
                        }

                        @Override
                        public void onError(Error error) {
                            view.setNotificationError();
                        }
                    });
                    break;
            }
        }
    };

    public HomeActivityPresenter(IHomeActivityView view) {
        this.view = view;
    }

    public void updateFcmToken(String token) {
        if (!NetWorkInfo.isOnline(MyApplication.context)) {
            view.showToast(view.getActivity().getString(R.string.error_no_internet));
        } else {
            iCmd.excute(1, token);
        }
    }

    public void getNotificationCount() {
        if (!NetWorkInfo.isOnline(MyApplication.context)) {
            view.showToast(view.getActivity().getString(R.string.error_no_internet));
        } else {
            iCmd.excute(2);
        }
    }

}
