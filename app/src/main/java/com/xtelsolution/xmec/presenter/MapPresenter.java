package com.xtelsolution.xmec.presenter;


import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
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
import com.google.android.gms.maps.model.LatLng;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.callback.CallbacListener;
import com.xtel.nipservicesdk.callback.ResponseHandle;
import com.xtel.nipservicesdk.model.entity.Error;
import com.xtel.nipservicesdk.model.entity.RESP_Login;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.HealthyCareModel;
import com.xtelsolution.xmec.model.RESP_List_Map_Healthy_Care;
import com.xtelsolution.xmec.model.RESP_Map_Healthy_Care;
import com.xtelsolution.xmec.views.inf.IMapView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by phimau on 3/1/2017.
 */

public class MapPresenter extends BasePresenter implements
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        com.google.android.gms.location.LocationListener {
    private final int GETLOCATION = 1;
    private IMapView view;
    private Activity mActivity;
    private LocationManager mLocationManager;
    private double lat;
    private double log;
    private double radius;
    private GoogleApiClient googleApiClient;
    private LocationRequest locationRequest;
    private HashMap<Integer, RESP_Map_Healthy_Care> listPos;
    private boolean isMapInit = false;

    public MapPresenter(IMapView view) {
        this.view = view;
        mActivity = view.getActivity();
        radius = 1;
        listPos = new HashMap<>();
    }

    public void getCurrentLocation() {
        if (!isLocationEnable()) {
            view.onGPSDisabled();
            xLog.e("getCurrentLocation", "getCurrentLocation");
            return;
        }
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(
                googleApiClient);
        if (location != null) {
            lat = location.getLatitude();
            log = location.getLongitude();
            view.onGetCurrentLocationFinish(new LatLng(lat, log));
        }


    }

    public void initMap() {
        isMapInit = true;


    }

    public void checkPermission() {
        mLocationManager = (LocationManager) view.getActivity().getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            view.getFragmentView().requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 99);
            view.onPermissionDenied();
            return;
        } else {
            view.onPermissionGranted();
            if (googleApiClient == null) {
                googleApiClient = new GoogleApiClient.Builder(mActivity)
                        .addConnectionCallbacks(this)
                        .addOnConnectionFailedListener(this)
                        .addApi(LocationServices.API)
                        .build();
            }
            view.onMapCreateSuccess();
            googleApiClient.connect();
        }
    }

    public void getHospitals(final Object... param) {
        final double latitude = (double) param[1];
        final double longitude = (double) param[2];
        if (latitude == 0 && longitude == 0)
            return;
        String location = "latitude=" + latitude + "&longitude=" + longitude;
        String url = "http://124.158.5.112:8092/xmec/v0.1/user/hospitals-around?latitude=" + latitude + "&longitude=" + longitude + "&radius=" + radius + "&type=-1";
        xLog.e("getHospitals", url);
        HealthyCareModel.getInstance().getHospital(url, LoginManager.getCurrentSession(), new ResponseHandle<RESP_List_Map_Healthy_Care>(RESP_List_Map_Healthy_Care.class) {
            @Override
            public void onSuccess(RESP_List_Map_Healthy_Care obj) {
                List<RESP_Map_Healthy_Care> result = obj.getData();
                List<RESP_Map_Healthy_Care> data = new ArrayList<>();
                for (RESP_Map_Healthy_Care mapHealthyCare : result) {
                    if (listPos.get(mapHealthyCare.getId()) == null) {
                        listPos.put(mapHealthyCare.getId(), mapHealthyCare);
                        data.add(mapHealthyCare);
                    }
                }
                view.onGetListHealtyCareSuccess(data);
            }

            @Override
            public void onError(Error error) {
                if (error.getCode() == 2) {
                    CallbackManager.create(view.getActivity()).getNewSesion(new CallbacListener() {
                        @Override
                        public void onSuccess(RESP_Login success) {
                            getHospitals(GETLOCATION, latitude, longitude);
                        }

                        @Override
                        public void onError(Error error) {
                            view.requireLogin();
                            view.showToast("Vui lòng đăng nhập để tiếp tục.");
                        }
                    });
                } else {
                    view.showToast("Có lỗi xảy ra.");
                }
            }
        });
    }

    public void addHealThyCare(RESP_Map_Healthy_Care healthyCare) {
        RESP_Map_Healthy_Care mapHealthyCare = listPos.get(healthyCare.getId());
        boolean isNew = false;
        if (mapHealthyCare == null) {
            listPos.put(healthyCare.getId(), healthyCare);
            isNew = true;
            mapHealthyCare = healthyCare;
        }
        view.onAddHealThyCareSuccess(mapHealthyCare, isNew);
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

    public void initPermission() {
        view.getActivity().requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, 99);
    }

    private void createLocationRequest() {
        locationRequest = new LocationRequest();
        locationRequest.setInterval(1000);
        locationRequest.setFastestInterval(500);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        registerLocation();
    }

    public void registerLocation() {
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            if (mLocationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
                createLocationRequest();
                startLocationUpdates();
                Location location = LocationServices.FusedLocationApi.getLastLocation(
                        googleApiClient);
                if (location != null) {
                    lat = location.getLatitude();
                    log = location.getLongitude();
                    getCurrentLocation();
                    view.onLocationChange(new LatLng(lat, log));
//                checkGetHospital(lat,log);
                }
            }
        }
    }

    private void startLocationUpdates() {
        if (googleApiClient == null) {
            xLog.e("startLocationUpdates", "client is null");
        }
        if (locationRequest == null) {
            xLog.e("startLocationUpdates", "Request is null");
        }
        if (ContextCompat.checkSelfPermission(mActivity, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED && mLocationManager.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {
            LocationServices.FusedLocationApi.requestLocationUpdates(googleApiClient, locationRequest, this);
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

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 99)
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("MY", "onRequestPermissionsResult: ");
                checkPermission();
            } else if (grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Log.e("MY", "onRequestPermissionsResult: denine");
                view.onPermissionDenied();
            }
    }

}
