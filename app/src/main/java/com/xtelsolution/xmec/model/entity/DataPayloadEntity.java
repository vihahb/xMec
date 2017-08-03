package com.xtelsolution.xmec.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by vivhp on 7/29/2017.
 */

public class DataPayloadEntity {
    @Expose
    private String Title;
    @Expose
    private String Message;
    @Expose
    private int Type;
    @Expose
    private String Banner;

    public DataPayloadEntity() {
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public int getType() {
        return Type;
    }

    public void setType(int type) {
        Type = type;
    }

    public String getBanner() {
        return Banner;
    }

    public void setBanner(String banner) {
        Banner = banner;
    }

    @Override
    public String toString() {
        return "DataPayloadEntity{" +
                "Title='" + Title + '\'' +
                ", Message='" + Message + '\'' +
                ", Type=" + Type +
                '}';
    }
}
