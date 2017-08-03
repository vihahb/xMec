package com.xtelsolution.xmec.listener;

import com.xtelsolution.xmec.model.RESP_Map_Healthy_Care;

import java.util.List;

/**
 * Created by phimau on 3/28/2017.
 */

public interface OnLoadMapSuccessListener {
    void onLoadMapSuccess(List<RESP_Map_Healthy_Care> data);
}
