package com.xtelsolution.xmec.model.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by vivhp on 8/2/2017.
 */

public class DrugsEntity extends DrugEntity implements Serializable {

    @Expose
    private String instruction;

    public DrugsEntity() {
    }

    public String getInstruction() {
        return instruction;
    }

    public void setInstruction(String instruction) {
        this.instruction = instruction;
    }

    @Override
    public String toString() {
        return "DrugsEntity{" +
                "instruction='" + instruction + '\'' +
                '}';
    }
}
