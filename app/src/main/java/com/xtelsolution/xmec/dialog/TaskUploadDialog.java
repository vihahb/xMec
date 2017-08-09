package com.xtelsolution.xmec.dialog;

import android.app.Dialog;
import android.content.Context;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.xtelsolution.xmec.R;

/**
 * Created by vivhp on 8/7/2017
 */

public class TaskUploadDialog {

    private Context context;

    private Dialog dialog;

    private TextView tv_count;

    private ProgressBar progressBar;

    public TaskUploadDialog(Context context) {
        this.context = context;
    }

    public void show() {
        if (dialog != null) {
            dialog.dismiss();
            dialog = null;
        }

        dialog = new Dialog(context, R.style.Theme_Transparent);
        dialog.setContentView(R.layout.dialog_task_upload);
        //noinspection ConstantConditions
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);

        tv_count = (TextView) dialog.findViewById(R.id.txt_count);
        progressBar = (ProgressBar) dialog.findViewById(R.id.progressBar);

        progressBar.setMax(100);
        progressBar.setProgress(0);

        dialog.show();
    }

    public void dismis() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

    public void setProgressBarTotal(int total) {
        progressBar.setMax(total);
    }

    public void updateProgressBar(int progress) {
        progressBar.setProgress(progress);
    }

    public void setTextCount(String count) {
        tv_count.setText(count);
    }
}
