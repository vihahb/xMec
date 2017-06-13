package com.xtelsolution.xmec.views.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.ClusterItem;
import com.google.maps.android.clustering.ClusterManager;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.model.RESP_Map_Healthy_Care;
import com.xtelsolution.xmec.model.entity.HospitalClusterItem;
import com.xtelsolution.xmec.presenter.MapPresenter;
import com.xtelsolution.xmec.views.activity.DetailHospitalActivity;
import com.xtelsolution.xmec.views.inf.IMapView;
import com.xtelsolution.xmec.views.widget.CustomClusterManager;
import com.xtelsolution.xmec.views.widget.CustomClusterRender;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//import yalantis.com.sidemenu.interfaces.ScreenShotable;

/**
 * Created by HUNGNT on 1/18/2017.
 */

public class MapFragment extends BasicFragment implements/* ScreenShotable,*/
        OnMapReadyCallback, IMapView, GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraIdleListener, CustomClusterManager.CameraIdle,
        ClusterManager.OnClusterItemClickListener {
    private static final String TAG = "MapFragment";

    public static MapFragment newInstance() {

        Bundle args = new Bundle();

        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private View view;
    private GoogleMap mMap;
    private Marker mMarker;
    private FloatingActionButton btnCurrentLocation;
    private boolean isCheckPermission;
//    private IMapView onLoadMapSuccessListener;
    private MapPresenter presenter;
    private CoordinatorLayout locationPermission;
    private Button btnInitPermission;
    private boolean isMapCreated = false;
    private CustomClusterManager clusterManager;
    private BroadcastReceiver receiver;
    int count = 0;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_health_center, container, false);
//            onLoadMapSuccessListener = ((HomeActivity) getActivity()).get();
            initview();
            initControl();
            presenter = new MapPresenter(this);
        }
        initBroad();
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

    private void initBroad() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constant.ACTION_LOCATION)) {
                    presenter.getCurrentLocation();
                } else if (intent.getAction().equals(Constant.ACTION_PERMISSION_LOCATION)) {
                    presenter.initPermission();
                } else if (intent.getAction().equals(Constant.ACTION_GPS_ENABLE)) {

                } else if (intent.getAction().equals(Constant.ACTION_RELOA_DATA_MAP)) {
                    if (isMapCreated)
                        presenter.checkGetHospital(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_LOCATION);
        filter.addAction(Constant.ACTION_PERMISSION_LOCATION);
        filter.addAction(Constant.ACTION_GPS_ENABLE);
        filter.addAction(Constant.ACTION_RELOA_DATA_MAP);
        getActivity().registerReceiver(receiver, filter);
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

        clusterManager = new CustomClusterManager(getContext(), mMap);
        mMap.setOnCameraIdleListener(clusterManager);
        clusterManager.setOnClusterItemClickListener(this);
        clusterManager.setAnimation(true);
//        clusterManager.setAlgorithm();
        mMap.setOnMarkerClickListener(clusterManager);
        clusterManager.setCameraIdle(this);
        clusterManager.setRenderer(new CustomClusterRender(getContext(), mMap, clusterManager));
        mMap.setOnCameraMoveCanceledListener(this);

    }

    @Override
    public void onMapCreateSuccess() {
        isCheckPermission = true;
        initMap();
//        onLoadMapSuccessListener.onMapCreateSuccess();
    }

    @Override
    public void onProviderDisabled() {
//        onLoadMapSuccessListener.onProviderDisabled();
        showToast("Bạn chưa bật chia sẻ vị trí");
    }

    @Override
    public void onGetCurrentLocationFinish(LatLng latLng) {
        Log.e("MY", "onGetCurrentLocationFinish: " + latLng.longitude + "   " + latLng.latitude);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14f));
//        onLoadMapSuccessListener.onGetCurrentLocationFinish(latLng);
    }

    @Override
    public void onLocationChange(LatLng latLng) {
        mMarker.setPosition(latLng);
//        xLog.e("onLocationChange", latLng.toString());
//        onLoadMapSuccessListener.onLocationChange(latLng);
    }

    @Override
    public void onPermissionDenied() {
        locationPermission.setVisibility(View.VISIBLE);
//        onLoadMapSuccessListener.onPermissionDenied();
    }

    @Override
    public void onPermissionGranted() {
        locationPermission.setVisibility(View.GONE);
//        onLoadMapSuccessListener.onPermissionGranted();
    }

    @Override
    public void onGPSDisabled() {
//        onLoadMapSuccessListener.onGPSDisabled();
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

//            Marker marker = mMap.addMarker(new MarkerOptions().position(new LatLng(data.get(i).getLat(), data.get(i).getLng())).title(data.get(i).getName()));
////
////             fix data C o so y te
//            if (data.get(i).getType() == 0) {
//                marker.setIcon(BitmapDescriptorFactory.fromBitmap(scaleBimap(R.drawable.marker_hospiotal)));
//            } else
//                marker.setIcon(BitmapDescriptorFactory.fromBitmap(scaleBimap(R.drawable.ic_pharmacy)));
//            marker.setTag(data.get(i).getId());
            LatLng latLng = new LatLng(data.get(i).getLat(), data.get(i).getLng());
            HospitalClusterItem hospital = new HospitalClusterItem(latLng, data.get(i).getName(), data.get(i).getId(), data.get(i).getType());
            clusterManager.addItem(hospital);
        }
        mMap.moveCamera(CameraUpdateFactory.zoomTo(mMap.getCameraPosition().zoom + 0.001f));
//        onLoadMapSuccessListener.onGetListHealtyCareSuccess(data);

    }


    private Bitmap scaleBimap(int id) {
        Bitmap b = BitmapFactory.decodeResource(getResources(), id);
        Bitmap bhalfsize = Bitmap.createScaledBitmap(b, 34, 64, false);
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

    @Override
    public boolean onClusterItemClick(ClusterItem clusterItem) {
        HospitalClusterItem item = (HospitalClusterItem) clusterItem;
        Intent i = new Intent(getActivity(), DetailHospitalActivity.class);
        i.putExtra(Constant.HEALTHY_CENTER_ID, item.getIdHospital());
        startActivity(i);
        return false;
    }

   /* @Override
    public void takeScreenShot() {

    }

    @Override
    public Bitmap getBitmap() {
        return null;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        try {
            getActivity().unregisterReceiver(receiver);
        } catch (Exception e) {
            Log.e(TAG, "onDestroy: ", new Throwable(e));
        }
    }*/
}
