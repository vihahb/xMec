package com.xtelsolution.xmec.presenter.Activity;

import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;
import com.xtel.nipservicesdk.model.entity.RESP_None;
import com.xtel.nipservicesdk.utils.JsonParse;
import com.xtelsolution.xmec.callbacks.ICommand;
import com.xtelsolution.xmec.common.NetWorkInfo;
import com.xtelsolution.xmec.model.RESP_ListFriend;
import com.xtelsolution.xmec.model.UserModel;
import com.xtelsolution.xmec.views.activity.inf.INotificationActivity;

/**
 * Created by vivhp on 7/27/2017
 */

public class NotificationActivityPresenter {

    private INotificationActivity view;

    private ICommand iCmd = new ICommand() {
        @Override
        public void excute(Object... params) {
            switch ((int)params[0]) {
                case 1:
                    //Get Notification
                    UserModel.getintance().getFriendRequest(LoginManager.getCurrentSession(), new ResponseHandle<RESP_ListFriend>(RESP_ListFriend.class) {
                        @Override
                        public void onSuccess(RESP_ListFriend obj) {
                            view.setRefresh(false);
                            if (obj.getData().size() == 0){
                                view.getNotificationRequestError("Dữ liệu trống");
                            } else {
                                view.getNotificationRequestSuccess(obj.getData());
                            }
                        }

                        @Override
                        public void onError(Error error) {
                            view.setRefresh(false);
                            if (error.getCode() == 2){
                                view.showToast(JsonParse.getCodeMessage(error.getCode(), "Có lỗi."));
                                view.onAuthentExpired();
                            } else {
                                view.showToast(JsonParse.getCodeMessage(error.getCode(), "Có lỗi."));
                            }
                        }
                    });
                    break;

                case 2:
                    //Accepted friend
                    final int uid_accept = (int) params[1];
                    UserModel.getintance().sendAcceptFriend(uid_accept, LoginManager.getCurrentSession(), new ResponseHandle<RESP_None>(RESP_None.class) {
                        @Override
                        public void onSuccess(RESP_None obj) {
                            view.showToast("Đã chấp nhận lời mời kết bạn");
                            view.onAccepted(uid_accept);
                        }

                        @Override
                        public void onError(Error error) {
                            if (error.getCode() == 2){
                                view.showToast(JsonParse.getCodeMessage(error.getCode(), "Có lỗi."));
                                view.onAuthentExpired();
                            } else {
                                view.showToast(JsonParse.getCodeMessage(error.getCode(), "Có lỗi."));
                            }
                        }
                    });
                    break;

                case 3:
                    //Decline Friend
                    final int uid_decline = (int) params[1];
                    UserModel.getintance().sendDeclineFriend(uid_decline, LoginManager.getCurrentSession(), new ResponseHandle<RESP_Basic>(RESP_Basic.class) {
                        @Override
                        public void onSuccess(RESP_Basic obj) {
                            view.showToast("Đã hủy lời mời kết bạn");
                            view.onDeclined(uid_decline);
                        }

                        @Override
                        public void onError(Error error) {
                            if (error.getCode() == 2){
                                view.showToast(JsonParse.getCodeMessage(error.getCode(), "Có lỗi."));
                                view.onAuthentExpired();
                            } else {
                                view.showToast(JsonParse.getCodeMessage(error.getCode(), "Có lỗi."));
                            }
                        }
                    });
                    break;
            }
        }
    };

    public NotificationActivityPresenter(INotificationActivity view) {
        this.view = view;
    }

    public void getNotificationRequest(){
        if (!NetWorkInfo.isOnline(view.getActivity())){
            view.onNoNetWork();
        } else {
            iCmd.excute(1);
        }
    }

    public void onActiveFriend(int uid_friend){
        if (!NetWorkInfo.isOnline(view.getActivity())){
            view.onNoNetWork();
        } else {
            iCmd.excute(2, uid_friend);
        }
    }

    public void onDeclineFriend(int uid_friend){
        if (!NetWorkInfo.isOnline(view.getActivity())){
            view.onNoNetWork();
        } else {
            iCmd.excute(3, uid_friend);
        }
    }
}
