package com.xtelsolution.xmec.views.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.xtel.nipservicesdk.CallbackManager;
import com.xtel.nipservicesdk.LoginManager;
import com.xtelsolution.xmec.R;
import com.xtelsolution.xmec.common.Constant;
import com.xtelsolution.xmec.listener.list.ItemClickListener;
import com.xtelsolution.xmec.model.RESP_Map_Healthy_Care;
import com.xtelsolution.xmec.model.SharedPreferencesUtils;
import com.xtelsolution.xmec.views.fragment.HomeFragment;
import com.xtelsolution.xmec.views.fragment.MapFragment;
import com.xtelsolution.xmec.views.fragment.MedicineFragment;
import com.xtelsolution.xmec.views.fragment.NewsFeedFragment;
import com.xtelsolution.xmec.views.fragment.SearchFragment;
import com.yalantis.guillotine.animation.GuillotineAnimation;


public class HomeActivity extends BasicActivity implements /*IMapView,*/
        ItemClickListener, View.OnClickListener {
    private Toolbar toolbar;
    private FragmentManager fragmentManager;
    //    private SlidingDrawer slidingDrawer;
//    private RecyclerView rvHosiptalCenter;
//    private HospitalCenterAdapter adapter;
    private String TAG = "HomeActivity";
    //    private List<RESP_Map_Healthy_Care> mapHealthyCareList;
    private BroadcastReceiver receiver;
    private TextView tvtoolbarTitle, textMenuLogin;
    private ImageView icMenuLogin;
    //    private Button btnAction;
//    private ProgressBar loadding;
    private CallbackManager callbackManager;
    //    private EditText edsearch;
    private boolean isOpenMenu = false;

    private FrameLayout root;
    private View contentHamburger;
    private View guillotineMenu;
    private LinearLayout itemYba, itemTinTuc, itemTimKiemBenh, itemTimKiemThuoc, itemTimKiemCoSoYte, itemLogIn;
    private GuillotineAnimation animation;

    private static final long RIPPLE_DURATION = 250;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fragmentManager = getSupportFragmentManager();
//        mapHealthyCareList = new ArrayList<>();
        init();
        initReceiver();


//        rvHosiptalCenter.setAdapter(adapter);
//        rvHosiptalCenter.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
    }


    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        tvtoolbarTitle = (TextView) findViewById(R.id.toolbar_title);
//        textError = (TextView) findViewById(R.id.textError);
//        btnAction = (Button) findViewById(R.id.btnAction);
//        loadding = (ProgressBar) findViewById(R.id.progress_bar);
//        Menu nav


//        nav end
        toolbar.setTitle("");
        initView();
        callbackManager = CallbackManager.create(this);
