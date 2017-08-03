package com.xtelsolution.xmec.model.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by vivhp on 8/2/2017.
 */

public class SpecialEntity implements Serializable {

    @Expose
    private ArrayList<DrugEntity> data;

    public SpecialEntity() {
    }

    public ArrayList<DrugEntity> getData() {
        return data;
    }

    public void setData(ArrayList<DrugEntity> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SpecialEntity{" +
                "data=" + data +
                '}';
    }
}
