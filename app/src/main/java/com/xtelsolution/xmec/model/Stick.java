package com.xtelsolution.xmec.model;

/**
 * Created by Admin on 2/3/2017.
 */

public class Stick {
    private int id;
    private String name;
    private String type;

    public Stick() {
        super();
    }

    @Override
    public String toString() {
        return "Stick{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }

    public Stick(int id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
