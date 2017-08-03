package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

import java.util.ArrayList;

/**
 * Created by vivhp on 8/2/2017.
 */

public class RESP_ListFriendActive extends RESP_Basic {

    @Expose
    private ArrayList<RESP_User> data;

    public ArrayList<RESP_User> getData() {
        return data;
    }

    public void setData(ArrayList<RESP_User> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "RESP_ListFriendActive{" +
                "data=" + data +
                '}';
    }
}
