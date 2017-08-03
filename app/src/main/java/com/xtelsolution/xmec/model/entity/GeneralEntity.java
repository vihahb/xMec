package com.xtelsolution.xmec.model.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by vivhp on 8/2/2017.
 */

public class GeneralEntity implements Serializable {
    @Expose
    private ArrayList<DrugsEntity> data;

    public ArrayList<DrugsEntity> getData() {
        return data;
    }

    public void setData(ArrayList<DrugsEntity> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "GeneralEntity{" +
                "data=" + data +
                '}';
    }
}
