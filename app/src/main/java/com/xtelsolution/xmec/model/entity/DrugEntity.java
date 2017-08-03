package com.xtelsolution.xmec.model.entity;

import com.google.gson.annotations.Expose;

import java.io.Serializable;

/**
 * Created by vivhp on 8/2/2017.
 */

public class DrugEntity implements Serializable {

    @Expose
    private int user_diease_id;
    @Expose
    private String diease_name;
    @Expose
    private int user_drug_id;
    @Expose
    private String drug_name;
    @Expose
    private int drug_id;
    private boolean isAdd = false;

    public DrugEntity() {
    }

    public DrugEntity(boolean isAdd) {
        this.isAdd = isAdd;
    }

    public int getUser_diease_id() {
        return user_diease_id;
    }

    public void setUser_diease_id(int user_diease_id) {
        this.user_diease_id = user_diease_id;
    }

    public String getDiease_name() {
        return diease_name;
    }

    public void setDiease_name(String diease_name) {
        this.diease_name = diease_name;
    }

    public int getUser_drug_id() {
        return user_drug_id;
    }

    public void setUser_drug_id(int user_drug_id) {
        this.user_drug_id = user_drug_id;
    }

    public String getDrug_name() {
        return drug_name;
    }

    public void setDrug_name(String drug_name) {
        this.drug_name = drug_name;
    }

    public int getDrug_id() {
        return drug_id;
    }

    public void setDrug_id(int drug_id) {
        this.drug_id = drug_id;
    }

    public boolean isAdd() {
        return isAdd;
    }

    public void setAdd(boolean add) {
        isAdd = add;
    }

    @Override
    public String toString() {
        return "DrugEntity{" +
                "user_diease_id=" + user_diease_id +
                ", diease_name='" + diease_name + '\'' +
                ", user_drug_id=" + user_drug_id +
                ", drug_name='" + drug_name + '\'' +
                ", drug_id=" + drug_id +
                '}';
    }
}
