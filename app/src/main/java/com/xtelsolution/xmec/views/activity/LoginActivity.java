package com.xtelsolution.xmec.views.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.content.IntentCompat;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.dd.processbutton.iml.ActionProcessButton;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.PhoneNumber;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.firebase.iid.FirebaseInstanceId;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.callback.CallbackListenerActive;
import com.xtel.nipservicesdk.callback.CallbackListenerReactive;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtel.nipservicesdk.model.entity.RESP_Reactive;
import com.xtel.nipservicesdk.utils.JsonHelper;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.SharedPreferencesUtils;
import com.xtelsolution.xmec.presenter.LoginPresenter;
import com.xtelsolution.xmec.views.activity.inf.ILoginActivity;
import com.xtelsolution.xmec.views.widget.KeyboardDetectorRelativeLayout;

import java.util.Collections;

/**
 * Created by phimau on 1/17/2017.
 */

public class LoginActivity extends BasicActivity implements ILoginActivity {
    private static final String TAG = "LoginActivity";
    LoginPresenter presenter;
    private ImageView imgLogo;
    private TextView tvSignUp, tv_ForgotPassword;
    private EditText etPhone, etPassword;
    private ActionProcessButton btnLoginbyPhone;
    private Button btnLoginByFB;
    private KeyboardDetectorRelativeLayout content;
    private LinearLayout box2;
    private Handler handler;
    private CallbackManager callbackManager;
    private com.facebook.CallbackManager fbCallbackManager;

    public static boolean checkApiVersion() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }
        return false;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        handler = new Handler();
        setUi(findViewById(R.id.lg_content));
        presenter = new LoginPresenter(this);
        init();
