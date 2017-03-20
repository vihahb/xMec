package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by phimau on 3/19/2017.
 */

public class REQ_Add_Disease {
    @Expose
    private int id_medical;
    @Expose
    private String name;
    @Expose
    private int id_disease;
    @Expose
    private String note;
    @Expose
    private List<REQ_Medicine> medicines;

    public REQ_Add_Disease(int id_medical, String name, int id_disease, String note) {
        this.id_medical = id_medical;
        this.name = name;
        this.id_disease = id_disease;
        this.note = note;
    }

    public REQ_Add_Disease(int id_medical, String name, int id_disease, String note, List<REQ_Medicine> medicines) {
        this.id_medical = id_medical;
        this.name = name;
        this.id_disease = id_disease;
        this.note = note;
        this.medicines = medicines;
    }
}
