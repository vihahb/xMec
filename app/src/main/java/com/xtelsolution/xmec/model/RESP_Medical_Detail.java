package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by phimau on 3/8/2017.
 */

public class RESP_Medical_Detail extends RESP_Medical {
    @Expose
    private String note;
    @Expose
    private List<Resource> resources;

    public RESP_Medical_Detail(int id, String name, long begin_time, long end_time, int type) {
        super(id, name, begin_time, end_time, type);
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Resource> getResources() {
        return resources;
    }

    public void setResources(List<Resource> resources) {
        this.resources = resources;
    }
}
