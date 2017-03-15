package com.xtelsolution.xmec.xmec.views.inf;

import android.support.v4.app.Fragment;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by phimau on 3/1/2017.
 */

public interface IMapView extends BaseView {
    void onPermissionGranted();
    void onProviderDisabled();
    void onGetCurrentLocationFinish(LatLng latLng);
    void onLocationChange(LatLng latLng);
    Fragment getFragmentView();
}
