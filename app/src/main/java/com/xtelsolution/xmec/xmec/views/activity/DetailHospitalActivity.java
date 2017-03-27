package com.xtelsolution.xmec.xmec.views.activity;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.ImageView;
import android.widget.TextView;

import com.elyeproj.loaderviewlibrary.LoaderImageView;
import com.elyeproj.loaderviewlibrary.LoaderTextView;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.xtel.nipservicesdk.utils.PermissionHelper;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.model.HealthyCareModel;
import com.xtelsolution.xmec.model.RESP_Healthy_Care_Detail;
import com.xtelsolution.xmec.presenter.HeathyCareDetailPresenter;
import com.xtelsolution.xmec.xmec.views.inf.IHeathyCareDetailView;

public class DetailHospitalActivity extends BasicActivity implements OnMapReadyCallback,IHeathyCareDetailView {
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

        id = getIntent().getIntExtra(Constant.HEALTHY_CENTER_ID,-1);
        presenter = new HeathyCareDetailPresenter(this);
        presenter.checkGetHealthCare(id);

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
        tvVoteRate =(TextView) findViewById(R.id.tv_vote);
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
        googleMap.getUiSettings().setAllGesturesEnabled(false);
    }

    @Override
    public void onGetHeathyCareSuccess(RESP_Healthy_Care_Detail healthyCare) {
        tvAddress.setText(getResources().getString(R.string.address)+healthyCare.getAddress());
        tvFax.setText(getResources().getString(R.string.fax)+healthyCare.getFax());
        tvNumPhone.setText(getResources().getString(R.string.tel)+healthyCare.getNum_phone());
        tvName.setText(healthyCare.getName());
        tvIntroduce.setText(healthyCare.getIntroduce());
        tvVoteRate.setText(healthyCare.getVote_rate());
        tvWorkTime.setText(healthyCare.getOpenTime());
        if (healthyCare.getUrl_avatar()!=null)
            setImage(imgAvatar,healthyCare.getUrl_avatar());
        else
            imgAvatar.setImageResource(R.drawable.ic_avatar_hospital);

    }


    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    public void showToast(String msg) {

    }

    @Override
    public void showLog(String msg) {

    }

    @Override
    public void showProgressDialog(String title) {

    }

    @Override
    public void dismissProgressDialog() {

    }
}
