package com.xtelsolution.xmec.presenter;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;
import com.xtel.nipservicesdk.utils.PermissionHelper;
import com.xtelsolution.xmec.xmec.views.inf.IMapView;

/**
 * Created by phimau on 3/1/2017.
 */

public class MapPresenter {
    private IMapView view;
    private Activity mActivity;
    private LocationManager mLocationManager;
    private double lat;
    private double log;
    private boolean isCheckPermission;
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            lat = location.getLatitude();
            log = location.getLongitude();
            view.onLocationChange(new LatLng(lat,log));
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            Log.d("MapPresenter", "onStatusChanged: ");
        }

        @Override
        public void onProviderEnabled(String s) {
            Log.d("MapPresenter", "onProviderEnabled: ");
        }

        @Override
        public void onProviderDisabled(String s) {
            Log.d("MapPresenter", "onProviderDisabled: ");
        }
    };

    public MapPresenter(IMapView view) {
        this.view = view;
        mActivity = view.getActivity();

    }

    public void getCurrentLocation() {
        if (!isLocationEnable()) {
            view.onProviderDisabled();
            return;
        }
        view.onGetCurrentLocationFinish(new LatLng(lat, log));

    }

    public void initPermission() {

        mLocationManager = (LocationManager) view.getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            view.getFragmentView().requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 99);
            return;
        } else {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10, mLocationListener);
            Location lastKnownLocationGPS = mLocationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (lastKnownLocationGPS != null) {
                lat = lastKnownLocationGPS.getLatitude();
                log = lastKnownLocationGPS.getLongitude();
                Log.e("MY", "initPermission: "+lat+"    "+log );
            }
            view.onPermissionGranted();
        }


    }

    private boolean isLocationEnable() {
        if (mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            return true;
        }
        return false;
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 99)
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("MY", "onRequestPermissionsResult: ");
                initPermission();
            }
    }
}
