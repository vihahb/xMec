package com.xtelsolution.xmec.xmec.views.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.xtelsolution.xmec.xmec.views.inf.BaseView;

/**
 * Created by HUNGNT on 1/17/2017.
 */

public class BasicActivity extends AppCompatActivity implements BaseView{
    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLog(String msg) {
        Log.d("MY_TAG",msg);
    }
}
