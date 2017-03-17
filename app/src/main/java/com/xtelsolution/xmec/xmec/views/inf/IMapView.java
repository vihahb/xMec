package com.xtelsolution.xmec.xmec.views.inf;

import android.support.v4.app.Fragment;

import com.google.android.gms.maps.model.LatLng;
import com.xtelsolution.xmec.model.RESP_List_Map_Healthy_Care;
import com.xtelsolution.xmec.model.RESP_Map_Healthy_Care;

import java.util.List;

/**
 * Created by phimau on 3/1/2017.
 */

public interface IMapView extends BaseView {
    void onPermissionGranted();
    void onProviderDisabled();
    void onGetCurrentLocationFinish(LatLng latLng);
    void onGetListHealtyCareSuccess(List<RESP_Map_Healthy_Care> data);
    void onLocationChange(LatLng latLng);
    Fragment getFragmentView();
}
