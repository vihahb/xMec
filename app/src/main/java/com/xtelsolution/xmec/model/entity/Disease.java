package com.xtelsolution.xmec.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by phimau on 1/22/2017.
 */

public class Disease {

    @Expose
    private String name;
    @Expose
    private int id;
    public Disease(String name) {
        this.name = name;
    }

    public Disease(int id, String name) {
        this.id = id;
        this.name = name;
    }

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

    @Override
    public String toString() {
        return "Disease{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
