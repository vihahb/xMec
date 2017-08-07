package com.xtelsolution.xmec.dialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.dd.processbutton.iml.ActionProcessButton;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;
import com.koushikdutta.ion.Response;
import com.squareup.picasso.Picasso;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.commons.Cts;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_None;
import com.xtelsolution.xmec.MyApplication;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.model.RESP_User;
import com.xtelsolution.xmec.model.UserModel;
import com.xtelsolution.xmec.views.widget.RoundedImageView;

/**
 * Created by Admin on 5/31/2017
 */

public class DialogAddFriend {
    static String TAG = "DialogAddFriend";
    static int friend_id = -1;

    public static Dialog DiaLodAddFriend(final Context context) {
        final Dialog dialog = new Dialog(context, R.style.Theme_Transparent);
        dialog.setContentView(R.layout.dialog_add_friend);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

//        final TextView editMessage = (TextView) dialog.findViewById(R.id.numberPhone);
        final EditText numberPhone = (EditText) dialog.findViewById(R.id.numberPhone);
        final ActionProcessButton btnSend = (ActionProcessButton) dialog.findViewById(R.id.btnSend);
        final ActionProcessButton btnAddUser = (ActionProcessButton) dialog.findViewById(R.id.btnAddUser);
        final LinearLayout layoutUser = (LinearLayout) dialog.findViewById(R.id.layoutUser);
        final LinearLayout layoutSearch = (LinearLayout) dialog.findViewById(R.id.layoutSearch);
        final TextView tvName = (TextView) layoutUser.findViewById(R.id.tvName);
        final RoundedImageView avatarUser = (RoundedImageView) layoutUser.findViewById(R.id.avatarUser);
        layoutUser.setVisibility(View.GONE);
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
                if (validPhone()) {
                    Log.e("onClick: ", btnSend.getText().toString());
                    btnSend.setEnabled(false);
                    numberPhone.setEnabled(false);
                    btnSend.setProgress(50);

                    String phone_number = getNumberPhone(numberPhone);
                    Log.e("Phone ", phone_number);

                    searchUser(context, phone_number, new ResponseHandle<RESP_User>(RESP_User.class) {
                        @Override
                        public void onSuccess(RESP_User obj) {
                            btnSend.setVisibility(View.GONE);
                            btnAddUser.setEnabled(true);
                            numberPhone.setEnabled(true);
                            btnAddUser.setProgress(0);
                            btnAddUser.setText(R.string.add_user);
                            layoutSearch.setVisibility(View.GONE);
                            tvName.setText("Họ tên: " + obj.getFullname());
                            if (!TextUtils.isEmpty(obj.getAvatar())) {
                                Picasso.with(MyApplication.context)
                                        .load(obj.getAvatar())
                                        .fit()
                                        .error(R.mipmap.avatar)
                                        .into(avatarUser);
                            }
                            friend_id = obj.getUid();
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
            }

            private boolean validPhone() {
                if (numberPhone.getText().toString().length() < 10) {
                    showToast("Sai định sạng số điện thoại!");
                    return false;
                } else if (numberPhone.getText().toString().length() > 11) {
                    showToast("Sai định sạng số điện thoại!");
                    return false;
                }
                return true;
            }
        });

        btnAddUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addUser(dialog, context, friend_id);
            }
        });

        return dialog;
    }


    private static void searchUser(Context context, String phone, final ResponseHandle<RESP_User> listener) {

        Log.e(TAG, "url searchUser: " + Constant.SEARCH_FRIEND + phone);
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

    private static void addUser(final Dialog dialog, final Context context, int friend_id) {
        String url_add = Constant.ADD_FRIEND + friend_id;
        Log.e(TAG, "url addUser: " + url_add);
        Log.e(TAG, "addUser: " + LoginManager.getCurrentSession());

        UserModel.getintance().addFriend(url_add, LoginManager.getCurrentSession(), new ResponseHandle<RESP_None>(RESP_None.class) {
            @Override
            public void onSuccess(RESP_None obj) {
                Toast.makeText(context, "Thêm thành viên thành công!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
            }

            @Override
            public void onError(Error error) {
                if (error.getCode() == 4) {
                    showToast("Bạn đã gửi yêu cầu kết bạn đến thành viên này rồi.");
                } else {
                    if (!TextUtils.isEmpty(error.getMessage())) {
                        Toast.makeText(context, "Có lỗi xảy ra \n " + error.getMessage() + "!", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Có lỗi xảy ra!", Toast.LENGTH_SHORT).show();
                    }
                }
                dialog.dismiss();
            }
        });

//        Ion.with(context).load("POST", url_add)
//                .setHeader(Cts.SESSION, LoginManager.getCurrentSession())
//                .asJsonObject().withResponse()
//                .setCallback(new FutureCallback<Response<JsonObject>>() {
//                    @Override
//                    public void onCompleted(Exception e, Response<JsonObject> result) {
//                        if (e == null) {
//                            if (result.getResult() == null) {
////                                Log.e(TAG, "onCompleted: " + result.getResult().toString());
//                                listener.onSuccess("OK");
//
//                            } else {
//                                listener.onError(new Error(101, null, null));
//                            }
//                        } else {
//                            listener.onError(new Error(101, null, null));
//                        }
//                    }
//                });
    }

    public static void showToast(String message) {
        Toast.makeText(MyApplication.context, message, Toast.LENGTH_SHORT).show();
    }

    private static String getNumberPhone(EditText edt_Phone) {
        String phone_number = "";
        if (edt_Phone.getText().toString().startsWith("0")) {
            phone_number = edt_Phone.getText().toString().replaceFirst("0", "84");
        } else if (edt_Phone.getText().toString().startsWith("+84")) {
            phone_number = edt_Phone.getText().toString().replace("+84", "84");
        }
        return phone_number;
    }
}
