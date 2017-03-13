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
import android.widget.TextView;

import com.elyeproj.loaderviewlibrary.LoaderTextView;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.RESP_List_IIlness;
import com.xtelsolution.xmec.model.REQ_Medical_Detail;
import com.xtelsolution.xmec.model.RESP_Medical_Detail;
import com.xtelsolution.xmec.model.Resource;
import com.xtelsolution.xmec.model.entity.Illness;
import com.xtelsolution.xmec.presenter.DetailMedicalPresenter;
import com.xtelsolution.xmec.xmec.views.adapter.HealtRecoderAdapter;
import com.xtelsolution.xmec.xmec.views.adapter.IllnessAdapter2;
import com.xtelsolution.xmec.xmec.views.adapter.ImageViewAdapter;
import com.xtelsolution.xmec.xmec.views.inf.IDetailMedicalView;

import java.util.ArrayList;
import java.util.List;

public class DetailMedicalActivity extends BasicActivity implements IDetailMedicalView,ItemClickListener {
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
    private DetailMedicalPresenter presenter;
    private int id;
    private List<Resource> listUrl;
    private ArrayList<Illness> illnesses;


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

        mContext =getBaseContext();

        listUrl = new ArrayList<>();
        illnesses = new ArrayList<>();
        presenter = new DetailMedicalPresenter(this);
        healtRecoderAdapter = new HealtRecoderAdapter(mContext,listUrl);
        imageViewAdapter = new ImageViewAdapter(listUrl,mContext);
        illnessAdapter = new IllnessAdapter2(mContext,illnesses);

        rcHealthReconder.setAdapter(healtRecoderAdapter);
        rcHealthReconder.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));


        viewPager.setAdapter(imageViewAdapter);

        healtRecoderAdapter.setOnItemClickListener(this);

        rcDesease.setAdapter(illnessAdapter);
        rcDesease.setLayoutManager(new LinearLayoutManager(mContext));
        id =getIntent().getIntExtra(Constant.MEDICAL_ID,-1);

        presenter.getDetailMedical(id);
       // presenter.getListIllness(id);

    }

    private void initControl() {
        btnUpdateMedical.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(DetailMedicalActivity.this,AddMedicalActivity.class);
                i.putExtra(Constant.MEDICAL_ID,id);
                startActivityForResult(i,95);
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
        dialog=new Dialog(this,android.R.style.Theme_Black_NoTitleBar_Fullscreen);
        dialog.setContentView(R.layout.image_viewer_dialog);
        viewPager = (ViewPager) dialog.findViewById(R.id.viewpager);

    }


    @Override
    public void onLoadMedicalFinish(RESP_Medical_Detail obj) {
        Log.e("MY", "onLoadMedicalFinish: "+obj.getResources().size());
       healtRecoderAdapter.addAll(obj.getResources());
        imageViewAdapter.notifyDataSetChanged();
        tvTime.setText(timePaser(obj.getBegin_time(),obj.getEnd_time()));
        tvNote.setText(obj.getNote());
        tvName.setText(obj.getName());
    }

    @Override
    public void onLoadListIllnessFinish(RESP_List_IIlness data) {
        illnessAdapter.addAll(data.getList());
    }

    @Override
    public void onUpdateMedicalFinish() {

    }

    private String timePaser(long beginTime,long endTime){
        return Constant.getDate(beginTime)+" - "+Constant.getDate(endTime);

    }

    @Override
    public void onItemClickListener(Object item, int position) {
        dialog.show();
    }
}
