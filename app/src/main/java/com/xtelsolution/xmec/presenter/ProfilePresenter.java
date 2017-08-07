package com.xtelsolution.xmec.presenter;

import android.app.Activity;
import android.graphics.Bitmap;
import android.util.Log;

import com.google.gson.JsonObject;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.Task;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.listener.IProfileView;
import com.xtelsolution.xmec.listener.UploadFileListener;
import com.xtelsolution.xmec.model.RESP_User;
import com.xtelsolution.xmec.model.SharedPreferencesUtils;
import com.xtelsolution.xmec.model.UserModel;

/**
 * Created by phimau on 2/17/2017.
 */

public class ProfilePresenter extends BasePresenter {
    private static final String TAG = "ProfilePresenter";
    private final int UPDATEPROFILE = 1;
    private IProfileView view;

    public ProfilePresenter(IProfileView view) {
        this.view = view;
    }

    //    final String name, final long birthDay, final double hegiht, final double weight, String urlAvatar
    private void updateProfile(final Object... param) {

        final String name = (String) param[1];
        final long birthDay = (long) param[2];
        final double hegiht = (double) param[3];
        final double weight = (double) param[4];
        final String urlAvatar = (String) param[5];
        final int gender = (int) param[6];
        view.showProgressDialog(view.getActivity().getResources().getString(R.string.update_process));
        String url = Constant.SERVER_XMEC + Constant.GET_USER;
        Log.e("TEST", "updateProfile: " + url);
        JsonObject jsonUser = new JsonObject();
        jsonUser.addProperty(Constant.USER_FULL_NAME, name);
        jsonUser.addProperty(Constant.USER_BIRTHDAY, birthDay);
        jsonUser.addProperty(Constant.USER_PHONE_NUMBER, SharedPreferencesUtils.getInstance().getStringValue(Constant.USER_PHONE_NUMBER));
        jsonUser.addProperty(Constant.USER_ADDRESS, SharedPreferencesUtils.getInstance().getStringValue(Constant.USER_ADDRESS));
        jsonUser.addProperty(Constant.USER_AVATAR, urlAvatar);
        jsonUser.addProperty(Constant.USER_HEIGHT, hegiht);
        jsonUser.addProperty(Constant.USER_WEIGHT, weight);
        jsonUser.addProperty(Constant.USER_GENDER, gender);
        Log.e("ProfilePresenter", "updateProfile: " + jsonUser.toString());
        UserModel.getintance().updateInfoUser(url, jsonUser.toString(), LoginManager.getCurrentSession(), new ResponseHandle<RESP_Basic>(RESP_Basic.class) {
            @Override
            public void onSuccess(RESP_Basic obj) {
                view.onUpdateProfileSuccess();
                SharedPreferencesUtils.getInstance().putStringValue(Constant.USER_FULL_NAME, name);
                SharedPreferencesUtils.getInstance().putLongValue(Constant.USER_BIRTHDAY, birthDay);
                SharedPreferencesUtils.getInstance().putFloatValue(Constant.USER_HEIGHT, (float) hegiht);
                SharedPreferencesUtils.getInstance().putFloatValue(Constant.USER_WEIGHT, (float) weight);
                SharedPreferencesUtils.getInstance().putStringValue(Constant.USER_AVATAR, urlAvatar);
                SharedPreferencesUtils.getInstance().putIntValue(Constant.USER_GENDER, gender);
                view.dismissProgressDialog();
            }

            @Override
            public void onError(Error error) {
                view.onError();
                handlerError(view, error, param);
            }
        });
    }

    public void checkUpdateProfile(final String name, final long birthDay, final double hegiht, final double weight, String urlAvatar, int sex) {
        if (!checkConnnecttion(view)) {
            view.onError();
            return;
        }
        updateProfile(UPDATEPROFILE, name, birthDay, hegiht, weight, urlAvatar, sex);
    }

    public void getProfile() {
//        RESP_User user = SharedPreferencesUtils.getInstance().getUser();
        RESP_User user = (RESP_User) view.getActivity().getIntent().getSerializableExtra(Constant.OBJECT);
        Log.e("USER", "getProfile: " + user.toString());
        ;
        view.onLoadProfileSuccess(user.getFullname(), user.getBirthday(), user.getHeight(), user.getWeight(), user.getAvatar(), user.getGender());
    }

    @Override
    public void onGetNewSessionSuccess(Object... param) {
        switch ((int) param[0]) {
            case UPDATEPROFILE:
                updateProfile(param);
                break;
        }
    }

    public void postImage(Bitmap bitmap, boolean isBigImage, Activity context) {

        view.showProgressDialog(view.getActivity().getResources().getString(R.string.upload_image));
        if (!checkConnnecttion(view))
            return;
        new Task.ConvertImage(context, isBigImage, new UploadFileListener() {
            @Override
            public void onSuccess(String url) {
                xLog.e(TAG, "postImage: onSuccess" + "onSuccess: " + url);
                view.onUploadImageSussces(url);
                view.dismissProgressDialog();
            }

            @Override
            public void onError(String e) {
                view.onError();
            }
        }).execute(bitmap);

    }
}
