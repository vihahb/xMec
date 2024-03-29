package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;

/**
 * Created by phimau on 3/19/2017.
 */

public class REQ_Medicine {
    @Expose
    private String name;
    @Expose
    private int id_medicine;

    public REQ_Medicine(String name, int id_medicine) {
        this.name = name;
        this.id_medicine = id_medicine;
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

    @Override
    public String toString() {
        return "REQ_Medicine{" +
                ", name='" + name + '\'' +
                ", id_medicine=" + id_medicine +
                '}';
    }
}
