package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;
import com.xtelsolution.xmec.model.entity.GeneralEntity;
import com.xtelsolution.xmec.model.entity.SpecialEntity;

/**
 * Created by vivhp on 8/2/2017.
 */

public class RESP_DrugList extends RESP_Basic {

    @Expose
    private GeneralEntity general;

    @Expose
    private SpecialEntity special;

    public GeneralEntity getGeneral() {
        return general;
    }

    public void setGeneral(GeneralEntity general) {
        this.general = general;
    }

    public SpecialEntity getSpecial() {
        return special;
    }

    public void setSpecial(SpecialEntity special) {
        this.special = special;
    }

    @Override
    public String toString() {
        return "RESP_DrugList{" +
                "general=" + general +
                ", special=" + special +
                '}';
    }
}
