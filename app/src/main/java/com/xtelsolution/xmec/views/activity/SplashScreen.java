package com.xtelsolution.xmec.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.NipApplication;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.SharedPreferencesUtils;

public class SplashScreen extends AppCompatActivity {
    private static final String TAG = "SplashScreen";
    private static final int SPLASH_TIME_OUT = 2000;
    private Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        NipApplication.context = this;
        init();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(i);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        finish();
                    }
                }, 1000);
            }
        }, SPLASH_TIME_OUT);

    }

    private void init() {
        if (LoginManager.getCurrentSession() == null) {
            i = new Intent(SplashScreen.this, HomeActivity.class);
            SharedPreferencesUtils.getInstance().setLogout();
        } else {
            i = new Intent(SplashScreen.this, HomeActivity.class);
            SharedPreferencesUtils.getInstance().setLogined();
            xLog.e(TAG, "init: " + Constant.LOGPHI + LoginManager.getCurrentSession());
        }
    }
}
