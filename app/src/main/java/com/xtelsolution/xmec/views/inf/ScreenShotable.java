package com.xtelsolution.xmec.views.inf;

import android.graphics.Bitmap;

/**
 * Created by HuyenVu on 6/13/2017.
 */

public interface ScreenShotable {
    public void takeScreenShot();

    public Bitmap getBitmap();
}
