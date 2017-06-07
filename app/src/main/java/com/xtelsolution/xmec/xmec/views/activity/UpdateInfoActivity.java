package com.xtelsolution.xmec.xmec.views.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.model.RESP_User;
import com.xtelsolution.xmec.model.SharedPreferencesUtils;

import agency.tango.android.avatarview.views.AvatarView;

public class UpdateInfoActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView etName;
    private TextView etBirthday;
    private TextView etHeight;
    private TextView etWeight;
    private Button btnUpdateInfo;
    private AvatarView btnUpdateAvatar;
    private RESP_User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_info);
        initView();
        initControl();
        user= SharedPreferencesUtils.getInstance().getUser();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        DialogFragment datePicker = new DatePickerFragment();
        Log.d("datepicker", "onCreate: ");

    }

    private void initControl() {
        btnUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = etName.getText().toString();
                double height = Double.valueOf(etHeight.getText().toString());
                double weight = Double.valueOf(etWeight.getText().toString());

            }
        });
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        etBirthday = (TextView) findViewById(R.id.et_birth_day);
        etName = (TextView) findViewById(R.id.et_name);
        etHeight = (TextView) findViewById(R.id.et_height);
        etWeight = (TextView) findViewById(R.id.et_weight);
        btnUpdateInfo = (Button) findViewById(R.id.btn_update_info);
        btnUpdateAvatar = (AvatarView) findViewById(R.id.btn_update_avatar);
    }

    private void showDatePicker(){
//        DialogFragment datePicker = new DatePickerFragment();

    }

}
