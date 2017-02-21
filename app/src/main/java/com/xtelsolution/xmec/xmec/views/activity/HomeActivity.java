package com.xtelsolution.xmec.xmec.views.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.SlidingDrawer;

import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.LoginManager;
import com.xtel.nipservicesdk.utils.JsonHelper;
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

public class HomeActivity extends BasicActivity {

    private SpaceTabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragmentList;
    private SlidingDrawer slidingDrawer;
    private RecyclerView rvHosiptalCenter;
    private HospitalCenterAdapter adapter;
    private ImageView imgHanderSliding;
    private String TAG = "HomeActivity";
    private BroadcastReceiver receiver;
    private FragmentManager fragmentManager;
    private CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        init();
        initReceiver();
        tabLayout.initialize(viewPager, fragmentManager, fragmentList, savedInstanceState);
        rvHosiptalCenter.setAdapter(adapter);
        rvHosiptalCenter.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        slidingDrawer.setVisibility(View.GONE);
        slidingDrawer.unlock();
    }


    private void init() {
        List<String> list = new ArrayList<>();
        list.add("phimh");
        list.add("hungnt");
        list.add("hungtt");
        Log.d(TAG, "init: "+ JsonHelper.toJson(list));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        fragmentManager = getSupportFragmentManager();
        callbackManager = CallbackManager.create(this);
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
        if (callbackManager.getCurrentSession()==null){
            menu.getItem(0).setIcon(R.drawable.ic_action_login);
            menu.getItem(0).setTitle(R.string.login);
        }else {
            menu.getItem(0).setIcon(R.drawable.ic_action_logout);
            menu.getItem(0).setTitle(R.string.logout);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==R.id.action_login_logout){
            if (callbackManager.getCurrentSession()==null){
                startActivity(new Intent(HomeActivity.this,LoginActivity.class));
                finish();
            }else {
                LoginManager.logOut();
                showToast("Đã đăng xuất");
                item.setIcon(R.drawable.ic_action_login);
            }
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (slidingDrawer.isOpened())
            slidingDrawer.close();
        else {
            if (doubleBackToExitPressedOnce) {
                super.onBackPressed();
                return;
            }

            this.doubleBackToExitPressedOnce = true;
            showToast("Nhấn BACK 2 lần để thoát");

            new Handler().postDelayed(new Runnable() {

                @Override
                public void run() {
                    doubleBackToExitPressedOnce = false;
                }
            }, 2000);
        }
    }
}
