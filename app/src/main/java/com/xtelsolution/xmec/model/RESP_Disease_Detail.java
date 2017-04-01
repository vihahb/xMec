package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;
import com.xtelsolution.xmec.model.entity.Disease;

import java.util.List;

/**
 * Created by phimau on 4/1/2017.
 */

public class RESP_Disease_Detail extends RESP_Basic {
    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private String note;
    @Expose
    private List<Medicine> medicines;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Medicine> getMedicines() {
        return medicines;
    }

    public void setMedicines(List<Medicine> medicines) {
        this.medicines = medicines;
    }
}
