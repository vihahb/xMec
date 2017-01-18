package com.xtelsolution.xmec.xmec.views.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.xmec.views.fragment.HomeFragment;
import com.xtelsolution.xmec.xmec.views.fragment.MapFragment;
import com.xtelsolution.xmec.xmec.views.fragment.MedicineFragment;
import com.xtelsolution.xmec.xmec.views.fragment.NewsFeedFragment;
import com.xtelsolution.xmec.xmec.views.fragment.SearchFragment;

import java.util.ArrayList;
import java.util.List;

import eu.long1.spacetablayout.SpaceTabLayout;

public class HomeActivity extends AppCompatActivity {

    private SpaceTabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragmentList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        tabLayout.initialize(viewPager, getSupportFragmentManager(), fragmentList, savedInstanceState);

    }


    private void init() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        tabLayout = (SpaceTabLayout) findViewById(R.id.spaceTabLayout);
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        fragmentList = new ArrayList<>();
        fragmentList.add(new SearchFragment());
        fragmentList.add(new MedicineFragment());
        fragmentList.add(new HomeFragment());
        fragmentList.add(new NewsFeedFragment());
        fragmentList.add(new MapFragment());
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        tabLayout.saveState(outState);
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }


}
