package com.xtelsolution.xmec.xmec.views.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.xtelsolution.xmec.R;

/**
 * Created by phimau on 1/17/2017.
 */

public class LoginActivity extends AppCompatActivity {
    private ImageView imgLogo;
    private TextView tvSignUp;
    private Button btnLog;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
        imgLogo.setTransitionName("logo");
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
            }
        });
        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this,HomeActivity.class));
            }
        });
    }

    private void init() {
        imgLogo = (ImageView) findViewById(R.id.img_logo);
        tvSignUp = (TextView) findViewById(R.id.tv_sign_up);
        btnLog = (Button) findViewById(R.id.btnLogin);
    }

}
