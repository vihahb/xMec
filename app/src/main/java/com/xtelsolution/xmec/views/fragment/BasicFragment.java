package com.xtelsolution.xmec.views.fragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.xtelsolution.xmec.MyApplication;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.callbacks.DialogActionListener;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.SharedPreferencesUtils;
import com.xtelsolution.xmec.views.activity.LoginActivity;
import com.xtelsolution.xmec.views.inf.BaseView;

/**
 * Created by phimau on 2/15/2017.
 */

public abstract class BasicFragment extends Fragment implements BaseView {

    private ProgressDialog progressDialog;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initProgressDialog();
    }

    private void initProgressDialog() {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setCancelable(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
    }

    protected void setImage(ImageView img, String url) {
        Picasso.with(getContext())
                .load(url)
                .placeholder(R.mipmap.avatar)
                .error(R.mipmap.avatar)
                .into(img);
    }

    @Override
    public void showToast(String msg) {
        if (getContext() != null)
            Toast.makeText(MyApplication.context, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLog(String TAG, String msg) {
        xLog.d(TAG, msg);
    }

    @Override
    public void showProgressDialog(String title) {
        progressDialog.setMessage(title);
        progressDialog.show();
    }

    @Override
    public void dismissProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    protected void hideKeyBoard() {
        if (getActivity().getCurrentFocus() != null) {
            InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService(Activity.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    protected void setUi(View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    hideKeyBoard();
                    return false;
                }
            });
        }
        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                setUi(((ViewGroup) view).getChildAt(i));
            }
        }
    }

    protected void showLoginDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle("Bạn cần đăng nhập để sử dụng chức năng này")
                .setPositiveButton("Đăng nhập", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.show();
    }

    protected void showActionDialog(String title, String positiveTitle, final DialogActionListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext())
                .setTitle(title)
                .setPositiveButton(positiveTitle, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        listener.onActionProcess();
                    }
                })
                .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
        builder.show();
    }

    protected boolean isLogin() {
        if (SharedPreferencesUtils.getInstance().isLogined())
            return true;
        return false;
    }

    public void requireLogin() {
        if (LoginActivity.active) {
            getActivity().startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }
    }
}
