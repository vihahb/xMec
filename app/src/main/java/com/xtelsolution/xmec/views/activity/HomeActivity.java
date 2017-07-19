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
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
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
    private String TAG = "HomeActivity";
    private BroadcastReceiver receiver;
    private TextView tvtoolbarTitle, textMenuLogin;
    private ImageView icMenuLogin;
    private CallbackManager callbackManager;
    private boolean isOpenMenu = false;
    private int curentTab = 1;
    private boolean isShowingListType=false ;
    private FrameLayout root;
    private View contentHamburger;
    private View guillotineMenu;
    private LinearLayout itemYba, itemTinTuc, itemTimKiemBenh, itemTimKiemThuoc, itemTimKiemCoSoYte, itemLogIn;
    private LinearLayout llMapToolbar;
    private GuillotineAnimation animation;

    private static final long RIPPLE_DURATION = 250;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        fragmentManager = getSupportFragmentManager();
        init();
        initReceiver();
    }


    private void init() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_top);
        tvtoolbarTitle = (TextView) findViewById(R.id.toolbar_title);
        tvtoolbarTitle.setText(getString(R.string.user_medical));
        toolbar.setTitle("");
        initView();
        callbackManager = CallbackManager.create(this);
        initUiNav();
    }

    private void initUiNav() {
        root = (FrameLayout) findViewById(R.id.container_frame);
        contentHamburger = (View) findViewById(R.id.content_hamburger);
        llMapToolbar = (LinearLayout) findViewById(R.id.toolbar_search);
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
                isOpenMenu = false;
                animation.close();
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
                    dissisMapToolbar();
                    tvtoolbarTitle.setText(getString(R.string.title_menu_y_ba));
                    itemYba.setBackgroundColor(getResources().getColor(R.color.informationPrimary));
                    addFragment(HomeFragment.newInstance());
                    break;
                case 2:
                    dissisMapToolbar();
                    tvtoolbarTitle.setText(getString(R.string.title_menu_tin_tuc));
                    itemTinTuc.setBackgroundColor(getResources().getColor(R.color.informationPrimary));
                    addFragment(NewsFeedFragment.newInstance());
                    break;
                case 3:
                    dissisMapToolbar();
                    tvtoolbarTitle.setText(getString(R.string.find_drug));
                    itemTimKiemBenh.setBackgroundColor(getResources().getColor(R.color.informationPrimary));
                    addFragment(MedicineFragment.newInstance());
                    break;
                case 4:
                    dissisMapToolbar();
                    tvtoolbarTitle.setText(getString(R.string.find_disease));
                    itemTimKiemThuoc.setBackgroundColor(getResources().getColor(R.color.informationPrimary));
                    addFragment(SearchFragment.newInstance());
                    break;
                case 5:
                    showMapToolbar();
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

    private void dissisMapToolbar(){
        tvtoolbarTitle.setVisibility(View.VISIBLE);
        llMapToolbar.setVisibility(View.GONE);
    }
    private void showMapToolbar(){
        tvtoolbarTitle.setVisibility(View.GONE);
        llMapToolbar.setVisibility(View.VISIBLE);
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

    protected void
    addFragment(Fragment fragment) {
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
    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_find_health_cennter, menu);
//        SearchView searchView = (SearchView) menu.findItem(R.id.search_view).getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                return false;
//            }
//        });
//        final MenuItem menuItem = menu.findItem(R.id.list);
//        menuItem.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                isShowingListType = !isShowingListType;
//                menuItem.setIcon(getResources().getDrawable(getDrawable()));
//                return false;
//            }
//        });
        return true;
    }

    public int getDrawable() {
        return (isShowingListType) ? R.mipmap.avatar : R.mipmap.family_logo;
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

    private void setActionBar() {
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View v) {
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


}
