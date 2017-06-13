package com.xtelsolution.xmec.views.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.model.RESP_Healthy_Care_Detail;
import com.xtelsolution.xmec.presenter.HeathyCareDetailPresenter;
import com.xtelsolution.xmec.views.inf.IHeathyCareDetailView;

public class DetailHospitalActivity extends BasicActivity implements OnMapReadyCallback, IHeathyCareDetailView {
    private static final String TAG = "DetailHospitalActivity";
    private Toolbar mToolbar;

    private TextView tvName;
    private TextView tvNumPhone;
    private TextView tvFax;
    private TextView tvAddress;
    private TextView tvIntroduce;
    private TextView tvWorkTime;
    private TextView tvVoteRate;
    private ImageView imgAvatar;
    private int id;
    private GoogleMap mMap;
    private HeathyCareDetailPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_hospital);
        initUI();
        initControl();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        id = getIntent().getIntExtra(Constant.HEALTHY_CENTER_ID, -1);
        presenter = new HeathyCareDetailPresenter(this);


    }


    private void initControl() {

    }

    private void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        tvAddress = (TextView) findViewById(R.id.tv_address);
        tvName = (TextView) findViewById(R.id.tv_hospital_name);
        tvNumPhone = (TextView) findViewById(R.id.tv_tel);
        tvFax = (TextView) findViewById(R.id.tv_fax);
        tvIntroduce = (TextView) findViewById(R.id.tv_introduce);
        tvWorkTime = (TextView) findViewById(R.id.tv_work_time);
        tvVoteRate = (TextView) findViewById(R.id.tv_vote);
        imgAvatar = (ImageView) findViewById(R.id.img_avt);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.getUiSettings().setAllGesturesEnabled(false);
        presenter.checkGetHealthCare(id);
//        presenter.getDetail("https://vicare.vn/benh-vien-k-co-so-1-14415/gay-me-hoi-suc");
    }

    @Override
    public void onGetHeathyCareSuccess(RESP_Healthy_Care_Detail healthyCare) {
        tvAddress.setText(getResources().getString(R.string.address) + healthyCare.getAddress());
//        tvFax.setText(getResources().getString(R.string.fax) + healthyCare.getFax());
        tvName.setText(healthyCare.getName());
//        tvIntroduce.setText(healthyCare.getIntroduce());
        tvVoteRate.setText(((double) healthyCare.getVote_rate()) / 10 + " ");
//        xLog.e(TAG, "onGetHeathyCareSuccess: " + healthyCare.getVote_rate() + "33");
//        tvWorkTime.setText(healthyCare.getOpenTime() + "");
//        xLog.e(TAG, "onGetHeathyCareSuccess: " + healthyCare.getOpenTime() + "33");
//        if (healthyCare.getUrl_avatar() != null)
//            setImage(imgAvatar, healthyCare.getUrl_avatar());
//        else
//            imgAvatar.setImageResource(R.drawable.ic_avatar_hospital);
        LatLng latLng = new LatLng(healthyCare.getLat(), healthyCare.getLng());
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 14));
        mMap.addMarker(new MarkerOptions().position(latLng));
    }

    @Override
    public void onGetHealCareSuccess(String opentime, String description, String phone, String url) {
        if (phone != null)
            tvNumPhone.setText(getResources().getString(R.string.tel) + phone);
        else
            tvNumPhone.setText(getResources().getString(R.string.tel) + "");
        tvIntroduce.setText(description);
        if (opentime != null)
            tvWorkTime.setText(opentime);
        else
            tvWorkTime.setText("");
        if (url != null)
            setImage(imgAvatar, url);

    }


    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
