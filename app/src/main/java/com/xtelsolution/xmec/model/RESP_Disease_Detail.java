package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

import java.io.Serializable;
import java.util.List;

/**
 * Created by phimau on 4/1/2017.
 */

public class RESP_Disease_Detail extends RESP_Basic implements Serializable {
    @Expose
    private int id;
    @Expose
    private String ten_benh;
    @Expose
    private String note;
    @Expose
    private int id_disease;
    @Expose
    private String link;
    @Expose
    private List<RESP_User_Medicine> data;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getId_disease() {
        return id_disease;
    }

    public void setId_disease(int id_disease) {
        this.id_disease = id_disease;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTen_benh() {
        return ten_benh;
    }

    public void setTen_benh(String ten_benh) {
        this.ten_benh = ten_benh;
    }

    public List<RESP_User_Medicine> getData() {
        return data;
    }

    public void setData(List<RESP_User_Medicine> data) {
        this.data = data;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

}
