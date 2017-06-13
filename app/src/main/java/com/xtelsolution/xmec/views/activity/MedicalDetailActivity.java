package com.xtelsolution.xmec.views.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.RESP_Disease;
import com.xtelsolution.xmec.model.RESP_Medical_Detail;
import com.xtelsolution.xmec.model.Resource;
import com.xtelsolution.xmec.presenter.MedicalDetailPresenter;
import com.xtelsolution.xmec.views.adapter.HealtRecoderAdapter;
import com.xtelsolution.xmec.views.adapter.IllnessAdapterWithEditButton;
import com.xtelsolution.xmec.views.adapter.ImageViewAdapter;
import com.xtelsolution.xmec.views.fragment.MedicineInMedicalDetailFragment;
import com.xtelsolution.xmec.views.inf.IMedicalDetailView;

import java.util.ArrayList;
import java.util.List;

public class MedicalDetailActivity extends BasicActivity implements IMedicalDetailView,
        ItemClickListener, ItemClickListener.ButtonAdapterClickListener, ItemClickListener.ItemIconClickListener {
    private String TAG = "MedicalDetailActivity";
    private Toolbar mToolbar;
    private TextView toolBarTille;
    private Context mContext;
    private RecyclerView rcDesease;
    private RecyclerView rcHealthReconder;
    private HealtRecoderAdapter healtRecoderAdapter;
    private ImageViewAdapter imageViewAdapter;
    private ViewPager viewPager;
    private PagerAdapter mSectionsPagerAdapter;
    private ViewPager page;
    private Dialog dialog;
    private IllnessAdapterWithEditButton illnessAdapter;
    private TextView tvNote;
    private TextView tvName;
    private TextView tvTime;
    private TextView btnUpdateMedical;
    private MedicalDetailPresenter presenter;
    private int id;
    private int index = -1;
    private List<Resource> listUrl;
    private ArrayList<RESP_Disease> diseases;
    private CoordinatorLayout progcess;
    IMedicalDetailView medicalDetailView;

    public void sendData(IMedicalDetailView medicalDetailView) {
        this.medicalDetailView = medicalDetailView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_medical);
        id = getIntent().getIntExtra(Constant.MEDICAL_ID, -1);
        index = getIntent().getIntExtra(Constant.MEDICAL_INDEX, -1);

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
                i.putExtra(Constant.MEDICAL_ID, id);
                startActivityForResult(i, Constant.DETAIL_USER_DISEASE_CODR);

            }
        });
        rcHealthReconder.setAdapter(healtRecoderAdapter);
        rcHealthReconder.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));


        viewPager.setAdapter(imageViewAdapter);

        healtRecoderAdapter.setOnItemClickListener(this);

        rcDesease.setAdapter(illnessAdapter);
        rcDesease.setLayoutManager(new LinearLayoutManager(mContext));

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

        mSectionsPagerAdapter = new PagerAdapter(getSupportFragmentManager());

        page = (ViewPager) findViewById(R.id.viewPager);
        page.setAdapter(mSectionsPagerAdapter);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(page);
    }


    @Override
    public void onLoadMedicalFinish(RESP_Medical_Detail obj) {
        if (medicalDetailView!=null)   medicalDetailView.onLoadMedicalFinish(obj);
        Log.e("MY", "onLoadMedicalFinish: " + obj.getResources().size());
        if (obj.getResources() != null)
            healtRecoderAdapter.addAll(obj.getResources());
        imageViewAdapter.notifyDataSetChanged();
        tvTime.setText(timePaser(obj.getBegin_time(), obj.getEnd_time()));
        tvNote.setText(obj.getNote());
        tvName.setText(obj.getName());
        progcess.setVisibility(View.GONE);

    }

    @Override
    public void onLoadListIllnessFinish(List<RESP_Disease> data) {
      if (medicalDetailView!=null)  medicalDetailView.onLoadListIllnessFinish(data);
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
        if (requestCode == 95) {
            if (resultCode == Activity.RESULT_OK) {
                Intent i = new Intent();
                i.putExtra(Constant.MEDICAL_INDEX, index);
                setResult(Activity.RESULT_OK, i);
                finish();
            } else if (resultCode == Activity.RESULT_FIRST_USER) {
                String name = data.getStringExtra(Constant.MEDICAL_NAME);
                tvName.setText(name);
                tvNote.setText(data.getStringExtra(Constant.MEDICAL_NOTE));
                long medicalBeginTime = data.getLongExtra(Constant.MEDICAL_BEGIN_TIME, -1);
                tvTime.setText(timePaser(medicalBeginTime, data.getLongExtra(Constant.MEDICAL_END_TIME, -1)));
                Intent i = new Intent();
                i.putExtra(Constant.MEDICAL_NAME, name);
                i.putExtra(Constant.MEDICAL_BEGIN_TIME, medicalBeginTime);
                i.putExtra(Constant.MEDICAL_INDEX, index);
                setResult(Constant.REMOVE_MEDICAL_CODE, i);
            }
        } else if (requestCode == Constant.DETAIL_USER_DISEASE_CODR) {
            if (resultCode == Activity.RESULT_OK) {
                illnessAdapter.clearData();
                presenter.checkGetListIllness(id);
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
//        Intent i = new Intent(MedicalDetailActivity.this, AddIllnessActivity.class);
//        i.putExtra(Constant.MEDICAL_ID, id);
//        startActivity(i);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;

        }
        return false;
    }

    public class PagerAdapter extends FragmentPagerAdapter {

        public PagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).


            switch (position) {
                case 0:
                    return MedicineInMedicalDetailFragment.newInstance(id);
                case 1:
                    return MedicineInMedicalDetailFragment.newInstance(id);

            }

            return MedicineInMedicalDetailFragment.newInstance(id);

        }

        @Override
        public int getCount() {
            // Show 9 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Danh sách bênh";
                case 1:
                    return "Danh sách thuốc";
            }
            return null;
        }
    }
}
