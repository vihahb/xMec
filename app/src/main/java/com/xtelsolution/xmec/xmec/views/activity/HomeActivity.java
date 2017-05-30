package com.xtelsolution.xmec.xmec.views.activity;

import android.animation.ValueAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.model.LatLng;
import com.polyak.iconswitch.IconSwitch;
import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.LoginManager;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
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

import vn.com.elcom.mymenu.ContextMenuDialogFragment;
import vn.com.elcom.mymenu.MenuObject;
import vn.com.elcom.mymenu.MenuParams;
import vn.com.elcom.mymenu.interfaces.OnMenuItemClickListener;
import vn.com.elcom.mymenu.interfaces.OnMenuItemLongClickListener;

public class HomeActivity extends BasicActivity implements IMapView,
        IconSwitch.CheckedChangeListener, ValueAnimator.AnimatorUpdateListener,
        ItemClickListener, View.OnClickListener, OnMenuItemClickListener, OnMenuItemLongClickListener {
    private Toolbar toolbar;
    private FrameLayout layout;
    private ContextMenuDialogFragment mMenuDialogFragment;
    private FragmentManager fragmentManager;
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
        fragmentManager = getSupportFragmentManager();
        mapHealthyCareList = new ArrayList<>();
        init();
        initReceiver();
        window = getWindow();
        initMenuFragment();

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

    protected void addFragment(Fragment fragment) {
        invalidateOptionsMenu();
        String backStackName = fragment.getClass().getName();
        boolean fragmentPopped = fragmentManager.popBackStackImmediate(backStackName, 0);
        if (!fragmentPopped) {
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            transaction.add(R.id.content_frame, fragment, backStackName)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//            if (addToBackStack)
            transaction.addToBackStack(backStackName);
            transaction.commit();
        }
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
        switch (item.getItemId()) {
            case android.R.id.home:
                if (fragmentManager.findFragmentByTag(ContextMenuDialogFragment.TAG) == null)
                    mMenuDialogFragment.show(fragmentManager, ContextMenuDialogFragment.TAG);

                break;
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
        addFragment(HomeFragment.newInstance());
        tvtoolbarTitle.setText(getResources().getString(R.string.user_medical));
        setActionBar();
    }


    private void setActionBar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

    }


    private void initMenuFragment() {
        MenuParams menuParams = new MenuParams();
        menuParams.setActionBarSize((int) getResources().getDimension(R.dimen.tool_bar_height));
        menuParams.setMenuObjects(getMenuObjects());
        menuParams.setClosableOutside(false);
        mMenuDialogFragment = ContextMenuDialogFragment.newInstance(menuParams);
        mMenuDialogFragment.setItemClickListener(this);
        mMenuDialogFragment.setItemLongClickListener(this);
    }

    List<MenuObject> menuObjects;

    private List<MenuObject> getMenuObjects() {
        menuObjects = new ArrayList<>();

        MenuObject close = new MenuObject();
        close.setResource(R.drawable.ic_close_black_24dp);

        MenuObject home = new MenuObject(getString(R.string.user_medical));
        home.setResource(R.drawable.ic_menu_yba);

        MenuObject seatCsyt = new MenuObject(getString(R.string.search_health_center));
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_cosoyte);
        seatCsyt.setBitmap(b);

        MenuObject searchDisease = new MenuObject(getString(R.string.find_disease));
        BitmapDrawable bd = new BitmapDrawable(getResources(),
                BitmapFactory.decodeResource(getResources(), R.drawable.ic_menu_benh));
        searchDisease.setDrawable(bd);

        MenuObject searchDrug = new MenuObject(getString(R.string.find_drug));
        searchDrug.setResource(R.drawable.ic_menu_thuoc);

        MenuObject news = new MenuObject(getString(R.string.news));
        news.setResource(R.drawable.ic_menu_tintuc);

        menuObjects.add(close);
        menuObjects.add(home);
        menuObjects.add(news);
        menuObjects.add(searchDisease);
        menuObjects.add(searchDrug);
        menuObjects.add(seatCsyt);
        return menuObjects;
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

    int curentTab = 1;

    @Override
    public void onMenuItemClick(View clickedView, int position) {
        Log.e(TAG, "onMenuItemClick: " + position);
        if (curentTab != position) {
            curentTab = position;
            switch (position) {
                case 1:
                    addFragment(HomeFragment.newInstance());
                    break;
                case 2:
                    addFragment(NewsFeedFragment.newInstance());
                    break;
                case 3:
                    addFragment(MedicineFragment.newInstance());
                    break;
                case 4:
                    addFragment(SearchFragment.newInstance());
                    break;
                case 5:
                    addFragment(MapFragment.newInstance());
                    break;

            }
            if (position > 0)
                tvtoolbarTitle.setText(menuObjects.get(position).getTitle());
            if (position == 5) {
                toolbarSearch.setVisibility(View.VISIBLE);
                tvtoolbarTitle.setVisibility(View.GONE);
                content.setVisibility(View.VISIBLE);
                iconSwitch.setChecked(IconSwitch.Checked.LEFT);
            } else {
                iconSwitch.setChecked(IconSwitch.Checked.RIGHT);
                toolbarSearch.setVisibility(View.GONE);
                content.setVisibility(View.GONE);
                tvtoolbarTitle.setVisibility(View.VISIBLE);

            }
        }
    }

    @Override
    public void onMenuItemLongClick(View clickedView, int position) {

    }
}
