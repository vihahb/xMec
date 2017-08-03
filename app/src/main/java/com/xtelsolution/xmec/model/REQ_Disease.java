package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;

/**
 * Created by phimau on 3/19/2017.
 */

public class REQ_Disease {
    @Expose
    private String name;
    @Expose
    private int id_disease;
    @Expose
    private String note;

    public REQ_Disease(String name, int id_disease, String note) {
        this.name = name;
        this.id_disease = id_disease;
        this.note = note;
    }
}
