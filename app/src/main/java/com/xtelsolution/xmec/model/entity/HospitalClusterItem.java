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
    private String address;

    public HospitalClusterItem(LatLng latLng, String title, int idHospital, int type, String address) {
        this.latLng = latLng;
        this.title = title;
        this.idHospital = idHospital;
        this.type = type;
        this.address = address;
    }

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

    public String getAddress() {
        return address;
    }

//    public HospitalClusterItem(LatLng latLng, String title, int idHospital, String address) {
//        this.latLng = latLng;
//        this.title = title;
//        this.idHospital = idHospital;
//        this.address = address;
//    }

    public void setAddress(String address) {
        this.address = address;
    }

//    @Override
//    public boolean equals(Object obj) {
//        HospitalClusterItem clusterItem = (HospitalClusterItem) obj;
//
//        return clusterItem.getIdHospital()==this.idHospital;
//    }
}
