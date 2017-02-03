package com.xtelsolution.xmec.common;


import android.util.Log;

/**
 * Created by Admin on 2/3/2017.
 */

public class xLog {
    private static final boolean DEBUG_MODE = true;

    public static void e(String TAG, String msg) {
        if (DEBUG_MODE && msg != null)
            Log.e(TAG, msg);
    }

    public static void d(String TAG, String msg) {
        if (DEBUG_MODE && msg != null)
            Log.d(TAG, msg);
    }


    public static void i(String TAG, String msg) {
        if (DEBUG_MODE && msg != null)
            Log.i(TAG, msg);


    }

    public static void w(String TAG, String msg) {
        if (DEBUG_MODE && msg != null)
            Log.w(TAG, msg);

    }
}
