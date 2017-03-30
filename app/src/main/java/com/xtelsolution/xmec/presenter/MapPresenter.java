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
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.maps.android.SphericalUtil;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.LoginModel;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.utils.PermissionHelper;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.HealthyCareModel;
import com.xtelsolution.xmec.model.RESP_List_Map_Healthy_Care;
import com.xtelsolution.xmec.model.RESP_Map_Healthy_Care;
import com.xtelsolution.xmec.xmec.views.inf.IMapView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by phimau on 3/1/2017.
 */

public class MapPresenter extends BasePresenter implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {
    private static final String TAG = "MapPresenter";
    private IMapView view;
    private Activity mActivity;
    private LocationManager mLocationManager;
    private double lat;
    private double log;
    private double radius;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private HashMap<Integer, Boolean> listPos;
    private final int GETLOCATION = 1;
    private boolean isMapInit = false;

//    private final LocationListener mLocationListener = new LocationListener() {
//        @Override
//        public void onLocationChanged(Location location) {
//
//        }
//
//        @Override
//        public void onStatusChanged(String s, int i, Bundle bundle) {
//            Log.d("MapPresenter", "onStatusChanged: ");
//        }
//
//        @Override
//        public void onProviderEnabled(String s) {
//            Log.d("MapPresenter", "onProviderEnabled: ");
//        }
//
//        @Override
//        public void onProviderDisabled(String s) {
//            Log.d("MapPresenter", "onProviderDisabled: ");
//        }
//    };

    public MapPresenter(IMapView view) {
        this.view = view;
        mActivity = view.getActivity();
        radius = 2;
        listPos = new HashMap<>();
    }

    public void getCurrentLocation() {
        if (!isLocationEnable()) {
            view.onProviderDisabled();
            return;
        }
        view.onGetCurrentLocationFinish(new LatLng(lat, log));
    }

    public void initMap() {
        isMapInit = true;
        view.onLocationChange(new LatLng(lat, log));


    }

    public void initPermission() {
        mLocationManager = (LocationManager) view.getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            view.getFragmentView().requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 99);
            return;
        } else {
            if (googleApiClient == null) {
                googleApiClient = new GoogleApiClient.Builder(mActivity)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build();
            }
            googleApiClient.connect();
        }
    }

    public void getHospitals(final Object... param) {
        double latitude = (double) param[1];
        double longitude = (double) param[2];
        String location = "latitude=" + latitude + "&longitude=" + longitude;
        String url = Constant.SERVER_XMEC + Constant.HEALTHY_CENTER + "?" + location + "&radius=" + radius + "&type=1";
        xLog.e(TAG, "getHospitals: " + url);
        HealthyCareModel.getInstance().getHospital(url, LoginManager.getCurrentSession(), new ResponseHandle<RESP_List_Map_Healthy_Care>(RESP_List_Map_Healthy_Care.class) {
            @Override
            public void onSuccess(RESP_List_Map_Healthy_Care obj) {
                List<RESP_Map_Healthy_Care> result = obj.getData();
                List<RESP_Map_Healthy_Care> data = new ArrayList<>();
                for (RESP_Map_Healthy_Care mapHealthyCare : result) {
                    if (listPos.get(mapHealthyCare.getId()) == null) {
                        listPos.put(mapHealthyCare.getId(), true);
                        data.add(mapHealthyCare);
                    }
                }
                view.onGetListHealtyCareSuccess(data);
                view.dismissProgressDialog();
            }

            @Override
            public void onError(Error error) {
                handlerError(view, error, param);
            }
        });
    }

    public void checkGetHospital(double lat, double lng) {
        if (!checkConnnecttion(view))
            return;
        getHospitals(GETLOCATION, lat, lng);
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

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(100);
        locationRequest.setFastestInterval(100);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onGetNewSessionSuccess(Object... param) {
        switch ((int) param[0]) {
            case GETLOCATION:
                getHospitals(param);
                break;
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED && mLocationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            createLocationRequest();
            startLocationUpdates();
            Location location = LocationServices.FusedLocationApi.getLastLocation(
                    googleApiClient);
            if (location != null) {
                lat = location.getLatitude();
                log = location.getLongitude();
                view.onGetCurrentLocationFinish(new LatLng(lat, log));
                checkGetHospital(lat, log);
                view.onMapCreateSuccess();
            }
        }

    }

    private void startLocationUpdates() {
        if (googleApiClient == null) {
            xLog.e(TAG,"startLocationUpdates: client is null");
        }
        if (locationRequest == null) {
            xLog.e(TAG,"startLocationUpdates: location request is null");
        }
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED && mLocationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    googleApiClient, locationRequest, this);
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        lat = location.getLatitude();
        log = location.getLongitude();
        if (isMapInit)
            view.onLocationChange(new LatLng(lat, log));
    }
}
