package com.xtelsolution.xmec.xmec.views.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.sdk.utils.Utils;

public class DetailControlDiseaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_control_disease);
        Utils.showToast(this, getIntent().getStringExtra(Constant.INTENT_ACTION_DETAIL_CONTROL_DISEASE));
    }
}
