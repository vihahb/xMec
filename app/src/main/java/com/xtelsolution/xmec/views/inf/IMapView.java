package com.xtelsolution.xmec.views.inf;

import android.support.v4.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.xtelsolution.xmec.model.RESP_Map_Healthy_Care;

import java.util.List;

/**
 * Created by phimau on 3/1/2017.
 */

public interface IMapView extends BaseView {
    void onMapCreateSuccess();

    void onProviderDisabled();

    void onGetCurrentLocationFinish(LatLng latLng);

    void onGetListHealtyCareSuccess(List<RESP_Map_Healthy_Care> data);

    void onLocationChange(LatLng latLng);

    void onPermissionDenied();

    void onPermissionGranted();

    void onAddHealThyCareSuccess(RESP_Map_Healthy_Care healthyCare, boolean isNew);

    void onGPSDisabled();

    Fragment getFragmentView();
}
