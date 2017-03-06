package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

/**
 * Created by phimau on 2/15/2017.
 */

public class RESP_MEDICAL extends RESP_Basic {
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

    public RESP_MEDICAL(int id,String name, long begin_time, long end_time, int type) {
        this.name = name;
        this.begin_time = begin_time;
        this.end_time = end_time;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
