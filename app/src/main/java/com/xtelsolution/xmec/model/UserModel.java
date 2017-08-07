package com.xtelsolution.xmec.model;

import android.util.Log;

import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.BasicModel;
import com.xtel.nipservicesdk.model.entity.RESP_None;
import com.xtelsolution.xmec.common.Constant;

/**
 * Created by phimau on 2/15/2017
 */

public class UserModel extends BasicModel {
    private static final String TAG = UserModel.class.getSimpleName();
    private static UserModel instance = new UserModel();

    public static UserModel getintance() {
        return instance;
    }

    public String getSession() {
        return SharedPreferencesUtils.getInstance().getStringValue(Constant.USER_SESSION);
    }

    public void getUser(String url, String session, ResponseHandle<RESP_User> responseHandle) {
        requestServer.getApi(url, session, responseHandle);
    }

    //    public void getMedicalReportBooks(String url, String session, ResponseHandle<RESP_List_Medical> responseHandle){
//        requestServer.getApi(url,session,responseHandle);
//    }
    public void updateInfoUser(String url, String userObj, String session, ResponseHandle responseHandle) {
        requestServer.putApi(url, userObj, session, responseHandle);
    }

    public void addFriend(String url, String session, ResponseHandle responseHandle) {
        requestServer.postApi(url, "", session, responseHandle);
    }

    public void searchFriend(String url, String session, ResponseHandle responseHandle) {
        requestServer.getApi(url, session, responseHandle);
    }

    public void getFriendRequest(String session, ResponseHandle responseHandle) {
        String url = Constant.SERVER_XMEC + Constant.FRIEND_REQUEST_STATE + Constant.FRIEND_NOT_ACCEFT;
        Log.e("Danh sach ban url", url);
        requestServer.getApi(url, session, responseHandle);
    }

    public void sendAcceptFriend(int uid, String session, ResponseHandle responseHandle) {
        String url = Constant.SERVER_XMEC + "friend/status?friend_id=" + uid + "&status=" + 1;
        Log.e("friend action url", url);
        requestServer.putApi(url, "", session, responseHandle);
    }

    public void sendDeclineFriend(int uid, String session, ResponseHandle responseHandle) {
        String url = Constant.SERVER_XMEC + Constant.FRIEND_ACTION + uid;
        Log.e("friend action url", url);
        requestServer.deleteApi(url, "", session, responseHandle);
    }

    public void updateCloudMessageToken(String token, String sesion, ResponseHandle responseHandle) {
        String url = Constant.SERVER_XMEC + "user/update/fcm?key=" + token;
        Log.e("url fcm", url);
        requestServer.putApi(url, "", sesion, responseHandle);
    }

    public void getFriendList(String session, ResponseHandle responseHandle) {
        String url = Constant.SERVER_XMEC + Constant.FRIEND_REQUEST_STATE + Constant.FRIEND_ACCEFTED;
        Log.e("Danh sach ban active", url);
        requestServer.getApi(url, session, responseHandle);
    }

    public void getMedicalFromUId(int uid, String session, ResponseHandle<RESP_List_Medical> responseHandle) {
//        String url = "http://124.158.5.112:8092/xmec/v0.1/user/medical-report-book/friend?uid=281&page=1&pagesize=10";
        String url = Constant.SERVER_XMEC + "user/medical-report-book/friend?uid=" + uid + "&page=1&pagesize=10";
        Log.e(TAG, "getMedicalFromUId: " + url);
        requestServer.getApi(url, session, responseHandle);
    }

    public void deleteFriendFromUid(int uid, String session, ResponseHandle<RESP_None> responseHandle) {
        String url = Constant.SERVER_XMEC + "friend?friend_id=" + uid;
        Log.e(TAG, "deleteFriendFromUid: " + url);
        requestServer.deleteApi(url, "", session, responseHandle);
    }
}
