package com.xtelsolution.xmec.xmec.views.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.xmec.views.widget.KeyboardDetectorRelativeLayout;

/**
 * Created by phimau on 1/17/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private ImageView imgLogo;
    private TextView tvSignUp;
    private Button btnLog;
    private KeyboardDetectorRelativeLayout content;
    private LinearLayout box2;

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
                finish();
            }
        });
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, HomeActivity.class));
                finish();
            }
        });

    }

    private void init() {
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        tvSignUp = (TextView) findViewById(R.id.tv_sign_up);
        btnLog = (Button) findViewById(R.id.btnLogin);
        box2 = (LinearLayout) findViewById(R.id.LoginBox2);
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

}
