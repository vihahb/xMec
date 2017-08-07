package com.xtelsolution.xmec.model;

import android.graphics.Bitmap;

import com.google.gson.annotations.Expose;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

/**
 * Created by phimau on 3/8/2017.
 */

public class Resource extends RESP_Basic {

    @Expose
    private int id;
    @Expose
    private String server_path;
    @Expose
    private Bitmap bitmap;

    public Resource(String server_path, Bitmap bitmap) {
        this.server_path = server_path;
        this.bitmap = bitmap;
    }
//    public Resource(String server_path) {
//        this.server_path = server_path;
//    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getServer_path() {
        return server_path;
    }

    public void setServer_path(String server_path) {
        this.server_path = server_path;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
