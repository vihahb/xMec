package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;
import com.xtelsolution.xmec.model.entity.UserInfo;

import java.util.ArrayList;

/**
 * Created by vivhp on 7/30/2017.
 */

public class RESP_ListFriend extends RESP_Basic {
    @Expose
    private ArrayList<UserInfo> data;

    public ArrayList<UserInfo> getData() {
        return data;
    }

    public void setData(ArrayList<UserInfo> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RESP_ListFriend{" +
                "data=" + data +
                '}';
    }
}
