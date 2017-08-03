package com.xtelsolution.xmec.views.fragment;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

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
import com.xtelsolution.xmec.views.adapter.HospitalCenterAutoCompliteAdapter;
import com.xtelsolution.xmec.views.inf.IMapView;
import com.xtelsolution.xmec.views.smallviews.DelayAutoCompleteTextView;
import com.xtelsolution.xmec.views.widget.CustomClusterManager;
import com.xtelsolution.xmec.views.widget.CustomClusterRender;

import java.util.List;

import javax.annotation.Nullable;

/**
 * Created by HUNGNT on 1/18/2017.
 */

public class MapFragment extends BasicFragment implements
        OnMapReadyCallback, IMapView, GoogleMap.OnCameraMoveCanceledListener,
        GoogleMap.OnCameraIdleListener, CustomClusterManager.CameraIdle,
        ClusterManager.OnClusterItemClickListener, GoogleMap.OnInfoWindowClickListener {
    private static final String TAG = "MapFragment";
    int count = 0;
    private View view;
    private GoogleMap mMap;
    private Marker mMarker;
    private FloatingActionButton btnCurrentLocation;
    private HospitalCenterAutoCompliteAdapter findHospital;
    private DelayAutoCompleteTextView etFindHospital;
    private boolean isCheckPermission;
    private MapPresenter presenter;
    private CoordinatorLayout locationPermission;
    private ProgressBar progressBar;
    private Button btnInitPermission;
    private boolean isMapCreated = false;
    private CustomClusterRender customClusterRender;
    private HospitalClusterItem clickedItem;
    private CustomClusterManager clusterManager;
    private BroadcastReceiver receiver;

    public static MapFragment newInstance() {

        Bundle args = new Bundle();

        MapFragment fragment = new MapFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@android.support.annotation.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        showToast("Đã tạo ");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (view == null) {
            view = inflater.inflate(R.layout.fragment_health_center, container, false);
            initview();
            initAdapter();
            initControl();
            presenter = new MapPresenter(this);
        }
        initBroad();
        return view;
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
        etFindHospital = (DelayAutoCompleteTextView) getActivity().findViewById(R.id.ed_search);
        progressBar = (ProgressBar) getActivity().findViewById(R.id.pb_loading_indicator);

    }

    private void initAdapter() {
        findHospital = new HospitalCenterAutoCompliteAdapter(getActivity());
        etFindHospital.setThreshold(3);
        etFindHospital.setLoadingIndicator(progressBar);
        etFindHospital.setAdapter(findHospital);
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
        etFindHospital.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RESP_Map_Healthy_Care healthyCare = (RESP_Map_Healthy_Care) findHospital.getItem(position);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(healthyCare.getLat(), healthyCare.getLng()), 18f));
                presenter.addHealThyCare(healthyCare);
            }
        });
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
        customClusterRender = new CustomClusterRender(getContext(), mMap, clusterManager);
        mMap.setOnCameraIdleListener(clusterManager);
        clusterManager.setOnClusterItemClickListener(this);
        clusterManager.setAnimation(true);
        mMap.setOnMarkerClickListener(clusterManager);
        clusterManager.setCameraIdle(this);
        clusterManager.setRenderer(customClusterRender);
        mMap.setOnCameraMoveCanceledListener(this);
        HospitalInfoWindowAdapter windowAdapter = new HospitalInfoWindowAdapter();
        mMap.setInfoWindowAdapter(windowAdapter);
        mMap.setOnInfoWindowClickListener(this);
        clusterManager.getMarkerCollection().setOnInfoWindowAdapter(windowAdapter);
        clusterManager.cluster();


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
    public void onAddHealThyCareSuccess(RESP_Map_Healthy_Care data, boolean isNew) {
        LatLng latLng = new LatLng(data.getLat(), data.getLng());
        final HospitalClusterItem hospital = new HospitalClusterItem(latLng, data.getName(), data.getId(), data.getType(), data.getAddress());
        clusterManager.addItem(hospital);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Marker marker = customClusterRender.getMarker(hospital);
                clickedItem = hospital;
                marker.showInfoWindow();
            }
        }, 500);

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
        for (int i = 0; i < data.size(); i++) {
            LatLng latLng = new LatLng(data.get(i).getLat(), data.get(i).getLng());
            HospitalClusterItem hospital = new HospitalClusterItem(latLng, data.get(i).getName(), data.get(i).getId(), data.get(i).getType(), data.get(i).getAddress());
            clusterManager.addItem(hospital);
        }
        mMap.moveCamera(CameraUpdateFactory.zoomTo(mMap.getCameraPosition().zoom + 0.001f));

    }


    @Override
    public void onCameraMoveCanceled() {
        xLog.e(TAG, "onCameraMoveCanceled: " + mMap.getCameraPosition().target.latitude + "           " + mMap.getCameraPosition().target.longitude);
        presenter.checkGetHospital(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);
    }

    @Override
    public void onCameraIdle() {
        xLog.e(TAG, "onCameraIdle:" + mMap.getCameraPosition().target.latitude + "           " + mMap.getCameraPosition().target.longitude);
        presenter.checkGetHospital(mMap.getCameraPosition().target.latitude, mMap.getCameraPosition().target.longitude);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 97) {
            xLog.e("onActivityResult ", resultCode + " ");
            presenter.registerLocation();
        }
    }


    @Override
    public boolean onClusterItemClick(ClusterItem clusterItem) {
        HospitalClusterItem item = (HospitalClusterItem) clusterItem;
        clickedItem = item;
        return false;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_find_health_cennter, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.search_view).getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);

    }

    @Override
    public void onInfoWindowClick(Marker marker) {
        Intent i = new Intent(getActivity(), DetailHospitalActivity.class);
        i.putExtra(Constant.HEALTHY_CENTER_ID, clickedItem.getIdHospital());
        startActivity(i);
    }


    public class HospitalInfoWindowAdapter implements GoogleMap.InfoWindowAdapter {

        private View view;

        public HospitalInfoWindowAdapter() {
            this.view = getActivity().getLayoutInflater().inflate(R.layout.item_window_info, null);
        }

        @Override
        public View getInfoWindow(Marker marker) {
            ImageView img = (ImageView) view.findViewById(R.id.img_avatar);
            TextView tvName = (TextView) view.findViewById(R.id.tv_name);
            TextView tvAdress = (TextView) view.findViewById(R.id.tv_address);

            if (clickedItem.getType() == 0)
                img.setImageResource(R.drawable.marker_hospiotal);
            else
                img.setImageResource(R.drawable.ic_pharmacy);
            tvName.setText(clickedItem.getTitle());
            tvAdress.setText(clickedItem.getAddress());
            return view;
        }

        @Override
        public View getInfoContents(Marker marker) {
            return null;
        }
    }
}

