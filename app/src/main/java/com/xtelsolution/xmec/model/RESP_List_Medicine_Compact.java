package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

import java.util.List;

/**
 * Created by phimau on 3/21/2017.
 */

public class RESP_List_Medicine_Compact extends RESP_Basic {
    @Expose
    private List<Medicine> data;

    public List<Medicine> getData() {
        return data;
    }

    public void setData(List<Medicine> data) {
        this.data = data;
    }
}
