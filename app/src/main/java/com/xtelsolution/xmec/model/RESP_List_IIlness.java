package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;
import com.xtelsolution.xmec.model.entity.IIlness;

import java.util.ArrayList;

/**
 * Created by phimau on 2/16/2017.
 */

public class RESP_List_IIlness extends RESP_Basic {
    @Expose
    private ArrayList<IIlness> list;

    public ArrayList<IIlness> getList() {
        return list;
    }

    public void setList(ArrayList<IIlness> list) {
        this.list = list;
    }
}
