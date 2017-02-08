package com.xtelsolution.xmec.xmec.views.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.accountkit.AccountKit;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.sdk.utils.JsonHelper;
import com.xtelsolution.xmec.xmec.views.widget.KeyboardDetectorRelativeLayout;

import java.util.Collections;

/**
 * Created by phimau on 1/17/2017.
 */

public class LoginActivity extends BasicActivity {
    private ImageView imgLogo;
    private TextView tvSignUp;
    private EditText etPhone, etPassword;
    private Button btnLoginbyPhone, btnLoginByFB;
    private KeyboardDetectorRelativeLayout content;
    private LinearLayout box2;
    private CallbackManager callbackManager;
    private com.facebook.CallbackManager fbCallbackManager;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        init();
        animation();
        if (checkApiVersion())
            imgLogo.setTransitionName("logo");


        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
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
        btnLoginbyPhone = (Button) findViewById(R.id.btnLogin);
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
                        //showLog(JsonHelper.toJson(loginResult));
                        showLog(loginResult.getAccessToken().getToken());
                        callbackManager.LoginFaceook(loginResult.getAccessToken().getToken(), new CallbacListener() {
                            @Override
                            public void onSuccess(RESP_Login success) {
                                showLog(JsonHelper.toJson(success));
                                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                                finish();
                            }

                            @Override
                            public void onError(Error error) {
                                showToast(JsonHelper.toJson(error));
                            }
                        });
                    }

                    @Override
                    public void onCancel() {
                        showLog("Login Cancel");
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        showLog(exception.getMessage());
                    }
                });
    }

    public static boolean checkApiVersion() {
        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
        if (currentapiVersion >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            return true;
        }
        return false;
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

    private void onPhoneLogin() {
        callbackManager.LoginNipAcc(etPhone.getText().toString(), etPassword.getText().toString(), true, new CallbacListener() {
            @Override
            public void onSuccess(RESP_Login success) {
                showLog(JsonHelper.toJson(success));
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }

            @Override
            public void onError(Error error) {
                showToast(error.getMessage());
            }
        });
    }

    private void onFacebookLogin() {
        LoginManager.getInstance().logInWithReadPermissions(LoginActivity.this, Collections.singletonList("email"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        fbCallbackManager.onActivityResult(requestCode, resultCode, data);
    }
}
