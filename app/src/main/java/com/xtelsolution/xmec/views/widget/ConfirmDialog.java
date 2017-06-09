package com.xtelsolution.xmec.views.widget;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.xtelsolution.xmec.common.ConfirmDialogListener;

/**
 * Created by HUNGNT on 2/28/2017.
 */

public class ConfirmDialog {

    private AlertDialog.Builder mBuilder;


    public static void showDialog(Context context, String title, String message, final ConfirmDialogListener dialogListener) {
        AlertDialog.Builder mBuilder = new AlertDialog.Builder(context);
        mBuilder.setTitle(title);
        mBuilder.setMessage(message);

        mBuilder.setNegativeButton("Oke", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogListener.onConfirm(dialog,which);
            }
        });

        mBuilder.setPositiveButton("Thoát", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialogListener.onExit(dialog,which);
            }
        });

        mBuilder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                dialogListener.onCance(dialog);
            }
        });
        mBuilder.show();
    }
}
