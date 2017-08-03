package com.xtelsolution.xmec.model;

import com.google.gson.annotations.Expose;
import com.xtel.nipservicesdk.model.entity.RESP_Basic;

import java.util.List;

/**
 * Created by phimau on 3/14/2017.
 */

public class RESP_List_Map_Healthy_Care extends RESP_Basic {
    // fix data co so y te
//    @Expose
//    List<RESP_Map_Healthy_Care> data;
    @Expose
    List<RESP_Map_Healthy_Care> data;
//    public List<RESP_Map_Healthy_Care> getData() {
//        return data;
//    }
//
//    public void setData(List<RESP_Map_Healthy_Care> data) {
//        this.data = data;
//    }

    public List<RESP_Map_Healthy_Care> getData() {
        return data;
    }

    public void setData(List<RESP_Map_Healthy_Care> data) {
        this.data = data;
    }
}
