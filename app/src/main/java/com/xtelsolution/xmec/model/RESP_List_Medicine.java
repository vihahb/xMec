package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

import java.util.List;

/**
 * Created by phimau on 3/19/2017.
 */

public class RESP_List_Medicine extends RESP_Basic {
    @Expose
    private List<RESP_Medicine> data;

    public List<RESP_Medicine> getData() {
        return data;
    }

    public void setData(List<RESP_Medicine> data) {
        this.data = data;
    }
}
