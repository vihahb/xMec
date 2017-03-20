package com.xtelsolution.xmec.xmec.views.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.elyeproj.loaderviewlibrary.LoaderTextView;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.listener.list.ButtonAdapterClickListener;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.RESP_Disease;
import com.xtelsolution.xmec.model.RESP_List_Disease;
import com.xtelsolution.xmec.model.RESP_Medical_Detail;
import com.xtelsolution.xmec.model.Resource;
import com.xtelsolution.xmec.model.entity.Disease;
import com.xtelsolution.xmec.presenter.MedicalDetailPresenter;
import com.xtelsolution.xmec.xmec.views.adapter.HealtRecoderAdapter;
import com.xtelsolution.xmec.xmec.views.adapter.IllnessAdapter2;
import com.xtelsolution.xmec.xmec.views.adapter.ImageViewAdapter;
import com.xtelsolution.xmec.xmec.views.inf.IMedicalDetailView;

import java.util.ArrayList;
import java.util.List;

public class MedicalDetailActivity extends BasicActivity implements IMedicalDetailView, ItemClickListener, ButtonAdapterClickListener {
    private Toolbar mToolbar;
    private TextView toolBarTille;
    private Context mContext;
    private RecyclerView rcDesease;
    private RecyclerView rcHealthReconder;
    private HealtRecoderAdapter healtRecoderAdapter;
    private ImageViewAdapter imageViewAdapter;
    private ViewPager viewPager;
    private Dialog dialog;
    private IllnessAdapter2 illnessAdapter;
    private LoaderTextView tvNote;
    private LoaderTextView tvName;
    private LoaderTextView tvTime;
    private TextView btnUpdateMedical;
    private MedicalDetailPresenter presenter;
    private int id;
    private List<Resource> listUrl;
    private ArrayList<RESP_Disease> diseases;


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
        illnessAdapter = new IllnessAdapter2(mContext, diseases);
        illnessAdapter.setButtonAdapterClickListener(this);
        illnessAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(Object item, int position) {
                String link =((RESP_Disease) item).getLink();
                if ( link!= null&&link.length()==0) {
                    Intent i = new Intent(MedicalDetailActivity.this, IllnessDetailActivity.class);
                    i.putExtra(Constant.ILLNESS_URL, link);
                    startActivity(i);
                }
            }
        });
        rcHealthReconder.setAdapter(healtRecoderAdapter);
        rcHealthReconder.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));


        viewPager.setAdapter(imageViewAdapter);

        healtRecoderAdapter.setOnItemClickListener(this);

        rcDesease.setAdapter(illnessAdapter);
        rcDesease.setLayoutManager(new LinearLayoutManager(mContext));
        id = getIntent().getIntExtra(Constant.MEDICAL_ID, -1);

        presenter.getDetailMedical(id);
//        presenter.getListIllness(id);

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
        tvName = (LoaderTextView) findViewById(R.id.tv_name);
        tvNote = (LoaderTextView) findViewById(R.id.tv_note);
        tvTime = (LoaderTextView) findViewById(R.id.tv_time);
        rcDesease = (RecyclerView) findViewById(R.id.rcDisease);
        finishActivity(R.id.btn_update_medical);
        rcHealthReconder = (RecyclerView) findViewById(R.id.rc_healthy_recoder);
        dialog = new Dialog(this, android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.image_viewer_dialog);
        viewPager = (ViewPager) dialog.findViewById(R.id.viewpager);
        btnUpdateMedical = (TextView) findViewById(R.id.btn_update_medical);

    }


    @Override
    public void onLoadMedicalFinish(RESP_Medical_Detail obj) {
        Log.e("MY", "onLoadMedicalFinish: " + obj.getResources().size());
        healtRecoderAdapter.addAll(obj.getResources());
        imageViewAdapter.notifyDataSetChanged();
        tvTime.setText(timePaser(obj.getBegin_time(), obj.getEnd_time()));
        tvNote.setText(obj.getNote());
        tvName.setText(obj.getName());
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
    public void onItemClickListener(Object item, int position) {
        dialog.show();
    }

    @Override
    public void onButtonAdapterClickListener(Button button) {
        Intent i = new Intent(MedicalDetailActivity.this, AddIllnessActivity.class);
        i.putExtra(Constant.MEDICAL_ID, id);
        startActivity(i);
    }

    @Override
    public void onRemoveButtonClick(Object obj, int position) {

    }
}
