package com.xtelsolution.xmec.xmec.views.fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.xtel.nipservicesdk.utils.PermissionHelper;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.presenter.MapPresenter;
import com.xtelsolution.xmec.xmec.views.adapter.HospitalCenterAdapter;
import com.xtelsolution.xmec.xmec.views.inf.IMapView;

/**
 * Created by HUNGNT on 1/18/2017.
 */

public class MapFragment extends BasicFragment implements OnMapReadyCallback, IMapView {
    private View view;
    private GoogleMap mMap;
    private Marker marker;
    private FloatingActionButton btnCurrentLocation;
    private boolean isCheckPermission;
    private MapPresenter presenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_health_center, container, false);
            initview();
            initControl();
            presenter = new MapPresenter(this);
        }
        return view;
    }

    private void initControl() {
        btnCurrentLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.getCurrentLocation();
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
        presenter.initPermission();
    }


    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (isCheckPermission) {
            presenter.getCurrentLocation();
            marker =mMap.addMarker(new MarkerOptions().position(new LatLng(0f,0f)));
        }
    }

    @Override
    public void onPermissionGranted() {
        isCheckPermission = true;
    }

    @Override
    public void onProviderDisabled() {
        showToast("Bạn chưa bật chia sẻ vị trí");
    }

    @Override
    public void onGetCurrentLocationFinish(LatLng latLng) {
        if (mMap != null) {
            Log.e("MY", "onGetCurrentLocationFinish: " + latLng.longitude + "   " + latLng.latitude);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f));
        }
    }

    @Override
    public void onLocationChange(LatLng latLng) {
        marker.setPosition(latLng);
    }

    @Override
    public Fragment getFragmentView() {
        return MapFragment.this;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        presenter.onRequestPermissionsResult(requestCode, permissions, grantResults);


    }
}
