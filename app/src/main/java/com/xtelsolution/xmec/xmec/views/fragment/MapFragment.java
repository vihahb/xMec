package com.xtelsolution.xmec.xmec.views.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SlidingDrawer;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.xtel.nipservicesdk.utils.PermissionHelper;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.xmec.views.adapter.HospitalCenterAdapter;
import com.xtelsolution.xmec.xmec.views.inf.IMapView;

/**
 * Created by HUNGNT on 1/18/2017.
 */

public class MapFragment extends BasicFragment implements OnMapReadyCallback, IMapView {
    private View view;
    private SlidingDrawer slidingDrawer;
    private RecyclerView rvHosiptalCenter;
    private HospitalCenterAdapter adapter;
    private GoogleMap mMap;
    private FloatingActionButton btnCurrentLocation;
    private boolean isCheckPermission;
    private double myLongitude;
    private double myLatitude;
    private LocationManager mLocationManager;
    private final LocationListener mLocationListener = new LocationListener() {
        @Override
        public void onLocationChanged(final Location location) {
            myLatitude = location.getLatitude();
            myLongitude = location.getLongitude();
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {

        }

        @Override
        public void onProviderEnabled(String s) {

        }

        @Override
        public void onProviderDisabled(String s) {

        }
    };

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_health_center, container, false);
            initview();
            initControl();
            mLocationManager = (LocationManager) getActivity().getSystemService(Context.LOCATION_SERVICE);
            isCheckPermission = PermissionHelper.checkOnlyPermission(Manifest.permission.ACCESS_FINE_LOCATION, getActivity(), 98);
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            } else {
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 3000, 50, mLocationListener);}

        }
        return view;
    }

    private void initControl() {
        btnCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLatitude,myLongitude),15));
            }
        });
    }

    private void initview() {
        btnCurrentLocation = (FloatingActionButton) view.findViewById(R.id.btn__current_location);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initMap();
    }

    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (isCheckPermission) {
            xLog.e("isCheckPermission" + "Done");
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(myLatitude,myLongitude),13));

        }
    }

    @Override
    public void onPermissionGranted() {
        isCheckPermission = true;
        onMapReady(mMap);
    }
}
