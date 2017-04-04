package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

/**
 * Created by phimau on 4/3/2017.
 */

public class RESP_User_Medicine extends RESP_Basic {
    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private int id_drug;

    public RESP_User_Medicine(int id, String name, int id_drug) {
        this.id = id;
        this.name = name;
        this.id_drug = id_drug;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_drug() {
        return id_drug;
    }

    public void setId_drug(int id_drug) {
        this.id_drug = id_drug;
    }
}
