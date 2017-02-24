package com.xtelsolution.xmec.model.entity;

import com.google.gson.annotations.Expose;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

/**
 * Created by phimau on 2/14/2017.
 */

public class MedicalDirectory extends RESP_Basic {

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
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

}
