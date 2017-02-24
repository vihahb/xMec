package com.xtelsolution.xmec.model.entity;

import com.google.gson.annotations.Expose;

/**
 * Created by phimau on 1/22/2017.
 */

public class IIlness {

    @Expose
    private String name;

    public IIlness(String name) {
        this.name = name;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
