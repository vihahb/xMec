package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;

/**
 * Created by phimau on 3/20/2017.
 */

public class RESP_Disease {

    @Expose
    private String name;
    @Expose
    private int id_medical;
    @Expose
    private int id_disease;
    @Expose
    private String note;
    @Expose
    private int id;
    @Expose
    private String link;

    public int getId_medical() {
        return id_medical;
    }

    public void setId_medical(int id_medical) {
        this.id_medical = id_medical;
    }

    public int getId_disease() {
        return id_disease;
    }

    public void setId_disease(int id_disease) {
        this.id_disease = id_disease;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
