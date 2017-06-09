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
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.model.RESP_User;

import java.util.Calendar;

/**
 * Created by Admin on 5/31/2017.
 */

public class DialogUtil {
    public static Dialog DiaLodAddFriend(Context context) {
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
                btnSend.setProgress(50);
                searchUser(numberPhone.getText().toString(), new SearchUserListener() {
                    @Override
                    public void success(RESP_User user) {
                        btnSend.setEnabled(true);
                        numberPhone.setEnabled(false);
                        btnSend.setProgress(100);
                        btnSend.setText(R.string.add_user);
                        layoutSearch.setVisibility(View.GONE);
                        layoutUser.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void error() {
                        btnSend.setProgress(-1);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                btnSend.setProgress(0);
                                btnSend.setEnabled(true);
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

    interface SearchUserListener {
        void success(RESP_User user);

        void error();
    }

    private static void searchUser(String phone, final SearchUserListener listener) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (Calendar.getInstance().getTimeInMillis() % 2 == 0) {
                    listener.success(null);
                } else {
                    listener.error();
                }
            }
        }, 2000);
    }

}