//        rvHosiptalCenter = (RecyclerView) findViewById(R.id.rList);
//        adapter = new HospitalCenterAdapter(getApplicationContext(), mapHealthyCareList);
//        adapter.setItemClickListener(this);
//        btnAction.setOnClickListener(this);
        initUiNav();
    }

    private void initUiNav() {
        root = (FrameLayout) findViewById(R.id.container_frame);
        contentHamburger = (View) findViewById(R.id.content_hamburger);
        guillotineMenu = LayoutInflater.from(this).inflate(R.layout.navigation_layout, null);
        root.addView(guillotineMenu);
        View view = guillotineMenu.findViewById(R.id.guillotine_hamburger);
        animation = new GuillotineAnimation.GuillotineBuilder(guillotineMenu, view, contentHamburger)
                .setStartDelay(RIPPLE_DURATION)
                .setActionBarViewForAnimation(toolbar)
                .setClosedOnStart(true)
                .build();

        itemYba = (LinearLayout) findViewById(R.id.itemYba);
        itemTinTuc = (LinearLayout) findViewById(R.id.itemTinTuc);
        itemTimKiemBenh = (LinearLayout) findViewById(R.id.itemTimKiemBenh);
        itemTimKiemThuoc = (LinearLayout) findViewById(R.id.itemTimKiemThuoc);
        itemTimKiemCoSoYte = (LinearLayout) findViewById(R.id.itemTimKiemCoSoYte);
        itemLogIn = (LinearLayout) findViewById(R.id.itemLogIn);
        icMenuLogin = (ImageView) findViewById(R.id.icMenuLogin);
        textMenuLogin = (TextView) findViewById(R.id.textMenuLogin);
        itemYba.setOnClickListener(this);
        itemTinTuc.setOnClickListener(this);
        itemTimKiemBenh.setOnClickListener(this);
        itemTimKiemThuoc.setOnClickListener(this);
        itemTimKiemCoSoYte.setOnClickListener(this);
        itemLogIn.setOnClickListener(this);
        itemYba.setBackgroundColor(getResources().getColor(R.color.informationPrimary));
        if (callbackManager.getCurrentSession() == null) {
            icMenuLogin.setImageResource(R.drawable.ic_action_login);
            textMenuLogin.setText(getString(R.string.login));
        } else {
            icMenuLogin.setImageResource(R.drawable.ic_action_logout);
            textMenuLogin.setText(getString(R.string.logout));
        }
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (isOpenMenu) {
                isOpenMenu = false;
                animation.close();
//                } else {
////                    animation.open();
//                    isOpenMenu = true;
//
//                }
                Log.e(TAG, "clickOpenMenu: " + isOpenMenu);
            }
        });
        findViewById(R.id.content_hamburger).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isOpenMenu = true;
                animation.open();
                Log.e(TAG, "clickOpenMenu: " + isOpenMenu);
            }
        });
    }

    private void cleanSelect() {
        TypedValue outValue = new TypedValue();
        getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);

        itemYba.setBackgroundResource(outValue.resourceId);
        itemTinTuc.setBackgroundResource(outValue.resourceId);
        itemTimKiemBenh.setBackgroundResource(outValue.resourceId);
        itemTimKiemThuoc.setBackgroundResource(outValue.resourceId);
        itemTimKiemCoSoYte.setBackgroundResource(outValue.resourceId);
    }

    public void onMenuItemClick(int position) {
        Log.e(TAG, "onMenuItemClick: " + position);
        isOpenMenu = false;
        if (curentTab != position) {
            curentTab = position;
            cleanSelect();
            switch (position) {
                case 1:
                    tvtoolbarTitle.setText(getString(R.string.title_menu_y_ba));
                    itemYba.setBackgroundColor(getResources().getColor(R.color.informationPrimary));
                    addFragment(HomeFragment.newInstance());
                    break;
                case 2:
                    tvtoolbarTitle.setText(getString(R.string.title_menu_tin_tuc));
                    itemTinTuc.setBackgroundColor(getResources().getColor(R.color.informationPrimary));
                    addFragment(NewsFeedFragment.newInstance());
                    break;
                case 3:
                    tvtoolbarTitle.setText(getString(R.string.find_disease));
                    itemTimKiemBenh.setBackgroundColor(getResources().getColor(R.color.informationPrimary));
                    addFragment(MedicineFragment.newInstance());
                    break;
                case 4:
                    tvtoolbarTitle.setText(getString(R.string.find_drug));
                    itemTimKiemThuoc.setBackgroundColor(getResources().getColor(R.color.informationPrimary));
                    addFragment(SearchFragment.newInstance());
                    break;
                case 5:
                    tvtoolbarTitle.setText(getString(R.string.title_tim_kiem_co_so_y_te));
                    itemTimKiemCoSoYte.setBackgroundColor(getResources().getColor(R.color.informationPrimary));
                    addFragment(MapFragment.newInstance());
                    break;
                case 6:
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
                                startActivityAndFinish(HomeActivity.class);
                            }
                        }, 1000);
                    }
                    break;

            }
        }
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
            transaction.add(R.id.content_frame, fragment/*, backStackName*/)
                    .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
            transaction.commit();
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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
        if (isOpenMenu) {
            animation.close();
            isOpenMenu = false;
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

//    //Version 1.2
    private void initView() {
        addFragment(HomeFragment.newInstance());
        tvtoolbarTitle.setText(getResources().getString(R.string.user_medical));
        setActionBar();
    }

    //
//
    private void setActionBar() {
        setSupportActionBar(toolbar);
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_menu_black_24dp);

    }
