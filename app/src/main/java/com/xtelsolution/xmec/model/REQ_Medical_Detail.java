package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;

/**
 * Created by phimau on 2/24/2017.
 */

public class REQ_Medical_Detail  {
    @Expose
    private int id;
    @Expose
    private String name;
    @Expose
    private long begin_time;
    @Expose
    private long end_time;
    @Expose
    private int type;
    @Expose
    private String note;
    @Expose
    private String[] resources;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getBegin_time() {
        return begin_time;
    }

    public void setBegin_time(long begin_time) {
        this.begin_time = begin_time;
    }

    public long getEnd_time() {
        return end_time;
    }

    public void setEnd_time(long end_time) {
        this.end_time = end_time;
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

    public String[] getResources() {
        return resources;
    }

    public void setResources(String[] resources) {
        this.resources = resources;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

}
