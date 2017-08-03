package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

import java.util.List;

/**
 * Created by phimau on 3/20/2017.
 */

public class RESP_List_Disease_With_Link extends RESP_Basic {
    @Expose
    private List<RESP_Disease> data;

    public List<RESP_Disease> getData() {
        return data;
    }

    public void setData(List<RESP_Disease> data) {
        this.data = data;
    }
}
