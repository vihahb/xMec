package com.xtelsolution.xmec.xmec.views.activity;

import android.Manifest;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.lv.listenkeyboardevent.KeyboardVisibilityEvent;
import com.lv.listenkeyboardevent.KeyboardVisibilityEventListener;
import com.polyak.iconswitch.IconSwitch;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.LoginManager;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.listener.OnLoadMapSuccessListener;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.RESP_Map_Healthy_Care;
import com.xtelsolution.xmec.model.SharedPreferencesUtils;
import com.xtelsolution.xmec.xmec.views.adapter.HospitalCenterAdapter;
import com.xtelsolution.xmec.xmec.views.fragment.HomeFragment;
import com.xtelsolution.xmec.xmec.views.fragment.MapFragment;
import com.xtelsolution.xmec.xmec.views.fragment.MedicineFragment;
import com.xtelsolution.xmec.xmec.views.fragment.NewsFeedFragment;
import com.xtelsolution.xmec.xmec.views.fragment.SearchFragment;
import com.xtelsolution.xmec.xmec.views.inf.IMapView;

import java.util.ArrayList;
import java.util.List;

import eu.long1.spacetablayout.SpaceTabLayout;
import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
import yalantis.com.sidemenu.interfaces.Resourceble;
import yalantis.com.sidemenu.interfaces.ScreenShotable;
import yalantis.com.sidemenu.model.SlideMenuItem;
import yalantis.com.sidemenu.util.ViewAnimator;

public class HomeActivity extends BasicActivity implements IMapView,
        IconSwitch.CheckedChangeListener, ValueAnimator.AnimatorUpdateListener,
        ItemClickListener, ViewAnimator.ViewAnimatorListener, View.OnClickListener {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private List<SlideMenuItem> list = new ArrayList<>();
    private ViewAnimator viewAnimator;
    private LinearLayout linearLayout;
    private Toolbar toolbar;
    private FrameLayout layout;

    //    private SlidingDrawer slidingDrawer;
    private RecyclerView rvHosiptalCenter;
    private HospitalCenterAdapter adapter;
    private String TAG = "HomeActivity";
    private List<RESP_Map_Healthy_Care> mapHealthyCareList;
    private BroadcastReceiver receiver;
    private TextView tvtoolbarTitle, textError;
    private Button btnAction;
    private ProgressBar loadding;
    private CallbackManager callbackManager;
    private Activity thisActivity;
    private EditText edsearch;


    private static final int DURATION_COLOR_CHANGE_MS = 400;


    private int[] toolbarColors;
    private int[] statusBarColors;
    private ValueAnimator statusBarAnimator;
    private Interpolator contentInInterpolator;
    private Interpolator contentOutInterpolator;
    private Point revealCenter;

    private Window window;
    private View toolbarSearch;
    private View content;
    private IconSwitch iconSwitch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mapHealthyCareList = new ArrayList<>();
        thisActivity = this;
        init();
        initReceiver();
        window = getWindow();

        initColors();
        initAnimationRelatedFields();
        content = findViewById(R.id.content);
        content.setVisibility(View.GONE);
        toolbarSearch = findViewById(R.id.toolbar_search);
        edsearch = (EditText) findViewById(R.id.ed_search);

        iconSwitch = (IconSwitch) findViewById(R.id.icon_switch);
        iconSwitch.setCheckedChangeListener(this);
        updateColors(false);
        rvHosiptalCenter.setAdapter(adapter);
        rvHosiptalCenter.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }


    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        tvtoolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        textError = (TextView) findViewById(R.id.textError);
        btnAction = (Button) findViewById(R.id.btnAction);
        loadding = (ProgressBar) findViewById(R.id.progress_bar);
        toolbar.setTitle("");
        initView();
        callbackManager = CallbackManager.create(this);
        rvHosiptalCenter = (RecyclerView) findViewById(R.id.rList);
        adapter = new HospitalCenterAdapter(getApplicationContext(), mapHealthyCareList);
        adapter.setItemClickListener(this);
        btnAction.setOnClickListener(this);
    }


    public void initReceiver() {
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION_HIDE_BOTTOM_BAR);
        filter.addAction(Constant.ACTION_SHOW_BOTTOM_BAR);
        registerReceiver(receiver, filter);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_main, menu);
