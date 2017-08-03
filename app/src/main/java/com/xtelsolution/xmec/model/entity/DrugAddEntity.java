package com.xtelsolution.xmec.model.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by vivhp on 8/2/2017.
 */

public class DrugAddEntity  implements Serializable {

    @Expose
    private int mrb_id;
    @Expose
    private int drug_id;
    @Expose
    private String  instruction;

    public DrugAddEntity() {
    }

    public int getMrb_id() {
        return mrb_id;
    }

    public void setMrb_id(int mrb_id) {
        this.mrb_id = mrb_id;
    }

    public int getDrug_id() {
        return drug_id;
    }

    public void setDrug_id(int drug_id) {
        this.drug_id = drug_id;
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    @Override
    public String toString() {
        return "DrugAddEntity{" +
                "mrb_id=" + mrb_id +
                ", drug_id=" + drug_id +
                ", instruction='" + instruction + '\'' +
                '}';
    }
}
