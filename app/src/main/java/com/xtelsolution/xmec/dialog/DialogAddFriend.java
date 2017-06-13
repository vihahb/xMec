package com.xtelsolution.xmec.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.commons.Cts;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;
import com.xtel.nipservicesdk.model.entity.RESP_None;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.model.RESP_User;

import java.io.IOException;
import java.util.Calendar;

/**
 * Created by Admin on 5/31/2017.
 */

public class DialogAddFriend {
    static String TAG = "DialogAddFriend";

    public static Dialog DiaLodAddFriend(final Context context) {
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add_friend);

//        final TextView editMessage = (TextView) dialog.findViewById(R.id.numberPhone);
        final EditText numberPhone = (EditText) dialog.findViewById(R.id.numberPhone);
        final ActionProcessButton btnSend = (ActionProcessButton) dialog.findViewById(R.id.btnSend);
        final ActionProcessButton btnAddUser = (ActionProcessButton) dialog.findViewById(R.id.btnAddUser);
        final LinearLayout layoutUser = (LinearLayout) dialog.findViewById(R.id.layoutUser);
        final LinearLayout layoutSearch = (LinearLayout) dialog.findViewById(R.id.layoutSearch);
        btnSend.setEnabled(false);
        numberPhone.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if (s.length() > 8) {
                    btnSend.setEnabled(true);
                } else btnSend.setEnabled(false);
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("onClick: ", btnSend.getText().toString());
                btnSend.setEnabled(false);
                numberPhone.setEnabled(false);
                btnSend.setProgress(50);
                searchUser(context, numberPhone.getText().toString(), new ResponseHandle<RESP_User>(RESP_User.class) {
                    @Override
                    public void onSuccess(RESP_User obj) {
                        btnSend.setEnabled(true);
                        numberPhone.setEnabled(true);
                        btnSend.setProgress(100);
                        btnSend.setText(R.string.add_user);
                        layoutSearch.setVisibility(View.GONE);
                        layoutUser.setVisibility(View.VISIBLE);
                        Log.e(TAG, "onSuccess: " + obj);
                    }

                    @Override
                    public void onError(Error error) {
                        btnSend.setProgress(-1);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                btnSend.setProgress(0);
                                btnSend.setEnabled(true);
                                numberPhone.setEnabled(true);
                            }
                        }, 1000);
                        btnSend.setEnabled(true);
                        numberPhone.setEnabled(true);
                    }
                });

            }
        });

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return dialog;
    }


    private static void searchUser(Context context, String phone, final ResponseHandle<RESP_User> listener) {

        Log.e(TAG, "searchUser: " + Constant.SEARCH_FRIEND + phone);
        Log.e(TAG, "searchUser: " + LoginManager.getCurrentSession());

        Ion.with(context).load(Constant.SEARCH_FRIEND + phone)
                .setHeader(Cts.SESSION, LoginManager.getCurrentSession())
                .asJsonObject().withResponse()
                .setCallback(new FutureCallback<Response<JsonObject>>() {
                    @Override
                    public void onCompleted(Exception e, Response<JsonObject> result) {
                        if (e == null) {
                            if (result.getResult() != null) {
                                Log.e(TAG, "onCompleted: " + result.getResult().toString());
                                listener.onSuccess(result.getResult().toString());
                            } else {
                                listener.onError(new Error(101, null, null));
                            }
                        } else {
                            listener.onError(new Error(101, null, null));
                        }
                    }
                });
    }

}
