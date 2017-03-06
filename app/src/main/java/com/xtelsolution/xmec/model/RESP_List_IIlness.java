package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;
import com.xtelsolution.xmec.model.entity.Illness;

import java.util.ArrayList;

/**
 * Created by phimau on 2/16/2017.
 */

public class RESP_List_IIlness extends RESP_Basic {
    @Expose
    private ArrayList<Illness> data;

    public ArrayList<Illness> getList() {
        return data;
    }

    public void setList(ArrayList<Illness> list) {
        this.data = list;
    }
}
