package com.xtelsolution.xmec.views.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.xtelsolution.xmec.R;

public class ConfirmOPTActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_opt);
        getSupportActionBar().setTitle("Xác nhận");
    }
}
