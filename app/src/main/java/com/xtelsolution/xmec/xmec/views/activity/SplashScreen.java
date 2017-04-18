package com.xtelsolution.xmec.xmec.views.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.NipApplication;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.SharedPreferencesUtils;

public class SplashScreen extends AppCompatActivity {
    private static final String TAG = "SplashScreen";
    private ImageView imgLogo;
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
                Pair<View, String> pair = new Pair<View, String>(imgLogo, "logo");
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SplashScreen.this, pair);
                startActivity(i, optionsCompat.toBundle());
                overridePendingTransition(R.anim.right_in, R.anim.left_out);

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
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        if (LoginManager.getCurrentSession() == null) {
            i = new Intent(SplashScreen.this, LoginActivity.class);
        } else {
            i = new Intent(SplashScreen.this, HomeActivity.class);
//            SharedPreferencesUtils.getInstance().setLogined();
            xLog.e(TAG, "init: " + Constant.LOGPHI + LoginManager.getCurrentSession());
        }
    }
}
