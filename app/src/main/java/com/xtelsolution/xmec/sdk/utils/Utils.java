package com.xtelsolution.xmec.sdk.utils;

import android.content.Context;
import android.widget.Toast;

/**
 * Created by Admin on 1/23/2017.
 */

public class Utils {
    public static void showToast(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
