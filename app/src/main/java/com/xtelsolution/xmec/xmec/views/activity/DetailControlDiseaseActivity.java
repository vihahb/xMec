package com.xtelsolution.xmec.xmec.views.activity;

import android.os.Bundle;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;

public class DetailControlDiseaseActivity extends BasicActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_control_disease);
        showToast(getIntent().getStringExtra(Constant.INTENT_ACTION_DETAIL_CONTROL_DISEASE));
    }
}
