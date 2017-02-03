package com.xtelsolution.xmec.model;

/**
 * Created by Admin on 2/3/2017.
 */

public class Medicine {
    private int id;
    private String name;
    private String type;

    @Override
    public String toString() {
        return "Medicine{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                ", id=" + id +
                '}';
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

    public Medicine() {

    }

    public Medicine(int id, String name, String type) {

        this.id = id;
        this.name = name;
        this.type = type;
    }
}
