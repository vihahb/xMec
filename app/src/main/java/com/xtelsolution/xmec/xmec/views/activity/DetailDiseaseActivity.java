package com.xtelsolution.xmec.xmec.views.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.xmec.views.adapter.ViewPagerAdapter;
import com.xtelsolution.xmec.xmec.views.fragment.DetailDiseaseFragment;
import com.xtelsolution.xmec.xmec.views.fragment.OverviewDiseaseFragment;

public class DetailDiseaseActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TextView titleToolbar;
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_disease);
        init();
        setSupportActionBar(mToolbar);
        titleToolbar.setText("Bệnh Trĩ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        setupViewPager(mViewPager);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter tabAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        tabAdapter.addFragment(getResources().getString(R.string.overview_text), new OverviewDiseaseFragment());
        tabAdapter.addFragment(getResources().getString(R.string.detail_text), new DetailDiseaseFragment());
        viewPager.setAdapter(tabAdapter);

    }

    private void init() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        titleToolbar = (TextView) findViewById(R.id.toolbar_title);
        mViewPager = (ViewPager) findViewById(R.id.viewpager);
        mTabLayout = (TabLayout) findViewById(R.id.tab);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
