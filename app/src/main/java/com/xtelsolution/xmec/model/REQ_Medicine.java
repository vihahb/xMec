package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;

/**
 * Created by phimau on 3/19/2017.
 */

public class REQ_Medicine  {
    @Expose
    private int id_disease;
    @Expose
    private String name;
    @Expose
    private int id_medicine;

    public REQ_Medicine( String name, int id_medicine) {
        this.name = name;
        this.id_medicine = id_medicine;
    }

    public REQ_Medicine(int id, String name, int id_medicine) {
        this.id_disease = id;
        this.name = name;
        this.id_medicine = id_medicine;
    }

    public int getId_disease() {
        return id_disease;
    }

    public void setId_disease(int id_disease) {
        this.id_disease = id_disease;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId_medicine() {
        return id_medicine;
    }

    public void setId_medicine(int id_medicine) {
        this.id_medicine = id_medicine;
    }
}
