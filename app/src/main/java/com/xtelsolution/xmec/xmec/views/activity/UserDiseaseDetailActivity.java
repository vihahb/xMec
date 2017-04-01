package com.xtelsolution.xmec.xmec.views.activity;

import android.content.Context;
import android.content.Intent;
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
import com.xtelsolution.xmec.model.Medicine;
import com.xtelsolution.xmec.model.RESP_Disease_Detail;
import com.xtelsolution.xmec.presenter.DiseaseDetailPresenter;
import com.xtelsolution.xmec.xmec.views.adapter.MedicineAdapter;
import com.xtelsolution.xmec.xmec.views.inf.IDiseaseDetailView;

import java.util.ArrayList;
import java.util.List;

public class UserDiseaseDetailActivity extends BasicActivity implements IDiseaseDetailView {
    private Toolbar mToolbar;
    private Context mContext;
    private TextView tvName;
    private TextView tvNote;
    private TextView tvToolbarTitle;
    private TextView btnViewDisease;
    private RecyclerView rvMedicine;
    private List<Medicine> medicines;
    private MedicineAdapter adapter;
    private DiseaseDetailPresenter presenter;
    private int idDisease;

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
        idDisease = getIntent().getIntExtra(Constant.DISEASE_ID,-1);


        presenter = new DiseaseDetailPresenter(this);
        presenter.checkGetDiseaseDetail(idDisease);

    }

    private void initRecyclerView() {
        medicines = new ArrayList<>();
        adapter = new MedicineAdapter(mContext,medicines);
        rvMedicine.setAdapter(adapter);
        rvMedicine.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        rvMedicine.setNestedScrollingEnabled(false);
    }

    private void initControl() {
        btnViewDisease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showToast("Đã click");
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
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(mContext);
        menuInflater.inflate(R.menu.menu_edit_disease, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_remove_medical:
                Intent i = new Intent(UserDiseaseDetailActivity.this,AddIllnessActivity.class);
                i.putExtra(Constant.DISEASE_ID,idDisease);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLoadDiseaseDetailSuccess(RESP_Disease_Detail diseaseDetail) {
        tvName.setText(diseaseDetail.getName());
        tvNote.setText(diseaseDetail.getNote());
        adapter.addAll(diseaseDetail.getMedicines());
    }
}
