package com.xtelsolution.xmec.xmec.views.activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethod;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.xmec.views.inf.BaseView;

/**
 * Created by HUNGNT on 1/17/2017.
 */

public class BasicActivity extends AppCompatActivity implements BaseView {


    private ProgressDialog progressDialog;

    @Override
    public void onCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
        initProgressDialog();
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLog(String TAG, String msg) {
        xLog.d(TAG, msg);
    }

    @Override
    public void showProgressDialog(String title) {
        if (progressDialog == null)
            initProgressDialog();
        progressDialog.setMessage(title);
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if (progressDialog == null)
            initProgressDialog();
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    protected void setImage(ImageView img, String url) {
        if (url == null || url.trim().length() == 0) {
            img.setImageResource(R.mipmap.avatar);
        } else {
            Picasso.with(this)
                    .load(url)
                    .placeholder(R.mipmap.avatar)
                    .error(R.mipmap.avatar)
                    .into(img);
        }
    }

    public void showAleartDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("");
        builder.setMessage("");
        builder.setNegativeButton("OKE", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
    }
    protected void hideKeyBoard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(),0);
    }
    protected void setUi(View view){
        if (!(view instanceof EditText)){
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    hideKeyBoard();
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup){
            for (int i=0;i<((ViewGroup) view).getChildCount();i++){
                setUi(((ViewGroup)view).getChildAt(i));
            }
        }
    }
    protected void startActivityAndFinish(Class clazz){
        Intent i =new Intent(this,clazz);
        startActivity(i);
        finish();
    }
}