//
//
//    @Override
//    public void onMapCreateSuccess() {
//        sendBroadcast(new Intent(Constant.ACTION_LOCATION));
//    }
//
//    @Override
//    public void onProviderDisabled() {
//        loadding.setVisibility(View.GONE);
//        textError.setVisibility(View.VISIBLE);
//        textError.setText(R.string.gps_permission);
//        btnAction.setVisibility(View.VISIBLE);
//        btnAction.setText(R.string.btn_open_gps);
//    }
//
//    @Override
//    public void onGetCurrentLocationFinish(LatLng latLng) {
//
//    }
//
//    @Override
//    public void onGetListHealtyCareSuccess(List<RESP_Map_Healthy_Care> data) {
//        adapter.addAll(data);
//        if (adapter.getItemCount() > 0) {
//            rvHosiptalCenter.setVisibility(View.VISIBLE);
//            textError.setVisibility(View.GONE);
//            loadding.setVisibility(View.GONE);
//            btnAction.setVisibility(View.GONE);
//        } else {
//            loadding.setVisibility(View.GONE);
//            textError.setVisibility(View.VISIBLE);
//            textError.setText(R.string.emtry_list_new_feed);
//            btnAction.setVisibility(View.VISIBLE);
//            btnAction.setText(R.string.reload);
//        }
//    }
//
//    @Override
//    public void onLocationChange(LatLng latLng) {
//
//    }
//
//    @Override
//    public void onPermissionDenied() {
//        loadding.setVisibility(View.GONE);
//        textError.setVisibility(View.VISIBLE);
//        textError.setText(R.string.location_permission);
//        btnAction.setVisibility(View.VISIBLE);
//        btnAction.setText(R.string.btn_location_permission);
//    }
//
//    @Override
//    public void onPermissionGranted() {
//        loadding.setVisibility(View.VISIBLE);
//        textError.setVisibility(View.GONE);
//        btnAction.setVisibility(View.GONE);
//        sendBroadcast(new Intent(Constant.ACTION_RELOA_DATA_MAP));
//    }
//
//    @Override
//    public void onGPSDisabled() {
//        loadding.setVisibility(View.GONE);
//        textError.setVisibility(View.VISIBLE);
//        textError.setText(R.string.gps_permission);
//        btnAction.setVisibility(View.VISIBLE);
//        btnAction.setText(R.string.btn_open_gps);
//    }
//
//    @Override
//    public Fragment getFragmentView() {
//        return null;
//    }

    @Override
    public void onClick(View v) {
//        if (btnAction.getText().equals(getString(R.string.btn_open_gps)))
//            startActivityForResult(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS), 97);
//        if (btnAction.getText().equals(getString(R.string.btn_location_permission)))
//            sendBroadcast(new Intent(Constant.ACTION_PERMISSION_LOCATION));
//        if (btnAction.getText().equals(getString(R.string.reload)))
//            sendBroadcast(new Intent(Constant.ACTION_RELOA_DATA_MAP));
        switch (v.getId()) {
            case R.id.itemYba:
                animation.close();
                onMenuItemClick(1);
                break;
            case R.id.itemTinTuc:
                animation.close();
                onMenuItemClick(2);
                break;
            case R.id.itemTimKiemBenh:
                animation.close();
                onMenuItemClick(3);
                break;
            case R.id.itemTimKiemThuoc:
                animation.close();
                onMenuItemClick(4);
                break;
            case R.id.itemTimKiemCoSoYte:
                animation.close();
                onMenuItemClick(5);
                break;
            case R.id.itemLogIn:
                animation.close();
                onMenuItemClick(6);
                break;
        }
    }

    int curentTab = 1;


}
