package com.xtelsolution.xmec.xmec.views.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.xtelsolution.xmec.entity.IIlness;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.xmec.views.adapter.HealtRecoderAdapter;
import com.xtelsolution.xmec.xmec.views.adapter.IIlnessAdapter2;

import java.util.ArrayList;

public class MedicalDirectoryActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private IIlnessAdapter2 mAdapter;
    private HealtRecoderAdapter healtRecoderAdapter;
    private RecyclerView rcDesease;
    private RecyclerView rvHealthReconder;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_directory);
        mContext = MedicalDirectoryActivity.this;
        init();

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        rcDesease.setAdapter(mAdapter);
        rcDesease.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        initControl();
        rvHealthReconder.setAdapter(healtRecoderAdapter);
        rvHealthReconder.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void initControl() {
        mAdapter.setOnItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClickListener(Object item, int position) {
                Intent i = new Intent(mContext, DetailDiseaseActivity.class);
                mContext.startActivity(i);
            }
        });
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_top);
        rcDesease = (RecyclerView) findViewById(R.id.rv_Desease);
        rvHealthReconder = (RecyclerView) findViewById(R.id.rv_health_records);

        ArrayList<IIlness> iIlnesses = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            iIlnesses.add(new IIlness("Tên bệnh "+i));
        }
        mAdapter = new IIlnessAdapter2(getBaseContext(), iIlnesses);
        healtRecoderAdapter = new HealtRecoderAdapter(mContext);
        rcDesease.setNestedScrollingEnabled(false);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


}
