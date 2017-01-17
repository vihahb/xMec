package com.xtelsolution.xmec;

import android.content.Intent;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class SplashScreen extends AppCompatActivity {
    private ImageView imgLogo;
    private static final int SPLASH_TIME_OUT =2000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, MainActivity.class);
                Pair<View,String> pair = new Pair<View, String>(imgLogo,"logo");
                ActivityOptionsCompat optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(SplashScreen.this,pair);
                startActivity(i,optionsCompat.toBundle());
                finish();
            }
        }, SPLASH_TIME_OUT);
    }

    private void init() {
        imgLogo = (ImageView) findViewById(R.id.img_logo);
    }


}
