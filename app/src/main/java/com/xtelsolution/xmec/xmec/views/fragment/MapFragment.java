package com.xtelsolution.xmec.xmec.views.fragment;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
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
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.xtelsolution.xmec.listener.OnLoadMapSuccessListener;
import com.xtelsolution.xmec.model.RESP_List_Map_Healthy_Care;
import com.xtelsolution.xmec.model.RESP_Map_Healthy_Care;
import com.xtelsolution.xmec.presenter.MapPresenter;
import com.xtelsolution.xmec.xmec.views.activity.DetailHospitalActivity;
import com.xtelsolution.xmec.xmec.views.activity.HomeActivity;
import com.xtelsolution.xmec.xmec.views.adapter.HospitalCenterAdapter;
import com.xtelsolution.xmec.xmec.views.inf.IMapView;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by HUNGNT on 1/18/2017.
 */

public class MapFragment extends BasicFragment implements OnMapReadyCallback, IMapView, GoogleMap.OnMarkerClickListener, GoogleMap.OnCameraMoveCanceledListener, GoogleMap.OnCameraIdleListener {
    private static final String TAG = "MapFragment";
    private View view;
    private GoogleMap mMap;
    private Marker mMarker;
    private FloatingActionButton btnCurrentLocation;
    private boolean isCheckPermission;
    private OnLoadMapSuccessListener onLoadMapSuccessListener;
    private MapPresenter presenter;
    private CoordinatorLayout locationPermission;
    private Button btnInitPermission;
    private boolean isMapCreated = false;
    int count = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_health_center, container, false);
            onLoadMapSuccessListener = ((HomeActivity) getActivity()).get();
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
        btnInitPermission.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.initPermission();
            }
        });
    }

    private void initview() {
        btnCurrentLocation = (FloatingActionButton) view.findViewById(R.id.btn__current_location);
        locationPermission = (CoordinatorLayout) view.findViewById(R.id.location_permission);
        btnInitPermission = (Button) view.findViewById(R.id.btn_init_permission);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (mMap == null) {
            presenter.checkPermission();
        }
    }


    private void initMap() {
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMarker = mMap.addMarker(new MarkerOptions().position(new LatLng(0f, 0f)));
        mMarker.setIcon(BitmapDescriptorFactory.fromResource(R.drawable.my_location));
        mMarker.setFlat(true);
        mMarker.setAnchor(0.5f, 0.5f);
        presenter.initMap();
        isMapCreated = true;
        mMap.setOnCameraIdleListener(this);
        mMap.setOnCameraMoveCanceledListener(this);
        mMap.setOnMarkerClickListener(this);
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
        Log.e("MY", "onGetCurrentLocationFinish: " + latLng.longitude + "   " + latLng.latitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f));
    }

    @Override
    public void onLocationChange(LatLng latLng) {
        mMarker.setPosition(latLng);
//        xLog.e("onLocationChange", latLng.toString());
    }

    @Override
    public void onPermissionDenied() {
        locationPermission.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPermissionGranted() {
        locationPermission.setVisibility(View.GONE);
    }

    @Override
    public void onGPSDisabled() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(getActivity());
        alertDialog.setTitle("Sử dụng chức năng này cần Bật GPS");
        alertDialog.setPositiveButton("Cài đặt", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 97);
                dialogInterface.dismiss();
            }
        });
        alertDialog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        alertDialog.show();
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
        if (data.size() == 0)
            return;
        count++;
        xLog.e(TAG, "onGetListHealtyCareSuccess: " + count + "PHILOG");
        for (int i = 0; i < data.size(); i++) {

            Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(data.get(i).getLat(), data.get(i).getLng())).title(data.get(i).getName()));
            marker.setIcon(BitmapDescriptorFactory.fromBitmap(scaleBimap(R.drawable.marker_hospiotal)));
//
//             fix data C o so y te
//            if (data.get(i).getType() == 1) {
//                marker.setIcon(BitmapDescriptorFactory.fromBitmap(scaleBimap(R.drawable.marker_hospiotal)));
//            } else
//                marker.setIcon(BitmapDescriptorFactory.fromBitmap(scaleBimap(R.drawable.ic_pharmacy)));
//            marker.setTag(data.get(i).getId());
        }
        onLoadMapSuccessListener.onLoadMapSuccess(data);

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        if (marker.getTag() == null)
            return false;
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
        xLog.e(TAG, "onCameraMoveCanceled: " + mMap.getCameraPosition().target.latitude + "           " + mMap.getCameraPosition().target.longitude);
        presenter.checkGetHospital(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);
    }

    //    @Override
//    public void onCameraMove() {
//        xLog.e(mMap.getCameraPosition().target.latitude + "           " + mMap.getCameraPosition().target.longitude);
//        presenter.checkGetHospital(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);
//    }
    @Override
    public void onCameraIdle() {
            xLog.e(TAG, "onCameraIdle:" + mMap.getCameraPosition().target.latitude + "           " + mMap.getCameraPosition().target.longitude);
            presenter.checkGetHospital(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);
//        listTemp(mMap.getCameraPosition().target);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 97) {
            xLog.e("onActivityResult ", resultCode + " ");
            presenter.registerLocation();
        }
    }

    List<Marker> list = new ArrayList<>();

    private void listTemp(LatLng center) {
        if (list.size() > 0) {
            for (Marker marker : list
                    ) {
                marker.remove();
            }
        }
        for (int i = 0; i < 20; i++) {
            LatLng lng = toRadiusLatLng(center, 1000);
//            RESP_Map_Healthy_Care care = new RESP_Map_Healthy_Care();
//            care.setId(i);
//            care.setAddress("Unknown");
//            care.setName("Unknown");
//            care.setType((i % 2 == 0) ? 0 : 1);
//            care.setLatitude(lng.latitude);
//            care.setLongitude(lng.longitude);
//            list.add(care);
            Marker marker = mMap.addMarker(new MarkerOptions().position(lng));
            if (i % 2 == 0) {
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(scaleBimap(R.drawable.marker_hospiotal)));
            } else
                marker.setIcon(BitmapDescriptorFactory.fromBitmap(scaleBimap(R.drawable.ic_pharmacy)));
            marker.setTag(i);
            list.add(marker);

        }


    }

    private static LatLng toRadiusLatLng(LatLng center, double radius) {

        Random random = new Random();
        double radiusAngle = Math.toDegrees(radius / 6371009) /
                Math.cos(Math.toRadians(center.latitude));
        double maxLat = center.latitude + radiusAngle;
        double minLat = center.latitude - radiusAngle;
        double maxLon = center.longitude + radiusAngle;
        double minLon = center.longitude - radiusAngle;

        double foundLat = random.nextDouble() * (maxLat - minLat) + minLat;
        double foundLon = random.nextDouble() * (maxLon - minLon) + minLon;

        return new LatLng(foundLat, foundLon);
    }

}
