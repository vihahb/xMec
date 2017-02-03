package com.xtelsolution.xmec.xmec.views.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.DragEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.SlidingDrawer;

import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.common.xLog;
import com.xtelsolution.xmec.xmec.views.adapter.HospitalCenterAdapter;
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
    private SlidingDrawer slidingDrawer;
    private RecyclerView rvHosiptalCenter;
    private HospitalCenterAdapter adapter;
    private ImageView imgHanderSliding;
    private String TAG = "HomeActivity";
    private BroadcastReceiver receiver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        initReceiver();
        tabLayout.initialize(viewPager, getSupportFragmentManager(), fragmentList, savedInstanceState);
        rvHosiptalCenter.setAdapter(adapter);
        rvHosiptalCenter.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        slidingDrawer.setVisibility(View.GONE);
        slidingDrawer.unlock();

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
        slidingDrawer = (SlidingDrawer) findViewById(R.id.drawer);
        rvHosiptalCenter = (RecyclerView) slidingDrawer.findViewById(R.id.rv_hospital_center);
        adapter = new HospitalCenterAdapter(getApplicationContext(), HomeActivity.this);
        imgHanderSliding = (ImageView) slidingDrawer.findViewById(R.id.handleImageView);
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position == 4) {
                    slidingDrawer.setVisibility(View.VISIBLE);
                } else {
                    slidingDrawer.setVisibility(View.GONE);
                }

                tabLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();

            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

    }

    public void initReceiver() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Constant.ACTION_HIDE_BOTTOM_BAR)) {
                    tabLayout.animate().translationY(tabLayout.getHeight()).setInterpolator(new AccelerateInterpolator(2)).start();
//                    tabLayout.setVisibility(View.GONE);
                    xLog.i(TAG, "Action hide");
                } else if (intent.getAction().equals(Constant.ACTION_SHOW_BOTTOM_BAR)) {
                    tabLayout.animate().translationY(0).setInterpolator(new DecelerateInterpolator(2)).start();
//                    tabLayout.setVisibility(View.VISIBLE);
                    xLog.i(TAG, "Action show");
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_HIDE_BOTTOM_BAR);
        filter.addAction(Constant.ACTION_SHOW_BOTTOM_BAR);
        registerReceiver(receiver, filter);
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

    @Override
    public void onBackPressed() {
        if (slidingDrawer.isOpened())
            slidingDrawer.close();
        else
            super.onBackPressed();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }
}
