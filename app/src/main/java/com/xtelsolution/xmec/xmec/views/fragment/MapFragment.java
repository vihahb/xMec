package com.xtelsolution.xmec.xmec.views.fragment;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LevelListDrawable;
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
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.xtel.nipservicesdk.utils.PermissionHelper;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.RESP_List_Map_Healthy_Care;
import com.xtelsolution.xmec.model.RESP_Map_Healthy_Care;
import com.xtelsolution.xmec.presenter.MapPresenter;
import com.xtelsolution.xmec.xmec.views.activity.DetailHospitalActivity;
import com.xtelsolution.xmec.xmec.views.adapter.HospitalCenterAdapter;
import com.xtelsolution.xmec.xmec.views.inf.IMapView;

import java.util.List;

/**
 * Created by HUNGNT on 1/18/2017.
 */

public class MapFragment extends BasicFragment implements OnMapReadyCallback, IMapView, GoogleMap.OnMarkerClickListener, GoogleMap.OnCameraMoveCanceledListener ,GoogleMap.OnCameraIdleListener{
    private View view;
    private GoogleMap mMap;
    private Marker mMarker;
    private FloatingActionButton btnCurrentLocation;
    private boolean isCheckPermission;
    private MapPresenter presenter;
    private boolean isMapCreated = false;


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
        presenter.initPermission();
    }


    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setOnMarkerClickListener(this);
        if (isCheckPermission) {
            presenter.getCurrentLocation();
            mMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(0f, 0f)));
            presenter.initMap();
            isMapCreated = true;
            mMap.setOnCameraIdleListener(this);
            mMap.setOnCameraMoveCanceledListener(this);

        }
    }

    @Override
    public void onMapCreateSuccess() {
        isCheckPermission = true;
        initMap();

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
        mMarker.setPosition(latLng);
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

    @Override
    public void onGetListHealtyCareSuccess(List<RESP_Map_Healthy_Care> data) {
        for (int i = 0; i < data.size(); i++) {
            Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(data.get(i).getLatitude(), data.get(i).getLongitude())).title(data.get(i).getName()));
            if (data.get(i).getType() == 1) {
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(scaleBimap(R.drawable.ic_hospital)));
            } else
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(scaleBimap(R.drawable.ic_drug_store)));
            marker.setTag(data.get(i).getId());
        }
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        Intent i = new Intent(getActivity(), DetailHospitalActivity.class);
        i.putExtra(Constant.HEALTHY_CENTER_ID, (int) marker.getTag());
        startActivity(i);
        return false;
    }

    private Bitmap scaleBimap(int id) {
        Bitmap b = BitmapFactory.decodeResource(getResources(), id);
        Bitmap bhalfsize = Bitmap.createScaledBitmap(b, 64, 64, false);
        return bhalfsize;
    }

//    @Override
//    public void onCameraMoveCanceled() {
//        xLog.e(mMap.getCameraPosition().target.latitude+"           "+mMap.getCameraPosition().target.longitude);
//        if (isCheckPermission){
//            presenter.checkGetHospital(mMap.getCameraPosition().target.latitude,mMap.getCameraPosition().target.longitude);
//        }
//    }


    @Override
    public void onCameraMoveCanceled() {
        xLog.e(mMap.getCameraPosition().target.latitude + "           " + mMap.getCameraPosition().target.longitude);
        presenter.checkGetHospital(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);
    }

//    @Override
//    public void onCameraMove() {
//        xLog.e(mMap.getCameraPosition().target.latitude + "           " + mMap.getCameraPosition().target.longitude);
//        presenter.checkGetHospital(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);
//    }
    @Override
    public void onCameraIdle() {
        xLog.e(mMap.getCameraPosition().target.latitude + "           " + mMap.getCameraPosition().target.longitude);
        presenter.checkGetHospital(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);
    }
}
