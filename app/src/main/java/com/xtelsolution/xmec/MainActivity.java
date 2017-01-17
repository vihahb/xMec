package com.xtelsolution.xmec;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    private ImageView imgLogo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
        imgLogo.setTransitionName("logo");
    }

    private void init() {
        imgLogo = (ImageView) findViewById(R.id.img_logo);
    }
}
