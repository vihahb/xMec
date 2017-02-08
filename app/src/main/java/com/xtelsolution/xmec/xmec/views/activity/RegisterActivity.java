package com.xtelsolution.xmec.xmec.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.callback.CallbackLisenerRegister;
import com.xtel.nipservicesdk.callback.CallbackListenerActive;
import com.xtel.nipservicesdk.callback.CallbackListenerReactive;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Reactive;
import com.xtel.nipservicesdk.model.entity.RESP_Register;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtelsolution.xmec.R;

public class RegisterActivity extends BasicActivity {
    private CallbackManager callbackManager;
    private EditText etPhone, etPassword, etRePassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        callbackManager = CallbackManager.create(this);
        initUI();

    }

    private void initUI() {
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPassword = (EditText) findViewById(R.id.etPassword);
        etRePassword = (EditText) findViewById(R.id.etRePassword);
        findViewById(R.id.btnCreateAccount).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerAccount();
            }
        });
    }

    private void registerAccount() {
        final String phone = etPhone.getText().toString();
        String password = etPassword.getText().toString();
        String rePassword = etRePassword.getText().toString();
        if (phone.length() < 9) {
            etPhone.setError("Số điện thoại không khả dụng");
        } else if (password.length() < 6) {
            etPassword.setError("Mật khẩu quá yếu");
        } else if (!password.equals(rePassword)) {
            etRePassword.setError("Mật khẩu chưa trùng khớp");
        } else {
            callbackManager.registerNipService(phone, password, null, true, new CallbackLisenerRegister() {
                @Override
                public void onSuccess(RESP_Register register) {
                    activeAccount(phone);
                }

                @Override
                public void onError(Error error) {
                    if (error.getCode() == 103) {
                        showToast("Tài khoản đã tồn tại");
                        activeAccount(phone);
                    }
                }
            });
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        callbackManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    public void activeAccount(String phone) {
        final Intent intent = new Intent(RegisterActivity.this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.CODE);
        configurationBuilder.setInitialPhoneNumber(new PhoneNumber("+84", phone, null));
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        startActivityForResult(intent, 99);
    }

    @Override
    protected void onActivityResult(
            final int requestCode,
            final int resultCode,
            final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 99) {
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (loginResult.getError() != null) {
                showLog(loginResult.getError().toString());
            } else if (loginResult.wasCancelled()) {
                showLog("AccountKit was Cancelled");
            } else {
                if (loginResult.getAccessToken() != null) {
                    showLog("Access Token: " + loginResult.getAccessToken());
                } else {
                    sendAuthenzationCode(loginResult.getAuthorizationCode());
                }
            }
        }
    }

    private void sendAuthenzationCode(final String code) {
        callbackManager.reactiveNipAccount(etPhone.getText().toString(), true, new CallbackListenerReactive() {
            @Override
            public void onSuccess(RESP_Reactive reactive) {
                Log.d("MY_TAG", JsonHelper.toJson(reactive));
                callbackManager.activeNipAccount(code, "PHONE-NUMBER", new CallbackListenerActive() {
                    @Override
                    public void onSuccess() {
                        showToast("Xác nhận tài khoản thành công");
                        startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(Error error) {
                        showLog(JsonHelper.toJson(error));
                    }
                });
            }

            @Override
            public void onError(Error error) {
                showLog(JsonHelper.toJson(error));
            }
        });
    }
}
