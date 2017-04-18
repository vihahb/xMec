package com.xtelsolution.xmec.xmec.views.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.Medicine;
import com.xtelsolution.xmec.model.RESP_Disease_Detail;
import com.xtelsolution.xmec.model.RESP_User_Medicine;
import com.xtelsolution.xmec.presenter.DiseaseDetailPresenter;
import com.xtelsolution.xmec.xmec.views.adapter.MedicineAdapter;
import com.xtelsolution.xmec.xmec.views.adapter.MedicineAdapterOptionButton;
import com.xtelsolution.xmec.xmec.views.inf.IDiseaseDetailView;

import java.util.ArrayList;
import java.util.List;

public class UserDiseaseDetailActivity extends BasicActivity implements IDiseaseDetailView, ItemClickListener {
    private Toolbar mToolbar;
    private Context mContext;
    private TextView tvName;
    private TextView tvNote;
    private TextView tvToolbarTitle;
    private TextView btnViewDisease;
    private RecyclerView rvMedicine;
    private List<RESP_User_Medicine> medicines;
    private MedicineAdapterOptionButton adapter;
    private DiseaseDetailPresenter presenter;
    private RESP_Disease_Detail diseaseDetail;
    private int idDisease = -1;
    private int idMedical = -1;
    private CoordinatorLayout progressbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_disease_detail);
        initUI();
        initControl();
        initRecyclerView();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        tvToolbarTitle.setText(getResources().getString(R.string.disease_detail));
        idDisease = getIntent().getIntExtra(Constant.DISEASE_ID, -1);
        idMedical = getIntent().getIntExtra(Constant.MEDICAL_ID, -1);

        presenter = new DiseaseDetailPresenter(this);
        presenter.checkGetDiseaseDetail(idDisease);

    }

    private void initRecyclerView() {
        medicines = new ArrayList<>();
        adapter = new MedicineAdapterOptionButton(medicines, false, mContext);
        adapter.setItemClickListener(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            rvMedicine.setAdapter(adapter);
            rvMedicine.setLayoutManager(new LinearLayoutManager(getBaseContext()));
            rvMedicine.setNestedScrollingEnabled(false);
        }

    }

    private void initControl() {
        btnViewDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                showToast(diseaseDetail.getId_disease());

                String link = diseaseDetail.getLink();
                if (!link.equals("")) {
                    Intent i = new Intent(UserDiseaseDetailActivity.this, DetailDiseaseActivity.class);
                    i.putExtra(Constant.ILLNESS_URL, link);
                    startActivity(i);
                }
            }
        });

    }

    private void initUI() {

        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        tvToolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        mContext = getApplicationContext();
        tvName = (TextView) findViewById(R.id.tv_disease_name);
        tvNote = (TextView) findViewById(R.id.tv_disease_note);
        rvMedicine = (RecyclerView) findViewById(R.id.rv_medicine);
        btnViewDisease = (TextView) findViewById(R.id.btn_view_disease);
        progressbar = (CoordinatorLayout) findViewById(R.id.progress_bar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_edit_disease, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            case R.id.action_remove_medical:
                Intent i = new Intent(UserDiseaseDetailActivity.this, EditDiseaseActivity.class);
                i.putExtra(Constant.DISEASE_DETAIL, diseaseDetail);
                i.putExtra(Constant.MEDICAL_ID, idMedical);
                startActivityForResult(i, Constant.EDIT_USER_DISEASE);
                break;
        }
        return false;
    }

    @Override
    public void onLoadDiseaseDetailSuccess(RESP_Disease_Detail diseaseDetail) {
        this.diseaseDetail = diseaseDetail;
        diseaseDetail.setId(idDisease);
//        idDisease = diseaseDetail.getId_disease();
        tvName.setText(diseaseDetail.getTen_benh());
        tvNote.setText(diseaseDetail.getNote());
        progressbar.setVisibility(View.GONE);
        if (diseaseDetail.getData() != null)
            adapter.addAll(diseaseDetail.getData());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.EDIT_USER_DISEASE) {
            if (resultCode == Activity.RESULT_OK) {
                adapter.clearAll();
                presenter.checkGetDiseaseDetail(idDisease);
                setResult(Activity.RESULT_OK);
            }
        }
    }

    @Override
    public void onItemClickListener(Object item, int position) {
        RESP_User_Medicine medicine = (RESP_User_Medicine) item;
        if (medicine.getId_drug() != -1) {
            Intent i = new Intent(UserDiseaseDetailActivity.this, MedicineDetailActivity.class);
            i.putExtra(Constant.INTENT_ID_MEDICINE, medicine.getId_drug());
            startActivity(i);
        }
    }
}
