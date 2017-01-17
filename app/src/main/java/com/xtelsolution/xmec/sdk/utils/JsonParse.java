package com.xtelsolution.xmec.sdk.utils;

import android.content.Context;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;


import org.json.JSONObject;

/**
 * Created by Lê Công Long Vũ on 11/9/2016.
 */

public class JsonParse {

    public static boolean checkJsonObject(String result) {
        try {
            JSONObject jsonObject = new JSONObject(result);
            return true;
        } catch (Exception e) {
            Log.e("Loi_check_json", e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public static void getCodeError(Context activity, View view, int code, String content) {
        if (code == 3) {
            if (view != null)
                Snackbar.make(view, "", Snackbar.LENGTH_SHORT).show();
        }
    }

    public static String getCodeMessage(int code, String content) {
        if (code == 2) {
            return "";
        }
        return "";
    }
}