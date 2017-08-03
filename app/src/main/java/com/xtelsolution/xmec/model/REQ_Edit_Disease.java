package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;

/**
 * Created by phimau on 4/4/2017.
 */

public class REQ_Edit_Disease {
    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private String note;
    @Expose
    private int id_disease;

    public REQ_Edit_Disease(int id, String name, int id_disease) {
        this.id = id;
        this.name = name;
        this.id_disease = id_disease;
    }

    public REQ_Edit_Disease(int id, String name, String note, int id_disease) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.id_disease = id_disease;
    }
}
