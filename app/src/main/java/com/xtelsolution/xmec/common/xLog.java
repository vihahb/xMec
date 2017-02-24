package com.xtelsolution.xmec.common;


import android.util.Log;

/**
 * Created by Admin on 2/3/2017.
 */

public class xLog {
    private static final boolean DEBUG_MODE = true;
    private static final String DEBUG_TAG = "xMec_DEBUG";

    public static void e(String msg) {
        if (DEBUG_MODE && msg != null)
            Log.e(DEBUG_TAG, msg);
    }

    public static void d(String msg) {
        if (DEBUG_MODE && msg != null)
            Log.d(DEBUG_TAG, msg);
    }


    public static void i(String msg) {
        if (DEBUG_MODE && msg != null)
            Log.i(DEBUG_TAG, msg);


    }

    public static void w(String msg) {
        if (DEBUG_MODE && msg != null)
            Log.w(DEBUG_TAG, msg);

    }
}