//        if (callbackManager.getCurrentSession() == null) {
//            menu.getItem(0).setIcon(R.drawable.ic_action_login);
//            menu.getItem(0).setTitle(R.string.login);
//        } else {
//            menu.getItem(0).setIcon(R.drawable.ic_action_logout);
//            menu.getItem(0).setTitle(R.string.logout);
//        }
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.action_settings:
                return true;
            case R.id.action_login_logout:
                if (callbackManager.getCurrentSession() == null) {
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                } else {
                    LoginManager.logOut();
                    SharedPreferencesUtils.getInstance().setLogined();
                    showToast("Đã đăng xuất");
                    SharedPreferencesUtils.getInstance().setLogout();
                    item.setIcon(R.drawable.ic_action_login);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startActivityAndFinish(LoginActivity.class);
                        }
                    }, 1000);


                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(receiver);
    }

    boolean doubleBackToExitPressedOnce = false;

    @Override
    public void onBackPressed() {
        if (iconSwitch.getChecked().equals(IconSwitch.Checked.LEFT)) {
            iconSwitch.setChecked(IconSwitch.Checked.RIGHT);
            return;
        }
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
//        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        for (Fragment fragment : fragmentManager.getFragments()) {
//            fragment.onActivityResult(requestCode, resultCode, data);
//        }
        if (requestCode == 97) {
            sendBroadcast(new Intent(Constant.ACTION_LOCATION));
        }

    }

    @Override
    public void onItemClickListener(Object item, int position) {
        Intent i = new Intent(HomeActivity.this, DetailHospitalActivity.class);
        i.putExtra(Constant.HEALTHY_CENTER_ID, ((RESP_Map_Healthy_Care) item).getId());
        startActivity(i);
    }

    public IMapView get() {
        return this;
    }

    //Version 1.2
    private void initView() {
        layout = (FrameLayout) findViewById(R.id.content_frame);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.content_frame, HomeFragment.newInstance(), Constant.HOME)
                .commit();
        tvtoolbarTitle.setText(getResources().getString(R.string.user_medical));
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
//        drawerLayout.setScrimColor(Color.TRANSPARENT);
        linearLayout = (LinearLayout) findViewById(R.id.left_drawer);
        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
            }
        });


        setActionBar();
        createMenuList();
        viewAnimator = new ViewAnimator<>(this, list, HomeFragment.newInstance(), drawerLayout, this);
    }

    private void createMenuList() {
        SlideMenuItem menuItem0 = new SlideMenuItem(Constant.CLOSE, R.drawable.ic_close_dark);
        list.add(menuItem0);
        SlideMenuItem menuItem = new SlideMenuItem(Constant.HOME, R.drawable.ic_menu_yba);
        list.add(menuItem);
        SlideMenuItem menuItem2 = new SlideMenuItem(Constant.BENH, R.drawable.ic_menu_benh);
        list.add(menuItem2);
        SlideMenuItem menuItem3 = new SlideMenuItem(Constant.THUOC, R.drawable.ic_menu_thuoc);
        list.add(menuItem3);
        SlideMenuItem menuItem4 = new SlideMenuItem(Constant.TINTUC, R.drawable.ic_menu_tintuc);
        list.add(menuItem4);
        SlideMenuItem menuItem5 = new SlideMenuItem(Constant.COSOYTE, R.drawable.ic_menu_cosoyte);
        list.add(menuItem5);
        SlideMenuItem menuItem6 = new SlideMenuItem(Constant.LOGIN, R.drawable.ic_action_login);
        list.add(menuItem6);
    }

    private void setActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
                toolbar,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                linearLayout.removeAllViews();
                linearLayout.invalidate();
            }

            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                if (slideOffset > 0.6 && linearLayout.getChildCount() == 0)
                    viewAnimator.showMenuContent();
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        drawerLayout.setDrawerListener(drawerToggle);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    String TabCurrent = "";


    private ScreenShotable replaceFragment(ScreenShotable screenShotable, int topPosition, String name) {
        View view = findViewById(R.id.content_frame);
        int finalRadius = Math.max(view.getWidth(), view.getHeight());
        SupportAnimator animator = ViewAnimationUtils.createCircularReveal(view, 0, topPosition, 0, finalRadius);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(ViewAnimator.CIRCULAR_REVEAL_ANIMATION_DURATION);

        findViewById(R.id.content_overlay).setBackgroundDrawable(new BitmapDrawable(getResources(), screenShotable.getBitmap()));
        animator.start();
        iconSwitch.setChecked(IconSwitch.Checked.RIGHT);
        switch (name) {
            case Constant.HOME:
                tvtoolbarTitle.setText(getResources().getString(R.string.user_medical));
                HomeFragment contentFragment = HomeFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment, name).commit();
                return contentFragment;
            case Constant.THUOC:
                tvtoolbarTitle.setText(getResources().getString(R.string.find_disease));
                MedicineFragment medicineFragment = MedicineFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, medicineFragment, name).commit();
                return medicineFragment;
            case Constant.BENH:
                tvtoolbarTitle.setText(getResources().getString(R.string.find_drug));
                SearchFragment searchFragment = SearchFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, searchFragment, name).commit();
                return searchFragment;
            case Constant.TINTUC:
                NewsFeedFragment newsFeedFragment = NewsFeedFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, newsFeedFragment, name).commit();
                tvtoolbarTitle.setText(getResources().getString(R.string.news));
                return newsFeedFragment;

            case Constant.COSOYTE:
                iconSwitch.setChecked(IconSwitch.Checked.LEFT);
                MapFragment mapFragment = MapFragment.newInstance();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, mapFragment, name).commit();
                tvtoolbarTitle.setText(getResources().getString(R.string.health_care));
                return mapFragment;


        }


        HomeFragment contentFragment = HomeFragment.newInstance();
        getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, contentFragment).commit();
        return contentFragment;
    }

    @Override
    public ScreenShotable onSwitch(Resourceble slideMenuItem, ScreenShotable screenShotable, int position) {
        Log.e(TAG, "onSwitch: " + slideMenuItem.getName());

        if (slideMenuItem.getName().equals(Constant.COSOYTE)) {
            toolbarSearch.setVisibility(View.VISIBLE);
            tvtoolbarTitle.setVisibility(View.GONE);
            content.setVisibility(View.VISIBLE);
            iconSwitch.setChecked(IconSwitch.Checked.RIGHT);
        } else {
            iconSwitch.setChecked(IconSwitch.Checked.RIGHT);
            toolbarSearch.setVisibility(View.GONE);
            content.setVisibility(View.GONE);
            tvtoolbarTitle.setVisibility(View.VISIBLE);

        }
        changeContentVisibility();
        switch (slideMenuItem.getName()) {
            case Constant.CLOSE:
                return screenShotable;
            default:
                if (Constant.LOGIN.equals(slideMenuItem.getName())) {
                    if (callbackManager.getCurrentSession() == null) {
                        startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    } else {
                        LoginManager.logOut();
                        SharedPreferencesUtils.getInstance().setLogined();
                        showToast("Đã đăng xuất");
                        SharedPreferencesUtils.getInstance().setLogout();
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                startActivityAndFinish(LoginActivity.class);
                            }
                        }, 1000);


                    }
                    return screenShotable;
                } else if (!TabCurrent.equals(slideMenuItem.getName())) {
                    TabCurrent = slideMenuItem.getName();
                    return replaceFragment(screenShotable, position, slideMenuItem.getName());
                } else {
                    return screenShotable;
                }
        }

    }

    @Override
    public void disableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(false);

    }

    @Override
    public void enableHomeButton() {
        getSupportActionBar().setHomeButtonEnabled(true);
        drawerLayout.closeDrawers();

    }

    @Override
    public void addViewToContainer(View view) {
        linearLayout.addView(view);
    }


    private void updateColors(boolean animated) {
        int colorIndex = iconSwitch.getChecked().ordinal();
        toolbar.setBackgroundColor(toolbarColors[colorIndex]);
        if (animated) {
            switch (iconSwitch.getChecked()) {
                case LEFT:
                    statusBarAnimator.reverse();
                    break;
                case RIGHT:
                    statusBarAnimator.start();
                    break;
            }
            revealToolbar();
        } else {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(statusBarColors[colorIndex]);
            }
        }
    }

    private void revealToolbar() {
        iconSwitch.getThumbCenter(revealCenter);
        moveFromSwitchToToolbarSpace(revealCenter);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            android.view.ViewAnimationUtils.createCircularReveal(toolbar,
                    revealCenter.x, revealCenter.y,
                    iconSwitch.getHeight(), toolbar.getWidth())
                    .setDuration(DURATION_COLOR_CHANGE_MS)
                    .start();
        }
    }

    @Override
    public void onAnimationUpdate(ValueAnimator animator) {
        if (animator == statusBarAnimator) {
            int color = (Integer) animator.getAnimatedValue();
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(color);
            }
        }
    }

    private void changeContentVisibility() {
        int targetTranslation = 0;
        Interpolator interpolator = null;
        switch (iconSwitch.getChecked()) {
            case LEFT:
                targetTranslation = 0;
                interpolator = contentInInterpolator;

                break;
            case RIGHT:
                targetTranslation = content.getHeight();
                interpolator = contentOutInterpolator;
                break;
        }
        content.animate().cancel();
        content.animate()
                .translationY(targetTranslation)
                .setInterpolator(interpolator)
                .setDuration(DURATION_COLOR_CHANGE_MS)
                .start();
    }

    @Override
    public void onCheckChanged(IconSwitch.Checked current) {
        updateColors(true);
        changeContentVisibility();
    }


    private void initAnimationRelatedFields() {
        revealCenter = new Point();
        statusBarAnimator = createArgbAnimator(
                statusBarColors[IconSwitch.Checked.LEFT.ordinal()],
                statusBarColors[IconSwitch.Checked.RIGHT.ordinal()]);
        contentInInterpolator = new OvershootInterpolator(0.5f);
        contentOutInterpolator = new DecelerateInterpolator();
    }

    private void initColors() {
        toolbarColors = new int[IconSwitch.Checked.values().length];
        statusBarColors = new int[toolbarColors.length];
        toolbarColors[IconSwitch.Checked.RIGHT.ordinal()] = color(R.color.colorPrimary);
        statusBarColors[IconSwitch.Checked.RIGHT.ordinal()] = color(R.color.colorPrimary);
        toolbarColors[IconSwitch.Checked.LEFT.ordinal()] = color(R.color.informationPrimary);
        statusBarColors[IconSwitch.Checked.LEFT.ordinal()] = color(R.color.informationPrimaryDark);
    }

    private ValueAnimator createArgbAnimator(int leftColor, int rightColor) {
        ValueAnimator animator = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            animator = ValueAnimator.ofArgb(leftColor, rightColor);
        }
        animator.setDuration(DURATION_COLOR_CHANGE_MS);
        animator.addUpdateListener(this);
        return animator;
    }

    private void moveFromSwitchToToolbarSpace(Point point) {
        point.set(point.x + iconSwitch.getLeft(), point.y + iconSwitch.getTop());
    }

    @ColorInt
    private int color(@ColorRes int res) {
        return ContextCompat.getColor(this, res);
    }

    @Override
    public void onMapCreateSuccess() {
        sendBroadcast(new Intent(Constant.ACTION_LOCATION));
    }

    @Override
    public void onProviderDisabled() {
        loadding.setVisibility(View.GONE);
        textError.setVisibility(View.VISIBLE);
        textError.setText(R.string.gps_permission);
        btnAction.setVisibility(View.VISIBLE);
        btnAction.setText(R.string.btn_open_gps);
    }

    @Override
    public void onGetCurrentLocationFinish(LatLng latLng) {

    }

    @Override
    public void onGetListHealtyCareSuccess(List<RESP_Map_Healthy_Care> data) {
        adapter.addAll(data);
        if (adapter.getItemCount() > 0) {
            rvHosiptalCenter.setVisibility(View.VISIBLE);
            textError.setVisibility(View.GONE);
            loadding.setVisibility(View.GONE);
            btnAction.setVisibility(View.GONE);
        } else {
            loadding.setVisibility(View.GONE);
            textError.setVisibility(View.VISIBLE);
            textError.setText(R.string.emtry_list_new_feed);
            btnAction.setVisibility(View.VISIBLE);
            btnAction.setText(R.string.reload);
        }
    }

    @Override
    public void onLocationChange(LatLng latLng) {

    }

    @Override
    public void onPermissionDenied() {
        loadding.setVisibility(View.GONE);
        textError.setVisibility(View.VISIBLE);
        textError.setText(R.string.location_permission);
        btnAction.setVisibility(View.VISIBLE);
        btnAction.setText(R.string.btn_location_permission);
    }

    @Override
    public void onPermissionGranted() {
        loadding.setVisibility(View.VISIBLE);
        textError.setVisibility(View.GONE);
        btnAction.setVisibility(View.GONE);
        sendBroadcast(new Intent(Constant.ACTION_RELOA_DATA_MAP));
    }

    @Override
    public void onGPSDisabled() {
        loadding.setVisibility(View.GONE);
        textError.setVisibility(View.VISIBLE);
        textError.setText(R.string.gps_permission);
        btnAction.setVisibility(View.VISIBLE);
        btnAction.setText(R.string.btn_open_gps);
    }

    @Override
    public Fragment getFragmentView() {
        return null;
    }

    @Override
    public void onClick(View v) {
        if (btnAction.getText().equals(getString(R.string.btn_open_gps)))
            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 97);
        if (btnAction.getText().equals(getString(R.string.btn_location_permission)))
            sendBroadcast(new Intent(Constant.ACTION_PERMISSION_LOCATION));
        if (btnAction.getText().equals(getString(R.string.reload)))
            sendBroadcast(new Intent(Constant.ACTION_RELOA_DATA_MAP));
    }
}
