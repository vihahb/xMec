package com.xtelsolution.xmec.views.activity.inf;

import com.xtelsolution.xmec.model.entity.UserInfo;
import com.xtelsolution.xmec.views.inf.BaseView;

import java.util.ArrayList;

/**
 * Created by vivhp on 7/28/2017.
 */

public interface INotificationActivity extends BaseView {

    void getNotificationRequestSuccess(ArrayList<UserInfo> data);

    void getNotificationRequestError(String mes);

    void onNoNetWork();

    void onAccept(int uid_friend);

    void onDecline(int uid_friend);

    void onAccepted(int uid_friend);

    void onDeclined(int uid_friend);

    void onAuthentExpired();

    void setRefresh(boolean isRefresh);
}