//        animation();
//        if (checkApiVersion())
//            imgLogo.setTransitionName("logo");


        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });

        tv_ForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, ResetPasswordActivity.class));
            }
        });
        btnLoginbyPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onPhoneLogin();
            }
        });
        btnLoginByFB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onFacebookLogin();
            }
        });

    }

    private void init() {
        callbackManager = CallbackManager.create(this);
        initFacebookSdk();
        AccountKit.initialize(getApplicationContext());
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        tvSignUp = (TextView) findViewById(R.id.tv_sign_up);
        tv_ForgotPassword = (TextView) findViewById(R.id.tv_forgot_pw);
        btnLoginbyPhone = (ActionProcessButton) findViewById(R.id.btnLogin);
        btnLoginByFB = (Button) findViewById(R.id.btnLoginByFB);
        box2 = (LinearLayout) findViewById(R.id.LoginBox2);
        etPhone = (EditText) findViewById(R.id.etPhone);
        etPassword = (EditText) findViewById(R.id.etPassword);
        content = (KeyboardDetectorRelativeLayout) findViewById(R.id.lg_content);
        content.addKeyboardStateChangedListener(new KeyboardDetectorRelativeLayout.IKeyboardChanged() {
            @Override
            public void onKeyboardShown() {
//                box2.setVisibility(View.GONE);
//                imgLogo.setVisibility(View.GONE);
            }

            @Override
            public void onKeyboardHidden() {
//                box2.setVisibility(View.VISIBLE);
//                imgLogo.setVisibility(View.VISIBLE);
            }
        });
    }

    private void initFacebookSdk() {
        FacebookSdk.sdkInitialize(this.getApplicationContext());
        fbCallbackManager = com.facebook.CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(fbCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        showLog(TAG, "initFacebookSdk: onSuccess: " + loginResult.getAccessToken().getToken());
                        callbackManager.LoginFaceook(loginResult.getAccessToken().getToken(), new CallbacListener() {
                            @Override
                            public void onSuccess(RESP_Login success) {
                                showLog(TAG, "initFacebookSdk: onSuccess: " + JsonHelper.toJson(success));

                                SharedPreferencesUtils.getInstance().setLogined();
                                startActivityFinishAll();
                            }

                            @Override
                            public void onError(Error error) {
                                showToast(JsonHelper.toJson(error));
                            }
                        });
                    }

                    @Override
                    public void onCancel() {
                        showLog(TAG, "initFacebookSdk: onCancel: " + "Login Cancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        showLog(TAG, "initFacebookSdk: onError: " + exception.getMessage());
                    }
                });
    }

    public void animation() {
        final ScrollView LoginBox = (ScrollView) findViewById(R.id.LoginBox);
        LoginBox.setVisibility(View.GONE);

        Animation animTranslate = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.translate);
        animTranslate.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            @Override
            public void onAnimationEnd(Animation arg0) {
                LoginBox.setVisibility(View.VISIBLE);
                Animation animFade = AnimationUtils.loadAnimation(LoginActivity.this, R.anim.fade);
                LoginBox.startAnimation(animFade);
            }
        });
        imgLogo.startAnimation(animTranslate);
    }

    private void setDisenabledView() {
        etPassword.setEnabled(false);
        etPhone.setEnabled(false);
        btnLoginbyPhone.setEnabled(false);
        tvSignUp.setEnabled(false);
    }

    private void setEnabledView() {
        etPassword.setEnabled(true);
        etPhone.setEnabled(true);
        btnLoginbyPhone.setEnabled(true);
        tvSignUp.setEnabled(true);
    }

    private void onPhoneLogin() {
        if (etPhone.getText().toString().trim().length() <= 9) {
            etPhone.setError("Số điện thoại không hợp lệ");
        } else if (etPassword.getText().toString().length() == 0) {
            etPassword.setError("Mật khẩu trống");
        } else {
            setDisenabledView();
            btnLoginbyPhone.setProgress(50);
            callbackManager.LoginNipAcc(etPhone.getText().toString(), etPassword.getText().toString(), true, new CallbacListener() {
                @Override
                public void onSuccess(RESP_Login success) {
                    String tokenFCM = FirebaseInstanceId.getInstance().getToken();
                    Log.e("tokenFCM", "onSuccess: " + tokenFCM);
                    presenter.updateFcmToken(tokenFCM);
                    Log.e("Session", "onSuccess: " + JsonHelper.toJson(success));
                    xLog.e(TAG, "onPhoneLogin: onSuccess: " + Constant.LOGPHI + success.getSession());
                }

                @Override
                public void onError(final Error error) {
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setEnabledView();
                            btnLoginbyPhone.setProgress(-1);
                            showLog(TAG, "onPhoneLogin: onError: " + JsonHelper.toJson(error));
                            int errorCode = error.getCode();
                            switch (errorCode) {
                                case 111:
                                    showToast("SĐT hoặc mật khẩu sai");
                                    break;
                                case 112:
                                    showToast("Tài khoản chưa được kích hoạt");
                                    activeAccount(etPhone.getText().toString());
                                    break;
                                default:
                                    showToast("Đăng nhập thất bại");
                                    break;
                            }
                        }
                    }, 2000);
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            btnLoginbyPhone.setProgress(0);
                        }
                    }, 4000);
                }
            });
        }
    }

    private void startActivityFinishAll() {
        Intent intents = new Intent(LoginActivity.this, HomeActivity.class);
        intents.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | IntentCompat.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intents);
        finish();
    }

    private void onFacebookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Collections.singletonList("email"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fbCallbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99) {
            AccountKitLoginResult loginResult = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);
            if (loginResult.getError() != null) {
                showLog(TAG, "onActivityResult: " + loginResult.getError().toString());
            } else if (loginResult.wasCancelled()) {
                showLog(TAG, "onActivityResult: " + "AccountKit was Cancelled");
            } else {
                if (loginResult.getAccessToken() != null) {
                    showLog(TAG, "onActivityResult: " + "Access Token: " + loginResult.getAccessToken());
                } else {
                    sendAuthenzationCode(loginResult.getAuthorizationCode());
                }
            }
        }
    }

    public void activeAccount(String phone) {
        final Intent intent = new Intent(LoginActivity.this, AccountKitActivity.class);
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

    private void sendAuthenzationCode(final String code) {
        callbackManager.reactiveNipAccount(etPhone.getText().toString(), true, new CallbackListenerReactive() {
            @Override
            public void onSuccess(RESP_Reactive reactive) {
                Log.d("MY_TAG", com.xtel.nipservicesdk.utils.JsonHelper.toJson(reactive));
                callbackManager.activeNipAccount(code, "PHONE-NUMBER", new CallbackListenerActive() {
                    @Override
                    public void onSuccess() {
                        showToast("Xác nhận tài khoản thành công");
                        startActivity(new Intent(LoginActivity.this, LoginActivity.class));
                        finish();
                    }

                    @Override
                    public void onError(Error error) {
                        showLog(TAG, "sendAuthenzationCode: onError: " + JsonHelper.toJson(error));
                    }
                });
            }

            @Override
            public void onError(Error error) {
                showLog(TAG, "sendAuthenzationCode: onError: " + JsonHelper.toJson(error));
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        callbackManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    public void updateTokenSuccess() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferencesUtils.getInstance().setLogined();
                btnLoginbyPhone.setProgress(100);
                startActivityFinishAll();
            }
        }, 1000);
    }

    @Override
    public void updateTokenError() {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferencesUtils.getInstance().setLogined();
                btnLoginbyPhone.setProgress(100);
                startActivityFinishAll();
            }
        }, 1000);
    }
}
