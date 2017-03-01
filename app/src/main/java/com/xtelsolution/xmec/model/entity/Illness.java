package com.xtelsolution.xmec.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by phimau on 1/22/2017.
 */

public class Illness {

    @Expose
    private String name;
    @Expose
    private int id;
    public Illness(String name) {
        this.name = name;
    }

    public Illness(int id, String name) {
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
        return "Illness{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
