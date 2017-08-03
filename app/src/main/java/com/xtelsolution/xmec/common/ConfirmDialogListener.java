package com.xtelsolution.xmec.common;

import android.content.DialogInterface;

/**
 * Created by HUNGNT on 3/1/2017.
 */

public interface ConfirmDialogListener {
    void onConfirm(DialogInterface dialog, int which);

    void onCance(DialogInterface dialog);

    void onExit(DialogInterface dialog, int which);
}
