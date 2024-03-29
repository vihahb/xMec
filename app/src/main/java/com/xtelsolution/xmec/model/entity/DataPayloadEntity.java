package com.xtelsolution.xmec.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vivhp on 7/29/2017.
 */

public class DataPayloadEntity {
    @Expose
    private int uid;
    @Expose
    private String name;
    @Expose
    private int type;

    public DataPayloadEntity() {
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DataPayloadEntity{" +
                "uid=" + uid +
                ", name='" + name + '\'' +
                ", type=" + type +
                '}';
    }
}
