package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

import java.util.ArrayList;

/**
 * Created by phimau on 2/15/2017.
 */

public class RESP_LIST_MEDICAL extends RESP_Basic {
    @Expose
    private ArrayList<RESP_MEDICAL> data;

    public ArrayList<RESP_MEDICAL> getList() {
        return data;
    }

    public void setList(ArrayList<RESP_MEDICAL> list) {
        this.data = list;
    }
}
