package com.xtelsolution.xmec.views.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.xtelsolution.xmec.R;

public class DetailBodyPart extends AppCompatActivity {
    private TextView tvTest;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detial_body_part);
        tvTest = (TextView) findViewById(R.id.tv_test);
        String nameBodyPart =getIntent().getStringExtra("part");
        tvTest.setText(nameBodyPart);
    }
}
