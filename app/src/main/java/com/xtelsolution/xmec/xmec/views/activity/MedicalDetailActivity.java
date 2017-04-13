package com.xtelsolution.xmec.xmec.views.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.RESP_Disease;
import com.xtelsolution.xmec.model.RESP_Medical;
import com.xtelsolution.xmec.model.RESP_Medical_Detail;
import com.xtelsolution.xmec.model.Resource;
import com.xtelsolution.xmec.presenter.MedicalDetailPresenter;
import com.xtelsolution.xmec.xmec.views.adapter.HealtRecoderAdapter;
import com.xtelsolution.xmec.xmec.views.adapter.IllnessAdapterWithEditButton;
import com.xtelsolution.xmec.xmec.views.adapter.ImageViewAdapter;
import com.xtelsolution.xmec.xmec.views.inf.IMedicalDetailView;

import java.util.ArrayList;
import java.util.List;

public class MedicalDetailActivity extends BasicActivity implements IMedicalDetailView, ItemClickListener, ItemClickListener.ButtonAdapterClickListener, ItemClickListener.ItemIconClickListener {
    private String TAG = "MedicalDetailActivity";
    private Toolbar mToolbar;
    private TextView toolBarTille;
    private Context mContext;
    private RecyclerView rcDesease;
    private RecyclerView rcHealthReconder;
    private HealtRecoderAdapter healtRecoderAdapter;
    private ImageViewAdapter imageViewAdapter;
    private ViewPager viewPager;
    private Dialog dialog;
    private IllnessAdapterWithEditButton illnessAdapter;
    private TextView tvNote;
    private TextView tvName;
    private TextView tvTime;
    private TextView btnUpdateMedical;
    private MedicalDetailPresenter presenter;
    private int id;
    private int index=-1;
    private List<Resource> listUrl;
    private ArrayList<RESP_Disease> diseases;
    private CoordinatorLayout progcess;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_medical);
        initUI();
        initControl();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        mContext = getBaseContext();

        listUrl = new ArrayList<>();
        diseases = new ArrayList<>();
        presenter = new MedicalDetailPresenter(this);
        healtRecoderAdapter = new HealtRecoderAdapter(mContext, listUrl);
        imageViewAdapter = new ImageViewAdapter(listUrl, mContext);
        illnessAdapter = new IllnessAdapterWithEditButton(mContext, diseases);
        illnessAdapter.setButtonAdapterClickListener(this);
        illnessAdapter.setIconClickListener(this);
        illnessAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(Object item, int position) {
                int idDisease = ((RESP_Disease) item).getId();
                xLog.e(TAG, "onCreate: LINK  " + idDisease);
                Intent i = new Intent(MedicalDetailActivity.this, UserDiseaseDetailActivity.class);
                i.putExtra(Constant.DISEASE_ID, idDisease);
                i.putExtra(Constant.MEDICAL_ID,id);
                startActivity(i);

            }
        });
        rcHealthReconder.setAdapter(healtRecoderAdapter);
        rcHealthReconder.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));


        viewPager.setAdapter(imageViewAdapter);

        healtRecoderAdapter.setOnItemClickListener(this);

        rcDesease.setAdapter(illnessAdapter);
        rcDesease.setLayoutManager(new LinearLayoutManager(mContext));
        id = getIntent().getIntExtra(Constant.MEDICAL_ID, -1);
        index = getIntent().getIntExtra(Constant.MEDICAL_INDEX,-1);
        presenter.checkGetDetailMedical(id);
        presenter.checkGetListIllness(id);

    }

    private void initControl() {
        btnUpdateMedical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MedicalDetailActivity.this, AddMedicalDetailActivity.class);
                i.putExtra(Constant.MEDICAL_ID, id);
                startActivityForResult(i, 95);
            }
        });

    }

    private void initUI() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        toolBarTille = (TextView) findViewById(R.id.toolbar_title);
        toolBarTille.setText(getResources().getText(R.string.title_toobar_detail_medical));
        tvName = (TextView) findViewById(R.id.tv_name);
        tvNote = (TextView) findViewById(R.id.tv_note);
        tvTime = (TextView) findViewById(R.id.tv_time);
        rcDesease = (RecyclerView) findViewById(R.id.rcDisease);
        finishActivity(R.id.btn_update_medical);
        rcHealthReconder = (RecyclerView) findViewById(R.id.rc_healthy_recoder);
        dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.image_viewer_dialog);
        viewPager = (ViewPager) dialog.findViewById(R.id.viewpager);
        btnUpdateMedical = (TextView) findViewById(R.id.btn_update_medical);
        progcess = (CoordinatorLayout) findViewById(R.id.progress_bar);
    }


    @Override
    public void onLoadMedicalFinish(RESP_Medical_Detail obj) {
        Log.e("MY", "onLoadMedicalFinish: " + obj.getResources().size());
        healtRecoderAdapter.addAll(obj.getResources());
        imageViewAdapter.notifyDataSetChanged();
        tvTime.setText(timePaser(obj.getBegin_time(), obj.getEnd_time()));
        tvNote.setText(obj.getNote());
        tvName.setText(obj.getName());
        progcess.setVisibility(View.GONE);

    }

    @Override
    public void onLoadListIllnessFinish(List<RESP_Disease> data) {
        illnessAdapter.addAll(data);
    }

    @Override
    public void onUpdateMedicalFinish() {

    }

    @Override
    public void onRemoveMedicalSuccess() {

    }


    private String timePaser(long beginTime, long endTime) {
        return Constant.getDate(beginTime) + " - " + Constant.getDate(endTime);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==95){
            if (resultCode==Activity.RESULT_OK) {
                Intent i = new Intent();
                i.putExtra(Constant.MEDICAL_INDEX,index);
                setResult(Activity.RESULT_OK,i);
                finish();
            }
        }
    }

    @Override
    public void onItemClickListener(Object item, int position) {
        dialog.show();
    }

    @Override
    public void onButtonAdapterClickListener() {
        Intent i = new Intent(MedicalDetailActivity.this, AddIllnessActivity.class);
        i.putExtra(Constant.MEDICAL_ID, id);
        startActivity(i);
    }


    @Override
    public void onItemIconClickListener(Object item, int positon) {
        Intent i = new Intent(MedicalDetailActivity.this, AddIllnessActivity.class);
        i.putExtra(Constant.MEDICAL_ID, id);
        startActivity(i);
    }
}
