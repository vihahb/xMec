package com.xtelsolution.xmec.xmec.views.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;

import com.xtelsolution.xmec.Entity.IIlness;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.xmec.views.adapter.HealtRecoderAdapter;
import com.xtelsolution.xmec.xmec.views.adapter.IIlnessAdapter2;

import java.util.ArrayList;

public class MedicalDirectoryActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private IIlnessAdapter2 mAdapter;
    private HealtRecoderAdapter healtRecoderAdapter;
    private RecyclerView rcDesease;
    private RecyclerView rvHealthReconder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_medical_directory);
        init();
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

        rcDesease.setAdapter(mAdapter);
        rcDesease.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        rvHealthReconder.setAdapter(healtRecoderAdapter);
        rvHealthReconder.setLayoutManager(new LinearLayoutManager(getBaseContext(), LinearLayoutManager.HORIZONTAL, false));
    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar_top);
        rcDesease = (RecyclerView) findViewById(R.id.rv_Desease);
        rvHealthReconder = (RecyclerView) findViewById(R.id.rv_health_records);
        IIlness ilness1 = new IIlness(false);
        IIlness ilness3 = new IIlness(false);
        IIlness ilness4 = new IIlness(false);
        IIlness ilness5 = new IIlness(false);
        IIlness ilness6 = new IIlness(true);
        ArrayList<IIlness> iIlnesses = new ArrayList<>();
        iIlnesses.add(ilness1);
        iIlnesses.add(ilness3);
        iIlnesses.add(ilness4);
        iIlnesses.add(ilness5);
        iIlnesses.add(ilness6);
        mAdapter = new IIlnessAdapter2(getApplicationContext(), iIlnesses);
        healtRecoderAdapter = new HealtRecoderAdapter();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
}
