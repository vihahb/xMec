package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;
import com.xtelsolution.xmec.model.entity.Disease;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by phimau on 2/16/2017.
 */

public class RESP_List_Disease extends RESP_Basic {
    @Expose
    private List<Disease> data;

    public List<Disease> getList() {
        return data;
    }

    public void setList(ArrayList<Disease> list) {
        this.data = list;
    }
}
