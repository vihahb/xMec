package com.xtelsolution.xmec.presenter;

import android.util.Log;
import android.widget.Toast;

import com.google.gson.JsonObject;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.LoginModel;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.model.RESP_User;
import com.xtelsolution.xmec.model.SharedPreferencesUtils;
import com.xtelsolution.xmec.model.UserModel;
import com.xtelsolution.xmec.xmec.views.inf.IProfileView;

/**
 * Created by phimau on 2/17/2017.
 */

public class UpdateInfoPresenter {
    private IProfileView view;

    public UpdateInfoPresenter(IProfileView view) {
        this.view = view;
    }

    public void updateProfile(final String name, final long birthDay, final double hegiht, final double weight, String urlAvatar) {
        String url = Constant.SERVER_XMEC+Constant.GET_USER;
        Log.e("TEST", "updateProfile: "+url );
        JsonObject jsonUser = new JsonObject();
        jsonUser.addProperty(Constant.USER_FULL_NAME,name);
        jsonUser.addProperty(Constant.USER_GENDER,SharedPreferencesUtils.getInstance().getIntValue(Constant.USER_GENDER));
        jsonUser.addProperty(Constant.USER_BIRTHDAY,birthDay);
        jsonUser.addProperty(Constant.USER_PHONE_NUMBER,SharedPreferencesUtils.getInstance().getStringValue(Constant.USER_PHONE_NUMBER));
        jsonUser.addProperty(Constant.USER_ADDRESS,SharedPreferencesUtils.getInstance().getStringValue(Constant.USER_ADDRESS));
        jsonUser.addProperty(Constant.USER_AVATAR,urlAvatar);
        jsonUser.addProperty(Constant.USER_HEIGHT,hegiht);
        jsonUser.addProperty(Constant.USER_WEIGHT,weight);
        Log.e("UpdateInfoPresenter", "updateProfile: "+jsonUser.toString());
        UserModel.getintance().updateInfoUser(url, jsonUser.toString(), "V5BDuS4BFpiMjgfAZBrkQpb2FUFGX8owdAxh9G77o9dE6kXfyuhPss7M5NxyNTgKwxns6SMStxlVERmOH1n05RTvbOUOC0TBWMKR", new ResponseHandle<RESP_Basic>(RESP_Basic.class) {
            @Override
            public void onSuccess(RESP_Basic obj) {
                view.onUpdateProfileSuccess();
                Log.e("TEST", "onSuccess: ");
                SharedPreferencesUtils.getInstance().putStringValue(Constant.USER_FULL_NAME,name);
                SharedPreferencesUtils.getInstance().putLongValue(Constant.USER_BIRTHDAY,birthDay);
                SharedPreferencesUtils.getInstance().putFloatValue(Constant.USER_HEIGHT,(float) hegiht);
                SharedPreferencesUtils.getInstance().putFloatValue(Constant.USER_WEIGHT,(float) weight);
            }
            @Override
            public void onError(Error error) {
                Log.e("ERR", "onError: "+error.getCode());
                switch (error.getCode()) {

                    case 2:
                        view.showToast("Session không hợp lệ");
                        break;
                    case -1:
                        view.showToast("Lỗi hệ thống");
                }
            }
        });
    }

    public void getProfile() {
        RESP_User user = SharedPreferencesUtils.getInstance().getUser();
        Log.e("USER", "getProfile: " +user.toString());;
        view.onLoadProfileSuccess(user.getFullname(),user.getBirthday(),user.getHeight(),user.getWeight(),user.getAvatar());
    }
}
