package com.xtelsolution.xmec.views.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.callback.CallbackListenerReset;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.presenter.ResetPasswordPresenter;
import com.xtelsolution.xmec.views.inf.IResetPasswordActivityView;

public class ResetPasswordActivity extends AppCompatActivity implements IResetPasswordActivityView, View.OnClickListener {

    private EditText edtPhone, edtNewPassword, edtReNewPassword;
    private TextView tv_alreadyAccount;
    private ActionProcessButton btnRecoveryAccount;
    private boolean isValidated = false;
    private ResetPasswordPresenter presenter;
    private CallbackManager callbackManager;
    private String phone;
    private String TAG = "ResetPasswordActivity";
    private String authorizationCode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password);
        presenter = new ResetPasswordPresenter(this);
        callbackManager = CallbackManager.create(this);
        initView();
        setDefault();
    }

    private void initView() {
        edtPhone = (EditText) findViewById(R.id.etPhone);
        edtNewPassword = (EditText) findViewById(R.id.etNewPassword);
        edtReNewPassword = (EditText) findViewById(R.id.etReNewPassword);
        btnRecoveryAccount = (ActionProcessButton) findViewById(R.id.btnRecoveryPassword);
        tv_alreadyAccount = (TextView) findViewById(R.id.tv_already_account);
        btnRecoveryAccount.setOnClickListener(this);
        tv_alreadyAccount.setOnClickListener(this);
    }

    private void setDefault() {
        edtNewPassword.setEnabled(false);
        edtReNewPassword.setEnabled(false);
        btnRecoveryAccount.setText(R.string.title_validate_phone);
    }

    private void setAccessPassword() {
        edtPhone.setText(phone);
        edtPhone.setEnabled(false);
        edtNewPassword.setEnabled(true);
        edtReNewPassword.setEnabled(true);
        btnRecoveryAccount.setProgress(50);
        isValidated = true;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnRecoveryPassword:
                if (!isValidated) {
                    if (validatePhone()) {
                        phone = edtPhone.getText().toString();
                        initAccountKit();
                    }
                } else {
                    if (validatePassword()) {
                        resetPassword(edtNewPassword.getText().toString());
                    }
                }
                break;

            case R.id.tv_already_account:
                startActivity(new Intent(ResetPasswordActivity.this, LoginActivity.class));
                finish();
                break;
        }
    }

    private boolean validatePassword() {
        if (edtNewPassword.getText().toString().length() < 6) {
            edtNewPassword.setError("Mật khẩu quá yếu");
            edtNewPassword.requestFocus();
            return false;
        }
        if (!edtNewPassword.getText().toString().equals(edtReNewPassword.getText().toString())) {
            edtReNewPassword.setError("Mật khẩu chưa trùng khớp");
            edtReNewPassword.requestFocus();
            return false;
        }
        return true;
    }

    private boolean validatePhone() {
        if (TextUtils.isEmpty(edtPhone.getText().toString())) {
            showToast(getString(R.string.error_phone_number_null));
            edtPhone.setError(getString(R.string.error_phone_number_null));
            edtPhone.requestFocus();
            return false;
        } else if (edtPhone.getText().toString().length() < 10 &&
                edtPhone.getText().toString().length() > 11) {
            edtPhone.setError(getString(R.string.error_phone_number_wrong));
            edtPhone.requestFocus();
            return false;
        }
        btnRecoveryAccount.setProgress(50);
        return true;
    }

    private void initAccountKit() {
        final Intent intent = new Intent(ResetPasswordActivity.this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder configurationBuilder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(
                        LoginType.PHONE,
                        AccountKitActivity.ResponseType.CODE);
        configurationBuilder.setInitialPhoneNumber(new PhoneNumber("+84", phone, null));
        intent.putExtra(
                AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,
                configurationBuilder.build());
        btnRecoveryAccount.setProgress(100);
        startActivityForResult(intent, 101);
    }

    public void resetPassword(String password) {
        callbackManager.AdapterReset(null, password, authorizationCode, new CallbackListenerReset() {
            @Override
            public void onSuccess() {
                btnRecoveryAccount.setProgress(100);
                btnRecoveryAccount.setText("Đổi mật khẩu thành công");
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1500);
            }

            @Override
            public void onError(Error error) {
                showToast(error.getMessage());
            }
        });
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showLog(String TAG, String msg) {

    }

    @Override
    public void showProgressDialog(String title) {

    }

    @Override
    public void dismissProgressDialog() {

    }

    @Override
    public void recoveryPasswordSuccess(String mes) {

    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        callbackManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101) {
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (loginResult.getError() != null) {
                showLog(TAG, "onActivityResult: " + loginResult.getError().toString());
            } else if (loginResult.wasCancelled()) {
                showLog(TAG, "onActivityResult: " + "AccountKit was Cancelled");
            } else {
                if (loginResult.getAccessToken() != null) {
                    showLog(TAG, "onActivityResult: " + "Access Token: " + loginResult.getAccessToken());
                } else {
//                    sendAuthenzationCode(loginResult.getAuthorizationCode());
                    authorizationCode = loginResult.getAuthorizationCode();
                    Log.e("Authorization code", authorizationCode);
                    btnRecoveryAccount.setText(R.string.title_change_phone);
                    setAccessPassword();
                }
            }
        }
    }

}
