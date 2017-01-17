package com.xtelsolution.xmec;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        getSupportActionBar().setTitle("Đăng ký tài khoản");
        startActivity(new Intent(RegisterActivity.this,ConfirmOPTActivity.class));
    }
}
