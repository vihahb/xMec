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

    public List<REQ_Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<REQ_Medicine> medicines) {
        this.medicines = medicines;
    }

    public int getId_medical() {
        return id_medical;
    }

    public void setId_medical(int id_medical) {
        this.id_medical = id_medical;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
}
