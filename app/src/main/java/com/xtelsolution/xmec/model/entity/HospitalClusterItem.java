package com.xtelsolution.xmec.model.entity;

import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.clustering.ClusterItem;

/**
 * Created by phimau on 4/9/2017.
 */

public class HospitalClusterItem implements ClusterItem {
    private LatLng latLng;
    private String title;
    private int idHospital;
    private int type;
    @Override
    public LatLng getPosition() {
        return latLng;
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public String getSnippet() {
        return null;
    }

    public int getIdHospital() {
        return idHospital;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public HospitalClusterItem(LatLng latLng, String title, int idHospital) {
        this.latLng = latLng;
        this.title = title;
        this.idHospital = idHospital;
    }

    public HospitalClusterItem(LatLng latLng, String title, int idHospital, int type) {
        this.latLng = latLng;
        this.title = title;
        this.idHospital = idHospital;
        this.type = type;
    }
}
