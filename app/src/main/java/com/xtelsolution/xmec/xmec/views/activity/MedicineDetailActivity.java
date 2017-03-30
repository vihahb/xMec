package com.xtelsolution.xmec.xmec.views.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.model.Medicine;

public class MedicineDetailActivity extends BasicActivity {
    private static final String TAG = "MedicineDetailActivity";
    private Context mContext;
    private TextView tv_ten_thuoc,
            tv_dang_bao_che,
            tv_nhom_duoc_ly,
            tv_thanh_phan,
            tv_chi_dinh,
            tv_chong_chi_dinh,
            tv_tuong_tac_thuoc,
            tv_tac_dung_phu,
            chu_y_de_phong,
            tv_lieu_luong,
            tv_bao_quan;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medicine_detail);
        mContext = this;
        initUI();
        showToast(getIntent().getIntExtra(Constant.INTENT_ID_MEDICINE, -1) + "");
    }

    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("");
        tv_ten_thuoc = (TextView) findViewById(R.id.tv_ten_thuoc);
        tv_dang_bao_che = (TextView) findViewById(R.id.tv_dang_bao_che);
        tv_nhom_duoc_ly = (TextView) findViewById(R.id.tv_nhom_duoc_ly);
        tv_thanh_phan = (TextView) findViewById(R.id.tv_thanh_phan);
        tv_chi_dinh = (TextView) findViewById(R.id.tv_chi_dinh);
        tv_chong_chi_dinh = (TextView) findViewById(R.id.tv_chong_chi_dinh);
        tv_tuong_tac_thuoc = (TextView) findViewById(R.id.tv_tuong_tac_thuoc);
        tv_tac_dung_phu = (TextView) findViewById(R.id.tv_tac_dung_phu);
        chu_y_de_phong = (TextView) findViewById(R.id.chu_y_de_phong);
        tv_lieu_luong = (TextView) findViewById(R.id.tv_lieu_luong);
        tv_bao_quan = (TextView) findViewById(R.id.tv_bao_quan);
    }

    private void setDataToView(Medicine medicine) {
        tv_ten_thuoc.setText(getString(R.string.namet_medicen_text, medicine.getName()));
        tv_dang_bao_che.setText(getString(R.string.dang_bao_che, medicine.getType()));
        tv_nhom_duoc_ly.setText(getString(R.string.nhom_duoc_ly, medicine.getGroup()));
        tv_thanh_phan.setText(getString(R.string.thanh_phan, medicine.getComponent()));
        tv_chi_dinh.setText(getString(R.string.chi_dinh, medicine.getIndication()));
        tv_chong_chi_dinh.setText(getString(R.string.chong_chi_dinh, medicine.getContraindication()));
        tv_tuong_tac_thuoc.setText(getString(R.string.tuong_tac_thuoc, medicine.getDrugInteraction()));
        tv_tac_dung_phu.setText(getString(R.string.tac_dung_phu, medicine.getSidEeffect()));
        chu_y_de_phong.setText(getString(R.string.chu_y_de_phong, medicine.getWarning()));
        tv_lieu_luong.setText(getString(R.string.lieu_luong, medicine.getDosage()));
        tv_bao_quan.setText(getString(R.string.bao_quan, medicine.getPreservation()));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) finish();
        return super.onOptionsItemSelected(item);
    }
}
