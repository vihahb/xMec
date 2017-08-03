package com.xtelsolution.xmec.model;

/**
 * Created by Admin on 1/23/2017.
 */

public class ControlDetailDisease {
    private int id;
    private String name;
    private int image;

    public ControlDetailDisease() {
    }

    public ControlDetailDisease(int id, String name, int image) {

        this.id = id;
        this.name = name;
        this.image = image;
    }

    @Override
    public String toString() {
        return "ControlDetailDisease{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image=" + image +
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
